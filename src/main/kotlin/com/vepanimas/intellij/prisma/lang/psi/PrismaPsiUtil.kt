package com.vepanimas.intellij.prisma.lang.psi

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType

fun PrismaPath.findTopmostPathParent(): PsiElement? {
    return PsiTreeUtil.skipParentsOfType(this, PrismaPath::class.java)
}

val PsiElement.isKeyword: Boolean
    get() = elementType in PRISMA_KEYWORDS

fun PsiElement?.skipWhitespacesForwardWithoutNewLines() =
    PsiTreeUtil.skipMatching(this, { it.nextSibling }, { it is PsiWhiteSpace && !it.textContains('\n') })

fun PsiElement?.skipWhitespacesBackwardWithoutNewLines() =
    PsiTreeUtil.skipMatching(this, { it.prevSibling }, { it is PsiWhiteSpace && !it.textContains('\n') })