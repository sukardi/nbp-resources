/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2014 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2014 Sun Microsystems, Inc.
 */

package org.netbeans.modules.cnd.highlight.hints;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Position;
import org.netbeans.editor.BaseDocument;
import org.netbeans.modules.cnd.analysis.api.AnalyzerResponse;
import org.netbeans.modules.cnd.api.model.CsmClass;
import org.netbeans.modules.cnd.api.model.CsmFile;
import org.netbeans.modules.cnd.api.model.CsmFunctionDefinition;
import org.netbeans.modules.cnd.api.model.CsmMember;
import org.netbeans.modules.cnd.api.model.CsmMethod;
import org.netbeans.modules.cnd.api.model.CsmNamespaceDefinition;
import org.netbeans.modules.cnd.api.model.CsmOffsetableDeclaration;
import org.netbeans.modules.cnd.api.model.services.CsmFileInfoQuery;
import org.netbeans.modules.cnd.api.model.services.CsmVirtualInfoQuery;
import org.netbeans.modules.cnd.api.model.syntaxerr.AbstractCodeAudit;
import org.netbeans.modules.cnd.api.model.syntaxerr.AuditPreferences;
import org.netbeans.modules.cnd.api.model.syntaxerr.CodeAuditFactory;
import org.netbeans.modules.cnd.api.model.syntaxerr.CsmErrorInfo;
import org.netbeans.modules.cnd.api.model.syntaxerr.CsmErrorInfoHintProvider;
import org.netbeans.modules.cnd.api.model.syntaxerr.CsmErrorProvider;
import org.netbeans.modules.cnd.api.model.util.CsmKindUtilities;
import org.netbeans.spi.editor.hints.ChangeInfo;
import org.netbeans.spi.editor.hints.Fix;
import org.openide.text.NbDocument;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Alexander Simon
 */
class MissingMethodOverride extends AbstractCodeAudit {
    private MissingMethodOverride(String id, String name, String description, String defaultSeverity, boolean defaultEnabled, AuditPreferences myPreferences) {
        super(id, name, description, defaultSeverity, defaultEnabled, myPreferences);
    }
    
    @Override
    public boolean isSupportedEvent(CsmErrorProvider.EditorEvent kind) {
        return kind == CsmErrorProvider.EditorEvent.FileBased;
    }
    
    @Override
    public void doGetErrors(CsmErrorProvider.Request request, CsmErrorProvider.Response response) {
        CsmFile file = request.getFile();
        if (file != null) {
            if (request.isCancelled()) {
                return;
            }
            if (!CsmFileInfoQuery.getDefault().isCpp11OrLater(file)) {
                return;
            }
            if (request.isCancelled()) {
                return;
            }
            visit(file.getDeclarations(), request, response);
        }
    }

    private void visit(Collection<? extends CsmOffsetableDeclaration> decls, CsmErrorProvider.Request request, CsmErrorProvider.Response response) {
        for(CsmOffsetableDeclaration decl : decls) {
            if (request.isCancelled()) {
                return;
            }
            if (CsmKindUtilities.isClassMember(decl)) {
                visit((CsmMember) decl, request, response);
            } else if (CsmKindUtilities.isClass(decl)) {
                visit((CsmClass) decl, request, response);
            } else if (CsmKindUtilities.isNamespaceDefinition(decl)) {
                visit(((CsmNamespaceDefinition) decl).getDeclarations(), request, response);
            }
        }
    }
    
