/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2010 Oracle and/or its affiliates. All rights reserved.
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
 *
 * Contributor(s):
 *
 * Portions Copyrighted 2008 Sun Microsystems, Inc.
 */
package org.netbeans.modules.maven.cos;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.maven.model.Resource;
import org.codehaus.plexus.util.DirectoryScanner;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.modules.maven.api.FileUtilities;
import org.netbeans.modules.maven.api.NbMavenProject;
import org.netbeans.modules.maven.api.execute.RunUtils;
import org.netbeans.modules.maven.spi.cos.AdditionalDestination;
import org.openide.filesystems.FileChangeAdapter;
import org.openide.filesystems.FileEvent;
import org.openide.filesystems.FileLock;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileRenameEvent;
import org.openide.filesystems.FileUtil;
import org.openide.util.RequestProcessor;
import org.openide.util.Utilities;

/**
 * @author mkleint
 */
public class CopyResourcesOnSave extends FileChangeAdapter {

    private static final RequestProcessor RP = new RequestProcessor(CopyResourcesOnSave.class);
    private static final Logger LOG = Logger.getLogger(CopyResourcesOnSave.class.getName());
    
    private final NbMavenProject nbproject;
    private final Set<File> resourceUris = new HashSet<File>();
    private final Project project;
    private final PropertyChangeListener pchl = new PropertyChangeListener() {

        @Override
        public void propertyChange(PropertyChangeEvent pce) {
            if (NbMavenProject.PROP_PROJECT.equals(pce.getPropertyName())) {
                refresh();
            }
        }
    };

    public CopyResourcesOnSave(NbMavenProject nbprj, Project prj) {
        this.nbproject = nbprj;
        this.project = prj;
    }
    
    public final void opened() {
        refresh();
        nbproject.addPropertyChangeListener(pchl);
    }
    

    public final void closed() {
        nbproject.removePropertyChangeListener(pchl);
        synchronized (resourceUris) {
            for (File fl : resourceUris) {
                FileUtil.removeRecursiveListener(this, fl);
            }
            resourceUris.clear();
        }
    }
    
    final void refresh() {
        synchronized (resourceUris) {
            List<Resource> resources = new ArrayList<Resource>();
            resources.addAll(nbproject.getMavenProject().getResources());
            resources.addAll(nbproject.getMavenProject().getTestResources());
            Set<File> old = new HashSet<File>(resourceUris);
            Set<File> added = new HashSet<File>();
            
                for (Resource res : resources) {
                    String dir = res.getDirectory();
                    if (dir == null) {
                        continue;
                    }
                    URI uri = FileUtilities.getDirURI(project.getProjectDirectory(), dir);
                    File file = Utilities.toFile(uri);
                    if (!old.contains(file) && !added.contains(file)) { // if a given file is there multiple times, we get assertion back from FileUtil. there can be only one listener+file tuple
                        FileUtil.addRecursiveListener(this, file);
                    }
                    added.add(file);
                }
            
            old.removeAll(added);
            for (File oldFile : old) {
                FileUtil.removeRecursiveListener(this, oldFile);
            }
            resourceUris.removeAll(old);
            resourceUris.addAll(added);
        }
    }

    private static void copySrcToDest( FileObject srcFile, FileObject destFile) throws IOException {
        if (destFile != null && !srcFile.isFolder()) {
            InputStream is = null;
            OutputStream os = null;
            FileLock fl = null;
            try {
                is = srcFile.getInputStream();
                fl = destFile.lock();
                os = destFile.getOutputStream(fl);
                FileUtil.copy(is, os);
            } finally {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
                if (fl != null) {
                    fl.releaseLock();
                }
            }
        }
    }

    private Project getOwningMavenProject(FileObject file) {
        Project prj = FileOwnerQuery.getOwner(file);
        if (prj == null || !prj.equals(project)) {
            return null;
        }
        //#180447
        if (!prj.getProjectDirectory().isValid()) {
            return null;
        }
        NbMavenProject mvn = prj.getLookup().lookup(NbMavenProject.class);
        if (mvn == null) {
            return null;
        }
        if (RunUtils.isCompileOnSaveEnabled(prj)) {
            return prj;
        }
        return null;
    }

