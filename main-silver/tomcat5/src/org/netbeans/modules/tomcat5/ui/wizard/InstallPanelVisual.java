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

package org.netbeans.modules.tomcat5.ui.wizard;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.netbeans.modules.j2ee.deployment.plugins.api.InstanceProperties;
import org.netbeans.modules.tomcat5.TomcatFactory;
import org.netbeans.modules.tomcat5.deploy.TomcatManager.TomcatVersion;
import org.netbeans.modules.tomcat5.config.gen.Server;
import org.netbeans.modules.tomcat5.util.TomcatInstallUtil;
import org.netbeans.modules.tomcat5.util.TomcatProperties;
import org.netbeans.modules.tomcat5.util.TomcatUsers;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;


/**
 * Add Tomcat wizard panel.
 * 
 * @author Martin Grebac
 */
class InstallPanelVisual extends javax.swing.JPanel {
    
    private static final String SERVER_XML = "conf/server.xml"; // NOI18N
    
    private static final Logger LOGGER = Logger.getLogger(InstallPanelVisual.class.getName());

    private final List<ChangeListener> listeners = new CopyOnWriteArrayList<ChangeListener>();
    
    private String errorMessage;
    private boolean infoMessage;
    
    private String serverPort;
    private String shutdownPort;
    
    private RequestProcessor.Task validationTask;
    
    /* GuardedBy(this) */
    private String textHomeDir;

    /* GuardedBy(this) */
    private TomcatVersion version;
    
    /** Creates new form JPanel */
    public InstallPanelVisual() {
        initComponents();
        DocumentListener updateListener = new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                fireChange();
            }

            public void removeUpdate(DocumentEvent e) {
                fireChange();
            }

