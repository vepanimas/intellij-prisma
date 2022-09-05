package com.vepanimas.intellij.prisma.ide.schema

import com.vepanimas.intellij.prisma.lang.PrismaConstants.DatasourceTypes
import kotlinx.collections.immutable.toImmutableSet
import java.util.EnumSet

enum class PrismaDatasourceType(val label: String) {
    MYSQL(DatasourceTypes.MYSQL),
    POSTGRESQL(DatasourceTypes.POSTGRESQL),
    SQLITE(DatasourceTypes.SQLITE),
    SQLSERVER(DatasourceTypes.SQLSERVER),
    MONGODB(DatasourceTypes.MONGODB),
    COCKROACHDB(DatasourceTypes.COCKROACHDB);

    companion object {
        private val ALL: Set<PrismaDatasourceType> = EnumSet.allOf(PrismaDatasourceType::class.java)

        fun fromString(s: String?): PrismaDatasourceType? =
            PrismaDatasourceType.values().find { it.label == s }

        fun except(vararg types: PrismaDatasourceType): Set<PrismaDatasourceType> {
            return ALL - types.toSet()
        }
    }
}