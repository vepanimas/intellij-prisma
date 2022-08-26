package com.vepanimas.intellij.prisma.lang.psi

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiParserFacade
import com.intellij.psi.util.PsiTreeUtil
import com.vepanimas.intellij.prisma.lang.PrismaLanguage
import org.jetbrains.annotations.NonNls

object PrismaElementFactory {
    fun createFile(project: Project, @NonNls text: String): PrismaFile =
        PsiFileFactory.getInstance(project).createFileFromText(
            "dummy.prisma", PrismaLanguage, text, false, true
        ) as PrismaFile

    inline fun <reified T : PrismaElement> createElement(project: Project, text: String): T? =
        PsiTreeUtil.findChildOfType(createFile(project, text), T::class.java, true)

    fun createIdentifier(project: Project, name: String): PsiElement =
        createElement<PrismaModelDeclaration>(project, "model $name {}")?.identifier
            ?: error("Invalid identifier: $name")

    fun createNewLine(project: Project, count: Int = 1): PsiElement =
        PsiParserFacade.getInstance(project).createWhiteSpaceFromText("\n".repeat(count))
}