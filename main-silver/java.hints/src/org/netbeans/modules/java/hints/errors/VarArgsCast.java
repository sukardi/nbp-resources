/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2011 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2011 Sun Microsystems, Inc.
 */

package org.netbeans.modules.java.hints.errors;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.Tree.Kind;
import com.sun.source.util.TreePath;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.netbeans.api.java.source.CompilationInfo;
import org.netbeans.api.java.source.TreeMaker;
import org.netbeans.api.java.source.TypeMirrorHandle;
import org.netbeans.api.java.source.WorkingCopy;
import org.netbeans.modules.java.hints.spi.ErrorRule;
import org.netbeans.modules.java.hints.spi.ErrorRule.Data;
import org.netbeans.spi.editor.hints.Fix;
import org.netbeans.spi.java.hints.JavaFix;
import org.netbeans.spi.java.hints.JavaFixUtilities;
import org.openide.util.NbBundle;

/**
 *
 * @author lahvac
 */
public class VarArgsCast implements ErrorRule<Void> {

    private static final Set<String> CODES = new HashSet<String>(Arrays.asList("compiler.warn.inexact.non-varargs.call"));
    
    @Override
    public Set<String> getCodes() {
        return CODES;
    }

    @Override
    public List<Fix> run(CompilationInfo info, String diagnosticKey, int offset, TreePath treePath, Data<Void> data) {
        TreePath call = treePath;//.getParentPath();
        
        if (call.getLeaf().getKind() != Kind.METHOD_INVOCATION) {
            call = call.getParentPath();
            if (call.getLeaf().getKind() != Kind.METHOD_INVOCATION) {
                return null;
            }
        }
        
        MethodInvocationTree mit = (MethodInvocationTree) call.getLeaf();
        TypeMirror mType = info.getTrees().getTypeMirror(new TreePath(call, mit.getMethodSelect()));
        
        if (mType == null || mType.getKind() != TypeKind.EXECUTABLE) {
            return null;
        }
        
        ExecutableType methodType = (ExecutableType) mType;
        
        if (methodType.getParameterTypes().isEmpty() || methodType.getParameterTypes().get(methodType.getParameterTypes().size() - 1).getKind() != TypeKind.ARRAY) {
            return null;
        }
        
        ArrayType targetArray = (ArrayType) methodType.getParameterTypes().get(methodType.getParameterTypes().size() - 1);
        TreePath target = new TreePath(call, mit.getArguments().get(mit.getArguments().size() - 1));
        
        return Arrays.asList(new FixImpl(info, target, targetArray.getComponentType()).toEditorFix(),
                             new FixImpl(info, target, targetArray).toEditorFix());
    }

    @Override
    public String getId() {
        return VarArgsCast.class.getName();
    }

    @Override
    public String getDisplayName() {
        return NbBundle.getMessage(VarArgsCast.class, "DN_VarArgsCast");
    }

    @Override
    public void cancel() {
        
    }
    
    static final class FixImpl extends JavaFix {
        
        private final TypeMirrorHandle<TypeMirror> type;
        private final String treeName;
        private final String typeName;

        public FixImpl(CompilationInfo info, TreePath path, TypeMirror type) {
            super(info, path);
            this.type = TypeMirrorHandle.create(type);
            this.treeName = org.netbeans.modules.java.hints.errors.Utilities.shortDisplayName(info, (ExpressionTree) path.getLeaf());
            this.typeName = info.getTypeUtilities().getTypeName(type).toString();
        }

        @Override
        protected String getText() {
            return NbBundle.getMessage(VarArgsCast.class, "FIX_VarArgsCast", treeName, typeName);
        }

        @Override
        protected void performRewrite(TransformationContext ctx) {
            WorkingCopy wc = ctx.getWorkingCopy();
            TreePath tp = ctx.getPath();
            TypeMirror targetType = this.type.resolve(wc);
            
            if (targetType == null) {
                //XXX: log
                return ;
            }
            
            TreeMaker make = wc.getTreeMaker();
            ExpressionTree nue = make.TypeCast(make.Type(targetType), (ExpressionTree) tp.getLeaf());
            
            if (JavaFixUtilities.requiresParenthesis(tp.getLeaf(), tp.getLeaf(), nue)) {
                nue = make.TypeCast(make.Type(targetType), make.Parenthesized((ExpressionTree) tp.getLeaf()));
            }
            
            wc.rewrite(tp.getLeaf(), nue);
        }
        
    }

}
