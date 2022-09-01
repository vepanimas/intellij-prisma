package com.vepanimas.intellij.prisma.ide.completion

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.ElementPattern
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiErrorElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.ProcessingContext
import com.vepanimas.intellij.prisma.ide.schema.PRISMA_SCHEMA
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaElementKind
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaFakeElement
import com.vepanimas.intellij.prisma.lang.psi.PrismaElementTypes
import com.vepanimas.intellij.prisma.lang.psi.PrismaFile

object PrismaKeywordProvider : PrismaCompletionProvider() {
    override val pattern: ElementPattern<out PsiElement> =
        psiElement(PrismaElementTypes.IDENTIFIER).withParent(
            psiElement(PsiErrorElement::class.java).withParent(PrismaFile::class.java)
        )

    override fun addCompletions(
        parameters: CompletionParameters,
        context: ProcessingContext,
        result: CompletionResultSet
    ) {
        val parent = PsiTreeUtil.skipParentsOfType(
            parameters.originalPosition,
            PsiWhiteSpace::class.java,
            PsiErrorElement::class.java
        ) ?: parameters.originalFile

        PRISMA_SCHEMA[PrismaSchemaElementKind.KEYWORD].values.forEach {
            result.addElement(
                LookupElementBuilder.create(it.label).bold()
                    .withPsiElement(PrismaSchemaFakeElement(it.label, it.elementType, parent))
                    .withPrismaInsertHandler(it.insertHandler)
            )
        }
    }
}

