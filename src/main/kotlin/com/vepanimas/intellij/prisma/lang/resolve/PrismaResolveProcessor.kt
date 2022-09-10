package com.vepanimas.intellij.prisma.lang.resolve

import com.intellij.psi.PsiElement
import com.intellij.psi.ResolveState
import com.intellij.psi.scope.PsiScopeProcessor
import com.intellij.util.SmartList
import com.vepanimas.intellij.prisma.lang.psi.PrismaNamedElement
import com.vepanimas.intellij.prisma.lang.psi.PrismaReferenceElement

class PrismaResolveProcessor(element: PrismaReferenceElement) : PsiScopeProcessor {
    private val variants = SmartList<PrismaNamedElement>()
    private val expectedName = element.referenceName

    override fun execute(element: PsiElement, state: ResolveState): Boolean {
        if (element !is PrismaNamedElement) {
            return true
        }
        if (element.name == expectedName) {
            variants.add(element)
            return false
        }
        return true
    }

    fun getResults(): List<PrismaNamedElement> {
        return variants
    }
}