            public void insertUpdate(DocumentEvent e) {
                fireChange();
            }
        };
        jTextFieldHomeDir.getDocument().addDocumentListener(updateListener);
        jTextFieldBaseDir.getDocument().addDocumentListener(updateListener);
        jTextFieldUsername.getDocument().addDocumentListener(updateListener);
        jTextFieldPassword.getDocument().addDocumentListener(updateListener);
        createUserCheckBox.getModel().addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                fireChange();
            }
        });
        addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                // if JWSDP installed, disable the catalina base directory
                if (isJWSDP()) {
                    if (jCheckBoxShared.isEnabled()) {
                        jCheckBoxShared.setEnabled(false);
                        setBaseEnabled(false);
                    }
                } else {
                    if (!jCheckBoxShared.isEnabled()) {
                        jCheckBoxShared.setEnabled(true);
                        if (jCheckBoxShared.isSelected()) {
                            setBaseEnabled(true);
                        }
                    }
                }
            }
        });
    }
    
    public void addChangeListener(ChangeListener l) {
        listeners.add(l);
    }
    
    public void removeChangeListener(ChangeListener l) {
        listeners.remove(l);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelHomeDir = new javax.swing.JLabel();
        jLabelBaseDir = new javax.swing.JLabel();
        jTextFieldHomeDir = new javax.swing.JTextField();
        jTextFieldBaseDir = new javax.swing.JTextField();
        jButtonBaseBrowse = new javax.swing.JButton();
        jButtonHomeBrowse = new javax.swing.JButton();
        jCheckBoxShared = new javax.swing.JCheckBox();
        jLabelUsername = new javax.swing.JLabel();
        jLabelPassword = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldPassword = new javax.swing.JPasswordField();
        jTextFieldUsername = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        createUserCheckBox = new javax.swing.JCheckBox();

        setName(org.openide.util.NbBundle.getMessage(InstallPanelVisual.class, "LBL_InstallationAndLoginDetails")); // NOI18N

        jLabelHomeDir.setLabelFor(jTextFieldHomeDir);
        org.openide.awt.Mnemonics.setLocalizedText(jLabelHomeDir, org.openide.util.NbBundle.getMessage(InstallPanelVisual.class, "LBL_home_dir")); // NOI18N

        jLabelBaseDir.setLabelFor(jTextFieldBaseDir);
        org.openide.awt.Mnemonics.setLocalizedText(jLabelBaseDir, org.openide.util.NbBundle.getMessage(InstallPanelVisual.class, "LBL_base_dir")); // NOI18N
        jLabelBaseDir.setEnabled(false);

        jTextFieldHomeDir.setColumns(15);

        jTextFieldBaseDir.setColumns(15);
        jTextFieldBaseDir.setEnabled(false);

        org.openide.awt.Mnemonics.setLocalizedText(jButtonBaseBrowse, org.openide.util.NbBundle.getMessage(InstallPanelVisual.class, "LBL_file_chooser_base")); // NOI18N
        jButtonBaseBrowse.setEnabled(false);
        jButtonBaseBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBaseBrowseActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButtonHomeBrowse, org.openide.util.NbBundle.getMessage(InstallPanelVisual.class, "LBL_file_chooser_home")); // NOI18N
        jButtonHomeBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHomeBrowseActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBoxShared, org.openide.util.NbBundle.getMessage(InstallPanelVisual.class, "LBL_SharedInstall")); // NOI18N
        jCheckBoxShared.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxSharedActionPerformed(evt);
            }
        });

        jLabelUsername.setLabelFor(jTextFieldUsername);
        org.openide.awt.Mnemonics.setLocalizedText(jLabelUsername, org.openide.util.NbBundle.getMessage(InstallPanelVisual.class, "LBL_Username")); // NOI18N
        jLabelUsername.setToolTipText(org.openide.util.NbBundle.getMessage(InstallPanelVisual.class, "LBL_CreateUserToolTip")); // NOI18N

        jLabelPassword.setLabelFor(jTextFieldPassword);
        org.openide.awt.Mnemonics.setLocalizedText(jLabelPassword, org.openide.util.NbBundle.getMessage(InstallPanelVisual.class, "LBL_Password")); // NOI18N
        jLabelPassword.setToolTipText(org.openide.util.NbBundle.getMessage(InstallPanelVisual.class, "LBL_CreateUserToolTip")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(InstallPanelVisual.class, "MSG_TextAbove")); // NOI18N

        jTextFieldPassword.setColumns(20);

        jTextFieldUsername.setColumns(20);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(InstallPanelVisual.class, "LBL_Credentials")); // NOI18N
        jLabel2.setToolTipText(org.openide.util.NbBundle.getMessage(InstallPanelVisual.class, "LBL_CreateUserToolTip")); // NOI18N

        createUserCheckBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(createUserCheckBox, org.openide.util.NbBundle.getMessage(InstallPanelVisual.class, "LBL_CreateUser")); // NOI18N
        createUserCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(InstallPanelVisual.class, "LBL_CreateUserToolTip")); // NOI18N
        createUserCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        createUserCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1)
            .addComponent(jCheckBoxShared)
            .addComponent(jLabel2)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelUsername)
                    .addComponent(jLabelPassword)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabelBaseDir))
                    .addComponent(jLabelHomeDir))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(createUserCheckBox)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldBaseDir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
                            .addComponent(jTextFieldHomeDir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonHomeBrowse, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonBaseBrowse, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextFieldPassword, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                            .addComponent(jTextFieldUsername, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabelHomeDir))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonHomeBrowse)
                            .addComponent(jTextFieldHomeDir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(5, 5, 5)
                .addComponent(jCheckBoxShared)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabelBaseDir))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldBaseDir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonBaseBrowse))))
                .addGap(14, 14, 14)
                .addComponent(jLabel2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabelUsername))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jTextFieldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelPassword)
                    .addComponent(jTextFieldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(createUserCheckBox)
                .addContainerGap(51, Short.MAX_VALUE))
        );

        jLabelHomeDir.getAccessibleContext().setAccessibleName(NbBundle.getMessage(InstallPanelVisual.class, "A11Y_NAME_labelHomeDir"));
        jLabelHomeDir.getAccessibleContext().setAccessibleDescription(NbBundle.getMessage(InstallPanelVisual.class, "A11Y_DESC_labelHomeDir"));
        jLabelBaseDir.getAccessibleContext().setAccessibleName(NbBundle.getMessage(InstallPanelVisual.class, "A11Y_NAME_labelBaseDir"));
        jLabelBaseDir.getAccessibleContext().setAccessibleDescription(NbBundle.getMessage(InstallPanelVisual.class, "A11Y_DESC_labelBaseDir"));
        jTextFieldHomeDir.getAccessibleContext().setAccessibleName(NbBundle.getMessage(InstallPanelVisual.class, "A11Y_NAME_homeDir"));
        jTextFieldHomeDir.getAccessibleContext().setAccessibleDescription(NbBundle.getMessage(InstallPanelVisual.class, "A11Y_DESC_homeDir"));
        jTextFieldBaseDir.getAccessibleContext().setAccessibleName(NbBundle.getMessage(InstallPanelVisual.class, "A11Y_NAME_baseDir"));
        jTextFieldBaseDir.getAccessibleContext().setAccessibleDescription(NbBundle.getMessage(InstallPanelVisual.class, "A11Y_DESC_baseDir"));
        jButtonBaseBrowse.getAccessibleContext().setAccessibleName(NbBundle.getMessage(InstallPanelVisual.class, "A11Y_NAME_ButtonBaseBrowse"));
        jButtonBaseBrowse.getAccessibleContext().setAccessibleDescription(NbBundle.getMessage(InstallPanelVisual.class, "A11Y_DESC_ButtonBaseBrowse"));
        jButtonHomeBrowse.getAccessibleContext().setAccessibleName(NbBundle.getMessage(InstallPanelVisual.class, "A11Y_NAME_ButtonHomeBrowse"));
        jButtonHomeBrowse.getAccessibleContext().setAccessibleDescription(NbBundle.getMessage(InstallPanelVisual.class, "A11Y_DESC_ButtonHomeBrowse"));
        jCheckBoxShared.getAccessibleContext().setAccessibleName(NbBundle.getMessage(InstallPanelVisual.class, "A11Y_NAME_shared"));
        jCheckBoxShared.getAccessibleContext().setAccessibleDescription(NbBundle.getMessage(InstallPanelVisual.class, "A11Y_DESC_shared"));
        jLabelUsername.getAccessibleContext().setAccessibleName(NbBundle.getMessage(InstallPanelVisual.class, "A11Y_NAME_labelUsername"));
        jLabelUsername.getAccessibleContext().setAccessibleDescription(NbBundle.getMessage(InstallPanelVisual.class, "A11Y_DESC_labelUsername"));
        jLabelPassword.getAccessibleContext().setAccessibleName(NbBundle.getMessage(InstallPanelVisual.class, "A11Y_NAME_labelPassword"));
        jLabelPassword.getAccessibleContext().setAccessibleDescription(NbBundle.getMessage(InstallPanelVisual.class, "A11Y_DESC_labelPassword"));
        jTextFieldPassword.getAccessibleContext().setAccessibleName(NbBundle.getMessage(InstallPanelVisual.class, "A11Y_NAME_password"));
        jTextFieldPassword.getAccessibleContext().setAccessibleDescription(NbBundle.getMessage(InstallPanelVisual.class, "A11Y_DESC_password"));
        jTextFieldUsername.getAccessibleContext().setAccessibleName(NbBundle.getMessage(InstallPanelVisual.class, "A11Y_NAME_username"));
        jTextFieldUsername.getAccessibleContext().setAccessibleDescription(NbBundle.getMessage(InstallPanelVisual.class, "A11Y_DESC_username"));

        getAccessibleContext().setAccessibleName(NbBundle.getMessage(InstallPanelVisual.class, "A11Y_NAME_panel"));
        getAccessibleContext().setAccessibleDescription(NbBundle.getMessage(InstallPanelVisual.class, "A11Y_DESC_panel"));
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonBaseBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBaseBrowseActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setSelectedFile(new File(jTextFieldBaseDir.getText().trim()));
        if (chooser.showOpenDialog(SwingUtilities.getWindowAncestor(this)) == JFileChooser.APPROVE_OPTION) {
            jTextFieldBaseDir.setText(chooser.getSelectedFile().getAbsolutePath());
            fireChange();
        }
    }//GEN-LAST:event_jButtonBaseBrowseActionPerformed

    private void jButtonHomeBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHomeBrowseActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setSelectedFile(new File(jTextFieldHomeDir.getText().trim()));
        if (chooser.showOpenDialog(SwingUtilities.getWindowAncestor(this)) == JFileChooser.APPROVE_OPTION) {
            jTextFieldHomeDir.setText(chooser.getSelectedFile().getAbsolutePath());
            fireChange();
        }
    }//GEN-LAST:event_jButtonHomeBrowseActionPerformed

    private void jCheckBoxSharedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxSharedActionPerformed
        setBaseEnabled(jCheckBoxShared.isSelected());
        fireChange();
    }//GEN-LAST:event_jCheckBoxSharedActionPerformed
    
    public java.util.Properties getProperties() {
        Properties p = new Properties();
        p.put(TomcatProperties.PROP_SERVER_PORT, serverPort);
        p.put(TomcatProperties.PROP_SHUTDOWN,    shutdownPort);
        p.put(TomcatProperties.PROP_MONITOR,     "false"); // NOI18N
        return p;
    }
    
    public synchronized TomcatVersion getTomcatVersion() {
        return version;
    }
    
    public String getUrl() {
        String url;
        switch (getTomcatVersion()) {
            case TOMCAT_80:
                url = TomcatFactory.TOMCAT_URI_PREFIX_80;
                break;
            case TOMCAT_70:
                url = TomcatFactory.TOMCAT_URI_PREFIX_70;
                break;
            case TOMCAT_60:
                url = TomcatFactory.TOMCAT_URI_PREFIX_60;
                break;
            case TOMCAT_55:
                url = TomcatFactory.TOMCAT_URI_PREFIX_55;
                break;
            case TOMCAT_50:
            default:
                url = TomcatFactory.TOMCAT_URI_PREFIX_50;
                break;
        }
        
        url += "home=" + jTextFieldHomeDir.getText();       // NOI18N
        
        if (jCheckBoxShared.isEnabled() && jCheckBoxShared.isSelected()) {
            url += ":base=" + jTextFieldBaseDir.getText();  // NOI18N
        }
        Logger.getLogger(InstallPanelVisual.class.getName()).log(Level.FINE, "TomcatInstall.getUrl: " + url);    // NOI18N
        return url;
    }
    
    public String getUsername() {
        return jTextFieldUsername.getText();
    }
    
    public String getPassword() {
        return new String(jTextFieldPassword.getPassword());
    }

    boolean createUserEnabled() {
        return createUserCheckBox.isSelected();
    }
    
    public File getHomeDir() {
        return new File(jTextFieldHomeDir.getText());
    }
    
    /**
     * Returns catalina base folder if the base folder exists and is not empty, 
     * otherwise it returns catalina home folder.
     */
    private File getBaseDir() {
        if (jCheckBoxShared.isSelected()) {
            File base = new File(jTextFieldBaseDir.getText());
            if (base.isDirectory()) {
                File[] files = base.listFiles();
                if (files != null && files.length > 0) {
                    return base;
                }
            }
        }
        return new File(jTextFieldHomeDir.getText());
    }
    
    public String getErrorMessage() {
        // prevent the message from being cut off - wizard descriptor issue work-around
        return errorMessage == null 
                ? null
                : "<html>" + errorMessage.replaceAll("<",  "&lt;").replaceAll(">",  "&gt;") + "</html>"; // NIO18N
    }
    
    public boolean isInfoMessage() {
        return infoMessage;
    }
    
    boolean isServerXmlValid(File file) {
        try {
            Server server = Server.createGraph(file);
            serverPort = TomcatInstallUtil.getPort(server);
            shutdownPort = TomcatInstallUtil.getShutdownPort(server);
            if (serverPort != null && shutdownPort != null) {
                // test whether it's parseable
                Integer.parseInt(serverPort);
                Integer.parseInt(shutdownPort);
                return true;
            }
        } catch (IOException ioe) {
            Logger.getLogger(InstallPanelVisual.class.getName()).log(Level.INFO, null, ioe);
        } catch (NumberFormatException nfe) {
            Logger.getLogger(InstallPanelVisual.class.getName()).log(Level.INFO, null, nfe);
        } catch (RuntimeException e) {
            // catch any runtime exception that may occur during graph parsing
            Logger.getLogger(InstallPanelVisual.class.getName()).log(Level.INFO, null, e);
        }
        return false;
    }

    private boolean isHomeValid() {
        String homeDir = jTextFieldHomeDir.getText();
        if (homeDir.length() == 0) {
            errorMessage = NbBundle.getMessage(InstallPanelVisual.class, "MSG_SpecifyHomeDir");
            infoMessage = true;
            return false;
        }
        if (!new File(homeDir, "bin/bootstrap.jar").exists()) { // NOI18N
            errorMessage = NbBundle.getMessage(InstallPanelVisual.class, "MSG_InvalidHomeDir");
            return false;
        }

        // check the lib directory
        File libDir = TomcatVersion.TOMCAT_50.equals(getTomcatVersion())
                || TomcatVersion.TOMCAT_55.equals(getTomcatVersion())
            ? new File(homeDir, "common" + File.separator + "lib") // NOI18N
            : new File(homeDir, "lib"); // NOI18N
        if (!(libDir.exists() && libDir.isDirectory())) {
            errorMessage = NbBundle.getMessage(InstallPanelVisual.class, "MSG_InvalidHomeDir");
            return false;
        }

        // check whether server.xml is in configured BASE_DIR if so, we
        // don't want to checks its presence in HOME_DIR
        boolean serverXmlInBaseDir = false;
        if (jCheckBoxShared.isEnabled() && jCheckBoxShared.isSelected()) {
            String base = jTextFieldBaseDir.getText();
            if (base.length() != 0) {
                File serverFile = new File(base, SERVER_XML);
                if (serverFile.exists()) {
                    serverXmlInBaseDir = true;
                }
            }
        }

        File serverFile = new File(homeDir, SERVER_XML);
        if (!serverXmlInBaseDir && !serverFile.canRead()) {
            errorMessage = NbBundle.getMessage(InstallPanelVisual.class, "MSG_NonReadableHomeServerXml");
            return false;
        }
        if ((!jCheckBoxShared.isEnabled() || !jCheckBoxShared.isSelected()) && !isServerXmlValid(serverFile)) {
            errorMessage = NbBundle.getMessage(InstallPanelVisual.class, "MSG_CorruptedHomeServerXml");
            return false;
        }
        return true;
    }

    /** Is it Tomcat with the JWSDP installed? Does it contain the jwsdp-shared folder? */
    private boolean isJWSDP() {
        if (isHomeValid()) {
            File homeDir = getHomeDir();
            if (homeDir != null && homeDir.exists()) {
                File files[] = homeDir.listFiles(new FilenameFilter() {
                    public boolean accept(File dir, String name) {
                        if ("jwsdp-shared".equals(name)) { // NOI18N
                            return true;
                        }
                        return false;
                    }
                });
                return files.length != 0 ? true : false;
            }
        }
        return false;
    }
    
    private boolean isBaseValid() {        
        // catalina base
        if (jCheckBoxShared.isEnabled() && jCheckBoxShared.isSelected()) {
            String base = jTextFieldBaseDir.getText();
            if (base.length() == 0) {
                errorMessage = NbBundle.getMessage(InstallPanelVisual.class, "MSG_SpecifyBaseDir");
                infoMessage = true;
                return false;
            }
            File baseDir = new File(base);
            String[] files = baseDir.list();
            File serverXml = new File(baseDir, SERVER_XML);
            // is the base dir empty or does the server.xml file exists?
            if (!baseDir.exists() || files == null
                || (files.length > 0 &&  !serverXml.exists())) {
                errorMessage = NbBundle.getMessage(InstallPanelVisual.class, "MSG_InvalidBaseDir");
                return false;
            }
            if (files.length > 0) {
                if (!serverXml.canRead()) {
                    errorMessage = NbBundle.getMessage(InstallPanelVisual.class, "MSG_NonReadableBaseServerXml");
                    return false;
                }
                // check CATALINA_BASE/conf/server.xml, if base dir not empty
                if (!isServerXmlValid(serverXml)) {
                    errorMessage = NbBundle.getMessage(InstallPanelVisual.class, "MSG_CorruptedBaseServerXml");
                    return false;
                }
            } else {
                File serverFile = new File(jTextFieldHomeDir.getText(), SERVER_XML);
                if (!serverFile.canRead()) {
                    errorMessage = NbBundle.getMessage(InstallPanelVisual.class, "MSG_NonReadableHomeServerXml");
                    return false;                    
                }
                // otherwise check CATALINA_HOME/conf/server.xml which we will copy to base dir
                if (!isServerXmlValid(serverFile)) {
                    errorMessage = NbBundle.getMessage(InstallPanelVisual.class, "MSG_CorruptedHomeServerXml");
                    return false;
                }
            }
        }        
        return true;
    }

    private boolean isUsernamePasswordValid() {
        if (createUserCheckBox.isSelected()) {
            if (jTextFieldUsername.getText().length() == 0) {
                errorMessage = NbBundle.getMessage(InstallPanelVisual.class, "MSG_UsernameEmpty");
                infoMessage = true;
                return false;
            }
            if (jTextFieldPassword.getPassword().length == 0) {
                errorMessage = NbBundle.getMessage(InstallPanelVisual.class, "MSG_PasswordEmpty");
                infoMessage = true;
                return false;
            }
        } else {
            if (jTextFieldUsername.getText().length() == 0) {
                errorMessage = NbBundle.getMessage(InstallPanelVisual.class, "MSG_UsernameEmptyWarning");
                infoMessage = true;
            }
            File tomcatUsersXml = new File(getBaseDir(), "conf/tomcat-users.xml");
            try {
                if (!TomcatUsers.userExists(tomcatUsersXml, jTextFieldUsername.getText())) {
                    errorMessage = NbBundle.getMessage(InstallPanelVisual.class, "MSG_UserDoesNotExist");
                    infoMessage = true;
                } else if (!TomcatUsers.hasManagerRole(getTomcatVersion(), tomcatUsersXml, jTextFieldUsername.getText())) {
                    errorMessage = NbBundle.getMessage(InstallPanelVisual.class, "MSG_UserHasNotManagerRole",
                            TomcatVersion.TOMCAT_70.equals(getTomcatVersion()) || TomcatVersion.TOMCAT_80.equals(getTomcatVersion())
                                ? "manager-script"
                                : "manager");
                    infoMessage = true;
                }
            } catch (IOException e) {
                errorMessage = NbBundle.getMessage(InstallPanelVisual.class, "MSG_MissingOrInvalidTomcatUsersXml", tomcatUsersXml.getPath());
            }
        }
        return true;
    }
    
    private boolean isAlreadyRegistered() {
        if (InstanceProperties.getInstanceProperties(getUrl()) != null) {
            errorMessage = NbBundle.getMessage(InstallPanelVisual.class, 
                                jCheckBoxShared.isEnabled() && jCheckBoxShared.isSelected() 
                                    ? "MSG_AlreadyRegisteredBase" 
                                    : "MSG_AlreadyRegisteredHome");
            return true;
        }
        return false;
    }
    
    private void setBaseEnabled(boolean enabled) {
        jLabelBaseDir.setEnabled(enabled);
        jTextFieldBaseDir.setEnabled(enabled);
        jButtonBaseBrowse.setEnabled(enabled);
    }
    
    public boolean hasValidData() {
        errorMessage = null;
        infoMessage = false;
        return isHomeValid() && isBaseValid() && !isAlreadyRegistered() && isUsernamePasswordValid();
    }
    
    private void fireChange() {
        // schedule the validation task so that error messages won't flash e.g. 
        // when calling jTextFieldBaseDir.setText which triggers two consecutive 
        // events removeUpdate and insertUpdate. validation after the first one 
        // inevitably leads to a failure.
        synchronized (this) {
            textHomeDir = jTextFieldHomeDir.getText();
        }
        if (validationTask == null) {
            validationTask = RequestProcessor.getDefault().create(new Runnable() {
                @Override
                public void run() {
                    synchronized (InstallPanelVisual.this) {
                        version = TomcatFactory.getTomcatVersion(new File(textHomeDir));
                        LOGGER.log(Level.FINE, "Detected Tomcat version {0}", version);
                    }

                    SwingUtilities.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            ChangeEvent event = new ChangeEvent(this);
                            for (ChangeListener listener : listeners) {
                                listener.stateChanged(event);
                            }
                        }
                    });
                }
            });
        }
        validationTask.schedule(60);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox createUserCheckBox;
    private javax.swing.JButton jButtonBaseBrowse;
    private javax.swing.JButton jButtonHomeBrowse;
    private javax.swing.JCheckBox jCheckBoxShared;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelBaseDir;
    private javax.swing.JLabel jLabelHomeDir;
    private javax.swing.JLabel jLabelPassword;
    private javax.swing.JLabel jLabelUsername;
    private javax.swing.JTextField jTextFieldBaseDir;
    private javax.swing.JTextField jTextFieldHomeDir;
    private javax.swing.JPasswordField jTextFieldPassword;
    private javax.swing.JTextField jTextFieldUsername;
    // End of variables declaration//GEN-END:variables
    
}
