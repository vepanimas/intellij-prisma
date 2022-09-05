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

            val schemaElement = adjustSchemaElement(element) ?: return null
            val kind = getSchemaKind(schemaElement) ?: return null
            val label = getSchemaLabel(schemaElement) ?: return null

            return PrismaSchemaContext(kind, label, schemaElement)
        }

        private fun adjustSchemaElement(element: PsiElement): PsiElement? {
            return when (element.elementType) {
                UNSUPPORTED -> element.parent
                IDENTIFIER -> findIdentifierParent(element)
                else -> element
            }
        }

        private fun findIdentifierParent(element: PsiElement): PsiElement? {
            val parent =
                PsiTreeUtil.skipParentsOfType(element, PsiWhiteSpace::class.java, PsiErrorElement::class.java)

            if (parent is PrismaPath) {
                val pathParent = parent.findTopmostPathParent()
                if (pathParent != null) {
                    return pathParent
                }
            }

            return parent
        }

        private fun getSchemaKind(element: PsiElement): PrismaSchemaElementKind? {
            return when (element.elementType) {
                in PRISMA_KEYWORDS -> PrismaSchemaElementKind.KEYWORD

                UNSUPPORTED_TYPE -> PrismaSchemaElementKind.PRIMITIVE_TYPE

                TYPE_REFERENCE ->
                    if (PrismaConstants.Types.PRIMITIVE.contains((element as? PrismaReferencingElement)?.referenceText)) {
                        PrismaSchemaElementKind.PRIMITIVE_TYPE
                    } else {
                        null
                    }

                KEY_VALUE -> {
                    val memberDeclaration = element as PrismaMemberDeclaration
                    val declaration = memberDeclaration.containingDeclaration
                    when (declaration?.elementType) {
                        DATASOURCE_DECLARATION -> PrismaSchemaElementKind.DATASOURCE_FIELD
                        GENERATOR_DECLARATION -> PrismaSchemaElementKind.GENERATOR_FIELD
                        else -> null
                    }
                }

                BLOCK_ATTRIBUTE -> PrismaSchemaElementKind.BLOCK_ATTRIBUTE
                FIELD_ATTRIBUTE -> PrismaSchemaElementKind.FIELD_ATTRIBUTE

                else -> null
            }
        }

        private fun getSchemaLabel(element: PsiElement): String? {
            val psiRenderer = PrismaPsiRenderer()
            return when {
                element.isKeyword -> element.text
                element is PrismaTypeReference -> psiRenderer.build(element)
                element is PrismaBlockAttribute -> "@@${psiRenderer.build(element.path)}"
                element is PrismaFieldAttribute -> "@${psiRenderer.build(element.path)}"
                element is PrismaUnsupportedType -> PrismaConstants.Types.UNSUPPORTED
                element is PrismaKeyValue -> psiRenderer.build(element.identifier)
                element is PsiNamedElement -> element.name
                else -> null
            }
        }
    }
}
