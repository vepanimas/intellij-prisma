package com.vepanimas.intellij.prisma.ide.schema

import com.vepanimas.intellij.prisma.ide.schema.definitions.PRISMA_SCHEMA_FIELDS
import com.vepanimas.intellij.prisma.ide.schema.definitions.PRISMA_SCHEMA_KEYWORDS
import com.vepanimas.intellij.prisma.ide.schema.definitions.PRISMA_SCHEMA_PRIMITIVE_TYPES

val PRISMA_SCHEMA_DEFINITION = schema {
    compose(PRISMA_SCHEMA_KEYWORDS)
    compose(PRISMA_SCHEMA_PRIMITIVE_TYPES)
    compose(PRISMA_SCHEMA_FIELDS)
}
