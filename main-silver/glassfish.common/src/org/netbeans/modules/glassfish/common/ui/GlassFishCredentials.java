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
package org.netbeans.modules.glassfish.common.ui;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.modules.glassfish.common.GlassFishLogger;
import org.netbeans.modules.glassfish.common.GlassfishInstance;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import static org.openide.NotifyDescriptor.CANCEL_OPTION;
import org.openide.util.NbBundle;

/**
 * Input GlassFish credentials (username and password).
 * <p/>
 * @author Tomas Kraus
 */
public class GlassFishCredentials extends CommonPasswordPanel {

    ////////////////////////////////////////////////////////////////////////////
    // Class attributes                                                       //
    ////////////////////////////////////////////////////////////////////////////

    /** Local logger. */
    private static final Logger LOGGER
            = GlassFishLogger.get(GlassFishCredentials.class);

    ////////////////////////////////////////////////////////////////////////////
    // Static methods                                                         //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Display GlassFish credentials panel to get GlassFish credentials.
     * <p/>
     * User name and password is stored in server instance properties.
     * Properties are persisted.
     * <p/>
     * @param instance GlassFish server instance.
     * @return Value of <code>true</code> when GlassFish credentials were
     *         updated of <code>false</code> when <code>Cancel</code> button
     *         was selected.
     */
    public static boolean setCredentials(final GlassfishInstance instance,
            final String message) {
        String title = NbBundle.getMessage(
                GlassFishPassword.class, "GlassFishCredentials.title");
        NotifyDescriptor notifyDescriptor = new NotifyDescriptor(
                null, title, NotifyDescriptor.OK_CANCEL_OPTION,
                NotifyDescriptor.PLAIN_MESSAGE, null, null);
        GlassFishCredentials panel
                = new GlassFishCredentials(notifyDescriptor, instance, message);
        Object button = DialogDisplayer.getDefault().notify(notifyDescriptor);
        if (button == CANCEL_OPTION) {
            return false;
        }
        String userName = panel.getUserName();
        String password = panel.getPassword();
        instance.setAdminUser(userName);
        instance.setAdminPassword(password);
        try {
            GlassfishInstance.writeInstanceToFile(instance);
        } catch (IOException ex) {
            LOGGER.log(Level.INFO,
                    "Could not store GlassFish server attributes", ex);
        }
        return true;
    }

    /**
     * Display GlassFish credentials panel to get GlassFish credentials.
     * <p/>
     * User name and password is stored in server instance properties.
     * Properties are persisted.
     * <p/>
     * @param instance GlassFish server instance.
     * @return Value of <code>true</code> when GlassFish credentials were
     *         updated of <code>false</code> when <code>Cancel</code> button
     *         was selected.
     */
    public static boolean setCredentials(final GlassfishInstance instance) {
        String message = NbBundle.getMessage(
                GlassFishPassword.class,
                "GlassFishCredentials.message", instance.getDisplayName());
        return setCredentials(instance, message);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Constructors                                                           //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Creates new GlassFish credentials panel.
     * <p/>
     * @param descriptor User notification descriptor.
     * @param instance   GlassFish server instance used to search
     *                   for supported platforms.
     * @param message    Message text.
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public GlassFishCredentials(final NotifyDescriptor descriptor,
            final GlassfishInstance instance, final String message) {
        super(descriptor, instance, message);
        initComponents();
        initFileds(true);
    }

    ////////////////////////////////////////////////////////////////////////////
    // GUI Getters and Setters                                                //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Get password value from form.
     * <p/>
     * @return Password value from form.
     */
    String getPassword() {
        return new String(password.getPassword());
    }

    /**
     * Get user name value from form.
     * <p/>
     * @return User name value from form.
     */
    String getUserName() {
        return userText.getText();
    }

    ////////////////////////////////////////////////////////////////////////////
    // Methods                                                                //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Clear form fields.
     */
    public void clear() {
        this.password.setText("");
        this.userText.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        messageLabel = new javax.swing.JLabel();
        userLabel = new javax.swing.JLabel();
        passwordLabel = new javax.swing.JLabel();
        userText = new javax.swing.JTextField();
        password = new javax.swing.JPasswordField();

        setMaximumSize(new java.awt.Dimension(500, 200));
        setMinimumSize(new java.awt.Dimension(500, 150));
        setPreferredSize(new java.awt.Dimension(500, 150));

        org.openide.awt.Mnemonics.setLocalizedText(messageLabel, this.message);

        org.openide.awt.Mnemonics.setLocalizedText(userLabel, this.userLabelText);

        org.openide.awt.Mnemonics.setLocalizedText(passwordLabel, this.passwordLabelText);

        userText.setText(this.instance.getAdminUser());

        password.setText(this.instance.getAdminPassword());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(messageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(passwordLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(userLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(userText, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                            .addComponent(password))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(messageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(userLabel)
                    .addComponent(userText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordLabel)
                    .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(65, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel messageLabel;
    private javax.swing.JPasswordField password;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JLabel userLabel;
    private javax.swing.JTextField userText;
    // End of variables declaration//GEN-END:variables
}
