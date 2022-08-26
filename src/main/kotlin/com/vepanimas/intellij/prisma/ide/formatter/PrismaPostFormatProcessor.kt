package com.vepanimas.intellij.prisma.ide.formatter

import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.impl.source.codeStyle.PostFormatProcessor
import com.intellij.util.text.CharArrayUtil
import com.vepanimas.intellij.prisma.lang.psi.PrismaFile

class PrismaPostFormatProcessor : PostFormatProcessor {
    private fun isApplicable(file: PsiFile): Boolean {
        return file is PrismaFile
    }

    override fun processElement(source: PsiElement, settings: CodeStyleSettings): PsiElement {
        val file = source.containingFile
        if (!isApplicable(file)) {
            return source
        }
        processText(file, source.textRange, settings)
        return source
    }

    override fun processText(source: PsiFile, rangeToReformat: TextRange, settings: CodeStyleSettings): TextRange {
        if (!isApplicable(source)) {
            return rangeToReformat
        }

        val manager = PsiDocumentManager.getInstance(source.project)
        val document = manager.getDocument(source) ?: return rangeToReformat
        val oldRange = findTrailingCommentRange(document)
        if (!rangeToReformat.intersects(oldRange)) {
            return rangeToReformat
        }

        if (oldRange.startOffset != 0 && document.getText(oldRange) != "\n") {
            document.replaceString(oldRange.startOffset, oldRange.endOffset, "\n")
            manager.commitDocument(document)
            return rangeToReformat.grown(-(oldRange.length - 1))
        }
        return rangeToReformat
    }

    private fun findTrailingCommentRange(document: Document): TextRange {
        val chars = document.charsSequence
        val from = CharArrayUtil.shiftBackward(chars, chars.length - 1, " \r\n\t")
        return TextRange.create(from + 1, chars.length)
    }
}