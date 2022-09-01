package com.vepanimas.intellij.prisma.completion

class PrismaKeywordCompletionTest : PrismaCompletionTestBase() {
    fun testRootKeywords() {
        val lookupElements = completeItems(
            """
                model M {}
                <caret>
                model M1 {}
            """.trimIndent(),
            """
                model M {}
                model <caret>
                model M1 {}
            """.trimIndent(),
            "model"
        )
        assertContainsElements(lookupElements.strings, "model", "datasource", "generator", "type")
    }

    fun testFinishRootKeyword() {
        complete(
            """
                model M {}
                ty<caret>
                model M1 {}
            """.trimIndent(),
            """
                model M {}
                type <caret>
                model M1 {}
            """.trimIndent()
        )
    }

    fun testNoKeywordsInDeclaration() {
        completeEmpty("model <caret>")
    }

    fun testNoKeywordsInBlock() {
        completeEmpty("model M { <caret> }")
    }

    fun testTypeKeywordDoc() {
        val lookupElement = completeItem("<caret>").find("type")
        completionDoc(lookupElement)
    }
}