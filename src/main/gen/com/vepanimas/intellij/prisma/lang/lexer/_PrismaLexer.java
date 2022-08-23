/* The following code was generated by JFlex 1.7.0 tweaked for IntelliJ platform */

package com.vepanimas.intellij.prisma.lang.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import static com.intellij.psi.TokenType.*;

import static com.vepanimas.intellij.prisma.lang.psi.PrismaElementTypes.*;
import static com.vepanimas.intellij.prisma.lang.parser.PrismaParserDefinition.*;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.7.0
 * from the specification file <tt>Prisma.flex</tt>
 */
class _PrismaLexer implements FlexLexer {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0, 0
  };

  /** 
   * Translates characters to character classes
   * Chosen bits are [9, 6, 6]
   * Total runtime size is 3872 bytes
   */
  public static int ZZ_CMAP(int ch) {
    return ZZ_CMAP_A[(ZZ_CMAP_Y[(ZZ_CMAP_Z[ch>>12]<<6)|((ch>>6)&0x3f)]<<6)|(ch&0x3f)];
  }

  /* The ZZ_CMAP_Z table has 272 entries */
  static final char ZZ_CMAP_Z[] = zzUnpackCMap(
    "\1\0\1\1\1\2\7\3\1\4\4\3\1\5\1\6\1\7\4\3\1\10\6\3\1\11\1\12\361\3");

  /* The ZZ_CMAP_Y table has 704 entries */
  static final char ZZ_CMAP_Y[] = zzUnpackCMap(
    "\1\0\1\1\1\2\26\3\1\4\1\3\1\5\3\3\1\6\5\3\1\7\1\3\1\7\1\3\1\7\1\3\1\7\1\3"+
    "\1\7\1\3\1\7\1\3\1\7\1\3\1\7\1\3\1\7\1\3\1\7\1\3\1\10\1\3\1\10\1\4\4\3\1\6"+
    "\1\10\34\3\1\4\1\10\4\3\1\11\1\3\1\10\2\3\1\12\2\3\1\10\1\5\2\3\1\12\16\3"+
    "\1\13\227\3\1\4\12\3\1\10\1\6\2\3\1\14\1\3\1\10\5\3\1\5\114\3\1\10\25\3\1"+
    "\4\56\3\1\7\1\3\1\5\1\15\2\3\1\10\3\3\1\5\5\3\1\10\1\3\1\10\5\3\1\10\1\3\1"+
    "\6\1\5\6\3\1\4\15\3\1\10\67\3\1\4\3\3\1\10\61\3\1\16\105\3\1\10\32\3");

  /* The ZZ_CMAP_A table has 960 entries */
  static final char ZZ_CMAP_A[] = zzUnpackCMap(
    "\11\0\1\2\1\1\2\13\1\1\22\0\1\2\1\45\1\6\5\0\1\36\1\37\2\0\1\47\1\10\1\11"+
    "\1\12\12\3\1\43\2\0\1\42\1\0\1\44\1\46\24\4\1\33\5\4\1\40\1\7\1\41\1\0\1\5"+
    "\1\0\1\30\1\4\1\32\1\16\1\17\1\4\1\26\4\4\1\20\1\14\1\24\1\15\1\23\1\4\1\27"+
    "\1\31\1\21\1\25\3\4\1\22\1\4\1\34\1\0\1\35\7\0\1\13\232\0\12\3\106\0\12\3"+
    "\6\0\12\3\134\0\12\3\40\0\12\3\54\0\12\3\60\0\12\3\6\0\12\3\116\0\2\13\46"+
    "\0\12\3\26\0\12\3\74\0\12\3\16\0\62\3");

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\0\1\1\1\2\1\3\1\4\1\5\1\1\1\6"+
    "\1\1\6\4\1\7\1\10\1\11\1\12\1\13\1\14"+
    "\1\15\1\16\1\17\1\20\1\21\1\22\1\0\1\5"+
    "\1\0\1\23\6\4\1\24\1\3\1\23\1\25\10\4"+
    "\1\26\1\27\2\4\1\30\15\4\1\31\1\4\1\32"+
    "\1\4\1\33";

  private static int [] zzUnpackAction() {
    int [] result = new int[72];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\50\0\120\0\170\0\240\0\310\0\360\0\50"+
    "\0\u0118\0\u0140\0\u0168\0\u0190\0\u01b8\0\u01e0\0\u0208\0\50"+
    "\0\50\0\50\0\50\0\50\0\50\0\50\0\50\0\50"+
    "\0\50\0\u0230\0\50\0\u0258\0\50\0\u0280\0\u02a8\0\u02d0"+
    "\0\u02f8\0\u0320\0\u0348\0\u0370\0\u0398\0\50\0\u0258\0\u03c0"+
    "\0\u03e8\0\u0410\0\u0438\0\u0460\0\u0488\0\u04b0\0\u04d8\0\u0500"+
    "\0\u0528\0\240\0\240\0\u0550\0\u0578\0\240\0\u05a0\0\u05c8"+
    "\0\u05f0\0\u0618\0\u0640\0\u0668\0\u0690\0\u06b8\0\u06e0\0\u0708"+
    "\0\u0730\0\u0758\0\u0780\0\240\0\u07a8\0\240\0\u07d0\0\240";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[72];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\2\2\3\1\4\1\5\1\2\1\6\1\2\1\7"+
    "\1\10\1\11\1\2\1\12\1\5\1\13\1\14\1\5"+
    "\1\15\4\5\1\16\4\5\1\17\1\20\1\21\1\22"+
    "\1\23\1\24\1\25\1\26\1\27\1\30\1\31\1\32"+
    "\1\33\51\0\2\3\50\0\1\4\5\0\1\34\42\0"+
    "\2\5\2\0\1\5\3\0\20\5\14\0\1\6\1\0"+
    "\4\6\1\35\1\36\40\6\3\0\1\4\56\0\1\37"+
    "\41\0\2\5\2\0\1\5\3\0\1\5\1\40\16\5"+
    "\20\0\2\5\2\0\1\5\3\0\14\5\1\41\3\5"+
    "\20\0\2\5\2\0\1\5\3\0\10\5\1\42\7\5"+
    "\20\0\2\5\2\0\1\5\3\0\6\5\1\43\11\5"+
    "\20\0\2\5\2\0\1\5\3\0\3\5\1\44\14\5"+
    "\20\0\2\5\2\0\1\5\3\0\10\5\1\45\7\5"+
    "\62\0\1\46\4\0\1\47\44\0\1\6\1\0\46\6"+
    "\1\50\1\0\10\50\1\51\1\0\34\50\4\0\2\5"+
    "\2\0\1\5\3\0\2\5\1\52\15\5\20\0\2\5"+
    "\2\0\1\5\3\0\5\5\1\53\12\5\20\0\2\5"+
    "\2\0\1\5\3\0\11\5\1\54\6\5\20\0\2\5"+
    "\2\0\1\5\3\0\7\5\1\55\10\5\20\0\2\5"+
    "\2\0\1\5\3\0\10\5\1\56\7\5\20\0\2\5"+
    "\2\0\1\5\3\0\15\5\1\57\2\5\14\0\1\50"+
    "\1\0\11\50\1\0\34\50\1\51\1\0\11\51\1\0"+
    "\34\51\4\0\2\5\2\0\1\5\3\0\3\5\1\60"+
    "\14\5\20\0\2\5\2\0\1\5\3\0\14\5\1\61"+
    "\3\5\20\0\2\5\2\0\1\5\3\0\1\62\17\5"+
    "\20\0\2\5\2\0\1\5\3\0\3\5\1\63\14\5"+
    "\20\0\2\5\2\0\1\5\3\0\3\5\1\64\14\5"+
    "\20\0\2\5\2\0\1\5\3\0\11\5\1\65\6\5"+
    "\20\0\2\5\2\0\1\5\3\0\4\5\1\66\13\5"+
    "\20\0\2\5\2\0\1\5\3\0\15\5\1\67\2\5"+
    "\20\0\2\5\2\0\1\5\3\0\13\5\1\70\4\5"+
    "\20\0\2\5\2\0\1\5\3\0\7\5\1\71\10\5"+
    "\20\0\2\5\2\0\1\5\3\0\1\5\1\72\16\5"+
    "\20\0\2\5\2\0\1\5\3\0\14\5\1\73\3\5"+
    "\20\0\2\5\2\0\1\5\3\0\7\5\1\74\10\5"+
    "\20\0\2\5\2\0\1\5\3\0\11\5\1\75\6\5"+
    "\20\0\2\5\2\0\1\5\3\0\5\5\1\76\12\5"+
    "\20\0\2\5\2\0\1\5\3\0\1\5\1\77\16\5"+
    "\20\0\2\5\2\0\1\5\3\0\13\5\1\100\4\5"+
    "\20\0\2\5\2\0\1\5\3\0\1\5\1\101\16\5"+
    "\20\0\2\5\2\0\1\5\3\0\13\5\1\102\4\5"+
    "\20\0\2\5\2\0\1\5\3\0\16\5\1\103\1\5"+
    "\20\0\2\5\2\0\1\5\3\0\13\5\1\104\4\5"+
    "\20\0\2\5\2\0\1\5\3\0\5\5\1\105\12\5"+
    "\20\0\2\5\2\0\1\5\3\0\3\5\1\106\14\5"+
    "\20\0\2\5\2\0\1\5\3\0\3\5\1\107\14\5"+
    "\20\0\2\5\2\0\1\5\3\0\2\5\1\110\15\5"+
    "\14\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[2040];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String[] ZZ_ERROR_MSG = {
    "Unknown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\0\1\11\5\1\1\11\7\1\12\11\1\1\1\11"+
    "\1\0\1\11\1\0\7\1\1\11\42\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[72];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private CharSequence zzBuffer = "";

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /**
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
  public _PrismaLexer() {
    this((java.io.Reader)null);
  }


  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  _PrismaLexer(java.io.Reader in) {
    this.zzReader = in;
  }


  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    int size = 0;
    for (int i = 0, length = packed.length(); i < length; i += 2) {
      size += packed.charAt(i);
    }
    char[] map = new char[size];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < packed.length()) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }

  public final int getTokenStart() {
    return zzStartRead;
  }

  public final int getTokenEnd() {
    return getTokenStart() + yylength();
  }

  public void reset(CharSequence buffer, int start, int end, int initialState) {
    zzBuffer = buffer;
    zzCurrentPos = zzMarkedPos = zzStartRead = start;
    zzAtEOF  = false;
    zzAtBOL = true;
    zzEndRead = end;
    yybegin(initialState);
  }

  /**
   * Refills the input buffer.
   *
   * @return      {@code false}, iff there was new input.
   *
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {
    return true;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final CharSequence yytext() {
    return zzBuffer.subSequence(zzStartRead, zzMarkedPos);
  }


  /**
   * Returns the character at position {@code pos} from the
   * matched text.
   *
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch.
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer.charAt(zzStartRead+pos);
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occurred while scanning.
   *
   * In a wellformed scanner (no or only correct usage of
   * yypushback(int) and a match-all fallback rule) this method
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  }


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public IElementType advance() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    CharSequence zzBufferL = zzBuffer;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {

          if (zzCurrentPosL < zzEndReadL) {
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL/*, zzEndReadL*/);
            zzCurrentPosL += Character.charCount(zzInput);
          }
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL/*, zzEndReadL*/);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + ZZ_CMAP(zzInput) ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
        zzAtEOF = true;
        return null;
      }
      else {
        switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
          case 1: 
            { return BAD_CHARACTER;
            } 
            // fall through
          case 28: break;
          case 2: 
            { return WHITE_SPACE;
            } 
            // fall through
          case 29: break;
          case 3: 
            { return NUMERIC_LITERAL;
            } 
            // fall through
          case 30: break;
          case 4: 
            { return IDENTIFIER;
            } 
            // fall through
          case 31: break;
          case 5: 
            { return STRING_LITERAL;
            } 
            // fall through
          case 32: break;
          case 6: 
            { return DOT;
            } 
            // fall through
          case 33: break;
          case 7: 
            { return LBRACE;
            } 
            // fall through
          case 34: break;
          case 8: 
            { return RBRACE;
            } 
            // fall through
          case 35: break;
          case 9: 
            { return LPAREN;
            } 
            // fall through
          case 36: break;
          case 10: 
            { return RPAREN;
            } 
            // fall through
          case 37: break;
          case 11: 
            { return LBRACKET;
            } 
            // fall through
          case 38: break;
          case 12: 
            { return RBRACKET;
            } 
            // fall through
          case 39: break;
          case 13: 
            { return EQ;
            } 
            // fall through
          case 40: break;
          case 14: 
            { return COLON;
            } 
            // fall through
          case 41: break;
          case 15: 
            { return QUEST;
            } 
            // fall through
          case 42: break;
          case 16: 
            { return EXCL;
            } 
            // fall through
          case 43: break;
          case 17: 
            { return AT;
            } 
            // fall through
          case 44: break;
          case 18: 
            { return COMMA;
            } 
            // fall through
          case 45: break;
          case 19: 
            { return LINE_COMMENT;
            } 
            // fall through
          case 46: break;
          case 20: 
            { return ATAT;
            } 
            // fall through
          case 47: break;
          case 21: 
            { return DOC_COMMENT;
            } 
            // fall through
          case 48: break;
          case 22: 
            { return ENUM;
            } 
            // fall through
          case 49: break;
          case 23: 
            { return TYPE;
            } 
            // fall through
          case 50: break;
          case 24: 
            { return MODEL;
            } 
            // fall through
          case 51: break;
          case 25: 
            { return GENERATOR;
            } 
            // fall through
          case 52: break;
          case 26: 
            { return DATASOURCE;
            } 
            // fall through
          case 53: break;
          case 27: 
            { return UNSUPPORTED;
            } 
            // fall through
          case 54: break;
          default:
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
