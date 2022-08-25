// This is a generated file. Not intended for manual editing.
package com.vepanimas.intellij.prisma.lang.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static com.vepanimas.intellij.prisma.lang.psi.PrismaElementTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class PrismaParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return Schema(b, l + 1);
  }

  /* ********************************************************** */
  // NamedArgument | Expression
  public static boolean Argument(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Argument")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ARGUMENT, "<argument>");
    r = NamedArgument(b, l + 1);
    if (!r) r = Expression(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '(' [Argument (',' Argument)*] ','? ')'
  public static boolean ArgumentsList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ArgumentsList")) return false;
    if (!nextTokenIs(b, LPAREN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LPAREN);
    r = r && ArgumentsList_1(b, l + 1);
    r = r && ArgumentsList_2(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, ARGUMENTS_LIST, r);
    return r;
  }

  // [Argument (',' Argument)*]
  private static boolean ArgumentsList_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ArgumentsList_1")) return false;
    ArgumentsList_1_0(b, l + 1);
    return true;
  }

  // Argument (',' Argument)*
  private static boolean ArgumentsList_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ArgumentsList_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Argument(b, l + 1);
    r = r && ArgumentsList_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (',' Argument)*
  private static boolean ArgumentsList_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ArgumentsList_1_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!ArgumentsList_1_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ArgumentsList_1_0_1", c)) break;
    }
    return true;
  }

  // ',' Argument
  private static boolean ArgumentsList_1_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ArgumentsList_1_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && Argument(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ','?
  private static boolean ArgumentsList_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ArgumentsList_2")) return false;
    consumeToken(b, COMMA);
    return true;
  }

  /* ********************************************************** */
  // '[' [Expression (',' Expression)*] ']'
  public static boolean ArrayExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ArrayExpression")) return false;
    if (!nextTokenIs(b, LBRACKET)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LBRACKET);
    r = r && ArrayExpression_1(b, l + 1);
    r = r && consumeToken(b, RBRACKET);
    exit_section_(b, m, ARRAY_EXPRESSION, r);
    return r;
  }

  // [Expression (',' Expression)*]
  private static boolean ArrayExpression_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ArrayExpression_1")) return false;
    ArrayExpression_1_0(b, l + 1);
    return true;
  }

  // Expression (',' Expression)*
  private static boolean ArrayExpression_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ArrayExpression_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Expression(b, l + 1);
    r = r && ArrayExpression_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (',' Expression)*
  private static boolean ArrayExpression_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ArrayExpression_1_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!ArrayExpression_1_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "ArrayExpression_1_0_1", c)) break;
    }
    return true;
  }

  // ',' Expression
  private static boolean ArrayExpression_1_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ArrayExpression_1_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && Expression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // UnsupportedType | Identifier
  public static boolean BaseType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "BaseType")) return false;
    if (!nextTokenIs(b, "<base type>", IDENTIFIER, UNSUPPORTED)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, BASE_TYPE, "<base type>");
    r = UnsupportedType(b, l + 1);
    if (!r) r = Identifier(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '@@' Path ArgumentsList?
  public static boolean BlockAttribute(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "BlockAttribute")) return false;
    if (!nextTokenIs(b, ATAT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ATAT);
    r = r && Path(b, l + 1);
    r = r && BlockAttribute_2(b, l + 1);
    exit_section_(b, m, BLOCK_ATTRIBUTE, r);
    return r;
  }

  // ArgumentsList?
  private static boolean BlockAttribute_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "BlockAttribute_2")) return false;
    ArgumentsList(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // DATASOURCE Identifier KeyValueBlock
  public static boolean DatasourceDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "DatasourceDeclaration")) return false;
    if (!nextTokenIs(b, DATASOURCE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DATASOURCE);
    r = r && Identifier(b, l + 1);
    r = r && KeyValueBlock(b, l + 1);
    exit_section_(b, m, DATASOURCE_DECLARATION, r);
    return r;
  }

  /* ********************************************************** */
  // ModelDeclaration
  //     | TypeDeclaration
  //     | EnumDeclaration
  //     | DatasourceDeclaration
  //     | GeneratorDeclaration
  //     | TypeAlias
  static boolean Declaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Declaration")) return false;
    boolean r;
    r = ModelDeclaration(b, l + 1);
    if (!r) r = TypeDeclaration(b, l + 1);
    if (!r) r = EnumDeclaration(b, l + 1);
    if (!r) r = DatasourceDeclaration(b, l + 1);
    if (!r) r = GeneratorDeclaration(b, l + 1);
    if (!r) r = TypeAlias(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // ENUM Identifier '{' (EnumValueDeclaration | BlockAttribute)* '}'
  public static boolean EnumDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EnumDeclaration")) return false;
    if (!nextTokenIs(b, ENUM)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ENUM);
    r = r && Identifier(b, l + 1);
    r = r && consumeToken(b, LBRACE);
    r = r && EnumDeclaration_3(b, l + 1);
    r = r && consumeToken(b, RBRACE);
    exit_section_(b, m, ENUM_DECLARATION, r);
    return r;
  }

  // (EnumValueDeclaration | BlockAttribute)*
  private static boolean EnumDeclaration_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EnumDeclaration_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!EnumDeclaration_3_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "EnumDeclaration_3", c)) break;
    }
    return true;
  }

  // EnumValueDeclaration | BlockAttribute
  private static boolean EnumDeclaration_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EnumDeclaration_3_0")) return false;
    boolean r;
    r = EnumValueDeclaration(b, l + 1);
    if (!r) r = BlockAttribute(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // Identifier FieldAttribute*
  public static boolean EnumValueDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EnumValueDeclaration")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Identifier(b, l + 1);
    r = r && EnumValueDeclaration_1(b, l + 1);
    exit_section_(b, m, ENUM_VALUE_DECLARATION, r);
    return r;
  }

  // FieldAttribute*
  private static boolean EnumValueDeclaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EnumValueDeclaration_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!FieldAttribute(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "EnumValueDeclaration_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // FunctionCall
  //     | ArrayExpression
  //     | NUMERIC_LITERAL
  //     | STRING_LITERAL
  //     | Path
  public static boolean Expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Expression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, EXPRESSION, "<expression>");
    r = FunctionCall(b, l + 1);
    if (!r) r = ArrayExpression(b, l + 1);
    if (!r) r = consumeToken(b, NUMERIC_LITERAL);
    if (!r) r = consumeToken(b, STRING_LITERAL);
    if (!r) r = Path(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '@' Path ArgumentsList?
  public static boolean FieldAttribute(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FieldAttribute")) return false;
    if (!nextTokenIs(b, AT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, AT);
    r = r && Path(b, l + 1);
    r = r && FieldAttribute_2(b, l + 1);
    exit_section_(b, m, FIELD_ATTRIBUTE, r);
    return r;
  }

  // ArgumentsList?
  private static boolean FieldAttribute_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FieldAttribute_2")) return false;
    ArgumentsList(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // IDENTIFIER ':'? FieldType? FieldAttribute*
  public static boolean FieldDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FieldDeclaration")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    r = r && FieldDeclaration_1(b, l + 1);
    r = r && FieldDeclaration_2(b, l + 1);
    r = r && FieldDeclaration_3(b, l + 1);
    exit_section_(b, m, FIELD_DECLARATION, r);
    return r;
  }

  // ':'?
  private static boolean FieldDeclaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FieldDeclaration_1")) return false;
    consumeToken(b, COLON);
    return true;
  }

  // FieldType?
  private static boolean FieldDeclaration_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FieldDeclaration_2")) return false;
    FieldType(b, l + 1);
    return true;
  }

  // FieldAttribute*
  private static boolean FieldDeclaration_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FieldDeclaration_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!FieldAttribute(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "FieldDeclaration_3", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // '{' (FieldDeclaration | BlockAttribute)* '}'
  static boolean FieldDeclarationBlock(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FieldDeclarationBlock")) return false;
    if (!nextTokenIs(b, LBRACE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LBRACE);
    r = r && FieldDeclarationBlock_1(b, l + 1);
    r = r && consumeToken(b, RBRACE);
    exit_section_(b, m, null, r);
    return r;
  }

  // (FieldDeclaration | BlockAttribute)*
  private static boolean FieldDeclarationBlock_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FieldDeclarationBlock_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!FieldDeclarationBlock_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "FieldDeclarationBlock_1", c)) break;
    }
    return true;
  }

  // FieldDeclaration | BlockAttribute
  private static boolean FieldDeclarationBlock_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FieldDeclarationBlock_1_0")) return false;
    boolean r;
    r = FieldDeclaration(b, l + 1);
    if (!r) r = BlockAttribute(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // UnsupportedOptionalListType
  //     | ListType
  //     | OptionalType
  //     | LegacyRequiredType
  //     | LegacyListType
  //     | BaseType
  public static boolean FieldType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FieldType")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FIELD_TYPE, "<field type>");
    r = UnsupportedOptionalListType(b, l + 1);
    if (!r) r = ListType(b, l + 1);
    if (!r) r = OptionalType(b, l + 1);
    if (!r) r = LegacyRequiredType(b, l + 1);
    if (!r) r = LegacyListType(b, l + 1);
    if (!r) r = BaseType(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // Path ArgumentsList
  public static boolean FunctionCall(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "FunctionCall")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Path(b, l + 1);
    r = r && ArgumentsList(b, l + 1);
    exit_section_(b, m, FUNCTION_CALL, r);
    return r;
  }

  /* ********************************************************** */
  // GENERATOR Identifier KeyValueBlock
  public static boolean GeneratorDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "GeneratorDeclaration")) return false;
    if (!nextTokenIs(b, GENERATOR)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, GENERATOR);
    r = r && Identifier(b, l + 1);
    r = r && KeyValueBlock(b, l + 1);
    exit_section_(b, m, GENERATOR_DECLARATION, r);
    return r;
  }

  /* ********************************************************** */
  // IDENTIFIER
  static boolean Identifier(PsiBuilder b, int l) {
    return consumeToken(b, IDENTIFIER);
  }

  /* ********************************************************** */
  // Identifier '=' Expression
  public static boolean KeyValue(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "KeyValue")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Identifier(b, l + 1);
    r = r && consumeToken(b, EQ);
    r = r && Expression(b, l + 1);
    exit_section_(b, m, KEY_VALUE, r);
    return r;
  }

  /* ********************************************************** */
  // '{' KeyValue* '}'
  static boolean KeyValueBlock(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "KeyValueBlock")) return false;
    if (!nextTokenIs(b, LBRACE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LBRACE);
    r = r && KeyValueBlock_1(b, l + 1);
    r = r && consumeToken(b, RBRACE);
    exit_section_(b, m, null, r);
    return r;
  }

  // KeyValue*
  private static boolean KeyValueBlock_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "KeyValueBlock_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!KeyValue(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "KeyValueBlock_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // '[' BaseType ']'
  public static boolean LegacyListType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LegacyListType")) return false;
    if (!nextTokenIs(b, LBRACKET)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LBRACKET);
    r = r && BaseType(b, l + 1);
    r = r && consumeToken(b, RBRACKET);
    exit_section_(b, m, LEGACY_LIST_TYPE, r);
    return r;
  }

  /* ********************************************************** */
  // BaseType '!'
  public static boolean LegacyRequiredType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "LegacyRequiredType")) return false;
    if (!nextTokenIs(b, "<legacy required type>", IDENTIFIER, UNSUPPORTED)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LEGACY_REQUIRED_TYPE, "<legacy required type>");
    r = BaseType(b, l + 1);
    r = r && consumeToken(b, EXCL);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // BaseType '[' ']'
  public static boolean ListType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ListType")) return false;
    if (!nextTokenIs(b, "<list type>", IDENTIFIER, UNSUPPORTED)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LIST_TYPE, "<list type>");
    r = BaseType(b, l + 1);
    r = r && consumeTokens(b, 0, LBRACKET, RBRACKET);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // MODEL Identifier FieldDeclarationBlock
  public static boolean ModelDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ModelDeclaration")) return false;
    if (!nextTokenIs(b, MODEL)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, MODEL);
    r = r && Identifier(b, l + 1);
    r = r && FieldDeclarationBlock(b, l + 1);
    exit_section_(b, m, MODEL_DECLARATION, r);
    return r;
  }

  /* ********************************************************** */
  // Identifier ':' Expression
  public static boolean NamedArgument(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NamedArgument")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Identifier(b, l + 1);
    r = r && consumeToken(b, COLON);
    r = r && Expression(b, l + 1);
    exit_section_(b, m, NAMED_ARGUMENT, r);
    return r;
  }

  /* ********************************************************** */
  // BaseType '?'
  public static boolean OptionalType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "OptionalType")) return false;
    if (!nextTokenIs(b, "<optional type>", IDENTIFIER, UNSUPPORTED)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, OPTIONAL_TYPE, "<optional type>");
    r = BaseType(b, l + 1);
    r = r && consumeToken(b, QUEST);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // Identifier ('.' Path?)*
  public static boolean Path(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Path")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Identifier(b, l + 1);
    r = r && Path_1(b, l + 1);
    exit_section_(b, m, PATH, r);
    return r;
  }

  // ('.' Path?)*
  private static boolean Path_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Path_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!Path_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Path_1", c)) break;
    }
    return true;
  }

  // '.' Path?
  private static boolean Path_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Path_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DOT);
    r = r && Path_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // Path?
  private static boolean Path_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Path_1_0_1")) return false;
    Path(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // Declaration*
  static boolean Schema(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Schema")) return false;
    while (true) {
      int c = current_position_(b);
      if (!Declaration(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Schema", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // TYPE Identifier '=' BaseType FieldAttribute*
  public static boolean TypeAlias(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeAlias")) return false;
    if (!nextTokenIs(b, TYPE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TYPE);
    r = r && Identifier(b, l + 1);
    r = r && consumeToken(b, EQ);
    r = r && BaseType(b, l + 1);
    r = r && TypeAlias_4(b, l + 1);
    exit_section_(b, m, TYPE_ALIAS, r);
    return r;
  }

  // FieldAttribute*
  private static boolean TypeAlias_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeAlias_4")) return false;
    while (true) {
      int c = current_position_(b);
      if (!FieldAttribute(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "TypeAlias_4", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // TYPE Identifier FieldDeclarationBlock
  public static boolean TypeDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeDeclaration")) return false;
    if (!nextTokenIs(b, TYPE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TYPE);
    r = r && Identifier(b, l + 1);
    r = r && FieldDeclarationBlock(b, l + 1);
    exit_section_(b, m, TYPE_DECLARATION, r);
    return r;
  }

  /* ********************************************************** */
  // BaseType '[' ']' '?'
  public static boolean UnsupportedOptionalListType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "UnsupportedOptionalListType")) return false;
    if (!nextTokenIs(b, "<unsupported optional list type>", IDENTIFIER, UNSUPPORTED)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, UNSUPPORTED_OPTIONAL_LIST_TYPE, "<unsupported optional list type>");
    r = BaseType(b, l + 1);
    r = r && consumeTokens(b, 0, LBRACKET, RBRACKET, QUEST);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // UNSUPPORTED '(' STRING_LITERAL ')'
  public static boolean UnsupportedType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "UnsupportedType")) return false;
    if (!nextTokenIs(b, UNSUPPORTED)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, UNSUPPORTED, LPAREN, STRING_LITERAL, RPAREN);
    exit_section_(b, m, UNSUPPORTED_TYPE, r);
    return r;
  }

}
