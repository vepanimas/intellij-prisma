package com.vepanimas.intellij.prisma.completion

import com.intellij.openapi.util.text.StringUtil
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaKind
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaProvider
import com.vepanimas.intellij.prisma.lang.PrismaConstants
import com.vepanimas.intellij.prisma.lang.PrismaConstants.Functions

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

    fun testDatasourceUrlFunction() {
        val lookupElements = completeSelected(
            """
                datasource db {
                  provider = "postgresql"
                  url = <caret>
                }
            """.trimIndent(),
            """
                datasource db {
                  provider = "postgresql"
                  url = env("<caret>")
                }
            """.trimIndent(),
            "env"
        )

        assertSameElements(lookupElements.strings, Functions.ENV)
        checkLookupDocumentation(lookupElements, Functions.ENV)
    }

    fun testDatasourceUrlFunctionArgs() {
        val item = StringUtil.wrapWithDoubleQuote("DATABASE_URL")
        val lookupElements = completeSelected(
            """
                datasource db {
                  provider = "postgresql"
                  url = env(<caret>)
                }
            """.trimIndent(),
            """
                datasource db {
                  provider = "postgresql"
                  url = env("DATABASE_URL")
                }
            """.trimIndent(),
            item
        )
        assertSameElements(lookupElements.strings, item)
    }

    fun testDatasourceUrlFunctionArgsInQuoted() {
        val item = "DATABASE_URL"
        completeSelected(
            """
                datasource db {
                  provider = "postgresql"
                  url = env("<caret>")
                }
            """.trimIndent(),
            """
                datasource db {
                  provider = "postgresql"
                  url = env("DATABASE_URL")
                }
            """.trimIndent(),
            item
        )
    }
}