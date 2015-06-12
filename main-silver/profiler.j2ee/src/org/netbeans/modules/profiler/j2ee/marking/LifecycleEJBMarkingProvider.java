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

package org.netbeans.modules.profiler.j2ee.marking;

import java.util.HashSet;
import java.util.Set;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

import org.netbeans.lib.profiler.marker.Mark;
import org.openide.util.Lookup;

/**
 *
 * @author Jaroslav Bachorik
 */
public class LifecycleEJBMarkingProvider extends BaseEJBMarkingProvider {
    private static Set<String> includedMethodNames = new HashSet<String>() {
        {
            add("ejbActivate");
            add("ejbPassivate");
            add("ejbRemove");
            add("setSessionContext");
            add("setEntityContext");
            add("unsetEntityContext");
            add("setMessageDrivenContext");
        }
    };
    private static Set<String> includedAnnotationsNames = new HashSet<String>() {
        {
            add("javax.ejb.PostActivate");
            add("javax.ejb.PrePassivate");
            add("javax.ejb.Remove");
        }
    };

    public LifecycleEJBMarkingProvider(Lookup.Provider project, Mark assignedMark) {
        super(project, assignedMark);
    }

    @Override
    protected boolean isValid(ExecutableElement method) {
        return isApplicable(method);
    }
    
    static boolean isApplicable(ExecutableElement method) {
        if (!includedMethodNames.contains(method.getSimpleName().toString())) {
            for(AnnotationMirror am : method.getAnnotationMirrors()) {
                if (includedAnnotationsNames.contains(
                        ((TypeElement)am.getAnnotationType().asElement()).
                        getQualifiedName().toString()))
                {
                    return true;
                }
            }
        } else {
            return true;
        }
        return false;
    }
}
