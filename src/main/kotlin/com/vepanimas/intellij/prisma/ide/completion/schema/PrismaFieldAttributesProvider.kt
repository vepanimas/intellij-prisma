package com.vepanimas.intellij.prisma.ide.completion.schema

import com.intellij.patterns.ElementPattern
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.psi.PsiElement
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaKind
import com.vepanimas.intellij.prisma.lang.psi.*

object PrismaFieldAttributesProvider : PrismaSchemaCompletionProvider() {
    override val kind: PrismaSchemaKind = PrismaSchemaKind.FIELD_ATTRIBUTE

    override val pattern: ElementPattern<out PsiElement> =
        psiElement().andOr(
            psiElement().withParent(
                psiElement(PrismaFieldDeclaration::class.java)
                    .afterSiblingIncludingNewLines(psiElement(PrismaFieldDeclaration::class.java))
            ),
            psiElement().withParent(
                psiElement(PrismaPath::class.java).withParent(
                    psiElement(PrismaFieldAttribute::class.java)
                        .afterSiblingIncludingNewLines(
                            psiElement().andOr(
                                psiElement(PrismaFieldType::class.java),
                                psiElement(PrismaFieldAttribute::class.java),
                            )
                        )
                )
            )
        ).inside(psiElement(PrismaModelDeclaration::class.java))

}