package com.vepanimas.intellij.prisma.completion

import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaKind
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaProvider
import com.vepanimas.intellij.prisma.lang.PrismaConstants.BlockAttributes
import com.vepanimas.intellij.prisma.lang.PrismaConstants.FieldNames
import junit.framework.TestCase

class PrismaParametersCompletionTest : PrismaCompletionTestBase() {
    override fun getBasePath(): String = "/completion/parameters"

    fun testBlockAttributeParam() {
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
            FieldNames.FIELDS
        )
        val expectedParams = getBlockAttributeParams(BlockAttributes.ID)
        assertSameElements(lookupElements.strings, expectedParams)
        checkLookupDocumentation(lookupElements, FieldNames.FIELDS)

        val presentation = lookupElements.find(FieldNames.FIELDS).presentation!!
        TestCase.assertEquals("FieldReference[]", presentation.typeText)
    }

    fun testBlockAttributeParamFiltered() {
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
            FieldNames.MAP
        )
        val expectedParams = getBlockAttributeParams(BlockAttributes.ID)
        assertSameElements(lookupElements.strings, expectedParams - FieldNames.FIELDS)
        checkLookupDocumentation(lookupElements, FieldNames.MAP)
    }

    fun testBlockAttributeParamComplete() {
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

    fun testBlockAttributeNoParamsInList() {
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

    private fun getBlockAttributeParams(label: String) = PrismaSchemaProvider.getSchema()
        .getElement(PrismaSchemaKind.BLOCK_ATTRIBUTE, label)!!.params.map { it.label }
}