package com.vepanimas.intellij.prisma.ide.formatter

import com.intellij.formatting.Block
import com.intellij.formatting.Spacing
import com.intellij.formatting.SpacingBuilder
import com.intellij.psi.formatter.common.AbstractBlock
import com.vepanimas.intellij.prisma.lang.psi.PRISMA_TOP_ELEMENTS
import com.vepanimas.intellij.prisma.lang.psi.PrismaElementTypes.*
import com.vepanimas.intellij.prisma.lang.psi.PrismaFileElementType

class PrismaSpacingProcessor(private val block: AbstractBlock, context: PrismaFmtBlockContext) {
    private val parent = block.node
    private val spacingBuilder = SpacingBuilder(context.commonSettings)
        .after(PRISMA_TOP_ELEMENTS).spacing(1, 1)
        .after(LBRACE).spacing(1, 0)
        .before(RBRACE).spacing(1, 0)
        .withinPair(LBRACE, RBRACE).spacing(1, 0)
        .between(FIELD_DECLARATION, FIELD_DECLARATION).spacing(1, 1)
        .between(FIELD_DECLARATION, BLOCK_ATTRIBUTE).spacing(2, 1)
        .after(BLOCK_ATTRIBUTE).spacing(1, 0)
        .between(ENUM_VALUE_DECLARATION, ENUM_VALUE_DECLARATION).spacing(1, 1)
        .between(KEY_VALUE, KEY_VALUE).spacing(1, 1)

    fun createSpacing(child1: Block?, child2: Block): Spacing? {
        val parentType = parent.elementType
        if (parentType == PrismaFileElementType && child1 == null) {
            return Spacing.createSpacing(0, 0, 0, false, 0)
        }

        return spacingBuilder.getSpacing(block, child1, child2)
    }

    private fun SpacingBuilder.RuleBuilder.spacing(minLF: Int, keepBlankLines: Int): SpacingBuilder {
        return spacing(0, 0, minLF, false, keepBlankLines)
    }
}