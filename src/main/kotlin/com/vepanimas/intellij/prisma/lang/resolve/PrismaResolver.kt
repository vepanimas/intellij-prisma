package com.vepanimas.intellij.prisma.lang.resolve

import com.vepanimas.intellij.prisma.lang.psi.PrismaFieldDeclaration
import com.vepanimas.intellij.prisma.lang.psi.PrismaFunctionCall

object PrismaResolver {
    fun isFieldExpression(function: PrismaFunctionCall) =
        function.pathExpression.resolve() is PrismaFieldDeclaration
}