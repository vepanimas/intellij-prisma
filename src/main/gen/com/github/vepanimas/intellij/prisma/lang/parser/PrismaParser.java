// This is a generated file. Not intended for manual editing.
package com.github.vepanimas.intellij.prisma.lang.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static com.github.vepanimas.intellij.prisma.lang.psi.PrismaElementTypes.*;
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
    return PrismaFile(b, l + 1);
  }

  /* ********************************************************** */
  // ModelDeclaration
  public static boolean Declaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Declaration")) return false;
    if (!nextTokenIs(b, MODEL)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = ModelDeclaration(b, l + 1);
    exit_section_(b, m, DECLARATION, r);
    return r;
  }

  /* ********************************************************** */
  // MODEL IDENTIFIER
  public static boolean ModelDeclaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ModelDeclaration")) return false;
    if (!nextTokenIs(b, MODEL)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, MODEL, IDENTIFIER);
    exit_section_(b, m, MODEL_DECLARATION, r);
    return r;
  }

  /* ********************************************************** */
  // Declaration*
  static boolean PrismaFile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrismaFile")) return false;
    while (true) {
      int c = current_position_(b);
      if (!Declaration(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "PrismaFile", c)) break;
    }
    return true;
  }

}
