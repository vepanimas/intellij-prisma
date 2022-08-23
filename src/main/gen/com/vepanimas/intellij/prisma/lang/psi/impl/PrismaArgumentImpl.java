// This is a generated file. Not intended for manual editing.
package com.vepanimas.intellij.prisma.lang.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.vepanimas.intellij.prisma.lang.psi.PrismaElementTypes.*;
import com.vepanimas.intellij.prisma.lang.psi.*;

public class PrismaArgumentImpl extends PrismaElementImpl implements PrismaArgument {

  public PrismaArgumentImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PrismaVisitor visitor) {
    visitor.visitArgument(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PrismaVisitor) accept((PrismaVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PrismaEmptyArgument getEmptyArgument() {
    return findChildByClass(PrismaEmptyArgument.class);
  }

  @Override
  @Nullable
  public PrismaExpression getExpression() {
    return findChildByClass(PrismaExpression.class);
  }

  @Override
  @Nullable
  public PrismaNamedArgument getNamedArgument() {
    return findChildByClass(PrismaNamedArgument.class);
  }

}