package com.vepanimas.intellij.prisma.completion

class PrismaFieldsCompletionTest : PrismaCompletionTestBase() {
    override fun getBasePath(): String = "/completion/fields"

    fun testDatasourceField() {
        val lookupElements = completeSelected(
            """
            datasource db {
              <caret>
            }
        """.trimIndent(), """
            datasource db {
              url = <caret>
            }
        """.trimIndent(),
            "url"
        )
        checkLookupDocumentation(lookupElements, "url")
    }
}