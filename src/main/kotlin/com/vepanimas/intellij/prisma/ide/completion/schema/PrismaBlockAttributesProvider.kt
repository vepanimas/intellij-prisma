package com.vepanimas.intellij.prisma.ide.completion.schema

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.patterns.ElementPattern
import com.intellij.patterns.PlatformPatterns.or
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.psi.PsiElement
import com.intellij.psi.util.parentOfType
import com.intellij.util.ProcessingContext
import com.vepanimas.intellij.prisma.ide.completion.collectExistingAttributeNamesForDeclaration
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaElement
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaKind
import com.vepanimas.intellij.prisma.lang.PrismaConstants.BlockAttributes
import com.vepanimas.intellij.prisma.lang.PrismaConstants.FieldAttributes
import com.vepanimas.intellij.prisma.lang.psi.*

object PrismaBlockAttributesProvider : PrismaSchemaCompletionProvider() {
    override val kind: PrismaSchemaKind = PrismaSchemaKind.BLOCK_ATTRIBUTE

    override val pattern: ElementPattern<out PsiElement> =
        psiElement().andOr(
            psiElement().withParent(
                psiElement(PrismaFieldDeclaration::class.java).andOr(
                    psiElement().afterNewLine(),
                    psiElement().afterLeaf(psiElement(PrismaElementTypes.AT))
                )
            ),
            psiElement().withSuperParent(
                2, or(
                    psiElement().andOr(
                        psiElement(PrismaFieldAttribute::class.java),
                        psiElement(PrismaBlockAttribute::class.java),
                    ).afterNewLine()
                )
            )
        ).inside(psiElement(PrismaModelDeclaration::class.java))

    override fun collectSchemaElements(
        parameters: CompletionParameters,
        context: ProcessingContext
    ): Collection<PrismaSchemaElement> {
        val declaration = parameters.originalPosition?.parentOfType<PrismaDeclaration>() ?: return emptyList()

        val excluded = mutableSetOf<String>()

        val existingAttributes = collectExistingAttributeNamesForDeclaration(declaration)
        excluded.addAll(existingAttributes) // skip already used block attributes

        if (FieldAttributes.ID in existingAttributes) {
            excluded.add(BlockAttributes.ID)
        }

        return super.collectSchemaElements(parameters, context).filter { it.label !in excluded }
    }
}