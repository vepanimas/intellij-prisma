package com.vepanimas.intellij.prisma.ide.formatter

import com.intellij.formatting.Alignment
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType
import com.intellij.refactoring.suggested.endOffset
import com.vepanimas.intellij.prisma.lang.psi.PrismaElementTypes.*
import com.vepanimas.intellij.prisma.lang.psi.PrismaFieldAttribute
import com.vepanimas.intellij.prisma.lang.psi.PrismaFieldType
import com.vepanimas.intellij.prisma.lang.psi.hasTrailingComment

class PrismaChildAlignment(
    private val typeAlignment: Alignment?,
    private val attributeAlignment: Alignment?,
    private val keyValueAlignment: Alignment?
) {
    fun getAlignmentForElement(element: PsiElement): Alignment? {
        return when (element.elementType) {
            EQ -> keyValueAlignment

            FIELD_TYPE -> typeAlignment

            FIELD_ATTRIBUTE -> {
                // align only by the first attribute in the list
                val prev = PsiTreeUtil.skipWhitespacesBackward(element)
                return if (prev is PrismaFieldType) {
                    attributeAlignment
                } else {
                    null
                }
            }

            else -> null
        }
    }

    fun createAlignmentAnchor(node: ASTNode): PrismaAnchorBlock? {
        if (node.elementType == FIELD_TYPE && attributeAlignment != null) {
            val element = node.psi
            val next = PsiTreeUtil.skipWhitespacesForward(element)
            if (next !is PrismaFieldAttribute) {
                return PrismaAnchorBlock(element.endOffset, attributeAlignment)
            }
        }

        return null
    }
}
