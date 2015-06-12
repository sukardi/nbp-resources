/*
 * AbstractNameValidationDialog.java
 *
 * Created on January 28, 2007, 9:11 PM
 */

package org.netbeans.modules.vmd.game.dialog;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.openide.DialogDescriptor;
import org.openide.NotifyDescriptor;
import org.openide.util.ImageUtilities;
import org.openide.util.Utilities;

/**
 *
 * @author  kherink
 */
public abstract class AbstractNameValidationDialog extends javax.swing.JPanel implements ActionListener {
	
    private static final Icon ICON_ERROR = ImageUtilities.loadImageIcon("org/netbeans/modules/vmd/midp/resources/error.gif", false);
	private DialogDescriptor dd;

	private String initialTextContent;
	
	/** Creates new form AbstractNameValidationDialog */
	public AbstractNameValidationDialog(String initialTextContent) {
		this.initialTextContent = initialTextContent;
		initComponents();
		manualInit();
	}
	
	protected abstract String getInitialStateDescriptionText();
	protected abstract String getNameLabelText();
	protected abstract String getDialogNameText();
	protected abstract String getCurrentStateErrorText();
	protected abstract void handleOKButton();
	
	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelSequenceName = new javax.swing.JLabel();
        fieldName = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        labelError = new javax.swing.JLabel();

        labelSequenceName.setLabelFor(fieldName);
        org.openide.awt.Mnemonics.setLocalizedText(labelSequenceName, org.openide.util.NbBundle.getMessage(AbstractNameValidationDialog.class, "AbstractNameValidationDialog.labelSequenceName.text")); // NOI18N

        fieldName.setText(org.openide.util.NbBundle.getMessage(AbstractNameValidationDialog.class, "AbstractNameValidationDialog.fieldName.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(labelError, org.openide.util.NbBundle.getMessage(AbstractNameValidationDialog.class, "AbstractNameValidationDialog.labelError.text")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelError, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelError, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelSequenceName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fieldName, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSequenceName)
                    .addComponent(fieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        labelSequenceName.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(AbstractNameValidationDialog.class, "ACSN_Name")); // NOI18N
        labelSequenceName.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(AbstractNameValidationDialog.class, "ACSD_Name")); // NOI18N
        fieldName.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(AbstractNameValidationDialog.class, "AbstractNameValidationDialog.findName.accessible.name")); // NOI18N
        fieldName.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(AbstractNameValidationDialog.class, "AbstractNameValidationDialog.findName.accessible.description")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents
	
	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JTextField fieldName;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelError;
    private javax.swing.JLabel labelSequenceName;
    // End of variables declaration//GEN-END:variables

	private void manualInit() {
		this.fieldName.setText(this.initialTextContent);
		this.fieldName.selectAll();
		this.fieldName.getDocument().addDocumentListener(new LayerFieldListener());
		
		this.labelError.setForeground(Color.RED);		
		this.labelSequenceName.setLabelFor(this.fieldName);	
		
		this.labelError.setIcon(ICON_ERROR);
		this.labelError.setText(getInitialStateDescriptionText());
	}

	public void setDialogDescriptor(DialogDescriptor dd) {
		this.dd = dd;
		this.dd.setValid(false);
	}
	
	private static boolean isValidJavaIdentifier(String str) {
		if (str == null || "".equals(str)) {
			return false;
		}
		
		if (!Character.isJavaIdentifierStart(str.charAt(0))) {
			return false;
		}
		for (int i = 1; i < str.length(); i++) {
			if (!Character.isJavaIdentifierPart(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	private class LayerFieldListener implements DocumentListener, FocusListener {
		private boolean error;
		public void insertUpdate(DocumentEvent e) {
			this.handleTextContentChange();
		}
		public void removeUpdate(DocumentEvent e) {
			this.handleTextContentChange();
		}
		public void changedUpdate(DocumentEvent e) {
			this.handleTextContentChange();
		}
		private void handleTextContentChange() {
			String errMsg = null;
			if (!isValidJavaIdentifier(AbstractNameValidationDialog.this.fieldName.getText())) {
				errMsg = "Name must be a valid Java identifier.";
			}
			else {
				errMsg = AbstractNameValidationDialog.this.getCurrentStateErrorText();
			}
			
			if (errMsg != null) {
				AbstractNameValidationDialog.this.labelError.setText(errMsg);
				AbstractNameValidationDialog.this.labelError.setIcon(ICON_ERROR);
				AbstractNameValidationDialog.this.dd.setValid(false);
			}
			else {
				AbstractNameValidationDialog.this.labelError.setText("");
				AbstractNameValidationDialog.this.labelError.setIcon(null);
				AbstractNameValidationDialog.this.dd.setValid(true);
			}
		}
		public void focusGained(FocusEvent e) {
			this.handleTextContentChange();
		}
		public void focusLost(FocusEvent e) {
		}
	}

	
	
	public void actionPerformed(ActionEvent e) {
		//if OK button pressed create the new layer
		if (e.getSource() == NotifyDescriptor.OK_OPTION) {
			this.handleOKButton();
		}
	}
	
	
}
