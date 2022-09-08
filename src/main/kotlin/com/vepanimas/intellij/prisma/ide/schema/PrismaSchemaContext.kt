package com.vepanimas.intellij.prisma.ide.schema

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiErrorElement
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType
import com.intellij.psi.util.parentOfType
import com.vepanimas.intellij.prisma.lang.PrismaConstants
import com.vepanimas.intellij.prisma.lang.psi.*
import com.vepanimas.intellij.prisma.lang.psi.PrismaElementTypes.*
import com.vepanimas.intellij.prisma.lang.psi.presentation.PrismaPsiRenderer

sealed class PrismaSchemaContext(
    val label: String,
    val element: PsiElement,
) {
    companion object {
        fun forElement(element: PsiElement?): PrismaSchemaContext? {
            if (element == null) {
                return null
            }

            if (element is PrismaSchemaFakeElement) {
                return forElement(element)
            }

            val contextElement = findContextElement(element) ?: return null
            if (contextElement is PrismaNamedArgument) {
                val parentElement = contextElement.parentOfType<PrismaArgumentsOwner>() ?: return null
                val parentContext = forElement(parentElement) as? PrismaSchemaDeclarationContext ?: return null
                return PrismaSchemaParameterContext(contextElement.identifier.text, contextElement, parentContext)
            } else {
                val kind = getSchemaKind(contextElement) ?: return null
                val label = getSchemaLabel(contextElement) ?: return null
                return PrismaSchemaDeclarationContext(label, contextElement, kind)
            }
        }

        private fun forElement(element: PrismaSchemaFakeElement): PrismaSchemaContext? {
            return when (val schemaElement = element.schemaElement) {
                is PrismaSchemaDeclaration -> {
                    PrismaSchemaDeclarationContext(schemaElement.label, element, schemaElement.kind)
                }

                is PrismaSchemaParameter -> {
                    val parentContext =
                        forElement(element.parent) as? PrismaSchemaDeclarationContext ?: return null
                    return PrismaSchemaParameterContext(
                        schemaElement.label,
                        element,
                        parentContext
                    )
                }
            }
        }

        private fun findContextElement(element: PsiElement): PsiElement? {
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

        private fun getSchemaKind(element: PsiElement): PrismaSchemaKind? {
            return when (element.elementType) {
                in PRISMA_KEYWORDS -> PrismaSchemaKind.KEYWORD

                UNSUPPORTED_TYPE -> PrismaSchemaKind.PRIMITIVE_TYPE

                TYPE_REFERENCE ->
                    if (PrismaConstants.Types.PRIMITIVE.contains((element as? PrismaReferencingElement)?.referenceText)) {
                        PrismaSchemaKind.PRIMITIVE_TYPE
                    } else {
                        null
                    }

                KEY_VALUE -> {
                    val memberDeclaration = element as PrismaMemberDeclaration
                    val declaration = memberDeclaration.containingDeclaration
                    when (declaration?.elementType) {
                        DATASOURCE_DECLARATION -> PrismaSchemaKind.DATASOURCE_FIELD
                        GENERATOR_DECLARATION -> PrismaSchemaKind.GENERATOR_FIELD
                        else -> null
                    }
                }

                BLOCK_ATTRIBUTE -> PrismaSchemaKind.BLOCK_ATTRIBUTE
                FIELD_ATTRIBUTE -> PrismaSchemaKind.FIELD_ATTRIBUTE

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
                element is PrismaNamedArgument -> psiRenderer.build(element.identifier)
                element is PsiNamedElement -> element.name
                else -> null
            }
        }
    }
}

class PrismaSchemaDeclarationContext(
    label: String,
    element: PsiElement,
    val kind: PrismaSchemaKind,
) : PrismaSchemaContext(label, element)

class PrismaSchemaParameterContext(
    label: String,
    element: PsiElement,
    val parent: PrismaSchemaDeclarationContext,
) : PrismaSchemaContext(label, element)
