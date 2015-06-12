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
 * Software is Sun Microsystems, Inc. Portions Copyright 2006 Sun
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

package org.netbeans.modules.websvc.wsitconf.ui.service.profiles;

import org.netbeans.modules.websvc.wsitconf.spi.SecurityProfile;
import org.netbeans.modules.websvc.wsitconf.spi.features.SecureConversationFeature;
import org.netbeans.modules.websvc.wsitconf.wsdlmodelext.AlgoSuiteModelHelper;
import org.netbeans.modules.websvc.wsitmodelext.versioning.ConfigVersion;
import org.netbeans.modules.websvc.wsitconf.wsdlmodelext.PolicyModelHelper;
import org.netbeans.modules.websvc.wsitconf.wsdlmodelext.SecurityPolicyModelHelper;
import org.netbeans.modules.websvc.wsitconf.wsdlmodelext.SecurityTokensModelHelper;
import org.netbeans.modules.websvc.wsitmodelext.policy.Policy;
import org.netbeans.modules.websvc.wsitmodelext.security.BootstrapPolicy;
import org.netbeans.modules.websvc.wsitmodelext.security.tokens.ProtectionToken;
import org.netbeans.modules.websvc.wsitmodelext.security.tokens.SecureConversationToken;
import org.netbeans.modules.xml.wsdl.model.WSDLComponent;

/**
 *
 * @author  Martin Grebac
 */
public class EndorsingCertificate extends ProfileBaseForm {

    /**
     * Creates new form EndorsingCertificate
     */
    public EndorsingCertificate(WSDLComponent comp, SecurityProfile secProfile) {
        super(comp, secProfile);
        initComponents();

        inSync = true;
        fillLayoutCombo(layoutCombo);
        fillAlgoSuiteCombo(algoSuiteCombo);       
        inSync = false;
        
        sync();
    }
    
    protected void sync() {
        inSync = true;

        WSDLComponent secBinding = null;
        WSDLComponent topSecBinding = SecurityPolicyModelHelper.getSecurityBindingTypeElement(comp);
        WSDLComponent protTokenKind = SecurityTokensModelHelper.getTokenElement(topSecBinding, ProtectionToken.class);
        WSDLComponent protToken = SecurityTokensModelHelper.getTokenTypeElement(protTokenKind);
        
        boolean secConv = (protToken instanceof SecureConversationToken);
        setChBox(secConvChBox, secConv);
        
        if (secConv) {
            WSDLComponent bootPolicy = SecurityTokensModelHelper.getTokenElement(protToken, BootstrapPolicy.class);
            secBinding = SecurityPolicyModelHelper.getSecurityBindingTypeElement(bootPolicy);
            Policy p = (Policy) secBinding.getParent();
            setChBox(derivedKeysChBox, SecurityPolicyModelHelper.isRequireDerivedKeys(protToken));
            setChBox(reqSigConfChBox, SecurityPolicyModelHelper.isRequireSignatureConfirmation(p));
            setChBox(encryptSignatureChBox, SecurityPolicyModelHelper.isEncryptSignature(bootPolicy));
            setChBox(encryptOrderChBox, SecurityPolicyModelHelper.isEncryptBeforeSigning(bootPolicy));
        } else {
            secBinding = SecurityPolicyModelHelper.getSecurityBindingTypeElement(comp);
            setChBox(derivedKeysChBox, false);
            setChBox(reqSigConfChBox, SecurityPolicyModelHelper.isRequireSignatureConfirmation(comp));
            setChBox(encryptSignatureChBox, SecurityPolicyModelHelper.isEncryptSignature(comp));
            setChBox(encryptOrderChBox, SecurityPolicyModelHelper.isEncryptBeforeSigning(comp));
        }

        WSDLComponent tokenKind = SecurityTokensModelHelper.getTokenElement(secBinding, ProtectionToken.class);
        WSDLComponent token = SecurityTokensModelHelper.getTokenTypeElement(tokenKind);
        setChBox(reqDerivedKeys, SecurityPolicyModelHelper.isRequireDerivedKeys(token));

        setCombo(algoSuiteCombo, AlgoSuiteModelHelper.getAlgorithmSuite(secBinding));
        setCombo(layoutCombo, SecurityPolicyModelHelper.getMessageLayout(secBinding));

        enableDisable();
        
        inSync = false;
    }

