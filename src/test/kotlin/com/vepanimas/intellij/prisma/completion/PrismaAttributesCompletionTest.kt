package com.vepanimas.intellij.prisma.completion

import com.vepanimas.intellij.prisma.lang.PrismaConstants.BlockAttributes
import com.vepanimas.intellij.prisma.lang.PrismaConstants.FieldAttributes

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
        assertSameElements(lookupElements.strings, BlockAttributes.ALL)
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
        assertSameElements(lookupElements.strings, BlockAttributes.ALL)
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
        assertSameElements(lookupElements.strings, BlockAttributes.ALL)
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
        assertSameElements(lookupElements.strings, BlockAttributes.ALL)
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
        assertSameElements(lookupElements.strings, BlockAttributes.ALL)
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

    fun testBlockAttributesNoFulltext() {
        val lookupElements = getLookupElements(
            """
            datasource db {
              provider = "postgresql"
            }
            
            model M {
              @@<caret>
            }
        """.trimIndent()
        )
        assertDoesntContain(lookupElements.strings, BlockAttributes.FULLTEXT)
    }

    fun testNoBlockAttributesInType() {
        val lookupElements = getLookupElements(
            """
            type M {
              <caret>
            }
        """.trimIndent()
        )
        assertDoesntContain(lookupElements.strings, BlockAttributes.ALL)
    }

    fun testNoBlockAttributesForField() {
        val lookupElements = getLookupElements(
            """
            model M {
              id String <caret>
            }
        """.trimIndent()
        )
        assertDoesntContain(lookupElements.strings, BlockAttributes.ALL)
    }

    fun testNoBlockAttributesForFieldAfterAt() {
        val lookupElements = getLookupElements(
            """
            model M {
              id String @<caret>
            }
        """.trimIndent()
        )
        assertDoesntContain(lookupElements.strings, BlockAttributes.ALL)
    }

    fun testFieldAttributes() {
        val lookupElements = completeSelected(
            """
            model User {
            }
            model M {
              user User <caret>
            }
        """.trimIndent(), """
            model User {
            }
            model M {
              user User @relation(<caret>)
            }
        """.trimIndent(),
            FieldAttributes.RELATION
        )
        assertSameElements(lookupElements.strings, FieldAttributes.ALL)
        checkLookupDocumentation(lookupElements, FieldAttributes.RELATION)
    }

    fun testFieldAttributeAfterAnother() {
        val lookupElements = completeSelected(
            """
            model M {
              user User @unique <caret>
            }
        """.trimIndent(), """
            model M {
              user User @unique @map("<caret>")
            }
        """.trimIndent(),
            FieldAttributes.MAP
        )
        assertSameElements(lookupElements.strings, FieldAttributes.ALL)
        checkLookupDocumentation(lookupElements, FieldAttributes.MAP)
    }
}