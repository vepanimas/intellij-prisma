package com.vepanimas.intellij.prisma.ide.completion

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.patterns.ElementPattern
import com.intellij.psi.PsiElement

abstract class PrismaCompletionProvider : CompletionProvider<CompletionParameters>() {
    abstract val pattern: ElementPattern<out PsiElement>
}