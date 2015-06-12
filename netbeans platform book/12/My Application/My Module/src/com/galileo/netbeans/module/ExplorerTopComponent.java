/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.galileo.netbeans.module;

import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.actions.CopyAction;
import org.openide.actions.CutAction;
import org.openide.actions.DeleteAction;
import org.openide.actions.PasteAction;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.BeanTreeView;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.nodes.Children;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//com.galileo.netbeans.module//Explorer//EN",
autostore = false)
@TopComponent.Description(preferredID = "ExplorerTopComponent",
iconBase = "com/galileo/netbeans/module/explorer.png",
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "explorer", openAtStartup = true)
@ActionID(category = "Window", id = "com.galileo.netbeans.module.ExplorerTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(displayName = "#CTL_ExplorerAction",
preferredID = "ExplorerTopComponent")
public final class ExplorerTopComponent extends TopComponent implements ExplorerManager.Provider {

   private final ExplorerManager manager = new ExplorerManager();
   private static final String ROOT_NODE    = "Explorer";

   public ExplorerTopComponent() {
      initComponents();
      initActions();
      initTree();
      associateLookup(ExplorerUtils.createLookup(manager, getActionMap()));

      setName(NbBundle.getMessage(ExplorerTopComponent.class, "CTL_ExplorerTopComponent"));
      setToolTipText(NbBundle.getMessage(ExplorerTopComponent.class, "HINT_ExplorerTopComponent"));

   }

   /** This method is called from within the constructor to
    * initialize the form.
    * WARNING: Do NOT modify this code. The content of this method is
    * always regenerated by the Form Editor.
    */
      // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      jScrollPane1 = new BeanTreeView();

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
      this.setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
      );
   }// </editor-fold>//GEN-END:initComponents

      // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JScrollPane jScrollPane1;
   // End of variables declaration//GEN-END:variables
   private void initActions() {
      getActionMap().put(CutAction.get(CutAction.class).getActionMapKey(), 
              ExplorerUtils.actionCut(manager));
      getActionMap().put(CopyAction.get(CopyAction.class).getActionMapKey(), 
              ExplorerUtils.actionCopy(manager));
      getActionMap().put(PasteAction.get(PasteAction.class).getActionMapKey(), 
              ExplorerUtils.actionPaste(manager));
      getActionMap().put(DeleteAction.get(DeleteAction.class).getActionMapKey(), 
              ExplorerUtils.actionDelete(manager, true));
   }

   private void initTree() {
      FileObject root = FileUtil.getConfigFile(ROOT_NODE);

      if(root != null) { /* folder found */
         manager.setRootContext(new ExplorerFolderNode(
                 root, 
                 Children.create(new ExplorerFolderFactory(root), false)));
      }
   }

   @Override
    protected void componentActivated() {
        ExplorerUtils.activateActions(manager, true);
    }
   @Override
    protected void componentDeactivated() {
        ExplorerUtils.activateActions(manager, false);
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
      return manager;
   }
}
