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
import com.vepanimas.intellij.prisma.lang.psi.presentation.icon
import javax.swing.Icon

class PrismaSchemaFakeElement(
    private val parent: PsiElement,
    val schemaElement: PrismaSchemaElement,
) : FakePsiElement(), PrismaNamedElement {
    override fun getParent(): PsiElement = parent

    override fun getProject(): Project = parent.project

    override fun getContainingFile(): PsiFile = parent.containingFile

    override fun getName(): String = schemaElement.label

    override fun getText(): String = name

    override fun getIcon(open: Boolean): Icon? = this.schemaElement.icon

    companion object {
        fun createForCompletion(
            parameters: CompletionParameters,
            schemaElement: PrismaSchemaElement,
        ): PrismaSchemaFakeElement {
            val parent = findSuitableParent(parameters)
            return createForCompletion(parent, schemaElement)
        }

        fun createForCompletion(
            parent: PsiElement,
            schemaElement: PrismaSchemaElement,
        ): PrismaSchemaFakeElement {
            return PrismaSchemaFakeElement(parent, schemaElement)
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