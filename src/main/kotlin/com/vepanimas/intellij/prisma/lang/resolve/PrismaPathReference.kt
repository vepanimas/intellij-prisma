package com.vepanimas.intellij.prisma.lang.resolve

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.parentOfType
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaParameter
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaProvider
import com.vepanimas.intellij.prisma.lang.PrismaConstants
import com.vepanimas.intellij.prisma.lang.presentation.PrismaPsiRenderer
import com.vepanimas.intellij.prisma.lang.psi.*
import com.vepanimas.intellij.prisma.lang.types.PrismaResolvableType
import com.vepanimas.intellij.prisma.lang.types.unwrapType

class PrismaPathReference(
    element: PsiElement,
    range: TextRange,
    soft: Boolean = false,
) : PrismaReference(element, range, soft) {

    override fun processCandidates(
        processor: PrismaProcessor,
        state: ResolveState,
        place: PsiElement,
    ) {
        val context = findContext(element) ?: return
        val schemaElement = PrismaSchemaProvider.getSchema().match(context) ?: return
        if (schemaElement is PrismaSchemaParameter) {
            when (schemaElement.label) {
                PrismaConstants.ParameterNames.FIELDS -> {
                    resolveLocalField(context, processor, state, place)
                }

                PrismaConstants.ParameterNames.REFERENCES -> {
                    resolveTypeField(context, processor, state, place)
                }
            }
        }
    }

    private fun findContext(element: PsiElement): PsiElement? {
        var parent = PsiTreeUtil.skipParentsOfType(element, PrismaPathExpression::class.java)
        if (parent is PrismaFunctionCall) {
            parent = parent.parent
        }
        if (parent !is PrismaArrayExpression || parent.parent !is PrismaArgument) {
            return null
        }
        return parent.parentOfType<PrismaArgument>(withSelf = true)
    }

    private fun resolveTypeField(
        context: PsiElement,
        processor: PrismaProcessor,
        state: ResolveState,
        place: PsiElement
    ) {
        val typeOwner = context.parentOfType<PrismaTypeOwner>() ?: return
        val type = typeOwner.type.unwrapType()
        if (type is PrismaResolvableType) {
            type.resolveDeclaration()?.processDeclarations(processor, state, null, place)
        }
    }

    private fun resolveLocalField(
        context: PsiElement,
        processor: PrismaProcessor,
        state: ResolveState,
        place: PsiElement
    ) {
        context.parentOfType<PrismaDeclaration>()?.processDeclarations(processor, state, null, place)
    }

    override fun collectIgnoredNames(): Set<String> {
        val arrayExpression = element.parentOfType<PrismaArrayExpression>() ?: return emptySet()
        val psiRenderer = PrismaPsiRenderer()
        return arrayExpression.expressionList.mapNotNullTo(mutableSetOf()) {
            var expr = it
            if (expr is PrismaFunctionCall) {
                expr = expr.pathExpression
            }
            if (expr is PrismaPathExpression) {
                return@mapNotNullTo psiRenderer.build(expr.leftmostQualifier)
            }

            null
        }
    }

    companion object {
        fun create(element: PrismaReferenceElement): PrismaPathReference? {
            val identifier = element.referenceNameElement ?: return null
            val range = TextRange.from(identifier.startOffsetInParent, identifier.textLength)
            return PrismaPathReference(element, range)
        }
    }
}