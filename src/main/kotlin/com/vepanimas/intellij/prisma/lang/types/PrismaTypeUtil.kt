package com.vepanimas.intellij.prisma.lang.types

fun PrismaType.unwrapType(): PrismaType {
    if (this is PrismaDecoratedType) {
        return this.unwrap()
    }
    return this
}

val PrismaType.name: String?
    get() = when (val underlyingType = unwrapType()) {
        is PrismaPrimitiveType -> underlyingType.name
        is PrismaTypeImpl -> underlyingType.name
        else -> null
    }

fun parseTypeName(type: String?): String? {
    return type?.removeSuffix("?")?.removeSuffix("[]")
}

fun isNamedType(type: String?, expected: String): Boolean {
    return parseTypeName(type) == expected
}

fun isListType(type: String?): Boolean {
    return type?.contains("[]") ?: false
}

fun isOptionalType(type: String?): Boolean {
    return type?.contains("?") ?: false
}