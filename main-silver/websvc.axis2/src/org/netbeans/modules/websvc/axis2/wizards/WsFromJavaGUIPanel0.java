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

package org.netbeans.modules.websvc.axis2.wizards;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.netbeans.api.java.classpath.ClassPath;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.nodes.Node;

/**
 *
 * @author  mkuchtiak
 */
public class WsFromJavaGUIPanel0 extends javax.swing.JPanel implements ItemListener {
    WsFromJavaPanel0 wizardPanel;
    private FileObject javaClass;
    
    /** Creates new form WsFromJavaGUIPanel1 */
    public WsFromJavaGUIPanel0(WsFromJavaPanel0 wizardPanel) {
        this.wizardPanel = wizardPanel;
        initComponents();
        setName("Service Type Selection"); // NOI18N
        jRadioButton1.addItemListener(this);
        jRadioButton2.addItemListener(this);
        jTextField1.getDocument().addDocumentListener(new DocumentListener() {

            public void insertUpdate(DocumentEvent e) {
                WsFromJavaGUIPanel0.this.wizardPanel.fireChange();
            }

            public void removeUpdate(DocumentEvent e) {
                WsFromJavaGUIPanel0.this.wizardPanel.fireChange();
            }

            public void changedUpdate(DocumentEvent e) {
                WsFromJavaGUIPanel0.this.wizardPanel.fireChange();
            }
        });
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();

        jLabel1.setLabelFor(jTextField1);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(WsFromJavaGUIPanel0.class, "WsFromJavaGUIPanel0.jLabel1.text")); // NOI18N
        jLabel1.setEnabled(false);

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jRadioButton1, org.openide.util.NbBundle.getMessage(WsFromJavaGUIPanel0.class, "WsFromJavaGUIPanel0.jRadioButton1.text")); // NOI18N

        buttonGroup1.add(jRadioButton2);
        org.openide.awt.Mnemonics.setLocalizedText(jRadioButton2, org.openide.util.NbBundle.getMessage(WsFromJavaGUIPanel0.class, "WsFromJavaGUIPanel0.jRadioButton2.text")); // NOI18N

        jTextField1.setEditable(false);

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(WsFromJavaGUIPanel0.class, "WsFromJavaGUIPanel0.jButton1.text")); // NOI18N
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox1, org.openide.util.NbBundle.getMessage(WsFromJavaGUIPanel0.class, "WsFromJavaGUIPanel0.jCheckBox1.text")); // NOI18N

        jLabel2.setText(org.openide.util.NbBundle.getMessage(WsFromJavaGUIPanel0.class, "WsFromJavaGUIPanel0.jLabel2.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addContainerGap())
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel2))
                            .addComponent(jCheckBox1))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(29, 29, 29)
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jRadioButton1.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(WsFromJavaGUIPanel0.class, "WsFromJavaGUIPanel0.jRadioButton1.AccessibleContext.accessibleDescription")); // NOI18N
        jRadioButton2.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(WsFromJavaGUIPanel0.class, "WsFromJavaGUIPanel0.jRadioButton2.AccessibleContext.accessibleDescription")); // NOI18N
        jTextField1.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(WsFromJavaGUIPanel0.class, "WsFromJavaGUIPanel0.jTextField1.AccessibleContext.accessibleDescription")); // NOI18N
        jButton1.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(WsFromJavaGUIPanel0.class, "WsFromJavaGUIPanel0.jButton1.AccessibleContext.accessibleDescription")); // NOI18N
        jCheckBox1.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(WsFromJavaGUIPanel0.class, "WsFromJavaGUIPanel0.jCheckBox1.AccessibleContext.accessibleDescription")); // NOI18N

        getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(WsFromJavaGUIPanel0.class, "WsFromJavaGUIPanel0.AccessibleContext.accessibleDescription")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        SelectClassPanel panel = new SelectClassPanel(wizardPanel.getProject());
        DialogDescriptor dd = new DialogDescriptor(panel, 
                org.openide.util.NbBundle.getMessage(WsFromJavaGUIPanel0.class, "TTL_SelectClass"));
        DialogDisplayer.getDefault().notify(dd);
        if (dd.getValue() == DialogDescriptor.OK_OPTION) {
            Node[] nodes = panel.getSelectedNodes();
            if (nodes.length > 0) {
                FileObject fo = getFileObjectFromNode(nodes[0]);
                if (fo!=null) {
                    javaClass = fo;
                    ClassPath classPath = ClassPath.getClassPath(fo, ClassPath.SOURCE);
                    String className = classPath.getResourceName(fo, '.', false);
                    jTextField1.setText(className);
                }
                
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
    
    boolean isFinishable() {
        return jRadioButton2.isSelected() && jTextField1.getText().trim().length()>0;
    }
    
    boolean isFromScratch() {
        return jRadioButton1.isSelected();
    }
    
    boolean isEmptyWebService() {
        return jRadioButton1.isSelected();
    }

    public void itemStateChanged(ItemEvent e) {
        if (jRadioButton1.isSelected()) {
            jLabel1.setEnabled(false);
            jTextField1.setEditable(false);
            jButton1.setEnabled(false);
        } else {
            jLabel1.setEnabled(true);
            jTextField1.setEditable(true);
            jButton1.setEnabled(true);
        }
        WsFromJavaGUIPanel0.this.wizardPanel.fireChange();
    }
    
    boolean dataIsValid() {
        return jRadioButton1.isSelected() || jTextField1.getText().trim().length()>0;
    }
    
    private FileObject getFileObjectFromNode(Node n) {
        DataObject dObj = n.getCookie(DataObject.class);
        if (dObj!=null) return dObj.getPrimaryFile();
        return null;
    }
    
    FileObject getJavaClass() {
        return javaClass;
    }
    
    boolean generateWsdl() {
        return jCheckBox1.isSelected();
    }
}
