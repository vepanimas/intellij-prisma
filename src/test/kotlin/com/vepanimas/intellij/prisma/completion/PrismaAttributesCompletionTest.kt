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

    fun testBlockAttributesNoIdIfHasIdFieldAttribute() {
        val lookupElements = getLookupElements(
            """
            model M {
              id Int @id
              
              <caret>
            }
        """.trimIndent()
        )
        assertDoesntContain(lookupElements.strings, BlockAttributes.ID)
    }

    fun testBlockAttributesNoDuplicates() {
        val lookupElements = getLookupElements(
            """
            model M {
              id Int
              
              @@map("foo")
              @@unique([id])
              <caret>
            }
        """.trimIndent()
        )
        assertDoesntContain(lookupElements.strings, BlockAttributes.MAP, BlockAttributes.UNIQUE)
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
        assertSameElements(
            lookupElements.strings,
            FieldAttributes.ALL - FieldAttributes.ID - FieldAttributes.UPDATED_AT,
        )

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
        assertSameElements(
            lookupElements.strings,
            FieldAttributes.ALL - FieldAttributes.ID - FieldAttributes.UPDATED_AT - FieldAttributes.UNIQUE
        )
        checkLookupDocumentation(lookupElements, FieldAttributes.MAP)
    }

    fun testFieldAttributeForCompositeTypeField() {
        val lookupElements = getLookupElements(
            """
            type T {}
            model M {
              type T <caret>
            }
        """.trimIndent()
        )
        assertDoesntContain(lookupElements.strings, FieldAttributes.DEFAULT, FieldAttributes.RELATION)
        assertSameElements(lookupElements.strings, FieldAttributes.IGNORE, FieldAttributes.MAP, FieldAttributes.UNIQUE)
    }

    fun testFieldAttributeIdAllowedForEnum() {
        val lookupElements = getLookupElements(
            """
            enum Lang {}
            model M {
              lang Lang <caret>
            }
        """.trimIndent()
        )
        assertContainsElements(lookupElements.strings, FieldAttributes.ID)
    }

    fun testFieldAttributeIdAllowedForInt() {
        val lookupElements = getLookupElements(
            """
            model M {
              i Int <caret>
            }
        """.trimIndent()
        )
        assertContainsElements(lookupElements.strings, FieldAttributes.ID)
    }

    fun testFieldAttributeIdAllowedForString() {
        val lookupElements = getLookupElements(
            """
            model M {
              s String <caret>
            }
        """.trimIndent()
        )
        assertContainsElements(lookupElements.strings, FieldAttributes.ID)
    }

    fun testFieldAttributeNoIdForCompositeTypes() {
        val lookupElements = getLookupElements(
            """
            model M {
              m1 M1 <caret>
            }
            model M1 {
                id Int @id
            }
        """.trimIndent()
        )
        assertDoesntContain(lookupElements.strings, FieldAttributes.ID)
    }

    fun testFieldAttributeUpdatedAtForDateTime() {
        val lookupElements = getLookupElements(
            """
            model M {
              updated DateTime <caret>
            }
        """.trimIndent()
        )
        assertContainsElements(lookupElements.strings, FieldAttributes.UPDATED_AT)
    }

    fun testFieldAttributeNoIdIfBlockAttributeExists() {
        val lookupElements = getLookupElements(
            """
            model M {
              id Int <caret>
              
              @@id([id])
            }
        """.trimIndent()
        )
        assertDoesntContain(lookupElements.strings, FieldAttributes.ID)
    }
}