package com.vepanimas.intellij.prisma.lang.parser

import com.intellij.lang.PsiBuilder
import com.intellij.lang.parser.GeneratedParserUtilBase
import com.vepanimas.intellij.prisma.PRISMA_BUNDLE
import com.vepanimas.intellij.prisma.PrismaBundle
import org.jetbrains.annotations.PropertyKey

class PrismaParserUtil : GeneratedParserUtilBase() {
    companion object {
        @JvmStatic
        fun consumeWithError(
            b: PsiBuilder,
            level: Int,
            rule: Parser,
            @PropertyKey(resourceBundle = PRISMA_BUNDLE) messageKey: String
        ): Boolean {
            val marker = b.mark()
            val result = rule.parse(b, level)
            if (result) {
                marker.error(PrismaBundle.message(messageKey))
            } else {
                marker.rollbackTo()
            }
            return result
        }
    }
}