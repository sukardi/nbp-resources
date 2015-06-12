/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
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
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
 * Microsystems, Inc. All Rights Reserved.
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
 */
package org.netbeans.lib.lexer;

import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import junit.framework.TestCase;
import org.netbeans.api.lexer.Language;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenId;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.lib.lexer.lang.TestEmbeddingTokenId;
import org.netbeans.lib.lexer.test.LexerTestUtilities;
import org.netbeans.lib.lexer.lang.TestJavadocTokenId;
import org.netbeans.lib.lexer.lang.TestTokenId;

/**
 *
 * @author Jan Lahoda
 */
public class EmbeddedTokenListTest extends TestCase {
    
    public EmbeddedTokenListTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    public void testUpdateStartOffset() throws Exception {
        Document d = new PlainDocument();
        
        d.putProperty(Language.class,TestTokenId.language());
        
        d.insertString(0, "ident ident /** @see X */", null);
        
        TokenHierarchy<?> h = TokenHierarchy.get(d);
        ((AbstractDocument)d).readLock();
        try {
            TokenSequence<?> ts = h.tokenSequence();

            LexerTestUtilities.assertNextTokenEquals(ts,TestTokenId.IDENTIFIER, "ident");
            assertEquals(0, ts.offset());

            LexerTestUtilities.assertNextTokenEquals(ts,TestTokenId.WHITESPACE, " ");
            assertEquals(5, ts.offset());

            LexerTestUtilities.assertNextTokenEquals(ts,TestTokenId.IDENTIFIER, "ident");
            assertEquals(6, ts.offset());

            LexerTestUtilities.assertNextTokenEquals(ts,TestTokenId.WHITESPACE, " ");
            assertEquals(11, ts.offset());

            LexerTestUtilities.assertNextTokenEquals(ts,TestTokenId.JAVADOC_COMMENT, "/** @see X */");
            assertEquals(12, ts.offset());

            TokenSequence<?> inner = ts.embedded();

            assertNotNull(inner);

            LexerTestUtilities.assertNextTokenEquals(inner,TestJavadocTokenId.OTHER_TEXT, " ");
            assertEquals(15, inner.offset());

            LexerTestUtilities.assertNextTokenEquals(inner,TestJavadocTokenId.TAG, "@see");
            assertEquals(16, inner.offset());

            LexerTestUtilities.assertNextTokenEquals(inner,TestJavadocTokenId.OTHER_TEXT, " ");
            assertEquals(20, inner.offset());

            LexerTestUtilities.assertNextTokenEquals(inner,TestJavadocTokenId.IDENT, "X");
            assertEquals(21, inner.offset());

            LexerTestUtilities.assertNextTokenEquals(inner,TestJavadocTokenId.OTHER_TEXT, " ");
            assertEquals(22, inner.offset());
        } finally {
            ((AbstractDocument)d).readUnlock();
        }
    }

