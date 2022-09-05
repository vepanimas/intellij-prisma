package com.vepanimas.intellij.prisma.lang.psi

import com.intellij.patterns.ObjectPattern
import com.intellij.patterns.PatternCondition
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.patterns.PsiElementPattern
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiErrorElement
import com.intellij.psi.TokenType
import com.intellij.psi.util.elementType
import com.intellij.util.ProcessingContext

object PrismaPsiPatterns {
    val topKeyword: PsiElementPattern.Capture<PsiElement> =
        psiElement(PrismaElementTypes.IDENTIFIER).withParent(
            psiElement(PsiErrorElement::class.java).withParent(PrismaFile::class.java).afterNewLine()
        )

    val typeReference: PsiElementPattern.Capture<PsiElement> =
        psiElement(PrismaElementTypes.IDENTIFIER).withParent(psiElement(PrismaTypeReference::class.java))

    val datasourceField: PsiElementPattern.Capture<PsiElement> =
        psiElement()
            .withParent(PrismaKeyValue::class.java)
            .withSuperParent(3, PrismaDatasourceDeclaration::class.java)

    val generatorField: PsiElementPattern.Capture<PsiElement> =
        psiElement()
            .withParent(PrismaKeyValue::class.java)
            .withSuperParent(3, PrismaGeneratorDeclaration::class.java)
}

fun <T : PsiElement, Self : ObjectPattern<T, Self>> ObjectPattern<T, Self>.afterNewLine(): Self =
    with("afterNewLine") { element ->
        val prev = element.skipWhitespacesBackwardWithoutNewLines() ?: return@with true
        prev.elementType == TokenType.WHITE_SPACE && prev.textContains('\n')
    }

fun <T : Any, Self : ObjectPattern<T, Self>> ObjectPattern<T, Self>.with(name: String, cond: (T) -> Boolean): Self =
    with(object : PatternCondition<T>(name) {
        override fun accepts(t: T, context: ProcessingContext?): Boolean = cond(t)
    })

fun <T : Any, Self : ObjectPattern<T, Self>> ObjectPattern<T, Self>.with(
    name: String,
    cond: (T, ProcessingContext?) -> Boolean
): Self = with(object : PatternCondition<T>(name) {
    override fun accepts(t: T, context: ProcessingContext?): Boolean = cond(t, context)
})