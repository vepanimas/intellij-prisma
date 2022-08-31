package com.vepanimas.intellij.prisma.lang.psi

import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType
import com.intellij.util.castSafelyTo
import com.vepanimas.intellij.prisma.ide.completion.PrismaKeywordFakeElement
import com.vepanimas.intellij.prisma.lang.parser.PrismaParserDefinition

fun PrismaPath.findTopmostPathParent(): PsiElement? {
    return PsiTreeUtil.skipParentsOfType(this, PrismaPath::class.java)
}

val PsiElement.isKeyword
    get() = elementType in PRISMA_KEYWORDS || this is PrismaKeywordFakeElement

val PsiElement?.hasTrailingComment: Boolean
    get() = skipWhitespacesForwardWithoutNewLines() is PsiComment

val PsiElement?.isTrailingComment: Boolean
    get() {
        if (this !is PsiComment) {
            return false
        }
        val prev = skipWhitespacesBackwardWithoutNewLines()
        return prev != null && prev !is PsiWhiteSpace
    }

fun PsiElement?.skipWhitespacesForwardWithoutNewLines() =
    PsiTreeUtil.skipMatching(this, { it.nextSibling }, { it is PsiWhiteSpace && !it.textContains('\n') })

fun PsiElement?.skipWhitespacesBackwardWithoutNewLines() =
    PsiTreeUtil.skipMatching(this, { it.prevSibling }, { it is PsiWhiteSpace && !it.textContains('\n') })

val PsiElement?.trailingDocComment: PsiComment?
    get() = skipWhitespacesForwardWithoutNewLines().castSafelyTo<PsiComment>()?.takeIf { it.isDocComment }

fun PsiElement.prevDocComment(includeTrailing: Boolean = false): PsiComment? {
    var prev = prevSibling
    if (prev is PsiWhiteSpace && StringUtil.countNewLines(prev.text) <= 1) prev = prev.prevSibling
    return prev?.takeIf { it.isDocComment && (includeTrailing || !it.isTrailingComment) }.castSafelyTo()
}

fun PsiElement.nextDocComment(includeTrailing: Boolean = false): PsiComment? {
    var next = nextSibling
    if (next is PsiWhiteSpace && StringUtil.countNewLines(next.text) <= 1) next = next.nextSibling
    return next?.takeIf { it.isDocComment && (includeTrailing || !it.isTrailingComment) }.castSafelyTo()
}

val PsiElement.isDocComment
    get() = elementType == PrismaParserDefinition.DOC_COMMENT

fun PsiElement.collectPrecedingDocComments(strict: Boolean = true): List<PsiComment> =
    generateSequence(if (strict) prevDocComment() else this) { it.prevDocComment() }
        .filterIsInstance<PsiComment>()
        .toList()
        .asReversed()