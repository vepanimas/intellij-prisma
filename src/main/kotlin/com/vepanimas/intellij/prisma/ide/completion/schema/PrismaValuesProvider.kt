package com.vepanimas.intellij.prisma.ide.completion.schema

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.openapi.util.text.StringUtil
import com.intellij.patterns.ElementPattern
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.TokenSet
import com.intellij.psi.util.elementType
import com.intellij.util.ProcessingContext
import com.vepanimas.intellij.prisma.ide.completion.PrismaCompletionProvider
import com.vepanimas.intellij.prisma.ide.schema.*
import com.vepanimas.intellij.prisma.lang.PrismaConstants.PrimitiveTypes
import com.vepanimas.intellij.prisma.lang.psi.PrismaElementTypes.*


object PrismaValuesProvider : PrismaCompletionProvider() {
    override val pattern: ElementPattern<out PsiElement> =
        psiElement()
            .withElementType(TokenSet.create(STRING_LITERAL, IDENTIFIER))
            .afterLeaf(psiElement(EQ))
            .inside(psiElement().withElementType(TokenSet.create(DATASOURCE_DECLARATION, GENERATOR_DECLARATION)))

    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        val parent = findVariantsProvider(parameters) ?: return
        val schemaElement = PrismaSchemaProvider.getSchema().match(parent) ?: return

        schemaElement.variants.expandRefs()
            .asSequence()
            .map {
                val label = computeLabel(it, parameters)
                createLookupElement(label, it, PrismaSchemaFakeElement.createForCompletion(parent, it))
            }
            .forEach { result.addElement(it) }
    }

    private fun computeLabel(schemaElement: PrismaSchemaElement, parameters: CompletionParameters): String {
        return when (schemaElement) {
            is PrismaSchemaVariant -> {
                val wrapInQuotes =
                    schemaElement.type == PrimitiveTypes.STRING && parameters.originalPosition?.elementType != STRING_LITERAL
                return if (wrapInQuotes) StringUtil.wrapWithDoubleQuote(schemaElement.label) else schemaElement.label
            }

            else -> schemaElement.label
        }
    }

    private fun findVariantsProvider(parameters: CompletionParameters): PsiElement? {
        return PrismaSchemaFakeElement.findSuitableParent(parameters)
    }
}