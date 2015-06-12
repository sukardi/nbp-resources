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

/*
 * Customizer.java
 *
 * Created on 23.Mar 2004, 11:31
 */
package org.netbeans.modules.mobility.project.ui.customizer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import org.netbeans.api.java.platform.JavaPlatform;
import org.netbeans.api.java.platform.JavaPlatformManager;
import org.netbeans.api.java.platform.Specification;
import org.netbeans.api.mobility.project.ui.customizer.ProjectProperties;
import org.netbeans.modules.mobility.cldcplatform.J2MEPlatform;
import org.netbeans.modules.mobility.project.DefaultPropertiesDescriptor;
import org.netbeans.spi.mobility.project.ui.customizer.CustomizerPanel;
import org.netbeans.spi.mobility.project.ui.customizer.support.VisualPropertySupport;
import org.netbeans.spi.mobility.project.ui.customizer.VisualPropertyGroup;
import org.openide.util.NbBundle;

/**
 *
 * @author  Adam Sotona
 */
public class CustomizerRun extends JPanel implements CustomizerPanel, VisualPropertyGroup, ActionListener {
    
    private static final String[] PROPERTY_GROUP = new String[] {DefaultPropertiesDescriptor.RUN_METHOD, DefaultPropertiesDescriptor.RUN_SECURITY_DOMAIN, DefaultPropertiesDescriptor.RUN_USE_SECURITY_DOMAIN, DefaultPropertiesDescriptor.RUN_CMD_OPTIONS, DefaultPropertiesDescriptor.DEBUG_TIMEOUT};
    
    private VisualPropertySupport vps;
    String[] securitydomains;
    
