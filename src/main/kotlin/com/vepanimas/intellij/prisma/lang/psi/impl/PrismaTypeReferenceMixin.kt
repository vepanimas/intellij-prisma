package com.vepanimas.intellij.prisma.lang.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.vepanimas.intellij.prisma.lang.psi.PrismaReferenceElement
import com.vepanimas.intellij.prisma.lang.psi.PrismaTypeReference

abstract class PrismaTypeReferenceMixin(node: ASTNode) :
    PrismaReferenceElementBase(node),
    PrismaTypeReference,
    PrismaReferenceElement {

    override val referenceNameElement: PsiElement?
        get() = identifier ?: unsupportedType?.identifier

}