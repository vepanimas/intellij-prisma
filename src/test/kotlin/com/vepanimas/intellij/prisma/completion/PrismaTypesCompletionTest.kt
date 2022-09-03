package com.vepanimas.intellij.prisma.completion

import com.vepanimas.intellij.prisma.lang.PrismaConstants

class PrismaTypesCompletionTest : PrismaCompletionTestBase() {
    override fun getBasePath(): String = "/completion/types"

    fun testPrimitiveTypes() {
        val lookupElements = completeSelected(
            """
                model M {
                  id <caret>
                }
            """.trimIndent(),
            """
                model M {
                  id DateTime<caret>
                }
            """.trimIndent(),
            "DateTime"
        )
        assertSameElements(lookupElements.strings, PrismaConstants.Types.PRIMITIVE)
        checkLookupDocumentation(lookupElements, "DateTime")
    }

    fun testUnsupportedType() {
        val lookupElements = completeSelected(
            """
                model M {
                  id Uns<caret>
                }
            """.trimIndent(),
            """
                model M {
                  id Unsupported("<caret>")
                }
            """.trimIndent(),
            "Unsupported"
        )
        checkLookupDocumentation(lookupElements, "Unsupported")
    }
}