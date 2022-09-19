package com.vepanimas.intellij.prisma.ide.completion.schema

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.ElementPattern
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.psi.PsiElement
import com.intellij.psi.util.parentOfType
import com.intellij.util.ProcessingContext
import com.vepanimas.intellij.prisma.ide.completion.PrismaCompletionProvider
import com.vepanimas.intellij.prisma.ide.completion.withPrismaInsertHandler
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaDeclaration
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaFakeElement
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaProvider
import com.vepanimas.intellij.prisma.lang.psi.*
import com.vepanimas.intellij.prisma.lang.psi.presentation.icon

object PrismaParametersProvider : PrismaCompletionProvider() {
    override val pattern: ElementPattern<out PsiElement> =
        psiElement().withParent(
            psiElement(PrismaPathExpression::class.java).withParent(PrismaValueArgument::class.java)
        )

    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        val file = parameters.originalFile as? PrismaFile
        val position = parameters.originalPosition ?: parameters.position
        val datasource = file?.datasourceType
        var argumentsOwner = position.parentOfType<PrismaArgumentsOwner>() ?: return
        val isFieldArgument =
            argumentsOwner is PrismaFunctionCall && argumentsOwner.parent is PrismaArrayExpression
        if (isFieldArgument) {
            argumentsOwner = argumentsOwner.parentOfType() ?: return
        }
        val schemaDeclaration =
            PrismaSchemaProvider.getSchema().match(argumentsOwner) as? PrismaSchemaDeclaration ?: return
        val parent = PrismaSchemaFakeElement.createForCompletion(parameters, schemaDeclaration)
        val usedParams = argumentsOwner.getArgumentsList()?.argumentList
            ?.asSequence()
            ?.filterIsInstance<PrismaNamedArgument>()
            ?.map { it.referenceName }
            ?.toSet()
            ?: emptySet()

        schemaDeclaration.params
            .filter {
                it.label !in usedParams &&
                        it.isAvailableForDatasource(datasource) &&
                        it.isOnFieldLevel == isFieldArgument
            }
            .forEach {
                val element = LookupElementBuilder.create(it.label)
                    .withPsiElement(PrismaSchemaFakeElement.createForCompletion(parent, it))
                    .withTypeText(it.type)
                    .withPrismaInsertHandler(it.insertHandler)
                    .withIcon(it.icon)

                result.addElement(element)
            }
    }
}

