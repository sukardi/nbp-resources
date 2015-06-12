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
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2008 Sun
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
package org.netbeans.modules.clearcase.ui.status;

import org.openide.util.ImageUtilities;
import org.openide.util.actions.SystemAction;
import org.openide.util.HelpCtx;

import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;

/**
 * @author Maros Sandor
 */
public class OpenVersioningAction extends SystemAction {

    public OpenVersioningAction() {
        putValue("noIconInMenu", Boolean.FALSE); // NOI18N
        setIcon(ImageUtilities.loadImageIcon("org/netbeans/modules/clearcase/resources/icons/versioning-view.png", false)); // NOI18N
    }


    public String getName() {
        return NbBundle.getMessage(OpenVersioningAction.class, "OpenCCView_Name_Clearcase"); //NOI18N;
    }

    public HelpCtx getHelpCtx() {
        return new HelpCtx(OpenVersioningAction.class);
    }

    public void actionPerformed(ActionEvent ev) {
        ClearcaseTopComponent stc = ClearcaseTopComponent.getInstance();
        if (stc.hasContext()) {
            stc.open();
            stc.requestActive();
        } else {
            // TODO: set the context
            stc.open();
            stc.requestActive();
        }
    }
}
