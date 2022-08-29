package com.vepanimas.intellij.prisma.lang.psi.impl

import com.intellij.lang.ASTNode
import com.vepanimas.intellij.prisma.lang.psi.PrismaMemberDeclaration

abstract class PrismaKeyValueMixin(node: ASTNode) : PrismaNamedElementImpl(node), PrismaMemberDeclaration