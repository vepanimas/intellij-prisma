package com.vepanimas.intellij.prisma.ide.documentation

import com.intellij.lang.documentation.DocumentationMarkup
import com.intellij.psi.PsiElement
import com.vepanimas.intellij.prisma.lang.psi.*

class PrismaDocumentationBuilder(private val element: PsiElement) {
    fun buildDocumentation(): String? {
        return when (element) {
            is PrismaDeclaration -> buildDeclaration(element)
            is PrismaMemberDeclaration -> buildMemberDeclaration(element)
            else -> null
        }
    }

    private fun buildDeclaration(element: PrismaDeclaration): String? {
        val name = element.name ?: return null
        return buildString {
            definition {
                kind(element)
                append(name)
            }
            content {
                doc(element)
            }
        }
    }

    private fun buildMemberDeclaration(member: PrismaMemberDeclaration): String? {
        val name = member.name ?: return null
        val declaration = member.containingDeclaration
        return buildString {
            definition {
                kind(member)
                if (declaration != null) {
                    append(declaration.name)
                    append(".")
                }
                append(name)
            }
            content {
                doc(element)
            }
        }
    }

    private fun getKind(element: PsiElement?): String? {
        return when (element) {
            is PrismaModelDeclaration -> "model"
            is PrismaTypeDeclaration -> "type"
            is PrismaDatasourceDeclaration -> "datasource"
            is PrismaGeneratorDeclaration -> "generator"
            is PrismaTypeAlias -> "alias"
            is PrismaEnumDeclaration -> "enum"
            else -> null
        }
    }

    private fun StringBuilder.definition(block: StringBuilder.() -> Unit) {
        append(DocumentationMarkup.DEFINITION_START)
        block()
        append(DocumentationMarkup.DEFINITION_END)
    }

    private fun StringBuilder.content(block: StringBuilder.() -> Unit) {
        append(DocumentationMarkup.CONTENT_START)
        block()
        append(DocumentationMarkup.CONTENT_END)
    }

    private fun StringBuilder.doc(element: PsiElement?) {
        if (element !is PrismaDocumentationOwner) {
            return
        }

        val comment = element.docComment ?: return
        append(PrismaDocumentationRenderer(comment).render())
    }

    private fun StringBuilder.kind(element: PsiElement?) {
        getKind(element)?.let {
            append(it)
            space()
        }
    }

    private fun StringBuilder.space() {
        if (isNotEmpty() && this[length - 1] != ' ') {
            append(" ")
        }
    }
}