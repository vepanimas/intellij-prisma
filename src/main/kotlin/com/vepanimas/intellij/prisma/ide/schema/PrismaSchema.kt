package com.vepanimas.intellij.prisma.ide.schema

import com.intellij.codeInsight.completion.InsertHandler
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.patterns.ElementPattern
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IElementType
import com.vepanimas.intellij.prisma.lang.psi.isKeyword
import com.vepanimas.intellij.prisma.lang.psi.prismaElementType

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

    fun match(element: PsiElement?): PrismaSchemaElement? {
        if (element == null) {
            return null
        }

        val kind = element.schemaKind ?: return null
        val label = element.schemaLabel ?: return null
        val elementType = element.prismaElementType ?: return null
        val schemaElement = get(kind, label) ?: return null
        if (schemaElement.elementType != elementType) {
            return null
        }
        if (element !is PrismaSchemaFakeElement &&
            schemaElement.pattern != null &&
            !schemaElement.pattern.accepts(element)
        ) {
            return null
        }
        return schemaElement
    }
}

typealias PrismaSchemaElementGroup = Map<String, PrismaSchemaElement>

open class PrismaSchemaElement(
    val kind: PrismaSchemaElementKind,
    val label: String,
    val elementType: IElementType? = null,
    val documentation: String? = null,
    val signature: String? = null,
    val insertHandler: InsertHandler<LookupElement>? = null,
    val params: Map<String, PrismaSchemaElementParameter> = emptyMap(),
    val pattern: ElementPattern<out PsiElement>? = null,
)

class PrismaSchemaElementParameter(
    val label: String,
    val elementType: IElementType? = null,
    val documentation: String?,
    val insertHandler: InsertHandler<LookupElement>? = null,
)
