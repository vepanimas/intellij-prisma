package com.vepanimas.intellij.prisma.ide.schema

import com.intellij.codeInsight.completion.InsertHandler
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.patterns.ElementPattern
import com.intellij.psi.PsiElement

class PrismaSchema(private val elements: Map<PrismaSchemaKind, PrismaSchemaElementGroup>) {
    fun getElementsByKind(kind: PrismaSchemaKind): Collection<PrismaSchemaDeclaration> {
        return elements[kind]?.values ?: emptyList()
    }

    fun getElement(kind: PrismaSchemaKind, label: String?): PrismaSchemaDeclaration? {
        return elements[kind]?.get(label)
    }

    fun match(element: PsiElement?): PrismaSchemaElement? {
        val context = PrismaSchemaContext.forElement(element) ?: return null
        when (context) {
            is PrismaSchemaDeclarationContext -> {
                val declaration = getElement(context.kind, context.label) ?: return null
                return filterByPattern(context.element, declaration)
            }

            is PrismaSchemaParameterContext -> {
                val declaration = getElement(context.parent.kind, context.parent.label) ?: return null
                val parameter = declaration.params.find { it.label == context.label }
                return filterByPattern(context.element, parameter)
            }
        }
    }

    private fun filterByPattern(
        element: PsiElement,
        schemaElement: PrismaSchemaElement?
    ): PrismaSchemaElement? {
        if (schemaElement == null) return null

        if (element !is PrismaSchemaFakeElement &&
            schemaElement.pattern != null &&
            !schemaElement.pattern.accepts(element)
        ) {
            return null
        }

        return schemaElement
    }

    class Builder : SchemaDslBuilder<PrismaSchema> {
        private val elements: MutableMap<PrismaSchemaKind, PrismaSchemaElementGroup> = mutableMapOf()

        fun group(kind: PrismaSchemaKind, block: PrismaSchemaElementGroup.Builder.() -> Unit) {
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

class PrismaSchemaElementGroup(val group: Map<String, PrismaSchemaDeclaration>) :
    Map<String, PrismaSchemaDeclaration> by group {

    class Builder(private val kind: PrismaSchemaKind) :
        SchemaDslBuilder<PrismaSchemaElementGroup> {
        private val group: MutableMap<String, PrismaSchemaDeclaration> = mutableMapOf()

        fun element(block: PrismaSchemaDeclaration.Builder.() -> Unit) {
            val elementBuilder = PrismaSchemaDeclaration.Builder(kind)
            elementBuilder.block()
            val schemaElement = elementBuilder.build()
            group[schemaElement.label] = schemaElement
        }

        override fun build(): PrismaSchemaElementGroup {
            return PrismaSchemaElementGroup(group)
        }
    }

}

sealed class PrismaSchemaElement(
    val label: String,
    val documentation: String? = null,
    val insertHandler: InsertHandler<LookupElement>? = null,
    val pattern: ElementPattern<out PsiElement>? = null,
    val datasources: Set<PrismaDatasourceType>? = null
)

open class PrismaSchemaDeclaration(
    val kind: PrismaSchemaKind,
    label: String,
    documentation: String? = null,
    val signature: String? = null,
    insertHandler: InsertHandler<LookupElement>? = null,
    val params: List<PrismaSchemaParameter> = emptyList(),
    pattern: ElementPattern<out PsiElement>? = null,
    datasources: Set<PrismaDatasourceType>? = null
) : PrismaSchemaElement(label, documentation, insertHandler, pattern, datasources) {
    open class Builder(private val kind: PrismaSchemaKind) : SchemaDslBuilder<PrismaSchemaDeclaration> {
        var label: String? = null
        var documentation: String? = null
        var signature: String? = null
        var insertHandler: InsertHandler<LookupElement>? = null
        var pattern: ElementPattern<out PsiElement>? = null
        var datasources: Set<PrismaDatasourceType>? = null

        private var params: MutableList<PrismaSchemaParameter> = mutableListOf()

        fun param(block: PrismaSchemaParameter.Builder.() -> Unit) {
            val builder = PrismaSchemaParameter.Builder()
            builder.block()
            val parameter = builder.build()
            params.add(parameter)
        }

        override fun build(): PrismaSchemaDeclaration {
            return label
                ?.takeIf { it.isNotBlank() }
                ?.let {
                    PrismaSchemaDeclaration(
                        kind, it, documentation, signature,
                        insertHandler, params, pattern, datasources
                    )
                }
                ?: error("label is not specified")
        }
    }
}

class PrismaSchemaParameter(
    label: String,
    documentation: String?,
    insertHandler: InsertHandler<LookupElement>? = null,
    val type: String? = null,
) : PrismaSchemaElement(label, documentation, insertHandler = insertHandler) {
    class Builder : SchemaDslBuilder<PrismaSchemaParameter> {
        var label: String? = null
        var documentation: String? = null
        var type: String? = null
        var insertHandler: InsertHandler<LookupElement>? = null

        override fun build(): PrismaSchemaParameter {
            return label
                ?.takeIf { it.isNotBlank() }
                ?.let { PrismaSchemaParameter(it, documentation, insertHandler, type) }
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
interface SchemaDslBuilder<out T> {
    fun build(): T
}