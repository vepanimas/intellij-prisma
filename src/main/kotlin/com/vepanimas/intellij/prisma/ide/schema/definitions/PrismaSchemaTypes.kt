package com.vepanimas.intellij.prisma.ide.schema.definitions

import com.intellij.patterns.ElementPattern
import com.intellij.psi.PsiElement
import com.vepanimas.intellij.prisma.ide.schema.PrismaIndexAlgorithm
import com.vepanimas.intellij.prisma.ide.schema.PrismaReferentialAction
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaParameter
import com.vepanimas.intellij.prisma.ide.schema.PrismaSortOrder
import com.vepanimas.intellij.prisma.lang.PrismaConstants.PrimitiveTypes
import com.vepanimas.intellij.prisma.lang.PrismaConstants.Types
import com.vepanimas.intellij.prisma.lang.types.parseTypeName

fun PrismaSchemaParameter.Builder.typeBasedVariants(type: String) {
    when (parseTypeName(type)) {
        PrimitiveTypes.BOOLEAN -> booleanTypeValues()
        Types.SORT_ORDER -> sortOrderTypeValues()
        Types.INDEX_TYPE -> indexingAlgorithmTypeValues()
        Types.REFERENTIAL_ACTION -> referentialActionTypeValues()
    }
}

fun PrismaSchemaParameter.Builder.booleanTypeValues(elementPattern: ElementPattern<out PsiElement>? = null) {
    variant {
        label = "true"
        type = PrimitiveTypes.BOOLEAN
        pattern = elementPattern
    }
    variant {
        label = "false"
        type = PrimitiveTypes.BOOLEAN
        pattern = elementPattern
    }
}

private fun PrismaSchemaParameter.Builder.sortOrderTypeValues() {
    PrismaSortOrder.values().forEach {
        variant {
            label = it.name
            type = Types.SORT_ORDER
        }
    }
}

private fun PrismaSchemaParameter.Builder.indexingAlgorithmTypeValues() {
    PrismaIndexAlgorithm.values().forEach {
        variant {
            label = it.name
            documentation = it.documentation
            type = Types.INDEX_TYPE
        }
    }
}

private fun PrismaSchemaParameter.Builder.referentialActionTypeValues() {
    PrismaReferentialAction.values().forEach {
        variant {
            label = it.name
            documentation = it.documentation
            type = Types.REFERENTIAL_ACTION
            datasources = it.datasources
        }
    }
}