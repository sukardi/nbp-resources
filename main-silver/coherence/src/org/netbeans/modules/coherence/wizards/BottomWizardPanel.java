/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2011 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2011 Sun Microsystems, Inc.
 */
package org.netbeans.modules.coherence.wizards;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.api.java.classpath.ClassPath;
import org.netbeans.api.java.project.JavaProjectConstants;
import org.netbeans.api.java.project.classpath.ProjectClassPathModifier;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.api.project.libraries.LibrariesCustomizer;
import org.netbeans.api.project.libraries.Library;
import org.netbeans.api.project.libraries.LibraryManager;
import org.netbeans.api.server.CommonServerUIs;
import org.netbeans.api.server.ServerInstance;
import org.netbeans.modules.coherence.library.LibraryUtils;
import org.netbeans.modules.coherence.project.CoherenceProjectUtils;
import org.netbeans.spi.project.ui.templates.support.Templates;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.util.Mutex;
import org.openide.util.NbBundle;
import org.openide.util.NbBundle.Messages;

/**
 *
 * @author Martin Fousek <marfous@netbeans.org>
 */
public class BottomWizardPanel extends javax.swing.JPanel {

    protected static final String SELECTED_COHERENCE = "selectedCoherenceLibrary"; //NOI18N

    @Messages("BottomWizardPanel.label.none.library=None")
    private static final String NONE_LIBRARY = Bundle.BottomWizardPanel_label_none_library();

    @Messages("BottomWizardPanel.label.new.coherence.library=New Cohrence Library ...")
    private static final String CREATE_NEW_LIBRARY = Bundle.BottomWizardPanel_label_new_coherence_library();

    private final WizardDescriptor wizard;

    /**
     * Creates new form BottomWizardPanel.
     */
    public BottomWizardPanel(WizardDescriptor wizard) {
        this.wizard = wizard;

        initComponents();
        initLibrariesPanel();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        coherenceInProjectLabel = new javax.swing.JLabel();
        librariesComboBox = new javax.swing.JComboBox();

        coherenceInProjectLabel.setText(org.openide.util.NbBundle.getMessage(BottomWizardPanel.class, "BottomWizardPanel.coherenceInProjectLabel.text")); // NOI18N

        librariesComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Loading libraries..." }));
        librariesComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                librariesComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(coherenceInProjectLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(librariesComboBox, 0, 166, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(coherenceInProjectLabel)
                    .addComponent(librariesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void librariesComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_librariesComboBoxActionPerformed
        Object selectedItem = librariesComboBox.getSelectedItem();
        if (selectedItem == CREATE_NEW_LIBRARY) {
            registerCoherence();
        }
    }//GEN-LAST:event_librariesComboBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel coherenceInProjectLabel;
    private javax.swing.JComboBox librariesComboBox;
    // End of variables declaration//GEN-END:variables

    @NbBundle.Messages(
        "title.register.coherence.dialog=Register New Coherence"
    )
    private void registerCoherence() {
        final ServerOrLibraryPanel panel = new ServerOrLibraryPanel();
        DialogDescriptor desc = new DialogDescriptor(
                panel,
                Bundle.title_register_coherence_dialog(),
                true,
                DialogDescriptor.DEFAULT_OPTION,
                DialogDescriptor.OK_OPTION,
                null);
        Object ret = DialogDisplayer.getDefault().notify(desc);
        if (ret == DialogDescriptor.OK_OPTION) {
            if (panel.getLibraryChecked()) {
                Library library = LibrariesCustomizer.showCreateNewLibraryCustomizer(LibraryManager.getDefault());
                if (library != null) {
                    initLibrariesPanel();
                }
            } else {
                ServerInstance serverInstance = CommonServerUIs.showAddServerInstanceWizard();
                if (serverInstance != null) {
                    initLibrariesPanel();
                }
            }
        } else {
            setSelectedLibrariesItem(NONE_LIBRARY);
        }
    }

    public Library getSelectedLibrary() {
        Object selectedItem = librariesComboBox.getSelectedItem();
        if (selectedItem instanceof LibraryItem) {
            return ((LibraryItem) selectedItem).getLibrary();
        }
        return null;
    }

    private void initLibrariesPanel() {
        librariesComboBox.removeAllItems();
        // add None option
        librariesComboBox.addItem(NONE_LIBRARY);
        // add all registered Coherence libraries
        for (Library library : LibraryUtils.getRegisteredCoherenceLibraries()) {
            librariesComboBox.addItem(new LibraryItem(library));
        }
        // add Create New Library option
        librariesComboBox.addItem(CREATE_NEW_LIBRARY);

        Project project = Templates.getProject(wizard);
        if (CoherenceProjectUtils.isCoherenceProject(project)) {
            Library includedLibrary = LibraryUtils.getCoherenceLibraryOnProjectClasspath(project);
            for (int i = 0; i < librariesComboBox.getItemCount(); i++) {
                Object item = librariesComboBox.getItemAt(i);
                if (item instanceof LibraryItem) {
                    if (((LibraryItem) item).getLibrary() == includedLibrary) {
                        setSelectedLibrariesItem(item);
                    }
                }
            }
            librariesComboBox.setEnabled(false);
        } else {
            setSelectedLibrariesItem(NONE_LIBRARY);
        }
    }

    private void setSelectedLibrariesItem(final Object item) {
        Mutex.EVENT.readAccess(new Runnable() {
            @Override
            public void run() {
                librariesComboBox.setSelectedItem(item);
            }
        });
    }

    private static class LibraryItem {

        private Library library;

        public LibraryItem(Library library) {
            this.library = library;
        }

        /**
         * Get the value of library
         *
         * @return the value of library
         */
        public Library getLibrary() {
            return library;
        }

        /**
         * Set the value of library
         *
         * @param library new value of library
         */
        public void setLibrary(Library library) {
            this.library = library;
        }

        @Override
        public String toString() {
            return library.getDisplayName();
        }
    }
}
