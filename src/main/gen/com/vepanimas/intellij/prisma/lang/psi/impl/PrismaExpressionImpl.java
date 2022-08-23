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

public class PrismaExpressionImpl extends PrismaElementImpl implements PrismaExpression {

  public PrismaExpressionImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PrismaVisitor visitor) {
    visitor.visitExpression(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PrismaVisitor) accept((PrismaVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PrismaArrayExpression getArrayExpression() {
    return findChildByClass(PrismaArrayExpression.class);
  }

  @Override
  @Nullable
  public PrismaFunctionCall getFunctionCall() {
    return findChildByClass(PrismaFunctionCall.class);
  }

  @Override
  @Nullable
  public PrismaPath getPath() {
    return findChildByClass(PrismaPath.class);
  }

  @Override
  @Nullable
  public PsiElement getNumericLiteral() {
    return findChildByType(NUMERIC_LITERAL);
  }

  @Override
  @Nullable
  public PsiElement getStringLiteral() {
    return findChildByType(STRING_LITERAL);
  }

}
