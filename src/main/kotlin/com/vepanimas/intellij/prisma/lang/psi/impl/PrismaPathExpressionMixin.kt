package com.vepanimas.intellij.prisma.lang.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.vepanimas.intellij.prisma.lang.psi.PrismaElementTypes
import com.vepanimas.intellij.prisma.lang.psi.PrismaPathExpression

abstract class PrismaPathExpressionMixin(node: ASTNode) : PrismaElementImpl(node), PrismaPathExpression {
    override val qualifier: PsiElement?
        get() = findChildByType(PrismaElementTypes.PATH_EXPRESSION)

    override val identifier: PsiElement?
        get() = findChildByType(PrismaElementTypes.IDENTIFIER)

    override val referenceText: String?
        get() = identifier?.text
}