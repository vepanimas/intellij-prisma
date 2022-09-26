package com.vepanimas.intellij.prisma.ide.schema.definitions

import com.vepanimas.intellij.prisma.ide.completion.PrismaInsertHandler
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaKind
import com.vepanimas.intellij.prisma.ide.schema.schema
import com.vepanimas.intellij.prisma.lang.PrismaConstants.Functions
import com.vepanimas.intellij.prisma.lang.PrismaConstants.PrimitiveTypes

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
    }
}