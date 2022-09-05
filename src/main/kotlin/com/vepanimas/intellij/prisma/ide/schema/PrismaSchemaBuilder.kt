package com.vepanimas.intellij.prisma.ide.schema

import com.intellij.codeInsight.completion.InsertHandler
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.patterns.ElementPattern
import com.intellij.psi.PsiElement

class PrismaSchema(private val elements: Map<PrismaSchemaElementKind, PrismaSchemaElementGroup>) {
    fun getElementsByKind(kind: PrismaSchemaElementKind): Collection<PrismaSchemaElement> {
        return elements[kind]?.values ?: emptyList()
    }

    fun getElement(kind: PrismaSchemaElementKind, label: String?): PrismaSchemaElement? {
        return elements[kind]?.get(label)
    }

    fun match(element: PsiElement?): PrismaSchemaElement? {
        val context = PrismaSchemaContext.forElement(element) ?: return null
        return match(context)
    }

    fun match(context: PrismaSchemaContext): PrismaSchemaElement? {
        val schemaElement = getElement(context.kind, context.label) ?: return null
        if (context.element !is PrismaSchemaFakeElement &&
            schemaElement.pattern != null &&
            !schemaElement.pattern.accepts(context.element)
        ) {
            return null
        }
        return schemaElement
    }

    class Builder : SchemaDslBuilder<PrismaSchema> {
        private val elements: MutableMap<PrismaSchemaElementKind, PrismaSchemaElementGroup> = mutableMapOf()

        fun group(kind: PrismaSchemaElementKind, block: PrismaSchemaElementGroup.Builder.() -> Unit) {
            val groupBuilder = PrismaSchemaElementGroup.Builder(kind)
            groupBuilder.block()
            val group = groupBuilder.build()
            elements[kind] = group
        }

        fun compose(schema: PrismaSchema) {
            elements.putAll(schema.elements)
        }

        override fun build(): PrismaSchema {
            return PrismaSchema(elements)
        }
    }
}

class PrismaSchemaElementGroup(val group: Map<String, PrismaSchemaElement>) :
    Map<String, PrismaSchemaElement> by group {

    class Builder(private val kind: PrismaSchemaElementKind) :
        SchemaDslBuilder<PrismaSchemaElementGroup> {
        private val group: MutableMap<String, PrismaSchemaElement> = mutableMapOf()

        fun element(block: PrismaSchemaElement.Builder.() -> Unit) {
            val elementBuilder = PrismaSchemaElement.Builder(kind)
            elementBuilder.block()
            val schemaElement = elementBuilder.build()
            group[schemaElement.label] = schemaElement
        }

        override fun build(): PrismaSchemaElementGroup {
            return PrismaSchemaElementGroup(group)
        }
    }

}

open class PrismaSchemaElement(
    val kind: PrismaSchemaElementKind,
    val label: String,
    val documentation: String? = null,
    val signature: String? = null,
    val insertHandler: InsertHandler<LookupElement>? = null,
    val params: List<PrismaSchemaElementParameter> = emptyList(),
    val pattern: ElementPattern<out PsiElement>? = null,
    val datasources: Set<PrismaDatasourceType>? = null
) {
    open class Builder(private val kind: PrismaSchemaElementKind) :
        SchemaDslBuilder<PrismaSchemaElement> {

        var label: String? = null
        var documentation: String? = null
        var signature: String? = null
        var insertHandler: InsertHandler<LookupElement>? = null
        var pattern: ElementPattern<out PsiElement>? = null
        var datasources: Set<PrismaDatasourceType>? = null

        private var params: MutableList<PrismaSchemaElementParameter> = mutableListOf()

        fun param(block: PrismaSchemaElementParameter.Builder.() -> Unit) {
            val builder = PrismaSchemaElementParameter.Builder()
            builder.block()
            val parameter = builder.build()
            params.add(parameter)
        }

        override fun build(): PrismaSchemaElement {
            return label
                ?.takeIf { it.isNotBlank() }
                ?.let {
                    PrismaSchemaElement(
                        kind, it, documentation, signature,
                        insertHandler, params, pattern, datasources
                    )
                }
                ?: error("label is not specified")
        }
    }
}

class PrismaSchemaElementParameter(
    val label: String,
    val documentation: String?,
    val insertHandler: InsertHandler<LookupElement>? = null,
    val type: String? = null,
) {
    class Builder : SchemaDslBuilder<PrismaSchemaElementParameter> {
        var label: String? = null
        var documentation: String? = null
        var type: String? = null
        var insertHandler: InsertHandler<LookupElement>? = null

        override fun build(): PrismaSchemaElementParameter {
            return label
                ?.takeIf { it.isNotBlank() }
                ?.let { PrismaSchemaElementParameter(it, documentation, insertHandler, type) }
                ?: error("label is not specified")
        }
    }
}

fun schema(block: PrismaSchema.Builder.() -> Unit): PrismaSchema {
    val builder = PrismaSchema.Builder()
    builder.block()
    return builder.build()
}

@DslMarker
annotation class SchemaDslBuilderMarker

@SchemaDslBuilderMarker
interface SchemaDslBuilder<T> {
    fun build(): T
}