package com.vepanimas.intellij.prisma.ide.documentation

import com.intellij.lang.documentation.DocumentationMarkup
import com.intellij.psi.PsiElement
import com.vepanimas.intellij.prisma.PrismaBundle
import com.vepanimas.intellij.prisma.lang.psi.PrismaFieldDeclaration
import com.vepanimas.intellij.prisma.lang.psi.PrismaModelTypeDeclaration
import com.vepanimas.intellij.prisma.lang.psi.presentation.PrismaPsiRenderer

class PrismaDocumentationBuilder(private val element: PsiElement) {
    fun buildDocumentation(): String? {
        val definitionBuilder = PrismaDocumentationDefinitionBuilder(element)
        val def = definitionBuilder.buildDefinition() ?: return null

        return buildString {
            definition { append(def) }
            additionalSections()
            doc(element)
        }
    }

    private fun StringBuilder.additionalSections() {
        when (element) {
            is PrismaFieldDeclaration -> attributesSection(element)
            is PrismaModelTypeDeclaration -> modelTypeMembersSection(element)
        }
    }

    private fun StringBuilder.modelTypeMembersSection(element: PrismaModelTypeDeclaration) {
        sections {
            val psiRenderer = PrismaPsiRenderer()

            val block = element.getFieldDeclarationBlock()
            val fields = block.fieldDeclarationList

            if (fields.isNotEmpty()) {
                section(PrismaBundle.message("prisma.doc.section.fields")) {
                    fields.joinTo(this, separator = "<p>") {
                        psiRenderer.build(it.identifier)
                    }
                    append(DocumentationMarkup.SECTION_CONTENT_CELL.style("padding-left: 15px"))
                    fields.joinTo(this, separator = "<p>") {
                        psiRenderer.build(it.fieldType)
                    }
                }
            }

            val attributeList = block.blockAttributeList
            if (attributeList.isNotEmpty()) {
                section(
                    PrismaBundle.message("prisma.doc.section.attributes"),
                    DocumentationMarkup.SECTION_CONTENT_CELL.attr("colspan", 2).style("white-space: nowrap")
                ) {
                    attributeList.joinTo(this, separator = "<p>") { psiRenderer.build(it) }
                }
            }
        }
    }

    private fun StringBuilder.attributesSection(element: PrismaFieldDeclaration) {
        val attributeList = element.fieldAttributeList
        if (attributeList.isEmpty()) {
            return
        }

        sections {
            section(PrismaBundle.message("prisma.doc.section.attributes")) {
                attributeList.joinTo(this, separator = "<p>") { PrismaPsiRenderer().build(it) }
            }
        }
    }
}