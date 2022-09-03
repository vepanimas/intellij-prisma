package com.vepanimas.intellij.prisma.completion

class PrismaKeywordCompletionTest : PrismaCompletionTestBase() {
    override fun getBasePath(): String = "/completion/keywords"

    fun testRootKeywords() {
        val lookupElements = completeSelected(
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
        assertSameElements(lookupElements.strings, "model", "datasource", "generator", "type", "enum")
    }

    fun testFinishRootKeyword() {
        completeBasic(
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
        noCompletion("model <caret>")
    }

    fun testNoKeywordsInBlock() {
        noCompletion("model M { <caret> }")
    }

    fun testTypeKeywordDoc() {
        checkLookupDocumentation(getLookupElements("<caret>").find("type"))
    }
}