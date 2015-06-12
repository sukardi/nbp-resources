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

package org.netbeans.modules.websvc.rest.wadl.design.view.actions;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import org.netbeans.modules.websvc.rest.wadl.design.Util;
import org.netbeans.modules.websvc.rest.wadl.design.view.actions.ParamModel;
import org.netbeans.modules.websvc.rest.wadl.model.*;
import org.openide.util.NbBundle;

/**
 *
 * @author  Milan Kuchtiak
 */
public final class ParametersPanel extends javax.swing.JPanel {
    
    private static final int COL_NAME_INDEX = 0;
    private static final int COL_TYPE_INDEX = 1;
    private static final int COL_STYLE_INDEX = 2;
    private static final int COL_DEFAULT_INDEX = 3;
    private static final String[] columnNames = {
        NbBundle.getMessage(ParametersPanel.class, "ParametersPanel.LBL_Name"),
        NbBundle.getMessage(ParametersPanel.class, "ParametersPanel.LBL_Type"),
        NbBundle.getMessage(ParametersPanel.class, "ParametersPanel.LBL_Style"),
        NbBundle.getMessage(ParametersPanel.class, "ParametersPanel.LBL_Default")
    };
    
    private final ParamsTableModel tableModel;
    
    public ParametersPanel() {
        initComponents();
        tableModel = new ParamsTableModel();
        table.setModel(tableModel);
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                updateButtons();
            }
        });
        table.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                updateButtons();
            }
        });
        
        tableModel.addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent tableModelEvent) {
                updateButtons();
            }
        });
    }

    public List<ParamModel> getParameters() {
        return tableModel.getParameters();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        addButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        upButton = new javax.swing.JButton();
        downButton = new javax.swing.JButton();

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(table);

        org.openide.awt.Mnemonics.setLocalizedText(addButton, org.openide.util.NbBundle.getMessage(ParametersPanel.class, "LBL_ADD")); // NOI18N
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(removeButton, org.openide.util.NbBundle.getMessage(ParametersPanel.class, "LBL_REMOVE")); // NOI18N
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(upButton, org.openide.util.NbBundle.getMessage(ParametersPanel.class, "LBL_UP")); // NOI18N
        upButton.setEnabled(false);
        upButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(downButton, org.openide.util.NbBundle.getMessage(ParametersPanel.class, "LBL_DOWN")); // NOI18N
        downButton.setEnabled(false);
        downButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addButton)
                    .addComponent(removeButton)
                    .addComponent(upButton)
                    .addComponent(downButton))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {addButton, downButton, removeButton, upButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeButton)
                        .addGap(22, 22, 22)
                        .addComponent(upButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(downButton))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
    int selectedRow = table.getSelectedRow();
    if (selectedRow > -1) {
        tableModel.removeParameter(selectedRow);
    }
    if (selectedRow == table.getRowCount()) {
        selectedRow--;
    }
    table.getSelectionModel().setSelectionInterval(selectedRow, selectedRow);
    updateButtons();
}//GEN-LAST:event_removeButtonActionPerformed

private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
    int index = tableModel.addParameter();
    table.getSelectionModel().setSelectionInterval(index, index);
    updateButtons();
}//GEN-LAST:event_addButtonActionPerformed

private void upButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upButtonActionPerformed
    int selIndex = table.getSelectedRow();
    int newIndex = selIndex - 1;
    if (newIndex >= 0) {
        tableModel.set(newIndex, tableModel.set(selIndex, tableModel.getParameter(newIndex)));
        table.setRowSelectionInterval(newIndex, newIndex);
        updateButtons();
    }
}//GEN-LAST:event_upButtonActionPerformed

