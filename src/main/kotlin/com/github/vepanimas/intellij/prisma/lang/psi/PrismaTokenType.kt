package com.github.vepanimas.intellij.prisma.lang.psi

import com.github.vepanimas.intellij.prisma.lang.PrismaLanguage
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet

class PrismaTokenType(debugName: String) : IElementType(debugName, PrismaLanguage)

val PRISMA_STRINGS = TokenSet.EMPTY
val PRISMA_COMMENTS = TokenSet.EMPTY
val PRISMA_WS = TokenSet.create(TokenType.WHITE_SPACE)