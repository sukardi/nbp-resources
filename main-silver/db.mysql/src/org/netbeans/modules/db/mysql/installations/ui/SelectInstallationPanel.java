/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2013 Oracle and/or its affiliates. All rights reserved.
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
package org.netbeans.modules.db.mysql.installations.ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.ListSelectionModel;
import org.netbeans.modules.db.mysql.impl.Installation;
import org.netbeans.modules.db.mysql.impl.InstallationManager;
import org.netbeans.modules.db.mysql.impl.ServerNodeProvider;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;

/**
 *
 * @author jhavlin
 */
public class SelectInstallationPanel extends javax.swing.JPanel {

    private DialogDescriptor dialogDescriptor = null;

    /**
     * Creates new form SelectInstallationPanel
     */
    private SelectInstallationPanel() {
        initComponents();
    }

    @NbBundle.Messages({
        "MSG_Detecting_Wait=Detecting installations, please wait..."
    })
    private void initList() {
        installationList.setListData(new Object[]{
            Bundle.MSG_Detecting_Wait()
        });
        installationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        installationList.setEnabled(false);

        RequestProcessor.getDefault().post(new Runnable() {
            @Override
            public void run() {
                final List<Installation> installations =
                        InstallationManager.detectAllInstallations();
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        updateListForDetectedInstallations(installations);
                    }
                });
            }
        });
    }

    @NbBundle.Messages({
        "MSG_NoInstallationFound=Sorry, no installation was detected"
    })
    private void updateListForDetectedInstallations(List<Installation> insts) {
        if (insts.isEmpty()) {
            installationList.setListData(new Object[]{
                Bundle.MSG_NoInstallationFound()});
        } else {
            installationList.setListData(insts.toArray());
            installationList.setEnabled(true);
            dialogDescriptor.setValid(true);
        }
    }

    private void setDialogDescriptor(DialogDescriptor dialogDescriptor) {
        if (this.dialogDescriptor != null) {
            throw new IllegalStateException(
                    "DialogDescriptor has been already set."); //NOI18N
        }
        this.dialogDescriptor = dialogDescriptor;
        dialogDescriptor.setValid(false);
        initList();
    }

    @NbBundle.Messages({
        "selectInstalllation_title=Select MySQL Installation"
    })
    public static void showSelectInstallationDialog() {
        final SelectInstallationPanel sip = new SelectInstallationPanel();
        final DialogDescriptor dd = new DialogDescriptor(
                sip,
                Bundle.selectInstalllation_title(),
                true,
                DialogDescriptor.OK_CANCEL_OPTION,
                DialogDescriptor.OK_OPTION,
                null);
        sip.setDialogDescriptor(dd);
        DialogDisplayer.getDefault().createDialog(dd).setVisible(true);
        if (DialogDescriptor.OK_OPTION.equals(dd.getValue())) {
            Object val = sip.installationList.getSelectedValue();
            if (val instanceof Installation) {
                ServerNodeProvider.getDefault().registerInstallation(
                        (Installation) val);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        installationList = new javax.swing.JList();

        installationList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        scrollPane.setViewportView(installationList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList installationList;
    private javax.swing.JScrollPane scrollPane;
    // End of variables declaration//GEN-END:variables
}
