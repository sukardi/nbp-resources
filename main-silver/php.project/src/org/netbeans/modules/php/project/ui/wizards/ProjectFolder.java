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
 * Portions Copyrighted 2008 Sun Microsystems, Inc.
 */

package org.netbeans.modules.php.project.ui.wizards;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.netbeans.modules.php.project.ui.LastUsedFolders;
import org.netbeans.modules.php.project.ui.ProjectNameProvider;
import org.netbeans.modules.php.project.ui.SourcesFolderProvider;
import org.netbeans.modules.php.project.ui.Utils;
import org.openide.awt.Mnemonics;
import org.openide.filesystems.FileUtil;
import org.openide.util.ChangeSupport;
import org.openide.util.NbBundle;

/**
 * @author Tomas Mysik
 */
public class ProjectFolder extends JPanel implements ItemListener, DocumentListener, ChangeListener {
    private static final long serialVersionUID = 7976754658427748L;

    private final ChangeSupport changeSupport = new ChangeSupport(this);
    private final ProjectNameProvider projectNameProvider;
    private final SourcesFolderProvider sourcesFolderProvider;

    public ProjectFolder(ProjectNameProvider projectNameProvider, SourcesFolderProvider sourcesFolderProvider) {
        this.projectNameProvider = projectNameProvider;
        this.sourcesFolderProvider = sourcesFolderProvider;

        initComponents();

        init();
    }

    private void init() {
        projectFolderCheckBox.addItemListener(this);
        projectFolderTextField.getDocument().addDocumentListener(this);
        sourcesFolderProvider.addChangeListener(this);
    }

    void addProjectFolderListener(ChangeListener listener) {
        changeSupport.addChangeListener(listener);
    }

    void removeProjectFolderListener(ChangeListener listener) {
        changeSupport.removeChangeListener(listener);
    }

    boolean isProjectFolderUsed() {
        return projectFolderCheckBox.isSelected();
    }

    void setProjectFolderUsed(boolean used) {
        projectFolderCheckBox.setSelected(used);
        setState(used);
    }

    String getProjectFolder() {
        return projectFolderTextField.getText().trim();
    }

    void setProjectFolder(String projectFolder) {
        projectFolderTextField.setText(projectFolder);
    }

    void setState(boolean enabled) {
        projectFolderLabel.setEnabled(enabled);
        projectFolderTextField.setEnabled(enabled);
        projectFolderBrowseButton.setEnabled(enabled);
        // warning
        setWarning(enabled);
    }

    private void setWarning(boolean enabled) {
        boolean visible = enabled && isProjectDifferentFromSources();
        if (projectFolderScrollPane.isVisible() != visible) {
            projectFolderScrollPane.setVisible(visible);
            revalidate();
            repaint();
        }
    }

