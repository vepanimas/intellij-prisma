package com.vepanimas.intellij.prisma.lang.psi

import com.vepanimas.intellij.prisma.lang.types.PrismaType

interface PrismaTypeSignature : PrismaElement {
    val type: PrismaType
}