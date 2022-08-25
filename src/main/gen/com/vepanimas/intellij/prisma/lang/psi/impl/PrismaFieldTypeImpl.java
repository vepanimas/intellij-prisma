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

public class PrismaFieldTypeImpl extends PrismaElementImpl implements PrismaFieldType {

  public PrismaFieldTypeImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PrismaVisitor visitor) {
    visitor.visitFieldType(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PrismaVisitor) accept((PrismaVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PrismaLegacyListType getLegacyListType() {
    return findChildByClass(PrismaLegacyListType.class);
  }

  @Override
  @Nullable
  public PrismaLegacyRequiredType getLegacyRequiredType() {
    return findChildByClass(PrismaLegacyRequiredType.class);
  }

  @Override
  @Nullable
  public PrismaListType getListType() {
    return findChildByClass(PrismaListType.class);
  }

  @Override
  @Nullable
  public PrismaOptionalType getOptionalType() {
    return findChildByClass(PrismaOptionalType.class);
  }

  @Override
  @Nullable
  public PrismaTypeReference getTypeReference() {
    return findChildByClass(PrismaTypeReference.class);
  }

  @Override
  @Nullable
  public PrismaUnsupportedOptionalListType getUnsupportedOptionalListType() {
    return findChildByClass(PrismaUnsupportedOptionalListType.class);
  }

}
