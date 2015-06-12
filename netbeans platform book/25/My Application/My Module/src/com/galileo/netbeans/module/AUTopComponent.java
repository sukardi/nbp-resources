/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.galileo.netbeans.module;

import java.util.ArrayList;
import java.util.List;
import org.netbeans.api.autoupdate.UpdateElement;
import org.netbeans.api.autoupdate.UpdateManager;
import org.netbeans.api.autoupdate.UpdateUnit;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//com.galileo.netbeans.module//AU//EN",
autostore = false)
@TopComponent.Description(preferredID = "AUTopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "explorer", openAtStartup = true)
@ActionID(category = "Window", id = "com.galileo.netbeans.module.AUTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(displayName = "#CTL_AUAction",
preferredID = "AUTopComponent")
public final class AUTopComponent extends TopComponent {

   public AUTopComponent() {
      initComponents();
      setName(NbBundle.getMessage(AUTopComponent.class, "CTL_AUTopComponent"));
      setToolTipText(NbBundle.getMessage(AUTopComponent.class, "HINT_AUTopComponent"));

   }

   /** This method is called from within the constructor to
    * initialize the form.
    * WARNING: Do NOT modify this code. The content of this method is
    * always regenerated by the Form Editor.
    */
      // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      jButton1 = new javax.swing.JButton();

      org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(AUTopComponent.class, "AUTopComponent.jButton1.text")); // NOI18N
      jButton1.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
         }
      });

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
      this.setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jButton1)
            .addContainerGap(317, Short.MAX_VALUE))
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jButton1)
            .addContainerGap(266, Short.MAX_VALUE))
      );
   }// </editor-fold>//GEN-END:initComponents

   private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      
      //AutoInstaller.searchNewAndUpdatedModules();
              
   }//GEN-LAST:event_jButton1ActionPerformed

      // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JButton jButton1;
   // End of variables declaration//GEN-END:variables
   @Override
   public void componentOpened() {
      // TODO add custom code on component opening
   }

   @Override
   public void componentClosed() {
      // TODO add custom code on component closing
   }

   void writeProperties(java.util.Properties p) {
      // better to version settings since initial version as advocated at
      // http://wiki.apidesign.org/wiki/PropertyFiles
      p.setProperty("version", "1.0");
      // TODO store your settings
   }

   void readProperties(java.util.Properties p) {
      String version = p.getProperty("version");
      // TODO read your settings according to their version
   }
}
