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
 * Portions Copyrighted 2010 Sun Microsystems, Inc.
 */

/*
 * RevisionPicker.java
 *
 * Created on Oct 18, 2010, 3:57:00 PM
 */

package org.netbeans.modules.git.remote.ui.repository;

/**
 *
 * @author ondra
 */
public class RevisionDialog extends javax.swing.JPanel {
    private final RevisionInfoPanel panel;

    /** Creates new form RevisionPicker */
    public RevisionDialog (RevisionInfoPanel panel) {
        this.panel = panel;
        initComponents();
    }

    public RevisionDialog () {
        this.panel = new RevisionInfoPanel();
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

        org.netbeans.modules.git.remote.ui.repository.RevisionInfoPanel revisionInfoPanel = panel;
        branchesScrollPane = new javax.swing.JScrollPane();

        lblRevision.setLabelFor(revisionField);
        org.openide.awt.Mnemonics.setLocalizedText(lblRevision, org.openide.util.NbBundle.getMessage(RevisionDialog.class, "RevisionDialog.lblRevision.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(btnSelectRevision, org.openide.util.NbBundle.getMessage(RevisionDialog.class, "RevisionDialog.btnSelectRevision.text")); // NOI18N

        lblBranch.setLabelFor(lstBranches);
        org.openide.awt.Mnemonics.setLocalizedText(lblBranch, org.openide.util.NbBundle.getMessage(RevisionDialog.class, "RevisionDialog.lblBranch.text")); // NOI18N

        branchesPanel.setLayout(new java.awt.BorderLayout());

        lstBranches.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstBranches.setToolTipText(org.openide.util.NbBundle.getMessage(RevisionDialog.class, "RevisionDialog.lblBranch.TTtext")); // NOI18N
        branchesScrollPane.setViewportView(lstBranches);

        branchesPanel.add(branchesScrollPane, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(revisionInfoPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblRevision)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(revisionField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSelectRevision))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(lblBranch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(branchesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRevision)
                    .addComponent(revisionField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSelectRevision))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblBranch)
                    .addComponent(branchesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(revisionInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    final javax.swing.JPanel branchesPanel = new javax.swing.JPanel();
    private javax.swing.JScrollPane branchesScrollPane;
    final javax.swing.JButton btnSelectRevision = new javax.swing.JButton();
    final javax.swing.JLabel lblBranch = new javax.swing.JLabel();
    final javax.swing.JLabel lblRevision = new javax.swing.JLabel();
    final javax.swing.JList lstBranches = new javax.swing.JList();
    final javax.swing.JTextField revisionField = new javax.swing.JTextField();
    // End of variables declaration//GEN-END:variables

}
