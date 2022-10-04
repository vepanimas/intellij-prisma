package com.vepanimas.intellij.prisma.ide.lsp

import com.intellij.execution.ExecutionException
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.javascript.nodejs.interpreter.NodeCommandLineConfigurator
import com.intellij.javascript.nodejs.interpreter.NodeJsInterpreterManager
import com.intellij.javascript.nodejs.interpreter.local.NodeJsLocalInterpreter
import com.intellij.javascript.nodejs.interpreter.wsl.WslNodeInterpreter
import com.intellij.lang.javascript.service.JSLanguageServiceUtil
import com.intellij.lsp.LspServerDescriptorBase
import com.intellij.lsp.SocketModeDescriptor
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import java.io.File

class PrismaLspServerDescriptor(project: Project, root: VirtualFile) : LspServerDescriptorBase(project, root) {
    override fun createStdioServerStartingCommandLine(): GeneralCommandLine {
        val interpreter = NodeJsInterpreterManager.getInstance(project).interpreter
        if (interpreter !is NodeJsLocalInterpreter && interpreter !is WslNodeInterpreter) {
            throw ExecutionException("Local or WSL Node.js interpreter not configured.")
        }

        val lsp = JSLanguageServiceUtil.getPluginDirectory(javaClass, "prisma-language-server.js")
        if (!lsp.exists()) {
            throw ExecutionException("Language server is missing.")
        }

        return GeneralCommandLine().apply {
            withParentEnvironmentType(GeneralCommandLine.ParentEnvironmentType.CONSOLE)
            withCharset(Charsets.UTF_8)
            workDirectory = File(root.path)
            addParameter(lsp.path)
            addParameter("--stdio")

            NodeCommandLineConfigurator.find(interpreter)
                .configure(this, NodeCommandLineConfigurator.defaultOptions(project))
        }
    }

    override fun getSocketModeDescriptor(): SocketModeDescriptor? = null

    override fun useGenericCompletion(): Boolean = false

    override fun useGenericHighlighting(): Boolean = false

    override fun useGenericNavigation(): Boolean = false
}