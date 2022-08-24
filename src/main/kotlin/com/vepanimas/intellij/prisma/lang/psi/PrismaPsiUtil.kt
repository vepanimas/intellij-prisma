package com.vepanimas.intellij.prisma.lang.psi

import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil

fun PrismaPath.findPathParent(): PsiElement? {
    return PsiTreeUtil.skipParentsOfType(this, PrismaPath::class.java)
}