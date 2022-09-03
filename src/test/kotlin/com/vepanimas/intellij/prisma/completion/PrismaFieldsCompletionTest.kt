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

    fun testGeneratorField() {
        val lookupElements = completeSelected(
            """
            generator client {
              <caret>
            }
        """.trimIndent(), """
            generator client {
              provider = <caret>
            }
        """.trimIndent(),
            "provider"
        )
        checkLookupDocumentation(lookupElements, "provider")
    }
}