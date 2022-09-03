package com.vepanimas.intellij.prisma.ide.schema.definitions

import com.vepanimas.intellij.prisma.ide.completion.PrismaInsertHandler
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaElementKind
import com.vepanimas.intellij.prisma.ide.schema.schema
import com.vepanimas.intellij.prisma.lang.psi.PrismaConstants.Types

val PRISMA_SCHEMA_PRIMITIVE_TYPES = schema {
    group(PrismaSchemaElementKind.PRIMITIVE_TYPE) {
        element {
            label = Types.STRING
            documentation = "Variable length text"
        }
        element {
            label = Types.BOOLEAN
            documentation = "True or false value"
        }
        element {
            label = Types.INT
            documentation = "Integer value"
        }
        element {
            label = Types.FLOAT
            documentation = "Floating point number"
        }
        element {
            label = Types.DATETIME
            documentation = "Timestamp"
        }
        element {
            label = Types.JSON
            documentation = "A JSON object"
        }
        element {
            label = Types.BYTES
        }
        element {
            label = Types.DECIMAL
            documentation = "Decimal value"
        }
        element {
            label = Types.BIGINT
            documentation = "Integer values that may be greater than 2^53"
        }
        element {
            label = Types.UNSUPPORTED
            documentation =
                "An arbitrary database column type, for which Prisma has no syntax. Fields of type `Unsupported` work with Prisma Migrate and introspection, but are not exposed in Prisma Client."
            signature = "Unsupported(name: String)"
            insertHandler = PrismaInsertHandler.UNSUPPORTED_TYPE

            param {
                label = "name"
                type = "String"
                documentation =
                    "Name of the column type as expected by the underlying database, e.g. Unsupported(\"GEOGRAPHY(POINT,4326)\"). This string is not validated by Prisma Migrate and will be used by Prisma Migrate to generate the DDL statements to evolve the database schema. Prisma Introspect will overwrite this when re-introspecting if the type does not match."
            }
        }
    }
}