/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2011 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2011 Sun Microsystems, Inc.
 */

package org.netbeans.modules.php.project.connections.ftp;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.HtmlBrowser;
import org.openide.awt.Mnemonics;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;

/**
 * Warning about firewall issue on Windows and JDK 7, see issue #202021.
 */
public final class WindowsJdk7WarningPanel extends JPanel {

    private static final long serialVersionUID = 54654646872L;

    private static final boolean IS_WINDOWS = Utilities.isWindows();
    private static final boolean IS_JDK7 = System.getProperty("java.version").startsWith("1.7."); // NOI18N

    private static volatile Boolean windowsJdk7Warning;


    private WindowsJdk7WarningPanel() {
        initComponents();
    }

    /**
     * Possibly open warning dialog.
     */
    public static void warn() {
        if (!showWindowsJdk7Warning()) {
            return;
        }
        WindowsJdk7WarningPanel panel = new WindowsJdk7WarningPanel();
        NotifyDescriptor descriptor = new NotifyDescriptor.Message(panel, NotifyDescriptor.WARNING_MESSAGE);
        DialogDisplayer.getDefault().notify(descriptor);
        if (panel.doNotShowAgainCheckBox.isSelected()) {
            hideWindowsJdk7Warning();
        }
    }

    private static boolean showWindowsJdk7Warning() {
        if (windowsJdk7Warning == null) {
            windowsJdk7Warning = IS_WINDOWS && IS_JDK7 && FtpPreferences.getInstance().getWindowsJdk7Warning();
        }
        return windowsJdk7Warning;
    }

    private static void hideWindowsJdk7Warning() {
        windowsJdk7Warning = false;
        FtpPreferences.getInstance().setWindowsJdk7Warning(false);
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        infoLabel = new JLabel();
        issueLinkLabel = new JLabel();
        doNotShowAgainCheckBox = new JCheckBox();
        infoScrollPane = new JScrollPane();
        infoTextPane = new JTextPane();

        Mnemonics.setLocalizedText(infoLabel, NbBundle.getMessage(WindowsJdk7WarningPanel.class, "WindowsJdk7WarningPanel.infoLabel.text")); // NOI18N
        Mnemonics.setLocalizedText(issueLinkLabel, NbBundle.getMessage(WindowsJdk7WarningPanel.class, "WindowsJdk7WarningPanel.issueLinkLabel.text"));
        issueLinkLabel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                issueLinkLabelMouseEntered(evt);
            }
            public void mousePressed(MouseEvent evt) {
                issueLinkLabelMousePressed(evt);
            }
        });
        Mnemonics.setLocalizedText(doNotShowAgainCheckBox, NbBundle.getMessage(WindowsJdk7WarningPanel.class, "WindowsJdk7WarningPanel.doNotShowAgainCheckBox.text"));

        infoScrollPane.setBorder(null);

        infoTextPane.setBackground(UIManager.getDefaults().getColor("Label.background"));
        infoTextPane.setBorder(null);
        infoTextPane.setEditable(false);
        infoTextPane.setText(NbBundle.getMessage(WindowsJdk7WarningPanel.class, "TXT_WinJdk7FtpWarning")); // NOI18N
        infoScrollPane.setViewportView(infoTextPane);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                    .addComponent(infoLabel)
                    .addComponent(doNotShowAgainCheckBox))
                .addContainerGap(116, Short.MAX_VALUE))
            .addComponent(infoScrollPane, GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(issueLinkLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(infoLabel)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(infoScrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.UNRELATED)
                .addComponent(issueLinkLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(doNotShowAgainCheckBox))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void issueLinkLabelMouseEntered(MouseEvent evt) {//GEN-FIRST:event_issueLinkLabelMouseEntered
        evt.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_issueLinkLabelMouseEntered

    private void issueLinkLabelMousePressed(MouseEvent evt) {//GEN-FIRST:event_issueLinkLabelMousePressed
        try {
            URL url = new URL("http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=7077696"); // NOI18N
            HtmlBrowser.URLDisplayer.getDefault().showURL(url);
        } catch (MalformedURLException ex) {
            Exceptions.printStackTrace(ex);
        }
    }//GEN-LAST:event_issueLinkLabelMousePressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JCheckBox doNotShowAgainCheckBox;
    private JLabel infoLabel;
    private JScrollPane infoScrollPane;
    private JTextPane infoTextPane;
    private JLabel issueLinkLabel;
    // End of variables declaration//GEN-END:variables
}