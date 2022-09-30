package com.vepanimas.intellij.prisma.lang.psi

interface PrismaModelTypeDeclaration : PrismaEntityDeclaration {
    fun getFieldDeclarationBlock(): PrismaFieldDeclarationBlock?
}