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
    visitElement(o);
  }

  public void visitBaseType(@NotNull PrismaBaseType o) {
    visitElement(o);
  }

  public void visitBlockAttribute(@NotNull PrismaBlockAttribute o) {
    visitElement(o);
  }

  public void visitDatasourceDeclaration(@NotNull PrismaDatasourceDeclaration o) {
    visitDeclaration(o);
  }

  public void visitEmptyArgument(@NotNull PrismaEmptyArgument o) {
    visitElement(o);
  }

  public void visitEnumDeclaration(@NotNull PrismaEnumDeclaration o) {
    visitDeclaration(o);
  }

  public void visitEnumValueDeclaration(@NotNull PrismaEnumValueDeclaration o) {
    visitElement(o);
  }

  public void visitExpression(@NotNull PrismaExpression o) {
    visitElement(o);
  }

  public void visitFieldAttribute(@NotNull PrismaFieldAttribute o) {
    visitElement(o);
  }

  public void visitFieldDeclaration(@NotNull PrismaFieldDeclaration o) {
    visitElement(o);
  }

  public void visitFieldType(@NotNull PrismaFieldType o) {
    visitElement(o);
  }

  public void visitFunctionCall(@NotNull PrismaFunctionCall o) {
    visitElement(o);
  }

  public void visitGeneratorDeclaration(@NotNull PrismaGeneratorDeclaration o) {
    visitDeclaration(o);
  }

  public void visitKeyValue(@NotNull PrismaKeyValue o) {
    visitElement(o);
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

  public void visitModelDeclaration(@NotNull PrismaModelDeclaration o) {
    visitFieldsContainer(o);
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

  public void visitTypeAlias(@NotNull PrismaTypeAlias o) {
    visitDeclaration(o);
  }

  public void visitTypeDeclaration(@NotNull PrismaTypeDeclaration o) {
    visitFieldsContainer(o);
    // visitDeclaration(o);
  }

  public void visitUnsupportedOptionalListType(@NotNull PrismaUnsupportedOptionalListType o) {
    visitElement(o);
  }

  public void visitUnsupportedType(@NotNull PrismaUnsupportedType o) {
    visitElement(o);
  }

  public void visitDeclaration(@NotNull PrismaDeclaration o) {
    visitElement(o);
  }

  public void visitFieldsContainer(@NotNull PrismaFieldsContainer o) {
    visitElement(o);
  }

  public void visitElement(@NotNull PrismaElement o) {
    super.visitElement(o);
  }

}
