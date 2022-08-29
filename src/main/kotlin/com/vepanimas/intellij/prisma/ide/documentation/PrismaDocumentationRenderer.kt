package com.vepanimas.intellij.prisma.ide.documentation

import com.intellij.openapi.util.text.HtmlBuilder
import com.intellij.openapi.util.text.HtmlChunk
import com.vepanimas.intellij.prisma.lang.psi.PrismaVirtualDocumentationComment

class PrismaDocumentationRenderer(private val element: PrismaVirtualDocumentationComment) {
    fun render(): String {
        val lines = element
            .comments
            .asSequence()
            .map { stripDocCommentLinePrefix(it.text) }
            .map { HtmlChunk.span().addText(it) }
            .toList()

        return HtmlBuilder().appendWithSeparators(HtmlChunk.br(), lines).toString()
    }
}