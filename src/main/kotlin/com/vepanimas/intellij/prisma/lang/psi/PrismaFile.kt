package com.vepanimas.intellij.prisma.lang.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import com.intellij.psi.util.CachedValueProvider
import com.intellij.psi.util.CachedValuesManager
import com.intellij.psi.util.childrenOfType
import com.intellij.util.castSafelyTo
import com.vepanimas.intellij.prisma.ide.schema.PrismaDatasourceType
import com.vepanimas.intellij.prisma.lang.PrismaConstants
import com.vepanimas.intellij.prisma.lang.PrismaFileType
import com.vepanimas.intellij.prisma.lang.PrismaLanguage

class PrismaFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, PrismaLanguage) {
    override fun getFileType(): FileType = PrismaFileType

    val datasourceType: PrismaDatasourceType?
        get() = CachedValuesManager.getCachedValue(this) {
            val declaration = declarations.find { it is PrismaDatasourceDeclaration } as? PrismaDatasourceDeclaration
            val provider =
                declaration?.findMemberByName(PrismaConstants.DatasourceFields.PROVIDER) as? PrismaKeyValue
            val providerValue = provider?.expression.castSafelyTo<PrismaLiteralExpression>()?.value as? String
            val type = PrismaDatasourceType.fromString(providerValue)
            CachedValueProvider.Result.create(type, this)
        }

    val declarations: List<PrismaDeclaration>
        get() = childrenOfType()

    val entityDeclarations: List<PrismaEntityDeclaration>
        get() = childrenOfType()
}