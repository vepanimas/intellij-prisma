package com.vepanimas.intellij.prisma.ide.completion

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionType
import com.vepanimas.intellij.prisma.ide.completion.schema.*

class PrismaCompletionContributor : CompletionContributor() {
    init {
        extend(CompletionType.BASIC, PrismaKeywordProvider)
        extend(CompletionType.BASIC, PrismaPrimitiveTypeProvider)
        extend(CompletionType.BASIC, PrismaDatasourceFieldsProvider)
        extend(CompletionType.BASIC, PrismaGeneratorFieldsProvider)
        extend(CompletionType.BASIC, PrismaBlockAttributesProvider)
    }

    private fun extend(type: CompletionType, provider: PrismaCompletionProvider) {
        extend(type, provider.pattern, provider)
    }
}