package com.vepanimas.intellij.prisma.completion

import com.vepanimas.intellij.prisma.lang.PrismaConstants

class PrismaAttributesCompletionTest : PrismaCompletionTestBase() {
    override fun getBasePath(): String = "/completion/attributes"

    fun testBlockAttributes() {
        val lookupElements = completeSelected(
            """
            model M {
              <caret>
            }
        """.trimIndent(), """
            model M {
              @@id([<caret>])
            }
        """.trimIndent(),
            "@@id"
        )
        assertSameElements(lookupElements.strings, PrismaConstants.BlockAttributes.ALL)
        checkLookupDocumentation(lookupElements, "@@id")
    }

    fun testBlockAttributesPrefixAt() {
        val lookupElements = completeSelected(
            """
            model M {
              @<caret>
            }
        """.trimIndent(), """
            model M {
              @@map("<caret>")
            }
        """.trimIndent(),
            "@@map"
        )
        assertSameElements(lookupElements.strings, PrismaConstants.BlockAttributes.ALL)
    }

    fun testBlockAttributesPrefixAtAfterField() {
        val lookupElements = completeSelected(
            """
            model M {
              id String
              
              @<caret>
            }
        """.trimIndent(), """
            model M {
              id String
              
              @@index([<caret>])
            }
        """.trimIndent(),
            "@@index"
        )
        assertSameElements(lookupElements.strings, PrismaConstants.BlockAttributes.ALL)
    }

    fun testBlockAttributesPrefixAtAt() {
        val lookupElements = completeSelected(
            """
            model M {
              @@<caret>
            }
        """.trimIndent(), """
            model M {
              @@unique([<caret>])
            }
        """.trimIndent(),
            "@@unique"
        )
        assertSameElements(lookupElements.strings, PrismaConstants.BlockAttributes.ALL)
    }

    fun testBlockAttributesPrefixAtAtAfterField() {
        val lookupElements = completeSelected(
            """
            model M {
              id String
              
              @@<caret>
            }
        """.trimIndent(), """
            model M {
              id String
              
              @@unique([<caret>])
            }
        """.trimIndent(),
            "@@unique"
        )
        assertSameElements(lookupElements.strings, PrismaConstants.BlockAttributes.ALL)
    }

    fun testBlockAttributesPrefixName() {
        completeBasic(
            """
            model M {
              @@ign<caret>
            }
        """.trimIndent(), """
            model M {
              @@ignore<caret>
            }
        """.trimIndent()
        )
    }

    fun testBlockAttributesFulltext() {
        completeBasic(
            """
            datasource db {
              provider = "mysql"
            }
            
            model M {
              @@full<caret>
            }
        """.trimIndent(), """
            datasource db {
              provider = "mysql"
            }
            
            model M {
              @@fulltext([<caret>])
            }
        """.trimIndent()
        )
    }

    fun testNoBlockAttributesInType() {
        val lookupElements = getLookupElements(
            """
            type M {
              <caret>
            }
        """.trimIndent()
        )
        assertDoesntContain(lookupElements.strings, PrismaConstants.BlockAttributes.ALL)
    }

    fun testNoBlockAttributesForField() {
        val lookupElements = getLookupElements(
            """
            model M {
              id String <caret>
            }
        """.trimIndent()
        )
        assertDoesntContain(lookupElements.strings, PrismaConstants.BlockAttributes.ALL)
    }

    fun testNoBlockAttributesForFieldAfterAt() {
        val lookupElements = getLookupElements(
            """
            model M {
              id String @<caret>
            }
        """.trimIndent()
        )
        assertDoesntContain(lookupElements.strings, PrismaConstants.BlockAttributes.ALL)
    }
}