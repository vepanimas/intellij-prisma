package com.vepanimas.intellij.prisma.ide.editor

import com.intellij.codeInsight.editorActions.SimpleTokenSetQuoteHandler
import com.vepanimas.intellij.prisma.lang.psi.PRISMA_STRINGS

class PrismaQuoteHandler : SimpleTokenSetQuoteHandler(PRISMA_STRINGS)