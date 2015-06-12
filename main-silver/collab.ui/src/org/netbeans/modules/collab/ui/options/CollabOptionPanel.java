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
package org.netbeans.modules.collab.ui.options;

/**
 *
 * @author  catlan
 */
final class CollabOptionPanel extends javax.swing.JPanel {
    private final CollabOptionModel model;

    /** Creates new form CollabOptionPanel */
    public CollabOptionPanel() {
        model = new CollabOptionModel();
        initComponents();
    }

    public void update() {
        cbAutoApprove.setSelected(
                model.getAutoApprove()
            );
        cbAutoAcceptConversation.setSelected(
                model.getAutoAcceptConversation()
            );
        cbAutoLogin.setSelected(
                model.getAutoLogin()
            );
        spIdleTimeout.setValue(
                model.getIdleTimeout()
            );
        cbPlayAudioNotifications.setSelected(
                model.getPlayAudioNotifications()
            );
        cbShowPresenceNotifications.setSelected(
                model.getShowPresenceNotifications()
            );
    }
    
    public boolean isChanged() {
        if (cbAutoApprove.isSelected() != model.getAutoApprove())
            return true;
        if (cbAutoAcceptConversation.isSelected() != model.getAutoAcceptConversation())
            return true;
        if (cbAutoLogin.isSelected() != model.getAutoLogin())
            return true;
        if (!spIdleTimeout.getValue().equals(model.getIdleTimeout()))
            return true;
        if (cbPlayAudioNotifications.isSelected() != model.getPlayAudioNotifications())
            return true;
        if (cbShowPresenceNotifications.isSelected() != model.getShowPresenceNotifications())
            return true;
        
        return false;
    }
    
    
    public void applyChanges() {
        model.setAutoApprove(
                cbAutoApprove.isSelected()
            );
        model.setAutoAcceptConversation(
                cbAutoAcceptConversation.isSelected()
            );
        model.setAutoLogin(
                cbAutoLogin.isSelected()
            );
        model.setIdleTimeout(
                (Integer)spIdleTimeout.getValue()
            );
        model.setPlayAudioNotifications(
                cbPlayAudioNotifications.isSelected()
            );
        model.setShowPresenceNotifications(
                cbShowPresenceNotifications.isSelected()
            );
    }
    
    void cancel() {
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        cbAutoAcceptConversation = new javax.swing.JCheckBox();
        cbAutoApprove = new javax.swing.JCheckBox();
        cbAutoLogin = new javax.swing.JCheckBox();
        spIdleTimeout = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        cbPlayAudioNotifications = new javax.swing.JCheckBox();
        cbShowPresenceNotifications = new javax.swing.JCheckBox();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(CollabOptionPanel.class, "LBL_CollabSettings_DisplayName"))); // NOI18N
        jPanel2.setOpaque(false);

        cbAutoAcceptConversation.setText(org.openide.util.NbBundle.getMessage(CollabOptionPanel.class, "PROP_CollabSettingsBeanInfo_AutoAcceptConversation_DisplayName")); // NOI18N
        cbAutoAcceptConversation.setToolTipText(org.openide.util.NbBundle.getMessage(CollabOptionPanel.class, "PROP_CollabSettingsBeanInfo_AutoAcceptConversation_Description")); // NOI18N
        cbAutoAcceptConversation.setMargin(new java.awt.Insets(0, 0, 0, 0));
        cbAutoAcceptConversation.setOpaque(false);

        cbAutoApprove.setText(org.openide.util.NbBundle.getMessage(CollabOptionPanel.class, "PROP_CollabSettingsBeanInfo_AutoApprove_DisplayName")); // NOI18N
        cbAutoApprove.setToolTipText(org.openide.util.NbBundle.getMessage(CollabOptionPanel.class, "PROP_CollabSettingsBeanInfo_AutoApprove_Description")); // NOI18N
        cbAutoApprove.setMargin(new java.awt.Insets(0, 0, 0, 0));
        cbAutoApprove.setOpaque(false);

        cbAutoLogin.setText(org.openide.util.NbBundle.getMessage(CollabOptionPanel.class, "PROP_CollabSettingsBeanInfo_AutoLogin_DisplayName")); // NOI18N
        cbAutoLogin.setToolTipText(org.openide.util.NbBundle.getMessage(CollabOptionPanel.class, "PROP_CollabSettingsBeanInfo_AutoLogin_Description")); // NOI18N
        cbAutoLogin.setMargin(new java.awt.Insets(0, 0, 0, 0));
        cbAutoLogin.setOpaque(false);

        spIdleTimeout.setToolTipText(org.openide.util.NbBundle.getMessage(CollabOptionPanel.class, "PROP_CollabSettingsBeanInfo_IdleTimeout_Description")); // NOI18N

        jLabel3.setLabelFor(spIdleTimeout);
        jLabel3.setText(org.openide.util.NbBundle.getMessage(CollabOptionPanel.class, "PROP_CollabSettingsBeanInfo_IdleTimeout_DisplayName")); // NOI18N
        jLabel3.setToolTipText(org.openide.util.NbBundle.getMessage(CollabOptionPanel.class, "PROP_CollabSettingsBeanInfo_IdleTimeout_Description")); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbAutoAcceptConversation)
                    .addComponent(cbAutoApprove)
                    .addComponent(cbAutoLogin)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(spIdleTimeout, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)))
                .addContainerGap(299, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(cbAutoAcceptConversation)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbAutoApprove)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbAutoLogin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spIdleTimeout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(CollabOptionPanel.class, "LBL_NotificationSettings_DisplayName"))); // NOI18N
        jPanel3.setOpaque(false);

        cbPlayAudioNotifications.setText(org.openide.util.NbBundle.getMessage(CollabOptionPanel.class, "PROP_NotificationSettingsBeanInfo_PlayAudioNotifications_DisplayName")); // NOI18N
        cbPlayAudioNotifications.setToolTipText(org.openide.util.NbBundle.getMessage(CollabOptionPanel.class, "PROP_NotificationSettingsBeanInfo_PlayAudioNotifications_Description")); // NOI18N
        cbPlayAudioNotifications.setMargin(new java.awt.Insets(0, 0, 0, 0));
        cbPlayAudioNotifications.setOpaque(false);

        cbShowPresenceNotifications.setText(org.openide.util.NbBundle.getMessage(CollabOptionPanel.class, "PROP_NotificationSettingsBeanInfo_ShowPresenceNotifications_DisplayName")); // NOI18N
        cbShowPresenceNotifications.setToolTipText(org.openide.util.NbBundle.getMessage(CollabOptionPanel.class, "PROP_NotificationSettingsBeanInfo_ShowPresenceNotifications_Description")); // NOI18N
        cbShowPresenceNotifications.setMargin(new java.awt.Insets(0, 0, 0, 0));
        cbShowPresenceNotifications.setOpaque(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbPlayAudioNotifications)
                    .addComponent(cbShowPresenceNotifications))
                .addContainerGap(420, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(cbPlayAudioNotifications)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbShowPresenceNotifications)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox cbAutoAcceptConversation;
    private javax.swing.JCheckBox cbAutoApprove;
    private javax.swing.JCheckBox cbAutoLogin;
    private javax.swing.JCheckBox cbPlayAudioNotifications;
    private javax.swing.JCheckBox cbShowPresenceNotifications;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSpinner spIdleTimeout;
    // End of variables declaration//GEN-END:variables
    
}
