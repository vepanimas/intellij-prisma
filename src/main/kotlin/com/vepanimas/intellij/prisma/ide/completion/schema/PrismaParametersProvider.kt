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
import com.vepanimas.intellij.prisma.lang.psi.PrismaArgumentsOwner
import com.vepanimas.intellij.prisma.lang.psi.PrismaNamedArgument
import com.vepanimas.intellij.prisma.lang.psi.PrismaPathExpression
import com.vepanimas.intellij.prisma.lang.psi.PrismaValueArgument
import com.vepanimas.intellij.prisma.lang.psi.presentation.icon

object PrismaParametersProvider : PrismaCompletionProvider() {
    override val pattern: ElementPattern<out PsiElement> =
        psiElement().withSuperParent(
            2,
            psiElement(PrismaPathExpression::class.java).withParent(PrismaValueArgument::class.java)
        )

    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        val position = parameters.originalPosition ?: parameters.position
        val argumentsOwner = position.parentOfType<PrismaArgumentsOwner>() ?: return
        val schemaDeclaration =
            PrismaSchemaProvider.getSchema().match(argumentsOwner) as? PrismaSchemaDeclaration ?: return
        val parent = PrismaSchemaFakeElement.createForCompletion(parameters, schemaDeclaration)
        val usedParams = argumentsOwner.getArgumentsList()?.argumentList
            ?.asSequence()
            ?.filterIsInstance<PrismaNamedArgument>()
            ?.map { it.identifier.text }
            ?.toSet()
            ?: emptySet()

        schemaDeclaration.params
            .filter { it.label !in usedParams }
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

