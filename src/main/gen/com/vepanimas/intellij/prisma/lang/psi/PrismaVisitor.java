// This is a generated file. Not intended for manual editing.
package com.vepanimas.intellij.prisma.lang.psi;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;

public class PrismaVisitor extends PsiElementVisitor {

  public void visitArgument(@NotNull PrismaArgument o) {
    visitElement(o);
  }

  public void visitArgumentsList(@NotNull PrismaArgumentsList o) {
    visitElement(o);
  }

  public void visitArrayExpression(@NotNull PrismaArrayExpression o) {
    visitExpression(o);
  }

  public void visitBlockAttribute(@NotNull PrismaBlockAttribute o) {
    visitElement(o);
  }

  public void visitDatasourceDeclaration(@NotNull PrismaDatasourceDeclaration o) {
    visitDeclaration(o);
  }

  public void visitEnumDeclaration(@NotNull PrismaEnumDeclaration o) {
    visitDeclaration(o);
  }

  public void visitEnumDeclarationBlock(@NotNull PrismaEnumDeclarationBlock o) {
    visitBlock(o);
  }

  public void visitEnumValueDeclaration(@NotNull PrismaEnumValueDeclaration o) {
    visitMemberDeclaration(o);
  }

  public void visitExpression(@NotNull PrismaExpression o) {
    visitElement(o);
  }

  public void visitFieldAttribute(@NotNull PrismaFieldAttribute o) {
    visitElement(o);
  }

  public void visitFieldDeclaration(@NotNull PrismaFieldDeclaration o) {
    visitMemberDeclaration(o);
  }

  public void visitFieldDeclarationBlock(@NotNull PrismaFieldDeclarationBlock o) {
    visitBlock(o);
  }

  public void visitFieldType(@NotNull PrismaFieldType o) {
    visitElement(o);
  }

  public void visitFunctionCall(@NotNull PrismaFunctionCall o) {
    visitExpression(o);
  }

  public void visitGeneratorDeclaration(@NotNull PrismaGeneratorDeclaration o) {
    visitDeclaration(o);
  }

  public void visitKeyValue(@NotNull PrismaKeyValue o) {
    visitMemberDeclaration(o);
  }

  public void visitKeyValueBlock(@NotNull PrismaKeyValueBlock o) {
    visitBlock(o);
  }

  public void visitLegacyListType(@NotNull PrismaLegacyListType o) {
    visitElement(o);
  }

  public void visitLegacyRequiredType(@NotNull PrismaLegacyRequiredType o) {
    visitElement(o);
  }

  public void visitListType(@NotNull PrismaListType o) {
    visitElement(o);
  }

  public void visitLiteralExpression(@NotNull PrismaLiteralExpression o) {
    visitExpression(o);
  }

  public void visitModelDeclaration(@NotNull PrismaModelDeclaration o) {
    visitEntityDeclaration(o);
    // visitDeclaration(o);
  }

  public void visitNamedArgument(@NotNull PrismaNamedArgument o) {
    visitElement(o);
  }

  public void visitOptionalType(@NotNull PrismaOptionalType o) {
    visitElement(o);
  }

  public void visitPath(@NotNull PrismaPath o) {
    visitElement(o);
  }

  public void visitPathExpression(@NotNull PrismaPathExpression o) {
    visitExpression(o);
  }

  public void visitTypeAlias(@NotNull PrismaTypeAlias o) {
    visitDeclaration(o);
  }

  public void visitTypeDeclaration(@NotNull PrismaTypeDeclaration o) {
    visitEntityDeclaration(o);
    // visitDeclaration(o);
  }

  public void visitTypeReference(@NotNull PrismaTypeReference o) {
    visitReferencingElement(o);
  }

  public void visitUnsupportedOptionalListType(@NotNull PrismaUnsupportedOptionalListType o) {
    visitElement(o);
  }

  public void visitUnsupportedType(@NotNull PrismaUnsupportedType o) {
    visitElement(o);
  }

  public void visitBlock(@NotNull PrismaBlock o) {
    visitElement(o);
  }

  public void visitDeclaration(@NotNull PrismaDeclaration o) {
    visitElement(o);
  }

  public void visitEntityDeclaration(@NotNull PrismaEntityDeclaration o) {
    visitElement(o);
  }

  public void visitMemberDeclaration(@NotNull PrismaMemberDeclaration o) {
    visitElement(o);
  }

  public void visitReferencingElement(@NotNull PrismaReferencingElement o) {
    visitElement(o);
  }

  public void visitElement(@NotNull PrismaElement o) {
    super.visitElement(o);
  }

}
