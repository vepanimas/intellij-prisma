package com.vepanimas.intellij.prisma.ide.completion.schema

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.util.text.StringUtil
import com.intellij.patterns.ElementPattern
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.TokenSet
import com.intellij.psi.util.elementType
import com.intellij.util.ProcessingContext
import com.vepanimas.intellij.prisma.ide.completion.PrismaCompletionProvider
import com.vepanimas.intellij.prisma.ide.completion.withPrismaInsertHandler
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaFakeElement
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaProvider
import com.vepanimas.intellij.prisma.lang.PrismaConstants.PrimitiveTypes
import com.vepanimas.intellij.prisma.lang.psi.PrismaElementTypes.*
import com.vepanimas.intellij.prisma.lang.psi.presentation.icon


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

        schemaElement.variants.forEach {
            val wrapInQuotes = it.type == PrimitiveTypes.STRING &&
                    parameters.originalPosition?.elementType != STRING_LITERAL
            val label = if (wrapInQuotes) StringUtil.wrapWithDoubleQuote(it.label) else it.label

            val element = LookupElementBuilder.create(label)
                .withPsiElement(PrismaSchemaFakeElement.createForCompletion(parent, it))
                .withPrismaInsertHandler(it.insertHandler)
                .withIcon(it.icon)

            result.addElement(element)
        }
    }

    private fun findVariantsProvider(parameters: CompletionParameters): PsiElement? {
        return PrismaSchemaFakeElement.findSuitableParent(parameters)
    }
}