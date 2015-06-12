/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.runway.viewer;

import java.awt.BorderLayout;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.BeanTreeView;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//org.runway.viewer//RunWayViewer//EN",
        autostore = false)
@TopComponent.Description(
        preferredID = "RunWayViewerTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "explorer", openAtStartup = true)
@ActionID(category = "Window", id = "org.runway.viewer.RunWayViewerTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_RunWayViewerAction",
        preferredID = "RunWayViewerTopComponent")
@Messages({
    "CTL_RunWayViewerAction=RunWayViewer",
    "CTL_RunWayViewerTopComponent=RunWayViewer Window",
    "HINT_RunWayViewerTopComponent=This is a RunWayViewer window"
})
public final class RunWayViewerTopComponent extends TopComponent implements ExplorerManager.Provider {

    private ExplorerManager em = new ExplorerManager();

    public RunWayViewerTopComponent() {
        initComponents();
        setName(Bundle.CTL_RunWayViewerTopComponent());
        setToolTipText(Bundle.HINT_RunWayViewerTopComponent());

        Children runWayKids = Children.create(new RunWayChildFactory(), true);

        AbstractNode rootNode = new AbstractNode(runWayKids);

        em.setRootContext(rootNode);

        setLayout(new BorderLayout());

        BeanTreeView btv = new BeanTreeView();
        btv.setRootVisible(false);
        add(btv, BorderLayout.CENTER);

        associateLookup(ExplorerUtils.createLookup(em, getActionMap()));

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

    @Override
    public ExplorerManager getExplorerManager() {
        return em;
    }
}
