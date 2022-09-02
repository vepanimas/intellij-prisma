package com.vepanimas.intellij.prisma.ide.completion.schema

import com.intellij.patterns.ElementPattern
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.psi.PsiElement
import com.vepanimas.intellij.prisma.ide.completion.PrismaSchemaCompletionProvider
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaElementKind
import com.vepanimas.intellij.prisma.lang.psi.PrismaElementTypes
import com.vepanimas.intellij.prisma.lang.psi.PrismaTypeReference

object PrismaPrimitiveTypeProvider : PrismaSchemaCompletionProvider() {
    override val kind: PrismaSchemaElementKind
        get() = PrismaSchemaElementKind.PRIMITIVE_TYPE

    override val pattern: ElementPattern<out PsiElement> =
        psiElement(PrismaElementTypes.IDENTIFIER).withParent(psiElement(PrismaTypeReference::class.java))
}

