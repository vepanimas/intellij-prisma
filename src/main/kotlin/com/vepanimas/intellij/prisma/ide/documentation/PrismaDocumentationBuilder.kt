package com.vepanimas.intellij.prisma.ide.documentation

import com.intellij.lang.documentation.DocumentationMarkup
import com.intellij.openapi.util.text.HtmlChunk
import com.intellij.psi.PsiElement
import com.intellij.util.castSafelyTo
import com.vepanimas.intellij.prisma.PrismaBundle
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaDeclaration
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaElement
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaParameter
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaProvider
import com.vepanimas.intellij.prisma.lang.psi.PrismaFieldDeclaration
import com.vepanimas.intellij.prisma.lang.psi.PrismaFile
import com.vepanimas.intellij.prisma.lang.psi.PrismaModelTypeDeclaration
import com.vepanimas.intellij.prisma.lang.psi.presentation.PrismaPsiRenderer

private const val PARAM_INDENT = "\n\t\t"
private const val PARAM_SEP = ","
private const val PARAMS_WRAP_LIMIT = 3

class PrismaDocumentationBuilder(private val element: PsiElement) {
    private val psiRenderer = PrismaPsiRenderer()

    fun buildDocumentation(): String? {
        val schemaDoc = buildDocumentationForSchemaElement(element)
        if (schemaDoc != null) {
            return schemaDoc
        }

        val definitionBuilder = PrismaDocumentationDefinitionBuilder(element)
        val def = definitionBuilder.buildDefinition() ?: return null

        return buildString {
            definition { append(def) }
            documentationComment(element)
            additionalSections()
        }
    }

    private fun buildDocumentationForSchemaElement(element: PsiElement): String? {
        val schemaElement = PrismaSchemaProvider.getSchema().match(element) ?: return null
        val declaration = schemaElement.castSafelyTo<PrismaSchemaDeclaration>()
        val file = element.containingFile.castSafelyTo<PrismaFile>()
        val params = declaration?.getAvailableParams(file?.datasourceType, false) ?: emptyList()
        val definition = declaration?.signature ?: buildDefinitionFromSchema(schemaElement, params)

        return buildString {
            definition { append(toHtml(element.project, definition)) }

            documentationMarkdownToHtml(schemaElement.documentation)?.let {
                content {
                    append(it)
                }
            }

            paramsSection(params)
        }
    }

    private fun buildDefinitionFromSchema(
        schemaElement: PrismaSchemaElement,
        params: List<PrismaSchemaParameter>,
    ): String {
        return buildString {
            append(schemaElement.label)

            if (schemaElement is PrismaSchemaDeclaration && params.isNotEmpty()) {
                val indent = if (params.size > PARAMS_WRAP_LIMIT) {
                    PARAM_INDENT
                } else {
                    ""
                }
                val separator = PARAM_SEP + indent.ifEmpty { " " }

                params.joinTo(this, separator = separator, prefix = "($indent", postfix = ")") {
                    val type = if (it.type != null) {
                        ": ${it.type}"
                    } else {
                        ""
                    }

                    "${it.label}${type}"
                }
            } else if (schemaElement is PrismaSchemaParameter) {
                schemaElement.type?.let {
                    append(": $it")
                }
            }
        }
    }

    private fun StringBuilder.paramsSection(params: List<PrismaSchemaParameter>) {
        if (params.isEmpty()) return

        sections {
            for ((i, param) in params.withIndex()) {
                val header = if (i == 0) {
                    PrismaBundle.message("prisma.doc.section.params")
                } else {
                    ""
                }

                section(header) {
                    pre(param.label)
                    cellWithLeftPadding()
                    append(documentationMarkdownToHtml(param.documentation) ?: "")
                }
            }
        }
    }

    private fun StringBuilder.additionalSections() {
        when (element) {
            is PrismaFieldDeclaration -> fieldAttributesSection(element)
            is PrismaModelTypeDeclaration -> modelTypeMembers(element)
        }
    }

    private fun StringBuilder.modelTypeMembers(element: PrismaModelTypeDeclaration) {
        sections {
            val block = element.getFieldDeclarationBlock() ?: return@sections

            val fields = block.fieldDeclarationList
            if (fields.isNotEmpty()) {
                section(PrismaBundle.message("prisma.doc.section.fields")) {
                    fields.joinTo(this, separator = "<p>") {
                        psiRenderer.pre(it.identifier)
                    }
                    cellWithLeftPadding()
                    fields.joinTo(this, separator = "<p>") {
                        psiRenderer.pre(it.fieldType)
                    }
                }
            }

            val attributeList = block.blockAttributeList
            if (attributeList.isNotEmpty()) {
                section(
                    PrismaBundle.message("prisma.doc.section.attributes"),
                    DocumentationMarkup.SECTION_CONTENT_CELL.attr("colspan", 2).style("white-space: nowrap")
                ) {
                    attributeList.joinTo(this, separator = "<p>") { psiRenderer.pre(it) }
                }
            }
        }
    }

    private fun StringBuilder.fieldAttributesSection(element: PrismaFieldDeclaration) {
        val attributeList = element.fieldAttributeList
        if (attributeList.isEmpty()) {
            return
        }

        sections {
            section(PrismaBundle.message("prisma.doc.section.attributes")) {
                attributeList.joinTo(this, separator = "<p>") { psiRenderer.pre(it) }
            }
        }
    }

    private fun StringBuilder.cellWithLeftPadding() {
        append(DocumentationMarkup.SECTION_CONTENT_CELL.style("padding-left: 15px"))
    }

    private fun PrismaPsiRenderer.pre(element: PsiElement?) =
        HtmlChunk.text(build(element)).code().toString()

    private fun StringBuilder.pre(source: String) =
        append(HtmlChunk.text(source).code().toString())
}
