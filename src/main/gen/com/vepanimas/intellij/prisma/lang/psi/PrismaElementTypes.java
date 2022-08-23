// This is a generated file. Not intended for manual editing.
package com.vepanimas.intellij.prisma.lang.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.vepanimas.intellij.prisma.lang.psi.impl.*;

public interface PrismaElementTypes {

  IElementType ARGUMENT = new PrismaElementType("ARGUMENT");
  IElementType ARGUMENTS_LIST = new PrismaElementType("ARGUMENTS_LIST");
  IElementType ARRAY_EXPRESSION = new PrismaElementType("ARRAY_EXPRESSION");
  IElementType BASE_TYPE = new PrismaElementType("BASE_TYPE");
  IElementType BLOCK_ATTRIBUTE = new PrismaElementType("BLOCK_ATTRIBUTE");
  IElementType CONFIG_BLOCK = new PrismaElementType("CONFIG_BLOCK");
  IElementType EMPTY_ARGUMENT = new PrismaElementType("EMPTY_ARGUMENT");
  IElementType ENUM_DECLARATION = new PrismaElementType("ENUM_DECLARATION");
  IElementType ENUM_VALUE_DECLARATION = new PrismaElementType("ENUM_VALUE_DECLARATION");
  IElementType EXPRESSION = new PrismaElementType("EXPRESSION");
  IElementType FIELD_ATTRIBUTE = new PrismaElementType("FIELD_ATTRIBUTE");
  IElementType FIELD_DECLARATION = new PrismaElementType("FIELD_DECLARATION");
  IElementType FIELD_TYPE = new PrismaElementType("FIELD_TYPE");
  IElementType FUNCTION_CALL = new PrismaElementType("FUNCTION_CALL");
  IElementType KEY_VALUE = new PrismaElementType("KEY_VALUE");
  IElementType LEGACY_LIST_TYPE = new PrismaElementType("LEGACY_LIST_TYPE");
  IElementType LEGACY_REQUIRED_TYPE = new PrismaElementType("LEGACY_REQUIRED_TYPE");
  IElementType LIST_TYPE = new PrismaElementType("LIST_TYPE");
  IElementType MODEL_DECLARATION = new PrismaElementType("MODEL_DECLARATION");
  IElementType NAMED_ARGUMENT = new PrismaElementType("NAMED_ARGUMENT");
  IElementType OPTIONAL_TYPE = new PrismaElementType("OPTIONAL_TYPE");
  IElementType PATH = new PrismaElementType("PATH");
  IElementType TYPE_ALIAS = new PrismaElementType("TYPE_ALIAS");
  IElementType UNSUPPORTED_OPTIONAL_LIST_TYPE = new PrismaElementType("UNSUPPORTED_OPTIONAL_LIST_TYPE");
  IElementType UNSUPPORTED_TYPE = new PrismaElementType("UNSUPPORTED_TYPE");

  IElementType AT = new PrismaTokenType("@");
  IElementType ATAT = new PrismaTokenType("@@");
  IElementType COLON = new PrismaTokenType(":");
  IElementType COMMA = new PrismaTokenType(",");
  IElementType DATASOURCE = new PrismaTokenType("datasource");
  IElementType DOT = new PrismaTokenType(".");
  IElementType ENUM = new PrismaTokenType("enum");
  IElementType EQ = new PrismaTokenType("=");
  IElementType EXCL = new PrismaTokenType("!");
  IElementType GENERATOR = new PrismaTokenType("generator");
  IElementType IDENTIFIER = new PrismaTokenType("IDENTIFIER");
  IElementType LBRACE = new PrismaTokenType("{");
  IElementType LBRACKET = new PrismaTokenType("[");
  IElementType LPAREN = new PrismaTokenType("(");
  IElementType MODEL = new PrismaTokenType("model");
  IElementType NUMERIC_LITERAL = new PrismaTokenType("NUMERIC_LITERAL");
  IElementType QUEST = new PrismaTokenType("?");
  IElementType RBRACE = new PrismaTokenType("}");
  IElementType RBRACKET = new PrismaTokenType("]");
  IElementType RPAREN = new PrismaTokenType(")");
  IElementType STRING_LITERAL = new PrismaTokenType("STRING_LITERAL");
  IElementType TYPE = new PrismaTokenType("type");
  IElementType UNSUPPORTED = new PrismaTokenType("Unsupported");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == ARGUMENT) {
        return new PrismaArgumentImpl(node);
      }
      else if (type == ARGUMENTS_LIST) {
        return new PrismaArgumentsListImpl(node);
      }
      else if (type == ARRAY_EXPRESSION) {
        return new PrismaArrayExpressionImpl(node);
      }
      else if (type == BASE_TYPE) {
        return new PrismaBaseTypeImpl(node);
      }
      else if (type == BLOCK_ATTRIBUTE) {
        return new PrismaBlockAttributeImpl(node);
      }
      else if (type == CONFIG_BLOCK) {
        return new PrismaConfigBlockImpl(node);
      }
      else if (type == EMPTY_ARGUMENT) {
        return new PrismaEmptyArgumentImpl(node);
      }
      else if (type == ENUM_DECLARATION) {
        return new PrismaEnumDeclarationImpl(node);
      }
      else if (type == ENUM_VALUE_DECLARATION) {
        return new PrismaEnumValueDeclarationImpl(node);
      }
      else if (type == EXPRESSION) {
        return new PrismaExpressionImpl(node);
      }
      else if (type == FIELD_ATTRIBUTE) {
        return new PrismaFieldAttributeImpl(node);
      }
      else if (type == FIELD_DECLARATION) {
        return new PrismaFieldDeclarationImpl(node);
      }
      else if (type == FIELD_TYPE) {
        return new PrismaFieldTypeImpl(node);
      }
      else if (type == FUNCTION_CALL) {
        return new PrismaFunctionCallImpl(node);
      }
      else if (type == KEY_VALUE) {
        return new PrismaKeyValueImpl(node);
      }
      else if (type == LEGACY_LIST_TYPE) {
        return new PrismaLegacyListTypeImpl(node);
      }
      else if (type == LEGACY_REQUIRED_TYPE) {
        return new PrismaLegacyRequiredTypeImpl(node);
      }
      else if (type == LIST_TYPE) {
        return new PrismaListTypeImpl(node);
      }
      else if (type == MODEL_DECLARATION) {
        return new PrismaModelDeclarationImpl(node);
      }
      else if (type == NAMED_ARGUMENT) {
        return new PrismaNamedArgumentImpl(node);
      }
      else if (type == OPTIONAL_TYPE) {
        return new PrismaOptionalTypeImpl(node);
      }
      else if (type == PATH) {
        return new PrismaPathImpl(node);
      }
      else if (type == TYPE_ALIAS) {
        return new PrismaTypeAliasImpl(node);
      }
      else if (type == UNSUPPORTED_OPTIONAL_LIST_TYPE) {
        return new PrismaUnsupportedOptionalListTypeImpl(node);
      }
      else if (type == UNSUPPORTED_TYPE) {
        return new PrismaUnsupportedTypeImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}