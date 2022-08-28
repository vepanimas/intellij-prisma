package com.vepanimas.intellij.prisma.lang.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.util.childrenOfType
import com.vepanimas.intellij.prisma.lang.psi.PrismaBlock
import com.vepanimas.intellij.prisma.lang.psi.PrismaElement

abstract class PrismaBlockMixin(node: ASTNode) : PrismaElementImpl(node), PrismaBlock {
    override fun getMembers(): List<PrismaElement> = childrenOfType()
}