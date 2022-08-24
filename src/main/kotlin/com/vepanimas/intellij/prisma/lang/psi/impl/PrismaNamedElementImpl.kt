package com.vepanimas.intellij.prisma.lang.psi.impl

import com.intellij.lang.ASTNode
import com.vepanimas.intellij.prisma.lang.psi.PrismaNamedElement

abstract class PrismaNamedElementImpl(node: ASTNode) : PrismaElementImpl(node), PrismaNamedElement