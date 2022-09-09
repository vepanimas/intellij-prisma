package com.vepanimas.intellij.prisma.lang.psi.impl

import com.intellij.lang.ASTNode
import com.vepanimas.intellij.prisma.lang.PrismaConstants
import com.vepanimas.intellij.prisma.lang.psi.PrismaReferenceElement
import com.vepanimas.intellij.prisma.lang.psi.PrismaTypeReference

abstract class PrismaTypeReferenceMixin(node: ASTNode) :
    PrismaElementImpl(node),
    PrismaTypeReference,
    PrismaReferenceElement {

    override val referenceText: String?
        get() {
            val identifier = this.identifier
            if (identifier != null) {
                return identifier.text
            }

            return unsupportedType?.let { PrismaConstants.Types.UNSUPPORTED }
        }
}