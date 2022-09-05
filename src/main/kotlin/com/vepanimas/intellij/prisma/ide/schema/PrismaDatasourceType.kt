package com.vepanimas.intellij.prisma.ide.schema

import com.vepanimas.intellij.prisma.lang.PrismaConstants.DatasourceTypes

enum class PrismaDatasourceType(val label: String) {
    MYSQL(DatasourceTypes.MYSQL),
    POSTGRESQL(DatasourceTypes.POSTGRESQL),
    SQLITE(DatasourceTypes.SQLITE),
    SQLSERVER(DatasourceTypes.SQLSERVER),
    MONGODB(DatasourceTypes.MONGODB),
    COCKROACHDB(DatasourceTypes.COCKROACHDB);

    companion object {
        fun fromString(s: String?): PrismaDatasourceType? =
            PrismaDatasourceType.values().find { it.label == s }
    }
}