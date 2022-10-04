package com.vepanimas.intellij.prisma.ide.structureview

import com.intellij.ide.structureView.StructureViewModel
import com.intellij.ide.structureView.StructureViewModelBase
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.Sorter
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiFile
import com.vepanimas.intellij.prisma.lang.psi.PrismaDeclaration
import com.vepanimas.intellij.prisma.lang.psi.PrismaMemberDeclaration


class PrismaStructureViewModel(psiFile: PsiFile, editor: Editor?) :
    StructureViewModelBase(psiFile, editor, PrismaStructureViewElement(psiFile)),
    StructureViewModel.ElementInfoProvider {

    override fun isAlwaysShowsPlus(element: StructureViewTreeElement): Boolean = false

    override fun isAlwaysLeaf(element: StructureViewTreeElement): Boolean = false

    override fun getSorters(): Array<Sorter> {
        return arrayOf(Sorter.ALPHA_SORTER)
    }

    override fun getSuitableClasses(): Array<Class<*>> {
        return arrayOf(PrismaDeclaration::class.java, PrismaMemberDeclaration::class.java)
    }
}