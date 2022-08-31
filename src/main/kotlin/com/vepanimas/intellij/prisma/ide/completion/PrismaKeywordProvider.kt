package com.vepanimas.intellij.prisma.ide.completion

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.project.Project
import com.intellij.patterns.ElementPattern
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.impl.FakePsiElement
import com.intellij.util.ProcessingContext
import com.vepanimas.intellij.prisma.ide.schema.PRISMA_KEYWORDS_SCHEMA
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaElementKind
import com.vepanimas.intellij.prisma.lang.psi.PrismaElementTypes
import com.vepanimas.intellij.prisma.lang.psi.PrismaFile

object PrismaKeywordProvider : PrismaCompletionProvider() {
    override val pattern: ElementPattern<out PsiElement> =
        psiElement(PrismaElementTypes.IDENTIFIER).withParent(PrismaFile::class.java)

    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        PRISMA_KEYWORDS_SCHEMA[PrismaSchemaElementKind.KEYWORD].values.forEach {
            result.addElement(
                LookupElementBuilder.create(it.label).bold()
                    .withPsiElement(PrismaKeywordFakeElement(it.label, parameters.originalFile))
            )
        }
    }
}

class PrismaKeywordFakeElement(private val keyword: String, private val file: PsiFile) : FakePsiElement() {
    override fun getParent(): PsiElement = file

    override fun getProject(): Project = file.project

    override fun getContainingFile(): PsiFile = file

    override fun getText(): String = keyword
}