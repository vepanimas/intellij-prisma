package com.vepanimas.intellij.prisma.ide.completion.schema

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.completion.InsertHandler
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.psi.util.elementType
import com.intellij.psi.util.prevLeaf
import com.intellij.util.ProcessingContext
import com.vepanimas.intellij.prisma.ide.completion.PrismaCompletionProvider
import com.vepanimas.intellij.prisma.ide.completion.PrismaInsertHandler
import com.vepanimas.intellij.prisma.ide.schema.PRISMA_SCHEMA_DEFINITION
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaElement
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaElementKind
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaFakeElement
import com.vepanimas.intellij.prisma.lang.psi.PrismaElementTypes

abstract class PrismaSchemaCompletionProvider : PrismaCompletionProvider() {
    abstract val kind: PrismaSchemaElementKind

    final override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        val prefix = computePrefix(result, parameters)
        val resultSet = result.withPrefixMatcher(result.prefixMatcher.cloneWithPrefix(prefix))

        collectSchemaElements().forEach { schemaElement ->
            val lookupElementBuilder = createLookupElement(schemaElement, parameters, context)
            lookupElementBuilder?.let { builder ->
                resultSet.addElement(builder)
            }
        }
    }

    private fun computePrefix(
        result: CompletionResultSet,
        parameters: CompletionParameters
    ): String {
        val prefix = result.prefixMatcher.prefix
        val prev = parameters.position.prevLeaf()
        val attrPrefix = when (prev?.elementType) {
            PrismaElementTypes.AT -> "@"
            PrismaElementTypes.ATAT -> "@@"
            else -> ""
        }
        return attrPrefix + prefix
    }

    protected open fun collectSchemaElements() = PRISMA_SCHEMA_DEFINITION.getElementsByKind(kind)

    protected fun createLookupElement(
        schemaElement: PrismaSchemaElement,
        parameters: CompletionParameters,
        context: ProcessingContext,
    ) = LookupElementBuilder.create(schemaElement.label)
        .withPsiElement(PrismaSchemaFakeElement.createForCompletion(parameters, schemaElement.label, kind))
        .withPrismaInsertHandler(schemaElement.insertHandler)
        .let { processLookupElement(it, schemaElement, parameters, context) }

    protected open fun processLookupElement(
        builder: LookupElementBuilder,
        schemaElement: PrismaSchemaElement,
        parameters: CompletionParameters,
        context: ProcessingContext,
    ): LookupElementBuilder? = builder

    private fun LookupElementBuilder.withPrismaInsertHandler(
        customInsertHandler: InsertHandler<LookupElement>? = null
    ): LookupElementBuilder = if (customInsertHandler != null) {
        withInsertHandler(customInsertHandler)
    } else {
        withInsertHandler(PrismaInsertHandler.DEFAULT_INSERT_HANDLER)
    }
}