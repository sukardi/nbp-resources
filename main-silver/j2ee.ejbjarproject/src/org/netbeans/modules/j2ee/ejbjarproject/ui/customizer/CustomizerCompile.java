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
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
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

package org.netbeans.modules.j2ee.ejbjarproject.ui.customizer;

import javax.swing.JPanel;
import java.awt.Dialog;
import javax.swing.DefaultListModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.netbeans.modules.java.api.common.project.ui.ClassPathUiSupport;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;

import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;

/**
 *
 * @author  phrebejk
 */
public class CustomizerCompile extends JPanel implements HelpCtx.Provider {
    private static final long serialVersionUID = 236843764L;

        
    public CustomizerCompile( EjbJarProjectProperties uiProperties ) {
        initComponents();

        uiProperties.JAVAC_DEPRECATION_MODEL.setMnemonic( jCheckBoxDeprecation.getMnemonic() );
        jCheckBoxDeprecation.setModel( uiProperties.JAVAC_DEPRECATION_MODEL );
        uiProperties.JAVAC_DEBUG_MODEL.setMnemonic( jCheckBoxDebugInfo.getMnemonic() );
        jCheckBoxDebugInfo.setModel( uiProperties.JAVAC_DEBUG_MODEL );
        uiProperties.ENABLE_ANNOTATION_PROCESSING_MODEL.setMnemonic(enableAPTCheckBox.getMnemonic());
        enableAPTCheckBox.setModel(uiProperties.ENABLE_ANNOTATION_PROCESSING_MODEL);

        uiProperties.ENABLE_ANNOTATION_PROCESSING_IN_EDITOR_MODEL.setMnemonic(enableAPTEditorCheckBox.getMnemonic());
        enableAPTEditorCheckBox.setModel(uiProperties.ENABLE_ANNOTATION_PROCESSING_IN_EDITOR_MODEL);

        annotationProcessorsList.setModel(uiProperties.ANNOTATION_PROCESSORS_MODEL);
        enableAPTCheckBoxActionPerformed(null);
        additionalJavacParamsJTextField.setDocument( uiProperties.JAVAC_COMPILER_ARG_MODEL );                 
        
        jCheckBoxCompileOnSave.setModel(uiProperties.COMPILE_ON_SAVE_MODEL);
    }

    @Override
    public HelpCtx getHelpCtx() {
        return new HelpCtx( CustomizerCompile.class );
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBoxDebugInfo = new javax.swing.JCheckBox();
        jCheckBoxDeprecation = new javax.swing.JCheckBox();
        additionalJavacParamsJLabel = new javax.swing.JLabel();
        additionalJavacParamsJTextField = new javax.swing.JTextField();
        additionalJavacParamsExampleJLabel = new javax.swing.JLabel();
        enableAPTCheckBox = new javax.swing.JCheckBox();
        processorsPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        annotationProcessorsList = new javax.swing.JList();
        addProcessorButton = new javax.swing.JButton();
        removeProcessorButton = new javax.swing.JButton();
        annotationProcessorsLabel = new javax.swing.JLabel();
        enableAPTEditorCheckBox = new javax.swing.JCheckBox();
        jCheckBoxCompileOnSave = new javax.swing.JCheckBox();
        dosDescription = new javax.swing.JLabel();

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBoxDebugInfo, org.openide.util.NbBundle.getMessage(CustomizerCompile.class, "LBL_CustomizeCompile_Compiler_DebugInfo_JCheckBox")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBoxDeprecation, org.openide.util.NbBundle.getBundle(CustomizerCompile.class).getString("LBL_CustomizeCompile_Compiler_Deprecation_JCheckBox")); // NOI18N

