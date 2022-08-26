package com.vepanimas.intellij.prisma.ide.formatter

import com.intellij.formatting.Block
import com.intellij.formatting.Spacing
import com.intellij.lang.ASTNode

class PrismaSpacingProcessor(private val node: ASTNode, private val context: PrismaFmtBlockContext) {
    fun createSpacing(child1: Block?, child2: Block): Spacing? {
        return null
    }
}