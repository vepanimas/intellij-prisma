package com.vepanimas.intellij.prisma

import com.intellij.psi.formatter.FormatterTestCase

class PrismaFormatterTest : FormatterTestCase() {
    override fun getBasePath(): String = "/formatter"

    override fun getTestDataPath(): String = PrismaTestCase.relativeTestDataPath

    override fun getFileExtension(): String = "prisma"

    fun testIndents() {
        doTest()
    }

    fun testLineSpacing() {
        doTest()
    }
}