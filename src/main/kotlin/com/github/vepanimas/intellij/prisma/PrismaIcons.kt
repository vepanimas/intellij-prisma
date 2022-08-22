package com.github.vepanimas.intellij.prisma

import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

object PrismaIcons {

    val PRISMA = load("/icons/prisma.svg")

    private fun load(path: String): Icon = IconLoader.getIcon(path, PrismaIcons::class.java)

}