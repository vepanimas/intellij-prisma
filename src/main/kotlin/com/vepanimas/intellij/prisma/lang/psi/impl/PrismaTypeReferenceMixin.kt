package com.vepanimas.intellij.prisma.lang.psi.impl

import com.intellij.lang.ASTNode
import com.vepanimas.intellij.prisma.lang.psi.PrismaConstants
import com.vepanimas.intellij.prisma.lang.psi.PrismaReferencingElement
import com.vepanimas.intellij.prisma.lang.psi.PrismaTypeReference

abstract class PrismaTypeReferenceMixin(node: ASTNode) :
    PrismaElementImpl(node),
    PrismaTypeReference,
    PrismaReferencingElement {

    override val referenceText: String?
        get() {
            val identifier = this.identifier
            if (identifier != null) {
                return identifier.text
            }

            return unsupportedType?.let { PrismaConstants.Types.UNSUPPORTED }
        }
}