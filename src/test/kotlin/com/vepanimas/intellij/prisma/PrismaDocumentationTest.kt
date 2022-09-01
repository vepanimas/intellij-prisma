package com.vepanimas.intellij.prisma

import com.intellij.codeInsight.documentation.DocumentationManager

class PrismaDocumentationTest : PrismaTestCase() {
    override fun getBasePath(): String = "/documentation"

    fun testModel() {
        doTest()
    }

    fun testKeyword() {
        doTest()
    }

    fun testKeywordType() {
        doTest()
    }

    fun testKeywordTypeInAlias() {
        doTest()
    }

    private fun doTest() {
        val file = myFixture.configureByFile(getTestName())
        val originalElement = file.findElementAt(myFixture.caretOffset)
        val element = DocumentationManager.getInstance(myFixture.project)
            .findTargetElement(myFixture.editor, file, originalElement)
        val provider = DocumentationManager.getProviderFromElement(element, originalElement)
        val doc = provider.generateDoc(element, originalElement) ?: "<empty>"
        assertSameLinesWithFile("${testDataPath}/${getTestName("html")}", doc)
    }
}