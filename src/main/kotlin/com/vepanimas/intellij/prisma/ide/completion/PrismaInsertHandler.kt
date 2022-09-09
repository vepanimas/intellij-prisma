package com.vepanimas.intellij.prisma.ide.completion

import com.intellij.codeInsight.completion.AddSpaceInsertHandler
import com.intellij.codeInsight.completion.InsertHandler
import com.intellij.codeInsight.completion.util.ParenthesesInsertHandler
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.editor.EditorModificationUtil
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaContext
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaDeclarationContext
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaKind
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaParameterContext

object PrismaInsertHandler {
    val DEFAULT_INSERT_HANDLER = InsertHandler<LookupElement> { context, item ->
        val schemaContext = PrismaSchemaContext.forElement(item.psiElement)

        if (schemaContext is PrismaSchemaParameterContext) {
            COLON_ARGUMENT.handleInsert(context, item)
            return@InsertHandler
        }

        when ((schemaContext as? PrismaSchemaDeclarationContext)?.kind) {
            PrismaSchemaKind.KEYWORD -> {
                AddSpaceInsertHandler.INSTANCE.handleInsert(context, item)
            }

            PrismaSchemaKind.DATASOURCE_FIELD, PrismaSchemaKind.GENERATOR_FIELD -> {
                EQUALS_INSERT_HANDLER.handleInsert(context, item)
            }

            else -> {}
        }
    }

    val PARENS_QUOTED_ARGUMENT = InsertHandler<LookupElement> { context, item ->
        ParenthesesInsertHandler.getInstance(true).handleInsert(context, item)
        EditorModificationUtil.insertStringAtCaret(context.editor, "\"\"", false, true, 1)
    }

    val PARENS_LIST_ARGUMENT = InsertHandler<LookupElement> { context, item ->
        ParenthesesInsertHandler.getInstance(true).handleInsert(context, item)
        EditorModificationUtil.insertStringAtCaret(context.editor, "[]", false, true, 1)
    }

    val COLON_QUOTED_ARGUMENT = InsertHandler<LookupElement> { context, item ->
        EditorModificationUtil.insertStringAtCaret(context.editor, ": \"\"", false, true, 3)
    }

    val COLON_ARGUMENT = InsertHandler<LookupElement> { context, item ->
        EditorModificationUtil.insertStringAtCaret(context.editor, ": ", false, true)
    }

    val COLON_LIST_ARGUMENT = InsertHandler<LookupElement> { context, item ->
        EditorModificationUtil.insertStringAtCaret(context.editor, ": []", false, true, 3)
    }

    val EQUALS_INSERT_HANDLER = InsertHandler<LookupElement> { context, _ ->
        EditorModificationUtil.insertStringAtCaret(context.editor, " = ", false, true)
    }
}

fun LookupElementBuilder.withPrismaInsertHandler(
    customInsertHandler: InsertHandler<LookupElement>? = null
): LookupElementBuilder = if (customInsertHandler != null) {
    withInsertHandler(customInsertHandler)
} else {
    withInsertHandler(PrismaInsertHandler.DEFAULT_INSERT_HANDLER)
}