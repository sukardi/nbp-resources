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

package org.netbeans.modules.j2ee.archive.wizard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.Document;
import org.netbeans.modules.j2ee.archive.project.*;
import org.netbeans.modules.j2ee.archive.ui.JavaEePlatformUiSupport;
import org.netbeans.modules.j2ee.deployment.devmodules.api.ServerManager;
import org.netbeans.spi.project.ui.support.ProjectChooser;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.filesystems.FileUtil;
import org.openide.util.NbBundle;

class DeployablePanelVisual extends JPanel implements DocumentListener, ActionListener {
    
    private static final Logger log = Logger.getLogger(DeployablePanelVisual.class.getName());
    
    private transient DeployableWizardPanel panel;
    private static File savedArchiveLoc = null;
    
    /** Creates new form PanelProjectLocationVisual */
    public DeployablePanelVisual(DeployableWizardPanel panel) {
        initComponents();
        this.panel = panel;
        
        // Register listener on the textFields to make the automatic updates
        projectNameTextField.getDocument().addDocumentListener(this);
        projectLocationTextField.getDocument().addDocumentListener(this);
        archiveFileField.getDocument().addDocumentListener(this);
        serverInstanceComboBox.addActionListener(this);
    }
    
    
    public String getProjectName() {
        return this.projectNameTextField.getText();
    }
    
