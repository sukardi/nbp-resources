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
import java.io.File;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.netbeans.api.annotations.common.NonNull;
import org.netbeans.api.annotations.common.NullAllowed;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.SourceGroup;
import static org.netbeans.modules.project.ui.Bundle.*;
import org.netbeans.modules.project.uiapi.ProjectChooserFactory;
import org.netbeans.spi.project.ui.templates.support.Templates;
import org.openide.WizardDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileStateInvalidException;
import org.openide.filesystems.FileSystem;
import org.openide.filesystems.FileUtil;
import org.openide.util.ChangeSupport;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle.Messages;

/**
 *
 * @author  Petr Hrebejk
 */
final class SimpleTargetChooserPanel implements WizardDescriptor.Panel<WizardDescriptor>, ChangeListener {

    private final ChangeSupport changeSupport = new ChangeSupport(this);
    private SimpleTargetChooserPanelGUI gui;
    private boolean includesTemplatesWithProject = true;

    @NullAllowed
    private Project project;
    @NonNull
    private SourceGroup[] folders;
    private WizardDescriptor.Panel<WizardDescriptor> bottomPanel;
    private WizardDescriptor wizard;
    private boolean isFolder;
    private boolean freeFileExtension;
    private FileSystem fs;

    
    @SuppressWarnings("LeakingThisInConstructor")
    SimpleTargetChooserPanel(@NullAllowed Project project, @NonNull SourceGroup[] folders,
            WizardDescriptor.Panel<WizardDescriptor> bottomPanel, boolean isFolder, boolean freeFileExtension) {
        this.folders = folders;
        this.project = project;
        this.bottomPanel = bottomPanel;
        if ( bottomPanel != null ) {
            bottomPanel.addChangeListener( this );
        }
        this.isFolder = isFolder;
        this.freeFileExtension = freeFileExtension;
        this.gui = null;
    }

    public @Override Component getComponent() {
        if (noProjectFolders()) {
            return new JPanel();
        }
        if (gui == null) {
            gui = new SimpleTargetChooserPanelGUI(project, folders, bottomPanel == null ? null : bottomPanel.getComponent(), isFolder, freeFileExtension);
            gui.addChangeListener(this);
        }
        return gui;
    }

    private boolean noProjectFolders() { // #202410
        // if the project is null then any folder on the system is available
        return project != null && folders != null && folders.length == 0;
    }

    public @Override HelpCtx getHelp() {
        if ( bottomPanel != null ) {
            HelpCtx bottomHelp = bottomPanel.getHelp();
            if ( bottomHelp != null ) {
                return bottomHelp;
            }
        }
        
        //XXX
        return null;
        
    }

    public @Override boolean isValid() {
        if (noProjectFolders()) {
            return false;
        }

        boolean ok = ( gui != null && gui.getTargetName() != null &&
               ( bottomPanel == null || bottomPanel.isValid() ) );
        
        if (!ok) {
            return false;
        }
        
        // check if the file name can be created
        FileObject template = Templates.getTemplate( wizard );
        assert template != null : "Null template in wizard:" + wizard.getClass().getName() + ", prop WIZARD_KEY_TEMPLATE:" + wizard.getProperty( ProjectChooserFactory.WIZARD_KEY_TEMPLATE ) + ", thread:" + Thread.currentThread().getName();
        FileObject rootFolder;
        String targetFolder;
        if (gui.getTargetGroup() != null) {
            rootFolder = gui.getTargetGroup().getRootFolder();
            targetFolder = gui.getTargetFolder();
        }
        else if (gui.getTargetFolder() != null) {
            //this line will return null for non-existing folders
            //FileUtil.toFileObject(FileUtil.normalizeFile(new File(gui.getTargetFolder())))
            //that's suboptimal but makes the handling of the "outside of project" case easier.
            //ideally we would traverse the file structure up until we find an existing folder and then compute the non-existing part
            rootFolder =  fs == null ? FileUtil.toFileObject(FileUtil.normalizeFile(new File(gui.getTargetFolder()))) : fs.getRoot(); // NOI18N
            targetFolder = fs == null ? "" : gui.getTargetFolder();
        }
        else {
            rootFolder = null;
            targetFolder = null;
        }

        String errorMessage = ProjectUtilities.canUseFileName(rootFolder,
                targetFolder, gui.getTargetName(), template.getExt(), isFolder, freeFileExtension);
        wizard.putProperty(WizardDescriptor.PROP_ERROR_MESSAGE, errorMessage);

        return errorMessage == null;
    }

    public @Override void addChangeListener(ChangeListener l) {
        changeSupport.addChangeListener(l);
    }

    public @Override void removeChangeListener(ChangeListener l) {
        changeSupport.removeChangeListener(l);
    }

