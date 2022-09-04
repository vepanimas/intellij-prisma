package com.vepanimas.intellij.prisma.ide.schema.definitions

import com.vepanimas.intellij.prisma.ide.completion.PrismaInsertHandler
import com.vepanimas.intellij.prisma.ide.schema.PrismaDatasourceType
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaElementKind
import com.vepanimas.intellij.prisma.ide.schema.schema
import com.vepanimas.intellij.prisma.lang.PrismaConstants.BlockAttributes
import java.util.*

val PRISMA_SCHEMA_ATTRIBUTES = schema {
    group(PrismaSchemaElementKind.BLOCK_ATTRIBUTE) {
        element {
            label = BlockAttributes.MAP
            insertHandler = PrismaInsertHandler.PARENS_QUOTED_ARGUMENT
            documentation = "Maps a model name from the Prisma schema to a different table name."
            signature = "@@map(name: String)"

            param {
                label = "name"
                insertHandler = PrismaInsertHandler.COLON_QUOTED_ARGUMENT
                documentation = "The name of the target database table."
            }
        }
        element {
            label = BlockAttributes.ID
            insertHandler = PrismaInsertHandler.PARENS_LIST_ARGUMENT
            documentation = "Defines a multi-field ID on the model."
            signature = "@@id(fields: FieldReference[], name: String?, map: String?)"

            param {
                label = "fields"
                insertHandler = PrismaInsertHandler.COLON_LIST_ARGUMENT
                documentation = "A list of references."
            }
            param {
                label = "name"
                insertHandler = PrismaInsertHandler.COLON_QUOTED_ARGUMENT
                documentation = "Defines the name in your Prisma Client API."
            }
            param {
                label = "map"
                insertHandler = PrismaInsertHandler.COLON_QUOTED_ARGUMENT
                documentation = "Defines a custom name for the primary key in the database."
            }
        }
        element {
            label = BlockAttributes.UNIQUE
            insertHandler = PrismaInsertHandler.PARENS_LIST_ARGUMENT
            documentation = "Defines a compound unique constraint for the specified fields."
            signature = "@@unique(fields: FieldReference[], name: String?, map: String?)"

            param {
                label = "fields"
                insertHandler = PrismaInsertHandler.COLON_LIST_ARGUMENT
                documentation = "A list of references."
            }
            param {
                label = "name"
                insertHandler = PrismaInsertHandler.COLON_QUOTED_ARGUMENT
                documentation = "Defines the name in your Prisma Client API."
            }
            param {
                label = "map"
                insertHandler = PrismaInsertHandler.COLON_QUOTED_ARGUMENT
                documentation = "Defines a custom constraint name in the database."
            }
        }
        element {
            label = BlockAttributes.INDEX
            insertHandler = PrismaInsertHandler.PARENS_LIST_ARGUMENT
            documentation = "Defines an index on the model."
            signature = "@@index(fields: FieldReference[], map: String?)"

            param {
                label = "fields"
                insertHandler = PrismaInsertHandler.COLON_LIST_ARGUMENT
                documentation = "A list of references."
            }
            param {
                label = "map"
                insertHandler = PrismaInsertHandler.COLON_QUOTED_ARGUMENT
                documentation = "Defines a custom index name in the database."
            }
        }
        element {
            label = BlockAttributes.FULLTEXT
            insertHandler = PrismaInsertHandler.PARENS_LIST_ARGUMENT
            documentation = "Defines a full-text index on the model."
            signature = "@@fulltext(fields: FieldReference[], map: String?)"
            datasources = EnumSet.of(PrismaDatasourceType.MYSQL, PrismaDatasourceType.MONGODB)

            param {
                label = "fields"
                insertHandler = PrismaInsertHandler.COLON_LIST_ARGUMENT
                documentation = "A list of references."
            }
            param {
                label = "map"
                insertHandler = PrismaInsertHandler.COLON_QUOTED_ARGUMENT
                documentation = "Defines a custom index name in the database."
            }
        }
        element {
            label = BlockAttributes.IGNORE
            documentation =
                "A model with an `@@ignore` attribute can be kept in sync with the database schema using Prisma Migrate and Introspection, but won't be exposed in Prisma Client."
        }
    }
}