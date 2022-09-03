package com.vepanimas.intellij.prisma.ide.completion.schema

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.util.ProcessingContext
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaElement
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaElementKind
import com.vepanimas.intellij.prisma.lang.psi.PrismaPsiPatterns

object PrismaKeywordProvider : PrismaSchemaCompletionProvider() {
    override val kind: PrismaSchemaElementKind = PrismaSchemaElementKind.KEYWORD

    override val pattern = PrismaPsiPatterns.topKeyword

    override fun processLookupElement(
        builder: LookupElementBuilder,
        schemaElement: PrismaSchemaElement,
        parameters: CompletionParameters,
        context: ProcessingContext
    ): LookupElementBuilder {
        return builder.bold()
    }
}

