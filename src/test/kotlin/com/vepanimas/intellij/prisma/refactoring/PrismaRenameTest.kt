package com.vepanimas.intellij.prisma.refactoring

import com.vepanimas.intellij.prisma.PrismaTestCase

class PrismaRenameTest : PrismaTestCase() {

    override fun getBasePath(): String = "/rename"

    fun testModel() {
        myFixture.testRename("model.prisma", "model_after.prisma", "SuperUser")
    }
}