    protected void setValue(javax.swing.JComponent source) {

        if (inSync) return;

        WSDLComponent secBinding = null;
        WSDLComponent topSecBinding = SecurityPolicyModelHelper.getSecurityBindingTypeElement(comp);
        WSDLComponent protTokenKind = SecurityTokensModelHelper.getTokenElement(topSecBinding, ProtectionToken.class);
        WSDLComponent protToken = SecurityTokensModelHelper.getTokenTypeElement(protTokenKind);
        
        boolean secConv = (protToken instanceof SecureConversationToken);

        if (source.equals(secConvChBox)) {
            ((SecureConversationFeature)secProfile).enableSecureConversation(comp, secConvChBox.isSelected());
            sync();
        }

        ConfigVersion configVersion = PolicyModelHelper.getConfigVersion(comp);
        SecurityPolicyModelHelper spmh = SecurityPolicyModelHelper.getInstance(configVersion);
        AlgoSuiteModelHelper asmh = AlgoSuiteModelHelper.getInstance(configVersion);
        if (secConv) {
            WSDLComponent bootPolicy = SecurityTokensModelHelper.getTokenElement(protToken, BootstrapPolicy.class);
            secBinding = SecurityPolicyModelHelper.getSecurityBindingTypeElement(bootPolicy);
            Policy p = (Policy) secBinding.getParent();
            if (source.equals(reqSigConfChBox)) {
                spmh.enableRequireSignatureConfirmation(
                        SecurityPolicyModelHelper.getWss11(p), reqSigConfChBox.isSelected());
            }
            if (source.equals(derivedKeysChBox)) {
                spmh.enableRequireDerivedKeys(protToken, derivedKeysChBox.isSelected());
            }
        } else {
            secBinding = SecurityPolicyModelHelper.getSecurityBindingTypeElement(comp);
            if (source.equals(reqSigConfChBox)) {
                spmh.enableRequireSignatureConfirmation(SecurityPolicyModelHelper.getWss11(comp), reqSigConfChBox.isSelected());
            }
        }
        
        if (source.equals(encryptSignatureChBox)) {
            spmh.enableEncryptSignature(secBinding, encryptSignatureChBox.isSelected());
            if (secConv) {
                spmh.enableEncryptSignature(topSecBinding, encryptSignatureChBox.isSelected());
            }
        }
        if (source.equals(encryptOrderChBox)) {
            spmh.enableEncryptBeforeSigning(secBinding, encryptOrderChBox.isSelected());
            if (secConv) {
                spmh.enableEncryptBeforeSigning(topSecBinding, encryptOrderChBox.isSelected());
            }
        }
        if (source.equals(layoutCombo)) {
            spmh.setLayout(secBinding, (String) layoutCombo.getSelectedItem());
            if (secConv) {
                spmh.setLayout(topSecBinding, (String) layoutCombo.getSelectedItem());
            }
        }
        if (source.equals(algoSuiteCombo)) {
            asmh.setAlgorithmSuite(secBinding, (String) algoSuiteCombo.getSelectedItem());
            if (secConv) {
                asmh.setAlgorithmSuite(topSecBinding, (String) algoSuiteCombo.getSelectedItem());
            }
        }

        if (source.equals(reqDerivedKeys)) {
            WSDLComponent tokenKind = SecurityTokensModelHelper.getTokenElement(secBinding, ProtectionToken.class);
            WSDLComponent token = SecurityTokensModelHelper.getTokenTypeElement(tokenKind);
            spmh.enableRequireDerivedKeys(token, reqDerivedKeys.isSelected());
            return;
        }
        
        enableDisable();
    }

