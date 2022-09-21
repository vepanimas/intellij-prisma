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
        if (element is PrismaSchemaFakeElement) {
            return element.schemaElement
        }

        val context = PrismaSchemaContext.forElement(element) ?: return null
        return match(context)
    }

    private fun match(context: PrismaSchemaContext): PrismaSchemaElement? {
        return when (context) {
            is PrismaSchemaDeclarationContext -> {
                val declaration = getElement(context.kind, context.label)
                filterByPattern(context.element, declaration)
            }

            is PrismaSchemaParameterContext -> {
                val declaration = match(context.parent) as? PrismaSchemaDeclaration
                val parameter = declaration?.params?.find { it.label == context.label }
                filterByPattern(context.element, parameter)
            }

            is PrismaSchemaVariantContext -> {
                val parent = match(context.parent)
                parent?.variants?.find { it.label == context.label }
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

    class Builder(private val kind: PrismaSchemaKind) : SchemaDslBuilder<PrismaSchemaElementGroup> {
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
    val datasources: Set<PrismaDatasourceType>? = null,
    val variants: List<PrismaSchemaVariant> = emptyList(),
) {
    fun isAvailableForDatasource(usedDatasource: PrismaDatasourceType?): Boolean {
        // filter only when datasource provider is specified
        return datasources == null ||
                usedDatasource == null ||
                datasources.contains(usedDatasource)
    }
}

open class PrismaSchemaDeclaration(
    val kind: PrismaSchemaKind,
    label: String,
    documentation: String? = null,
    val signature: String? = null,
    insertHandler: InsertHandler<LookupElement>? = null,
    val params: List<PrismaSchemaParameter> = emptyList(),
    pattern: ElementPattern<out PsiElement>? = null,
    datasources: Set<PrismaDatasourceType>? = null,
    variants: List<PrismaSchemaVariant> = emptyList(),
) : PrismaSchemaElement(label, documentation, insertHandler, pattern, datasources, variants) {

    fun getAvailableParams(
        usedDatasource: PrismaDatasourceType?,
        isOnFieldLevel: Boolean,
    ): List<PrismaSchemaParameter> {
        return params.filter { it.isAvailableForDatasource(usedDatasource) && it.isOnFieldLevel == isOnFieldLevel }
    }

    open class Builder(private val kind: PrismaSchemaKind) : SchemaDslBuilder<PrismaSchemaDeclaration> {
        var label: String? = null
        var documentation: String? = null
        var signature: String? = null
        var insertHandler: InsertHandler<LookupElement>? = null
        var pattern: ElementPattern<out PsiElement>? = null
        var datasources: Set<PrismaDatasourceType>? = null

        private var params: MutableList<PrismaSchemaParameter> = mutableListOf()
        private var variants: MutableList<PrismaSchemaVariant> = mutableListOf()

        fun param(block: PrismaSchemaParameter.Builder.() -> Unit) {
            val builder = PrismaSchemaParameter.Builder()
            builder.block()
            params.add(builder.build())
        }

        fun variant(block: PrismaSchemaVariant.Builder.() -> Unit) {
            val builder = PrismaSchemaVariant.Builder()
            builder.block()
            variants.add(builder.build())
        }

        override fun build(): PrismaSchemaDeclaration {
            return label
                ?.takeIf { it.isNotBlank() }
                ?.let {
                    PrismaSchemaDeclaration(
                        kind, it, documentation, signature, insertHandler,
                        params, pattern, datasources, variants
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
    datasources: Set<PrismaDatasourceType>? = null,
    variants: List<PrismaSchemaVariant> = emptyList(),
    val type: String? = null,
    val isOnFieldLevel: Boolean = false,
    val skipInCompletion: Boolean = false,
) : PrismaSchemaElement(
    label,
    documentation,
    insertHandler = insertHandler,
    datasources = datasources,
    variants = variants
) {
    class Builder : SchemaDslBuilder<PrismaSchemaParameter> {
        var label: String? = null
        var documentation: String? = null
        var type: String? = null
        var insertHandler: InsertHandler<LookupElement>? = null
        var datasources: Set<PrismaDatasourceType>? = null
        var isOnFieldLevel: Boolean = false
        var skipInCompletion: Boolean = false

        private var variants: MutableList<PrismaSchemaVariant> = mutableListOf()

        fun variant(block: PrismaSchemaVariant.Builder.() -> Unit) {
            val builder = PrismaSchemaVariant.Builder()
            builder.block()
            variants.add(builder.build())
        }

        override fun build(): PrismaSchemaParameter {
            return label
                ?.takeIf { it.isNotBlank() }
                ?.let {
                    PrismaSchemaParameter(
                        it, documentation, insertHandler, datasources,
                        variants, type, isOnFieldLevel, skipInCompletion
                    )
                }
                ?: error("label is not specified")
        }
    }
}

class PrismaSchemaVariant(
    label: String,
    documentation: String?,
    insertHandler: InsertHandler<LookupElement>? = null,
    val type: String? = null,
) : PrismaSchemaElement(label, documentation, insertHandler) {
    class Builder : SchemaDslBuilder<PrismaSchemaVariant> {
        var label: String? = null
        var documentation: String? = null
        var insertHandler: InsertHandler<LookupElement>? = null
        var type: String? = null

        override fun build(): PrismaSchemaVariant {
            return label
                ?.takeIf { it.isNotBlank() }
                ?.let { PrismaSchemaVariant(it, documentation, insertHandler, type) }
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