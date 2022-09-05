package com.vepanimas.intellij.prisma.ide.schema

import com.vepanimas.intellij.prisma.ide.schema.definitions.PRISMA_SCHEMA_ATTRIBUTES
import com.vepanimas.intellij.prisma.ide.schema.definitions.PRISMA_SCHEMA_FIELDS
import com.vepanimas.intellij.prisma.ide.schema.definitions.PRISMA_SCHEMA_KEYWORDS
import com.vepanimas.intellij.prisma.ide.schema.definitions.PRISMA_SCHEMA_PRIMITIVE_TYPES

object PrismaSchemaProvider {

    private val PRISMA_SCHEMA_DEFINITION = lazy {
        schema {
            compose(PRISMA_SCHEMA_KEYWORDS)
            compose(PRISMA_SCHEMA_PRIMITIVE_TYPES)
            compose(PRISMA_SCHEMA_FIELDS)
            compose(PRISMA_SCHEMA_ATTRIBUTES)
        }
    }

    fun getSchema(): PrismaSchema = PRISMA_SCHEMA_DEFINITION.value
}