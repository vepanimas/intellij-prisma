// This is a generated file. Not intended for manual editing.
package com.vepanimas.intellij.prisma.lang.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface PrismaFieldDeclaration extends PrismaMemberDeclaration {

  @NotNull
  List<PrismaFieldAttribute> getFieldAttributeList();

  @Nullable
  PrismaFieldType getFieldType();

  @NotNull
  PsiElement getIdentifier();

}
