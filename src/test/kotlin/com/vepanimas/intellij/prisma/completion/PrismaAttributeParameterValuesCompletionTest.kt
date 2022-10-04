package com.vepanimas.intellij.prisma.completion

import com.vepanimas.intellij.prisma.ide.schema.types.PrismaIndexAlgorithm
import com.vepanimas.intellij.prisma.ide.schema.types.PrismaOperatorClass
import com.vepanimas.intellij.prisma.ide.schema.types.PrismaReferentialAction
import com.vepanimas.intellij.prisma.ide.schema.types.PrismaSortOrder
import com.vepanimas.intellij.prisma.lang.PrismaConstants

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

    fun testBlockAttributeIndexOpsNativeType() {
        val lookupElements = getLookupElements(
            """
            datasource db {
              provider = "postgresql"
            }
            model M {
              id String @db.Char
            
              @@index([id(ops: <caret>)], type: Brin)
            }
        """.trimIndent()
        )
        assertSameElements(
            lookupElements.strings,
            PrismaConstants.Functions.RAW,
            PrismaOperatorClass.BpcharBloomOps.name,
            PrismaOperatorClass.BpcharMinMaxOps.name,
        )
    }

    fun testBlockAttributeIndexOpsFieldType() {
        val lookupElements = getLookupElements(
            """
            datasource db {
              provider = "postgresql"
            }
            model M {
              id Float
            
              @@index([id(ops: <caret>)], type: Brin)
            }
        """.trimIndent()
        )
        assertSameElements(
            lookupElements.strings,
            PrismaConstants.Functions.RAW,
            PrismaOperatorClass.Float8BloomOps.name,
            PrismaOperatorClass.Float8MinMaxOps.name,
            PrismaOperatorClass.Float8MinMaxMultiOps.name,
        )
    }

    fun testBlockAttributeIndexOpsFieldTypeOptional() {
        val lookupElements = getLookupElements(
            """
            datasource db {
              provider = "postgresql"
            }
            model M {
              id Int?
            
              @@index([id(ops: <caret>)], type: Brin)
            }
        """.trimIndent()
        )
        assertSameElements(
            lookupElements.strings,
            PrismaConstants.Functions.RAW,
            PrismaOperatorClass.Int4BloomOps.name,
            PrismaOperatorClass.Int4MinMaxOps.name,
            PrismaOperatorClass.Int4MinMaxMultiOps.name,
        )
    }

    fun testBlockAttributeIndexOpsFieldTypeList() {
        val lookupElements = getLookupElements(
            """
            datasource db {
              provider = "postgresql"
            }
            model M {
              id Int[]
            
              @@index([id(ops: <caret>)], type: Gin)
            }
        """.trimIndent()
        )
        assertSameElements(
            lookupElements.strings,
            PrismaConstants.Functions.RAW,
            PrismaOperatorClass.ArrayOps.name,
        )
    }

    fun testBlockAttributeIndexFields() {
        val lookupElements = getLookupElements(
            """
            datasource db {
              provider = "postgresql"
            }
            model Post {
              id        Int      @id
              name      String
              createdAt DateTime
              isDeleted Boolean
            
              @@index([name, createdAt(), <caret>])
            }
        """.trimIndent()
        )
        assertSameElements(lookupElements.strings, "id", "isDeleted")
    }

    fun testFieldAttributeRelationReferences() {
        val lookupElements = getLookupElements(
            """
            model Post {
              id       Int    @id @default(autoincrement())
              title    String
              author   User   @relation(fields: [authorId], references: [<caret>])
              authorId Int
            }
            
            model User {
              id        Int      @id @default(autoincrement())
              posts     Post[]
              name      String
              createdAt DateTime
            }
        """.trimIndent()
        )
        assertSameElements(
            lookupElements.strings,
            "id", "posts", "name", "createdAt"
        )
    }

    fun testFieldAttributeRelationReferencesNoEnumValues() {
        val lookupElements = getLookupElements(
            """
            model Post {
              id       Int    @id @default(autoincrement())
              title    String
              author   Lang   @relation(fields: [authorId], references: [<caret>])
              authorId Int
            }
            
            enum Lang {
              EN
              FR
            }
        """.trimIndent()
        )
        assertEmpty(lookupElements.strings)
    }
}