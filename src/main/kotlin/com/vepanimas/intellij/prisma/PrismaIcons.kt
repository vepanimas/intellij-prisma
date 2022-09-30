package com.vepanimas.intellij.prisma

import com.intellij.icons.AllIcons
import com.intellij.openapi.util.IconLoader
import com.intellij.util.PlatformIcons
import javax.swing.Icon

object PrismaIcons {

    val PRISMA = load("/icons/prisma.svg")

    val PARAMETER: Icon = PlatformIcons.PARAMETER_ICON
    val FIELD: Icon = PlatformIcons.FIELD_ICON
    val ATTRIBUTE: Icon = PlatformIcons.ANNOTATION_TYPE_ICON
    val TYPE = AllIcons.Nodes.Type
    val ENUM: Icon = PlatformIcons.ENUM_ICON
    val FUNCTION: Icon = PlatformIcons.FUNCTION_ICON
    val KEY_VALUE: Icon = AllIcons.Json.Object
    val ALIAS: Icon = AllIcons.Nodes.ObjectTypeAttribute

    private fun load(path: String): Icon = IconLoader.getIcon(path, PrismaIcons::class.java)

}