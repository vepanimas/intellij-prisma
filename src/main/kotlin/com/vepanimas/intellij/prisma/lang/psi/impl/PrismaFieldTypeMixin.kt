package com.vepanimas.intellij.prisma.lang.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.util.CachedValueProvider
import com.intellij.psi.util.CachedValuesManager
import com.vepanimas.intellij.prisma.lang.psi.PrismaFieldType
import com.vepanimas.intellij.prisma.lang.psi.PrismaTypeReference
import com.vepanimas.intellij.prisma.lang.types.PrismaType
import com.vepanimas.intellij.prisma.lang.types.createTypeFromSignature

abstract class PrismaFieldTypeMixin(node: ASTNode) : PrismaElementImpl(node), PrismaFieldType {
    override fun getTypeReference(): PrismaTypeReference = findNotNullChildByClass(PrismaTypeReference::class.java)

    override val type: PrismaType
        get() = CachedValuesManager.getCachedValue(this) {
            CachedValueProvider.Result.create(createTypeFromSignature(this), containingFile)
        }

}