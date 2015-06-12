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
package org.netbeans.modules.j2ee.sun.share.configbean.customizers.webservice;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.ResourceBundle;
import javax.swing.JPanel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import org.netbeans.modules.j2ee.sun.dd.api.ASDDVersion;
import org.netbeans.modules.j2ee.sun.dd.api.CommonDDBean;
import org.netbeans.modules.j2ee.sun.dd.api.common.JavaMethod;
import org.netbeans.modules.j2ee.sun.dd.api.common.Message;
import org.netbeans.modules.j2ee.sun.dd.api.common.MessageSecurity;
import org.netbeans.modules.j2ee.sun.dd.api.common.MessageSecurityBinding;
import org.netbeans.modules.j2ee.sun.share.Constants;
import org.netbeans.modules.j2ee.sun.share.configbean.Utils;
import org.netbeans.modules.j2ee.sun.share.configbean.customizers.common.GenericTableModel;
import org.netbeans.modules.j2ee.sun.share.configbean.customizers.common.GenericTablePanel;
import org.netbeans.modules.j2ee.sun.share.configbean.customizers.common.HelpContext;
import org.netbeans.modules.j2ee.sun.share.configbean.customizers.common.InputDialog;
import org.openide.util.NbBundle;

/**
 *
 * @author Peter Williams
 */
public class EditBindingMultiview extends JPanel implements TableModelListener {
    
    private final ResourceBundle webserviceBundle = NbBundle.getBundle(
       "org.netbeans.modules.j2ee.sun.share.configbean.customizers.webservice.Bundle"); // NOI18N
    
    private final ASDDVersion asVersion;
    private final String asCloneVersion;
    private final MessageSecurityBinding msBinding;
    private final boolean methodAsOperation;
    
    private Dimension initialPreferredSize;
    
	// Table for editing MessageSecurity entries
	private GenericTableModel messageSecurityModel;
	private GenericTablePanel messageSecurityPanel;

    private MessageSecurity [] bindingData;
    private MessageSecurity [] newBindingData;

    private String providerId;
    
    
    /** Creates new form EditBinding */
    public EditBindingMultiview(MessageSecurityBinding binding, boolean asOperation, 
            ASDDVersion asDDVersion, String stringVersion) {
        methodAsOperation = asOperation;
        asVersion = asDDVersion;
        asCloneVersion = stringVersion;
        msBinding = binding;
        
        providerId = msBinding.getProviderId();
        bindingData = expand(binding.getMessageSecurity());
        
        initComponents();
        initUserComponents();
        
        initFields();
    }
    
