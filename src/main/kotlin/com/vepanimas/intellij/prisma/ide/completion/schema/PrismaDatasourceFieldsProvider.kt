package com.vepanimas.intellij.prisma.ide.completion.schema

import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaElementKind
import com.vepanimas.intellij.prisma.lang.psi.PrismaPsiPatterns

object PrismaDatasourceFieldsProvider : PrismaSchemaCompletionProvider() {
    override val kind: PrismaSchemaElementKind = PrismaSchemaElementKind.DATASOURCE_FIELD

    override val pattern = PrismaPsiPatterns.datasourceField
}

