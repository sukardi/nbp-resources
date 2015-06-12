/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2012 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2013 Sun Microsystems, Inc.
 */
package org.netbeans.modules.cordova.project;

import java.awt.EventQueue;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.netbeans.modules.cordova.updatetask.CordovaPlugin;

/**
 * UI for selecting Cordova Plugins.
 */
public final class PluginsPanel extends JPanel {

    // folder path is accessed outside of EDT thread
    private volatile boolean panelEnabled = true;

    private PluginsListModel selectedPluginsModel;
    private PluginsListModel allPluginsModel;

    public PluginsPanel() {
        checkUiThread();
        initComponents();
    }
   
    public void init(List<CordovaPlugin> current, List<CordovaPlugin> all) {
        checkUiThread();
        initComponents();
        pluginsFilterTextField.setVisible(false);
        selectedPluginsModel = new PluginsListModel(current);
        selectedPluginsModel.sortLibraries();
        all.removeAll(current); //remove already added plugins from available list
        allPluginsModel = new PluginsListModel(all);
        allPluginsModel.sortLibraries();
        initPlugins();
    }

    /**
     * Get the list of selected Plugins.
     * @return list of selected Plugins
     */
    public List<CordovaPlugin> getSelectedPlugins() {
        return selectedPluginsModel.plugins;
    }


    /**
     * Lock this panel, it means no user changes can be done.
     * <p>
     * This method must be run in the UI thread.
     * @see #unlockPanel()
     */
    public void lockPanel() {
        checkUiThread();
        enablePanel(false);
    }

    /**
     * Unlock this panel, it means no user changes can be done.
     * <p>
     * This method must be run in the UI thread.
     * @see #lockPanel()
     */
    public void unlockPanel() {
        checkUiThread();
        enablePanel(true);
    }


    private void checkUiThread() {
        if (!EventQueue.isDispatchThread()) {
            throw new IllegalStateException("Must be run in UI thread");
        }
    }

    private void initPlugins() {
        initAllPluginsList();
        initSelectedPluginsList();
        updateButtonsEnabled();
    }
    