        additionalJavacParamsJLabel.setDisplayedMnemonic(org.openide.util.NbBundle.getMessage (CustomizerCompile.class,"MNE_AdditionalCompilerOptions").charAt(0));
        additionalJavacParamsJLabel.setLabelFor(additionalJavacParamsJTextField);
        org.openide.awt.Mnemonics.setLocalizedText(additionalJavacParamsJLabel, org.openide.util.NbBundle.getMessage(CustomizerCompile.class, "LBL_AdditionalCompilerOptions")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(additionalJavacParamsExampleJLabel, org.openide.util.NbBundle.getMessage(CustomizerCompile.class, "LBL_AdditionalCompilerOptionsExample")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(enableAPTCheckBox, org.openide.util.NbBundle.getMessage(CustomizerCompile.class, "LBL_CustomizeCompile_Enable_Annotation_Processing")); // NOI18N
        enableAPTCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enableAPTCheckBoxActionPerformed(evt);
            }
        });

        processorsPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));

        jScrollPane1.setMaximumSize(new java.awt.Dimension(32767, 767));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(24, 154));

        annotationProcessorsList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        annotationProcessorsList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                annotationProcessorsListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(annotationProcessorsList);

        org.openide.awt.Mnemonics.setLocalizedText(addProcessorButton, org.openide.util.NbBundle.getMessage(CustomizerCompile.class, "LBL_CustomizeCompile_Add_Annotation_Processor")); // NOI18N
        addProcessorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProcessorButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(removeProcessorButton, org.openide.util.NbBundle.getMessage(CustomizerCompile.class, "LBL_CustomizeCompile_Remove_Annotation_Processors")); // NOI18N
        removeProcessorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeProcessorButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(annotationProcessorsLabel, org.openide.util.NbBundle.getMessage(CustomizerCompile.class, "LBL_CustomizeCompile_Annotation_Processors")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(enableAPTEditorCheckBox, org.openide.util.NbBundle.getMessage(CustomizerCompile.class, "LBL_CustomizeCompile_Enable_Editor_Annotation_Processing")); // NOI18N

        javax.swing.GroupLayout processorsPanelLayout = new javax.swing.GroupLayout(processorsPanel);
        processorsPanel.setLayout(processorsPanelLayout);
        processorsPanelLayout.setHorizontalGroup(
            processorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, processorsPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(processorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(addProcessorButton, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(removeProcessorButton)))
            .addGroup(processorsPanelLayout.createSequentialGroup()
                .addGroup(processorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(enableAPTEditorCheckBox)
                    .addComponent(annotationProcessorsLabel))
                .addContainerGap(141, Short.MAX_VALUE))
        );
        processorsPanelLayout.setVerticalGroup(
            processorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(processorsPanelLayout.createSequentialGroup()
                .addComponent(enableAPTEditorCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(annotationProcessorsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(processorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(processorsPanelLayout.createSequentialGroup()
                        .addComponent(addProcessorButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeProcessorButton)
                        .addContainerGap(107, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)))
        );

        org.openide.awt.Mnemonics.setLocalizedText(jCheckBoxCompileOnSave, org.openide.util.NbBundle.getMessage(CustomizerCompile.class, "LBL_CustomizeCompile_DeployOnSave_JCheckBox")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(dosDescription, org.openide.util.NbBundle.getMessage(CustomizerCompile.class, "LBL_CustomizeCompile_DeployOnSave_Description")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jCheckBoxCompileOnSave, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBoxDebugInfo, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBoxDeprecation, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(enableAPTCheckBox, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(processorsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(additionalJavacParamsJLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(additionalJavacParamsExampleJLabel)
                            .addComponent(additionalJavacParamsJTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(dosDescription, javax.swing.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jCheckBoxCompileOnSave)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dosDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBoxDebugInfo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBoxDeprecation)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(enableAPTCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(processorsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(additionalJavacParamsJLabel)
                    .addComponent(additionalJavacParamsJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(additionalJavacParamsExampleJLabel)
                .addContainerGap())
        );

        jCheckBoxDebugInfo.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(CustomizerCompile.class, "ACSD_CustomizerCompile_jCheckBoxDebugInfo")); // NOI18N
        jCheckBoxDeprecation.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(CustomizerCompile.class, "ACSD_CustomizerCompile_jCheckBoxDeprecation")); // NOI18N
        additionalJavacParamsJTextField.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage (CustomizerCompile.class,"AD_AdditionalCompilerOptions"));
    }// </editor-fold>//GEN-END:initComponents

    private void enableAPTCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enableAPTCheckBoxActionPerformed
        boolean b = enableAPTCheckBox.isSelected();
        enableAPTEditorCheckBox.setEnabled(b);
        annotationProcessorsLabel.setEnabled(b);
        annotationProcessorsList.setEnabled(b);
        addProcessorButton.setEnabled(b);
        int[] indices = annotationProcessorsList.getSelectedIndices();
        removeProcessorButton.setEnabled(b && indices != null && indices.length > 0);
}//GEN-LAST:event_enableAPTCheckBoxActionPerformed

    private void annotationProcessorsListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_annotationProcessorsListValueChanged
        int[] indices = annotationProcessorsList.getSelectedIndices();
        removeProcessorButton.setEnabled(enableAPTCheckBox.isSelected() && indices != null && indices.length > 0);
}//GEN-LAST:event_annotationProcessorsListValueChanged

    private void addProcessorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProcessorButtonActionPerformed
        final AddAnnotationProcessor panel = new AddAnnotationProcessor();
        final DialogDescriptor desc = new DialogDescriptor(panel, NbBundle.getMessage(CustomizerCompile.class, "LBL_AddAnnotationProcessor_Title")); //NOI18N
        desc.setValid(false);
        panel.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                String fqn = panel.getProcessorFQN();
                desc.setValid(fqn.length() > 0);
            }
        });
        Dialog dlg = DialogDisplayer.getDefault().createDialog(desc);
        dlg.setVisible(true);
        if (desc.getValue() == DialogDescriptor.OK_OPTION) {
            ((DefaultListModel)annotationProcessorsList.getModel()).addElement(panel.getProcessorFQN());
        }
        dlg.dispose();
}//GEN-LAST:event_addProcessorButtonActionPerformed

    private void removeProcessorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeProcessorButtonActionPerformed
        int[] newSelection = ClassPathUiSupport.remove((DefaultListModel) annotationProcessorsList.getModel(), annotationProcessorsList.getSelectedIndices());
        annotationProcessorsList.setSelectedIndices(newSelection);
}//GEN-LAST:event_removeProcessorButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addProcessorButton;
    private javax.swing.JLabel additionalJavacParamsExampleJLabel;
    private javax.swing.JLabel additionalJavacParamsJLabel;
    private javax.swing.JTextField additionalJavacParamsJTextField;
    private javax.swing.JLabel annotationProcessorsLabel;
    private javax.swing.JList annotationProcessorsList;
    private javax.swing.JLabel dosDescription;
    private javax.swing.JCheckBox enableAPTCheckBox;
    private javax.swing.JCheckBox enableAPTEditorCheckBox;
    private javax.swing.JCheckBox jCheckBoxCompileOnSave;
    private javax.swing.JCheckBox jCheckBoxDebugInfo;
    private javax.swing.JCheckBox jCheckBoxDeprecation;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel processorsPanel;
    private javax.swing.JButton removeProcessorButton;
    // End of variables declaration//GEN-END:variables



}
