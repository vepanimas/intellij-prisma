package com.vepanimas.intellij.prisma.lang.psi

interface PrismaArgumentsOwner : PrismaElement {
    fun getArgumentsList(): PrismaArgumentsList?
}