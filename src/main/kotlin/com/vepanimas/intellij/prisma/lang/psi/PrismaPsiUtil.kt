package com.vepanimas.intellij.prisma.lang.psi

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType
import com.intellij.util.castSafelyTo
import com.vepanimas.intellij.prisma.ide.schema.types.PrismaDatasourceType

fun PrismaPathExpression.findTopmostPathParent(): PsiElement? {
    return PsiTreeUtil.skipParentsOfType(this, PrismaPathExpression::class.java)
}

val PsiElement.isKeyword: Boolean
    get() = elementType in PRISMA_KEYWORDS

fun PsiElement.resolveDatasourceType(): PrismaDatasourceType? =
    containingFile.castSafelyTo<PrismaFile>()?.datasourceType

fun PsiElement?.skipWhitespacesForwardWithoutNewLines() =
    PsiTreeUtil.skipMatching(this, { it.nextSibling }, { it is PsiWhiteSpace && !it.textContains('\n') })

fun PsiElement?.skipWhitespacesBackwardWithoutNewLines() =
    PsiTreeUtil.skipMatching(this, { it.prevSibling }, { it is PsiWhiteSpace && !it.textContains('\n') })