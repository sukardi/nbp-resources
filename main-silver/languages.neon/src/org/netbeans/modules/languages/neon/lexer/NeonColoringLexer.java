/* The following code was generated by JFlex 1.4.3 on 11.11.13 13:51 */

/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 *
 * Contributor(s):
 *
 * Portions Copyrighted 2008 Sun Microsystems, Inc.
 */

package org.netbeans.modules.languages.neon.lexer;

import org.netbeans.spi.lexer.LexerInput;
import org.netbeans.spi.lexer.LexerRestartInfo;

@org.netbeans.api.annotations.common.SuppressWarnings({"SF_SWITCH_FALLTHROUGH", "URF_UNREAD_FIELD", "DLS_DEAD_LOCAL_STORE", "DM_DEFAULT_ENCODING"})

/**
 * This class is a scanner generated by
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 11.11.13 13:51 from the specification file
 * <tt>/home/warden/NetBeansProjects/web-main-new/languages.neon/tools/NeonColoringLexer.flex</tt>
 */
public class NeonColoringLexer {
/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 *
 * Contributor(s):
 *
 * Portions Copyrighted 2008 Sun Microsystems, Inc.
 */

  /** This character denotes the end of file */
  public static final int YYEOF = LexerInput.EOF;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int ST_IN_RIGHT_BLOCK = 10;
  public static final int ST_VALUED_BLOCK = 6;
  public static final int ST_IN_ARRAY_KEY = 12;
  public static final int ST_IN_BLOCK = 2;
  public static final int ST_IN_ARRAY_VALUE = 14;
  public static final int ST_IN_INHERITED_BLOCK = 8;
  public static final int ST_IN_MINUS_ARRAY_VALUE = 16;
  public static final int ST_IN_PA_ARRAY = 22;
  public static final int ST_HIGHLIGHTING_ERROR = 24;
  public static final int ST_IN_SQ_ARRAY = 18;
  public static final int ST_BLOCK_HEADER = 4;
  public static final int YYINITIAL = 0;
  public static final int ST_IN_CU_ARRAY = 20;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = {
     0,  0,  1,  1,  2,  2,  3,  3,  4,  4,  5,  5,  6,  6,  7,  7,
     5,  5,  8,  8,  9,  9, 10, 10, 11, 11
  };

