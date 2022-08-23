package com.vepanimas.intellij.prisma.lang.psi

import com.intellij.psi.TokenType.WHITE_SPACE
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet.create
import com.vepanimas.intellij.prisma.lang.PrismaLanguage
import com.vepanimas.intellij.prisma.lang.parser.PrismaParserDefinition
import com.vepanimas.intellij.prisma.lang.psi.PrismaElementTypes.STRING_LITERAL

class PrismaTokenType(debugName: String) : IElementType(debugName, PrismaLanguage)

val PRISMA_STRINGS = create(STRING_LITERAL)
val PRISMA_COMMENTS = create(PrismaParserDefinition.DOC_COMMENT, PrismaParserDefinition.LINE_COMMENT)
val PRISMA_WS = create(WHITE_SPACE)