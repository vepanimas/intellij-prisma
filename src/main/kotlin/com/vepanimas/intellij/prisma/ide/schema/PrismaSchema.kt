package com.vepanimas.intellij.prisma.ide.schema

import com.intellij.codeInsight.completion.InsertHandler
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.psi.PsiElement
import com.vepanimas.intellij.prisma.lang.psi.isKeyword

enum class PrismaSchemaElementKind {
    KEYWORD
}

val PsiElement.schemaKind: PrismaSchemaElementKind?
    get() {
        return when {
            isKeyword -> PrismaSchemaElementKind.KEYWORD
            else -> null
        }
    }

val PsiElement.schemaLabel: String?
    get() {
        return when {
            isKeyword -> text
            else -> null
        }
    }

class PrismaSchema(private val elements: Map<PrismaSchemaElementKind, PrismaSchemaElementGroup>) {
    operator fun get(kind: PrismaSchemaElementKind, label: String?): PrismaSchemaElement? {
        return elements[kind]?.get(label)
    }

    operator fun get(kind: PrismaSchemaElementKind): Map<String, PrismaSchemaElement> {
        return elements[kind] ?: emptyMap()
    }
}

typealias PrismaSchemaElementGroup = Map<String, PrismaSchemaElement>

open class PrismaSchemaElement(
    val kind: PrismaSchemaElementKind,
    val label: String,
    val documentation: String? = null,
    val signature: String? = null,
    val params: Map<String, PrismaSchemaElementParameter> = emptyMap()
)

class PrismaSchemaElementParameter(
    val label: String,
    val documentation: String?,
    val insertHandler: InsertHandler<out LookupElement>? = null
)
