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
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.vfs.VirtualFile
import java.io.File

class PrismaLspServerDescriptor(project: Project, root: VirtualFile) : LspServerDescriptorBase(project, root) {
    override fun createStdioServerStartingCommandLine(): GeneralCommandLine {
        val interpreter = NodeJsInterpreterManager.getInstance(project).interpreter
        if (interpreter !is NodeJsLocalInterpreter && interpreter !is WslNodeInterpreter) {
            throw ExecutionException("Local or WSL Node.js interpreter not configured.")
        }

        val targetDir = JSLanguageServiceUtil.getPluginDirectory(javaClass, "languageServer")
        val lsp = File(targetDir, "prisma-language-server.js")
        val wasm = File(targetDir, "prisma_fmt_build_bg.wasm")

        FileUtil.ensureExists(targetDir)
        javaClass.classLoader.getResourceAsStream("languageServer/prisma-language-server.js")!!.use { lspSource ->
            lsp.outputStream().use { FileUtil.copy(lspSource, it) }
        }
        javaClass.classLoader.getResourceAsStream("languageServer/prisma_fmt_build_bg.wasm")!!.use { wasmSource ->
            wasm.outputStream().use { FileUtil.copy(wasmSource, it) }
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
}