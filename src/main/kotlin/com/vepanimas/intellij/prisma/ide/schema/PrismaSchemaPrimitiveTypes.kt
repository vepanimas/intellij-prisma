package com.vepanimas.intellij.prisma.ide.schema

import com.intellij.codeInsight.completion.InsertHandler
import com.intellij.codeInsight.completion.util.ParenthesesInsertHandler
import com.intellij.openapi.editor.EditorModificationUtil
import com.vepanimas.intellij.prisma.lang.psi.PrismaConstants.Types
import com.vepanimas.intellij.prisma.lang.psi.PrismaElementTypes.TYPE_REFERENCE
import com.vepanimas.intellij.prisma.lang.psi.PrismaElementTypes.UNSUPPORTED_TYPE

val PRISMA_SCHEMA_PRIMITIVE_TYPES = schema {
    group(PrismaSchemaElementKind.PRIMITIVE_TYPE) {
        element {
            label = Types.STRING
            documentation = "Variable length text"
            elementType = TYPE_REFERENCE
        }
        element {
            label = Types.BOOLEAN
            documentation = "True or false value"
            elementType = TYPE_REFERENCE
        }
        element {
            label = Types.INT
            documentation = "Integer value"
            elementType = TYPE_REFERENCE
        }
        element {
            label = Types.FLOAT
            documentation = "Floating point number"
            elementType = TYPE_REFERENCE
        }
        element {
            label = Types.DATETIME
            documentation = "Timestamp"
            elementType = TYPE_REFERENCE
        }
        element {
            label = Types.JSON
            documentation = "A JSON object"
            elementType = TYPE_REFERENCE
        }
        element {
            label = Types.BYTES
            elementType = TYPE_REFERENCE
        }
        element {
            label = Types.DECIMAL
            documentation = "Decimal value"
            elementType = TYPE_REFERENCE
        }
        element {
            label = Types.BIGINT
            documentation = "Integer values that may be greater than 2^53"
            elementType = TYPE_REFERENCE
        }
        element {
            label = Types.UNSUPPORTED
            documentation =
                "An arbitrary database column type, for which Prisma has no syntax. Fields of type `Unsupported` work with Prisma Migrate and introspection, but are not exposed in Prisma Client."
            signature = "Unsupported(name: String)"
            elementType = UNSUPPORTED_TYPE
            insertHandler = InsertHandler { context, item ->
                ParenthesesInsertHandler.getInstance(true).handleInsert(context, item)
                EditorModificationUtil.insertStringAtCaret(context.editor, "\"\"", false, true, 1)
            }

            param {
                label = "name"
                type = "String"
                documentation =
                    "Name of the column type as expected by the underlying database, e.g. Unsupported(\"GEOGRAPHY(POINT,4326)\"). This string is not validated by Prisma Migrate and will be used by Prisma Migrate to generate the DDL statements to evolve the database schema. Prisma Introspect will overwrite this when re-introspecting if the type does not match."
            }
        }
    }
}