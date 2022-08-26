package com.vepanimas.intellij.prisma

import com.intellij.testFramework.fixtures.BasePlatformTestCase

abstract class PrismaTestCase : BasePlatformTestCase() {
    final override fun getTestDataPath(): String = "$relativeTestDataPath$basePath"

    protected fun getTestName(ext: String = "prisma"): String = "${getTestName(true)}.$ext"

    companion object {
        val relativeTestDataPath get() = "src/test/testData"
    }
}
