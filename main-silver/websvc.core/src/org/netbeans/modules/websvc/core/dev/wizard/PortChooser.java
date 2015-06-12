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

package org.netbeans.modules.websvc.core.dev.wizard;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.border.EtchedBorder;
import org.netbeans.modules.websvc.core.dev.wizard.nodes.PortNode;
import org.openide.explorer.view.BeanTreeView;
import org.openide.nodes.Node;
import org.openide.nodes.NodeAcceptor;
import org.openide.util.NbBundle;

public class PortChooser extends javax.swing.JPanel {
    
    public static final String IS_VALID = "portChooser_isValid"; //NOI18N
    
    private NodeAcceptor nodeAcceptor;
    private NodeDisplayPanel nodeDisplayPanel;
    
    /** Creates new form PortChooser */
    public PortChooser(Node root) {
        initComponents();

        this.nodeAcceptor = new NodeAcceptorImpl();

        nodeDisplayPanel = new NodeDisplayPanel(root);
        nodeDisplayPanel.setBorder(new EtchedBorder());
        jPanelBeanTree.add(nodeDisplayPanel);
        nodeDisplayPanel.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent pce) {
                validateNodes();
            }
        });
        
        BeanTreeView btw = (BeanTreeView)nodeDisplayPanel.getComponent(0);
        jLabelDesc.setLabelFor(btw.getViewport().getView());
        
        validateNodes();
    }
    
    private void validateNodes() {
        boolean nodeAccepted = nodeAcceptor.acceptNodes(nodeDisplayPanel.getSelectedNodes());
        if (!nodeAccepted)
            firePropertyChange(IS_VALID, Boolean.TRUE, Boolean.FALSE);
        else
            firePropertyChange(IS_VALID, Boolean.FALSE, Boolean.TRUE);
    }

    private void setErrorMessage(String message) {
        if (message == null)
            message = " ";
        jLabelError.setText(message);
    }
    
    public Node[] getSelectedNodes() {
        return nodeDisplayPanel.getSelectedNodes();
    }

    String getSelectedPortOwnerName() {
        Node[] nodes = nodeDisplayPanel.getSelectedNodes();
        return nodes.length > 0 ? nodeDisplayPanel.getSelectedNodes()[0].getParentNode().getDisplayName() : null;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabelDesc = new javax.swing.JLabel();
        jPanelBeanTree = new javax.swing.JPanel();
        jLabelError = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(jLabelDesc, org.openide.util.NbBundle.getMessage(PortChooser.class, "LBL_SelectPort")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 11, 0, 11);
        add(jLabelDesc, gridBagConstraints);

        jPanelBeanTree.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(11, 11, 11, 11);
        add(jPanelBeanTree, gridBagConstraints);

        jLabelError.setForeground(new java.awt.Color(255, 0, 0));
        jLabelError.setLabelFor(jPanelBeanTree);
        jLabelError.setText(" ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 11, 0, 11);
        add(jLabelError, gridBagConstraints);

        getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(PortChooser.class, "TTL_SelectPort")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabelDesc;
    private javax.swing.JLabel jLabelError;
    private javax.swing.JPanel jPanelBeanTree;
    // End of variables declaration//GEN-END:variables
    
    private class NodeAcceptorImpl implements NodeAcceptor {
        public boolean acceptNodes(Node[] nodes) {
            setErrorMessage(" "); //NOI18N

            // no node selected
            if (nodes.length == 0) {
                setErrorMessage(NbBundle.getMessage(PortChooser.class, "LBL_SelectOnePort")); //NOI18N
                return false;
            }
            
            PortNode port = nodes[0].getLookup().lookup(PortNode.class);
            // non-port node is selected
            if (port == null) {
                setErrorMessage(NbBundle.getMessage(PortChooser.class, "LBL_NodeIsNotPort")); //NOI18N
                return false;
            }
            
            return true;
        }
    }

}
