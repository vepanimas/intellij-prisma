package com.vepanimas.intellij.prisma.lang.psi

interface PrismaBlock : PrismaElement {
    fun getMembers(): List<PrismaElement>
}