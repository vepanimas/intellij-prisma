package com.vepanimas.intellij.prisma.ide.completion.schema

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.completion.InsertHandler
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.util.ProcessingContext
import com.vepanimas.intellij.prisma.ide.completion.PrismaCompletionProvider
import com.vepanimas.intellij.prisma.ide.completion.PrismaInsertHandler
import com.vepanimas.intellij.prisma.ide.schema.PRISMA_SCHEMA_DEFINITION
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaElement
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaElementKind
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaFakeElement

abstract class PrismaSchemaCompletionProvider : PrismaCompletionProvider() {
    abstract val kind: PrismaSchemaElementKind

    final override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        PRISMA_SCHEMA_DEFINITION.getElementsByKind(kind).forEach {
            val lookupElementBuilder = LookupElementBuilder.create(it.label).bold()
                .withPsiElement(PrismaSchemaFakeElement.createForCompletion(parameters, it.label, kind))
                .withPrismaInsertHandler(it.insertHandler)

            processLookupElement(lookupElementBuilder, it, parameters, context)?.let { builder ->
                result.addElement(builder)
            }
        }
    }

    protected open fun processLookupElement(
        builder: LookupElementBuilder,
        schemaElement: PrismaSchemaElement,
        parameters: CompletionParameters,
        context: ProcessingContext,
    ): LookupElementBuilder? {
        return builder
    }

    private fun LookupElementBuilder.withPrismaInsertHandler(customInsertHandler: InsertHandler<LookupElement>? = null): LookupElementBuilder {
        return if (customInsertHandler != null) {
            withInsertHandler(customInsertHandler)
        } else {
            withInsertHandler(PrismaInsertHandler.DEFAULT_INSERT_HANDLER)
        }
    }
}