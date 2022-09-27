package com.vepanimas.intellij.prisma.ide.completion.schema

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.patterns.ElementPattern
import com.intellij.psi.PsiElement
import com.intellij.util.ProcessingContext
import com.vepanimas.intellij.prisma.ide.completion.collectExistingMemberNames
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaElement
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaKind
import com.vepanimas.intellij.prisma.lang.psi.PrismaPsiPatterns
import com.vepanimas.intellij.prisma.lang.psi.afterNewLine

object PrismaGeneratorFieldsProvider : PrismaSchemaCompletionProvider() {
    override val kind: PrismaSchemaKind = PrismaSchemaKind.GENERATOR_FIELD

    override val pattern: ElementPattern<out PsiElement> = PrismaPsiPatterns.generatorField.afterNewLine()

    override fun collectSchemaElements(
        parameters: CompletionParameters,
        context: ProcessingContext
    ): Collection<PrismaSchemaElement> {
        val memberNames = collectExistingMemberNames(parameters)
        return super.collectSchemaElements(parameters, context).filter { it.label !in memberNames }
    }
}

