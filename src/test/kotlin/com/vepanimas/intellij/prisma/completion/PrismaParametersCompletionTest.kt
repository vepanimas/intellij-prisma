package com.vepanimas.intellij.prisma.completion

import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaKind
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaProvider
import com.vepanimas.intellij.prisma.lang.PrismaConstants.BlockAttributes
import com.vepanimas.intellij.prisma.lang.PrismaConstants.FieldAttributes
import com.vepanimas.intellij.prisma.lang.PrismaConstants.ParameterNames
import junit.framework.TestCase

class PrismaParametersCompletionTest : PrismaCompletionTestBase() {
    override fun getBasePath(): String = "/completion/parameters"

    fun testBlockAttributeId() {
        val lookupElements = completeSelected(
            """
            model M {
              @@id(<caret>)
            }
        """.trimIndent(), """
            model M {
              @@id(fields: [<caret>])
            }
        """.trimIndent(),
            ParameterNames.FIELDS
        )
        val expectedParams = getBlockAttributeParams(BlockAttributes.ID)
        assertSameElements(lookupElements.strings, expectedParams)
        checkLookupDocumentation(lookupElements, ParameterNames.FIELDS)

        val presentation = lookupElements.find(ParameterNames.FIELDS).presentation!!
        TestCase.assertEquals("FieldReference[]", presentation.typeText)
    }

    fun testBlockAttributeIdFiltered() {
        val lookupElements = completeSelected(
            """
            model M {
              @@id(fields: [], <caret>)
            }
        """.trimIndent(), """
            model M {
              @@id(fields: [], map: "<caret>")
            }
        """.trimIndent(),
            ParameterNames.MAP
        )
        val expectedParams = getBlockAttributeParams(BlockAttributes.ID)
        assertSameElements(lookupElements.strings, expectedParams - ParameterNames.FIELDS)
        checkLookupDocumentation(lookupElements, ParameterNames.MAP)
    }

    fun testBlockAttributeIdComplete() {
        completeBasic(
            """
            model M {
              @@id(fie<caret>)
            }
        """.trimIndent(),
            """
            model M {
              @@id(fields: [<caret>])
            }
        """.trimIndent(),
        )
    }

    fun testBlockAttributeIdNoParamsInList() {
        val lookupElements = getLookupElements(
            """
            model M {
              @@id(fields: [<caret>])
            }
        """.trimIndent()
        )
        val expectedParams = getBlockAttributeParams(BlockAttributes.ID)
        assertDoesntContain(lookupElements.strings, expectedParams)
    }

    fun testBlockAttributeIndexPostgreSQL() {
        val lookupElements = getLookupElements(
            """
            datasource db {
              provider = "postgresql"
            }
            model M {
              @@index(<caret>)
            }
        """.trimIndent()
        )
        assertSameElements(lookupElements.strings, ParameterNames.FIELDS, ParameterNames.MAP, ParameterNames.TYPE)
    }

    fun testBlockAttributeIndexMySQL() {
        val lookupElements = getLookupElements(
            """
            datasource db {
              provider = "mysql"
            }
            model M {
              @@index(<caret>)
            }
        """.trimIndent()
        )
        assertSameElements(lookupElements.strings, ParameterNames.FIELDS, ParameterNames.MAP)
    }

    fun testFieldAttributeRelation() {
        val lookupElements = completeSelected(
            """
            datasource db {
              provider = "mysql"
            }
            model M {
              user User @relation(<caret>)
            }
        """.trimIndent(), """
            datasource db {
              provider = "mysql"
            }
            model M {
              user User @relation(fields: [<caret>])
            }
        """.trimIndent(),
            ParameterNames.FIELDS
        )
        val expectedParams = getFieldAttributeParams(FieldAttributes.RELATION)
        assertSameElements(lookupElements.strings, expectedParams)
        checkLookupDocumentation(lookupElements, ParameterNames.FIELDS)
    }

    fun testFieldAttributeRelationOnUpdate() {
        val lookupElements = completeSelected(
            """
            datasource db {
              provider = "mysql"
            }
            model M {
              user User @relation(on<caret>)
            }
        """.trimIndent(), """
            datasource db {
              provider = "mysql"
            }
            model M {
              user User @relation(onUpdate: <caret>)
            }
        """.trimIndent(),
            ParameterNames.ON_UPDATE
        )
        checkLookupDocumentation(lookupElements, ParameterNames.ON_UPDATE)
    }

    fun testFieldAttributeRelationMongoDB() {
        val lookupElements = getLookupElements(
            """
            datasource db {
              provider = "mongodb"
            }
            model M {
              user User @relation(<caret>)
            }
        """.trimIndent()
        )
        assertDoesntContain(
            lookupElements.strings,
            ParameterNames.ON_UPDATE,
            ParameterNames.ON_DELETE,
            ParameterNames.MAP
        )
    }

    fun testFieldAttributeIdMySQL() {
        val lookupElements = getLookupElements(
            """
            datasource db {
              provider = "mysql"
            }
            model M {
              user User @id(<caret>)
            }
        """.trimIndent()
        )
        assertSameElements(lookupElements.strings, ParameterNames.LENGTH, ParameterNames.MAP)
    }

    fun testFieldAttributeIdSQLServer() {
        val lookupElements = getLookupElements(
            """
            datasource db {
              provider = "sqlserver"
            }
            model M {
              user User @id(<caret>)
            }
        """.trimIndent()
        )
        assertSameElements(lookupElements.strings, ParameterNames.MAP, ParameterNames.SORT, ParameterNames.CLUSTERED)
    }

    fun testFieldAttributeUniqueMySQL() {
        val lookupElements = getLookupElements(
            """
            datasource db {
              provider = "mysql"
            }
            model M {
              user User @unique(<caret>)
            }
        """.trimIndent()
        )
        assertSameElements(lookupElements.strings, ParameterNames.LENGTH, ParameterNames.MAP, ParameterNames.SORT)
    }

    fun testFieldAttributeUniqueSQLServer() {
        val lookupElements = getLookupElements(
            """
            datasource db {
              provider = "sqlserver"
            }
            model M {
              user User @unique(<caret>)
            }
        """.trimIndent()
        )
        assertSameElements(
            lookupElements.strings,
            ParameterNames.CLUSTERED,
            ParameterNames.MAP,
            ParameterNames.SORT
        )
    }

    private fun getBlockAttributeParams(label: String) = getAttributeParams(PrismaSchemaKind.BLOCK_ATTRIBUTE, label)

    private fun getFieldAttributeParams(label: String) = getAttributeParams(PrismaSchemaKind.FIELD_ATTRIBUTE, label)

    private fun getAttributeParams(kind: PrismaSchemaKind, label: String) =
        PrismaSchemaProvider.getSchema().getElement(kind, label)!!.params.map { it.label }
}