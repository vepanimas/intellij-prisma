package com.vepanimas.intellij.prisma.lang.psi

import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.patterns.PsiElementPattern
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiErrorElement

object PrismaPsiPatterns {
    val topKeyword: PsiElementPattern.Capture<PsiElement> =
        psiElement(PrismaElementTypes.IDENTIFIER).withParent(
            psiElement(PsiErrorElement::class.java).withParent(PrismaFile::class.java)
        )

    val typeReference: PsiElementPattern.Capture<PsiElement> =
        psiElement(PrismaElementTypes.IDENTIFIER).withParent(psiElement(PrismaTypeReference::class.java))

    val datasourceField: PsiElementPattern.Capture<PsiElement> =
        psiElement()
            .withParent(PrismaKeyValue::class.java)
            .withSuperParent(3, PrismaDatasourceDeclaration::class.java)

    val generatorField: PsiElementPattern.Capture<PsiElement> =
        psiElement()
            .withParent(PrismaKeyValue::class.java)
            .withSuperParent(3, PrismaGeneratorDeclaration::class.java)
}