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
            Types.STRING, Types.BOOLEAN, Types.INT, Types.FLOAT, Types.DATETIME,
            Types.JSON, Types.BYTES, Types.DECIMAL, Types.BIGINT, Types.UNSUPPORTED,
        )
    }
}