package com.vepanimas.intellij.prisma.ide.formatter

import com.intellij.formatting.*
import com.intellij.lang.ASTNode
import com.intellij.psi.formatter.FormatterUtil
import com.intellij.psi.formatter.common.AbstractBlock

class PrismaFmtBlock(
    node: ASTNode,
    wrap: Wrap?,
    alignment: Alignment?,
    private val context: PrismaFmtBlockContext
) : AbstractBlock(node, wrap, alignment) {
    private val spacingProcessor = PrismaSpacingProcessor(this, context)
    private val indentProcessor = PrismaIndentProcessor()
    private val currentIndent = indentProcessor.getIndent(node)

    override fun getSpacing(child1: Block?, child2: Block): Spacing? {
        return spacingProcessor.createSpacing(child1, child2)
    }

    override fun isLeaf(): Boolean = false

    override fun buildChildren(): List<Block> {
        return node.getChildren(null).asSequence()
            .filterNot { FormatterUtil.isWhitespaceOrEmpty(it) }
            .map { PrismaFmtBlock(it, null, null, context) }
            .toList()
    }

    override fun getChildIndent(): Indent? = indentProcessor.getChildIndent(node)

    override fun getIndent(): Indent = currentIndent
}