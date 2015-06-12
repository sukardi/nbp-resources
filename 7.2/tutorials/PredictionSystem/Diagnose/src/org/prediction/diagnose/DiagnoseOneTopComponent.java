/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.prediction.diagnose;

import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
    dtd = "-//org.prediction.diagnose//DiagnoseOne//EN",
autostore = false)
@TopComponent.Description(
    preferredID = "DiagnoseOneTopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(roles={"diagnose"}, mode = "properties", openAtStartup = true)
@Messages({
    "CTL_DiagnoseOneAction=DiagnoseOne",
    "CTL_DiagnoseOneTopComponent=DiagnoseOne Window",
    "HINT_DiagnoseOneTopComponent=This is a DiagnoseOne window"
})
public final class DiagnoseOneTopComponent extends TopComponent {

    public DiagnoseOneTopComponent() {
        initComponents();
        setName(Bundle.CTL_DiagnoseOneTopComponent());
        setToolTipText(Bundle.HINT_DiagnoseOneTopComponent());

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
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
