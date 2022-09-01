package com.vepanimas.intellij.prisma.completion

import com.intellij.codeInsight.CodeInsightSettings
import com.intellij.codeInsight.lookup.Lookup
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementPresentation
import com.intellij.testFramework.UsefulTestCase
import com.vepanimas.intellij.prisma.PrismaTestCase
import com.vepanimas.intellij.prisma.ide.documentation.PrismaDocumentationProvider
import com.vepanimas.intellij.prisma.lang.PrismaFileType
import junit.framework.TestCase

abstract class PrismaCompletionTestBase : PrismaTestCase() {
    override fun getTestDataPath(): String {
        return super.getTestDataPath() + "/completion"
    }

    protected fun completeItems(source: String, expected: String, selected: String? = null): Array<out LookupElement> {
        return withoutAutoCompletion {
            myFixture.configureByText(PrismaFileType, source)
            val lookupElements = myFixture.completeBasic()
            TestCase.assertNotNull(lookupElements)
            if (selected != null) {
                val selectedItem = myFixture.lookupElements?.find { it.lookupString == selected }
                TestCase.assertNotNull(selectedItem)
                myFixture.lookup.currentItem = selectedItem
                myFixture.finishLookup(Lookup.NORMAL_SELECT_CHAR)
                myFixture.checkResult(expected)
            }
            lookupElements
        }
    }

    protected fun completeItem(source: String): Array<out LookupElement> {
        return completeItems(source, source)
    }

    protected fun complete(source: String, expected: String) {
        myFixture.configureByText(PrismaFileType, source)
        myFixture.completeBasic()
        myFixture.checkResult(expected)
    }

    protected fun completeEmpty(source: String) {
        UsefulTestCase.assertSize(0, completeItems(source, source))
    }

    protected fun completionDoc(lookupElement: LookupElement?) {
        val element = lookupElement?.psiElement
        assertNotNull(element)
        val doc = PrismaDocumentationProvider().generateDoc(element!!, null)
        assertNotNull(doc)
        assertSameLinesWithFile("${testDataPath}/${getTestName("html")}", doc!!)
    }

    protected fun <T> withoutAutoCompletion(block: () -> T): T {
        val before = CodeInsightSettings.getInstance().AUTOCOMPLETE_ON_CODE_COMPLETION
        CodeInsightSettings.getInstance().AUTOCOMPLETE_ON_CODE_COMPLETION = false
        try {
            return block()
        } finally {
            CodeInsightSettings.getInstance().AUTOCOMPLETE_ON_CODE_COMPLETION = before
        }
    }

    protected val Array<out LookupElement>?.strings: List<String>
        get() = this?.map { it.lookupString } ?: emptyList()

    protected fun Array<out LookupElement>?.find(lookupString: String): LookupElement? =
        this?.find { it.lookupString == lookupString }

    protected val LookupElement?.presentation: LookupElementPresentation?
        get() = if (this == null) {
            null
        } else {
            LookupElementPresentation().also { renderElement(it) }
        }
}