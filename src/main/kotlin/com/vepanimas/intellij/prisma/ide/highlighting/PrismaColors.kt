package com.vepanimas.intellij.prisma.ide.highlighting

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey

object PrismaColors {
    val DOC_COMMENT = createTextAttributesKey("PRISMA_DOC_COMMENT", DefaultLanguageHighlighterColors.DOC_COMMENT)
    val LINE_COMMENT = createTextAttributesKey("PRISMA_LINE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
    val STRING_LITERAL = createTextAttributesKey("PRISMA_STRING_LITERAL", DefaultLanguageHighlighterColors.STRING)
    val KEYWORD = createTextAttributesKey("PRISMA_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD)
    val IDENTIFIER = createTextAttributesKey("PRISMA_IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER)
    val NUMBER = createTextAttributesKey("PRISMA_NUMBER", DefaultLanguageHighlighterColors.NUMBER)
    val BRACKETS = createTextAttributesKey("PRISMA_BRACKETS", DefaultLanguageHighlighterColors.BRACKETS)
    val PARENTHESES = createTextAttributesKey("PRISMA_PARENTHESES", DefaultLanguageHighlighterColors.PARENTHESES)
    val BRACES = createTextAttributesKey("PRISMA_BRACES", DefaultLanguageHighlighterColors.BRACES)
    val DOT = createTextAttributesKey("PRISMA_DOT", DefaultLanguageHighlighterColors.DOT)
    val COMMA = createTextAttributesKey("PRISMA_COMMA", DefaultLanguageHighlighterColors.COMMA)
    val OPERATION_SIGN = createTextAttributesKey("PRISMA_OPERATION_SIGN", DefaultLanguageHighlighterColors.OPERATION_SIGN)
    val UNSUPPORTED_TYPE = createTextAttributesKey("PRISMA_UNSUPPORTED_TYPE", DefaultLanguageHighlighterColors.CONSTANT)
}