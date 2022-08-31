package com.vepanimas.intellij.prisma.ide.completion

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionType

class PrismaCompletionContributor : CompletionContributor() {
    init {
        extend(CompletionType.BASIC, PrismaKeywordProvider)
    }

    private fun extend(type: CompletionType, provider: PrismaCompletionProvider) {
        extend(type, provider.pattern, provider)
    }
}