package com.vepanimas.intellij.prisma.lang.psi.presentation

import com.intellij.psi.PsiElement
import com.vepanimas.intellij.prisma.lang.psi.PrismaBlockAttribute
import com.vepanimas.intellij.prisma.lang.psi.PrismaExpression
import com.vepanimas.intellij.prisma.lang.psi.PrismaFieldAttribute
import com.vepanimas.intellij.prisma.lang.psi.PrismaFieldDeclaration
import com.vepanimas.intellij.prisma.lang.psi.PrismaFieldType

class PrismaPsiRenderer {
    fun build(element: PsiElement?): String {
        // TODO: implement
        return when (element) {
            is PrismaFieldDeclaration -> element.text
            is PrismaFieldType -> element.text
            is PrismaFieldAttribute -> element.text
            is PrismaBlockAttribute -> element.text
            is PrismaExpression -> element.text
            else -> element?.text ?: ""
        }
    }
}