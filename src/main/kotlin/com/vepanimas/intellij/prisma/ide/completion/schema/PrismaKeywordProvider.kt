package com.vepanimas.intellij.prisma.ide.completion.schema

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.ElementPattern
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiErrorElement
import com.intellij.util.ProcessingContext
import com.vepanimas.intellij.prisma.ide.completion.PrismaSchemaCompletionProvider
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaElement
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaElementKind
import com.vepanimas.intellij.prisma.lang.psi.PrismaElementTypes
import com.vepanimas.intellij.prisma.lang.psi.PrismaFile

object PrismaKeywordProvider : PrismaSchemaCompletionProvider() {
    override val kind: PrismaSchemaElementKind
        get() = PrismaSchemaElementKind.KEYWORD

    override val pattern: ElementPattern<out PsiElement> =
        psiElement(PrismaElementTypes.IDENTIFIER).withParent(
            psiElement(PsiErrorElement::class.java).withParent(PrismaFile::class.java)
        )

    override fun processLookupElement(
        builder: LookupElementBuilder,
        schemaElement: PrismaSchemaElement,
        parameters: CompletionParameters,
        context: ProcessingContext
    ): LookupElementBuilder {
        return builder.bold()
    }
}

