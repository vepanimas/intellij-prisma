package com.vepanimas.intellij.prisma.lang.psi.impl

import com.intellij.lang.ASTNode
import com.vepanimas.intellij.prisma.lang.psi.PrismaDeclaration

abstract class PrismaTypeAliasMixin(node: ASTNode) : PrismaDeclarationMixin(node), PrismaDeclaration