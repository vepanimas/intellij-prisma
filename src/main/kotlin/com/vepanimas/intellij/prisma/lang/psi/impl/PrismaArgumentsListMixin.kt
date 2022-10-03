package com.vepanimas.intellij.prisma.lang.psi.impl

import com.intellij.lang.ASTNode
import com.vepanimas.intellij.prisma.lang.psi.PrismaArgumentsList
import com.vepanimas.intellij.prisma.lang.psi.PrismaNamedArgument

abstract class PrismaArgumentsListMixin(node: ASTNode) : PrismaElementImpl(node), PrismaArgumentsList {
    override fun findArgumentByName(name: String): PrismaNamedArgument? {
        return arguments.filterIsInstance<PrismaNamedArgument>().find { it.referenceName == name }
    }
}