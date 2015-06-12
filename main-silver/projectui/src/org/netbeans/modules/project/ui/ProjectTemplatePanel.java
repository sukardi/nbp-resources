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

package org.netbeans.modules.project.ui;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import org.netbeans.modules.project.ui.api.ProjectTemplates;
import org.openide.ErrorManager;
import org.openide.WizardDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.loaders.TemplateWizard;
import org.openide.nodes.Children;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;
import org.openide.util.AsyncGUIJob;
import org.openide.util.ChangeSupport;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;

/**
 * Wizard panel for New Project.
 * @author  tom
 */
public class ProjectTemplatePanel implements WizardDescriptor.Panel<WizardDescriptor> {
    
    private final ChangeSupport changeSupport = new ChangeSupport(this);
    private TemplatesPanelGUI panel;
    private WarmupJob warmUp;
    private boolean warmUpActive;
    private boolean needsReselect = false;   // WelcomeScreen hack, XXX Delete after WS is redesigned
    private WizardDescriptor wizard;
        
    /** Creates a new instance of ProjectTemplatePanel */
    public ProjectTemplatePanel() {
    }
    
    @Override
    public void readSettings(WizardDescriptor settings) {
        this.wizard = settings;
        panel.setWizardDescriptor(wizard);
        TemplateWizard wd = (TemplateWizard) settings;
        wd.putProperty (WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, new Integer (0));
        wd.putProperty (WizardDescriptor.PROP_CONTENT_DATA, new String[] {
                NbBundle.getBundle (ProjectTemplatePanel.class).getString ("LBL_TemplatesPanel_Name"), // NOI18N
                NbBundle.getBundle (ProjectTemplatePanel.class).getString ("LBL_TemplatesPanel_Dots")}); // NOI18N
        FileObject templatesFolder = (FileObject) wd.getProperty (TemplatesPanelGUI.TEMPLATES_FOLDER);
        
        // WelcomeScreen hack, XXX Delete after WS is redesigned
        String preselectedCategory = (String) wd.getProperty(ProjectTemplates.PRESELECT_CATEGORY);
        if ( templatesFolder != null && templatesFolder.isFolder() && 
            ( wd.getTemplate() == null || preselectedCategory != null || needsReselect ) ) {
            
            String preselectedTemplate = (String) wd.getProperty(ProjectTemplates.PRESELECT_TEMPLATE);
            String template;
            String selectedCategory = OpenProjectListSettings.getInstance().getLastSelectedProjectCategory ();
            String selectedTemplate = OpenProjectListSettings.getInstance().getLastSelectedProjectType ();

            if (preselectedTemplate == null) {
                template = preselectedCategory != null ? null : selectedTemplate;
            } else {
                template = preselectedCategory != null ? preselectedTemplate : selectedTemplate;
            }
            
            TemplatesPanelGUI p = (TemplatesPanelGUI) this.getComponent();
            if (isWarmUpActive()) {
                WarmupJob wup = getWarmUp();
                wup.setTemplatesFolder (templatesFolder);
                wup.setSelectedCategory( preselectedCategory != null ? preselectedCategory : selectedCategory );
                wup.setSelectedTemplate( template );
            }
            else {
                p.setTemplatesFolder(templatesFolder);
                p.setSelectedCategoryByName (preselectedCategory != null ? preselectedCategory : selectedCategory);
                p.setSelectedTemplateByName (template);
            }

        }
        // bugfix #44792: project wizard title always changes
        wd.putProperty("NewProjectWizard_Title", null); // NOI18N
    }
    
    @Override
    public void storeSettings(WizardDescriptor settings) {
        TemplateWizard wd = (TemplateWizard) settings;
        
        // WelcomeScreen hack, XXX Delete after WS is redesigned
        String preselectedCategory = (String) wd.getProperty(ProjectTemplates.PRESELECT_CATEGORY);

        TemplatesPanelGUI gui = (TemplatesPanelGUI)this.getComponent();
        FileObject fo = gui.getSelectedTemplate();
        if (fo != null && fo.isValid()) {
            try {
                wd.setTemplate (DataObject.find(fo));
            } catch (DataObjectNotFoundException e) {
                ErrorManager.getDefault().notify(e);
            }
        }
        if ( preselectedCategory == null ) {

            String path = gui.getSelectedCategoryName();
            if (path != null) {
                OpenProjectListSettings.getInstance().setLastSelectedProjectCategory(path);
            }
            path = gui.getSelectedTemplateName();
            if (path != null) {
                OpenProjectListSettings.getInstance().setLastSelectedProjectType (path);
            }
            needsReselect = false;
        }
        else {
            needsReselect = true;
        }
    }
    
    @Override
    public void addChangeListener(ChangeListener l) {
        changeSupport.addChangeListener(l);
    }
    
    @Override
    public void removeChangeListener(ChangeListener l) {
        changeSupport.removeChangeListener(l);
    }
    
    @Override
    public boolean isValid() {
        return ((TemplatesPanelGUI)this.getComponent()).getSelectedTemplate() != null;
    }
    
    @Override
    public HelpCtx getHelp() {
        return new HelpCtx( ProjectTemplatePanel.class );
    }
    
