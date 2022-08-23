package com.vepanimas.intellij.prisma.lang.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import static com.intellij.psi.TokenType.*;

import static com.vepanimas.intellij.prisma.lang.psi.PrismaElementTypes.*;

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

EOL_WS           = \n | \r | \r\n
LINE_WS          = [\ \t]
WHITE_SPACE_CHAR = {EOL_WS} | {LINE_WS}
WHITE_SPACE      = {WHITE_SPACE_CHAR}+
DIGIT            = [:digit:]

NAME_START       = [a-zA-Z]
NAME_BODY        = [a-zA-Z\-_]
IDENTIFIER       = {NAME_START} ({NAME_BODY})*

STRING_LITERAL   = \"([^\\\"\r\n]|\\[^\r\n])*\"?
NUMERIC_LITERAL  = "-"? {DIGIT}+ ("." {DIGIT}+)?

DOC_COMMENT = "///" .*
LINE_COMMENT = "//" .*

%%

"model"            { return MODEL; }
"type"             { return TYPE; }
"enum"             { return ENUM; }
"generator"        { return GENERATOR; }
"datasource"       { return DATASOURCE; }

"Unsupported"      { return UNSUPPORTED; }

"{"                { return LBRACE; }
"}"                { return RBRACE; }
"("                { return LPAREN; }
")"                { return RPAREN; }
"["                { return LBRACKET; }
"]"                { return RBRACKET; }
"="                { return EQ; }
"."                { return DOT; }
":"                { return COLON; }
"?"                { return QUEST; }
"!"                { return EXCL; }
"@"                { return AT; }
"@@"               { return ATAT; }
","                { return COMMA; }

{IDENTIFIER}       { return IDENTIFIER; }
{NUMERIC_LITERAL}  { return NUMERIC_LITERAL; }
{STRING_LITERAL}   { return STRING_LITERAL; }
{WHITE_SPACE}      { return WHITE_SPACE; }

[^]                { return BAD_CHARACTER; }