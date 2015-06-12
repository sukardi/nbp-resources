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
 * Portions Copyrighted 2009 Sun Microsystems, Inc.
 */

/*
 * LandingPagePanel.java
 *
 * Created on Feb 25, 2009, 12:30:38 PM
 */

package org.netbeans.modules.kenai.ui;

import java.text.MessageFormat;
import org.openide.util.NbBundle;

/**
 *
 * @author Milan Kubec
 */
public class LandingPagePanel extends javax.swing.JPanel {

    private String projectName;
    private String repoPath;

    public LandingPagePanel(String prjName, String path, String kenaiName) {

        projectName = prjName;
        repoPath = path;

        initComponents();

        String repoMessage = "";
        if (repoPath != null || !"".equals(repoPath)) {
            String repoPattern = NbBundle.getMessage(LandingPagePanel.class,
                    "LandingPagePanel.messageEditorPane.repoMessage"); // NOI18N
            repoMessage = MessageFormat.format(repoPattern, repoPath);
        }

        String messagePattern = NbBundle.getMessage(LandingPagePanel.class,
                "LandingPagePanel.messageEditorPane.message"); // NOI18N
        String message = MessageFormat.format(messagePattern, projectName, repoMessage, kenaiName);
        messageEditorPane.setText(message);
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        iconLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        messageEditorPane = new javax.swing.JEditorPane();

        setPreferredSize(new java.awt.Dimension(450, 300));
        setLayout(new java.awt.GridBagLayout());

        iconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/modules/kenai/ui/resources/kenai_logo.png"))); // NOI18N
        iconLabel.setText(org.openide.util.NbBundle.getMessage(LandingPagePanel.class, "LandingPagePanel.iconLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(24, 24, 20, 30);
        add(iconLabel, gridBagConstraints);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane1.setEnabled(false);
        jScrollPane1.setOpaque(false);

        messageEditorPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        messageEditorPane.setContentType(org.openide.util.NbBundle.getMessage(LandingPagePanel.class, "LandingPagePanel.messageEditorPane.contentType")); // NOI18N
        messageEditorPane.setEditable(false);
        messageEditorPane.setText(org.openide.util.NbBundle.getMessage(LandingPagePanel.class, "LandingPagePanel.messageEditorPane.text")); // NOI18N
        messageEditorPane.setOpaque(false);
        jScrollPane1.setViewportView(messageEditorPane);
        messageEditorPane.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(LandingPagePanel.class, "LandingPagePanel.messageEditorPane.AccessibleContext.accessibleName")); // NOI18N
        messageEditorPane.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(LandingPagePanel.class, "LandingPagePanel.messageEditorPane.AccessibleContext.accessibleDescription")); // NOI18N

        jScrollPane1.getViewport().setOpaque(false);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jScrollPane1, gridBagConstraints);

        getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(LandingPagePanel.class, "LandingPagePanel.AccessibleContext.accessibleName")); // NOI18N
        getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(LandingPagePanel.class, "LandingPagePanel.AccessibleContext.accessibleDescription")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel iconLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JEditorPane messageEditorPane;
    // End of variables declaration//GEN-END:variables

}
