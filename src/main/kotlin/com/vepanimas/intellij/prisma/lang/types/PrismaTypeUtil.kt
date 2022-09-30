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