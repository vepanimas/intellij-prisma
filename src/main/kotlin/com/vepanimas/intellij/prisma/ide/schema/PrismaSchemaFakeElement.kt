package com.vepanimas.intellij.prisma.ide.schema

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.impl.FakePsiElement
import com.intellij.psi.tree.IElementType

class PrismaSchemaFakeElement(
    private val keyword: String,
    val elementType: IElementType?,
    private val parent: PsiElement
) :
    FakePsiElement() {
    override fun getParent(): PsiElement = parent

    override fun getProject(): Project = parent.project

    override fun getContainingFile(): PsiFile = parent.containingFile

    override fun getText(): String = keyword
}