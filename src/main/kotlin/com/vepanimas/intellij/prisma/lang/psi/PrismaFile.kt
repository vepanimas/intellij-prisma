package com.vepanimas.intellij.prisma.lang.psi

import com.vepanimas.intellij.prisma.lang.PrismaFileType
import com.vepanimas.intellij.prisma.lang.PrismaLanguage
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class PrismaFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, PrismaLanguage) {
    override fun getFileType(): FileType = PrismaFileType
}