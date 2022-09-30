package com.vepanimas.intellij.prisma.lang.resolve

import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import com.intellij.psi.impl.source.resolve.ResolveCache
import com.vepanimas.intellij.prisma.lang.psi.PrismaReferenceElement

abstract class PrismaReference(
    element: PsiElement,
    range: TextRange,
    soft: Boolean = false,
) : PsiPolyVariantReferenceBase<PsiElement>(element, range, soft) {

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        return ResolveCache.getInstance(element.project).resolveWithCaching(this, RESOLVER, false, false)
    }

    private fun resolveInner(): Array<ResolveResult> {
        val processor = createResolveProcessor(element) ?: return ResolveResult.EMPTY_ARRAY
        val state = ResolveState.initial()
        processCandidates(processor, state, element)
        return PsiElementResolveResult.createResults(processor.getResults())
    }

    protected open fun createResolveProcessor(element: PsiElement): PrismaResolveProcessor? {
        val name = (element as? PrismaReferenceElement)?.referenceName ?: return null
        return PrismaResolveProcessor(name, element)
    }

    protected open fun createCompletionProcessor(element: PsiElement): PrismaProcessor {
        return PrismaProcessor()
    }

    override fun getVariants(): Array<Any> {
        val processor = createCompletionProcessor(element)
        processCandidates(processor, ResolveState.initial(), element)
        val results = processor.getResults()
        return results.map { LookupElementBuilder.createWithIcon(it) }.toTypedArray()
    }

    protected abstract fun processCandidates(
        processor: PrismaProcessor,
        state: ResolveState,
        place: PsiElement,
    )

    protected fun processFileDeclarations(
        processor: PrismaProcessor,
        state: ResolveState,
        place: PsiElement,
    ): Boolean {
        val file = place.containingFile
        if (!file.processDeclarations(processor, state, null, place)) {
            return false
        }
        return true
    }

    companion object {
        private val RESOLVER = ResolveCache.PolyVariantResolver<PrismaReference> { ref, _ -> ref.resolveInner() }
    }
}