    // #169784
    private boolean isProjectDifferentFromSources() {
        File sources = FileUtil.normalizeFile(sourcesFolderProvider.getSourcesFolder());
        File project = FileUtil.normalizeFile(new File(getProjectFolder()));
        return !Utils.subdirectories(sources.getAbsolutePath(), project.getAbsolutePath());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        projectFolderCheckBox = new JCheckBox();
        projectFolderLabel = new JLabel();
        projectFolderTextField = new JTextField();
        projectFolderBrowseButton = new JButton();
        projectFolderScrollPane = new JScrollPane();
        projectFolderTextPane = new JTextPane();

        Mnemonics.setLocalizedText(projectFolderCheckBox, NbBundle.getMessage(ProjectFolder.class, "LBL_SeparateProjectFolder")); // NOI18N

        projectFolderLabel.setLabelFor(projectFolderTextField);
        Mnemonics.setLocalizedText(projectFolderLabel, NbBundle.getMessage(ProjectFolder.class, "LBL_MetadataFolder")); // NOI18N
        projectFolderLabel.setEnabled(false);

        projectFolderTextField.setColumns(20);
        projectFolderTextField.setEnabled(false);

        Mnemonics.setLocalizedText(projectFolderBrowseButton, NbBundle.getMessage(ProjectFolder.class, "LBL_BrowseProject")); // NOI18N
        projectFolderBrowseButton.setEnabled(false);
        projectFolderBrowseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                projectFolderBrowseButtonActionPerformed(evt);
            }
        });

        projectFolderScrollPane.setBorder(null);

        projectFolderTextPane.setBackground(UIManager.getDefaults().getColor("Label.background"));
        projectFolderTextPane.setBorder(null);
        projectFolderTextPane.setFont(new Font("Dialog", 1, 12)); // NOI18N
        projectFolderTextPane.setText(NbBundle.getMessage(ProjectFolder.class, "TXT_MetadataInfo")); // NOI18N
        projectFolderScrollPane.setViewportView(projectFolderTextPane);
        projectFolderTextPane.getAccessibleContext().setAccessibleName(NbBundle.getMessage(ProjectFolder.class, "ProjectFolder.projectFolderTextPane.AccessibleContext.accessibleName")); // NOI18N
        projectFolderTextPane.getAccessibleContext().setAccessibleDescription(NbBundle.getMessage(ProjectFolder.class, "ProjectFolder.projectFolderTextPane.AccessibleContext.accessibleDescription")); // NOI18N

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                    .addComponent(projectFolderCheckBox)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(projectFolderLabel)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(projectFolderTextField, GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)))
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(projectFolderBrowseButton))
            .addComponent(projectFolderScrollPane, GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(projectFolderCheckBox)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                    .addComponent(projectFolderTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(projectFolderLabel)
                    .addComponent(projectFolderBrowseButton))
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(projectFolderScrollPane))
        );

        projectFolderCheckBox.getAccessibleContext().setAccessibleName(NbBundle.getMessage(ProjectFolder.class, "ProjectFolder.projectFolderCheckBox.AccessibleContext.accessibleName")); // NOI18N
        projectFolderCheckBox.getAccessibleContext().setAccessibleDescription(NbBundle.getMessage(ProjectFolder.class, "ProjectFolder.projectFolderCheckBox.AccessibleContext.accessibleDescription")); // NOI18N
        projectFolderLabel.getAccessibleContext().setAccessibleName(NbBundle.getMessage(ProjectFolder.class, "ProjectFolder.projectFolderLabel.AccessibleContext.accessibleName")); // NOI18N
        projectFolderLabel.getAccessibleContext().setAccessibleDescription(NbBundle.getMessage(ProjectFolder.class, "ProjectFolder.projectFolderLabel.AccessibleContext.accessibleDescription")); // NOI18N
        projectFolderTextField.getAccessibleContext().setAccessibleName(NbBundle.getMessage(ProjectFolder.class, "ProjectFolder.projectFolderTextField.AccessibleContext.accessibleName")); // NOI18N
        projectFolderTextField.getAccessibleContext().setAccessibleDescription(NbBundle.getMessage(ProjectFolder.class, "ProjectFolder.projectFolderTextField.AccessibleContext.accessibleDescription")); // NOI18N
        projectFolderBrowseButton.getAccessibleContext().setAccessibleName(NbBundle.getMessage(ProjectFolder.class, "ProjectFolder.projectFolderBrowseButton.AccessibleContext.accessibleName")); // NOI18N
        projectFolderBrowseButton.getAccessibleContext().setAccessibleDescription(NbBundle.getMessage(ProjectFolder.class, "ProjectFolder.projectFolderBrowseButton.AccessibleContext.accessibleDescription")); // NOI18N
        projectFolderScrollPane.getAccessibleContext().setAccessibleName(NbBundle.getMessage(ProjectFolder.class, "ProjectFolder.projectFolderScrollPane1.AccessibleContext.accessibleName")); // NOI18N
        projectFolderScrollPane.getAccessibleContext().setAccessibleDescription(NbBundle.getMessage(ProjectFolder.class, "ProjectFolder.projectFolderScrollPane1.AccessibleContext.accessibleDescription")); // NOI18N

        getAccessibleContext().setAccessibleName(NbBundle.getMessage(ProjectFolder.class, "ProjectFolder.AccessibleContext.accessibleName")); // NOI18N
        getAccessibleContext().setAccessibleDescription(NbBundle.getMessage(ProjectFolder.class, "ProjectFolder.AccessibleContext.accessibleDescription")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents

    private void projectFolderBrowseButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_projectFolderBrowseButtonActionPerformed
        File newLocation = Utils.browseLocationAction(LastUsedFolders.NEW_PROJECT,
                NbBundle.getMessage(ProjectFolder.class, "LBL_SelectProjectFolder"));
        if (newLocation != null) {
            setProjectFolder(new File(newLocation, projectNameProvider.getProjectName()).getAbsolutePath());
        }
    }//GEN-LAST:event_projectFolderBrowseButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton projectFolderBrowseButton;
    private JCheckBox projectFolderCheckBox;
    private JLabel projectFolderLabel;
    private JScrollPane projectFolderScrollPane;
    private JTextField projectFolderTextField;
    private JTextPane projectFolderTextPane;
    // End of variables declaration//GEN-END:variables

    @Override
    public void itemStateChanged(ItemEvent e) {
        setState(e.getStateChange() == ItemEvent.SELECTED);
        changeSupport.fireChange();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        processUpdate();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        processUpdate();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        processUpdate();
    }

    private void processUpdate() {
        changeSupport.fireChange();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        setState(projectFolderCheckBox.isSelected());
    }
}
