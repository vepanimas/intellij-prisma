package com.vepanimas.intellij.prisma.completion

import com.vepanimas.intellij.prisma.ide.schema.types.PrismaIndexAlgorithm
import com.vepanimas.intellij.prisma.ide.schema.types.PrismaReferentialAction
import com.vepanimas.intellij.prisma.ide.schema.types.PrismaSortOrder

class PrismaAttributeParameterValuesCompletionTest : PrismaCompletionTestBase() {
    fun testBlockAttributeIndexPostgreSQLType() {
        val lookupElements = getLookupElements(
            """
            datasource db {
              provider = "postgresql"
            }
            model M {
              @@index(type: <caret>)
            }
        """.trimIndent()
        )
        assertSameElements(lookupElements.strings, PrismaIndexAlgorithm.values().map { it.name })
    }

    fun testBlockAttributeIndexMySQLOnFieldSort() {
        val lookupElements = getLookupElements(
            """
            datasource db {
              provider = "mysql"
            }
            model M {
              id String
            
              @@index([id(sort: <caret>)])
            }
        """.trimIndent()
        )
        assertSameElements(
            lookupElements.strings,
            PrismaSortOrder.values().map { it.name }
        )
    }

    fun testBlockAttributeIndexSQLServerClustered() {
        val lookupElements = completeSelected(
            """
            datasource db {
              provider = "sqlserver"
            }
            model M {
              @@index(clustered: <caret>)
            }
        """.trimIndent(),
            """
            datasource db {
              provider = "sqlserver"
            }
            model M {
              @@index(clustered: true)
            }
        """.trimIndent(),
            "true"
        )
        assertSameElements(lookupElements.strings, "true", "false")
    }

    fun testFieldAttributeIdSQLServerClustered() {
        val lookupElements = getLookupElements(
            """
            datasource db {
              provider = "sqlserver"
            }
            model M {
              int Int @id(clustered: <caret>)
            }
        """.trimIndent()
        )
        assertSameElements(lookupElements.strings, "true", "false")
    }

    fun testFieldAttributeRelationSQLServerOnDelete() {
        val lookupElements = getLookupElements(
            """
            datasource db {
              provider = "sqlserver"
            }
            
            model Post {
              id       Int    @id @default(autoincrement())
              title    String
              author   User   @relation(fields: [authorId], references: [id], onDelete: <caret>, onUpdate: NoAction)
              authorId Int
            }
            
            model User {
              id    Int    @id @default(autoincrement())
              posts Post[]
            }
        """.trimIndent()
        )
        assertSameElements(
            lookupElements.strings,
            PrismaReferentialAction.Cascade.name,
            PrismaReferentialAction.NoAction.name,
            PrismaReferentialAction.SetDefault.name,
            PrismaReferentialAction.SetNull.name,
        )
    }

    fun testFieldAttributeRelationMySQLOnDelete() {
        val lookupElements = getLookupElements(
            """
            datasource db {
              provider = "mysql"
            }
            
            model Post {
              id       Int    @id @default(autoincrement())
              title    String
              author   User   @relation(fields: [authorId], references: [id], onDelete: <caret>, onUpdate: NoAction)
              authorId Int
            }
            
            model User {
              id    Int    @id @default(autoincrement())
              posts Post[]
            }
        """.trimIndent()
        )
        assertSameElements(
            lookupElements.strings,
            PrismaReferentialAction.Cascade.name,
            PrismaReferentialAction.NoAction.name,
            PrismaReferentialAction.Restrict.name,
            PrismaReferentialAction.SetDefault.name,
            PrismaReferentialAction.SetNull.name,
        )
    }
}