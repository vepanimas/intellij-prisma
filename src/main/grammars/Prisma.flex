package com.github.vepanimas.intellij.prisma.lang.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.github.vepanimas.intellij.prisma.lang.psi.PrismaElementTypes.*;
import static com.intellij.psi.TokenType.*;

%%

%{
  public _PrismaLexer() {
    this((java.io.Reader)null);
  }
%}

%class _PrismaLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType

%%

"model"  { return MODEL; }

[^]      { return BAD_CHARACTER; }