    protected void enableDisable() {
        boolean secConvEnabled = secConvChBox.isSelected();
        derivedKeysChBox.setEnabled(secConvEnabled);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        secConvChBox = new javax.swing.JCheckBox();
        reqSigConfChBox = new javax.swing.JCheckBox();
        derivedKeysChBox = new javax.swing.JCheckBox();
        algoSuiteLabel = new javax.swing.JLabel();
        algoSuiteCombo = new javax.swing.JComboBox();
        layoutLabel = new javax.swing.JLabel();
        layoutCombo = new javax.swing.JComboBox();
        encryptSignatureChBox = new javax.swing.JCheckBox();
        encryptOrderChBox = new javax.swing.JCheckBox();
        reqDerivedKeys = new javax.swing.JCheckBox();

        org.openide.awt.Mnemonics.setLocalizedText(secConvChBox, org.openide.util.NbBundle.getMessage(EndorsingCertificate.class, "LBL_SecConvLabel")); // NOI18N
        secConvChBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        secConvChBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                secConvChBoxActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(reqSigConfChBox, org.openide.util.NbBundle.getMessage(EndorsingCertificate.class, "LBL_RequireSigConfirmation")); // NOI18N
        reqSigConfChBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        reqSigConfChBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reqSigConfChBoxActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(derivedKeysChBox, org.openide.util.NbBundle.getMessage(EndorsingCertificate.class, "LBL_RequireDerivedKeysForSecConv")); // NOI18N
        derivedKeysChBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        derivedKeysChBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                derivedKeysChBoxActionPerformed(evt);
            }
        });

        algoSuiteLabel.setLabelFor(algoSuiteCombo);
        org.openide.awt.Mnemonics.setLocalizedText(algoSuiteLabel, org.openide.util.NbBundle.getMessage(EndorsingCertificate.class, "LBL_AlgoSuiteLabel")); // NOI18N

        algoSuiteCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                algoSuiteComboActionPerformed(evt);
            }
        });

        layoutLabel.setLabelFor(layoutCombo);
        org.openide.awt.Mnemonics.setLocalizedText(layoutLabel, org.openide.util.NbBundle.getMessage(EndorsingCertificate.class, "LBL_LayoutLabel")); // NOI18N

        layoutCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                layoutComboActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(encryptSignatureChBox, org.openide.util.NbBundle.getMessage(EndorsingCertificate.class, "LBL_EncryptSignatureLabel")); // NOI18N
        encryptSignatureChBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        encryptSignatureChBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                encryptSignatureChBox(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(encryptOrderChBox, org.openide.util.NbBundle.getMessage(EndorsingCertificate.class, "LBL_EncryptOrderLabel")); // NOI18N
        encryptOrderChBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        encryptOrderChBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                encryptOrderChBoxActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(reqDerivedKeys, org.openide.util.NbBundle.getMessage(EndorsingCertificate.class, "LBL_RequireDerivedKeys")); // NOI18N
        reqDerivedKeys.setMargin(new java.awt.Insets(0, 0, 0, 0));
        reqDerivedKeys.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reqDerivedKeysActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(layoutLabel)
                    .addComponent(algoSuiteLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(layoutCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(algoSuiteCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(166, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addComponent(reqDerivedKeys)
                .addGap(178, 178, 178))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(reqSigConfChBox)
                    .addComponent(secConvChBox)
                    .addComponent(derivedKeysChBox)
                    .addComponent(encryptSignatureChBox)
                    .addComponent(encryptOrderChBox))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {algoSuiteCombo, layoutCombo});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(algoSuiteLabel)
                    .addComponent(algoSuiteCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(layoutLabel)
                    .addComponent(layoutCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addComponent(reqDerivedKeys)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(secConvChBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(derivedKeysChBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reqSigConfChBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(encryptSignatureChBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(encryptOrderChBox)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {algoSuiteCombo, layoutCombo});

    }// </editor-fold>//GEN-END:initComponents

    private void encryptOrderChBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_encryptOrderChBoxActionPerformed
         setValue(encryptOrderChBox);
    }//GEN-LAST:event_encryptOrderChBoxActionPerformed

    private void reqSigConfChBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reqSigConfChBoxActionPerformed
         setValue(reqSigConfChBox);
    }//GEN-LAST:event_reqSigConfChBoxActionPerformed

    private void derivedKeysChBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_derivedKeysChBoxActionPerformed
         setValue(derivedKeysChBox);
    }//GEN-LAST:event_derivedKeysChBoxActionPerformed

    private void secConvChBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_secConvChBoxActionPerformed
        setValue(secConvChBox);
    }//GEN-LAST:event_secConvChBoxActionPerformed

    private void encryptSignatureChBox(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_encryptSignatureChBox
        setValue(encryptSignatureChBox);
    }//GEN-LAST:event_encryptSignatureChBox

    private void layoutComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_layoutComboActionPerformed
        setValue(layoutCombo);
    }//GEN-LAST:event_layoutComboActionPerformed

    private void algoSuiteComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_algoSuiteComboActionPerformed
        setValue(algoSuiteCombo);
    }//GEN-LAST:event_algoSuiteComboActionPerformed

    private void reqDerivedKeysActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reqDerivedKeysActionPerformed
        setValue(reqDerivedKeys);
}//GEN-LAST:event_reqDerivedKeysActionPerformed
            
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox algoSuiteCombo;
    private javax.swing.JLabel algoSuiteLabel;
    private javax.swing.JCheckBox derivedKeysChBox;
    private javax.swing.JCheckBox encryptOrderChBox;
    private javax.swing.JCheckBox encryptSignatureChBox;
    private javax.swing.JComboBox layoutCombo;
    private javax.swing.JLabel layoutLabel;
    private javax.swing.JCheckBox reqDerivedKeys;
    private javax.swing.JCheckBox reqSigConfChBox;
    private javax.swing.JCheckBox secConvChBox;
    // End of variables declaration//GEN-END:variables
    
}
