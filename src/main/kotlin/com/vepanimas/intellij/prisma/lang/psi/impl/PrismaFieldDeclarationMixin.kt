package com.vepanimas.intellij.prisma.lang.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.util.CachedValueProvider
import com.intellij.psi.util.CachedValuesManager
import com.intellij.psi.util.PsiTreeUtil
import com.vepanimas.intellij.prisma.lang.psi.PrismaFieldDeclaration
import com.vepanimas.intellij.prisma.lang.psi.PrismaMemberDeclaration
import com.vepanimas.intellij.prisma.lang.psi.PrismaTypeSignature
import com.vepanimas.intellij.prisma.lang.types.PrismaAnyType
import com.vepanimas.intellij.prisma.lang.types.PrismaType

abstract class PrismaFieldDeclarationMixin(node: ASTNode) :
    PrismaNamedElementImpl(node),
    PrismaMemberDeclaration,
    PrismaFieldDeclaration {

    override val type: PrismaType
        get() = CachedValuesManager.getCachedValue(this) {
            val typeSignature = PsiTreeUtil.getChildOfType(this, PrismaTypeSignature::class.java)
            val calculatedType = typeSignature?.type ?: PrismaAnyType
            CachedValueProvider.Result.create(calculatedType, containingFile)
        }

    override fun getNativeType(): String? = CachedValuesManager.getCachedValue(this) {
        val nativeType = fieldAttributeList
            .asSequence()
            .mapNotNull { it.pathExpression }
            .find { it.qualifier?.textMatches("db") ?: false }
            ?.referenceName
        CachedValueProvider.Result.create(nativeType, containingFile)
    }
}