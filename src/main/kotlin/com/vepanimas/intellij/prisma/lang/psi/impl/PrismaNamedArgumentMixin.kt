package com.vepanimas.intellij.prisma.lang.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.vepanimas.intellij.prisma.lang.psi.PrismaNamedArgument

abstract class PrismaNamedArgumentMixin(node: ASTNode) :
    PrismaReferenceElementBase(node), PrismaNamedArgument {

    override val referenceNameElement: PsiElement
        get() = identifier

}