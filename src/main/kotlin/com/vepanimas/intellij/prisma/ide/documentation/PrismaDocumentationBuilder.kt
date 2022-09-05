package com.vepanimas.intellij.prisma.ide.documentation

import com.intellij.lang.documentation.DocumentationMarkup
import com.intellij.openapi.util.text.HtmlChunk
import com.intellij.psi.PsiElement
import com.vepanimas.intellij.prisma.PrismaBundle
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaElement
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaProvider
import com.vepanimas.intellij.prisma.lang.psi.PrismaEntityDeclaration
import com.vepanimas.intellij.prisma.lang.psi.PrismaFieldDeclaration
import com.vepanimas.intellij.prisma.lang.psi.presentation.PrismaPsiRenderer

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
        val definition = toHtml(element.project, schemaElement.signature ?: schemaElement.label)

        return buildString {
            definition { append(definition) }

            documentationMarkdownToHtml(schemaElement.documentation)?.let {
                content {
                    append(it)
                }
            }

            paramsSection(schemaElement)
        }
    }

    private fun StringBuilder.paramsSection(schemaElement: PrismaSchemaElement) {
        if (schemaElement.params.isEmpty()) return

        sections {
            for ((i, param) in schemaElement.params.withIndex()) {
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
            is PrismaEntityDeclaration -> entityMembersSection(element)
        }
    }

    private fun StringBuilder.entityMembersSection(element: PrismaEntityDeclaration) {
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