    public void testSnapshots() throws Exception {
        Document d = new PlainDocument();
        
        d.putProperty(Language.class,TestTokenId.language());
        
        d.insertString(0, "ident ident /** @see X */", null);
        
        TokenHierarchy<?> h = TokenHierarchy.get(d);
        ((AbstractDocument)d).readLock();
        try {
            TokenSequence<?> ts = h.tokenSequence();

            LexerTestUtilities.assertNextTokenEquals(ts,TestTokenId.IDENTIFIER, "ident");
            assertEquals(0, ts.offset());

            LexerTestUtilities.assertNextTokenEquals(ts,TestTokenId.WHITESPACE, " ");
            assertEquals(5, ts.offset());

            LexerTestUtilities.assertNextTokenEquals(ts,TestTokenId.IDENTIFIER, "ident");
            assertEquals(6, ts.offset());

            LexerTestUtilities.assertNextTokenEquals(ts,TestTokenId.WHITESPACE, " ");
            assertEquals(11, ts.offset());

            LexerTestUtilities.assertNextTokenEquals(ts,TestTokenId.JAVADOC_COMMENT, "/** @see X */");
            assertEquals(12, ts.offset());

            TokenSequence<?> inner = ts.embedded();

            assertNotNull(inner);

            LexerTestUtilities.assertNextTokenEquals(inner,TestJavadocTokenId.OTHER_TEXT, " ");
            assertEquals(15, inner.offset());

            LexerTestUtilities.assertNextTokenEquals(inner,TestJavadocTokenId.TAG, "@see");
            assertEquals(16, inner.offset());

            LexerTestUtilities.assertNextTokenEquals(inner,TestJavadocTokenId.OTHER_TEXT, " ");
            assertEquals(20, inner.offset());

            LexerTestUtilities.assertNextTokenEquals(inner,TestJavadocTokenId.IDENT, "X");
            assertEquals(21, inner.offset());

            LexerTestUtilities.assertNextTokenEquals(inner,TestJavadocTokenId.OTHER_TEXT, " ");
            assertEquals(22, inner.offset());
        } finally {
            ((AbstractDocument)d).readUnlock();
        }
            
        
        
//        h = TokenHierarchy.get(d).createSnapshot();
//        ts = h.tokenSequence();
//        
//        LexerTestUtilities.assertNextTokenEquals(ts,TestTokenId.IDENTIFIER, "ident");
//        assertEquals(0, ts.offset());
//        
//        LexerTestUtilities.assertNextTokenEquals(ts,TestTokenId.WHITESPACE, " ");
//        assertEquals(5, ts.offset());
//        
//        LexerTestUtilities.assertNextTokenEquals(ts,TestTokenId.IDENTIFIER, "ident");
//        assertEquals(6, ts.offset());
//        
//        LexerTestUtilities.assertNextTokenEquals(ts,TestTokenId.WHITESPACE, " ");
//        assertEquals(11, ts.offset());
//        
//        LexerTestUtilities.assertNextTokenEquals(ts,TestTokenId.JAVADOC_COMMENT, "/** @see X */");
//        assertEquals(12, ts.offset());
//        
//        inner = ts.embedded();
//        
//        assertNotNull(inner);
//        
//        LexerTestUtilities.assertNextTokenEquals(inner,TestJavadocTokenId.OTHER_TEXT, " ");
//        assertEquals(15, inner.offset());
//        
//        LexerTestUtilities.assertNextTokenEquals(inner,TestJavadocTokenId.TAG, "@see");
//        assertEquals(16, inner.offset());
//        
//        LexerTestUtilities.assertNextTokenEquals(inner,TestJavadocTokenId.OTHER_TEXT, " ");
//        assertEquals(20, inner.offset());
//        
//        LexerTestUtilities.assertNextTokenEquals(inner,TestJavadocTokenId.IDENT, "X");
//        assertEquals(21, inner.offset());
//        
//        LexerTestUtilities.assertNextTokenEquals(inner,TestJavadocTokenId.OTHER_TEXT, " ");
//        assertEquals(22, inner.offset());
    }
    
    public void testEmbeddingPresence() throws Exception {
        Document d = new PlainDocument();
        d.putProperty(Language.class,TestEmbeddingTokenId.language());
        d.insertString(0, " acnacn", null);
        
        TokenHierarchy<?> h = TokenHierarchy.get(d);
        ((AbstractDocument)d).readLock();
        try {
            TokenSequence<TestEmbeddingTokenId> ts = h.tokenSequence(TestEmbeddingTokenId.language());
            TokenSequence<?> inner;

            LexerTestUtilities.assertNextTokenEquals(ts,TestEmbeddingTokenId.TEXT, " ");
            inner = ts.embedded();
            LexerTestUtilities.assertNextTokenEquals(ts,TestEmbeddingTokenId.A, "a");
            inner = ts.embedded();
            LexerTestUtilities.assertNextTokenEquals(ts,TestEmbeddingTokenId.C, "c");
            inner = ts.embedded();
            LexerTestUtilities.assertNextTokenEquals(ts,TestEmbeddingTokenId.N, "n");
            inner = ts.embedded();
            LexerTestUtilities.assertNextTokenEquals(ts,TestEmbeddingTokenId.A, "a");
            inner = ts.embedded();
            LexerTestUtilities.assertNextTokenEquals(ts,TestEmbeddingTokenId.C, "c");
            inner = ts.embedded();
            LexerTestUtilities.assertNextTokenEquals(ts,TestEmbeddingTokenId.N, "n");
            inner = ts.embedded();

            assertEquals(1, TestEmbeddingTokenId.cEmbeddingQueryCount);
            assertEquals(2, TestEmbeddingTokenId.aEmbeddingQueryCount);
        } finally {
            ((AbstractDocument)d).readUnlock();
        }
    }
    
}
