package com.vepanimas.intellij.prisma.lang.resolve

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElementResolveResult
import com.intellij.psi.PsiPolyVariantReferenceBase
import com.intellij.psi.ResolveResult
import com.intellij.psi.ResolveState
import com.intellij.psi.impl.source.resolve.ResolveCache
import com.vepanimas.intellij.prisma.lang.psi.PrismaFile
import com.vepanimas.intellij.prisma.lang.psi.PrismaReferenceElement
import com.vepanimas.intellij.prisma.lang.psi.PrismaTypeReference

class PrismaReference(
    element: PrismaReferenceElement,
    range: TextRange,
    soft: Boolean = false,
) : PsiPolyVariantReferenceBase<PrismaReferenceElement>(element, range, soft) {

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        return ResolveCache.getInstance(element.project).resolveWithCaching(this, RESOLVER, false, false)
    }

    private fun resolveInner(): Array<ResolveResult> {
        val file = element.containingFile as? PrismaFile ?: return ResolveResult.EMPTY_ARRAY
        val processor = createResolveProcessor(element)
        val state = ResolveState.initial()
        processCandidates(processor, state, file)
        return PsiElementResolveResult.createResults(processor.getResults())
    }

    private fun processCandidates(processor: PrismaResolveProcessor, state: ResolveState, file: PrismaFile): Boolean {
        return when (element) {
            is PrismaTypeReference -> processTypeDeclarations(processor, state, file)
            else -> true
        }
    }

    private fun processTypeDeclarations(
        processor: PrismaResolveProcessor,
        state: ResolveState,
        file: PrismaFile
    ): Boolean {
        for (declaration in file.declarations) {
            if (!processor.execute(declaration, state)) return false
        }
        return true
    }

    private fun createResolveProcessor(element: PrismaReferenceElement): PrismaResolveProcessor {
        return PrismaResolveProcessor(element)
    }

    companion object {
        fun create(element: PrismaReferenceElement): PrismaReference? {
            val identifier = element.referenceNameElement ?: return null
            val range = TextRange.from(identifier.startOffsetInParent, identifier.textLength)
            return PrismaReference(element, range)
        }

        private val RESOLVER = ResolveCache.PolyVariantResolver<PrismaReference> { ref, _ -> ref.resolveInner() }
    }
}