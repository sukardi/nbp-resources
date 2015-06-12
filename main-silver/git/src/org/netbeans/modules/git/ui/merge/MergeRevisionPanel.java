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

/*
 * MergeRevisionPanel.java
 *
 * Created on Jan 18, 2011, 10:50:16 AM
 */

package org.netbeans.modules.git.ui.merge;

import org.netbeans.modules.git.ui.repository.RevisionDialog;

/**
 *
 * @author ondra
 */
public class MergeRevisionPanel extends javax.swing.JPanel {
    private final RevisionDialog revisionPanel;

    /** Creates new form MergeRevisionPanel */
    public MergeRevisionPanel (RevisionDialog revisionPanel) {
        this.revisionPanel = revisionPanel;
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rbFFOptions = new javax.swing.ButtonGroup();
        org.netbeans.modules.git.ui.repository.RevisionDialog revisionPickerDialog1 = this.revisionPanel;
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(MergeRevisionPanel.class, "MergeRevisionPanel.jLabel1.text")); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(MergeRevisionPanel.class, "MergeRevisionPanel.ffModePanel.text"))); // NOI18N

        rbFFOptions.add(rbFFOption);
        org.openide.awt.Mnemonics.setLocalizedText(rbFFOption, org.openide.util.NbBundle.getMessage(MergeRevisionPanel.class, "MergeRevision.ffoption.ff")); // NOI18N
        rbFFOption.setToolTipText(org.openide.util.NbBundle.getMessage(MergeRevisionPanel.class, "MergeRevision.ffoption.ff.tt")); // NOI18N

        rbFFOptions.add(rbFFOptionNever);
        org.openide.awt.Mnemonics.setLocalizedText(rbFFOptionNever, org.openide.util.NbBundle.getMessage(MergeRevisionPanel.class, "MergeRevision.ffoption.noff")); // NOI18N
        rbFFOptionNever.setToolTipText(org.openide.util.NbBundle.getMessage(MergeRevisionPanel.class, "MergeRevision.ffoption.noff.tt")); // NOI18N

        rbFFOptions.add(rbFFOptionOnly);
        org.openide.awt.Mnemonics.setLocalizedText(rbFFOptionOnly, org.openide.util.NbBundle.getMessage(MergeRevisionPanel.class, "MergeRevision.ffoption.ffonly")); // NOI18N
        rbFFOptionOnly.setToolTipText(org.openide.util.NbBundle.getMessage(MergeRevisionPanel.class, "MergeRevision.ffoption.ffonly.tt")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbFFOptionNever)
                    .addComponent(rbFFOptionOnly)
                    .addComponent(rbFFOption))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbFFOption)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbFFOptionOnly)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbFFOptionNever)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(revisionPickerDialog1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(revisionPickerDialog1, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    final javax.swing.JRadioButton rbFFOption = new javax.swing.JRadioButton();
    final javax.swing.JRadioButton rbFFOptionNever = new javax.swing.JRadioButton();
    final javax.swing.JRadioButton rbFFOptionOnly = new javax.swing.JRadioButton();
    private javax.swing.ButtonGroup rbFFOptions;
    // End of variables declaration//GEN-END:variables

}
