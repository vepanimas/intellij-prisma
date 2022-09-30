package com.vepanimas.intellij.prisma.lang.psi.impl

import com.intellij.lang.ASTNode
import com.vepanimas.intellij.prisma.lang.psi.PrismaKeyValueDeclaration

abstract class PrismaKeyValueDeclarationMixin(node: ASTNode) : PrismaDeclarationMixin(node), PrismaKeyValueDeclaration