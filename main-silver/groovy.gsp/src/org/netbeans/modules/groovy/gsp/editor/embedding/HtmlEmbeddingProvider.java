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
package org.netbeans.modules.groovy.gsp.editor.embedding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.netbeans.api.lexer.Language;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.modules.groovy.gsp.lexer.GspLexerLanguage;
import org.netbeans.modules.groovy.gsp.lexer.GspTokenId;
import org.netbeans.modules.parsing.api.Embedding;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.spi.EmbeddingProvider;
import org.netbeans.modules.parsing.spi.SchedulerTask;
import org.netbeans.modules.parsing.spi.TaskFactory;

/**
 * Provides model for html code.
 *
 * @author Marek Fukala
 */
public class HtmlEmbeddingProvider extends EmbeddingProvider {

    // Not sure what's the reason for this, but without it the HTML highlighting doesn't working
    public static final String GENERATED_CODE = "@@@"; //NOI18N

    @Override
    public List<Embedding> getEmbeddings(Snapshot snapshot) {
        final TokenSequence<GspTokenId> sequence = getTokenSequence(snapshot);
        final List<Embedding> embeddings = new ArrayList<Embedding>();

        boolean lastEmbeddingIsVirtual = false;
        while (sequence.moveNext()) {

            Token<GspTokenId> token = sequence.token();
            switch (token.id()) {
                case HTML:
                    embeddings.add(snapshot.create(sequence.offset(), token.length(), "text/html")); //NOI18N
                    lastEmbeddingIsVirtual = false;
                    break;
                default:
                    if (!lastEmbeddingIsVirtual) {
                        embeddings.add(snapshot.create(GENERATED_CODE, "text/html"));
                        lastEmbeddingIsVirtual = true;
                    }
            }
        }
        return Collections.singletonList(Embedding.create(embeddings));
    }

    private TokenSequence<GspTokenId> getTokenSequence(Snapshot snapshot) {
        final Language<GspTokenId> gspLanguage = GspLexerLanguage.getLanguage();
        final TokenHierarchy<CharSequence> tokenHierarchy = TokenHierarchy.create(snapshot.getText(), gspLanguage);
        final TokenSequence<GspTokenId> sequence = tokenHierarchy.tokenSequence(gspLanguage);

        sequence.moveStart();
        return sequence;
    }

    @Override
    public int getPriority() {
        return 200;
    }

    @Override
    public void cancel() {
        //do nothing
    }

    public static final class Factory extends TaskFactory {

        @Override
        public Collection<SchedulerTask> create(final Snapshot snapshot) {
            return Collections.<SchedulerTask>singletonList(new HtmlEmbeddingProvider());
        }
    }
}
