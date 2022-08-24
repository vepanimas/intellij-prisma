package com.vepanimas.intellij.prisma.lang.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.refactoring.suggested.startOffset
import com.vepanimas.intellij.prisma.lang.psi.PrismaElementFactory
import com.vepanimas.intellij.prisma.lang.psi.PrismaElementTypes
import com.vepanimas.intellij.prisma.lang.psi.PrismaNameIdentifierOwner

abstract class PrismaNameIdentifierOwnerImpl(node: ASTNode) : PrismaNamedElementImpl(node), PrismaNameIdentifierOwner {
    override fun getName(): String? = nameIdentifier?.text

    override fun setName(name: String): PsiElement {
        nameIdentifier?.replace(PrismaElementFactory.createIdentifier(project, name))
        return this
    }

    override fun getNameIdentifier(): PsiElement? = findChildByType(PrismaElementTypes.IDENTIFIER)

    override fun getTextOffset(): Int = nameIdentifier?.startOffset ?: super.getTextOffset()
}