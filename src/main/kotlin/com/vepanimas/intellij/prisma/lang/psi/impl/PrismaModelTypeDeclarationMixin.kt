package com.vepanimas.intellij.prisma.lang.psi.impl

import com.intellij.lang.ASTNode
import com.vepanimas.intellij.prisma.lang.psi.PrismaModelTypeDeclaration

abstract class PrismaModelTypeDeclarationMixin(node: ASTNode) :
    PrismaDeclarationMixin(node),
    PrismaModelTypeDeclaration