package com.vepanimas.intellij.prisma.ide.completion.schema

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.ElementPattern
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.psi.PsiElement
import com.intellij.psi.util.parentOfType
import com.intellij.util.ProcessingContext
import com.intellij.util.castSafelyTo
import com.vepanimas.intellij.prisma.PrismaIcons
import com.vepanimas.intellij.prisma.ide.completion.PrismaCompletionProvider
import com.vepanimas.intellij.prisma.ide.schema.types.PrismaIndexAlgorithm
import com.vepanimas.intellij.prisma.ide.schema.types.PrismaNativeType.PostgreSQL
import com.vepanimas.intellij.prisma.ide.schema.types.PrismaOperatorClass
import com.vepanimas.intellij.prisma.lang.PrismaConstants
import com.vepanimas.intellij.prisma.lang.psi.*
import com.vepanimas.intellij.prisma.lang.types.*


object PrismaOperatorClassProvider : PrismaCompletionProvider() {
    override val pattern: ElementPattern<out PsiElement> = psiElement().withSuperParent(
        2,
        PrismaPsiPatterns.namedArgument(PrismaConstants.ParameterNames.OPS)
    )

    override fun addCompletions(
        parameters: CompletionParameters, context: ProcessingContext, result: CompletionResultSet
    ) {
        val indexAlgorithm = parameters.position.parentOfType<PrismaBlockAttribute>()?.getArgumentsList()
            ?.findArgumentByName(PrismaConstants.ParameterNames.TYPE)?.expression?.castSafelyTo<PrismaPathExpression>()
            ?.let { PrismaIndexAlgorithm.fromString(it.referenceName) } ?: return

        val fieldExpression =
            parameters.position.parentOfType<PrismaNamedArgument>()?.parentOfType<PrismaFunctionCall>() ?: return
        val declaration = fieldExpression.pathExpression.resolve() as? PrismaFieldDeclaration ?: return

        val declaredType = declaration.type
        val isList = declaredType.isList()
        val fieldType = declaredType.unwrapType()
        val nativeType = declaration.nativeType

        val classes = mutableListOf<PrismaOperatorClass>()

        when (indexAlgorithm) {
            PrismaIndexAlgorithm.Gist -> gist(classes, nativeType)
            PrismaIndexAlgorithm.Gin -> gin(classes, fieldType, isList)
            PrismaIndexAlgorithm.SpGist -> spGist(classes, fieldType, nativeType)
            PrismaIndexAlgorithm.Brin -> brin(classes, fieldType, nativeType)
            else -> {}
        }

        classes.map {
            LookupElementBuilder.create(it).withIcon(PrismaIcons.FIELD)
                .withTypeText(PrismaConstants.Types.OPERATOR_CLASS)
        }.forEach { result.addElement(it) }
    }

    private fun gist(
        classes: MutableList<PrismaOperatorClass>,
        nativeType: String?
    ) {
        if (nativeType == PostgreSQL.Inet) {
            classes.add(PrismaOperatorClass.InetOps)
        }
    }

    private fun gin(
        classes: MutableList<PrismaOperatorClass>,
        fieldType: PrismaType?,
        isList: Boolean
    ) {
        if (isList) {
            classes.add(PrismaOperatorClass.ArrayOps)
        } else if (fieldType is PrismaJsonType) {
            classes.add(PrismaOperatorClass.JsonbOps)
            classes.add(PrismaOperatorClass.JsonbPathOps)
        }
    }

    private fun spGist(
        classes: MutableList<PrismaOperatorClass>,
        fieldType: PrismaType?,
        nativeType: String?
    ) {
        if (nativeType == PostgreSQL.Inet) {
            classes.add(PrismaOperatorClass.InetOps)
        } else if (
            fieldType is PrismaStringType && (
                    nativeType == null ||
                            nativeType == PostgreSQL.Text ||
                            nativeType == PostgreSQL.VarChar)
        ) {
            classes.add(PrismaOperatorClass.TextOps)
        }
    }

