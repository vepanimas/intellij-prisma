package com.vepanimas.intellij.prisma.lang.psi

import com.intellij.psi.PsiElement

interface PrismaModelTypeDeclaration : PrismaDeclaration {
    fun getFieldDeclarationBlock(): PrismaFieldDeclarationBlock

    fun getIdentifier(): PsiElement
}