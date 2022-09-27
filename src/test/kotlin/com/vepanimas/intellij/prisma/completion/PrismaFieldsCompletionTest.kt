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
        assertSameElements(lookupElements.strings, "provider", "url", "shadowDatabaseUrl")
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
        assertSameElements(
            lookupElements.strings,
            "provider",
            "output",
            "binaryTargets",
            "previewFeatures",
            "engineType"
        )
        checkLookupDocumentation(lookupElements, "provider")
    }

    fun testGeneratorPreviewFeatures() {
        val lookupElements = completeSelected(
            """
            generator client {
              provider = "prisma-client-js"
              <caret>
              engineType = "binary"
            }
        """.trimIndent(), """
            generator client {
              provider = "prisma-client-js"
              previewFeatures = [<caret>]
              engineType = "binary"
            }
        """.trimIndent(),
            "previewFeatures"
        )
        assertSameElements(lookupElements.strings, "output", "binaryTargets", "previewFeatures")
    }
}