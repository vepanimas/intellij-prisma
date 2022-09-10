package com.vepanimas.intellij.prisma.lang.resolve

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiPolyVariantReferenceBase
import com.intellij.psi.ResolveResult
import com.vepanimas.intellij.prisma.lang.psi.PrismaReferenceElement

class PrismaReference(
    element: PsiElement,
    range: TextRange,
    soft: Boolean = false,
) : PsiPolyVariantReferenceBase<PsiElement>(element, range, soft) {

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        return ResolveResult.EMPTY_ARRAY
    }

    companion object {
        fun create(element: PrismaReferenceElement): PrismaReference? {
            val identifier = element.referenceNameElement ?: return null
            val range = TextRange.from(identifier.startOffsetInParent, identifier.textLength)
            return PrismaReference(element, range)
        }
    }
}