package com.vepanimas.intellij.prisma.ide.schema.definitions

import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaElementKind
import com.vepanimas.intellij.prisma.ide.schema.schema

val PRISMA_SCHEMA_FIELDS = schema {
    group(PrismaSchemaElementKind.DATASOURCE_FIELD) {
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
}