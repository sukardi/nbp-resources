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
package org.netbeans.modules.subversion.remote.util;

import java.awt.Color;
import java.awt.Font;
import java.net.URL;
import javax.swing.UIManager;
import javax.swing.event.HyperlinkEvent;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLDocument;
import org.netbeans.modules.subversion.remote.Subversion;
import org.openide.awt.HtmlBrowser;

/**
 *
 * @author ondra
 */
public class NotifyHtmlPanel extends javax.swing.JPanel {

    /**
     * Creates new form SwitchToCliPanel
     */
    public NotifyHtmlPanel () {
        initComponents();
        Document doc = msgPanel.getDocument();
        if (doc instanceof HTMLDocument) { // Issue 185505
            HTMLDocument htmlDoc = (HTMLDocument)doc;
            Font font = UIManager.getFont("Label.font"); // NOI18N
            String bodyRule = "body { font-family: " + font.getFamily() + "; " // NOI18N
                + "color: " + SvnUtils.getColorString(msgPanel.getForeground()) + "; " //NOI18N
                + "font-size: " + font.getSize() + "pt; }"; // NOI18N
            htmlDoc.getStyleSheet().addRule(bodyRule);
        }
        msgPanel.setOpaque(false);
        msgPanel.setBackground(new Color(0,0,0,0)); // windows and nimbus workaround see issue 145826
    }

    public void setText (String text) {
        msgPanel.setText(text);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        msgPanel = new javax.swing.JTextPane();

        msgPanel.setBackground(new Color(0, 0, 0, 0));
        msgPanel.setContentType("text/html"); // NOI18N
        msgPanel.setEditable(false);
        msgPanel.setOpaque(false);
        msgPanel.addHyperlinkListener(new javax.swing.event.HyperlinkListener() {
            public void hyperlinkUpdate(javax.swing.event.HyperlinkEvent evt) {
                msgPanelHyperlinkUpdate(evt);
            }
        });
        jScrollPane1.setViewportView(msgPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void msgPanelHyperlinkUpdate(javax.swing.event.HyperlinkEvent evt) {//GEN-FIRST:event_msgPanelHyperlinkUpdate
        if(evt.getEventType() != HyperlinkEvent.EventType.ACTIVATED) {
            return;
        }
        URL url = evt.getURL();
        HtmlBrowser.URLDisplayer displayer = HtmlBrowser.URLDisplayer.getDefault ();
        if (displayer != null) {
            displayer.showURL (url);
        } else {
            Subversion.LOG.info("No URLDisplayer found.");
        }
    }//GEN-LAST:event_msgPanelHyperlinkUpdate

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane msgPanel;
    // End of variables declaration//GEN-END:variables
}