    @Messages({
        "SimpleTargetChooserPanelGUI_no_source_folders=No source folders in project; perhaps it has been deleted?",
        "LBL_TemplatesPanel_Name=Choose File Type"
    })
    @Override public void readSettings(WizardDescriptor settings) {
        Boolean b = (Boolean) settings.getProperty(NewFileWizard.INCLUDES_TEMPLATES_WITH_PROJECTS);
        if (b != null && b.equals(Boolean.FALSE)) {
            includesTemplatesWithProject = false;
        }
        wizard = settings;
                
        if (noProjectFolders()) {
            wizard.putProperty(WizardDescriptor.PROP_ERROR_MESSAGE, SimpleTargetChooserPanelGUI_no_source_folders());
            return;
        }

        if ( gui == null ) {
            getComponent();
        }
        
        // Try to preselect a folder            
        FileObject preselectedTarget = Templates.getTargetFolder( wizard );
        if (preselectedTarget == null) {
            if (project != null) {
                preselectedTarget = project.getProjectDirectory();
            }
            else {
                String home = System.getProperty("user.home");
                if (home != null && new File(home).isDirectory()) {
                    preselectedTarget = FileUtil.toFileObject(FileUtil.normalizeFile(new File(home)));
                }
            }
        }
        // Try to preserve the already entered target name
        String targetName = isFolder ? null : Templates.getTargetName( wizard );
        // Init values
        gui.initValues( Templates.getTemplate( wizard ), preselectedTarget, targetName, includesTemplatesWithProject );
        //only when we don't have a file from local filesystem
        if (preselectedTarget != null && FileUtil.toFile(preselectedTarget) == null) {
            try {
                fs = preselectedTarget.getFileSystem();
            } catch (FileStateInvalidException ex) {
                fs = null;
            }
        }        
        // XXX hack, TemplateWizard in final setTemplateImpl() forces new wizard's title
        // this name is used in NewFileWizard to modify the title
        Object substitute = gui.getClientProperty ("NewFileWizard_Title"); // NOI18N
        if (substitute != null) {
            wizard.putProperty ("NewFileWizard_Title", substitute); // NOI18N
        }
        
        wizard.putProperty(WizardDescriptor.PROP_CONTENT_DATA, new String[] {LBL_TemplatesPanel_Name(), LBL_SimpleTargetChooserPanel_Name()});
            
        if ( bottomPanel != null ) {
            bottomPanel.readSettings( settings );
        }
    }
    
    public @Override void storeSettings(WizardDescriptor settings) {
        if (noProjectFolders()) {
            return;
        }

        if (WizardDescriptor.PREVIOUS_OPTION.equals(settings.getValue())) {
            return;
        }
        if(!WizardDescriptor.CANCEL_OPTION.equals(settings.getValue())
                && !WizardDescriptor.CLOSED_OPTION.equals(settings.getValue()) && isValid()) {
            if ( bottomPanel != null ) {
                bottomPanel.storeSettings( settings );
            }
            if ( gui == null ) {
                getComponent();
            }
            String name = gui.getTargetName ();
            if (name != null && name.indexOf ('/') > 0) { // NOI18N
                name = name.substring (name.lastIndexOf ('/') + 1);
            }
            
            Templates.setTargetFolder(settings, getTargetFolderFromGUI());
            Templates.setTargetName(settings, name);
        }
        settings.putProperty("NewFileWizard_Title", null); // NOI18N
    }

    public @Override void stateChanged(ChangeEvent e) {
        changeSupport.fireChange();
    }
    
    private FileObject getTargetFolderFromGUI () {
        FileObject rootFolder;
        String folderName;
        if (gui.getTargetGroup() != null) {
            rootFolder = gui.getTargetGroup().getRootFolder();
            folderName = gui.getTargetFolder();
        }
        else if (gui.getTargetFolder() != null) {
//            rootFolder = FileUtil.toFileObject(FileUtil.normalizeFile(new File(gui.getTargetFolder())));
//            folderName = "";
            rootFolder =  fs == null ? FileUtil.toFileObject(FileUtil.normalizeFile(new File(gui.getTargetFolder()))) : fs.getRoot(); // NOI18N
            folderName = fs == null ? "" : gui.getTargetFolder();
        }
        else {
            rootFolder = null;
            folderName = null;
        }

        String newObject = gui.getTargetName ();
        
        if (newObject.indexOf ('/') > 0) { // NOI18N
            String path = newObject.substring (0, newObject.lastIndexOf ('/')); // NOI18N
            folderName = folderName == null || "".equals (folderName) ? path : folderName + '/' + path; // NOI18N
        }

        FileObject targetFolder;
        if ( folderName == null ) {
            targetFolder = rootFolder;
        }
        else {            
            targetFolder = rootFolder.getFileObject( folderName );
        }

        if ( targetFolder == null ) {
            // XXX add deletion of the file in uninitalize ow the wizard
            try {
                targetFolder = FileUtil.createFolder( rootFolder, folderName );
            } catch (IOException ioe) {
                // Can't create the folder
                throw new IllegalArgumentException(ioe); // ioe already annotated
            }
        }
        
        return targetFolder;
    }
}
