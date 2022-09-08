package com.vepanimas.intellij.prisma.lang.psi.impl

import com.intellij.lang.ASTNode
import com.vepanimas.intellij.prisma.lang.psi.PrismaNamedArgument

abstract class PrismaNamedArgumentMixin(node: ASTNode) : PrismaElementImpl(node), PrismaNamedArgument {
    override val referenceText: String?
        get() = identifier.text
}