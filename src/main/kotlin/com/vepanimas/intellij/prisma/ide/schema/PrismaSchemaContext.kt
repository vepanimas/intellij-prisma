package com.vepanimas.intellij.prisma.ide.schema

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiErrorElement
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.TokenType.ERROR_ELEMENT
import com.intellij.psi.TokenType.WHITE_SPACE
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType
import com.intellij.psi.util.parentOfType
import com.intellij.psi.util.parentOfTypes
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
            if (element == null || element is PrismaSchemaFakeElement) {
                return null
            }

            val contextElement = adjustContextElement(element) ?: return null
            return when (contextElement) {
                is PrismaNamedArgument -> createParameterContext(contextElement)
                is PrismaLiteralExpression -> createValueContext(contextElement)
                else -> createDeclarationContext(contextElement)
            }
        }

        private fun createParameterContext(element: PrismaNamedArgument): PrismaSchemaParameterContext? {
            var parentElement = element.parentOfType<PrismaArgumentsOwner>() ?: return null
            if (parentElement is PrismaFunctionCall && parentElement.parent is PrismaArrayExpression) {
                parentElement = parentElement.parentOfType() ?: return null
            }
            val parentContext = forElement(parentElement) as? PrismaSchemaDeclarationContext ?: return null
            val label = element.referenceName ?: return null
            return PrismaSchemaParameterContext(label, element, parentContext)
        }

        private fun createValueContext(element: PrismaLiteralExpression): PrismaSchemaValueContext? {
            val parent =
                element.parentOfTypes(PrismaArgument::class, PrismaMemberDeclaration::class) ?: return null
            val parentContext = forElement(parent) ?: return null
            val label = element.value?.toString() ?: return null
            return PrismaSchemaValueContext(label, element, parentContext)
        }

        private fun createDeclarationContext(element: PsiElement): PrismaSchemaDeclarationContext? {
            val kind = getSchemaKind(element) ?: return null
            val label = getSchemaLabel(element) ?: return null
            return PrismaSchemaDeclarationContext(label, element, kind)
        }

        fun adjustContextElement(element: PsiElement): PsiElement? {
            return when (element.elementType) {
                IDENTIFIER -> findIdentifierParent(element)

                AT, ATAT, UNSUPPORTED -> element.parent

                in PRISMA_LITERALS -> element.parent

                WHITE_SPACE, ERROR_ELEMENT ->
                    PsiTreeUtil.skipParentsOfType(element, PsiWhiteSpace::class.java, PsiErrorElement::class.java)

                else -> element
            }
        }

        private fun findIdentifierParent(element: PsiElement?): PsiElement? {
            val parent =
                PsiTreeUtil.skipParentsOfType(element, PsiWhiteSpace::class.java, PsiErrorElement::class.java)

            if (parent is PrismaPathExpression) {
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
                    if (PrismaConstants.PrimitiveTypes.ALL.contains((element as? PrismaTypeReference)?.referenceName)) {
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

        fun getSchemaLabel(element: PsiElement): String? {
            val psiRenderer = PrismaPsiRenderer()
            return when {
                element.isKeyword -> element.text
                element is PrismaTypeReference -> psiRenderer.build(element)
                element is PrismaBlockAttribute -> "@@${psiRenderer.build(element.pathExpression)}"
                element is PrismaFieldAttribute -> "@${psiRenderer.build(element.pathExpression)}"
                element is PrismaUnsupportedType -> PrismaConstants.PrimitiveTypes.UNSUPPORTED
                element is PrismaKeyValue -> psiRenderer.build(element.identifier)
                element is PrismaReferenceElement -> element.referenceName
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

class PrismaSchemaValueContext(
    label: String,
    element: PsiElement,
    val parent: PrismaSchemaContext,
) : PrismaSchemaContext(label, element)