private void downButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downButtonActionPerformed
    int selIndex = table.getSelectedRow();
    int newIndex = selIndex + 1;
    if (newIndex < tableModel.getParameters().size()) {
        tableModel.set(newIndex, tableModel.set(selIndex, tableModel.getParameter(newIndex)));
        table.setRowSelectionInterval(newIndex, newIndex);
        updateButtons();
    }
}//GEN-LAST:event_downButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton downButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton removeButton;
    private javax.swing.JTable table;
    private javax.swing.JButton upButton;
    // End of variables declaration//GEN-END:variables
    
    private void updateButtons() {        
        int selIndex = table.getSelectedRow();
        boolean oneSelected = table.getSelectedRowCount() == 1;
        
        removeButton.setEnabled(oneSelected);
        upButton.setEnabled(oneSelected && (selIndex > 0));
        downButton.setEnabled(oneSelected && (selIndex < tableModel.getRowCount() - 1));
    }
    
    // accessible for test
    private class ParamsTableModel extends AbstractTableModel {
        
        private final List<ParamModel> parameters;
        
        public ParamsTableModel() {
            parameters = new ArrayList<ParamModel>();
        }
        
        public List<ParamModel> getParameters() {
            return parameters;
        }
        
        public int addParameter() {
            String name = generateUniqueName("arg"); //NOI18N
            ParamModel parameter = new ParamModel(name);
            int index = parameters.size();
            parameters.add(parameter);
            setValueAt("xs:string", index, 1);
            setValueAt(ParamStyle.QUERY, index, 2);
            fireTableRowsInserted(index, index);
            return index;
        }
        
        public void removeParameter(int index) {
            parameters.remove(index);
            fireTableRowsDeleted(index, index);
        }
        
        public int getRowCount() {
            return parameters.size();
        }
        
        public int getColumnCount() {
            return columnNames.length;
        }
        
        public Object getValueAt(int row, int column) {
            Object result = null;
            ParamModel parameter = parameters.get(row);
            if (parameter != null) {
                switch (column) {
                    case COL_NAME_INDEX:
                        result = parameter.getParamName();
                        break;
                    case COL_TYPE_INDEX:
                        result = parameter.getParamType();
                        break;
                    case COL_STYLE_INDEX:
                        result = parameter.getParamStyle();
                        break;
                    case COL_DEFAULT_INDEX:
                        result = parameter.getParamDefault();
                        break;
                }
            }
            return result;
        }
        
        public String getColumnName(int column) {
            return columnNames[column];
        }
        
        public boolean isCellEditable(int row, int column) {
            return true;
        }
        
        public void setValueAt(Object aValue, int row, int column) {
            ParamModel parameter = parameters.get(row);
            ParamModel changedParameter = new ParamModel();
            changedParameter.setParamName(parameter.getParamName());
            changedParameter.setParamType(parameter.getParamType());
            changedParameter.setParamStyle(parameter.getParamStyle());
            changedParameter.setParamDefault(parameter.getParamDefault());
            switch (column) {
                case COL_NAME_INDEX:
                    changedParameter.setParamName((String) aValue);
                    break;
                case COL_TYPE_INDEX:
                    changedParameter.setParamType((String) aValue);
                    break;
                case COL_STYLE_INDEX:
                    changedParameter.setParamStyle((ParamStyle) aValue);
                    break;
                case COL_DEFAULT_INDEX:
                    changedParameter.setParamDefault((String) aValue);
                    break;
            }
            parameters.set(row, changedParameter);
            fireTableCellUpdated(row, column);
        }
        
        public ParamModel getParameter(int index) {
            return parameters.get(index);
        }
        
        public ParamModel set(int index, ParamModel parameter) {
            return parameters.set(index, parameter);
        }
        
        private String generateUniqueName(String name) {
            List<String> existingNames = new ArrayList<String>();
            if(parameters != null) {
                for (ParamModel param : parameters) {
                    existingNames.add(param.getParamName());
                }
            }
            return Util.generateUniqueName(name, existingNames);
        }
        
    }
    
    private class MyComboBoxRenderer extends JComboBox implements TableCellRenderer {
        public MyComboBoxRenderer(String[] items) {
            super(items);
        }
        
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                super.setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(table.getBackground());
            }
            
            // Select the current value
            setSelectedItem(value);
            return this;
        }
        
    }
    
    private class MyComboBoxEditor extends DefaultCellEditor {
        public MyComboBoxEditor(String[] items) {
            super(new JComboBox(items));
        }
    }
}
