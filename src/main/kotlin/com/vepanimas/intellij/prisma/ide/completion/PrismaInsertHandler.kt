package com.vepanimas.intellij.prisma.ide.completion

import com.intellij.codeInsight.completion.AddSpaceInsertHandler
import com.intellij.codeInsight.completion.InsertHandler
import com.intellij.codeInsight.completion.util.ParenthesesInsertHandler
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.openapi.editor.EditorModificationUtil
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaContext
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaElementKind

object PrismaInsertHandler {
    val DEFAULT_INSERT_HANDLER = InsertHandler<LookupElement> { context, item ->
        when (PrismaSchemaContext.forElement(item.psiElement)?.kind) {
            PrismaSchemaElementKind.KEYWORD -> {
                AddSpaceInsertHandler.INSTANCE.handleInsert(context, item)
            }

            PrismaSchemaElementKind.DATASOURCE_FIELD, PrismaSchemaElementKind.GENERATOR_FIELD -> {
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

    val COLON_LIST_ARGUMENT = InsertHandler<LookupElement> { context, item ->
        EditorModificationUtil.insertStringAtCaret(context.editor, ": []", false, true, 3)
    }

    val EQUALS_INSERT_HANDLER = InsertHandler<LookupElement> { context, _ ->
        EditorModificationUtil.insertStringAtCaret(context.editor, " = ", false, true)
    }
}