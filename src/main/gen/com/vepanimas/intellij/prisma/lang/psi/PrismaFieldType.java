// This is a generated file. Not intended for manual editing.
package com.vepanimas.intellij.prisma.lang.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface PrismaFieldType extends PrismaElement {

  @Nullable
  PrismaLegacyListType getLegacyListType();

  @Nullable
  PrismaLegacyRequiredType getLegacyRequiredType();

  @Nullable
  PrismaListType getListType();

  @Nullable
  PrismaOptionalType getOptionalType();

  @Nullable
  PrismaTypeReference getTypeReference();

  @Nullable
  PrismaUnsupportedOptionalListType getUnsupportedOptionalListType();

}
