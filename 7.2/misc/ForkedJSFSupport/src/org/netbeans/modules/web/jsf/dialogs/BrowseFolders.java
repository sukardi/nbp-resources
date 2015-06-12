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

package org.netbeans.modules.web.jsf.dialogs;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import org.netbeans.api.project.SourceGroup;
import org.openide.util.NbBundle;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.view.BeanTreeView;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.loaders.DataFolder;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.FilterNode;

// XXX I18N

/**
 *
 * @author  phrebejk, mkuchtiak
 */
public class BrowseFolders extends javax.swing.JPanel implements ExplorerManager.Provider {
    
    private ExplorerManager manager;
    private SourceGroup[] folders;
    
    private static JScrollPane SAMPLE_SCROLL_PANE = new JScrollPane();
    
    /** Creates new form BrowseFolders */
    public BrowseFolders( SourceGroup[] folders) {
        initComponents();
        this.folders = folders;
        manager = new ExplorerManager();        
        AbstractNode rootNode = new AbstractNode( new SourceGroupsChildren( folders ) );
        manager.setRootContext( rootNode );
        
        // Create the templates view
        BeanTreeView btv = new BeanTreeView();
        btv.setRootVisible( false );
        btv.setSelectionMode( javax.swing.tree.TreeSelectionModel.SINGLE_TREE_SELECTION );
        btv.setBorder( SAMPLE_SCROLL_PANE.getBorder() );
        btv.getAccessibleContext().setAccessibleName(java.util.ResourceBundle.getBundle("org/netbeans/modules/web/jsf/dialogs/Bundle").getString("ACSN_FlodersTree"));
        btv.getAccessibleContext().setAccessibleDescription(java.util.ResourceBundle.getBundle("org/netbeans/modules/web/jsf/dialogs/Bundle").getString("ACSD_FlodersTree"));
        jLabel1.setLabelFor(btv.getViewport().getView());
        folderPanel.add( btv, java.awt.BorderLayout.CENTER );
    }
        
    // ExplorerManager.Provider implementation ---------------------------------
    
    public ExplorerManager getExplorerManager() {
        return manager;
    }
        
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();
        folderPanel = new javax.swing.JPanel();

        setLayout(new java.awt.GridBagLayout());

        getAccessibleContext().setAccessibleDescription(java.util.ResourceBundle.getBundle("org/netbeans/modules/web/jsf/dialogs/Bundle").getString("ACSD_BrowseFiles"));

        setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(12, 12, 12, 12)));
        jLabel1.setText(org.openide.util.NbBundle.getMessage(BrowseFolders.class, "LBL_Folders"));
        jLabel1.setDisplayedMnemonic(java.util.ResourceBundle.getBundle("org/netbeans/modules/web/jsf/dialogs/Bundle").getString("MNE_Folders").charAt(0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 2, 0);
        add(jLabel1, gridBagConstraints);

        folderPanel.setLayout(new java.awt.BorderLayout());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(folderPanel, gridBagConstraints);

    }//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel folderPanel;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
        
    public static FileObject showDialog( SourceGroup[] folders ) {
        
        BrowseFolders bf = new BrowseFolders( folders );
        
        JButton options[] = new JButton[] { 
            //new JButton( NbBundle.getMessage( BrowseFolders.class, "LBL_BrowseFolders_Select_Option") ), // NOI18N
            //new JButton( NbBundle.getMessage( BrowseFolders.class, "LBL_BrowseFolders_Cancel_Option") ), // NOI18N
            new JButton( NbBundle.getMessage(BrowseFolders.class,"LBL_SelectFile")), 
            new JButton( NbBundle.getMessage(BrowseFolders.class,"LBL_Cancel") ), 
        };
                
        OptionsListener optionsListener = new OptionsListener( bf );
        
        options[ 0 ].setActionCommand( OptionsListener.COMMAND_SELECT );
        options[ 0 ].addActionListener( optionsListener );
        options[ 0 ].getAccessibleContext().setAccessibleDescription(java.util.ResourceBundle.getBundle("org/netbeans/modules/web/jsf/dialogs/Bundle").getString("ACSD_SelectFile"));
        options[ 1 ].setActionCommand( OptionsListener.COMMAND_CANCEL );
        options[ 1 ].addActionListener( optionsListener );    
        options[ 1 ].getAccessibleContext().setAccessibleDescription(java.util.ResourceBundle.getBundle("org/netbeans/modules/web/jsf/dialogs/Bundle").getString("ACSD_Cancel"));
        
        DialogDescriptor dialogDescriptor = new DialogDescriptor( 
            bf,                                     // innerPane
            NbBundle.getMessage(BrowseFolders.class, "LBL_BrowseFiles"), // displayName
            true,                                   // modal
            options,                                // options
            options[ 0 ],                           // initial value
            DialogDescriptor.BOTTOM_ALIGN,          // options align
            null,                                   // helpCtx
            null );                                 // listener 

        dialogDescriptor.setClosingOptions( new Object[] { options[ 0 ], options[ 1 ] } );
            
        Dialog dialog = DialogDisplayer.getDefault().createDialog( dialogDescriptor );
        dialog.setVisible(true);
        
        return optionsListener.getResult();
                
    }
    
    
    // Innerclasses ------------------------------------------------------------
    
    /** Children to be used to show FileObjects from given SourceGroups
     */
	 
    private final class SourceGroupsChildren extends Children.Keys {
        
        private SourceGroup[] groups;
        private SourceGroup group;
        private FileObject fo;
        
        public SourceGroupsChildren( SourceGroup[] groups ) {
            this.groups = groups;
        }
        
        public SourceGroupsChildren( FileObject fo, SourceGroup group ) {            
            this.fo = fo;
            this.group = group;
        }
        
        protected void addNotify() {
            super.addNotify();
            setKeys( getKeys() );
        }
        
        protected void removeNotify() {
            setKeys( Collections.EMPTY_SET );
            super.removeNotify();
        }
        
        protected Node[] createNodes(Object key) {
            
            FileObject fObj = null;
            SourceGroup group = null;
            boolean isFile=false;
            
            if ( key instanceof SourceGroup ) {
                fObj = ((SourceGroup)key).getRootFolder();
                group = (SourceGroup)key;
            }
            else if ( key instanceof Key ) {
                fObj = ((Key)key).folder;
                group = ((Key)key).group;
                if (!fObj.isFolder()) isFile=true;
            }

            try {
                DataObject dobj = DataObject.find( fObj );
                FilterNode fn = (isFile?new SimpleFilterNode(dobj.getNodeDelegate(),Children.LEAF):
                                        new SimpleFilterNode(dobj.getNodeDelegate(), new SourceGroupsChildren( fObj, group )));
                if ( key instanceof SourceGroup ) {
                    fn.setDisplayName( group.getDisplayName() );
                }
            
                return new Node[] { fn };            
            }
            catch ( DataObjectNotFoundException e ) {
                return null;
            }
        }
                
        private Collection getKeys() {
			
            if ( groups != null ) {
                return Arrays.asList( groups );                
            }
            else {
                FileObject files[] = fo.getChildren();
                Arrays.sort(files,new BrowseFolders.FileObjectComparator());
                ArrayList children = new ArrayList( files.length );
                /*
                if (BrowseFolders.this.target==org.openide.loaders.DataFolder.class)
                    for( int i = 0; i < files.length; i++ ) {
                        if ( files[i].isFolder() && group.contains( files[i] ) ) {
                            children.add( new Key( files[i], group ) );
                        }
                    }*/
                //else {
                    // add folders
                    for( int i = 0; i < files.length; i++ ) {
                        if ( group.contains( files[i]) && files[i].isFolder() ) children.add( new Key( files[i], group ) );
                    }
                    // add files
                    for( int i = 0; i < files.length; i++ ) {
                        if ( group.contains( files[i]) && !files[i].isFolder() ) children.add( new Key( files[i], group ) );
                    }
                //}
                
                return children;
            }
			
        }
        
        private class Key {
            
            private FileObject folder;
            private SourceGroup group;
            
            private Key ( FileObject folder, SourceGroup group ) {
                this.folder = folder;
                this.group = group;
            }
            
            
        }
        
    }

    private class FileObjectComparator implements java.util.Comparator {
        public int compare(Object o1, Object o2) {
            FileObject fo1 = (FileObject)o1;
            FileObject fo2 = (FileObject)o2;
            return fo1.getName().compareTo(fo2.getName());
        }
    }
    
    private static final class OptionsListener implements ActionListener {
    
        public static final String COMMAND_SELECT = "SELECT"; //NOI18N
        public static final String COMMAND_CANCEL = "CANCEL"; //NOI18N
            
        private BrowseFolders browsePanel;
        
        private FileObject result;
        //private Class target;
        
        public OptionsListener( BrowseFolders browsePanel ) {
            this.browsePanel = browsePanel;
        }
        
        public void actionPerformed( ActionEvent e ) {
            String command = e.getActionCommand();

            if ( COMMAND_SELECT.equals( command ) ) {
                Node selection[] = browsePanel.getExplorerManager().getSelectedNodes();
                
                if ( selection != null && selection.length > 0 ) {
                    DataObject dobj = (DataObject)selection[0].getLookup().lookup( DataObject.class );
                    //if (dobj!=null && dobj.getClass().isAssignableFrom(target)) {
                        result = dobj.getPrimaryFile();
                    //}
                    /*
                    if ( dobj != null ) {
                        FileObject fo = dobj.getPrimaryFile();
                        if ( fo.isFolder() ) {
                            result = fo;
                        }
                    }
                    */
                }
                
                
            }
        }
        
        public FileObject getResult() {
            return result;
        }
    }
    
    class SimpleFilterNode extends FilterNode {
        
        public SimpleFilterNode(org.openide.nodes.Node node, org.openide.nodes.Children children) {
            super(node, children);
            
        }
        
        public org.openide.util.actions.SystemAction[] getActions() {
            return new org.openide.util.actions.SystemAction[]{};
        }
        public org.openide.util.actions.SystemAction getDefaultAction() {
           return null;
        }
    }
    
}
