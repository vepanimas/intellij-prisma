package com.vepanimas.intellij.prisma.ide.completion.schema

import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaKind
import com.vepanimas.intellij.prisma.lang.psi.PrismaPsiPatterns

object PrismaDatasourceFieldsProvider : PrismaSchemaCompletionProvider() {
    override val kind: PrismaSchemaKind = PrismaSchemaKind.DATASOURCE_FIELD

    override val pattern = PrismaPsiPatterns.datasourceField
}

