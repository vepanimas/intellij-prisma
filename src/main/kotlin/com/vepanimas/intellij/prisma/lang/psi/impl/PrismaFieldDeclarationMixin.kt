package com.vepanimas.intellij.prisma.lang.psi.impl

import com.intellij.lang.ASTNode
import com.vepanimas.intellij.prisma.lang.psi.PrismaFieldDeclaration
import com.vepanimas.intellij.prisma.lang.psi.PrismaMemberDeclaration

abstract class PrismaFieldDeclarationMixin(node: ASTNode) :
    PrismaNamedElementImpl(node),
    PrismaMemberDeclaration,
    PrismaFieldDeclaration