package com.vepanimas.intellij.prisma.ide.schema

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiErrorElement
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType
import com.vepanimas.intellij.prisma.lang.PrismaConstants
import com.vepanimas.intellij.prisma.lang.psi.*
import com.vepanimas.intellij.prisma.lang.psi.PrismaElementTypes.*
import com.vepanimas.intellij.prisma.lang.psi.presentation.PrismaPsiRenderer

class PrismaSchemaContext(
    val kind: PrismaSchemaElementKind,
    val label: String,
    val element: PsiElement,
) {
    companion object {
        fun forElement(element: PsiElement?): PrismaSchemaContext? {
            if (element == null) {
                return null
            }

            if (element is PrismaSchemaFakeElement) {
                return PrismaSchemaContext(element.kind, element.label, element)
            }

            val schemaElement = element.adjustSchemaElement() ?: return null
            val kind = schemaElement.getSchemaKind() ?: return null
            val label = schemaElement.getSchemaLabel() ?: return null

            return PrismaSchemaContext(kind, label, schemaElement)
        }
    }
}

private fun PsiElement.adjustSchemaElement() = when (elementType) {
    UNSUPPORTED -> parent
    IDENTIFIER -> PsiTreeUtil.skipParentsOfType(this, PsiWhiteSpace::class.java, PsiErrorElement::class.java)
    else -> this
}

private fun PsiElement.getSchemaKind(): PrismaSchemaElementKind? {
    return when (elementType) {
        in PRISMA_KEYWORDS -> PrismaSchemaElementKind.KEYWORD

        UNSUPPORTED_TYPE -> PrismaSchemaElementKind.PRIMITIVE_TYPE

        TYPE_REFERENCE ->
            if (PrismaConstants.Types.PRIMITIVE.contains((this as? PrismaReferencingElement)?.referenceText)) {
                PrismaSchemaElementKind.PRIMITIVE_TYPE
            } else {
                null
            }

        KEY_VALUE -> {
            val memberDeclaration = this as PrismaMemberDeclaration
            val declaration = memberDeclaration.containingDeclaration
            when (declaration?.elementType) {
                DATASOURCE_DECLARATION -> PrismaSchemaElementKind.DATASOURCE_FIELD
                GENERATOR_DECLARATION -> PrismaSchemaElementKind.GENERATOR_FIELD
                else -> null
            }
        }

        else -> null
    }
}

private fun PsiElement.getSchemaLabel(): String? {
    val psiRenderer = PrismaPsiRenderer()
    return when {
        isKeyword -> text
        this is PrismaTypeReference -> psiRenderer.build(this)
        this is PrismaBlockAttribute -> "@@${psiRenderer.build(this.path)}"
        this is PrismaFieldAttribute -> "@${psiRenderer.build(this.path)}"
        this is PrismaUnsupportedType -> PrismaConstants.Types.UNSUPPORTED
        this is PrismaKeyValue -> psiRenderer.build(this.identifier)
        this is PsiNamedElement -> this.name
        else -> null
    }
}
