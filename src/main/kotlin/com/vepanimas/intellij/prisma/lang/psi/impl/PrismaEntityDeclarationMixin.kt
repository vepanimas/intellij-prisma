package com.vepanimas.intellij.prisma.lang.psi.impl

import com.intellij.lang.ASTNode
import com.vepanimas.intellij.prisma.lang.psi.PrismaEntityDeclaration

abstract class PrismaEntityDeclarationMixin(node: ASTNode) :
    PrismaNamedElementImpl(node),
    PrismaEntityDeclaration