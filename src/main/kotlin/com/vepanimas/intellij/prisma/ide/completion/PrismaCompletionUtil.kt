package com.vepanimas.intellij.prisma.ide.completion

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaContext
import com.vepanimas.intellij.prisma.lang.psi.*

fun findAssociatedField(position: PsiElement?): PrismaFieldDeclaration? {
    return when {
        position is PsiWhiteSpace -> position.skipWhitespacesBackwardWithoutNewLines() as? PrismaFieldDeclaration

        position.elementType == PrismaElementTypes.IDENTIFIER ->
            PsiTreeUtil.getParentOfType(position, PrismaFieldDeclaration::class.java)

        else -> null
    }
}

fun collectExistingAttributeNamesForDeclaration(declaration: PrismaDeclaration): Set<String> {
    val attributes = PsiTreeUtil.findChildrenOfAnyType(
        declaration,
        PrismaBlockAttribute::class.java,
        PrismaFieldAttribute::class.java
    )

    return attributes.mapNotNullTo(mutableSetOf()) { PrismaSchemaContext.getSchemaLabel(it) }
}

fun collectExistingAttributeNamesForField(field: PrismaFieldDeclaration): Set<String> {
    return field.fieldAttributeList.mapNotNullTo(mutableSetOf()) { PrismaSchemaContext.getSchemaLabel(it) }
}