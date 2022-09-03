package com.vepanimas.intellij.prisma.ide.completion.schema

import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaElementKind
import com.vepanimas.intellij.prisma.lang.psi.PrismaPsiPatterns

object PrismaPrimitiveTypeProvider : PrismaSchemaCompletionProvider() {
    override val kind: PrismaSchemaElementKind = PrismaSchemaElementKind.PRIMITIVE_TYPE

    override val pattern = PrismaPsiPatterns.typeReference
}

