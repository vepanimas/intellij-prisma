package com.vepanimas.intellij.prisma.lang.psi.presentation

import com.intellij.navigation.ItemPresentation
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