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

package org.netbeans.modules.projectimport.eclipse.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectManager;
import org.netbeans.modules.projectimport.eclipse.core.spi.ProjectImportModel;
import org.netbeans.modules.projectimport.eclipse.core.spi.ProjectTypeUpdater;
import org.openide.WizardDescriptor;
import org.openide.filesystems.FileUtil;
import org.openide.util.Mutex;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;
import org.openide.util.Task;

/**
 * Able to import given Eclipse projects in separate thread with providing
 * information about current state(progress). Converts eclipse projects and
 * their required projects into NetBeans ones and stores them into the given
 * destination.
 *
 * @author mkrauskopf
 */
final class Importer {
    
    /** Logger for this class. */
    private static final Logger logger = Logger.getLogger(Importer.class.getName());

    private final List<EclipseProject> eclProjects;
    private final String destination;
    private final List<Project> nbProjects;
    
    private int nOfProcessed;
    private String progressInfo;
    private List<String> warnings = new ArrayList<String>();
    
    private Task task = null;
    
    private List<WizardDescriptor.Panel<WizardDescriptor>> extraPanels;
    
    /**
     * 
     * @param eclProjects list of eclipse projects to import
     * @param destination destination location for NetBeans projects; can be null
     *  in which case NetBeans projects should be generated to the same folder as 
     *  eclipse projects
     */
    Importer(final List<EclipseProject> eclProjects, String destination, List<WizardDescriptor.Panel<WizardDescriptor>> extraPanels) {
        this.eclProjects = eclProjects;
        this.destination = destination;
        this.nbProjects = new ArrayList<Project>();
        this.extraPanels = extraPanels;
}
    
    /**
     * Starts importing process in separated thread. Use getters to obtain
     * information about current progress.
     */
    void startImporting() {
        task = RequestProcessor.getDefault().post(new Runnable() {
            public void run() {
                for (EclipseProject eclPrj : eclProjects) {
                    Project p = importProject(eclPrj, warnings);
                    if (p != null) {
                        nbProjects.add(p);
                    }
                }
            }
        });
    }
    
    /**
     * Returns number of already processed projects.
     */
    int getNOfProcessed() {
        return nOfProcessed;
    }
    
    /**
     * Returns localized message describing current importer activity.
     */
    String getProgressInfo() {
        return progressInfo;
    }
    
    /**
     * Returns whether importer has finished.
     */
    boolean isDone() {
        return task != null && task.isFinished();
    }
    
    List<String> getWarnings() {
        return warnings;
    }
    
    /**
     * Gets imported projects. Call after the importing <code>isDone()</code>.
     */
    Project[] getProjects() {
        return nbProjects.toArray(new Project[nbProjects.size()]);
    }
    
    private Project importProject(final EclipseProject eclProject, final List<String> importProblems) {
        assert eclProject != null : "Eclipse project cannot be null"; // NOI18N

        final List<String> projectImportProblems = new ArrayList<String>();

        // add problems which appeared during project opening/parsing
        projectImportProblems.addAll(eclProject.getImportProblems());

        try {
            /// import in two separate write locks to allow for events being
            // distributed after global properties were updated in stage0
            Boolean res = ProjectManager.mutex().writeAccess(new Mutex.Action<Boolean>() {
                public Boolean run() {
                    try {
                        importProjectStage0(eclProject, projectImportProblems);
                    } catch (Throwable ex) {
                        logger.log(Level.SEVERE, "import of Eclipse project "+eclProject.getDirectory().getPath()+" failed", ex); // NOI18N
                        projectImportProblems.add(org.openide.util.NbBundle.getMessage(Importer.class, "MSG_ImportFailed", ex.getMessage()));
                        return Boolean.FALSE;
                    }
                    return Boolean.TRUE;
                }});
            if (!res.booleanValue()) {
                return null;
            }

            return ProjectManager.mutex().writeAccess(new Mutex.Action<Project>() {
                public Project run() {
                    try {
                        return importProjectStage1(eclProject, importProblems, projectImportProblems);
                    } catch (Throwable ex) {
                        logger.log(Level.SEVERE, "import of Eclipse project "+eclProject.getDirectory().getPath()+" failed", ex); // NOI18N
                        projectImportProblems.add(org.openide.util.NbBundle.getMessage(Importer.class, "MSG_ImportFailed", ex.getMessage()));
                        return null;
                    }
                }});
        } finally {
            if (projectImportProblems.size() > 0) {
                importProblems.add(org.openide.util.NbBundle.getMessage(Importer.class, "MSG_ImportResults", eclProject.getName()));
                for (String s : projectImportProblems) {
                    importProblems.add(" "+s); //NOI18N
                }
            }
        }
    }
    
