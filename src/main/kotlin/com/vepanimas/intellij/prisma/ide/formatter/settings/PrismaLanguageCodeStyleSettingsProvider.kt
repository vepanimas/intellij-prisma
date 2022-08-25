package com.vepanimas.intellij.prisma.ide.formatter.settings

import com.intellij.lang.Language
import com.intellij.psi.codeStyle.*
import com.intellij.psi.codeStyle.CodeStyleSettingsCustomizable.CommenterOption
import com.vepanimas.intellij.prisma.lang.PrismaLanguage

class PrismaLanguageCodeStyleSettingsProvider : LanguageCodeStyleSettingsProvider() {
    override fun getLanguage(): Language = PrismaLanguage

    override fun getCodeSample(settingsType: SettingsType): String? = null

    override fun createCustomSettings(settings: CodeStyleSettings): CustomCodeStyleSettings? =
        PrismaCodeStyleSettings(settings)

    override fun customizeSettings(consumer: CodeStyleSettingsCustomizable, settingsType: SettingsType) {
        if (settingsType == SettingsType.COMMENTER_SETTINGS) {
            consumer.showStandardOptions(
                CommenterOption.LINE_COMMENT_ADD_SPACE.name,
                CommenterOption.LINE_COMMENT_AT_FIRST_COLUMN.name,
            )
        }
    }

    override fun customizeDefaults(
        commonSettings: CommonCodeStyleSettings,
        indentOptions: CommonCodeStyleSettings.IndentOptions
    ) {
        commonSettings.LINE_COMMENT_AT_FIRST_COLUMN = false
        commonSettings.LINE_COMMENT_ADD_SPACE = true
    }
}