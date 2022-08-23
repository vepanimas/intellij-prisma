package com.vepanimas.intellij.prisma.ide.highlighting

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory
import com.intellij.openapi.options.OptionsBundle
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import com.vepanimas.intellij.prisma.PrismaIcons
import com.vepanimas.intellij.prisma.lang.PrismaLanguage
import javax.swing.Icon

class PrismaColorSettingsPage : ColorSettingsPage {
    override fun getColorDescriptors(): Array<ColorDescriptor> = ColorDescriptor.EMPTY_ARRAY

    override fun getDisplayName(): String = PrismaLanguage.displayName

    override fun getIcon(): Icon = PrismaIcons.PRISMA

    override fun getHighlighter(): SyntaxHighlighter =
        SyntaxHighlighterFactory.getSyntaxHighlighter(PrismaLanguage, null, null)

    override fun getDemoText(): String = DEMO_TEXT

    override fun getAttributeDescriptors(): Array<AttributesDescriptor> = descriptors

    override fun getAdditionalHighlightingTagToDescriptorMap(): MutableMap<String, TextAttributesKey>? = null

    companion object {
        private val descriptors = arrayOf(
            AttributesDescriptor(
                OptionsBundle.messagePointer("options.language.defaults.doc.comment"),
                PrismaColors.DOC_COMMENT
            ),
            AttributesDescriptor(
                OptionsBundle.messagePointer("options.language.defaults.line.comment"),
                PrismaColors.LINE_COMMENT
            ),
            AttributesDescriptor(
                OptionsBundle.messagePointer("options.language.defaults.string"),
                PrismaColors.STRING_LITERAL
            ),
            AttributesDescriptor(
                OptionsBundle.messagePointer("options.language.defaults.keyword"),
                PrismaColors.KEYWORD
            ),
            AttributesDescriptor(
                OptionsBundle.messagePointer("options.language.defaults.identifier"),
                PrismaColors.IDENTIFIER
            ),
            AttributesDescriptor(OptionsBundle.messagePointer("options.language.defaults.number"), PrismaColors.NUMBER),
            AttributesDescriptor(
                OptionsBundle.messagePointer("options.language.defaults.brackets"),
                PrismaColors.BRACKETS
            ),
            AttributesDescriptor(
                OptionsBundle.messagePointer("options.language.defaults.parentheses"),
                PrismaColors.PARENTHESES
            ),
            AttributesDescriptor(OptionsBundle.messagePointer("options.language.defaults.braces"), PrismaColors.BRACES),
            AttributesDescriptor(OptionsBundle.messagePointer("options.language.defaults.dot"), PrismaColors.DOT),
            AttributesDescriptor(OptionsBundle.messagePointer("options.language.defaults.comma"), PrismaColors.COMMA),
            AttributesDescriptor(
                OptionsBundle.messagePointer("options.language.defaults.operation"),
                PrismaColors.OPERATION_SIGN
            ),
        )

        private val DEMO_TEXT = """
        /// Doc
        /// Comment
        generator client {
          provider = "prisma-client-js"
        }
        
        // Line comment
        datasource db {
          provider = "sqlite" // property comment
          url      = "file:./dev.db"
        }
        
        model User {
          id    Int     @id @default(autoincrement())
          email String  @unique
          name  String?
          posts Post[]
        }
        
        model Post {
          id        Int      @id @default(autoincrement())
          createdAt DateTime @default(now())
          updatedAt DateTime @updatedAt
          title     String
          content   String?
          published Boolean  @default(false)
          viewCount Int      @default(0)
          author    User?    @relation(fields: [authorId], references: [id])
          authorId  Int?
        }
        """.trimIndent()
    }
}