package com.vepanimas.intellij.prisma

import com.intellij.testFramework.ParsingTestCase
import com.vepanimas.intellij.prisma.lang.parser.PrismaParserDefinition

class PrismaParserTest: ParsingTestCase(
    "parser",
    "prisma",
    PrismaParserDefinition()
) {
    override fun getTestDataPath(): String = "src/test/testData"
    
    fun testPrismaFile() = doTest(true, true)
}