package com.vepanimas.intellij.prisma.lang.psi

interface PrismaEntityDeclaration : PrismaDeclaration {
    fun getFieldDeclarationBlock(): PrismaFieldDeclarationBlock
}