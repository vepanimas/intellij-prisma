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

public class PrismaTypeAliasImpl extends PrismaElementImpl implements PrismaTypeAlias {

  public PrismaTypeAliasImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PrismaVisitor visitor) {
    visitor.visitTypeAlias(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PrismaVisitor) accept((PrismaVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PrismaBaseType getBaseType() {
    return findChildByClass(PrismaBaseType.class);
  }

  @Override
  @NotNull
  public List<PrismaFieldAttribute> getFieldAttributeList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PrismaFieldAttribute.class);
  }

  @Override
  @NotNull
  public PsiElement getIdentifier() {
    return findNotNullChildByType(IDENTIFIER);
  }

}
