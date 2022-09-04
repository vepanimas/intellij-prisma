package com.vepanimas.intellij.prisma.ide.schema

import com.vepanimas.intellij.prisma.lang.PrismaConstants.Datasource
import java.util.*

enum class PrismaDatasourceType(val label: String) {
    MYSQL(Datasource.MYSQL),
    POSTGRESQL(Datasource.POSTGRESQL),
    SQLITE(Datasource.SQLITE),
    SQLSERVER(Datasource.SQLSERVER),
    MONGODB(Datasource.MONGODB),
    COCKROACHDB(Datasource.COCKROACHDB);

    companion object {
        val ALL: Set<PrismaDatasourceType> =
            Collections.unmodifiableSet(EnumSet.allOf(PrismaDatasourceType::class.java))
    }
}