package com.vepanimas.intellij.prisma.completion

import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaKind
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaProvider
import com.vepanimas.intellij.prisma.lang.PrismaConstants.BlockAttributes
import com.vepanimas.intellij.prisma.lang.PrismaConstants.FieldAttributes
import com.vepanimas.intellij.prisma.lang.PrismaConstants.ParameterNames
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
            ParameterNames.FIELDS
        )
        val expectedParams = getBlockAttributeParams(BlockAttributes.ID)
        assertSameElements(lookupElements.strings, expectedParams)
        checkLookupDocumentation(lookupElements, ParameterNames.FIELDS)

        val presentation = lookupElements.find(ParameterNames.FIELDS).presentation!!
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
            ParameterNames.MAP
        )
        val expectedParams = getBlockAttributeParams(BlockAttributes.ID)
        assertSameElements(lookupElements.strings, expectedParams - ParameterNames.FIELDS)
        checkLookupDocumentation(lookupElements, ParameterNames.MAP)
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

    fun testFieldAttributeParam() {
        val lookupElements = completeSelected(
            """
            model M {
              user User @relation(<caret>)
            }
        """.trimIndent(), """
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

    fun testFieldAttributeParamOnUpdate() {
        val lookupElements = completeSelected(
            """
            model M {
              user User @relation(on<caret>)
            }
        """.trimIndent(), """
            model M {
              user User @relation(onUpdate: <caret>)
            }
        """.trimIndent(),
            ParameterNames.ON_UPDATE
        )
        checkLookupDocumentation(lookupElements, ParameterNames.ON_UPDATE)
    }

    private fun getBlockAttributeParams(label: String) = getAttributeParams(PrismaSchemaKind.BLOCK_ATTRIBUTE, label)

    private fun getFieldAttributeParams(label: String) = getAttributeParams(PrismaSchemaKind.FIELD_ATTRIBUTE, label)

    private fun getAttributeParams(kind: PrismaSchemaKind, label: String) =
        PrismaSchemaProvider.getSchema().getElement(kind, label)!!.params.map { it.label }
}