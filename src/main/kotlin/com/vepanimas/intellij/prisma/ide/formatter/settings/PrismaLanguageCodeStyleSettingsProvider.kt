package com.vepanimas.intellij.prisma.ide.formatter.settings

import com.intellij.application.options.*
import com.intellij.lang.Language
import com.intellij.psi.codeStyle.*
import com.intellij.psi.codeStyle.CodeStyleSettingsCustomizable.CommenterOption
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider.SettingsType.COMMENTER_SETTINGS
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider.SettingsType.INDENT_SETTINGS
import com.vepanimas.intellij.prisma.lang.PrismaLanguage

class PrismaLanguageCodeStyleSettingsProvider : LanguageCodeStyleSettingsProvider() {
    override fun getLanguage(): Language = PrismaLanguage

    override fun getCodeSample(settingsType: SettingsType): String = when (settingsType) {
        INDENT_SETTINGS, COMMENTER_SETTINGS -> CODE_SAMPLE
        else -> ""
    }

    override fun getConfigurableDisplayName(): String = PrismaLanguage.displayName

    override fun createCustomSettings(settings: CodeStyleSettings): CustomCodeStyleSettings =
        PrismaCodeStyleSettings(settings)

    override fun createConfigurable(
        baseSettings: CodeStyleSettings,
        modelSettings: CodeStyleSettings
    ): CodeStyleConfigurable {
        return object : CodeStyleAbstractConfigurable(baseSettings, modelSettings, configurableDisplayName) {
            override fun createPanel(settings: CodeStyleSettings): CodeStyleAbstractPanel =
                PrismaCodeStyleMainPanel(currentSettings, settings)
        }
    }

    override fun getIndentOptionsEditor(): IndentOptionsEditor = IndentOptionsEditor()

    override fun customizeSettings(consumer: CodeStyleSettingsCustomizable, settingsType: SettingsType) {
        if (settingsType == COMMENTER_SETTINGS) {
            consumer.showStandardOptions(
                CommenterOption.LINE_COMMENT_ADD_SPACE.name,
                // TODO: [enable after sinceBuild=221] CommenterOption.LINE_COMMENT_ADD_SPACE_ON_REFORMAT.name,
                CommenterOption.LINE_COMMENT_AT_FIRST_COLUMN.name,
            )
        }
    }

    override fun customizeDefaults(
        commonSettings: CommonCodeStyleSettings,
        indentOptions: CommonCodeStyleSettings.IndentOptions
    ) {
        commonSettings.apply {
            LINE_COMMENT_AT_FIRST_COLUMN = false
            LINE_COMMENT_ADD_SPACE = true
            // TODO: [enable after sinceBuild=221] LINE_COMMENT_ADD_SPACE_ON_REFORMAT = true
        }
    }
}

class PrismaCodeStyleMainPanel(currentSettings: CodeStyleSettings, settings: CodeStyleSettings) :
    TabbedLanguageCodeStylePanel(PrismaLanguage, currentSettings, settings) {
    override fun initTabs(settings: CodeStyleSettings) {
        addIndentOptionsTab(settings)
        addTab(GenerationCodeStylePanel(settings, PrismaLanguage))
    }
}

private const val CODE_SAMPLE = """
generator client {
    provider = "prisma-client-js"
}

datasource db {
    provider = "postgresql"
    url      = env("DATABASE_URL")
}

model User {
    id          Int                @id @default(autoincrement())
    email       String             @unique
    firstName   String?
    lastName    String?
    social      Json?
    isAdmin     Boolean            @default(false)
    testResults TestResult[]       @relation(name: "results")
    testsGraded TestResult[]       @relation(name: "graded")
}    
"""