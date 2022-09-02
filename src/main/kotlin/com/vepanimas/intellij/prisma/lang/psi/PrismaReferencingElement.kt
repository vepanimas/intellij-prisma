package com.vepanimas.intellij.prisma.lang.psi

interface PrismaReferencingElement : PrismaElement {
    val referenceText: String?
}