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
 * RevertPanel.java
 *
 * Created on May 11, 2011, 3:48:55 PM
 */
package org.netbeans.modules.localhistory.ui.actions;

import java.awt.Dialog;
import java.awt.EventQueue;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.util.NbBundle;

/**
 *
 * @author tomas
 */
public class RevertPanel extends javax.swing.JPanel {

    /** Creates new form RevertPanel */
    public RevertPanel() {
        initComponents();
        listScrollPane.setVisible(false);
        titleLabel.setVisible(false);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        listScrollPane = new javax.swing.JScrollPane();
        titleLabel = new javax.swing.JLabel();
        messageLabel = new javax.swing.JLabel();

        tree.setRootVisible(false);
        tree.setShowsRootHandles(true);
        listScrollPane.setViewportView(tree);

        titleLabel.setText(org.openide.util.NbBundle.getMessage(RevertPanel.class, "RevertPanel.titleLabel.text")); // NOI18N

        initPanel.setBackground(javax.swing.UIManager.getDefaults().getColor("List.background"));
        initPanel.setMinimumSize(new java.awt.Dimension(380, 195));
        initPanel.setRequestFocusEnabled(false);
        initPanel.setLayout(new java.awt.GridBagLayout());

        messageLabel.setText(org.openide.util.NbBundle.getMessage(RevertPanel.class, "RevertPanel.messageLabel.text")); // NOI18N
        messageLabel.setEnabled(false);
        initPanel.add(messageLabel, new java.awt.GridBagConstraints());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(listScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addComponent(titleLabel)
                    .addComponent(initPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(listScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(initPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    final javax.swing.JPanel initPanel = new javax.swing.JPanel();
    private javax.swing.JScrollPane listScrollPane;
    private javax.swing.JLabel messageLabel;
    private javax.swing.JLabel titleLabel;
    final javax.swing.JTree tree = new javax.swing.JTree();
    // End of variables declaration//GEN-END:variables


    void setRootNode(final TreeNode root) {
        if(root != null) {
            tree.setModel(new DefaultTreeModel(root));
            for (int i = 0; i < tree.getRowCount(); i++) {
                tree.expandRow(i);
            }
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    listScrollPane.setVisible(true);
                    titleLabel.setVisible(true);
                    initPanel.setVisible(false);
                }
            });
        } else {
            messageLabel.setText(NbBundle.getMessage(RevertDeletedAction.class, "MSG_NO_FILES")); // NOI18N
        }
    }
    
    TreeNode getRootNode() {
        return (TreeNode) tree.getModel().getRoot();
    }

    boolean open() {
        final DialogDescriptor dd = 
            new DialogDescriptor (
                this, 
                NbBundle.getMessage(RevertDeletedAction.class, "LBL_SELECT_FILES"), // NOI18N
                true, 
                DialogDescriptor.OK_CANCEL_OPTION, 
                DialogDescriptor.OK_OPTION, 
                null); 
        final Dialog dialog = DialogDisplayer.getDefault().createDialog(dd);
        dialog.setVisible(true);
        return dd.getValue() == DialogDescriptor.OK_OPTION;
    }
    
}
