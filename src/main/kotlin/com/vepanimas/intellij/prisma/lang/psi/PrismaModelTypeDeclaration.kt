package com.vepanimas.intellij.prisma.lang.psi

interface PrismaModelTypeDeclaration : PrismaDeclaration {
    fun getFieldDeclarationBlock(): PrismaFieldDeclarationBlock
}