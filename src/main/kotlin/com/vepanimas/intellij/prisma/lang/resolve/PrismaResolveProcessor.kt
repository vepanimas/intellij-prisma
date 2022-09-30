package com.vepanimas.intellij.prisma.lang.resolve

import com.intellij.psi.PsiElement
import com.vepanimas.intellij.prisma.lang.psi.PrismaNamedElement

class PrismaResolveProcessor(val name: String, val place: PsiElement) : PrismaProcessor() {

    override val multiple = false

    override fun accepts(element: PsiElement): Boolean {
        return (element as? PrismaNamedElement)?.name == name
    }
}
