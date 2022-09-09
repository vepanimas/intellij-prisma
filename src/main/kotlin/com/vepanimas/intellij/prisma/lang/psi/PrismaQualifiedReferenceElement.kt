package com.vepanimas.intellij.prisma.lang.psi

import com.intellij.psi.PsiElement

interface PrismaQualifiedReferenceElement : PrismaReferenceElement {
    val qualifier: PsiElement?
}