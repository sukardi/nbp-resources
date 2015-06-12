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


package org.netbeans.modules.j2ee.ddloaders.web.multiview;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.netbeans.modules.j2ee.dd.api.common.SecurityRole;

/**
 * SecurityRoleRefPanel.java
 *
 * Panel for adding and editing the securit-role-ref element of the web deployment
 * descriptor.
 *
 * @author  ptliu
 */
public class SecurityRoleRefPanel extends javax.swing.JPanel {
    
    /** Creates new form SecurityRoleRefPanel */
    public SecurityRoleRefPanel(SecurityRole[] roles) {
        initComponents();
        
        String[] roleNames = new String[roles.length];
        for (int i = 0; i < roles.length; i++) {
            roleNames[i] = roles[i].getRoleName();
        }
        roleRefLinkCB.setModel(new DefaultComboBoxModel(roleNames));
        
        if (roleNames.length > 0) {
            roleRefLinkCB.setSelectedIndex(0);
        }
    }
    
    public String getRoleRefName() {
        return roleRefNameTF.getText();
    }
    
    public void setRoleRefName(String name) {
        roleRefNameTF.setText(name);
    }
    
    public String getRoleRefLink() {
        return (String) roleRefLinkCB.getSelectedItem();
    }
    
    public void setRoleRefLink(String link) {
        roleRefLinkCB.setSelectedItem(link.trim());
    }
    
    public String getDescription() {
        return descriptionTA.getText();
    }
    
    public void setDescription(String description) {
        descriptionTA.setText(description);
    }
    
    public JTextField getRoleRefNameTF() {
        return roleRefNameTF;
    }
    
    public JTextArea getDescriptionTA() {
        return descriptionTA;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roleRefNameLabel = new javax.swing.JLabel();
        roleRefLinkLabel = new javax.swing.JLabel();
        descriptionLabel = new javax.swing.JLabel();
        roleRefNameTF = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        descriptionTA = new javax.swing.JTextArea();
        roleRefLinkCB = new javax.swing.JComboBox();

        roleRefNameLabel.setLabelFor(roleRefNameTF);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/netbeans/modules/j2ee/ddloaders/web/multiview/Bundle"); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(roleRefNameLabel, bundle.getString("LBL_SecurityRoleRefName")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(roleRefLinkLabel, bundle.getString("LBL_SecurityRoleRefLink")); // NOI18N

        descriptionLabel.setLabelFor(descriptionTA);
        org.openide.awt.Mnemonics.setLocalizedText(descriptionLabel, bundle.getString("LBL_SecurityRoleRefDescription")); // NOI18N

        descriptionTA.setColumns(20);
        descriptionTA.setRows(5);
        jScrollPane1.setViewportView(descriptionTA);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roleRefLinkLabel)
                    .addComponent(descriptionLabel)
                    .addComponent(roleRefNameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(roleRefNameTF, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(roleRefLinkCB, 0, 300, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(roleRefNameTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(roleRefNameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(roleRefLinkLabel)
                    .addComponent(roleRefLinkCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(descriptionLabel)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JTextArea descriptionTA;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox roleRefLinkCB;
    private javax.swing.JLabel roleRefLinkLabel;
    private javax.swing.JLabel roleRefNameLabel;
    private javax.swing.JTextField roleRefNameTF;
    // End of variables declaration//GEN-END:variables
    
}
