package com.vepanimas.intellij.prisma.ide.documentation

import com.intellij.lang.documentation.DocumentationMarkup
import com.intellij.openapi.editor.colors.EditorColorsManager
import com.intellij.openapi.editor.colors.EditorColorsScheme
import com.intellij.openapi.editor.richcopy.HtmlSyntaxInfoUtil
import com.intellij.openapi.progress.ProcessCanceledException
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.text.HtmlChunk
import com.intellij.psi.PsiElement
import com.vepanimas.intellij.prisma.lang.psi.PrismaDocumentationOwner
import com.vepanimas.intellij.prisma.lang.psi.PrismaElementFactory
import org.intellij.markdown.IElementType
import org.intellij.markdown.MarkdownElementTypes
import org.intellij.markdown.flavours.MarkdownFlavourDescriptor
import org.intellij.markdown.flavours.gfm.GFMFlavourDescriptor
import org.intellij.markdown.html.GeneratingProvider
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.LinkMap
import org.intellij.markdown.parser.MarkdownParser
import java.net.URI

internal fun stripDocCommentBlockPrefixes(text: String): String {
    return text.trimMargin("///").trimIndent()
}

internal fun stripDocCommentLinePrefix(text: String): String {
    return text.removePrefix("///").trim()
}

internal fun StringBuilder.definition(block: StringBuilder.() -> Unit) {
    append(DocumentationMarkup.DEFINITION_START)
    block()
    append(DocumentationMarkup.DEFINITION_END)
    append("\n")
}

internal fun StringBuilder.sections(block: StringBuilder.() -> Unit) {
    append(DocumentationMarkup.SECTIONS_START)
    block()
    append(DocumentationMarkup.SECTIONS_END)
    append("\n")
}

internal fun StringBuilder.section(header: String, block: StringBuilder.() -> Unit) {
    append(DocumentationMarkup.SECTION_HEADER_START)
    append(header)
    append(DocumentationMarkup.SECTION_SEPARATOR)
    block()
    append(DocumentationMarkup.SECTION_END)
    append("\n")
}

internal fun StringBuilder.section(header: String, td: HtmlChunk, block: StringBuilder.() -> Unit) {
    append(DocumentationMarkup.SECTION_HEADER_START)
    append(header)
    append(DocumentationMarkup.SECTION_END)
    td.appendTo(this)
    block()
    append(DocumentationMarkup.SECTION_END)
    append("\n")
}

internal fun StringBuilder.content(block: StringBuilder.() -> Unit) {
    append(DocumentationMarkup.CONTENT_START)
    block()
    append(DocumentationMarkup.CONTENT_END)
    append("\n")
}

internal fun StringBuilder.doc(element: PsiElement?) {
    val comment = (element as? PrismaDocumentationOwner)?.docComment ?: return
    val rendered = PrismaDocumentationRenderer(comment).render() ?: return
    content {
        append(rendered)
    }
}

internal fun toHtml(project: Project, text: String): String {
    return try {
        toHtml(project, text, EditorColorsManager.getInstance().globalScheme)
    } catch (e: ProcessCanceledException) {
        throw e
    } catch (e: Exception) {
        text
    }
}

internal fun toHtml(project: Project, text: String, scheme: EditorColorsScheme): String {
    val code = text.trimIndent().replace("\t", "  ")
    val file = PrismaElementFactory.createFile(project, code)
    return HtmlSyntaxInfoUtil
        .getHtmlContent(file, code, null, scheme, 0, code.length)?.toString() ?: text
}

fun documentationMarkdownToHtml(markdown: String?): String? {
    if (markdown.isNullOrBlank()) {
        return null
    }

    val flavour = PrismaMarkdownFlavourDescriptor()
    val root = MarkdownParser(flavour).buildMarkdownTreeFromString(markdown)
    return HtmlGenerator(markdown, root, flavour).generateHtml()
}

private class PrismaMarkdownFlavourDescriptor(
    private val base: MarkdownFlavourDescriptor = GFMFlavourDescriptor(
        useSafeLinks = false,
        absolutizeAnchorLinks = true
    )
) : MarkdownFlavourDescriptor by base {

    override fun createHtmlGeneratingProviders(linkMap: LinkMap, baseURI: URI?): Map<IElementType, GeneratingProvider> {
        val generatingProviders = HashMap(base.createHtmlGeneratingProviders(linkMap, null))
        // Filter out MARKDOWN_FILE to avoid producing unnecessary <body> tags
        generatingProviders.remove(MarkdownElementTypes.MARKDOWN_FILE)
        return generatingProviders
    }
}