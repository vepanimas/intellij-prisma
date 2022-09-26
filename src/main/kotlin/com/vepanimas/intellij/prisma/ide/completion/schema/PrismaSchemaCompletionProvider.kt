package com.vepanimas.intellij.prisma.ide.completion.schema

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.util.ProcessingContext
import com.vepanimas.intellij.prisma.ide.completion.PrismaCompletionProvider
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaElement
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaFakeElement
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaKind
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaProvider
import com.vepanimas.intellij.prisma.lang.psi.PrismaFile

abstract class PrismaSchemaCompletionProvider : PrismaCompletionProvider() {
    abstract val kind: PrismaSchemaKind

    final override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        collectSchemaElements(parameters, context).forEach { schemaElement ->
            createLookupElement(schemaElement, parameters, context).let { builder ->
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

    protected open fun createLookupElement(
        schemaElement: PrismaSchemaElement,
        parameters: CompletionParameters,
        context: ProcessingContext,
    ): LookupElementBuilder {
        return createLookupElement(
            schemaElement.label,
            schemaElement,
            PrismaSchemaFakeElement.createForCompletion(parameters, schemaElement),
        )
    }
}