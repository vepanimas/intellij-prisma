package com.vepanimas.intellij.prisma.lang.psi.presentation

import com.intellij.navigation.ItemPresentation
import com.vepanimas.intellij.prisma.PrismaIcons
import com.vepanimas.intellij.prisma.ide.schema.*
import com.vepanimas.intellij.prisma.lang.psi.PrismaElement
import com.vepanimas.intellij.prisma.lang.psi.PrismaNamedElement
import javax.swing.Icon

fun getPresentation(element: PrismaElement): ItemPresentation = object : ItemPresentation {
    override fun getPresentableText(): String? = when (element) {
        is PrismaNamedElement -> element.name
        else -> null
    }

    override fun getLocationString(): String = element.containingFile.name

    override fun getIcon(unused: Boolean): Icon? = element.getIcon(0)
}

val PrismaSchemaElement.icon: Icon?
    get() = when (this) {
        is PrismaSchemaParameter -> PrismaIcons.PARAMETER

        is PrismaSchemaDeclaration -> when (kind) {
            PrismaSchemaKind.PRIMITIVE_TYPE -> PrismaIcons.TYPE
            PrismaSchemaKind.GENERATOR_FIELD, PrismaSchemaKind.DATASOURCE_FIELD -> PrismaIcons.KEY_VALUE
            PrismaSchemaKind.BLOCK_ATTRIBUTE, PrismaSchemaKind.FIELD_ATTRIBUTE -> PrismaIcons.ATTRIBUTE
            PrismaSchemaKind.FUNCTION -> PrismaIcons.FUNCTION
            else -> null
        }

        is PrismaSchemaVariant -> null
    }

