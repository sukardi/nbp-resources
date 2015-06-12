/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2013 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2013 Sun Microsystems, Inc.
 */
package org.netbeans.modules.cnd.modelimpl.csm;

import java.io.IOException;
import org.netbeans.modules.cnd.antlr.collections.AST;
import org.netbeans.modules.cnd.api.model.CsmDeclaration;
import org.netbeans.modules.cnd.api.model.CsmFile;
import org.netbeans.modules.cnd.api.model.CsmObject;
import org.netbeans.modules.cnd.api.model.CsmScopeElement;
import org.netbeans.modules.cnd.api.model.CsmType;
import org.netbeans.modules.cnd.api.model.CsmTypeAlias;
import org.netbeans.modules.cnd.modelimpl.csm.core.Disposable;
import org.netbeans.modules.cnd.modelimpl.csm.core.Utils;
import org.netbeans.modules.cnd.repository.spi.RepositoryDataInput;
import org.netbeans.modules.cnd.repository.spi.RepositoryDataOutput;

/**
 *
 * @author petrk
 */
public class TypeAliasImpl extends TypedefImpl implements CsmTypeAlias, CsmScopeElement, Disposable {
    
    public static TypeAliasImpl create(AST ast, CsmFile file, CsmObject container, CsmType type, CharSequence aName, boolean global) {
        TypeAliasImpl typeAliasImpl = new TypeAliasImpl(ast, file, container, type, aName);
        if (!global) {
            Utils.setSelfUID(typeAliasImpl);
        }
        return typeAliasImpl;
    }    
    

    protected TypeAliasImpl(AST ast, CsmFile file, CsmObject container, CsmType type, CharSequence aName) {
        super(ast, file, container, type, aName);
    }

    protected TypeAliasImpl(CsmType type, CharSequence name, CsmObject container, CsmFile file, int startOffset, int endOffset) {
        super(type, name, container, file, startOffset, endOffset);
    }   

    @Override
    public Kind getKind() {
        return CsmDeclaration.Kind.TYPEALIAS;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // impl of SelfPersistent
    @Override
    public void write(RepositoryDataOutput output) throws IOException {
        super.write(output);
    }
    
    public TypeAliasImpl(RepositoryDataInput input) throws IOException {
        super(input);
    }
}
