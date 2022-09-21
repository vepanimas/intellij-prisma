package com.vepanimas.intellij.prisma.completion

import com.intellij.openapi.util.text.StringUtil
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaKind
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaProvider
import com.vepanimas.intellij.prisma.lang.PrismaConstants

class PrismaValuesCompletionTest : PrismaCompletionTestBase() {
    override fun getBasePath(): String = "/completion/values"

    fun testDatasourceProvider() {
        val lookupElements = completeSelected(
            """
                datasource db {
                  provider = <caret>
                }
            """.trimIndent(),
            """
                datasource db {
                  provider = "sqlserver"
                }
            """.trimIndent(),
            "\"sqlserver\""
        )
        val element = PrismaSchemaProvider.getSchema()
            .getElement(PrismaSchemaKind.DATASOURCE_FIELD, PrismaConstants.DatasourceFields.PROVIDER)!!
        assertSameElements(lookupElements.strings, element.variants.map { StringUtil.wrapWithDoubleQuote(it.label) })
        checkLookupDocumentation(lookupElements, "\"sqlserver\"")
    }

    fun testDatasourceProviderInLiteral() {
        completeSelected(
            """
                datasource db {
                  provider = "sql<caret>"
                }
            """.trimIndent(),
            """
                datasource db {
                  provider = "sqlite"
                }
            """.trimIndent(),
            "sqlite"
        )
    }
}