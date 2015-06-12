/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2015 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2015 Sun Microsystems, Inc.
 */
package org.netbeans.modules.java.hints.suggestions;

import org.netbeans.modules.java.hints.ui.InnerPanelSupport;
import org.netbeans.modules.java.hints.ui.ClassNameList;
import java.awt.Color;
import java.util.prefs.Preferences;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author sdedic
 */
public class ConstantNameOptions extends javax.swing.JPanel {
    private boolean suppressEvents;
    private Preferences prefs;
    private Color defaultBkColor;
    private Color errorBkColor = new Color(255, 204, 153);
    private ClassNameList nameList;
    /**
     * Creates new form ConstantNameOptions
     */
    public ConstantNameOptions(Preferences prefs) {
        initComponents();
        SpinnerNumberModel mdl1 = new SpinnerNumberModel(0, 0, 255, 1);
        SpinnerNumberModel mdl2 = new SpinnerNumberModel(0, 0, 255, 1);
        
        suppressEvents = true;
        
        minLength.setModel(mdl1);
        maxLength.setModel(mdl2);
        
        this.prefs = prefs;
        
        nameList = new ClassNameList();
        
        typesHolder.add(nameList);
        
        namePattern.setText(prefs.get(ConstantNameHint.PREF_CONSTANT_NAME_PATTERN, ConstantNameHint.DEFAULT_CONSTANT_NAME_PATTERN));
        minLength.setValue(prefs.getInt(ConstantNameHint.PREF_MIN_LENGTH, ConstantNameHint.DEFAULT_MIN_LENGTH));
        maxLength.setValue(prefs.getInt(ConstantNameHint.PREF_MAX_LENGTH, ConstantNameHint.DEFAULT_MAX_LENGTH));
        immutableCheck.setSelected(prefs.getBoolean(ConstantNameHint.PREF_CHECK_ONLY_IMMUTABLES, ConstantNameHint.DEFAULT_CHECK_ONLY_IMMUTABLES));
        String types = prefs.get(ConstantNameHint.PREF_IMMUTABLE_CLASSES, "");
        nameList.setClassNames(types);

        prefs.put(ConstantNameHint.PREF_CONSTANT_NAME_PATTERN, namePattern.getText());
        prefs.putInt(ConstantNameHint.PREF_MIN_LENGTH, (int)minLength.getValue());
        prefs.putInt(ConstantNameHint.PREF_MAX_LENGTH, (int)maxLength.getValue());
        prefs.putBoolean(ConstantNameHint.PREF_CHECK_ONLY_IMMUTABLES, immutableCheck.isSelected());
        prefs.put(ConstantNameHint.PREF_IMMUTABLE_CLASSES, types);
        
        suppressEvents = false;
        
        nameList.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                saveClassNames();
            }
        });
        namePattern.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                documentChanged();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentChanged();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
            
        });
        
        if (!immutableCheck.isSelected()) {
            InnerPanelSupport.enablePanel(typesHolder, false);
        }
    }
    
    private void saveClassNames() {
        if (suppressEvents) {
            return;
        }
        prefs.put(ConstantNameHint.PREF_IMMUTABLE_CLASSES, nameList.getClassNameList());
    }

    private void documentChanged() {
        if (suppressEvents) {
            return;
        }
        String pat = namePattern.getText().trim();
        if (!"".equals(pat)) {
            // check the pattern is OK
            try {
                Pattern p = Pattern.compile(pat);
            } catch (PatternSyntaxException ex) {
                namePattern.setBackground(errorBkColor);
                return;
            }
            namePattern.setBackground(defaultBkColor);
        }
        prefs.put(ConstantNameHint.PREF_CONSTANT_NAME_PATTERN, pat);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        immutableCheck = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        namePattern = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        minLength = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        maxLength = new javax.swing.JSpinner();
        typesHolder = new javax.swing.JPanel();

        org.openide.awt.Mnemonics.setLocalizedText(immutableCheck, org.openide.util.NbBundle.getMessage(ConstantNameOptions.class, "ConstantNameOptions.immutableCheck.text")); // NOI18N
        immutableCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                immutableCheckActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(ConstantNameOptions.class, "ConstantNameOptions.jLabel1.text")); // NOI18N

        namePattern.setText(org.openide.util.NbBundle.getMessage(ConstantNameOptions.class, "ConstantNameOptions.namePattern.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(ConstantNameOptions.class, "ConstantNameOptions.jLabel3.text")); // NOI18N

        minLength.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                minLengthStateChanged(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(ConstantNameOptions.class, "ConstantNameOptions.jLabel4.text")); // NOI18N

        maxLength.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                maxLengthStateChanged(evt);
            }
        });

        typesHolder.setLayout(new java.awt.GridLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(typesHolder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(namePattern)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(minLength, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel4)
                                .addGap(6, 6, 6)
                                .addComponent(maxLength, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(immutableCheck)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(namePattern, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(minLength, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(maxLength, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(immutableCheck)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(typesHolder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void immutableCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_immutableCheckActionPerformed
        boolean enable = immutableCheck.isSelected();
        prefs.putBoolean(ConstantNameHint.PREF_CHECK_ONLY_IMMUTABLES, enable);
        InnerPanelSupport.enablePanel(typesHolder, enable);
    }//GEN-LAST:event_immutableCheckActionPerformed

    private void minLengthStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_minLengthStateChanged
        if (suppressEvents) {
            return;
        }
        prefs.putInt(ConstantNameHint.PREF_MIN_LENGTH, (int)minLength.getValue());
    }//GEN-LAST:event_minLengthStateChanged

    private void maxLengthStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_maxLengthStateChanged
        if (suppressEvents) {
            return;
        }
        prefs.putInt(ConstantNameHint.PREF_MAX_LENGTH, (int)maxLength.getValue());
    }//GEN-LAST:event_maxLengthStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox immutableCheck;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JSpinner maxLength;
    private javax.swing.JSpinner minLength;
    private javax.swing.JTextField namePattern;
    private javax.swing.JPanel typesHolder;
    // End of variables declaration//GEN-END:variables
}