    private void importProjectStage0(EclipseProject eclProject, List<String> projectImportProblems) throws IOException {
        // resolve classpath containers
        eclProject.resolveContainers(projectImportProblems, true);
        
        // create ENV variables in build.properties
        eclProject.setupEnvironmentVariables(projectImportProblems);
        
        // remove invalid source roots:
        eclProject.removeInvalidSourceRoots(projectImportProblems);
    }
        
    private Project importProjectStage1(EclipseProject eclProject, List<String> importProblems, List<String> projectImportProblems) throws IOException {
        nOfProcessed++;
        progressInfo = NbBundle.getMessage(Importer.class,
                "MSG_Progress_ProcessingProject", eclProject.getName()); // NOI18N
        
        File dest;
        Project alreadyImported = null;
        if (destination == null) {
            dest = eclProject.getDirectory();
        } else {
            dest = FileUtil.normalizeFile(new File(destination, eclProject.getDirectory().getName()));
        }
        if (dest.exists()) {
            alreadyImported = ProjectManager.getDefault().findProject(FileUtil.toFileObject(dest));
        }
        ProjectImportModel model = new ProjectImportModel(eclProject, dest, 
                JavaPlatformSupport.getJavaPlatformSupport().getJavaPlatform(eclProject, projectImportProblems), 
                nbProjects, extraPanels);
        Project p;
        if (alreadyImported != null) {
            p = alreadyImported;
            projectImportProblems.add(org.openide.util.NbBundle.getMessage(Importer.class, "MSG_AlreadyImportedProjectFound"));
        } else {
            if (!eclProject.isImportSupported()) {
                importProblems.add(org.openide.util.NbBundle.getMessage(Importer.class, "MSG_UnknownProject"));
                return null;
            }
            p = eclProject.getProjectTypeFactory().createProject(model, projectImportProblems);
        
            if (p != null && eclProject.getProjectTypeFactory() instanceof ProjectTypeUpdater) {
                ProjectTypeUpdater updater = (ProjectTypeUpdater)eclProject.getProjectTypeFactory();
                String key = updater.calculateKey(model);
                EclipseProjectReference ref = new EclipseProjectReference(p, 
                        eclProject.getDirectory().getAbsolutePath(), 
                        eclProject.getWorkspace() != null ? eclProject.getWorkspace().getDirectory().getAbsolutePath() : null, 0, key);
                EclipseProjectReference.write(p, ref);
                ProjectManager.getDefault().saveProject(p);
            }
            if (p != null) {
                // type: java / web
                // naked(false) or workspace(true)
                // imported to separate folder(false) or eclipse project folder(true)
                // number of import issues
                // TODO: add eg. eclipse version  (not available now)
                Util.logUsage(Importer.class, "USG_PROJECT_ECLIPSE_IMPORT",  // NOI18N
                        eclProject.getProjectTypeFactory().getProjectTypeName(),
                        eclProject.getWorkspace() != null ? "true" : "false", // NOI18N
                        destination == null ? "true" : "false", // NOI18N
                        projectImportProblems.size());
            }
        }
        return p;
    }
    
    private void logWarning(String message) {
        logWarning(message, false);
    }
    
    /**
     * Delegates to ErrorManager. When the <code>isGUIWarning</code> is true,
     * the warning will be also shown to the user after importing is done.
     */
    private void logWarning(String message, boolean isGUIWarning) {
        if (isGUIWarning) {
            warnings.add(message);
        }
        logger.warning(message);
    }

}
