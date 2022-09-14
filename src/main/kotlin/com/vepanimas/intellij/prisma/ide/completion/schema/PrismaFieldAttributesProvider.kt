package com.vepanimas.intellij.prisma.ide.completion.schema

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.patterns.ElementPattern
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiErrorElement
import com.intellij.util.ProcessingContext
import com.vepanimas.intellij.prisma.ide.completion.collectExistingAttributeNamesForDeclaration
import com.vepanimas.intellij.prisma.ide.completion.collectExistingAttributeNamesForField
import com.vepanimas.intellij.prisma.ide.completion.findAssociatedField
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaElement
import com.vepanimas.intellij.prisma.ide.schema.PrismaSchemaKind
import com.vepanimas.intellij.prisma.lang.PrismaConstants.BlockAttributes
import com.vepanimas.intellij.prisma.lang.PrismaConstants.FieldAttributes
import com.vepanimas.intellij.prisma.lang.PrismaConstants.PrimitiveTypes
import com.vepanimas.intellij.prisma.lang.psi.*

object PrismaFieldAttributesProvider : PrismaSchemaCompletionProvider() {
    override val kind: PrismaSchemaKind = PrismaSchemaKind.FIELD_ATTRIBUTE

    private val beforeFieldAttribute = psiElement().andOr(
        psiElement(PrismaFieldType::class.java),
        psiElement(PrismaFieldAttribute::class.java),
    )

    override val pattern: ElementPattern<out PsiElement> =
        psiElement().andOr(
            psiElement().withParent(
                psiElement(PsiErrorElement::class.java)
                    .afterSibling(beforeFieldAttribute)
            ),
            psiElement().withParent(
                psiElement(PrismaPathExpression::class.java).withParent(
                    psiElement(PrismaFieldAttribute::class.java)
                        .afterSiblingNewLinesAware(beforeFieldAttribute)
                )
            )
        ).inside(psiElement(PrismaModelDeclaration::class.java))


    override fun collectSchemaElements(
        parameters: CompletionParameters,
        context: ProcessingContext
    ): Collection<PrismaSchemaElement> {
        val field = findAssociatedField(parameters.originalPosition) ?: return emptyList()
        val typeReference = field.fieldType?.typeReference ?: return emptyList()
        val typeName = typeReference.referenceName
        val containingDeclaration = field.containingDeclaration as? PrismaModelDeclaration ?: return emptyList()
        val typeDeclaration = typeReference.resolve()

        val excluded = mutableSetOf<String>()

        if (typeDeclaration is PrismaTypeDeclaration) {
            excluded.add(FieldAttributes.DEFAULT)
            excluded.add(FieldAttributes.RELATION)
        }

        val isIdAllowed =
            typeName == PrimitiveTypes.INT || typeName == PrimitiveTypes.STRING || typeDeclaration is PrismaEnumDeclaration
        if (!isIdAllowed) {
            excluded.add(FieldAttributes.ID)
        }

        val isUpdatedAtAllowed = typeName == PrimitiveTypes.DATETIME
        if (!isUpdatedAtAllowed) {
            excluded.add(FieldAttributes.UPDATED_AT)
        }

        val existingAttributes = collectExistingAttributeNamesForDeclaration(containingDeclaration)
        if (FieldAttributes.ID in existingAttributes || BlockAttributes.ID in existingAttributes) {
            excluded.add(FieldAttributes.ID)
        }

        excluded.addAll(collectExistingAttributeNamesForField(field))

        return super.collectSchemaElements(parameters, context).filter { it.label !in excluded }
    }
}