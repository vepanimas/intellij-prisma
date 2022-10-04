package com.vepanimas.intellij.prisma.lang.presentation

import com.intellij.psi.PsiElement
import com.vepanimas.intellij.prisma.lang.psi.*

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