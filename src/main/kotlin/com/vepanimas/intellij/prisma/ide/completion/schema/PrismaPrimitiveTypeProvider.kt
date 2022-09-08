package com.vepanimas.intellij.prisma.ide.completion.schema

import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaKind
import com.vepanimas.intellij.prisma.lang.psi.PrismaPsiPatterns

object PrismaPrimitiveTypeProvider : PrismaSchemaCompletionProvider() {
    override val kind: PrismaSchemaKind = PrismaSchemaKind.PRIMITIVE_TYPE

    override val pattern = PrismaPsiPatterns.typeReference
}

