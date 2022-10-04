package com.vepanimas.intellij.prisma.lang.resolve

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.vepanimas.intellij.prisma.lang.psi.PrismaReferenceElement

class PrismaTypeNameReference(
    element: PsiElement,
    range: TextRange,
    soft: Boolean = false,
) : PrismaReference(element, range, soft) {

    override fun processCandidates(
        processor: PrismaProcessor,
        state: ResolveState,
        place: PsiElement,
    ) {
        processFileDeclarations(processor, state, place)
    }

    companion object {
        fun create(element: PrismaReferenceElement): PrismaTypeNameReference? {
            val identifier = element.referenceNameElement ?: return null
            val range = TextRange.from(identifier.startOffsetInParent, identifier.textLength)
            return PrismaTypeNameReference(element, range)
        }
    }
}