    private fun brin(
        classes: MutableList<PrismaOperatorClass>,
        fieldType: PrismaType?,
        nativeType: String?
    ) {
        if (nativeType == PostgreSQL.Bit) {
            classes.add(PrismaOperatorClass.BitMinMaxOps)
        } else if (nativeType == PostgreSQL.VarBit) {
            classes.add(PrismaOperatorClass.VarBitMinMaxOps)
        } else if (nativeType == PostgreSQL.Char) {
            classes.add(PrismaOperatorClass.BpcharBloomOps)
            classes.add(PrismaOperatorClass.BpcharMinMaxOps)
        } else if (nativeType == PostgreSQL.Date) {
            classes.add(PrismaOperatorClass.DateBloomOps)
            classes.add(PrismaOperatorClass.DateMinMaxOps)
            classes.add(PrismaOperatorClass.DateMinMaxMultiOps)
        } else if (nativeType == PostgreSQL.Real) {
            classes.add(PrismaOperatorClass.Float4BloomOps)
            classes.add(PrismaOperatorClass.Float4MinMaxOps)
            classes.add(PrismaOperatorClass.Float4MinMaxMultiOps)
        } else if (fieldType is PrismaFloatType) {
            classes.add(PrismaOperatorClass.Float8BloomOps)
            classes.add(PrismaOperatorClass.Float8MinMaxOps)
            classes.add(PrismaOperatorClass.Float8MinMaxMultiOps)
        } else if (nativeType == PostgreSQL.Inet) {
            classes.add(PrismaOperatorClass.InetBloomOps)
            classes.add(PrismaOperatorClass.InetInclusionOps)
            classes.add(PrismaOperatorClass.InetMinMaxOps)
            classes.add(PrismaOperatorClass.InetMinMaxMultiOps)
        } else if (nativeType == PostgreSQL.SmallInt) {
            classes.add(PrismaOperatorClass.Int2BloomOps)
            classes.add(PrismaOperatorClass.Int2MinMaxOps)
            classes.add(PrismaOperatorClass.Int2MinMaxMultiOps)
        } else if (fieldType is PrismaIntType && (nativeType == null || nativeType == PostgreSQL.Integer)) {
            classes.add(PrismaOperatorClass.Int4BloomOps)
            classes.add(PrismaOperatorClass.Int4MinMaxOps)
            classes.add(PrismaOperatorClass.Int4MinMaxMultiOps)
        } else if (fieldType is PrismaBigIntType) {
            classes.add(PrismaOperatorClass.Int8BloomOps)
            classes.add(PrismaOperatorClass.Int8MinMaxOps)
            classes.add(PrismaOperatorClass.Int8MinMaxMultiOps)
        } else if (fieldType is PrismaDecimalType) {
            classes.add(PrismaOperatorClass.NumericBloomOps)
            classes.add(PrismaOperatorClass.NumericMinMaxOps)
            classes.add(PrismaOperatorClass.NumericMinMaxMultiOps)
        } else if (nativeType == PostgreSQL.Oid) {
            classes.add(PrismaOperatorClass.OidBloomOps)
            classes.add(PrismaOperatorClass.OidMinMaxOps)
            classes.add(PrismaOperatorClass.OidMinMaxMultiOps)
        } else if (fieldType is PrismaBytesType && (nativeType == null || nativeType == PostgreSQL.ByteA)) {
            classes.add(PrismaOperatorClass.ByteaBloomOps)
            classes.add(PrismaOperatorClass.ByteaMinMaxOps)
        } else if (fieldType is PrismaStringType && (
                    nativeType == null ||
                            nativeType == PostgreSQL.Text ||
                            nativeType == PostgreSQL.VarChar)
        ) {
            classes.add(PrismaOperatorClass.TextBloomOps)
            classes.add(PrismaOperatorClass.TextMinMaxOps)
        } else if (fieldType is PrismaDateTimeType && (
                    nativeType == null ||
                            nativeType == PostgreSQL.Timestamp)
        ) {
            classes.add(PrismaOperatorClass.TimestampBloomOps)
            classes.add(PrismaOperatorClass.TimestampMinMaxOps)
            classes.add(PrismaOperatorClass.TimestampMinMaxMultiOps)
        } else if (nativeType == PostgreSQL.Timestamptz) {
            classes.add(PrismaOperatorClass.TimestampTzBloomOps)
            classes.add(PrismaOperatorClass.TimestampTzMinMaxOps)
            classes.add(PrismaOperatorClass.TimestampTzMinMaxMultiOps)
        } else if (nativeType == PostgreSQL.Time) {
            classes.add(PrismaOperatorClass.TimeBloomOps)
            classes.add(PrismaOperatorClass.TimeMinMaxOps)
            classes.add(PrismaOperatorClass.TimeMinMaxMultiOps)
        } else if (nativeType == PostgreSQL.Timetz) {
            classes.add(PrismaOperatorClass.TimeTzBloomOps)
            classes.add(PrismaOperatorClass.TimeTzMinMaxOps)
            classes.add(PrismaOperatorClass.TimeTzMinMaxMultiOps)
        } else if (nativeType == PostgreSQL.Uuid) {
            classes.add(PrismaOperatorClass.UuidBloomOps)
            classes.add(PrismaOperatorClass.UuidMinMaxOps)
            classes.add(PrismaOperatorClass.UuidMinMaxMultiOps)
        }
    }
}