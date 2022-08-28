package com.vepanimas.intellij.prisma.lang.psi

import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.util.PsiTreeUtil

fun PrismaPath.findPathParent(): PsiElement? {
    return PsiTreeUtil.skipParentsOfType(this, PrismaPath::class.java)
}

val PsiElement?.hasTrailingComment: Boolean
    get() = skipWhitespacesForwardWithoutNewLines() is PsiComment

fun PsiElement?.skipWhitespacesForwardWithoutNewLines() =
    PsiTreeUtil.skipMatching(this, { it.nextSibling }, { it is PsiWhiteSpace && !it.textContains('\n') })

fun PsiElement?.skipWhitespacesBackwardWithoutNewLines() =
    PsiTreeUtil.skipMatching(this, { it.prevSibling }, { it is PsiWhiteSpace && !it.textContains('\n') })