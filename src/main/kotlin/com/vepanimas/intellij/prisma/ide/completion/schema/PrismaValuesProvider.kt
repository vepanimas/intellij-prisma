package com.vepanimas.intellij.prisma.ide.completion.schema

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.openapi.util.text.StringUtil
import com.intellij.patterns.ElementPattern
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.patterns.StandardPatterns
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.TokenSet
import com.intellij.psi.util.elementType
import com.intellij.psi.util.parentOfTypes
import com.intellij.util.ProcessingContext
import com.vepanimas.intellij.prisma.ide.completion.PrismaCompletionProvider
import com.vepanimas.intellij.prisma.ide.schema.*
import com.vepanimas.intellij.prisma.lang.PrismaConstants.PrimitiveTypes
import com.vepanimas.intellij.prisma.lang.psi.PrismaArgument
import com.vepanimas.intellij.prisma.lang.psi.PrismaElementTypes.*
import com.vepanimas.intellij.prisma.lang.psi.PrismaMemberDeclaration


object PrismaValuesProvider : PrismaCompletionProvider() {
    private val keyValue = psiElement()
        .withElementType(TokenSet.create(STRING_LITERAL, IDENTIFIER))
        .afterLeaf(psiElement(EQ))
        .inside(psiElement().withElementType(TokenSet.create(DATASOURCE_DECLARATION, GENERATOR_DECLARATION)))

    private val paramValue = psiElement()
        .withSuperParent(2, psiElement().withElementType(TokenSet.create(NAMED_ARGUMENT, VALUE_ARGUMENT)))

    override val pattern: ElementPattern<out PsiElement> =
        StandardPatterns.or(keyValue, paramValue)

    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        val parent =
            parameters.position.parentOfTypes(PrismaArgument::class, PrismaMemberDeclaration::class) ?: return
        val schemaElement = PrismaSchemaProvider.getSchema().match(parent) ?: return

        schemaElement.variants.expandRefs()
            .asSequence()
            .map {
                val label = computeLabel(it, parameters)
                createLookupElement(label, it, PrismaSchemaFakeElement.createForCompletion(parameters, it))
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
}