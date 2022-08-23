// This is a generated file. Not intended for manual editing.
package com.vepanimas.intellij.prisma.lang.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.vepanimas.intellij.prisma.lang.psi.impl.*;

public interface PrismaElementTypes {

  IElementType DECLARATION = new PrismaElementType("DECLARATION");
  IElementType MODEL_DECLARATION = new PrismaElementType("MODEL_DECLARATION");

  IElementType IDENTIFIER = new PrismaTokenType("IDENTIFIER");
  IElementType MODEL = new PrismaTokenType("model");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == DECLARATION) {
        return new PrismaDeclarationImpl(node);
      }
      else if (type == MODEL_DECLARATION) {
        return new PrismaModelDeclarationImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
