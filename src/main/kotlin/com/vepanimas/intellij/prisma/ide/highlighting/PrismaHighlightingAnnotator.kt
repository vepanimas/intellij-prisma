package com.vepanimas.intellij.prisma.ide.highlighting

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType
import com.vepanimas.intellij.prisma.lang.psi.*
import com.vepanimas.intellij.prisma.lang.psi.PrismaElementTypes.IDENTIFIER

class PrismaHighlightingAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        when (element.elementType) {
            IDENTIFIER -> highlightIdentifier(holder, element)
        }
    }

    private fun highlightIdentifier(holder: AnnotationHolder, element: PsiElement) {
        val parent = element.parent ?: return

        when (parent) {
            is PrismaDeclaration -> newAnnotation(holder, element, PrismaColors.TYPE_NAME)
            is PrismaFieldDeclaration -> newAnnotation(holder, element, PrismaColors.FIELD_NAME)
            is PrismaTypeReference -> newAnnotation(holder, element, PrismaColors.TYPE_REFERENCE)
            is PrismaNamedArgument -> newAnnotation(holder, element, PrismaColors.PARAMETER)
            is PrismaKeyValue -> newAnnotation(holder, element, PrismaColors.FIELD_NAME)
            is PrismaEnumValueDeclaration -> newAnnotation(holder, element, PrismaColors.FIELD_NAME)
            is PrismaPath -> when (parent.findTopmostPathParent()) {
                is PrismaPathExpression -> newAnnotation(holder, element, PrismaColors.FIELD_REFERENCE)
                is PrismaFunctionCall -> newAnnotation(holder, element, PrismaColors.FUNCTION)
                is PrismaBlockAttribute, is PrismaFieldAttribute ->
                    newAnnotation(holder, element, PrismaColors.ATTRIBUTE)
            }
        }
    }

    private fun newAnnotation(
        holder: AnnotationHolder, element: PsiElement, textAttributesKey: TextAttributesKey
    ) {
        newAnnotationBuilder(holder, textAttributesKey.externalName)
            .textAttributes(textAttributesKey)
            .range(element)
            .create()
    }

    private fun newAnnotationBuilder(holder: AnnotationHolder, tag: String) =
        if (ApplicationManager.getApplication().isUnitTestMode)
            holder.newAnnotation(HighlightSeverity.INFORMATION, tag)
        else
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
}