package com.vepanimas.intellij.prisma.ide.schema

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiErrorElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.impl.FakePsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.vepanimas.intellij.prisma.lang.psi.PrismaNamedElement

class PrismaSchemaFakeElement(
    val label: String,
    private val parent: PsiElement,
    val kind: PrismaSchemaElementKind,
) : FakePsiElement(), PrismaNamedElement {
    override fun getParent(): PsiElement = parent

    override fun getProject(): Project = parent.project

    override fun getContainingFile(): PsiFile = parent.containingFile

    override fun getName(): String = label

    override fun getText(): String = name

    companion object {
        fun createForCompletion(
            parameters: CompletionParameters,
            text: String,
            kind: PrismaSchemaElementKind,
        ): PrismaSchemaFakeElement {
            val parent = findSuitableParent(parameters)
            return PrismaSchemaFakeElement(text, parent, kind)
        }

        private fun findSuitableParent(parameters: CompletionParameters): PsiElement {
            return PsiTreeUtil.skipParentsOfType(
                parameters.originalPosition,
                PsiWhiteSpace::class.java,
                PsiErrorElement::class.java
            ) ?: parameters.originalFile
        }
    }
}