package com.vepanimas.intellij.prisma.lang.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.vepanimas.intellij.prisma.lang.psi.PrismaElement

open class PrismaElementImpl(node: ASTNode) : ASTWrapperPsiElement(node), PrismaElement