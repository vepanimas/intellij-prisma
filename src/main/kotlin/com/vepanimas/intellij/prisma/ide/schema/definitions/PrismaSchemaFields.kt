package com.vepanimas.intellij.prisma.ide.schema.definitions

import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaKind
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaRef
import com.vepanimas.intellij.prisma.ide.schema.schema
import com.vepanimas.intellij.prisma.lang.PrismaConstants.Functions
import com.vepanimas.intellij.prisma.lang.PrismaConstants.PrimitiveTypes

val PRISMA_SCHEMA_FIELDS = schema {
    group(PrismaSchemaKind.DATASOURCE_FIELD) {
        element {
            label = "provider"
            documentation =
                "Describes which datasource connector to use. Can be one of the following datasource providers: `postgresql`, `mysql`, `sqlserver`, `sqlite`, `mongodb` or `cockroachdb`."

            variant {
                label = "mysql"
                documentation =
                    "Specifies a MySQL datasource. Learn more about this connector [here](https://pris.ly/d/mysql-connector)."
                type = PrimitiveTypes.STRING
            }
            variant {
                label = "postgresql"
                documentation =
                    "Specifies a PostgreSQL datasource. Learn more about this connector [here](https://pris.ly/d/postgresql-connector)."
                type = PrimitiveTypes.STRING
            }
            variant {
                label = "sqlite"
                documentation =
                    "Specifies a SQLite datasource. Learn more about this connector [here](https://pris.ly/d/sqlite-connector)."
                type = PrimitiveTypes.STRING
            }
            variant {
                label = "sqlserver"
                documentation =
                    "Specifies a Microsoft SQL Server datasource. Learn more about this connector [here](https://pris.ly/d/sqlserver-connector)."
                type = PrimitiveTypes.STRING
            }
            variant {
                label = "mongodb"
                documentation =
                    "Specifies a MongoDB datasource. Learn more about this connector [here](https://pris.ly/d/mongodb-connector)."
                type = PrimitiveTypes.STRING
            }
            variant {
                label = "cockroachdb"
                documentation =
                    "Specifies a CockroachDB datasource. Learn more about this connector [here](https://pris.ly/d/cockroachdb-connector)."
                type = PrimitiveTypes.STRING
            }
        }
        element {
            label = "url"
            documentation =
                "Connection URL including authentication info. Each datasource provider documents the URL syntax. Most providers use the syntax provided by the database [learn more](https://pris.ly/d/connection-strings)."

            variant {
                ref = PrismaSchemaRef(PrismaSchemaKind.FUNCTION, Functions.ENV)
            }
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

            variant {
                label = "prisma-client-js"
                documentation = "Built-in generator."
                type = PrimitiveTypes.STRING
            }
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
            type = "String[]"

            variant {
                label = "ReferentialIntegrity"
                type = PrimitiveTypes.STRING
            }
            variant {
                label = "InteractiveTransactions"
                type = PrimitiveTypes.STRING
            }
            variant {
                label = "FullTextSearch"
                type = PrimitiveTypes.STRING
            }
            variant {
                label = "FullTextIndex"
                type = PrimitiveTypes.STRING
            }
            variant {
                label = "Tracing"
                type = PrimitiveTypes.STRING
            }
            variant {
                label = "Metrics"
                type = PrimitiveTypes.STRING
            }
            variant {
                label = "OrderByNulls"
                type = PrimitiveTypes.STRING
            }
            variant {
                label = "FilteredRelationCount"
                type = PrimitiveTypes.STRING
            }
        }
        element {
            label = "engineType"
            documentation = "Defines the query engine type for Prisma Client."

            variant {
                label = "library"
                documentation = "Node-API library. (Default)"
                type = PrimitiveTypes.STRING
            }
            variant {
                label = "binary"
                documentation = "Executable binary."
                type = PrimitiveTypes.STRING
            }
            variant {
                label = "dataproxy"
                documentation = "Prisma Data Proxy."
                type = PrimitiveTypes.STRING
            }
        }
    }
}