    private void initAllPluginsList() {
        assert EventQueue.isDispatchThread();
        pluginsList.setModel(allPluginsModel);
        pluginsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        pluginsFilterTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                processChange();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                processChange();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                processChange();
            }
            private void processChange() {
                //filterLibrariesTable();
            }
        });
    }

    private void initSelectedPluginsList() {
        assert EventQueue.isDispatchThread();
        selectedPluginsList.setModel(selectedPluginsModel);
        selectedPluginsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private boolean isUpdateRunning() {
        return !panelEnabled;
    }

    private void startUpdate() {
        lockPanel();
    }

    void finishUpdate() {
        unlockPanel();
    }

    private void enablePanel(boolean enabled) {
        assert EventQueue.isDispatchThread();
        panelEnabled = enabled;
        pluginsFilterTextField.setEnabled(enabled);
        pluginsList.setEnabled(enabled);
        selectSelectedButton.setEnabled(enabled);
        deselectSelectedButton.setEnabled(enabled);
        selectedPluginsList.setEnabled(enabled);
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        generalInfoLabel = new javax.swing.JLabel();
        librariesLabel = new javax.swing.JLabel();
        pluginsFilterTextField = new javax.swing.JTextField();
        selectSelectedButton = new javax.swing.JButton();
        deselectSelectedButton = new javax.swing.JButton();
        selectedLabel = new javax.swing.JLabel();
        selectedLibrariesScrollPane = new javax.swing.JScrollPane();
        selectedPluginsList = new javax.swing.JList();
        pluginsScrollPane = new javax.swing.JScrollPane();
        pluginsList = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();

        org.openide.awt.Mnemonics.setLocalizedText(generalInfoLabel, org.openide.util.NbBundle.getMessage(PluginsPanel.class, "PluginsPanel.generalInfoLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(librariesLabel, org.openide.util.NbBundle.getMessage(PluginsPanel.class, "PluginsPanel.librariesLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(selectSelectedButton, org.openide.util.NbBundle.getMessage(PluginsPanel.class, "PluginsPanel.selectSelectedButton.text")); // NOI18N
        selectSelectedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectSelectedButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(deselectSelectedButton, org.openide.util.NbBundle.getMessage(PluginsPanel.class, "PluginsPanel.deselectSelectedButton.text")); // NOI18N
        deselectSelectedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deselectSelectedButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(selectedLabel, org.openide.util.NbBundle.getMessage(PluginsPanel.class, "PluginsPanel.selectedLabel.text")); // NOI18N

        selectedPluginsList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                selectedPluginsListValueChanged(evt);
            }
        });
        selectedLibrariesScrollPane.setViewportView(selectedPluginsList);

        pluginsList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                pluginsListValueChanged(evt);
            }
        });
        pluginsScrollPane.setViewportView(pluginsList);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(PluginsPanel.class, "PluginsPanel.jLabel1.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(generalInfoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 591, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(librariesLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pluginsFilterTextField))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(pluginsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(selectSelectedButton)
                            .addComponent(deselectSelectedButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(selectedLabel)
                            .addComponent(selectedLibrariesScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {deselectSelectedButton, selectSelectedButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(generalInfoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(librariesLabel)
                    .addComponent(selectedLabel)
                    .addComponent(pluginsFilterTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(selectedLibrariesScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(selectSelectedButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deselectSelectedButton)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(pluginsScrollPane))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void selectSelectedButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectSelectedButtonActionPerformed
        selectedPluginsModel.add(pluginsList.getSelectedValuesList());
        allPluginsModel.remove(pluginsList.getSelectedValuesList());
        updateButtonsEnabled();
    }//GEN-LAST:event_selectSelectedButtonActionPerformed

    private void deselectSelectedButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deselectSelectedButtonActionPerformed
        allPluginsModel.add(selectedPluginsList.getSelectedValuesList());
        selectedPluginsModel.remove(selectedPluginsList.getSelectedValuesList());
        updateButtonsEnabled();
    }//GEN-LAST:event_deselectSelectedButtonActionPerformed

    private void pluginsListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_pluginsListValueChanged
        updateButtonsEnabled();
    }//GEN-LAST:event_pluginsListValueChanged

    private void selectedPluginsListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_selectedPluginsListValueChanged
        updateButtonsEnabled();
    }//GEN-LAST:event_selectedPluginsListValueChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton deselectSelectedButton;
    private javax.swing.JLabel generalInfoLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel librariesLabel;
    private javax.swing.JTextField pluginsFilterTextField;
    private javax.swing.JList pluginsList;
    private javax.swing.JScrollPane pluginsScrollPane;
    private javax.swing.JButton selectSelectedButton;
    private javax.swing.JLabel selectedLabel;
    private javax.swing.JScrollPane selectedLibrariesScrollPane;
    private javax.swing.JList selectedPluginsList;
    // End of variables declaration//GEN-END:variables

    private void updateButtonsEnabled() {
        selectSelectedButton.setEnabled(allPluginsModel.getSize() > 0 && pluginsList.getSelectedIndex() != -1 && pluginsList.getSelectedIndex() < allPluginsModel.getSize());
        deselectSelectedButton.setEnabled(selectedPluginsModel.getSize() > 0 && selectedPluginsList.getSelectedIndex() != -1 && selectedPluginsList.getSelectedIndex() < selectedPluginsModel.getSize());
    }

    private static final class PluginsListModel extends AbstractListModel {

        private static final Comparator<CordovaPlugin> SELECTED_PLUGINS_COMPARATOR = new Comparator<CordovaPlugin>() {
            @Override
            public int compare(CordovaPlugin left, CordovaPlugin right) {
                return left.getName().compareToIgnoreCase(right.getName());
            }
        };

        private final List<CordovaPlugin> plugins;


        public PluginsListModel(List<CordovaPlugin> plugins) {
            this.plugins = plugins;
        }

        @Override
        public int getSize() {
            return plugins.size();
        }

        @Override
        public CordovaPlugin getElementAt(int index) {
            return plugins.get(index);
        }

        public void fireContentsChanged() {
            sortLibraries();
            fireContentsChanged(this, 0, plugins.size() - 1);
        }
        
        public void add(List<CordovaPlugin> add) {
            for (CordovaPlugin p:add) {
                if (!plugins.contains(p)) {
                    plugins.add(p);
                }
            }
            sortLibraries();
            fireContentsChanged();
        }
        
        public void remove(List<CordovaPlugin> rem) {
            plugins.removeAll(rem);
            sortLibraries();
            fireContentsChanged();
        }

        /**
         * Make selected plugins unique and sort them.
         */
        private void sortLibraries() {
            Collections.sort(plugins, SELECTED_PLUGINS_COMPARATOR);
        }

    }
}
