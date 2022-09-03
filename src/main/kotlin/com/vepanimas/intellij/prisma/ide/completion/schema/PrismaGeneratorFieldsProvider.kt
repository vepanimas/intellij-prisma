package com.vepanimas.intellij.prisma.ide.completion.schema

import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaElementKind
import com.vepanimas.intellij.prisma.lang.psi.PrismaPsiPatterns

object PrismaGeneratorFieldsProvider : PrismaSchemaCompletionProvider() {
    override val kind: PrismaSchemaElementKind = PrismaSchemaElementKind.GENERATOR_FIELD

    override val pattern = PrismaPsiPatterns.generatorField
}

