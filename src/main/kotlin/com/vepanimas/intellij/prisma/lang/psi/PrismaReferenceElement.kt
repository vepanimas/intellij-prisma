package com.vepanimas.intellij.prisma.lang.psi

import com.intellij.psi.PsiElement

interface PrismaReferenceElement : PrismaElement {
    val referenceText: String?

    val identifier: PsiElement?
}