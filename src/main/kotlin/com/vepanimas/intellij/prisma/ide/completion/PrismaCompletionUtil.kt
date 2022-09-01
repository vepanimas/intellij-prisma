package com.vepanimas.intellij.prisma.ide.completion

import com.intellij.codeInsight.completion.AddSpaceInsertHandler
import com.intellij.codeInsight.completion.InsertHandler
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.vepanimas.intellij.prisma.lang.psi.PRISMA_KEYWORDS
import com.vepanimas.intellij.prisma.lang.psi.prismaElementType

fun LookupElementBuilder.withPrismaInsertHandler(customInsertHandler: InsertHandler<LookupElement>? = null): LookupElementBuilder {
    return if (customInsertHandler != null) {
        withInsertHandler(customInsertHandler)
    } else {
        withInsertHandler(DEFAULT_INSERT_HANDLER)
    }
}

private val DEFAULT_INSERT_HANDLER = InsertHandler<LookupElement> { context, item ->
    val elementType = item.psiElement.prismaElementType

    if (elementType in PRISMA_KEYWORDS) {
        AddSpaceInsertHandler.INSTANCE.handleInsert(context, item)
        return@InsertHandler
    }
}