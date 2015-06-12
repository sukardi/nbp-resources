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

package org.netbeans.modules.versioning.system.cvss.ui.wizards;

import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.text.PlainDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

/**
 * CVS Root field by field customizer UI.
 *
 * @author  Petr Kuzel
 */
public class CvsRootPanel extends javax.swing.JPanel {

    /** Creates new form CvsRootPanel */
    public CvsRootPanel() {
        initComponents();

        // some components are dynamically hidden, set constant width
        // to avoid user visible relayouting
        int w = accessLabel.getPreferredSize().width;
        w = Math.max(w, userLabel.getPreferredSize().width);
        w = Math.max(w, hostLabel.getPreferredSize().width);
        w = Math.max(w, repositoryLabel.getPreferredSize().width);
        
        setPreferredWidth(accessLabel, w);
        setPreferredWidth(userLabel, w);
        setPreferredWidth(hostLabel, w);
        setPreferredWidth(repositoryLabel, w);

        portTextField.setDocument(new PortDocument());
    }
    
    private void setPreferredWidth(JLabel label, int width) {
        Dimension dim = label.getPreferredSize();
        dim.width = width;
        label.setPreferredSize(dim);
    }

     static class PortDocument extends PlainDocument {

         public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

             if (str == null) return;

             char[] upper = str.toCharArray();
             for (int i = 0; i < upper.length; i++) {
                 if ("1234567890".indexOf(upper[i]) == -1) {  // NOI18N
                     return;
                 }
             }
             super.insertString(offs, new String(upper), a);
         }
     }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        setLayout(new java.awt.GridBagLayout());

        accessLabel.setLabelFor(accessComboBox);
        org.openide.awt.Mnemonics.setLocalizedText(accessLabel, org.openide.util.NbBundle.getMessage(CvsRootPanel.class, "BK1006"));
        accessLabel.setToolTipText(java.util.ResourceBundle.getBundle("org/netbeans/modules/versioning/system/cvss/ui/wizards/Bundle").getString("TT_AccessMethod"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 3);
        add(accessLabel, gridBagConstraints);

        accessComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "pserver", "ext", "local", "fork" }));
        accessComboBox.setMinimumSize(new java.awt.Dimension(120, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 3, 0);
        add(accessComboBox, gridBagConstraints);

        userLabel.setLabelFor(userTextField);
        org.openide.awt.Mnemonics.setLocalizedText(userLabel, org.openide.util.NbBundle.getMessage(CvsRootPanel.class, "BK1007"));
        userLabel.setToolTipText(java.util.ResourceBundle.getBundle("org/netbeans/modules/versioning/system/cvss/ui/wizards/Bundle").getString("TT_Username"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        add(userLabel, gridBagConstraints);

        userTextField.setColumns(12);
        userTextField.setMinimumSize(new java.awt.Dimension(120, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 10.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(userTextField, gridBagConstraints);

        hostLabel.setLabelFor(hostTextField);
        org.openide.awt.Mnemonics.setLocalizedText(hostLabel, org.openide.util.NbBundle.getMessage(CvsRootPanel.class, "BK1008"));
        hostLabel.setToolTipText(java.util.ResourceBundle.getBundle("org/netbeans/modules/versioning/system/cvss/ui/wizards/Bundle").getString("TT_CVSHost"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        add(hostLabel, gridBagConstraints);

        hostTextField.setColumns(30);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 20.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(hostTextField, gridBagConstraints);

        portLabel.setLabelFor(portTextField);
        org.openide.awt.Mnemonics.setLocalizedText(portLabel, org.openide.util.NbBundle.getMessage(CvsRootPanel.class, "BK1009"));
        portLabel.setToolTipText(java.util.ResourceBundle.getBundle("org/netbeans/modules/versioning/system/cvss/ui/wizards/Bundle").getString("TT_CVSPort"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(portLabel, gridBagConstraints);

        portTextField.setColumns(5);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 3.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 0);
        add(portTextField, gridBagConstraints);

        repositoryLabel.setLabelFor(repositoryTextField);
        org.openide.awt.Mnemonics.setLocalizedText(repositoryLabel, org.openide.util.NbBundle.getMessage(CvsRootPanel.class, "BK1005"));
        repositoryLabel.setToolTipText(java.util.ResourceBundle.getBundle("org/netbeans/modules/versioning/system/cvss/ui/wizards/Bundle").getString("TT_Repository"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 3);
        add(repositoryLabel, gridBagConstraints);

        repositoryTextField.setColumns(30);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 20.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 0, 3);
        add(repositoryTextField, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        add(jPanel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jPanel2, gridBagConstraints);

    }
    // </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    final javax.swing.JComboBox accessComboBox = new javax.swing.JComboBox();
    final javax.swing.JLabel accessLabel = new javax.swing.JLabel();
    final javax.swing.JLabel hostLabel = new javax.swing.JLabel();
    final javax.swing.JTextField hostTextField = new javax.swing.JTextField();
    final javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
    final javax.swing.JPanel jPanel2 = new javax.swing.JPanel();
    final javax.swing.JLabel portLabel = new javax.swing.JLabel();
    final javax.swing.JTextField portTextField = new javax.swing.JTextField();
    final javax.swing.JLabel repositoryLabel = new javax.swing.JLabel();
    final javax.swing.JTextField repositoryTextField = new javax.swing.JTextField();
    final javax.swing.JLabel userLabel = new javax.swing.JLabel();
    final javax.swing.JTextField userTextField = new javax.swing.JTextField();
    // End of variables declaration//GEN-END:variables
    
}
