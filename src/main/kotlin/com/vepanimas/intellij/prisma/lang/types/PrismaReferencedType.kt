package com.vepanimas.intellij.prisma.lang.types

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.util.CachedValueProvider
import com.intellij.psi.util.CachedValuesManager
import com.vepanimas.intellij.prisma.lang.psi.PrismaFieldType

class PrismaReferencedType(val name: String, element: PsiElement) : PrismaTypeBase(element), PrismaResolvableType {
    override fun resolveDeclaration(): PsiNamedElement? {
        return CachedValuesManager.getCachedValue(element) {
            CachedValueProvider.Result.create(resolveDeclarationImpl(element), element)
        }
    }

    private fun resolveDeclarationImpl(element: PsiElement): PsiNamedElement? {
        if (!element.isValid) return null
        if (element is PrismaFieldType) {
            return element.typeReference.resolve() as? PsiNamedElement
        }
        return null
    }
}