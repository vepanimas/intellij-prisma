package com.vepanimas.intellij.prisma.lang

object PrismaConstants {
    object Types {
        const val STRING = "String"
        const val BOOLEAN = "Boolean"
        const val INT = "Int"
        const val FLOAT = "Float"
        const val DATETIME = "DateTime"
        const val JSON = "Json"
        const val BYTES = "Bytes"
        const val DECIMAL = "Decimal"
        const val BIGINT = "BigInt"
        const val UNSUPPORTED = "Unsupported"

        val PRIMITIVE = setOf(
            STRING, BOOLEAN, INT, FLOAT, DATETIME,
            JSON, BYTES, DECIMAL, BIGINT, UNSUPPORTED,
        )
    }

    object DatasourceTypes {
        const val MYSQL = "mysql"
        const val POSTGRESQL = "postgresql"
        const val SQLITE = "sqlite"
        const val SQLSERVER = "sqlserver"
        const val MONGODB = "mongodb"
        const val COCKROACHDB = "cockroachdb"

        val ALL = setOf(MYSQL, POSTGRESQL, SQLITE, SQLSERVER, MONGODB, COCKROACHDB)
    }

    object BlockAttributes {
        const val ID = "@@id"
        const val MAP = "@@map"
        const val UNIQUE = "@@unique"
        const val INDEX = "@@index"
        const val FULLTEXT = "@@fulltext"
        const val IGNORE = "@@ignore"

        val ALL = setOf(ID, MAP, UNIQUE, INDEX, FULLTEXT, IGNORE)
    }

    object FieldAttributes {
        const val ID = "@id"
        const val MAP = "@map"
        const val UNIQUE = "@unique"
        const val DEFAULT = "@default"
        const val RELATION = "@relation"
        const val UPDATED_AT = "@updatedAt"
        const val IGNORE = "@ignore"

        val ALL = setOf(ID, MAP, UNIQUE, DEFAULT, RELATION, UPDATED_AT, IGNORE)
    }

    object PreviewFeatures {
        const val FULL_TEXT_INDEX = "fulltextindex"
    }

    object DatasourceFields {
        const val PROVIDER = "provider"
        const val URL = "url"
    }

    object ParameterNames {
        const val FIELDS = "fields"
        const val NAME = "name"
        const val MAP = "map"
        const val REFERENCES = "references"
        const val EXPRESSION = "expression"
        const val SORT = "sort"
        const val CLUSTERED = "clustered"
        const val LENGTH = "length"
        const val ON_DELETE = "onDelete"
        const val ON_UPDATE = "onUpdate"
    }
}