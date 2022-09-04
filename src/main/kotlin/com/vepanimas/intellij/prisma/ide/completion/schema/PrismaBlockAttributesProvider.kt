package com.vepanimas.intellij.prisma.ide.completion.schema

import com.intellij.patterns.ElementPattern
import com.intellij.patterns.PlatformPatterns.or
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.psi.PsiElement
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaElementKind
import com.vepanimas.intellij.prisma.lang.psi.*

object PrismaBlockAttributesProvider : PrismaSchemaCompletionProvider() {
    override val kind: PrismaSchemaElementKind = PrismaSchemaElementKind.BLOCK_ATTRIBUTE

    override val pattern: ElementPattern<out PsiElement> =
        psiElement().andOr(
            psiElement().withParent(
                psiElement(PrismaFieldDeclaration::class.java).andOr(
                    psiElement().afterNewLine(),
                    psiElement().afterLeaf(psiElement(PrismaElementTypes.AT))
                )
            ),
            psiElement().withSuperParent(
                2, or(
                    psiElement().andOr(
                        psiElement(PrismaFieldAttribute::class.java),
                        psiElement(PrismaBlockAttribute::class.java),
                    ).afterNewLine()
                )
            )
        ).inside(psiElement(PrismaModelDeclaration::class.java))

}