    private void visit(CsmMember csmMember, CsmErrorProvider.Request request, CsmErrorProvider.Response response) {
        if (CsmKindUtilities.isMethod(csmMember) && !CsmKindUtilities.isDestructor(csmMember) && !CsmKindUtilities.isConstructor(csmMember)) {
            CsmMethod method = (CsmMethod)csmMember;
            if (!request.getFile().equals(method.getContainingFile())) {
                return;
            }
            CsmVirtualInfoQuery.CsmOverriddenChain overriddenChain = CsmVirtualInfoQuery.getDefault().getOverriddenChain(method);
            if (overriddenChain.getThisMethod().isVirtual() && overriddenChain.getBaseMethods().size() > 0 && !method.isOverride()) {
                String message = NbBundle.getMessage(MissingMethodOverride.class, "MissingMethodOverride.description"); // NOI18N
                CsmErrorInfo.Severity severity = toSeverity(minimalSeverity());
                if (response instanceof AnalyzerResponse) {
                    ((AnalyzerResponse) response).addError(AnalyzerResponse.AnalyzerSeverity.DetectedError, null, method.getContainingFile().getFileObject(),
                            new MissingMethodOverrideErrorInfoImpl(request.getDocument(), method, CsmHintProvider.NAME, getID(), getName()+"\n"+message, severity, method.getStartOffset(), method.getParameterList().getEndOffset())); // NOI18N
                } else {
                    response.addError(new MissingMethodOverrideErrorInfoImpl(request.getDocument(), method, CsmHintProvider.NAME, getID(), message, severity, method.getStartOffset(), method.getParameterList().getEndOffset()));
                }
            }
        }
    }
    private void visit(CsmClass csmClass, CsmErrorProvider.Request request, CsmErrorProvider.Response response) {
        visit(csmClass.getMembers(), request, response);
    }
    
    @ServiceProvider(path = CodeAuditFactory.REGISTRATION_PATH+CsmHintProvider.NAME, service = CodeAuditFactory.class, position = 1300)
    public static final class Factory implements CodeAuditFactory {
        @Override
        public AbstractCodeAudit create(AuditPreferences preferences) {
            String id = NbBundle.getMessage(MissingMethodOverride.class, "MissingMethodOverride.name"); // NOI18N
            String description = NbBundle.getMessage(MissingMethodOverride.class, "MissingMethodOverride.description"); // NOI18N
            return new MissingMethodOverride(id, id, description, "hint", true, preferences); // NOI18N
        }
    }
    
    private static final class MissingMethodOverrideErrorInfoImpl extends ErrorInfoImpl {
        private final BaseDocument doc;
        private final int end;
        public MissingMethodOverrideErrorInfoImpl(Document doc, CsmMethod method, String providerName, String audutName, String message, CsmErrorInfo.Severity severity, int startOffset, int endOffset) {
            super(providerName, audutName, message, severity, startOffset, endOffset);
            this.doc = (BaseDocument) doc;
            if (CsmKindUtilities.isFunctionDefinition(method)) {
                end = ((CsmFunctionDefinition)method).getBody().getStartOffset()-1;
            } else {
                end = method.getEndOffset() - 1;
            }
        }
    }    
    
    @ServiceProvider(service = CsmErrorInfoHintProvider.class, position = 1200)
    public static final class MissingMethodOverrideFixProvider extends CsmErrorInfoHintProvider {

        @Override
        protected List<Fix> doGetFixes(CsmErrorInfo info, List<Fix> alreadyFound) {
            if (info instanceof MissingMethodOverrideErrorInfoImpl) {
                alreadyFound.addAll(createFixes((MissingMethodOverrideErrorInfoImpl) info));
            }
            return alreadyFound;
        }
        
        private List<? extends Fix> createFixes(MissingMethodOverrideErrorInfoImpl info) {
            try {
                return Collections.singletonList(new AddMissingOverrideKeyvord(info.doc, info.end));
            } catch (BadLocationException ex) {
                return Collections.emptyList();
            }
        }
    }
    
    private static final class AddMissingOverrideKeyvord implements Fix {
        private final BaseDocument doc;
        private final Position start;

        public AddMissingOverrideKeyvord(BaseDocument doc, int endOffset) throws BadLocationException {
            this.doc = doc;
            this.start = NbDocument.createPosition(doc, endOffset, Position.Bias.Forward);
        }

        @Override
        public String getText() {
            return NbBundle.getMessage(NonVirtualDestructor.class, "MissingMethodOverride.fix"); // NOI18N
        }

        @Override
        public ChangeInfo implement() throws Exception {
            String t = doc.getText(start.getOffset(), 1);
            String text;
            if (t.charAt(0) == ' ' || t.charAt(0) == ';') {
                text = " override"; //NOI18N
            } else {
                text = " override "; //NOI18N
            }
            doc.insertString(start.getOffset(), text, null);
            return null;
        }
    }
}
