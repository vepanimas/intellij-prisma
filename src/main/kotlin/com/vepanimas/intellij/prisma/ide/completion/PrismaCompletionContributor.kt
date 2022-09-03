package com.vepanimas.intellij.prisma.ide.completion

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionType
import com.vepanimas.intellij.prisma.ide.completion.schema.PrismaDatasourceFieldsProvider
import com.vepanimas.intellij.prisma.ide.completion.schema.PrismaGeneratorFieldsProvider
import com.vepanimas.intellij.prisma.ide.completion.schema.PrismaKeywordProvider
import com.vepanimas.intellij.prisma.ide.completion.schema.PrismaPrimitiveTypeProvider

class PrismaCompletionContributor : CompletionContributor() {
    init {
        extend(CompletionType.BASIC, PrismaKeywordProvider)
        extend(CompletionType.BASIC, PrismaPrimitiveTypeProvider)
        extend(CompletionType.BASIC, PrismaDatasourceFieldsProvider)
        extend(CompletionType.BASIC, PrismaGeneratorFieldsProvider)
    }

    private fun extend(type: CompletionType, provider: PrismaCompletionProvider) {
        extend(type, provider.pattern, provider)
    }
}