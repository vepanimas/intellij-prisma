package com.vepanimas.intellij.prisma.ide.schema.definitions

import com.vepanimas.intellij.prisma.ide.schema.PrismaIndexAlgorithm
import com.vepanimas.intellij.prisma.ide.schema.PrismaReferentialAction
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaParameter
import com.vepanimas.intellij.prisma.ide.schema.PrismaSortOrder
import com.vepanimas.intellij.prisma.lang.PrismaConstants.PrimitiveTypes
import com.vepanimas.intellij.prisma.lang.PrismaConstants.Types
import com.vepanimas.intellij.prisma.lang.types.parseTypeName

fun PrismaSchemaParameter.Builder.typeBasedVariants(type: String) {
    when (val typeName = parseTypeName(type)) {
        PrimitiveTypes.BOOLEAN -> booleanTypeValues(typeName)
        Types.SORT_ORDER -> sortOrderTypeValues(typeName)
        Types.INDEX_TYPE -> indexingAlgorithmTypeValues(typeName)
        Types.REFERENTIAL_ACTION -> referentialActionTypeValues(typeName)
    }
}

private fun PrismaSchemaParameter.Builder.booleanTypeValues(typeName: String) {
    variant {
        label = "true"
        type = typeName
    }
    variant {
        label = "false"
        type = typeName
    }
}

private fun PrismaSchemaParameter.Builder.sortOrderTypeValues(typeName: String) {
    PrismaSortOrder.values().forEach {
        variant {
            label = it.name
            type = typeName
        }
    }
}

private fun PrismaSchemaParameter.Builder.indexingAlgorithmTypeValues(typeName: String) {
    PrismaIndexAlgorithm.values().forEach {
        variant {
            label = it.name
            documentation = it.documentation
            type = typeName
        }
    }
}

private fun PrismaSchemaParameter.Builder.referentialActionTypeValues(typeName: String) {
    PrismaReferentialAction.values().forEach {
        variant {
            label = it.name
            documentation = it.documentation
            type = typeName
            datasources = it.datasources
        }
    }
}