    /** Fired when a file is changed.
     * @param fe the event describing context where action has taken place
     */
    @Override
    public void fileChanged(final FileEvent fe) {
        RP.post(new Runnable() {//#167740
            @Override
            public void run() {
                fileChangedImpl(fe);
            }
        });
    }
    
    private  void fileChangedImpl(final FileEvent fe) {
        Project owning = getOwningMavenProject(fe.getFile());
        if (owning == null) {
            return;
        }
        try {
            handleCopyFileToDestDir(fe.getFile(), owning);
        } catch (IOException ex) {
            LOG.log(Level.INFO, null, ex);
        }
    }

    @Override
    public void fileDataCreated(final FileEvent fe) {
        RP.post(new Runnable() {//#167740
            @Override
            public void run() {
                fileDataCreatedImpl(fe);
            }
        });
    }
    private void fileDataCreatedImpl(final FileEvent fe) {
        Project owning = getOwningMavenProject(fe.getFile());
        if (owning == null) {
            return;
        }
        try {
            handleCopyFileToDestDir(fe.getFile(), owning);
        } catch (IOException ex) {
            LOG.log(Level.INFO, null, ex);
        }
    }

    @Override
    public void fileRenamed(final FileRenameEvent fe) {
        RP.post(new Runnable() {//#167740
            @Override
            public void run() {
                fileRenamedImpl(fe);
            }
        });
    }
    private void fileRenamedImpl(final FileRenameEvent fe) {
        try {
            FileObject fo = fe.getFile();
            Project owning = getOwningMavenProject(fo);
            if (owning == null) {
                return;
            }
            Tuple base = findAppropriateResourceRoots(fo, owning);
            if (base != null) {
                handleCopyFileToDestDir(base, fo, owning);
                FileObject parent = fo.getParent();
                String path;
                if (FileUtil.isParentOf(base.root, parent)) {
                    path = FileUtil.getRelativePath(base.root, fo.getParent()) +
                            "/" + fe.getName() + "." + fe.getExt(); //NOI18N
                } else {
                    path = fe.getName() + "." + fe.getExt(); //NOI18N
                }
                handleDeleteFileInDestDir(fo, path, base, owning);
            }
        } catch (IOException e) {
            LOG.log(Level.INFO, null, e);
        }
    }

    @Override
    public void fileDeleted(final FileEvent fe) {
        RP.post(new Runnable() {//#167740
            @Override
            public void run() {
                fileDeletedImpl(fe);
            }
        });
    }
    
    private void fileDeletedImpl(final FileEvent fe) {
        Project owning = getOwningMavenProject(fe.getFile());
        if (owning == null) {
            return;
        }
        try {
            handleDeleteFileInDestDir(fe.getFile(), null, owning);
        } catch (IOException ex) {
            LOG.log(Level.INFO, null, ex);
        }
    }

    private void handleDeleteFileInDestDir(FileObject fo, String path, Project project) throws IOException {
        Tuple tuple = findAppropriateResourceRoots(fo, project);
        handleDeleteFileInDestDir(fo, path, tuple, project);
    }

    private void handleDeleteFileInDestDir(FileObject fo, String path, Tuple tuple, Project project) throws IOException {
        if (tuple != null) {
            // inside docbase
            path = path != null ? path : FileUtil.getRelativePath(tuple.root, fo);
            path = addTargetPath(path, tuple.resource);
            FileObject toDelete = tuple.destinationRoot.getFileObject(path);
            if (toDelete != null) {
                toDelete.delete();
            }
            AdditionalDestination add = project.getLookup().lookup(AdditionalDestination.class);
            if (add != null) {
                add.delete(fo, path);
            }
        }
    }

    /** Copies a content file to an appropriate  destination directory, 
     * if applicable and relevant.
     */
    private void handleCopyFileToDestDir(FileObject fo, Project prj) throws IOException {
        Tuple tuple = findAppropriateResourceRoots(fo, prj);
        handleCopyFileToDestDir(tuple, fo, prj);
    }
    
