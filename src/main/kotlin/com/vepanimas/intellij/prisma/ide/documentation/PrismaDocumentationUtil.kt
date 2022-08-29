package com.vepanimas.intellij.prisma.ide.documentation

fun stripDocCommentBlockPrefixes(text: String): String {
    return text.trimMargin("///").trimIndent()
}

fun stripDocCommentLinePrefix(text: String): String {
    return text.removePrefix("///").trim()
}