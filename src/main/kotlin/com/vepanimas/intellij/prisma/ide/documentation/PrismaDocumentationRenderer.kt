package com.vepanimas.intellij.prisma.ide.documentation

import com.vepanimas.intellij.prisma.lang.psi.PrismaVirtualDocumentationComment

class PrismaDocumentationRenderer(private val element: PrismaVirtualDocumentationComment?) {
    fun render(): String? {
        if (element == null) {
            return null
        }

        return documentationMarkdownToHtml(element.content)
    }
}