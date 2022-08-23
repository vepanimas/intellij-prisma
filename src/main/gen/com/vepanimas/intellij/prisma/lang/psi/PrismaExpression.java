// This is a generated file. Not intended for manual editing.
package com.vepanimas.intellij.prisma.lang.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface PrismaExpression extends PrismaElement {

  @Nullable
  PrismaArrayExpression getArrayExpression();

  @Nullable
  PrismaFunctionCall getFunctionCall();

  @Nullable
  PrismaPath getPath();

  @Nullable
  PsiElement getNumericLiteral();

  @Nullable
  PsiElement getStringLiteral();

}
