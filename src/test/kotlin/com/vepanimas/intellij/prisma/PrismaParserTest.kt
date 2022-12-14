package com.vepanimas.intellij.prisma

import com.intellij.testFramework.ParsingTestCase
import com.vepanimas.intellij.prisma.lang.parser.PrismaParserDefinition

class PrismaParserTest : ParsingTestCase(
    "parser",
    "prisma",
    true,
    PrismaParserDefinition()
) {
    override fun getTestDataPath(): String = PrismaTestCase.relativeTestDataPath

    fun testPrismaFile() = doTest(true, true)

    fun testUnsupportedType() = doTest(true, false)

    fun testUnsupportedTypeInAlias() = doTest(true, true)

    fun testPathExpression() = doTest(true, true)
}