    @Override
    public synchronized Component getComponent() {        
        if (this.panel == null) {
            TemplatesPanelGUI.Builder firer = new Builder();
            this.panel = new TemplatesPanelGUI (firer);
            panel.setWizardDescriptor(wizard);
            Utilities.attachInitJob (panel, getWarmUp());
            this.warmUpActive = true;
            this.panel.setName (NbBundle.getBundle (ProjectTemplatePanel.class).getString ("LBL_TemplatesPanel_Name")); // NOI18N
        }
        return this.panel;
    }


    private synchronized WarmupJob getWarmUp () {
        if (this.warmUp == null) {
            this.warmUp = new WarmupJob();
        }
        return this.warmUp;
    }

    private synchronized boolean isWarmUpActive () {
        return warmUpActive;
    }

    private static class CategoriesChildren extends Children.Keys<DataObject> {
        
        private DataFolder root;
        private final String filterText;
                
        public CategoriesChildren (DataFolder folder, String filterText) {
            this.root = folder;
            this.filterText = filterText;
        }
        
        @Override
        protected void addNotify () {
            setKeys(root.getChildren());
        }
        
        @Override
        protected void removeNotify () {
            this.setKeys(new DataObject[0]);
        }
        
        @Override
        protected Node[] createNodes(DataObject dobj) {
            if (dobj instanceof DataFolder) {
                DataFolder folder = (DataFolder) dobj;
                int type = 0;   //Empty folder or File folder
                for (DataObject child : folder.getChildren()) {
                    type = 1;
                    if (Boolean.TRUE.equals(child.getPrimaryFile().getAttribute("template"))) { // NOI18N
                        continue;
                    }
                    if (child.getPrimaryFile().isFolder()) {
                        type = 2;   //Folder folder
                        break;
                    }
                }
                if (type == 1) {
                    Node categoryNode = new FilterNode(dobj.getNodeDelegate(), Children.LEAF);
                    boolean hasFilteredChildren = false;
                    for( DataObject child : folder.getChildren() ) {
                        if( child.isTemplate() ) {
                            if( null == filterText || child.getNodeDelegate().getDisplayName().toLowerCase().contains( filterText.toLowerCase() ) ) {
                                hasFilteredChildren = true;
                                break;
                            }
                        }
                    }
                    if( hasFilteredChildren )
                        return new Node[] { categoryNode };
                } else if (type == 2) {
                    return new Node[] {
                        new FilterNode(dobj.getNodeDelegate(), new CategoriesChildren((DataFolder)dobj, filterText))
                    };
                }
            }
            return new Node[0];
        }                
    }
    
    private static class TemplateChildren extends Children.Keys<DataObject> {
        
        private DataFolder folder;
        private final String filterText;
                
        public TemplateChildren (DataFolder folder, String filterText) {
            this.folder = folder;
            this.filterText = filterText;
        }
        
        @Override
        protected void addNotify () {
            this.setKeys (this.folder.getChildren ());
        }
        
        @Override
        protected void removeNotify () {
            this.setKeys(new DataObject[0]);
        }
        
        @Override
        protected Node[] createNodes(DataObject dobj) {
            if (dobj.isTemplate()) {
                Node templateNode = new FilterNode(dobj.getNodeDelegate(), Children.LEAF);
                if( null == filterText || templateNode.getDisplayName().toLowerCase().contains( filterText.toLowerCase() ) )
                    return new Node[] { templateNode };
            }
            return new Node[0];
        }        
        
    }
    
    private class WarmupJob implements AsyncGUIJob {

        private FileObject templatesFolder;
        private String category;
        private String template;

        @Override
        public void construct () {
            panel.warmUp (this.templatesFolder);
        }
        
        @Override
        public void finished () {
            Cursor cursor = null;
            try {
                cursor = panel.getCursor();
                panel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                panel.doFinished (this.templatesFolder, this.category, this.template);
            } finally {
                if (cursor != null) {
                    panel.setCursor (cursor);
                }
                synchronized(ProjectTemplatePanel.this) {
                    warmUpActive = false;
                }
            }
        }

        void setTemplatesFolder (FileObject fo) {
            this.templatesFolder = fo;
        }

        void setSelectedCategory (String s) {
            this.category = s;
        }

        void setSelectedTemplate (String s) {
            this.template = s;
        }
    }
    
    private class Builder implements TemplatesPanelGUI.Builder {

        @Override
        public org.openide.nodes.Children createCategoriesChildren (DataFolder folder, String filterText) {
            assert folder != null : "Folder cannot be null.";  //NOI18N
            return new CategoriesChildren (folder, filterText);
        }

        @Override
        public org.openide.nodes.Children createTemplatesChildren(DataFolder folder, String filterText) {
            return new TemplateChildren (folder, filterText);
        }


        @Override
        public String getCategoriesName() {
            return NbBundle.getMessage(ProjectTemplatePanel.class,"CTL_Categories");
        }


        @Override
        public String getTemplatesName() {
            return NbBundle.getMessage(ProjectTemplatePanel.class,"CTL_Projects");
        }

        @Override
        public void fireChange() {
            changeSupport.fireChange();
        }

        @Override
        public void actionPerformed( ActionEvent e ) {
            if( null != wizard ) {
                wizard.doNextClick();
            }
        }
    }
}
