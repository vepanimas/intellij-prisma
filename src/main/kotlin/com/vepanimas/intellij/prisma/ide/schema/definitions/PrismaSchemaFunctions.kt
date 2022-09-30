package com.vepanimas.intellij.prisma.ide.schema.definitions

import com.vepanimas.intellij.prisma.ide.completion.PrismaInsertHandler
import com.vepanimas.intellij.prisma.ide.schema.PrismaDatasourceType
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaKind
import com.vepanimas.intellij.prisma.ide.schema.schema
import com.vepanimas.intellij.prisma.lang.PrismaConstants.Functions
import com.vepanimas.intellij.prisma.lang.PrismaConstants.PrimitiveTypes
import com.vepanimas.intellij.prisma.lang.psi.PrismaPsiPatterns
import com.vepanimas.intellij.prisma.lang.psi.getDatasourceTypeForElement
import com.vepanimas.intellij.prisma.lang.types.PrismaBigIntType
import com.vepanimas.intellij.prisma.lang.types.PrismaDateTimeType
import com.vepanimas.intellij.prisma.lang.types.PrismaIntType
import com.vepanimas.intellij.prisma.lang.types.PrismaStringType
import java.util.*

val PRISMA_SCHEMA_FUNCTIONS = schema {
    group(PrismaSchemaKind.FUNCTION) {
        element {
            label = Functions.ENV
            insertHandler = PrismaInsertHandler.PARENS_QUOTED_ARGUMENT
            documentation =
                "Specifies a datasource via an environment variable. When running a Prisma CLI command that needs the database connection URL (e.g. `prisma generate`), you need to make sure that the `DATABASE_URL` environment variable is set. One way to do so is by creating a `.env` file. Note that the file must be in the same directory as your schema.prisma file to automatically be picked up by the Prisma CLI."

            param {
                label = "environmentVariable"
                documentation = "The environment variable in which the database connection URL is stored."
                type = PrimitiveTypes.STRING
                skipInCompletion = true

                variant {
                    label = "DATABASE_URL"
                    type = PrimitiveTypes.STRING
                }
            }
        }

        element {
            label = Functions.DBGENERATED
            documentation =
                "The SQL definition of the default value which is generated by the database. This is not validated by Prisma."
            insertHandler = PrismaInsertHandler.PARENS_QUOTED_ARGUMENT
            datasources = PrismaDatasourceType.except(PrismaDatasourceType.MONGODB)

            param {
                label = "value"
                type = PrimitiveTypes.STRING
                skipInCompletion = true
            }
        }

        element {
            label = Functions.AUTO
            documentation = "Represents default values that are automatically generated by the database."
            datasources = EnumSet.of(PrismaDatasourceType.MONGODB)
        }

        element {
            label = Functions.SEQUENCE
            documentation =
                "Create a sequence of integers in the underlying database and assign the incremented values to the ID values of the created records based on the sequence."
            datasources = EnumSet.of(PrismaDatasourceType.COCKROACHDB)
            pattern = PrismaPsiPatterns.withFieldType { type, _ -> type is PrismaIntType || type is PrismaBigIntType }

            param {
                label = "virtual"
                documentation =
                    "Virtual sequences are sequences that do not generate monotonically increasing values and instead produce values like those generated by the built-in function unique_rowid(). They are intended for use in combination with SERIAL-typed columns."
                skipInCompletion = true

                variant {
                    label = "virtual"
                    documentation =
                        "Virtual sequences are sequences that do not generate monotonically increasing values and instead produce values like those generated by the built-in function unique_rowid(). They are intended for use in combination with SERIAL-typed columns."
                }
            }

            param {
                label = "minValue"
                documentation = "The new minimum value of the sequence."
            }

            param {
                label = "maxValue"
                documentation = "The new maximum value of the sequence."
            }

            param {
                label = "cache"
                documentation =
                    "The number of sequence values to cache in memory for reuse in the session. A cache size of 1 means that there is no cache, and cache sizes of less than 1 are not valid."
            }

            param {
                label = "increment"
                documentation =
                    "The new value by which the sequence is incremented. A negative number creates a descending sequence. A positive number creates an ascending sequence."
            }

            param {
                label = "start"
                documentation =
                    "The value the sequence starts at if you RESTART or if the sequence hits the MAXVALUE and CYCLE is set."
            }
        }

        element {
            label = Functions.AUTOINCREMENT
            documentation =
                "Create a sequence of integers in the underlying database and assign the incremented values to the ID values of the created records based on the sequence."
            pattern = PrismaPsiPatterns.withFieldType { type, element ->
                type is PrismaIntType && getDatasourceTypeForElement(element) != PrismaDatasourceType.COCKROACHDB ||
                        type is PrismaBigIntType
            }
        }

        element {
            label = Functions.NOW
            documentation = "Set a timestamp of the time when a record is created."
            pattern = PrismaPsiPatterns.withFieldType { type, _ -> type is PrismaDateTimeType }
        }

        element {
            label = Functions.UUID
            documentation =
                "Generate a globally unique identifier based on the [UUID](https://en.wikipedia.org/wiki/Universally_unique_identifier) spec."
            pattern = PrismaPsiPatterns.withFieldType { type, _ -> type is PrismaStringType }
        }

        element {
            label = Functions.CUID
            documentation =
                "Generate a globally unique identifier based on the [cuid](https://github.com/ericelliott/cuid) spec."
            pattern = PrismaPsiPatterns.withFieldType { type, _ -> type is PrismaStringType }
        }
    }
}