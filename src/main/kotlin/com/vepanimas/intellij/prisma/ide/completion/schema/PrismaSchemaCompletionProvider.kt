package com.vepanimas.intellij.prisma.ide.completion.schema

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.util.ProcessingContext
import com.vepanimas.intellij.prisma.ide.completion.PrismaCompletionProvider
import com.vepanimas.intellij.prisma.ide.completion.withPrismaInsertHandler
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaElement
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaFakeElement
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaKind
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaProvider
import com.vepanimas.intellij.prisma.lang.psi.PrismaFile
import com.vepanimas.intellij.prisma.lang.psi.presentation.icon

abstract class PrismaSchemaCompletionProvider : PrismaCompletionProvider() {
    abstract val kind: PrismaSchemaKind

    final override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        collectSchemaElements(parameters, context).forEach { schemaElement ->
            val lookupElementBuilder = createLookupElement(schemaElement, parameters, context)
            lookupElementBuilder?.let { builder ->
                result.addElement(builder)
            }
        }
    }

    protected open fun collectSchemaElements(
        parameters: CompletionParameters,
        context: ProcessingContext
    ): Collection<PrismaSchemaElement> {
        val file = parameters.originalFile as? PrismaFile ?: return emptyList()
        val datasourceType = file.datasourceType

        return PrismaSchemaProvider.getSchema()
            .getElementsByKind(kind)
            .asSequence()
            .filter { it.isAvailableForDatasource(datasourceType) }
            .toList()
    }

    private fun createLookupElement(
        schemaElement: PrismaSchemaElement,
        parameters: CompletionParameters,
        context: ProcessingContext,
    ): LookupElementBuilder? {
        return LookupElementBuilder.create(schemaElement.label)
            .withPsiElement(PrismaSchemaFakeElement.createForCompletion(parameters, schemaElement))
            .withPrismaInsertHandler(schemaElement.insertHandler)
            .withIcon(schemaElement.icon)
            .let { processLookupElement(it, schemaElement, parameters, context) }
    }

    protected open fun processLookupElement(
        builder: LookupElementBuilder,
        schemaElement: PrismaSchemaElement,
        parameters: CompletionParameters,
        context: ProcessingContext,
    ): LookupElementBuilder? {
        return builder
    }
}