// This is a generated file. Not intended for manual editing.
package com.vepanimas.intellij.prisma.lang.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface PrismaEnumDeclaration extends PrismaDeclaration {

  @NotNull
  List<PrismaBlockAttribute> getBlockAttributeList();

  @NotNull
  List<PrismaEnumValueDeclaration> getEnumValueDeclarationList();

  @NotNull
  PsiElement getIdentifier();

}
