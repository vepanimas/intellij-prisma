package com.vepanimas.intellij.prisma.lang.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.navigation.ItemPresentation
import com.intellij.psi.PsiElement
import com.intellij.refactoring.suggested.startOffset
import com.vepanimas.intellij.prisma.lang.psi.PrismaElementFactory
import com.vepanimas.intellij.prisma.lang.psi.PrismaElementTypes
import com.vepanimas.intellij.prisma.lang.psi.PrismaNameIdentifierOwner
import com.vepanimas.intellij.prisma.lang.psi.presentation.getPresentation
import com.vepanimas.intellij.prisma.lang.psi.presentation.icon
import javax.swing.Icon

abstract class PrismaNamedElementImpl(node: ASTNode) : PrismaElementImpl(node), PrismaNameIdentifierOwner {
    override fun getName(): String? = nameIdentifier?.text

    override fun setName(name: String): PsiElement {
        nameIdentifier?.replace(PrismaElementFactory.createIdentifier(project, name))
        return this
    }

    override fun getNameIdentifier(): PsiElement? = findChildByType(PrismaElementTypes.IDENTIFIER)

    override fun getTextOffset(): Int = nameIdentifier?.startOffset ?: super.getTextOffset()

    override fun getPresentation(): ItemPresentation? = getPresentation(this)

    override fun getIcon(flags: Int): Icon? = icon
}