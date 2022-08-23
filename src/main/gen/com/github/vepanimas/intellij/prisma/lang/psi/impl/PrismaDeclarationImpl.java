// This is a generated file. Not intended for manual editing.
package com.github.vepanimas.intellij.prisma.lang.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.github.vepanimas.intellij.prisma.lang.psi.PrismaElementTypes.*;
import com.github.vepanimas.intellij.prisma.lang.psi.*;

public class PrismaDeclarationImpl extends PrismaElementImpl implements PrismaDeclaration {

  public PrismaDeclarationImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PrismaVisitor visitor) {
    visitor.visitDeclaration(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PrismaVisitor) accept((PrismaVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public PrismaModelDeclaration getModelDeclaration() {
    return findNotNullChildByClass(PrismaModelDeclaration.class);
  }

}