    public String getProviderId() {
        return providerId;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLblAuthReq = new javax.swing.JLabel();
        jLblAuthorizationLayer = new javax.swing.JLabel();
        jTxtAuthorizationLayer = new javax.swing.JTextField();
        jLblProvIdReq = new javax.swing.JLabel();
        jLblProviderId = new javax.swing.JLabel();
        jTxtProviderId = new javax.swing.JTextField();

        setLayout(new java.awt.GridBagLayout());

        jLblAuthReq.setLabelFor(jTxtAuthorizationLayer);
        jLblAuthReq.setText("*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(jLblAuthReq, gridBagConstraints);

        jLblAuthorizationLayer.setLabelFor(jTxtAuthorizationLayer);
        jLblAuthorizationLayer.setText(webserviceBundle.getString("LBL_AuthorizationLayer_1"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(jLblAuthorizationLayer, gridBagConstraints);

        jTxtAuthorizationLayer.setEditable(false);
        jTxtAuthorizationLayer.setText("SOAP");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 5);
        add(jTxtAuthorizationLayer, gridBagConstraints);

        jLblProvIdReq.setLabelFor(jTxtProviderId);
        jLblProvIdReq.setText("*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(jLblProvIdReq, gridBagConstraints);

        jLblProviderId.setLabelFor(jTxtProviderId);
        jLblProviderId.setText(webserviceBundle.getString("LBL_Provider_Id_1"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(jLblProviderId, gridBagConstraints);

        jTxtProviderId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtProviderIdKeyReleased(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 5);
        add(jTxtProviderId, gridBagConstraints);

    }// </editor-fold>//GEN-END:initComponents

    private void jTxtProviderIdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtProviderIdKeyReleased
        providerId = jTxtProviderId.getText();
        firePropertyChange(Constants.USER_DATA_CHANGED, null, null);
    }//GEN-LAST:event_jTxtProviderIdKeyReleased
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLblAuthReq;
    private javax.swing.JLabel jLblAuthorizationLayer;
    private javax.swing.JLabel jLblProvIdReq;
    private javax.swing.JLabel jLblProviderId;
    private javax.swing.JTextField jTxtAuthorizationLayer;
    private javax.swing.JTextField jTxtProviderId;
    // End of variables declaration//GEN-END:variables

    private void initUserComponents() {
		/* Save preferred size before adding table.  We have our own width and
		 * will add a constant of our own choosing for the height in init(), below.
		 */
		initialPreferredSize = getPreferredSize();

        /* Add message-security table panel :
         * TableEntry list has four properties: locale, agent, charset, description
         */
        ArrayList tableColumns = new ArrayList(5);
		tableColumns.add(new MessageEntry(methodAsOperation));
		tableColumns.add(new AuthorizationEntry(MessageSecurity.REQUEST_PROTECTION, 
                "AuthSource", "ReqAuthSource")); // NOI18N - property name
		tableColumns.add(new AuthorizationEntry(MessageSecurity.REQUEST_PROTECTION, 
                "AuthRecipient", "ReqAuthRecipient")); // NOI18N - property name
		tableColumns.add(new AuthorizationEntry(MessageSecurity.RESPONSE_PROTECTION, 
                "AuthSource", "RespAuthSource")); // NOI18N - property name
		tableColumns.add(new AuthorizationEntry(MessageSecurity.RESPONSE_PROTECTION, 
                "AuthRecipient", "RespAuthRecipient")); // NOI18N - property name

        messageSecurityModel = new GenericTableModel(
                new GenericTableModel.ParentPropertyFactory() {
                    public CommonDDBean newParentProperty(ASDDVersion asVersion) {
                        return msBinding.newMessageSecurity();
                    }
                },
                tableColumns);
		messageSecurityModel.addTableModelListener(this);
		messageSecurityPanel = new GenericTablePanel(messageSecurityModel, 
			webserviceBundle, "MessageSecurity", // NOI18N - property name
			MessageSecurityEntryPanel.class, 
			HelpContext.HELP_SERVICE_ENDPOINT_SECURITY_POPUP, Boolean.valueOf(this.methodAsOperation));

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(0, 6, 0, 5);
        add(messageSecurityPanel, gridBagConstraints);
        
        getAccessibleContext().setAccessibleName(webserviceBundle.getString("ACSN_EditBindings")); // NOI18N
        getAccessibleContext().setAccessibleDescription(webserviceBundle.getString("ACSD_EditBindings")); // NOI18N
    }
    
    private void initFields() {
        // Initialize table data model
        messageSecurityPanel.setModel(bindingData, asVersion);
        
        // Initialize text fields.
        updateTextFields();
        
        // Set preferred size (just height really) to be something reasonable (because
        // the default is unnecessarily tall).
        setPreferredSize(new Dimension(initialPreferredSize.width, initialPreferredSize.height + 148));
    }
    
    private void updateTextFields() {
        jTxtProviderId.setText(providerId);
    }
    
    Collection getErrors() {
        // Validate what the user typed in as a valid group name
        ArrayList errors = new ArrayList();
        String newProviderId = getProviderId();

        /** Providier ID must not be blank...
         */
        if(!Utils.notEmpty(newProviderId)) {
            errors.add(webserviceBundle.getString("ERR_BlankProviderId")); // NOI18N
        }
        
        /** Should be at least one binding...
         */
        if(newBindingData == null || newBindingData.length == 0) {
            errors.add(webserviceBundle.getString("ERR_NoSecurityBindings")); // NOI18N
        }
        
        /* Should only be one binding if any are named "*"
         */
        if(newBindingData != null && newBindingData.length > 1 && hasStarBinding()) {
            errors.add(webserviceBundle.getString("ERR_StarBindingConflict")); // NOI18N
        }

        return errors;
    }
    
    private boolean hasStarBinding() {
        boolean result = false;
        
        for(int i = 0; i < newBindingData.length; i++) {
            String name = null;
            if(newBindingData[i] != null) {
                Message [] msgs = newBindingData[i].getMessage();
                if(msgs != null && msgs.length > 0 && msgs[0] != null) {
                    name = getMethodName(msgs[0]);
                }
            }
            if("*".equals(name)) {
                result = true;
                break;
            }
        }
        
        return result;
    }
    
	/** -----------------------------------------------------------------------
	 *  Implementation of TableModelListener interface
	 */
	public void tableChanged(TableModelEvent e) {
        newBindingData = (MessageSecurity[]) messageSecurityModel.getData().toArray(new MessageSecurity [0]);
        firePropertyChange(Constants.USER_DATA_CHANGED, null, null);	
    }
	
    private void commit() {
        // Set authorization layer
        msBinding.setAuthLayer("SOAP"); // NOI18N
        
        // Set provider id to binding.
        String newProviderId = getProviderId();
        msBinding.setProviderId(newProviderId);

        // Set message security entries.
        newBindingData = compress(newBindingData);
        msBinding.setMessageSecurity(newBindingData);
    }    

    private MessageSecurity [] expand(MessageSecurity [] bindingData) {
        MessageSecurity [] result = new MessageSecurity[0];
        
        if(bindingData != null && bindingData.length > 0) {
            ArrayList bindings = new ArrayList(bindingData.length * 10);
            
            for(int i = 0; i < bindingData.length; i++) {
                MessageSecurity ms = bindingData[i];
                Message [] messages = ms.getMessage();
                if(messages != null && messages.length > 0) {
                    if(messages.length == 1) {
                        bindings.add(ms);
                    } else {
                        for(int j = 0; j < messages.length; j++) {
                            MessageSecurity newMS = (MessageSecurity) ms.cloneVersion(asCloneVersion);
                            Message newMessage = newMS.getMessage(j);
                            newMS.setMessage(new Message [] { newMessage } );
                            bindings.add(newMS);
                        }
                    }
                }
            }
            
            result = (MessageSecurity []) bindings.toArray(result);
        }
        
        return result;
    }
    
    private MessageSecurity [] compress(MessageSecurity [] bindingData) {
        MessageSecurity [] result = null;
        
        if(bindingData != null && bindingData.length > 0) {
            ArrayList securityList = new ArrayList(bindingData.length);
            Arrays.sort(bindingData, new MessageSecurityComparator(true));
            
            MessageSecurityComparator protectionComparator = new MessageSecurityComparator(false);
            for(int i = 0; i < bindingData.length; ) {
                int j = i + 1;
                while(j < bindingData.length && protectionComparator.compare(bindingData[i], bindingData[j]) == 0) {
                    j++;
                }
                int elementsToMerge = j - i;
                assert elementsToMerge > 0;
                        
                if(elementsToMerge == 1) {
                    securityList.add(bindingData[i].clone());
                } else {
                    ArrayList messageList = new ArrayList(elementsToMerge);
                    for(int m = i; m < j; m++) {
                        Message [] message = bindingData[m].getMessage();
                        if(message != null && message.length > 0) {
                            // Shouldn't be more than one message element by this point.
                            messageList.add(message[0].clone());
                        }
                    }
                    Message [] messages = (Message []) messageList.toArray(new Message [0]);
                    bindingData[i].setMessage(messages);
                    securityList.add(bindingData[i].clone());
                }
                i += elementsToMerge;
            }
            result = (MessageSecurity []) securityList.toArray(new MessageSecurity[0]);
        }
        
        return result;
    }
    
    private static class MessageSecurityComparator implements Comparator {
        
        private boolean sortNames;
        
        public MessageSecurityComparator(boolean sn) {
            sortNames = sn;
        }
        
        public int compare(Object o1, Object o2) {
            // Order by response/request settings, then alphabetically by operation/method name
            MessageSecurity ms1 = (MessageSecurity) o1;
            MessageSecurity ms2 = (MessageSecurity) o2;

            int result = Utils.strCompareTo(ms1.getRequestProtectionAuthSource(), ms2.getRequestProtectionAuthSource());
            if(result != 0) {
                return result;
            }
            result = Utils.strCompareTo(ms1.getRequestProtectionAuthRecipient(), ms2.getRequestProtectionAuthRecipient());
            if(result != 0) {
                return result;
            }
            result = Utils.strCompareTo(ms1.getResponseProtectionAuthSource(), ms2.getResponseProtectionAuthSource());
            if(result != 0) {
                return result;
            }
            result = Utils.strCompareTo(ms1.getResponseProtectionAuthRecipient(), ms2.getResponseProtectionAuthRecipient());
            if(result != 0) {
                return result;
            }
            
            if(sortNames) {
                Message [] m1 = ms1.getMessage();
                Message [] m2 = ms2.getMessage();

                assert m1.length == 1;
                assert m2.length == 1;

                result = Utils.strCompareTo(getMethodName(m1[0]), getMethodName(m2[0]));
            }
            
            return result;
        }

        public boolean equals(Object obj) {
            return this == obj;
        }

        public int hashCode() {
            return 17 + 30 * (sortNames ? 1 : 0);
        }
    }
    
    private static String getMethodName(Message m) {
        String name = m.getOperationName();
        if(name == null) {
            JavaMethod method = m.getJavaMethod();
            if(method != null) {
                name = method.getMethodName();
            }
        }
        return name;
    }
    
    /** Displays panel for editing the message-security-bindings of the selected endpoint.
     *
     * @param parent JPanel that is the parent of this popup - used for centering and sizing.
     * @param binding Reference to current binding data.
     * @param aSDDVersion For versioning the UI and creating the correct implementation
     *      of any needed MessageSecurity objects.
     */
    static void editMessageSecurityBinding(JPanel parent, boolean editMethodAsOperation, 
            MessageSecurityBinding binding, ASDDVersion asDDVersion, String stringVersion) {
        EditBindingMultiview bindingPanel = new EditBindingMultiview(binding, editMethodAsOperation, asDDVersion, stringVersion);
        bindingPanel.displayDialog(parent, 
                NbBundle.getBundle("org.netbeans.modules.j2ee.sun.share.configbean.customizers.webservice.Bundle").getString("TITLE_EditBindings"), // NOI18N
                HelpContext.HELP_SERVICE_ENDPOINT_SECURITY); // NOI18N
    }
    
    private void displayDialog(JPanel parent, String title, String helpId) {
        BetterInputDialog dialog = new BetterInputDialog(parent, title, helpId, this);

        do {
            int dialogChoice = dialog.display();

            if(dialogChoice == dialog.CANCEL_OPTION) {
                break;
            }

            if(dialogChoice == dialog.OK_OPTION) {
                Collection errors = getErrors();

                if(dialog.hasErrors()) {
                    // !PW is this even necessary w/ new validation model?
                    dialog.showErrors();
                } else {
                    commit();
                }
            }
        } while(dialog.hasErrors());
    }    

    private static class BetterInputDialog extends InputDialog {
        private final EditBindingMultiview dialogPanel;
        private final String panelHelpId;

        public BetterInputDialog(JPanel parent, String title, String helpId, EditBindingMultiview childPanel) {
            super(parent, title);

            dialogPanel = childPanel;
            panelHelpId = helpId;

            dialogPanel.setPreferredSize(new Dimension(parent.getWidth()*3/4, 
                dialogPanel.getPreferredSize().height));

            this.getAccessibleContext().setAccessibleName(dialogPanel.getAccessibleContext().getAccessibleName());
            this.getAccessibleContext().setAccessibleDescription(dialogPanel.getAccessibleContext().getAccessibleDescription());

            getContentPane().add(childPanel, BorderLayout.CENTER);
            addListeners();
            pack();
            setLocationInside(parent);
            handleErrorDisplay();
        }

        private void addListeners() {
            dialogPanel.addPropertyChangeListener(Constants.USER_DATA_CHANGED, new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    handleErrorDisplay();
                }
            });
        }

        private void handleErrorDisplay() {
            ArrayList errors = new ArrayList();
            errors.addAll(dialogPanel.getErrors());
            setErrors(errors);
        }

        protected String getHelpId() {
            return panelHelpId;
        }
    }
}
