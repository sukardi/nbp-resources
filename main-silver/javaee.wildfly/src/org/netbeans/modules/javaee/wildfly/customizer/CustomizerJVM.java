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
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
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

package org.netbeans.modules.javaee.wildfly.customizer;

import org.netbeans.api.java.platform.PlatformsCustomizer;

/**
 * Customizer JVM (Platform) tab.
 *
 * @author  sherold
 */
public class CustomizerJVM extends javax.swing.JPanel {
    
    private CustomizerDataSupport custData;
    
    /** Creates new form CustomizerJVM */
    public CustomizerJVM(CustomizerDataSupport custData) {
        this.custData = custData;
        initComponents();
        
        // mnemonics generated in the guarded block do not work
        proxyCheckBox.setMnemonic(org.openide.util.NbBundle.getMessage(CustomizerJVM.class, "MNE_Proxy").charAt(0));
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jvmLabel = new javax.swing.JLabel();
        jvmButton = new javax.swing.JButton();
        jvmComboBox = new javax.swing.JComboBox();
        proxyCheckBox = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jvmLabel.setLabelFor(jvmComboBox);
        org.openide.awt.Mnemonics.setLocalizedText(jvmLabel, org.openide.util.NbBundle.getMessage(CustomizerJVM.class, "TXT_JVM")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 0, 0);
        jPanel1.add(jvmLabel, gridBagConstraints);
        jvmLabel.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(CustomizerJVM.class, "ASCN_JVM")); // NOI18N
        jvmLabel.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(CustomizerJVM.class, "ASCD_JavaPlatform")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jvmButton, org.openide.util.NbBundle.getMessage(CustomizerJVM.class, "TXT_ManagePlatforms")); // NOI18N
        jvmButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jvmButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 5, 0, 12);
        jPanel1.add(jvmButton, gridBagConstraints);
        jvmButton.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(CustomizerJVM.class, "ASCN_ManagerPlatform")); // NOI18N
        jvmButton.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(CustomizerJVM.class, "ASCD_ManagerPlatform")); // NOI18N

        jvmComboBox.setModel(custData.getJvmModel());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(12, 5, 0, 0);
        jPanel1.add(jvmComboBox, gridBagConstraints);
        jvmComboBox.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(CustomizerJVM.class, "ASCN_JVM")); // NOI18N
        jvmComboBox.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(CustomizerJVM.class, "ASCD_JVM")); // NOI18N

        proxyCheckBox.setModel(custData.getProxyModel());
        proxyCheckBox.setMnemonic(org.openide.util.NbBundle.getMessage(CustomizerJVM.class, "MNE_Proxy").charAt(0));
        proxyCheckBox.setText(org.openide.util.NbBundle.getMessage(CustomizerJVM.class, "LBL_Proxy")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(11, 12, 0, 0);
        jPanel1.add(proxyCheckBox, gridBagConstraints);
        proxyCheckBox.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(CustomizerJVM.class, "ASCN_Proxy")); // NOI18N
        proxyCheckBox.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(CustomizerJVM.class, "ASCD_Proxy")); // NOI18N

        jLabel1.setLabelFor(jTextField1);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(CustomizerJVM.class, "TXT_VMOptions")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 0);
        jPanel1.add(jLabel1, gridBagConstraints);
        jLabel1.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(CustomizerJVM.class, "ASCN_VMOpts")); // NOI18N
        jLabel1.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(CustomizerJVM.class, "ASCD_VMOpts")); // NOI18N

        jTextField1.setColumns(30);
        jTextField1.setDocument(custData.getJavaOptsModel());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 12);
        jPanel1.add(jTextField1, gridBagConstraints);
        jTextField1.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(CustomizerJVM.class, "ASCN_VMOpts")); // NOI18N
        jTextField1.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(CustomizerJVM.class, "ASCD_VMOpts")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(CustomizerJVM.class, "TXT_VMOptionsEG")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        jPanel1.add(jLabel2, gridBagConstraints);
        jLabel2.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(CustomizerJVM.class, "ASCN_VMOptEG")); // NOI18N
        jLabel2.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(CustomizerJVM.class, "ASCD_VmOptsEG")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(CustomizerJVM.class, "TXT_NoteChangesTakeAffect")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 12, 0);
        jPanel1.add(jLabel3, gridBagConstraints);
        jLabel3.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(CustomizerJVM.class, "ASCN_Note")); // NOI18N
        jLabel3.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(CustomizerJVM.class, "ASCD_Note")); // NOI18N

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jvmButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jvmButtonActionPerformed
        PlatformsCustomizer.showCustomizer(null);
        custData.loadJvmModel();
    }//GEN-LAST:event_jvmButtonActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton jvmButton;
    private javax.swing.JComboBox jvmComboBox;
    private javax.swing.JLabel jvmLabel;
    private javax.swing.JCheckBox proxyCheckBox;
    // End of variables declaration//GEN-END:variables
    
}
