package com.vepanimas.intellij.prisma

import com.intellij.codeInsight.documentation.DocumentationManager
import com.intellij.testFramework.UsefulTestCase

class PrismaDocumentationTest : PrismaTestCase() {
    override fun getBasePath(): String = "/documentation"

    fun testModel() {
        doTest()
    }

    private fun doTest() {
        val file = myFixture.configureByFile(getTestName())
        val originalElement = file.findElementAt(myFixture.caretOffset)
        val element = DocumentationManager.getInstance(myFixture.project)
            .findTargetElement(myFixture.editor, file, originalElement)
        val provider = DocumentationManager.getProviderFromElement(element, originalElement)
        val doc = provider.generateDoc(element, originalElement)!!
        UsefulTestCase.assertSameLinesWithFile("${testDataPath}/${getTestName("html")}", doc)
    }
}