package com.vepanimas.intellij.prisma.lang.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.util.CachedValueProvider
import com.intellij.psi.util.CachedValuesManager
import com.vepanimas.intellij.prisma.lang.psi.PrismaReferenceElement
import com.vepanimas.intellij.prisma.lang.resolve.PrismaReference

abstract class PrismaReferenceElementBase(node: ASTNode) : PrismaElementImpl(node), PrismaReferenceElement {

    override fun getReference(): PrismaReference? {
        return CachedValuesManager.getCachedValue(this) {
            CachedValueProvider.Result.create(PrismaReference.create(this), this)
        }
    }

    override fun resolve(): PsiElement? = reference?.resolve()
}