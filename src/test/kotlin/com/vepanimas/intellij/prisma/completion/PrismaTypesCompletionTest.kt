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
        assertSameElements(lookupElements.strings, PrismaConstants.PrimitiveTypes.ALL)
        checkLookupDocumentation(lookupElements, "DateTime")
    }

    fun testNoDecimalInMongo() {
        val lookupElements = getLookupElements(
            """
                datasource db { 
                  provider = "mongodb" 
                }
                model M {
                  id <caret>
                }
            """.trimIndent(),
        )
        assertDoesntContain(lookupElements.strings, PrismaConstants.PrimitiveTypes.DECIMAL)
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