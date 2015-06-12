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
package org.netbeans.modules.xml.multiview.test;

import org.netbeans.modules.xml.multiview.ui.SectionInnerPanel;
import org.netbeans.modules.xml.multiview.ui.SectionView;
import org.netbeans.modules.xml.multiview.test.bookmodel.Book;
import org.netbeans.modules.xml.multiview.Error;

/**
 *
 * @author  mkuchtiak
 */
public class BookPanel extends SectionInnerPanel {
    Book book;
    BookDataObject dObj;
    javax.swing.JTextArea[] paragraphTA;
    /** Creates new form ChapterPanel */
    public BookPanel(SectionView view, BookDataObject dObj,  Book book) {
        super(view);
        this.dObj=dObj;
        this.book=book;
        initComponents();
        titleTF.setText(book.getTitle());
        addValidatee(titleTF);
        priceTF.setText(book.getPrice());
        addModifier(priceTF);
        paperbackBox.setSelected(book.isPaperback());
        String instock = book.getAttributeValue("instock");
        instockBox.setSelected("yes".equals(instock));
    }

    public void setValue(javax.swing.JComponent source, Object value) {
        if (source==titleTF) {
            book.setTitle((String)value);
        } else if (source==priceTF) {
            book.setPrice((String)value);
        }
    }
    
    public void documentChanged(javax.swing.text.JTextComponent comp, String value) {
        if (comp==titleTF) {
            String val = (String)value;
            if (val.length()==0) {
                getSectionView().getErrorPanel().setError(new Error(Error.MISSING_VALUE_MESSAGE, "title", comp));
                return;
            }
            getSectionView().getErrorPanel().clearError();
        }
    }

    public void rollbackValue(javax.swing.text.JTextComponent source) {
        if (titleTF==source) {
            titleTF.setText(book.getTitle());
        }
    }
    
    protected void endUIChange() {
        dObj.modelUpdatedFromUI();
    }

    public void linkButtonPressed(Object ddBean, String ddProperty) {
    }

    public javax.swing.JComponent getErrorComponent(String errorId) {
        if ("title".equals(errorId)) return titleTF;
        return null;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        titleLabel = new javax.swing.JLabel();
        titleTF = new javax.swing.JTextField();
        priceLabel = new javax.swing.JLabel();
        priceTF = new javax.swing.JTextField();
        paperbackBox = new javax.swing.JCheckBox();
        instockBox = new javax.swing.JCheckBox();
        filler = new javax.swing.JPanel();

        setLayout(new java.awt.GridBagLayout());

        titleLabel.setText("Title:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(titleLabel, gridBagConstraints);

        titleTF.setColumns(40);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        add(titleTF, gridBagConstraints);

        priceLabel.setText("Price:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 0);
        add(priceLabel, gridBagConstraints);

        priceTF.setColumns(30);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        add(priceTF, gridBagConstraints);

        paperbackBox.setText("Paperback");
        paperbackBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        paperbackBox.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 5, 0);
        add(paperbackBox, gridBagConstraints);

        instockBox.setText("In Stock");
        instockBox.setActionCommand("instock");
        instockBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        instockBox.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 5, 0);
        add(instockBox, gridBagConstraints);

        filler.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        add(filler, gridBagConstraints);

    }
    // </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel filler;
    private javax.swing.JCheckBox instockBox;
    private javax.swing.JCheckBox paperbackBox;
    private javax.swing.JLabel priceLabel;
    private javax.swing.JTextField priceTF;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JTextField titleTF;
    // End of variables declaration//GEN-END:variables
    
}
