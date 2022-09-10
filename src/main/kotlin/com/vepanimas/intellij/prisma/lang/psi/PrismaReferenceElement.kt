package com.vepanimas.intellij.prisma.lang.psi

import com.intellij.psi.PsiElement
import com.vepanimas.intellij.prisma.lang.resolve.PrismaReference

interface PrismaReferenceElement : PrismaElement {

    val referenceNameElement: PsiElement?

    val referenceName: String?
        get() = referenceNameElement?.text

    override fun getReference(): PrismaReference?

    fun resolve(): PsiElement?

}