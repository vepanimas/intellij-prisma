package com.vepanimas.intellij.prisma.ide.schema

import com.intellij.codeInsight.completion.InsertHandler
import com.intellij.codeInsight.lookup.LookupElement

@DslMarker
annotation class SchemaDslBuilderMarker

@SchemaDslBuilderMarker
interface SchemaDslBuilder<T> {
    fun build(): T
}

class PrismaSchemaBuilder : SchemaDslBuilder<PrismaSchema> {
    private val elements: MutableMap<PrismaSchemaElementKind, PrismaSchemaElementGroup> = mutableMapOf()

    fun group(kind: PrismaSchemaElementKind, block: PrismaSchemaGroupBuilder.() -> Unit) {
        val groupBuilder = PrismaSchemaGroupBuilder(kind)
        groupBuilder.block()
        val group = groupBuilder.build()
        elements[kind] = group
    }

    override fun build(): PrismaSchema {
        return PrismaSchema(elements)
    }
}

class PrismaSchemaGroupBuilder(private val kind: PrismaSchemaElementKind) : SchemaDslBuilder<PrismaSchemaElementGroup> {
    private val group: MutableMap<String, PrismaSchemaElement> = mutableMapOf()

    fun element(block: PrismaSchemaElementBuilder.() -> Unit) {
        val elementBuilder = PrismaSchemaElementBuilder(kind)
        elementBuilder.block()
        val schemaElement = elementBuilder.build()
        group[schemaElement.label] = schemaElement
    }

    override fun build(): PrismaSchemaElementGroup {
        return group
    }
}

open class PrismaSchemaElementBuilder(private val kind: PrismaSchemaElementKind) :
    SchemaDslBuilder<PrismaSchemaElement> {

    var label: String? = null
    var documentation: String? = null
    var signature: String? = null

    private var params: MutableMap<String, PrismaSchemaElementParameter> = mutableMapOf()

    fun param(block: PrismaSchemaParameterBuilder.() -> Unit) {
        val builder = PrismaSchemaParameterBuilder()
        builder.block()
        val parameter = builder.build()
        params[parameter.label] = parameter
    }

    override fun build(): PrismaSchemaElement {
        return label
            ?.takeIf { it.isNotBlank() }
            ?.let { PrismaSchemaElement(kind, it, documentation, signature, params) }
            ?: error("label is not specified")
    }
}

class PrismaSchemaParameterBuilder : SchemaDslBuilder<PrismaSchemaElementParameter> {
    var label: String? = null
    var documentation: String? = null
    var insertHandler: InsertHandler<out LookupElement>? = null

    override fun build(): PrismaSchemaElementParameter {
        return label
            ?.takeIf { it.isNotBlank() }
            ?.let { PrismaSchemaElementParameter(it, documentation, insertHandler) }
            ?: error("label is not specified")
    }
}

fun schema(block: PrismaSchemaBuilder.() -> Unit): PrismaSchema {
    val builder = PrismaSchemaBuilder()
    builder.block()
    return builder.build()
}