    private String validFreeProjectName(final File parentFolder, final String formater, final int index) {
        String name = MessageFormat.format(formater, new Object[]{(Integer) index});
        File file = new File(parentFolder, name);
        return file.exists() ? null : name;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        projectNameLabel = new javax.swing.JLabel();
        projectNameTextField = new javax.swing.JTextField();
        projectLocationLabel = new javax.swing.JLabel();
        projectLocationTextField = new javax.swing.JTextField();
        projectLocationButton = new javax.swing.JButton();
        createdFolderLabel = new javax.swing.JLabel();
        createdFolderTextField = new javax.swing.JTextField();
        archiveFileLabel = new javax.swing.JLabel();
        archiveFileField = new javax.swing.JTextField();
        archiveLocationButton = new javax.swing.JButton();
        targetServerLabel = new javax.swing.JLabel();
        serverInstanceComboBox = new javax.swing.JComboBox();
        addServerButton = new javax.swing.JButton();

        projectNameLabel.setLabelFor(projectNameTextField);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/netbeans/modules/j2ee/archive/wizard/Bundle"); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(projectNameLabel, bundle.getString("projectName_TEXT")); // NOI18N

        projectLocationLabel.setLabelFor(projectLocationTextField);
        org.openide.awt.Mnemonics.setLocalizedText(projectLocationLabel, bundle.getString("projectLocation_TEXT")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(projectLocationButton, bundle.getString("browse_TEXT")); // NOI18N
        projectLocationButton.setActionCommand(bundle.getString("BROWSE")); // NOI18N
        projectLocationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                projectLocationButtonActionPerformed(evt);
            }
        });

        createdFolderLabel.setLabelFor(createdFolderTextField);
        org.openide.awt.Mnemonics.setLocalizedText(createdFolderLabel, bundle.getString("projectFolder_TEXT")); // NOI18N

        createdFolderTextField.setEditable(false);

        org.openide.awt.Mnemonics.setLocalizedText(archiveFileLabel, bundle.getString("archiveFileLabel_TEXT")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(archiveLocationButton, bundle.getString("browse_TEXT")); // NOI18N
        archiveLocationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                archiveLocationButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(targetServerLabel, bundle.getString("targetServerLabel_TEXT")); // NOI18N

        serverInstanceComboBox.setModel(JavaEePlatformUiSupport.createPlatformComboBoxModel(null));
        serverInstanceComboBox.setPrototypeDisplayValue("The Gr8est Marvelous Nr. 1 Server");

        org.openide.awt.Mnemonics.setLocalizedText(addServerButton, bundle.getString("LBL_AddServer")); // NOI18N
        addServerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addServerButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(projectNameLabel)
                            .addComponent(projectLocationLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                            .addComponent(createdFolderLabel)
                            .addComponent(archiveFileLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(targetServerLabel)
                        .addGap(33, 33, 33)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(serverInstanceComboBox, 0, 196, Short.MAX_VALUE)
                    .addComponent(createdFolderTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                    .addComponent(projectNameTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                    .addComponent(projectLocationTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                    .addComponent(archiveFileField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(projectLocationButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(archiveLocationButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addServerButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(projectNameLabel)
                    .addComponent(projectNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(projectLocationLabel)
                    .addComponent(projectLocationTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(projectLocationButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(createdFolderLabel)
                    .addComponent(createdFolderTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(archiveFileLabel)
                    .addComponent(archiveFileField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(archiveLocationButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(targetServerLabel)
                    .addComponent(serverInstanceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addServerButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    private void addServerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addServerButtonActionPerformed
        Object serverInstanceWrapper =  serverInstanceComboBox.getSelectedItem();
        String selectedServerInstanceID = null;
        if (serverInstanceWrapper != null) {
            selectedServerInstanceID = JavaEePlatformUiSupport.getServerInstanceID(serverInstanceWrapper);
        }
        String newServerInstanceID = ServerManager.showAddServerInstanceWizard();
        if (newServerInstanceID != null) {
            selectedServerInstanceID = newServerInstanceID;
        }
        serverInstanceComboBox.setModel(JavaEePlatformUiSupport.createPlatformComboBoxModel(selectedServerInstanceID));
        panel.fireChangeEvent();
    }//GEN-LAST:event_addServerButtonActionPerformed
    
    private void archiveLocationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_archiveLocationButtonActionPerformed
        JFileChooser chooser = new JFileChooser();
        //FileUtil.preventFileChooserSymlinkTraversal(chooser, null);
        chooser.setCurrentDirectory(null);
        chooser.setDialogTitle(NbBundle.getMessage(DeployablePanelVisual.class,
                "DLG_TITLE_ArchiveChooser"));   // NOI18N
        chooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                String path = f.getAbsolutePath();
                return acceptableArchive(path);
            }
            @Override
            public String getDescription() {
                return NbBundle.getMessage(DeployablePanelVisual.class,
                        "ARCHIVE_CHOOSER_DESCRIPTOR");  //NOI18N
            }
        });
        String path = this.archiveFileField.getText();
        if (path.length() > 0) {
            File f = new File(path);
            if (f.exists()) {
                chooser.setSelectedFile(f);
            }
        } else if (savedArchiveLoc != null) {
            chooser.setCurrentDirectory(savedArchiveLoc);
        }
        if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(this)) {
            File projectDir = chooser.getSelectedFile();
            archiveFileField.setText(FileUtil.normalizeFile(projectDir).getAbsolutePath());
            savedArchiveLoc = projectDir.getParentFile();
        }
        panel.fireChangeEvent();
    }//GEN-LAST:event_archiveLocationButtonActionPerformed
    
    private void projectLocationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_projectLocationButtonActionPerformed
        JFileChooser chooser = new JFileChooser();
        //FileUtil.preventFileChooserSymlinkTraversal(chooser, null);
        chooser.setCurrentDirectory(null);
        chooser.setDialogTitle(NbBundle.getMessage(DeployablePanelVisual.class,
                "DLG_TITLE_FolderChooser"));    //NOI18N
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        String path = this.projectLocationTextField.getText();
        if (path.length() > 0) {
            File f = new File(path);
            if (f.exists()) {
                chooser.setSelectedFile(f);
            }
        }
        if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(this)) {
            File projectDir = chooser.getSelectedFile();
            projectLocationTextField.setText(FileUtil.normalizeFile(projectDir).getAbsolutePath());
        }
        panel.fireChangeEvent();
    }//GEN-LAST:event_projectLocationButtonActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addServerButton;
    private javax.swing.JTextField archiveFileField;
    private javax.swing.JLabel archiveFileLabel;
    private javax.swing.JButton archiveLocationButton;
    private javax.swing.JLabel createdFolderLabel;
    private javax.swing.JTextField createdFolderTextField;
    private javax.swing.JButton projectLocationButton;
    private javax.swing.JLabel projectLocationLabel;
    private javax.swing.JTextField projectLocationTextField;
    private javax.swing.JLabel projectNameLabel;
    private javax.swing.JTextField projectNameTextField;
    private javax.swing.JComboBox serverInstanceComboBox;
    private javax.swing.JLabel targetServerLabel;
    // End of variables declaration//GEN-END:variables
    
    @Override
    public void addNotify() {
        super.addNotify();
        //same problem as in 31086, initial focus on Cancel button
        projectNameTextField.requestFocus();
    }
    
    private final ResourceBundle bundle = NbBundle.getBundle(DeployablePanelVisual.class);
    
    boolean valid(WizardDescriptor wizardDescriptor) {
        if (projectNameTextField.getText().length() == 0) {
            setError(wizardDescriptor, "ERR_valid_folder_name");  //NOI18N
            return false; // Display name not specified
        }
        File f = FileUtil.normalizeFile(new File(projectLocationTextField.getText()).getAbsoluteFile());
        if (!f.isDirectory()) {
            setError(wizardDescriptor, "ERR_valid_path");  //NOI18N
            return false;
        }
        final File destFolder = FileUtil.normalizeFile(new File(createdFolderTextField.getText()).getAbsoluteFile());
        
        File projLoc = destFolder;
        while (projLoc != null && !projLoc.exists()) {
            projLoc = projLoc.getParentFile();
        }
        if (projLoc == null || !projLoc.canWrite()) {
            setError(wizardDescriptor, "ERR_cannot_write_folder");  //NOI18N
            return false;
        }
        
        if (FileUtil.toFileObject(projLoc) == null) {
            setError(wizardDescriptor, "ERR_valid_path");  //NOI18N
            return false;
        }
        
        File[] kids = destFolder.listFiles();
        if (destFolder.exists() && kids != null && kids.length > 0) {
            // Folder exists and is not empty
            setError(wizardDescriptor, "ERR_folder_not_empty");  //NOI18N
            return false;
        }
        
        String archName = archiveFileField.getText();
        if (archName.trim().length() < 1) {
            setInfo(wizardDescriptor, "ERR_empty_archive_name");  //NOI18N
            return false;
        }
        f = FileUtil.normalizeFile(new File(archName).getAbsoluteFile());
        if (!f.exists() || !f.isFile() || !f.canRead()) {
            setError(wizardDescriptor, "ERR_invalid_archive");  //NOI18N
            return false;
        }
        if (serverInstanceComboBox.getItemCount() == 0) {
            setError(wizardDescriptor, "ERR_no_possible_target");  //NOI18N
            return false;
        }
        if (serverInstanceComboBox.getSelectedItem() == null) {
            setError(wizardDescriptor, "ERR_no_target");  //NOI18N
            return false;
        }
        
        // this may take a while to compute...
        //
        String archiveName = f.getAbsoluteFile().getAbsolutePath();
        if (!acceptableArchive(archiveName, wizardDescriptor)) {
            setError(wizardDescriptor, "ERR_unsupported_archive");  //NOI18N
            return false;
        }
        
        if (ArchiveProjectProperties.PROJECT_TYPE_VALUE_UNKNOWN.equals(
                wizardDescriptor.getProperty(DeployableWizardIterator.PROJECT_TYPE_PROP)))
        {
            final File archiveFile = new File(archiveName);
            // do a bit of digging...
            boolean unknownType;
            try {
                JarFile jf = new JarFile(archiveFile);
                boolean isJar = DeployableWizardIterator.isEjbJar(jf);
                boolean isCar = DeployableWizardIterator.hasMain(jf);
                unknownType = !isJar && !isCar;
            }
            catch (IOException ex) {
                log.log(Level.WARNING, "Error during archive type discovery", ex);
                unknownType = true;
            }
            if (unknownType) {
                setError(wizardDescriptor, "ERR_Cannot_Deteremine_Type");  //NOI18N
                return false;
            }
        }
        
        wizardDescriptor.putProperty(WizardDescriptor.PROP_ERROR_MESSAGE, "");  // NOI18N
        return true;
    }
    
    private void setError(WizardDescriptor wizardDescriptor, String msgKey) {
        String message = bundle.getString(msgKey);
        wizardDescriptor.putProperty(WizardDescriptor.PROP_ERROR_MESSAGE, message);
    }

    private void setInfo(WizardDescriptor wizardDescriptor, String msgKey) {
        String message = bundle.getString(msgKey);
        wizardDescriptor.putProperty(WizardDescriptor.PROP_INFO_MESSAGE, message);
    }
    
    boolean acceptableArchive(String aPath, WizardDescriptor d) {
        // do the easy test first
        boolean retVal = false;
        if (aPath.endsWith(".war")) {    // NOI18N
            d.putProperty(DeployableWizardIterator.PROJECT_TYPE_PROP,
                    ArchiveProjectProperties.PROJECT_TYPE_VALUE_WAR);
            retVal = true;
        }
        if (aPath.endsWith(".car")) {    // NOI18N
            d.putProperty(DeployableWizardIterator.PROJECT_TYPE_PROP,
                    ArchiveProjectProperties.PROJECT_TYPE_VALUE_CAR);
            retVal = true;
        }
        if (aPath.endsWith(".rar")) {    // NOI18N
            d.putProperty(DeployableWizardIterator.PROJECT_TYPE_PROP,
                    ArchiveProjectProperties.PROJECT_TYPE_VALUE_RAR);
            retVal = true;
        }
        if (aPath.endsWith(".ear")) {    // NOI18N
            d.putProperty(DeployableWizardIterator.PROJECT_TYPE_PROP,
                    ArchiveProjectProperties.PROJECT_TYPE_VALUE_EAR);
            retVal = true;
        }
        if (aPath.endsWith(".jar")) {    // NOI18N
            d.putProperty(DeployableWizardIterator.PROJECT_TYPE_PROP,
                    ArchiveProjectProperties.PROJECT_TYPE_VALUE_UNKNOWN);
            retVal = true;
        }
        return retVal;
    }
    
    // if this is called on "next" we are golden here
    //
    void store(WizardDescriptor d) {
        String name = projectNameTextField.getText().trim();
        String folder = createdFolderTextField.getText().trim();
        
        d.putProperty(DeployableWizardIterator.PROJECT_DIR_PROP, new File(folder));
        d.putProperty(DeployableWizardIterator.PROJECT_NAME_PROP, name);
        d.putProperty(DeployableWizardIterator.PROJECT_TARGET_PROP, serverInstanceComboBox.getSelectedItem());
        d.putProperty(DeployableWizardIterator.PROJECT_ARCHIVE_PROP, new File(archiveFileField.getText().trim()));
    }
    
    void read(WizardDescriptor settings) {
        File projectLocation = (File) settings.getProperty(DeployableWizardIterator.PROJECT_DIR_PROP);
        if (projectLocation == null || projectLocation.getParentFile() == null || !projectLocation.getParentFile().isDirectory()) {
            projectLocation = ProjectChooser.getProjectsFolder();
        } else {
            projectLocation = projectLocation.getParentFile();
        }
        this.projectLocationTextField.setText(projectLocation.getAbsolutePath());
        
        String projectName = (String) settings.getProperty(DeployableWizardIterator.PROJECT_NAME_PROP);
        if(projectName == null) {
            int baseCount = 0;
            String formater = bundle.getString("TXT_DefaultProjectName");   // NOI18N
            while ((projectName=validFreeProjectName(projectLocation, formater, baseCount))==null)
                baseCount++;
        }
        this.projectNameTextField.setText(projectName);
        this.projectNameTextField.selectAll();
    }
    
    void validate(WizardDescriptor d) throws WizardValidationException {
        // nothing to validate
    }
    
    // Implementation of DocumentListener --------------------------------------
    
    @Override
    public void changedUpdate(DocumentEvent e) {
        updateTexts(e);
        if (this.projectNameTextField.getDocument() == e.getDocument()) {
            firePropertyChange(DeployableWizardIterator.PROJECT_NAME_PROP,null,this.projectNameTextField.getText());
        }
    }
    
    @Override
    public void insertUpdate(DocumentEvent e) {
        updateTexts(e);
        if (this.projectNameTextField.getDocument() == e.getDocument()) {
            firePropertyChange(DeployableWizardIterator.PROJECT_NAME_PROP,null,
                    this.projectNameTextField.getText());
        }
    }
    
    @Override
    public void removeUpdate(DocumentEvent e) {
        updateTexts(e);
        if (this.projectNameTextField.getDocument() == e.getDocument()) {
            firePropertyChange(DeployableWizardIterator.PROJECT_NAME_PROP,null,
                    this.projectNameTextField.getText());
        }
    }
    
    /** Handles changes in the Project name and project directory, */
    private void updateTexts(DocumentEvent e) {
        
        Document doc = e.getDocument();
        
        if (doc == projectNameTextField.getDocument() || doc == projectLocationTextField.getDocument()) {
            // Change in the project name
            
            String projectName = projectNameTextField.getText();
            String projectFolder = projectLocationTextField.getText();
            
            createdFolderTextField.setText(projectFolder + File.separatorChar + projectName);
        }
        panel.fireChangeEvent(); // Notify that the panel changed
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        panel.fireChangeEvent();
    }
    
    private boolean acceptableArchive(String aPath) {
        return (aPath.endsWith(".war") || aPath.endsWith(".car") ||  // NOI18N
            aPath.endsWith(".ear") || aPath.endsWith(".jar") ||  // NOI18N
            aPath.endsWith(".rar"));  // NOI18N
    }
}
