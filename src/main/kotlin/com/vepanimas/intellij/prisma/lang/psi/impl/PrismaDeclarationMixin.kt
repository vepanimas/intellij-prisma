package com.vepanimas.intellij.prisma.lang.psi.impl

import com.intellij.lang.ASTNode
import com.vepanimas.intellij.prisma.lang.psi.PrismaBlock
import com.vepanimas.intellij.prisma.lang.psi.PrismaDeclaration
import com.vepanimas.intellij.prisma.lang.psi.PrismaMemberDeclaration

abstract class PrismaDeclarationMixin(node: ASTNode) :
    PrismaNamedElementImpl(node),
    PrismaDeclaration {

    override fun getBlock(): PrismaBlock? = findChildByClass(PrismaBlock::class.java)

    override fun getMembers(): List<PrismaMemberDeclaration> =
        getBlock()?.getMembers()?.filterIsInstance<PrismaMemberDeclaration>() ?: emptyList()

    override fun findMemberByName(name: String): PrismaMemberDeclaration? =
        getBlock()?.findMemberByName(name) as? PrismaMemberDeclaration
}