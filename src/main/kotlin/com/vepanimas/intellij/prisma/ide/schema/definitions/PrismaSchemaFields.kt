package com.vepanimas.intellij.prisma.ide.schema.definitions

import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaKind
import com.vepanimas.intellij.prisma.ide.schema.schema

val PRISMA_SCHEMA_FIELDS = schema {
    group(PrismaSchemaKind.DATASOURCE_FIELD) {
        element {
            label = "provider"
            documentation =
                "Describes which datasource connector to use. Can be one of the following datasource providers: `postgresql`, `mysql`, `sqlserver`, `sqlite`, `mongodb` or `cockroachdb`."
        }
        element {
            label = "url"
            documentation =
                "Connection URL including authentication info. Each datasource provider documents the URL syntax. Most providers use the syntax provided by the database [learn more](https://pris.ly/d/connection-strings)."
        }
        element {
            label = "shadowDatabaseUrl"
            documentation =
                "Connection URL including authentication info to use for Migrate's [shadow database](https://pris.ly/d/migrate-shadow). Each datasource provider documents the URL syntax. Most providers use the syntax provided by the database."
        }
    }

    group(PrismaSchemaKind.GENERATOR_FIELD) {
        element {
            label = "provider"
            documentation =
                "Describes which generator to use. This can point to a file that implements a generator or specify a built-in generator directly."
        }
        element {
            label = "output"
            documentation =
                "Determines the location for the generated client [learn more](https://pris.ly/d/prisma-schema)"
        }
        element {
            label = "binaryTargets"
            documentation =
                "Specifies the OS on which the Prisma Client will run to ensure binary compatibility of the query engine."
        }
        element {
            label = "previewFeatures"
            documentation = "Enables preview feature flags."
        }
        element {
            label = "engineType"
            documentation = "Defines the query engine type for Prisma Client."
        }
    }
}