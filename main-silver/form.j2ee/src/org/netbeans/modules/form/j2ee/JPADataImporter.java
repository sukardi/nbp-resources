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
package org.netbeans.modules.form.j2ee;

import java.awt.Dialog;
import java.awt.EventQueue;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import org.netbeans.api.db.explorer.ConnectionManager;
import org.netbeans.api.db.explorer.DatabaseConnection;
import org.netbeans.api.db.explorer.JDBCDriver;
import org.netbeans.api.db.explorer.JDBCDriverManager;
import org.netbeans.api.db.explorer.support.DatabaseExplorerUIs;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.modules.form.DataImporter;
import org.netbeans.modules.form.FormDataObject;
import org.netbeans.modules.form.FormEditor;
import org.netbeans.modules.form.FormJavaSource;
import org.netbeans.modules.form.FormModel;
import org.netbeans.modules.form.RADComponent;
import org.netbeans.modules.j2ee.metadata.model.api.MetadataModel;
import org.netbeans.modules.j2ee.persistence.api.PersistenceScope;
import org.netbeans.modules.j2ee.persistence.api.metadata.orm.EntityMappingsMetadata;
import org.netbeans.modules.j2ee.persistence.dd.common.PersistenceUnit;
import org.netbeans.modules.j2ee.persistence.provider.InvalidPersistenceXmlException;
import org.netbeans.modules.j2ee.persistence.spi.PersistenceLocationProvider;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;

/**
 * Importer of list of JPA entities.
 *
 * @author Jan Stola
 */
@org.openide.util.lookup.ServiceProvider(service=org.netbeans.modules.form.DataImporter.class)
public class JPADataImporter extends JPanel implements DataImporter {

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        importerLabel = new javax.swing.JLabel();
        connectionLabel = new javax.swing.JLabel();
        tableLabel = new javax.swing.JLabel();
        tableCombo = new javax.swing.JComboBox();
        connectionCombo = new javax.swing.JComboBox();

        importerLabel.setText(org.openide.util.NbBundle.getMessage(JPADataImporter.class, "MSG_ImportData")); // NOI18N

        connectionLabel.setText(org.openide.util.NbBundle.getMessage(JPADataImporter.class, "LBL_ImportDBConnection")); // NOI18N

        tableLabel.setText(org.openide.util.NbBundle.getMessage(JPADataImporter.class, "LBL_ImportDBTable")); // NOI18N

        tableCombo.setEnabled(false);
        tableCombo.setRenderer(J2EEUtils.DBColumnInfo.getRenderer());

        connectionCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectionComboActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(importerLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(connectionLabel)
                            .addComponent(tableLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tableCombo, 0, 357, Short.MAX_VALUE)
                            .addComponent(connectionCombo, 0, 357, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(importerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(connectionLabel)
                    .addComponent(connectionCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tableLabel)
                    .addComponent(tableCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

private void connectionComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectionComboActionPerformed
    Object selItem = connectionCombo.getSelectedItem();
    if (selItem instanceof DatabaseConnection) {
        DatabaseConnection connection = (DatabaseConnection)selItem;
        Connection con = J2EEUtils.establishConnection(connection);
        if (con == null) return; // User canceled the connection dialog
        fillTableCombo(connection);
    } else {
        assert (selItem == null);
    }
}//GEN-LAST:event_connectionComboActionPerformed

    /**
     * Fills the content of <code>tableCombo</code>.
     *
     * @param connection database connection.
     */
    private void fillTableCombo(DatabaseConnection connection) {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for (J2EEUtils.DBColumnInfo table : J2EEUtils.tableNamesForConnection(connection)) {
            model.addElement(table);
        }
        tableCombo.setModel(model);
        tableCombo.setEnabled(tableCombo.getModel().getSize() != 0);
        tableCombo.setSelectedItem(tableCombo.getSelectedItem());
    }

    @Override
    public boolean canImportData(FormModel form) {
        return J2EEUtils.supportsJPA(form);
    }

    /**
     * Imports list of JPA entities that correspond to selected DB table.
     * 
     * @param formModel form to import the data into.
     * @return the component encapsulating the imported data.
     */
    @Override
    public Future<RADComponent> importData(final FormModel formModel) {
        removeAll();
        if (FormJavaSource.isInDefaultPackage(formModel)) {
            // 97982: default package
            DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(
                    NbBundle.getMessage(getClass(), "MSG_ImportToDefaultPackage"))); // NOI18N
            return null;
        }
        initComponents();
        DatabaseExplorerUIs.connect(connectionCombo, ConnectionManager.getDefault());
        DialogDescriptor dd = new DialogDescriptor(
                this,
                NbBundle.getMessage(getClass(), "TITLE_ImportData"), // NOI18N
                true,
                null);
        Dialog dialog = DialogDisplayer.getDefault().createDialog(dd);
        dialog.setVisible(true);
        if (dd.getValue() != DialogDescriptor.OK_OPTION) return null;
        FutureTask<RADComponent> task = new FutureTask<RADComponent>(new Callable<RADComponent>() {
            @Override
            public RADComponent call() {
                final RADComponent[] resultList = new RADComponent[1];
                try {
                    J2EEUtils.DBColumnInfo table = (J2EEUtils.DBColumnInfo)tableCombo.getSelectedItem();
                    if ((table == null) || !table.isValid()) return null;
                    String tableName = table.getName();
                    DatabaseConnection connection = (DatabaseConnection)connectionCombo.getSelectedItem();
                    FileObject formFile = FormEditor.getFormDataObject(formModel).getFormFile();
                    Project project = FileOwnerQuery.getOwner(formFile);

                    // Make sure persistence.xml file exists
                    FileObject persistenceXML = J2EEUtils.getPersistenceXML(project, true);

                    // Initializes persistence unit and persistence descriptor
                    PersistenceUnit unit = J2EEUtils.initPersistenceUnit(persistenceXML, connection);

                    // Initializes project's classpath
                    JDBCDriver[] driver = JDBCDriverManager.getDefault().getDrivers(connection.getDriverClass());
                    J2EEUtils.updateProjectForUnit(formFile, unit, driver[0]);

                    // Obtain description of entity mappings
                    PersistenceScope scope = PersistenceScope.getPersistenceScope(formFile);
                    MetadataModel<EntityMappingsMetadata> mappings = scope.getEntityMappingsModel(unit.getName());

                    // Find entity that corresponds to the dragged table
                    String[] entityInfo = J2EEUtils.findEntity(mappings, tableName);

                    // Create a new entity (if there isn't one that corresponds to the dragged table)
                    if (entityInfo == null) {
                        // Generates a Java class for the entity
                        J2EEUtils.createEntity(formFile.getParent(), scope, unit, connection, tableName, null);

                        mappings = scope.getEntityMappingsModel(unit.getName());
                        entityInfo = J2EEUtils.findEntity(mappings, tableName);
                    } else {
                        // Add the entity into the persistence unit if it is not there already
                        J2EEUtils.addEntityToUnit(entityInfo[1], unit, project);
                    }

                    J2EEUtils.makeEntityObservable(formFile, entityInfo, mappings);

                    final String puName = unit.getName();
                    final String[] info = entityInfo;
                    EventQueue.invokeAndWait(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                RADComponent entityManager = J2EEUtils.findEntityManager(formModel, puName);
                                if (entityManager == null) {
                                    entityManager = J2EEUtils.createEntityManager(formModel, puName);
                                }
                                RADComponent queryBean = DBTableDrop.createQueryBean(formModel, entityManager, info[0]);
                                resultList[0] = DBTableDrop.createResultListBean(formModel, queryBean, info);
                            } catch (Exception ex) {
                                Logger.getLogger(getClass().getName()).log(Level.INFO, ex.getMessage(), ex);
                            }
                        }
                    });
                } catch (IOException ioex) {
                    Logger.getLogger(JPADataImporter.class.getName()).log(Level.INFO, null, ioex);
                } catch (InvalidPersistenceXmlException ipxex) {
                    Logger.getLogger(JPADataImporter.class.getName()).log(Level.INFO, null, ipxex);
                } catch (InterruptedException iex) {
                    Logger.getLogger(JPADataImporter.class.getName()).log(Level.INFO, null, iex);
                } catch (InvocationTargetException itex) {
                    Logger.getLogger(JPADataImporter.class.getName()).log(Level.INFO, null, itex);
                }
                return resultList[0];
            }
        });
        RequestProcessor.getDefault().post(task);
        return task;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox connectionCombo;
    private javax.swing.JLabel connectionLabel;
    private javax.swing.JLabel importerLabel;
    private javax.swing.JComboBox tableCombo;
    private javax.swing.JLabel tableLabel;
    // End of variables declaration//GEN-END:variables
    
}