    /** Creates new form CustomizerConfigs */
    public CustomizerRun() {
        initComponents();
        initAccessibility();
        standardRadio.setActionCommand("STANDARD"); // NOI18N
        OTARadio.setActionCommand("OTA"); // NOI18N
        jCheckBoxUseSecurity.addActionListener(this);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        runMethodGroup = new javax.swing.ButtonGroup();
        defaultCheck = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        cmdOptionsText = new javax.swing.JTextField();
        standardRadio = new javax.swing.JRadioButton();
        jCheckBoxUseSecurity = new javax.swing.JCheckBox();
        domainsCombo = new javax.swing.JComboBox();
        OTARadio = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        debugTimeoutLabel = new javax.swing.JLabel();
        debugTimeoutField = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();

        setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(defaultCheck, NbBundle.getMessage(CustomizerRun.class, "LBL_Use_Default")); // NOI18N
        defaultCheck.setMargin(new java.awt.Insets(0, 0, 0, 2));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        add(defaultCheck, gridBagConstraints);
        defaultCheck.getAccessibleContext().setAccessibleDescription(NbBundle.getMessage(CustomizerRun.class, "ACSD_CustRun_UseDefault")); // NOI18N

        jLabel1.setLabelFor(cmdOptionsText);
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, NbBundle.getMessage(CustomizerRun.class, "LBL_CustRun_CmdOptions")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 0, 0, 5);
        add(jLabel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(11, 0, 0, 0);
        add(cmdOptionsText, gridBagConstraints);
        cmdOptionsText.getAccessibleContext().setAccessibleDescription(NbBundle.getMessage(CustomizerRun.class, "ACSD_CustRun_CmdOptions")); // NOI18N

        runMethodGroup.add(standardRadio);
        org.openide.awt.Mnemonics.setLocalizedText(standardRadio, NbBundle.getMessage(CustomizerRun.class, "LBL_CustRun_RegularExecution")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(12, 0, 0, 0);
        add(standardRadio, gridBagConstraints);
        standardRadio.getAccessibleContext().setAccessibleDescription(NbBundle.getMessage(CustomizerRun.class, "ACSD_CustRun_Standard")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBoxUseSecurity, NbBundle.getMessage(CustomizerRun.class, "LBL_CustRun_SecurityDomain")); // NOI18N
        jCheckBoxUseSecurity.setActionCommand(NbBundle.getMessage(CustomizerRun.class, "LBL_CustRun_SecurityDomain")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 12, 0, 0);
        add(jCheckBoxUseSecurity, gridBagConstraints);
        jCheckBoxUseSecurity.getAccessibleContext().setAccessibleDescription(NbBundle.getMessage(CustomizerRun.class, "ACSD_CustRun_UseSecurity")); // NOI18N

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        add(domainsCombo, gridBagConstraints);
        domainsCombo.getAccessibleContext().setAccessibleName(NbBundle.getMessage(CustomizerRun.class, "ACSN_CustRun_Domain")); // NOI18N
        domainsCombo.getAccessibleContext().setAccessibleDescription(NbBundle.getMessage(CustomizerRun.class, "ACSD_CustRun_Domain")); // NOI18N

        runMethodGroup.add(OTARadio);
        org.openide.awt.Mnemonics.setLocalizedText(OTARadio, NbBundle.getMessage(CustomizerRun.class, "LBL_CustRun_OTA")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(11, 0, 5, 0);
        add(OTARadio, gridBagConstraints);
        OTARadio.getAccessibleContext().setAccessibleDescription(NbBundle.getMessage(CustomizerRun.class, "ACSD_CustRun_OTA")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, NbBundle.getMessage(CustomizerRun.class, "LBL_CustRun_CmdHint")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        add(jLabel2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 6, 0);
        add(jSeparator1, gridBagConstraints);

        debugTimeoutLabel.setLabelFor(debugTimeoutField);
        org.openide.awt.Mnemonics.setLocalizedText(debugTimeoutLabel, org.openide.util.NbBundle.getMessage(CustomizerRun.class, "LBL_CustRun_DebugTimeout")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 6, 5);
        add(debugTimeoutLabel, gridBagConstraints);
        debugTimeoutLabel.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(CustomizerRun.class, "ACSN_CustRun_DebugTimeout")); // NOI18N
        debugTimeoutLabel.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(CustomizerRun.class, "ACSD_CustRun_DebugTimeout")); // NOI18N

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        add(debugTimeoutField, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jPanel1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
    
    private void initAccessibility() {
        getAccessibleContext().setAccessibleName(NbBundle.getMessage(CustomizerRun.class, "ACSN_CustRun"));
        getAccessibleContext().setAccessibleDescription(NbBundle.getMessage(CustomizerRun.class, "ACSD_CustRun"));
    }
    
    public void initValues(ProjectProperties props, String configuration) {
        this.vps = VisualPropertySupport.getDefault(props);
        String activePlatform = (String) props.get(VisualPropertySupport.translatePropertyName(configuration, "platform.active", false));//NOI18N
        if (activePlatform == null)
            activePlatform = (String) props.get("platform.active");//NOI18N
        String activeDevice = (String) props.get(VisualPropertySupport.translatePropertyName(configuration, "platform.device", false));//NOI18N
        if (activeDevice == null)
            activeDevice = (String) props.get("platform.device");//NOI18N
        final JavaPlatform[] platforms = JavaPlatformManager.getDefault().getPlatforms(null, new Specification(J2MEPlatform.SPECIFICATION_NAME, null));
        securitydomains = new String[0];
        if (activePlatform != null  &&  activeDevice != null  &&  platforms != null) for (int a = 0; a < platforms.length; a ++) {
            if (platforms[a] instanceof J2MEPlatform) {
                final J2MEPlatform platform = (J2MEPlatform) platforms[a];
                if (activePlatform.equals(platform.getDisplayName())) {
                    final J2MEPlatform.Device[] devices = platform.getDevices();
                    if (devices != null) for (int b = 0; b < devices.length; b ++) {
                        final J2MEPlatform.Device device = devices[b];
                        if (activeDevice.equals(device.getName())) {
                            if (device.getSecurityDomains() != null)
                                securitydomains = device.getSecurityDomains();
                            break;
                        }
                    }
                }
            }
        }
        vps.register(defaultCheck, configuration, this);
    }
    
    public String[] getGroupPropertyNames() {
        return PROPERTY_GROUP;
    }
    
    public void initGroupValues(final boolean useDefault) {
        vps.register(standardRadio, DefaultPropertiesDescriptor.RUN_METHOD, useDefault);
        vps.register(domainsCombo, securitydomains, DefaultPropertiesDescriptor.RUN_SECURITY_DOMAIN, useDefault);
        vps.register(OTARadio, DefaultPropertiesDescriptor.RUN_METHOD, useDefault);
        vps.register(jCheckBoxUseSecurity, DefaultPropertiesDescriptor.RUN_USE_SECURITY_DOMAIN, useDefault);
        vps.register(cmdOptionsText, DefaultPropertiesDescriptor.RUN_CMD_OPTIONS, useDefault);
        vps.register(debugTimeoutField, DefaultPropertiesDescriptor.DEBUG_TIMEOUT, useDefault);
        jLabel1.setEnabled(!useDefault);
        jLabel2.setEnabled(!useDefault);
        debugTimeoutLabel.setEnabled(!useDefault);
        actionPerformed(null);
    }
    
    public void actionPerformed(@SuppressWarnings("unused")
	final ActionEvent e) {
        domainsCombo.setEnabled(jCheckBoxUseSecurity.isEnabled() && jCheckBoxUseSecurity.isSelected());
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton OTARadio;
    private javax.swing.JTextField cmdOptionsText;
    private javax.swing.JTextField debugTimeoutField;
    private javax.swing.JLabel debugTimeoutLabel;
    private javax.swing.JCheckBox defaultCheck;
    private javax.swing.JComboBox domainsCombo;
    private javax.swing.JCheckBox jCheckBoxUseSecurity;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.ButtonGroup runMethodGroup;
    private javax.swing.JRadioButton standardRadio;
    // End of variables declaration//GEN-END:variables
    
}