    /** Copies a content file to an appropriate  destination directory,
     * if applicable and relevant.
     */
    private void handleCopyFileToDestDir(Tuple tuple, FileObject fo, Project project) throws IOException {
        if (tuple != null && !tuple.resource.isFiltering()) {
            //TODO what to do with filtering? for now ignore..
            String path = FileUtil.getRelativePath(tuple.root, fo);
            path = addTargetPath(path, tuple.resource);
            createAndCopy(fo, tuple.destinationRoot, path);
            AdditionalDestination add = project.getLookup().lookup(AdditionalDestination.class);
            if (add != null) {
                add.copy(fo, path);
            }
        }
    }

    private static /* #172620 */synchronized void createAndCopy(FileObject fo, FileObject root, String path) throws IOException {
        copySrcToDest(fo, ensureDestinationFileExists(root, path, fo.isFolder()));
    }

    private String addTargetPath(String path, Resource resource) {
        String target = resource.getTargetPath();
        if (target != null) {
            target = target.replace("\\", "/");
            target = target.endsWith("/") ? target : (target + "/");
            path = target + path;
        }
        return path;
    }

    private Tuple findAppropriateResourceRoots(FileObject child, Project prj) {
        NbMavenProject nbproj = prj.getLookup().lookup(NbMavenProject.class);
        assert nbproj != null;
        if (RunUtils.isCompileOnSaveEnabled(prj)) {
            Tuple tup = findResource(nbproj.getMavenProject().getTestResources(), prj, nbproj, child, true);
            if (tup != null) {
                return tup;
            }
            tup = findResource(nbproj.getMavenProject().getResources(), prj, nbproj, child, false);
            if (tup != null) {
                return tup;
            }
        }
        return null;
    }

    private Tuple findResource(List<Resource> resources, Project prj, NbMavenProject nbproj, FileObject child, boolean test) {
        if (resources == null) {
            return null;
        }
        FileObject target;
        //now figure the destination output folder
        File fil = nbproj.getOutputDirectory(test);
        File stamp = new File(fil, CosChecker.NB_COS);
        if (stamp.exists()) {
            target = FileUtil.toFileObject(fil);
        } else {
            // no compile on save stamp, means no copying, classes don't get copied/compiled either.
            return null;
        }

        resourceLoop:
        for (Resource res : resources) {
            String dir = res.getDirectory();
            if (dir == null) {
                continue;
            }
            URI uri = FileUtilities.getDirURI(prj.getProjectDirectory(), dir);
            FileObject fo = FileUtil.toFileObject(Utilities.toFile(uri));
            if (fo != null && FileUtil.isParentOf(fo, child)) {
                String path = FileUtil.getRelativePath(fo, child);
                //now check includes and excludes
                List<String> incls = res.getIncludes();
                if (incls.isEmpty()) {
                    incls = Arrays.asList(FilteredResourcesCoSSkipper.DEFAULT_INCLUDES);
                }
                boolean included = false;
                for (String incl : incls) {
                    if (DirectoryScanner.match(incl, path)) {
                        included = true;
                        break;
                    }
                }
                if (!included) {
                    break;
                }
                List<String> excls = new ArrayList<String>(res.getExcludes());
                excls.addAll(Arrays.asList(DirectoryScanner.DEFAULTEXCLUDES));
                for (String excl : excls) {
                    if (DirectoryScanner.match(excl, path)) {
                        continue resourceLoop;
                    }
                }

                return new Tuple(res, fo, target);
            }
        }
        return null;
    }

    /** Returns the destination file or folder
     */
    private static FileObject ensureDestinationFileExists(FileObject root, String path, boolean isFolder) throws IOException {
        if (isFolder) {
            return FileUtil.createFolder(root, path);
        } else {
            return FileUtil.createData(root, path);
        }
    }

    private class Tuple {
        Resource resource;
        FileObject root;
        FileObject destinationRoot;

        private Tuple(Resource res, FileObject fo, FileObject destFolder) {
            resource = res;
            root = fo;
            destinationRoot = destFolder;
        }
    }
}
