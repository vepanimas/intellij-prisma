package com.github.vepanimas.intellij.prisma.lang

import com.github.vepanimas.intellij.prisma.PrismaIcons
import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

object PrismaFileType : LanguageFileType(PrismaLanguage) {
    override fun getName(): String = "Prisma"

    override fun getDescription(): String = "Prisma"

    override fun getDefaultExtension(): String = "prisma"

    override fun getIcon(): Icon = PrismaIcons.PRISMA
}