  /**
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED =
    "\11\0\1\17\1\21\2\0\1\20\22\0\1\17\1\3\1\37\1\45"+
    "\1\0\1\41\1\0\1\40\1\52\1\35\1\1\1\23\1\36\1\24"+
    "\1\31\1\0\1\22\7\26\2\25\1\42\1\0\1\44\1\43\1\32"+
    "\1\0\1\46\1\11\3\30\1\7\1\10\5\1\1\12\1\1\1\15"+
    "\1\16\2\1\1\5\1\13\1\4\1\6\2\1\1\27\1\14\1\1"+
    "\1\50\1\47\1\33\1\0\1\1\1\0\1\11\3\30\1\7\1\10"+
    "\5\1\1\12\1\1\1\15\1\16\2\1\1\5\1\13\1\4\1\6"+
    "\2\1\1\27\1\14\1\1\1\51\1\0\1\34\1\0\67\1\4\0"+
    "\1\1\5\0\27\1\1\0\37\1\1\0\u01ca\1\4\0\14\1\16\0"+
    "\5\1\7\0\1\1\1\0\1\1\201\0\5\1\1\0\2\1\2\0"+
    "\4\1\10\0\1\1\1\0\3\1\1\0\1\1\1\0\24\1\1\0"+
    "\123\1\1\0\213\1\10\0\236\1\11\0\46\1\2\0\1\1\7\0"+
    "\47\1\110\0\33\1\5\0\3\1\55\0\53\1\25\0\12\2\4\0"+
    "\2\1\1\0\143\1\1\0\1\1\17\0\2\1\7\0\2\1\12\2"+
    "\3\1\2\0\1\1\20\0\1\1\1\0\36\1\35\0\131\1\13\0"+
    "\1\1\16\0\12\2\41\1\11\0\2\1\4\0\1\1\5\0\26\1"+
    "\4\0\1\1\11\0\1\1\3\0\1\1\27\0\31\1\253\0\66\1"+
    "\3\0\1\1\22\0\1\1\7\0\12\1\4\0\12\2\1\0\7\1"+
    "\1\0\7\1\5\0\10\1\2\0\2\1\2\0\26\1\1\0\7\1"+
    "\1\0\1\1\3\0\4\1\3\0\1\1\20\0\1\1\15\0\2\1"+
    "\1\0\3\1\4\0\12\2\2\1\23\0\6\1\4\0\2\1\2\0"+
    "\26\1\1\0\7\1\1\0\2\1\1\0\2\1\1\0\2\1\37\0"+
    "\4\1\1\0\1\1\7\0\12\2\2\0\3\1\20\0\11\1\1\0"+
    "\3\1\1\0\26\1\1\0\7\1\1\0\2\1\1\0\5\1\3\0"+
    "\1\1\22\0\1\1\17\0\2\1\4\0\12\2\25\0\10\1\2\0"+
    "\2\1\2\0\26\1\1\0\7\1\1\0\2\1\1\0\5\1\3\0"+
    "\1\1\36\0\2\1\1\0\3\1\4\0\12\2\1\0\1\1\21\0"+
    "\1\1\1\0\6\1\3\0\3\1\1\0\4\1\3\0\2\1\1\0"+
    "\1\1\1\0\2\1\3\0\2\1\3\0\3\1\3\0\14\1\26\0"+
    "\1\1\25\0\12\2\25\0\10\1\1\0\3\1\1\0\27\1\1\0"+
    "\12\1\1\0\5\1\3\0\1\1\32\0\2\1\6\0\2\1\4\0"+
    "\12\2\25\0\10\1\1\0\3\1\1\0\27\1\1\0\12\1\1\0"+
    "\5\1\3\0\1\1\40\0\1\1\1\0\2\1\4\0\12\2\1\0"+
    "\2\1\22\0\10\1\1\0\3\1\1\0\51\1\2\0\1\1\20\0"+
    "\1\1\21\0\2\1\4\0\12\2\12\0\6\1\5\0\22\1\3\0"+
    "\30\1\1\0\11\1\1\0\1\1\2\0\7\1\72\0\60\1\1\0"+
    "\2\1\14\0\7\1\11\0\12\2\47\0\2\1\1\0\1\1\2\0"+
    "\2\1\1\0\1\1\2\0\1\1\6\0\4\1\1\0\7\1\1\0"+
    "\3\1\1\0\1\1\1\0\1\1\2\0\2\1\1\0\4\1\1\0"+
    "\2\1\11\0\1\1\2\0\5\1\1\0\1\1\11\0\12\2\2\0"+
    "\2\1\42\0\1\1\37\0\12\2\26\0\10\1\1\0\44\1\33\0"+
    "\5\1\163\0\53\1\24\0\1\1\12\2\6\0\6\1\4\0\4\1"+
    "\3\0\1\1\3\0\2\1\7\0\3\1\4\0\15\1\14\0\1\1"+
    "\1\0\12\2\6\0\46\1\12\0\53\1\1\0\1\1\3\0\u0149\1"+
    "\1\0\4\1\2\0\7\1\1\0\1\1\1\0\4\1\2\0\51\1"+
    "\1\0\4\1\2\0\41\1\1\0\4\1\2\0\7\1\1\0\1\1"+
    "\1\0\4\1\2\0\17\1\1\0\71\1\1\0\4\1\2\0\103\1"+
    "\45\0\20\1\20\0\125\1\14\0\u026c\1\2\0\21\1\1\0\32\1"+
    "\5\0\113\1\25\0\15\1\1\0\4\1\16\0\22\1\16\0\22\1"+
    "\16\0\15\1\1\0\3\1\17\0\64\1\43\0\1\1\4\0\1\1"+
    "\3\0\12\2\46\0\12\2\6\0\130\1\10\0\51\1\1\0\1\1"+
    "\5\0\106\1\12\0\35\1\51\0\12\2\36\1\2\0\5\1\13\0"+
    "\54\1\25\0\7\1\10\0\12\2\46\0\27\1\11\0\65\1\53\0"+
    "\12\2\6\0\12\2\15\0\1\1\135\0\57\1\21\0\7\1\4\0"+
    "\12\2\51\0\36\1\15\0\2\1\12\2\6\0\46\1\32\0\44\1"+
    "\34\0\12\2\3\0\3\1\12\2\44\1\153\0\4\1\1\0\4\1"+
    "\16\0\300\1\100\0\u0116\1\2\0\6\1\2\0\46\1\2\0\6\1"+
    "\2\0\10\1\1\0\1\1\1\0\1\1\1\0\1\1\1\0\37\1"+
    "\2\0\65\1\1\0\7\1\1\0\1\1\3\0\3\1\1\0\7\1"+
    "\3\0\4\1\2\0\6\1\4\0\15\1\5\0\3\1\1\0\7\1"+
    "\164\0\1\1\15\0\1\1\20\0\15\1\145\0\1\1\4\0\1\1"+
    "\2\0\12\1\1\0\1\1\3\0\5\1\6\0\1\1\1\0\1\1"+
    "\1\0\1\1\1\0\4\1\1\0\13\1\2\0\4\1\5\0\5\1"+
    "\4\0\1\1\64\0\2\1\u0a7b\0\57\1\1\0\57\1\1\0\205\1"+
    "\6\0\4\1\21\0\46\1\12\0\66\1\11\0\1\1\20\0\27\1"+
    "\11\0\7\1\1\0\7\1\1\0\7\1\1\0\7\1\1\0\7\1"+
    "\1\0\7\1\1\0\7\1\1\0\7\1\120\0\1\1\u01d5\0\2\1"+
    "\52\0\5\1\5\0\2\1\4\0\126\1\6\0\3\1\1\0\132\1"+
    "\1\0\4\1\5\0\51\1\3\0\136\1\21\0\33\1\65\0\20\1"+
    "\u0200\0\u19b6\1\112\0\u51cc\1\64\0\u048d\1\103\0\56\1\2\0\u010d\1"+
    "\3\0\20\1\12\2\2\1\24\0\57\1\20\0\31\1\10\0\106\1"+
    "\61\0\11\1\2\0\147\1\2\0\4\1\1\0\2\1\16\0\12\1"+
    "\120\0\10\1\1\0\3\1\1\0\4\1\1\0\27\1\35\0\64\1"+
    "\16\0\62\1\34\0\12\2\30\0\6\1\3\0\1\1\4\0\12\2"+
    "\34\1\12\0\27\1\31\0\35\1\7\0\57\1\34\0\1\1\12\2"+
    "\46\0\51\1\27\0\3\1\1\0\10\1\4\0\12\2\6\0\27\1"+
    "\3\0\1\1\5\0\60\1\1\0\1\1\3\0\2\1\2\0\5\1"+
    "\2\0\1\1\1\0\1\1\30\0\3\1\43\0\6\1\2\0\6\1"+
    "\2\0\6\1\11\0\7\1\1\0\7\1\221\0\43\1\15\0\12\2"+
    "\6\0\u2ba4\1\14\0\27\1\4\0\61\1\u2104\0\u012e\1\2\0\76\1"+
    "\2\0\152\1\46\0\7\1\14\0\5\1\5\0\1\1\1\0\12\1"+
    "\1\0\15\1\1\0\5\1\1\0\1\1\1\0\2\1\1\0\2\1"+
    "\1\0\154\1\41\0\u016b\1\22\0\100\1\2\0\66\1\50\0\14\1"+
    "\164\0\5\1\1\0\207\1\23\0\12\2\7\0\32\1\6\0\32\1"+
    "\13\0\131\1\3\0\6\1\2\0\6\1\2\0\6\1\2\0\3\1"+
    "\43\0";

  /**
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /**
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\14\0\2\1\2\2\3\3\1\4\1\5\1\6\1\7"+
    "\2\10\1\6\1\11\1\12\2\6\2\13\6\14\2\15"+
    "\1\16\1\14\1\16\1\14\4\6\1\17\1\20\1\21"+
    "\1\22\1\23\1\3\1\24\4\6\1\25\1\13\1\25"+
    "\4\0\1\7\1\26\1\12\2\0\5\14\1\27\1\14"+
    "\1\16\2\14\2\16\1\0\1\30\1\0\1\31\1\32"+
    "\1\0\1\32\1\23\3\0\2\33\1\0\1\23\1\0"+
    "\1\23\1\0\2\23\1\0\1\23\3\34\1\0\3\14"+
    "\1\27\2\14\1\16\1\31\1\23\1\33\1\23\1\0";

  private static int [] zzUnpackAction() {
    int [] result = new int[121];
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
    "\0\0\0\53\0\126\0\201\0\254\0\327\0\u0102\0\u012d"+
    "\0\u0158\0\u0183\0\u01ae\0\u01d9\0\u0204\0\u022f\0\u0204\0\u025a"+
    "\0\u0285\0\u02b0\0\u0204\0\u0204\0\u02db\0\u0204\0\u0306\0\u0331"+
    "\0\u0204\0\u035c\0\u0204\0\u0387\0\u03b2\0\u03dd\0\u0408\0\u0204"+
    "\0\u0433\0\u045e\0\u0489\0\u04b4\0\u04df\0\u050a\0\u0535\0\u0204"+
    "\0\u0560\0\u058b\0\u05b6\0\u05e1\0\u060c\0\u0637\0\u0662\0\u068d"+
    "\0\u0204\0\u0204\0\u0204\0\u0204\0\u06b8\0\u06e3\0\u0204\0\u070e"+
    "\0\u0739\0\u0764\0\u078f\0\u0204\0\u07ba\0\u07e5\0\u025a\0\u0810"+
    "\0\u083b\0\u0866\0\u0204\0\u035c\0\u0204\0\u03b2\0\u03dd\0\u0891"+
    "\0\u08bc\0\u08e7\0\u0912\0\u093d\0\u0968\0\u0993\0\u09be\0\u09e9"+
    "\0\u0a14\0\u0a3f\0\u0a6a\0\u060c\0\u0204\0\u0637\0\u0a95\0\u0ac0"+
    "\0\u068d\0\u0aeb\0\u0b16\0\u0b41\0\u0b6c\0\u0764\0\u06b8\0\u0204"+
    "\0\u0b97\0\u0bc2\0\u070e\0\u0bed\0\u0739\0\u0c18\0\u0c43\0\u078f"+
    "\0\u0c6e\0\u0c99\0\u0204\0\u0cc4\0\u0cef\0\u0d1a\0\u0d45\0\u0d70"+
    "\0\u0433\0\u0d9b\0\u0a6a\0\u0a14\0\u0204\0\u0dc6\0\u0bc2\0\u0df1"+
    "\0\u0e1c";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[121];
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
    "\20\15\1\16\32\15\1\17\1\20\2\17\13\20\1\21"+
    "\1\22\1\23\5\17\2\20\11\17\2\24\1\17\1\25"+
    "\5\17\1\26\1\27\2\26\13\27\1\21\1\30\1\31"+
    "\2\26\1\32\2\26\2\27\11\26\2\31\1\33\1\25"+
    "\6\26\1\34\2\26\13\34\1\21\1\30\1\31\2\26"+
    "\1\32\2\26\2\34\6\26\1\35\1\36\1\26\2\31"+
    "\1\33\1\25\6\26\1\27\2\26\13\27\1\21\1\37"+
    "\1\40\5\26\2\27\11\26\2\31\1\26\1\25\5\26"+
    "\4\41\1\42\3\41\1\43\2\41\1\44\1\45\1\46"+
    "\1\41\1\21\1\47\1\50\1\51\2\52\2\53\2\41"+
    "\1\54\5\26\1\55\1\56\1\57\1\41\2\26\1\25"+
    "\1\60\1\41\1\61\1\62\1\63\17\41\1\21\1\22"+
    "\1\23\1\51\2\52\2\53\2\41\1\54\5\26\1\55"+
    "\1\56\1\26\1\41\1\64\1\26\1\25\1\60\1\41"+
    "\3\26\4\41\1\42\3\41\1\43\2\41\1\44\1\45"+
    "\1\46\1\41\1\21\1\22\1\23\1\51\2\52\2\53"+
    "\2\41\1\54\1\26\4\31\1\55\1\56\1\57\1\41"+
    "\2\26\1\25\1\60\1\41\1\61\1\62\1\63\17\65"+
    "\1\66\1\22\1\23\10\65\1\26\1\64\2\26\1\67"+
    "\1\70\1\71\1\72\1\65\2\26\1\25\1\73\1\65"+
    "\1\61\1\62\1\63\17\65\1\66\1\22\1\23\10\65"+
    "\2\26\1\64\1\26\1\67\1\70\1\71\1\72\1\65"+
    "\2\26\1\25\1\73\1\65\1\61\1\62\1\63\17\65"+
    "\1\66\1\22\1\23\10\65\3\26\1\64\1\67\1\70"+
    "\1\71\1\72\1\65\2\26\1\25\1\73\1\65\1\61"+
    "\1\62\1\63\17\74\1\75\1\76\32\74\74\0\1\15"+
    "\32\0\2\77\1\100\13\77\1\100\2\0\1\77\1\0"+
    "\6\77\10\0\1\101\1\0\1\102\1\77\1\0\1\77"+
    "\22\0\1\21\54\0\1\23\31\0\20\25\2\0\31\25"+
    "\1\0\2\27\1\103\13\27\3\0\1\27\1\0\6\27"+
    "\13\0\1\27\1\0\1\27\24\0\1\31\50\0\1\104"+
    "\34\0\2\34\1\105\13\34\3\0\1\34\1\0\6\34"+
    "\13\0\1\34\1\0\1\34\3\0\20\106\2\0\15\106"+
    "\1\105\13\106\20\107\2\0\16\107\1\105\12\107\21\0"+
    "\1\40\31\0\17\41\3\0\10\41\10\0\1\41\2\0"+
    "\1\41\1\0\1\41\3\0\5\41\1\110\11\41\3\0"+
    "\10\41\10\0\1\41\2\0\1\41\1\0\1\41\3\0"+
    "\11\41\1\111\5\41\3\0\10\41\10\0\1\41\2\0"+
    "\1\41\1\0\1\41\3\0\7\41\1\112\7\41\3\0"+
    "\10\41\10\0\1\41\2\0\1\41\1\0\1\41\3\0"+
    "\7\41\1\113\7\41\3\0\10\41\10\0\1\41\2\0"+
    "\1\41\1\0\1\41\3\0\6\41\1\114\7\41\1\115"+
    "\3\0\10\41\10\0\1\41\2\0\1\41\1\0\1\41"+
    "\24\0\1\50\31\0\7\41\1\116\7\41\3\0\1\117"+
    "\2\41\1\120\1\117\1\121\1\41\1\122\10\0\1\41"+
    "\2\0\1\41\1\0\1\41\3\0\17\41\3\0\3\41"+
    "\2\123\3\41\10\0\1\41\2\0\1\41\1\0\1\41"+
    "\3\0\7\41\1\116\7\41\3\0\1\53\2\41\2\53"+
    "\2\41\1\122\10\0\1\41\2\0\1\41\1\0\1\41"+
    "\3\0\17\41\3\0\1\122\2\41\2\122\3\41\10\0"+
    "\1\41\2\0\1\41\1\0\1\41\3\0\20\124\2\0"+
    "\15\124\1\125\13\124\20\126\2\0\16\126\1\125\12\126"+
    "\17\127\3\0\10\127\10\0\1\127\2\0\1\127\1\0"+
    "\1\127\4\0\1\130\2\0\13\130\10\0\2\130\14\0"+
    "\1\131\1\0\1\132\3\0\17\65\1\133\2\0\10\65"+
    "\5\0\1\134\1\135\1\136\1\137\1\140\1\0\1\65"+
    "\1\141\1\65\3\0\17\142\1\66\2\0\10\142\5\0"+
    "\1\134\1\135\1\136\1\142\2\0\1\142\1\141\1\142"+
    "\3\0\20\143\2\0\15\143\1\144\13\143\20\145\2\0"+
    "\16\145\1\144\12\145\17\146\3\0\10\146\10\0\1\146"+
    "\2\0\1\146\1\0\1\146\4\0\1\147\2\0\13\147"+
    "\10\0\2\147\14\0\1\150\1\0\1\151\22\0\1\75"+
    "\54\0\1\74\50\0\1\100\22\0\1\101\1\0\1\102"+
    "\25\0\1\101\1\152\1\153\23\0\1\154\6\0\1\155"+
    "\2\0\13\155\1\102\7\0\2\155\22\0\6\41\1\156"+
    "\10\41\3\0\10\41\10\0\1\41\2\0\1\41\1\0"+
    "\1\41\3\0\12\41\1\157\4\41\3\0\10\41\10\0"+
    "\1\41\2\0\1\41\1\0\1\41\3\0\12\41\1\160"+
    "\4\41\3\0\10\41\10\0\1\41\2\0\1\41\1\0"+
    "\1\41\3\0\13\41\1\161\3\41\3\0\10\41\10\0"+
    "\1\41\2\0\1\41\1\0\1\41\3\0\12\41\1\162"+
    "\4\41\3\0\10\41\10\0\1\41\2\0\1\41\1\0"+
    "\1\41\3\0\4\41\1\161\12\41\3\0\10\41\10\0"+
    "\1\41\2\0\1\41\1\0\1\41\3\0\17\41\3\0"+
    "\1\123\2\163\2\123\3\41\10\0\1\41\2\0\1\41"+
    "\1\0\1\41\3\0\7\41\1\116\7\41\3\0\1\117"+
    "\2\41\1\120\1\117\2\41\1\122\10\0\1\41\2\0"+
    "\1\41\1\0\1\41\3\0\7\41\1\116\7\41\3\0"+
    "\1\120\2\41\2\120\2\41\1\122\10\0\1\41\2\0"+
    "\1\41\1\0\1\41\3\0\7\41\3\164\5\41\3\0"+
    "\1\164\2\41\2\164\1\41\1\164\1\41\10\0\1\41"+
    "\2\0\1\41\1\0\1\41\3\0\7\41\1\116\7\41"+
    "\3\0\1\122\2\41\2\122\3\41\10\0\1\41\2\0"+
    "\1\41\1\0\1\41\3\0\17\41\3\0\1\123\2\41"+
    "\2\123\3\41\10\0\1\41\2\0\1\41\1\0\1\41"+
    "\3\0\17\127\3\0\10\127\7\0\1\165\1\127\2\0"+
    "\1\127\1\0\1\127\4\0\2\130\1\132\13\130\3\0"+
    "\1\130\1\0\6\130\13\0\1\130\1\0\1\130\4\0"+
    "\1\130\2\0\13\130\10\0\2\130\16\0\1\132\22\0"+
    "\1\133\22\0\2\140\7\0\20\134\2\0\15\134\1\142"+
    "\13\134\20\135\2\0\16\135\1\142\12\135\1\0\1\142"+
    "\2\0\13\142\10\0\2\142\14\0\1\141\1\0\1\142"+
    "\3\0\17\142\1\166\2\0\10\142\5\0\1\134\1\135"+
    "\1\136\1\142\2\0\1\142\1\141\1\142\3\0\17\142"+
    "\1\133\2\0\10\142\5\0\1\134\1\135\1\136\1\167"+
    "\1\140\1\0\1\142\1\141\1\142\3\0\17\146\1\166"+
    "\2\0\10\146\5\0\1\134\1\135\1\170\1\146\2\0"+
    "\1\146\1\141\1\146\3\0\1\142\2\147\1\151\13\147"+
    "\1\133\2\0\1\147\1\142\6\147\5\0\1\134\1\135"+
    "\1\136\1\167\1\140\1\0\1\147\1\141\1\147\3\0"+
    "\1\142\1\147\2\142\13\147\1\133\2\0\5\142\2\147"+
    "\1\142\5\0\1\134\1\135\1\136\1\167\1\140\1\0"+
    "\1\142\1\141\1\151\24\0\1\153\31\0\20\154\2\0"+
    "\31\154\1\0\2\155\1\171\13\155\1\171\2\0\1\155"+
    "\1\0\6\155\10\0\1\101\2\0\1\155\1\0\1\155"+
    "\3\0\7\41\1\161\7\41\3\0\10\41\10\0\1\41"+
    "\2\0\1\41\1\0\1\41\3\0\13\41\1\156\3\41"+
    "\3\0\10\41\10\0\1\41\2\0\1\41\1\0\1\41"+
    "\3\0\10\41\1\161\6\41\3\0\10\41\10\0\1\41"+
    "\2\0\1\41\1\0\1\41\3\0\12\41\1\161\4\41"+
    "\3\0\10\41\10\0\1\41\2\0\1\41\1\0\1\41"+
    "\22\0\1\166\33\0\17\146\1\166\2\0\10\146\5\0"+
    "\1\134\1\135\1\136\1\146\2\0\1\146\1\141\1\146"+
    "\22\0\1\171\22\0\1\101\10\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[3655];
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
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\14\0\1\11\1\1\1\11\3\1\2\11\1\1\1\11"+
    "\2\1\1\11\1\1\1\11\4\1\1\11\7\1\1\11"+
    "\10\1\4\11\2\1\1\11\4\1\1\11\2\1\4\0"+
    "\1\11\1\1\1\11\2\0\14\1\1\0\1\11\1\0"+
    "\2\1\1\0\2\1\3\0\1\1\1\11\1\0\1\1"+
    "\1\0\1\1\1\0\2\1\1\0\2\1\1\11\1\1"+
    "\1\0\7\1\1\11\3\1\1\0";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[121];
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
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the textposition at the last state to be included in yytext */
  private int zzPushbackPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the
   * matched text
   */
  private int yycolumn;

