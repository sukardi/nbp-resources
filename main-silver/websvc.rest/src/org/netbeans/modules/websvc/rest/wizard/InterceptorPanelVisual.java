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
 * Portions Copyrighted 2012 Sun Microsystems, Inc.
 */
package org.netbeans.modules.websvc.rest.wizard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.openide.WizardDescriptor;
import org.openide.util.NbBundle;

/**
 *
 * @author den
 */
public class InterceptorPanelVisual extends javax.swing.JPanel {

    /**
     * @param myDescriptor
     */
    public InterceptorPanelVisual( WizardDescriptor myDescriptor ) {
        listeners = new CopyOnWriteArrayList<ChangeListener>();
        initComponents();
        
        ActionListener listener = new ActionListener() {
            
            @Override
            public void actionPerformed( ActionEvent arg0 ) {
                fireChangeEvent();
            }
        };
        reader.addActionListener(listener);
        writer.addActionListener(listener);
    }
    
    void addChangeListener( ChangeListener listener ) {
        listeners.add(listener);        
    }

    String getError() {
        if ( !reader.isSelected() && !writer.isSelected()){
            return NbBundle.getMessage(InterceptorPanelVisual.class, 
                    "ERR_NoInterceptorType");
        }
        return null;
    }

    void readSettings( WizardDescriptor descriptor ) {
    }

    void removeChangeListener( ChangeListener listener ) {
        listeners.remove(listener);        
    }

    void storeSettings( WizardDescriptor descriptor ) {
        descriptor.putProperty(InterceptorPanel.READER, reader.isSelected());
        descriptor.putProperty(InterceptorPanel.WRITER, writer.isSelected());
    }
    
    private void fireChangeEvent(){
        ChangeEvent event = new ChangeEvent(this);
        for(ChangeListener listener :listeners ){
            listener.stateChanged(event);
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

        type = new javax.swing.JLabel();
        typesPanel = new javax.swing.JPanel();
        reader = new javax.swing.JCheckBox();
        writer = new javax.swing.JCheckBox();

        type.setLabelFor(typesPanel);
        org.openide.awt.Mnemonics.setLocalizedText(type, org.openide.util.NbBundle.getMessage(InterceptorPanelVisual.class, "LBL_InterceptorType")); // NOI18N

        typesPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        org.openide.awt.Mnemonics.setLocalizedText(reader, org.openide.util.NbBundle.getMessage(InterceptorPanelVisual.class, "LBL_ReaderInterceptor")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(writer, org.openide.util.NbBundle.getMessage(InterceptorPanelVisual.class, "LBL_WriterInterceptor")); // NOI18N

        javax.swing.GroupLayout typesPanelLayout = new javax.swing.GroupLayout(typesPanel);
        typesPanel.setLayout(typesPanelLayout);
        typesPanelLayout.setHorizontalGroup(
            typesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(typesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(typesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(reader)
                    .addComponent(writer))
                .addContainerGap(213, Short.MAX_VALUE))
        );
        typesPanelLayout.setVerticalGroup(
            typesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(typesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(reader)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(writer)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        reader.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(InterceptorPanelVisual.class, "ACSN_ReaderInterceptor")); // NOI18N
        reader.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(InterceptorPanelVisual.class, "ACSD_ReaderInterceptor")); // NOI18N
        writer.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(InterceptorPanelVisual.class, "ACSN_WriterInterceptor")); // NOI18N
        writer.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(InterceptorPanelVisual.class, "ACSD_WriterInterceptor")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(type)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(typesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(type)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(typesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        type.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(InterceptorPanelVisual.class, "ACSN_Interceptors")); // NOI18N
        type.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(InterceptorPanelVisual.class, "ACSD_Interceptors")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox reader;
    private javax.swing.JLabel type;
    private javax.swing.JPanel typesPanel;
    private javax.swing.JCheckBox writer;
    // End of variables declaration//GEN-END:variables
    
    private List<ChangeListener> listeners;
    
}
