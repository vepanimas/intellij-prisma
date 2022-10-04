package com.vepanimas.intellij.prisma.lang.resolve

import com.intellij.psi.PsiElement
import com.vepanimas.intellij.prisma.lang.psi.PrismaNamedElement

class PrismaResolveProcessor(val name: String, val place: PsiElement) : PrismaProcessor() {

    init {
        multiple = false
    }

    override fun accepts(element: PsiElement): Boolean {
        return super.accepts(element) && (element as? PrismaNamedElement)?.name == name
    }
}
