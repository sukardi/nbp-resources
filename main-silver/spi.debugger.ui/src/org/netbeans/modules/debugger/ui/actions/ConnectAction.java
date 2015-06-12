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

package org.netbeans.modules.debugger.ui.actions;

import java.awt.Dialog;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import org.netbeans.api.debugger.DebuggerManager;
import org.netbeans.modules.debugger.ui.Utils;
import org.netbeans.spi.debugger.ui.AttachType;
import org.netbeans.spi.debugger.ui.Controller;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotificationLineSupport;
import org.openide.NotifyDescriptor;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;


/**
* Connects debugger to some currently running VM.
* This class is final only for performance reasons,
* can be happily unfinaled if desired.
*
* @author   Jan Jancura
*/
public final class ConnectAction extends AbstractAction {

    private static RequestProcessor computeEnabledRP = new RequestProcessor("ConnectAction is enabled", 1); // NOI18N
    private RequestProcessor.Task computeEnabledTask;
    private ConnectorPanel cp;
    private DialogDescriptor descr;
    private Dialog dialog;
    private JButton bOk;
    private JButton bCancel;
    private NotificationLineSupport notificationSupport;
    private volatile boolean lastEnabled = true;

    
    public ConnectAction () {
        putValue (
            Action.NAME, 
            NbBundle.getMessage (
                ConnectAction.class, 
                "CTL_Connect"
            )
        );
        putValue (
            "iconBase", // NOI18N
            "org/netbeans/modules/debugger/resources/actions/Attach.gif" // NOI18N
        );
    }

    @Override
    public boolean isEnabled() {
        if (computeEnabledTask == null) {
            computeEnabledTask = computeEnabledRP.create(new Runnable() {
                public void run() {
                    List attachTypes = DebuggerManager.getDebuggerManager ().lookup (
                        null, AttachType.class
                    );
                    lastEnabled = attachTypes.size() > 0;
                }
            });
        }
        computeEnabledTask.schedule(0);
        try {
            computeEnabledTask.waitFinished(100);   // Wait 100ms at most in AWT.
        } catch (InterruptedException ex) {
        }
        return lastEnabled;
    }
    
    public void actionPerformed (ActionEvent evt) {
        bOk = new JButton (NbBundle.getMessage (ConnectAction.class, "CTL_Ok")); // NOI18N
        bCancel = new JButton (NbBundle.getMessage (ConnectAction.class, "CTL_Cancel")); // NOI18N
        bOk.getAccessibleContext ().setAccessibleDescription (NbBundle.getMessage (ConnectAction.class, "ACSD_CTL_Ok")); // NOI18N
        bCancel.getAccessibleContext ().setAccessibleDescription (NbBundle.getMessage (ConnectAction.class, "ACSD_CTL_Cancel")); // NOI18N
        bCancel.setDefaultCapable(false);
        cp = new ConnectorPanel ();
        descr = new DialogDescriptor (
            cp,
            NbBundle.getMessage (ConnectAction.class, "CTL_Connect_to_running_process"),
            true, // modal
            new ConnectListener (cp)
        );
        descr.setOptions (new JButton[] {
            bOk, bCancel
        });
        notificationSupport = descr.createNotificationLineSupport();
        descr.setClosingOptions (new Object [0]);
        descr.setHelpCtx(HelpCtx.findHelp(cp)); // This is mandatory so that the descriptor tracks the changes in help correctly.
        dialog = DialogDisplayer.getDefault ().createDialog (descr);
        dialog.setVisible(true);
    }


    // innerclasses ............................................................
    private class ConnectListener implements ActionListener, PropertyChangeListener {
        
        ConnectorPanel connectorPanel;
        Controller controller;
        
        ConnectListener (ConnectorPanel connectorPanel) {
            this.connectorPanel = connectorPanel;
            startListening();
            setValid();
            connectorPanel.addPropertyChangeListener(this);
        }
        
        public void actionPerformed (ActionEvent e) {
            if (dialog == null) {
                // Already closed.
                return ;
            }
            boolean okPressed = bOk.equals (e.getSource ());
            boolean close = false;
            if (okPressed) {
                close = connectorPanel.ok ();
            } else {
                close = connectorPanel.cancel ();
            }
            if (!close) return;
            connectorPanel.removePropertyChangeListener (this);
            stopListening ();
            dialog.setVisible (false);
            dialog.dispose ();
            dialog = null;
        }
        
        void startListening () {
            controller = connectorPanel.getController ();
            if (controller == null) return;
            controller.addPropertyChangeListener (this);
        }
        
        void stopListening () {
            if (controller == null) return;
            controller.removePropertyChangeListener (this);
            controller = null;
        }

        void setValid () {
            Controller controller = connectorPanel.getController ();
            if (controller == null) {
                bOk.setEnabled (false);
                return;
            }
            bOk.setEnabled (controller.isValid ());
        }

        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName () == ConnectorPanel.PROP_TYPE) {
                stopListening ();
                notificationSupport.clearMessages();
                descr.setHelpCtx(HelpCtx.findHelp(cp));
                setValid ();
                startListening ();
            } else if (evt.getPropertyName () == Controller.PROP_VALID) {
                setValid ();
            } else if (evt.getPropertyName() == NotifyDescriptor.PROP_ERROR_NOTIFICATION) {
                Object v = evt.getNewValue();
                String msg = (v == null) ? null : v.toString();
                notificationSupport.setErrorMessage(msg);
            } else if (evt.getPropertyName() == NotifyDescriptor.PROP_INFO_NOTIFICATION) {
                Object v = evt.getNewValue();
                String msg = (v == null) ? null : v.toString();
                notificationSupport.setInformationMessage(msg);
            } else if (evt.getPropertyName() == NotifyDescriptor.PROP_WARNING_NOTIFICATION) {
                Object v = evt.getNewValue();
                String msg = (v == null) ? null : v.toString();
                notificationSupport.setWarningMessage(msg);
            }
        }
        
    }
}


