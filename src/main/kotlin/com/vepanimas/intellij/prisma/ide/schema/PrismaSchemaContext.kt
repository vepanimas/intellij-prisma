package com.vepanimas.intellij.prisma.ide.schema

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiErrorElement
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.tree.IElementType
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType
import com.vepanimas.intellij.prisma.lang.psi.*
import com.vepanimas.intellij.prisma.lang.psi.PrismaElementTypes.TYPE_REFERENCE
import com.vepanimas.intellij.prisma.lang.psi.PrismaElementTypes.UNSUPPORTED_TYPE
import com.vepanimas.intellij.prisma.lang.psi.presentation.PrismaPsiRenderer

class PrismaSchemaContext(
    val kind: PrismaSchemaElementKind,
    val label: String,
    val elementType: IElementType,
    val element: PsiElement,
) {
    companion object {
        fun forElement(element: PsiElement?): PrismaSchemaContext? {
            if (element == null) {
                return null
            }

            val schemaElement = element.adjustSchemaElement() ?: return null
            val kind = schemaElement.schemaKind ?: return null
            val label = schemaElement.schemaLabel ?: return null
            val elementType = schemaElement.schemaElementType ?: return null

            return PrismaSchemaContext(kind, label, elementType, schemaElement)
        }
    }
}

val PsiElement.schemaKind: PrismaSchemaElementKind?
    get() {
        return when (schemaElementType) {
            in PRISMA_KEYWORDS -> PrismaSchemaElementKind.KEYWORD

            UNSUPPORTED_TYPE -> PrismaSchemaElementKind.PRIMITIVE_TYPE

            TYPE_REFERENCE ->
                if (this is PrismaSchemaFakeElement ||
                    PrismaConstants.PRIMITIVE_TYPES.contains((this as? PrismaReferencingElement)?.referenceText)
                ) {
                    PrismaSchemaElementKind.PRIMITIVE_TYPE
                } else {
                    null
                }

            else -> null
        }
    }

private fun PsiElement.adjustSchemaElement() = when {
    this is PrismaSchemaFakeElement -> this

    elementType == PrismaElementTypes.UNSUPPORTED -> parent

    elementType == PrismaElementTypes.IDENTIFIER ->
        PsiTreeUtil.skipParentsOfType(this, PsiWhiteSpace::class.java, PsiErrorElement::class.java)

    else -> this
}

val PsiElement.schemaLabel: String?
    get() {
        val psiRenderer = PrismaPsiRenderer()
        return when {
            isKeyword -> text
            this is PrismaTypeReference -> psiRenderer.build(this)
            this is PrismaBlockAttribute -> "@@${psiRenderer.build(this.path)}"
            this is PrismaFieldAttribute -> "@${psiRenderer.build(this.path)}"
            this is PrismaUnsupportedType -> PrismaConstants.Types.UNSUPPORTED
            this is PsiNamedElement -> this.name
            else -> null
        }
    }

private val PsiElement?.schemaElementType: IElementType?
    get() = when (this) {
        is PrismaSchemaFakeElement -> this.elementType
        else -> this.elementType
    }
