package com.vepanimas.intellij.prisma.lang.psi.impl

import com.intellij.lang.ASTNode
import com.vepanimas.intellij.prisma.lang.psi.PrismaFieldType
import com.vepanimas.intellij.prisma.lang.psi.PrismaTypeReference

abstract class PrismaFieldTypeMixin(node: ASTNode) : PrismaElementImpl(node), PrismaFieldType {
    override fun getTypeReference(): PrismaTypeReference = findNotNullChildByClass(PrismaTypeReference::class.java)
}