  /**
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF = false;

  /* user code: */

    private StateStack stack = new StateStack();

    private LexerInput input;

    public NeonColoringLexer(LexerRestartInfo info) {
        this.input = info.input();
        if(info.state() != null) {
            //reset state
            setState((LexerState) info.state());
        } else {
            zzState = zzLexicalState = YYINITIAL;
            stack.clear();
        }

    }

    public static final class LexerState  {
        final StateStack stack;
        /** the current state of the DFA */
        final int zzState;
        /** the current lexical state */
        final int zzLexicalState;

        LexerState(StateStack stack, int zzState, int zzLexicalState) {
            this.stack = stack;
            this.zzState = zzState;
            this.zzLexicalState = zzLexicalState;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != this.getClass()) {
                return false;
            }
            LexerState state = (LexerState) obj;
            return (this.stack.equals(state.stack)
                && (this.zzState == state.zzState)
                && (this.zzLexicalState == state.zzLexicalState));
        }

        @Override
        public int hashCode() {
            int hash = 11;
            hash = 31 * hash + this.zzState;
            hash = 31 * hash + this.zzLexicalState;
            if (stack != null) {
                hash = 31 * hash + this.stack.hashCode();
            }
            return hash;
        }
    }

    public LexerState getState() {
        return new LexerState(stack.createClone(), zzState, zzLexicalState);
    }

    public void setState(LexerState state) {
        this.stack.copyFrom(state.stack);
        this.zzState = state.zzState;
        this.zzLexicalState = state.zzLexicalState;
    }

    protected int getZZLexicalState() {
        return zzLexicalState;
    }

    protected void popState() {
		yybegin(stack.popStack());
	}

	protected void pushState(final int state) {
		stack.pushStack(getZZLexicalState());
		yybegin(state);
	}


 // End user code



  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public NeonColoringLexer(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public NeonColoringLexer(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /**
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 1702) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }



  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = zzPushbackPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
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
  public final String yytext() {
    return input.readText().toString();
  }


  /**
   * Returns the character at position <tt>pos</tt> from the
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
     return input.readText().charAt(pos);
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return input.readLength();
  }


  /**
   * Reports an error that occured while scanning.
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

    input.backup(number);
    //zzMarkedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public NeonTokenId nextToken() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    //int zzCurrentPosL;
    //int zzMarkedPosL;
    //int zzEndReadL = zzEndRead;
    //char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      //zzMarkedPosL = zzMarkedPos;

      zzAction = -1;

      //zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
      int tokenLength = 0;

      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {
            zzInput = input.read();

            if(zzInput == LexerInput.EOF) {
                //end of input reached
                zzInput = YYEOF;
                break zzForAction;
                //notice: currently LexerInput.EOF == YYEOF
            }

          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            tokenLength = input.readLength();
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      if(zzInput != YYEOF) {
         input.backup(input.readLength() - tokenLength);
      }

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 2:
          { pushState(ST_VALUED_BLOCK);
        yypushback(yylength());
          }
        case 29: break;
        case 11:
          { popState();
        return NeonTokenId.NEON_WHITESPACE;
          }
        case 30: break;
        case 24:
          { return NeonTokenId.NEON_STRING;
          }
        case 31: break;
        case 21:
          { return NeonTokenId.NEON_UNKNOWN;
          }
        case 32: break;
        case 12:
          { return NeonTokenId.NEON_LITERAL;
          }
        case 33: break;
        case 14:
          { return NeonTokenId.NEON_NUMBER;
          }
        case 34: break;
        case 1:
          { yypushback(yylength());
    pushState(ST_IN_BLOCK);
          }
        case 35: break;
        case 6:
          { yypushback(yylength());
        pushState(ST_HIGHLIGHTING_ERROR);
          }
        case 36: break;
        case 10:
          { return NeonTokenId.NEON_VALUED_BLOCK;
          }
        case 37: break;
        case 5:
          { return NeonTokenId.NEON_COMMENT;
          }
        case 38: break;
        case 28:
          { pushState(ST_BLOCK_HEADER);
        yypushback(yylength());
          }
        case 39: break;
        case 26:
          { return NeonTokenId.NEON_REFERENCE;
          }
        case 40: break;
        case 18:
          { popState();
        return NeonTokenId.NEON_INTERPUNCTION;
          }
        case 41: break;
        case 27:
          { pushState(ST_IN_ARRAY_KEY);
        yypushback(yylength());
          }
        case 42: break;
        case 23:
          { return NeonTokenId.NEON_KEYWORD;
          }
        case 43: break;
        case 3:
          { return NeonTokenId.NEON_WHITESPACE;
          }
        case 44: break;
        case 4:
          { pushState(ST_IN_RIGHT_BLOCK);
        return NeonTokenId.NEON_INTERPUNCTION;
          }
        case 45: break;
        case 22:
          // lookahead expression with fixed base length
          zzMarkedPos = zzStartRead + 1;
          { pushState(ST_IN_MINUS_ARRAY_VALUE);
        return NeonTokenId.NEON_INTERPUNCTION;
          }
        case 46: break;
        case 13:
          { yypushback(yylength());
        popState();
          }
        case 47: break;
        case 9:
          { pushState(ST_IN_INHERITED_BLOCK);
        return NeonTokenId.NEON_INTERPUNCTION;
          }
        case 48: break;
        case 15:
          { pushState(ST_IN_SQ_ARRAY);
        return NeonTokenId.NEON_INTERPUNCTION;
          }
        case 49: break;
        case 8:
          { popState();
        yypushback(yylength());
          }
        case 50: break;
        case 19:
          { pushState(ST_IN_ARRAY_VALUE);
        yypushback(yylength());
          }
        case 51: break;
        case 16:
          { pushState(ST_IN_CU_ARRAY);
        return NeonTokenId.NEON_INTERPUNCTION;
          }
        case 52: break;
        case 20:
          { return NeonTokenId.NEON_INTERPUNCTION;
          }
        case 53: break;
        case 17:
          { pushState(ST_IN_PA_ARRAY);
        return NeonTokenId.NEON_INTERPUNCTION;
          }
        case 54: break;
        case 25:
          { return NeonTokenId.NEON_VARIABLE;
          }
        case 55: break;
        case 7:
          { return NeonTokenId.NEON_BLOCK;
          }
        case 56: break;
        default:
          if (zzInput == YYEOF)
            //zzAtEOF = true;
              {         if(input.readLength() > 0) {
            // backup eof
            input.backup(1);
            //and return the text as error token
            return NeonTokenId.NEON_UNKNOWN;
        } else {
            return null;
        }
 }

          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
