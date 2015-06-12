/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2012 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2012 Sun Microsystems, Inc.
 */
package org.netbeans.modules.cnd.apt.impl.support;

import org.netbeans.modules.cnd.apt.support.APTTokenAbstact;
import org.netbeans.modules.cnd.apt.support.APTTokenTypes;
import org.netbeans.modules.cnd.utils.cache.TextCache;
import org.openide.util.CharSequences;

/**
 *
 * @author Egor Ushakov
 */
public final class APTLiteIdToken extends APTTokenAbstact {
    private static final int COL_BITS  = 10;
    private static final int MAX_COL   = (1<<COL_BITS) - 1;
    private static final int LINE_BITS = 22;
    private static final int MAX_LINE  = (1<<LINE_BITS) - 1;
//    private static final int TYPE_BITS = 6;
//    private static final int MAX_TYPE  = (1<<TYPE_BITS) - 1;
    private final int offset;
    private final int columnLineType;
    private CharSequence text = null;

    public static boolean isApplicable(int type, int offset, int column, int line) {
        if (type == APTTokenTypes.IDENT && line <= MAX_LINE && column <= MAX_COL) {
            return true;
        }
        return false;
    }

    /**
     * Creates a new instance of APTConstTextToken
     */
    public APTLiteIdToken(int offset, int column, int line) {
        this.offset = offset;
        columnLineType = ((column & MAX_COL)<<LINE_BITS) + (line & MAX_LINE);
        assert column == getColumn();
        assert line == getLine();
    }
    public APTLiteIdToken(int offset, int column, int line, CharSequence text) {
        this.offset = offset;
        columnLineType = ((column & MAX_COL)<<LINE_BITS) + (line & MAX_LINE);
        assert text != null;
        assert CharSequences.isCompact(text);
        this.text = TextCache.getManager().getString(text);
        assert column == getColumn();
        assert line == getLine();
    }

    @Override
    public final CharSequence getTextID() {
        return this.text;
    }

    @Override
    public final void setTextID(CharSequence textID) {
        this.text = TextCache.getManager().getString(textID);
    }

    @Override
    public final String getText() {
        if(this.text != null) {
            return text.toString();
        } else {
            return "";
        }
    }

    @Override
    public final void setText(String t) {
        text = TextCache.getManager().getString(t);
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public void setOffset(int o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getType() {
        return APTTokenTypes.IDENT;
    }

    @Override
    public void setType(int t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getColumn() {
        return (columnLineType>>LINE_BITS) & MAX_COL;
    }

    @Override
    public void setColumn(int c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getLine() {
        return columnLineType & MAX_LINE;
    }

    @Override
    public void setLine(int l) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getEndOffset() {
        return getOffset() + getTextID().length();
    }

    @Override
    public int getEndLine() {
        return getLine();
    }

    @Override
    public int getEndColumn() {
        return getColumn() + getTextID().length();
    }
}
