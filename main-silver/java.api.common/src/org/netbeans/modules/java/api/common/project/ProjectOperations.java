/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2013 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2013 Sun Microsystems, Inc.
 */

package org.netbeans.modules.java.api.common.project;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tools.ant.module.api.support.ActionUtils;
import org.netbeans.api.annotations.common.NonNull;
import org.netbeans.api.annotations.common.NullAllowed;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectInformation;
import org.netbeans.api.project.ProjectManager;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.modules.java.api.common.SourceRoots;
import org.netbeans.modules.java.api.common.ant.UpdateHelper;
import org.netbeans.modules.java.api.common.util.CommonProjectUtils;
import org.netbeans.spi.project.CopyOperationImplementation;
import org.netbeans.spi.project.DataFilesProviderImplementation;
import org.netbeans.spi.project.DeleteOperationImplementation;
import org.netbeans.spi.project.MoveOrRenameOperationImplementation;
import org.netbeans.spi.project.support.ant.AntProjectHelper;
import org.netbeans.spi.project.support.ant.EditableProperties;
import org.netbeans.spi.project.support.ant.PropertyEvaluator;
import org.netbeans.spi.project.support.ant.PropertyUtils;
import org.netbeans.spi.project.support.ant.ReferenceHelper;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileSystem;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.openide.util.Pair;
import org.openide.util.Parameters;
import org.openide.util.Utilities;

/**
 * Support for default project operations for Ant based project.
 * @author Tomas Zezula
 * @author Jan Lahoda
 * @since 1.65
 */
public final class ProjectOperations {

    private static final Logger LOG = Logger.getLogger(ProjectOperations.class.getName());
    private static final String TARGET_CLEAN = "clean";

    private ProjectOperations() {
        throw new IllegalStateException("No instance allowed.");    //NOI18N
    }

    /**
     * Creates a builder for creating {@link DeleteOperationImplementation},
     * {@link MoveOrRenameOperationImplementation}, {@link CopyOperationImplementation}
     * implementation.
     * @param project the project to create project operations for
     * @param eval  the project's {@link PropertyEvaluator}
     * @param helper the project's {@link UpdateHelper}
     * @param refHelper the project's {@link ReferenceHelper}
     * @param sources the project's source roots
     * @param tests the project's test roots
     * @return a builder for project operations
     */
    @NonNull
    public static ProjectOperationsBuilder createBuilder(
        @NonNull final Project project,
        @NonNull final PropertyEvaluator eval,
        @NonNull final UpdateHelper helper,
        @NonNull final ReferenceHelper refHelper,
        @NonNull final SourceRoots sources,
        @NonNull final SourceRoots tests) {
        return new ProjectOperationsBuilder(
            project,
            eval,
            helper,
            refHelper,
            sources,
            tests);
    }

    /**
     * Callback for project operations.
     * The callback is called before and after each project operation.
     * The callback is registered by {@link ProjectOperationsBuilder#setCallback} method.
     */
    public static interface Callback {
        /**
         * The type of project operation.
         */
        enum Operation {
            /**
             * Project delete.
             */
            DELETE,
            /**
             * Project copy.
             */
            COPY,
            /**
             * Project move.
             */
            MOVE,
            /**
             * Project rename.
             */
            RENAME;
        }

        /**
         * Called at the beginning of the project operation.
         * @param operation the operation being performed
         */
        void beforeOperation(@NonNull final Operation operation);

        /**
         * Called at the end of the project operation.
         * @param operation the operation being performed
         * @param newName the new project's name or null when not applicable, eg. delete
         * @param oldProject the old project or null when not applicable, eg. delete, move, rename
         */
        void afterOperation(
                @NonNull final Operation operation,
                @NullAllowed String newName,
                @NullAllowed Pair<File,Project> oldProject);
    }


    /**
     * The builder for projects operations.
     */
    public static final class ProjectOperationsBuilder {

        private final Project project;
        private final PropertyEvaluator eval;
        private final UpdateHelper helper;
        private final ReferenceHelper refHelper;
        private final SourceRoots sources;
        private final SourceRoots tests;
        private final List<String> additionalMetadataFiles;
        private final List<String> additionalDataFiles;
        private final List<String> cleanTargets;
        private final Set<String> privateProps;
        private final Map<String,Pair<String,Boolean>> updatedProps;
        private Callback callback;
        private String buildScriptProperty = ProjectProperties.BUILD_SCRIPT;

        private ProjectOperationsBuilder(
            @NonNull final Project project,
            @NonNull final PropertyEvaluator eval,
            @NonNull final UpdateHelper helper,
            @NonNull final ReferenceHelper refHelper,
            @NonNull final SourceRoots sources,
            @NonNull final SourceRoots tests) {
            Parameters.notNull("project", project); //NOI18N
            Parameters.notNull("eval", eval);   //NOI18N
            Parameters.notNull("helper", helper);   //NOI18N
            Parameters.notNull("refHelper", refHelper); //NOI18N
            Parameters.notNull("sources", sources); //NOI18N
            Parameters.notNull("tests", tests); //NOI18N
            this.project = project;
            this.eval = eval;
            this.helper = helper;
            this.refHelper = refHelper;
            this.sources = sources;
            this.tests = tests;
            this.additionalMetadataFiles = new ArrayList<>();
            this.additionalDataFiles = new ArrayList<>();
            this.cleanTargets = new ArrayList<>();            
            this.privateProps = new HashSet<>();
            this.updatedProps = new HashMap<>();
        }

        /**
         * Sets the name of property referencing the project build script.
         * If not set the {@link ProjectProperties#BUILD_SCRIPT} is used.
         * @param propertyName the name of property holding the name of project's build script.
         * @return the {@link ProjectOperationsBuilder}
         */
        @NonNull
        public ProjectOperationsBuilder setBuildScriptProperty(@NonNull final String propertyName) {
            Parameters.notNull("propertyName", propertyName);   //NOI18N
            this.buildScriptProperty = propertyName;
            return this;
        }

        /**
         * Adds build script targets to the list of clean targets.
         * All the added targets are executed before project operation, when no
         * clean target is set the default one "clean" is used.
         * @param cleanTargets the clean targets
         * @return the {@link ProjectOperationsBuilder}
         */
        @NonNull
        public ProjectOperationsBuilder addCleanTargets(@NonNull String... cleanTargets) {
            Parameters.notNull("cleanTargets", cleanTargets);   //NOI18N
            Collections.addAll(this.cleanTargets, cleanTargets);
            return this;
        }

        /**
         * Adds additional files to the list of project metadata.
         * The "nbproject" and project build script are already included.
         * @param paths the metadata file paths, relative to project directory
         * @return the {@link ProjectOperationsBuilder}
         */
        @NonNull
        public ProjectOperationsBuilder addMetadataFiles(@NonNull final String... paths) {
            Parameters.notNull("paths", paths);   //NOI18N
            Collections.addAll(additionalMetadataFiles, paths);
            return this;
        }

        /**
         * Adds additional files to the list of project data files.
         * The source roots, test roots and library definitions are already included.
         * @param paths the data file paths, relative to project directory
         * @return the {@link ProjectOperationsBuilder}
         */
        @NonNull
        public ProjectOperationsBuilder addDataFiles(@NonNull final String... paths) {
            Parameters.notNull("paths", paths);   //NOI18N
            Collections.addAll(additionalDataFiles, paths);
            return this;
        }

        /**
         * Adds private properties which should be retained during copy, move or rename of project.
         * @param properties the private properties to be retained
         * @return the {@link ProjectOperationsBuilder}
         */
        @NonNull
        public ProjectOperationsBuilder addPreservedPrivateProperties(@NonNull final String... properties) {
            Parameters.notNull("properties", properties);   //NOI18N
            Collections.addAll(privateProps, properties);
            return this;
        }

        /**
         * Adds a project property which should be updated by a new project name after rename or copy of project.
         * @param propertyName  the project property name
         * @param propertyPattern the {@link MessageFormat} pattern of the property value, the "{0}"
         * is replaced by the new project name
         * @param antName when true the project name is converted into Ant friendly name before substitution
         * @return the {@link ProjectOperationsBuilder}
         */
        @NonNull
        public ProjectOperationsBuilder addUpdatedNameProperty(
                @NonNull final String propertyName,
                @NullAllowed String propertyPattern,
                final boolean antName) {
            Parameters.notNull("propertyName", propertyName);  //NOI18N
            if (propertyPattern == null) {
                propertyPattern = "{0}";    //NOI18N
            }
            updatedProps.put(propertyName, Pair.<String,Boolean>of(propertyPattern, antName));
            return this;
        }

        /**
         * Sets the project operation callback.
         * @param callback the callback
         * @return the {@link ProjectOperationsBuilder}
         */
        @NonNull
        public ProjectOperationsBuilder setCallback(@NonNull final Callback callback) {
            Parameters.notNull("callback", callback);   //NOI18N
            this.callback = callback;
            return this;
        }

        /**
         * Creates a new configured {@link CopyOperationImplementation},
         * {@link DeleteOperationImplementation} and {@link MoveOrRenameOperationImplementation}
         * instance.
         * @return the project operations implementation
         */
        @NonNull
        public DataFilesProviderImplementation build() {
            if (cleanTargets.isEmpty()) {
                cleanTargets.add(TARGET_CLEAN);
            }
            return new Operations(
                project,
                eval,
                helper,
                refHelper,
                sources,
                tests,
                buildScriptProperty,
                additionalMetadataFiles,
                additionalDataFiles,
                cleanTargets,
                privateProps,
                updatedProps,
                callback);
        }
    }


    private static class Operations implements DataFilesProviderImplementation,
            DeleteOperationImplementation,
            CopyOperationImplementation, MoveOrRenameOperationImplementation {

        private final Project project;
        private final PropertyEvaluator eval;
        private final UpdateHelper helper;
        private final ReferenceHelper refHelper;
        private final SourceRoots sources;
        private final SourceRoots tests;
        private final String buildScriptProperty;
        private final List<? extends String> additionalMetadataFiles;
        private final List<? extends String> additionalDataFiles;
        private final List<? extends String> cleanTargets;
        private final Set<? extends String> privateProps;
        private final Map<String,Pair<String,Boolean>> updatedProps;
        private final Callback callback;


        //RELY: Valid only on original project after the notifyMoving or notifyCopying was called
        private final Map<String,String> privatePropsToRestore = new HashMap<>();
        //RELY: Valid only on original project after the notifyMoving or notifyCopying was called
        private String absolutesRelPath;
        //RELY: Valid only on original project after the notifyMoving or notifyCopying was called
        private String libraryPath;
        //RELY: Valid only on original project after the notifyMoving or notifyCopying was called
        private File libraryFile;
        //RELY: Valid only on original project after the notifyMoving or notifyCopying was called
        private boolean libraryWithinProject;
        //RELY: Valid only on original project after the notifyMoving or notifyCopying was called
        private FileSystem configs;

        Operations(
            @NonNull final Project project,
            @NonNull final PropertyEvaluator eval,
            @NonNull final UpdateHelper helper,
            @NonNull final ReferenceHelper refHelper,
            @NonNull final SourceRoots sources,
            @NonNull final SourceRoots tests,
            @NonNull final String buildScriptProperty,
            @NonNull final List<? extends String> additionalMetadataFiles,
            @NonNull final List<? extends String> additionalDataFiles,
            @NonNull final List<? extends String> cleanTargets,
            @NonNull final Set<? extends String> privateProps,
            @NonNull final Map<String,Pair<String,Boolean>> updatedProps,
            @NullAllowed final Callback callback) {
            Parameters.notNull("project", project); //NOI18N
            Parameters.notNull("eval", eval);   //NOI18N
            Parameters.notNull("helper", helper);   //NOI18N
            Parameters.notNull("refHelper", refHelper); //NOI18N
            Parameters.notNull("sources", sources); //NOI18N
            Parameters.notNull("tests", tests); //NOI18N
            Parameters.notNull("buildScriptProperty", buildScriptProperty); //NOI18N
            Parameters.notNull("additionalMetadataFiles", additionalMetadataFiles); //NOI18N
            Parameters.notNull("additionalDataFiles", additionalDataFiles); //NOI18N
            Parameters.notNull("cleanTargets", cleanTargets);   //NOI18N
            Parameters.notNull("privateProps", privateProps);   //NOI18N
            Parameters.notNull("updatedProps", updatedProps);   //NOI18N
            this.project = project;
            this.eval = eval;
            this.helper = helper;
            this.refHelper = refHelper;
            this.sources = sources;
            this.tests = tests;
            this.buildScriptProperty = buildScriptProperty;
            this.additionalMetadataFiles = additionalMetadataFiles;
            this.additionalDataFiles = additionalDataFiles;
            this.cleanTargets = cleanTargets;
            this.privateProps = privateProps;
            this.updatedProps = updatedProps;
            this.callback = callback;
        }

        @Override
        public void notifyDeleting() throws IOException {
            before(Callback.Operation.DELETE);
            clean();
        }

        @Override
        public void notifyDeleted() throws IOException {
            helper.getAntProjectHelper().notifyDeleted();
            after(Callback.Operation.DELETE, null, null);
        }

        @Override
        public List<FileObject> getMetadataFiles() {
            final FileObject projectDirectory = project.getProjectDirectory();
            final List<FileObject> files = new ArrayList<>();
            addFile(projectDirectory, "nbproject", files); // NOI18N
            addFile(projectDirectory, CommonProjectUtils.getBuildXmlName(eval, buildScriptProperty), files);
            for (String amf : additionalMetadataFiles) {
                addFile(projectDirectory, amf, files);
            }
            return files;
        }

        @Override
        public List<FileObject> getDataFiles() {
            final FileObject projectDirectory = project.getProjectDirectory();
            final List<FileObject> files = new ArrayList<>();
            //add source & test roots
            Collections.addAll(files, sources.getRoots());
            Collections.addAll(files, tests.getRoots());
            // add libraries folder if it is within project:
            final AntProjectHelper aph = helper.getAntProjectHelper();
            if (aph.getLibrariesLocation() != null) {
                File f = aph.resolveFile(aph.getLibrariesLocation());
                if (f != null && f.exists()) {
                    FileObject libFolder = FileUtil.toFileObject(f).getParent();
                    if (FileUtil.isParentOf(projectDirectory, libFolder)) {
                        files.add(libFolder);
                    }
                }
            }
            //add additional files
            for (String adf : additionalDataFiles) {
                addFile(projectDirectory, adf, files);
            }
            return files;
        }

        @Override
        public void notifyCopying() throws IOException {
            before(Callback.Operation.COPY);
            rememberLibraryLocation();
            readPrivateProperties();
            rememberConfigurations();
        }

        @Override
        public void notifyCopied(Project original, File originalPath, String nueName) throws IOException {
            if (original == null) {
                //do nothing for the original project.
                return ;
            }
            final Operations origOperations = original.getLookup().lookup(Operations.class);
            fixLibraryLocation(origOperations);
            fixPrivateProperties(origOperations);
            updateProjectProperties(nueName);
            refHelper.fixReferences(originalPath);
            restoreConfigurations(origOperations);
            after(Callback.Operation.COPY, nueName, null);
        }

        @Override
        public void notifyRenaming() throws IOException {
            if (!this.helper.requestUpdate()) {
                throw new IOException(NbBundle.getMessage(
                    ProjectOperations.class,
                    "MSG_OldProjectMetadata"));
            }
            before(Callback.Operation.RENAME);
            clean();
        }

        @Override
        public void notifyRenamed(String nueName) throws IOException {
            updateProjectProperties(nueName);
            after(Callback.Operation.RENAME, nueName, null);
        }

        @Override
        public void notifyMoving() throws IOException {
            if (!this.helper.requestUpdate()) {
            throw new IOException (NbBundle.getMessage(
                ProjectOperations.class,
                "MSG_OldProjectMetadata"));
            }
            before(Callback.Operation.MOVE);
            rememberLibraryLocation();
            readPrivateProperties ();
            rememberConfigurations();
            clean();
        }

        @Override
        public void notifyMoved(Project original, File originalPath, String nueName) throws IOException {
            if (original == null) {
                helper.getAntProjectHelper().notifyDeleted();
                return ;
            }
            final Operations origOperations = original.getLookup().lookup(Operations.class);
            fixLibraryLocation(origOperations);
            fixPrivateProperties (origOperations);
            updateProjectProperties(nueName);
            refHelper.fixReferences(originalPath);
            restoreConfigurations(origOperations);
            after(Callback.Operation.MOVE, nueName, Pair.<File, Project>of(originalPath,original));
        }

        private void clean() throws IOException {
            final Properties p = new Properties();
            final String buildXmlName = CommonProjectUtils.getBuildXmlName(eval, buildScriptProperty);
            final FileObject buildXML = project.getProjectDirectory().getFileObject(buildXmlName);
            if (buildXML != null) {
                ActionUtils.runTarget(
                    buildXML,
                    cleanTargets.toArray(new String[cleanTargets.size()]),
                    p).waitFinished();
            } else {
                LOG.log(
                    Level.INFO,
                    "Not cleaning the project: {0}, the build file: {1} does not exist.", //NOI18N
                    new Object[] {
                        ProjectUtils.getInformation(project).getDisplayName(),
                        buildXmlName
                    });
            }
        }

        private void rememberLibraryLocation() {
            libraryWithinProject = false;
            absolutesRelPath = null;
            libraryPath = helper.getAntProjectHelper().getLibrariesLocation();
            if (libraryPath != null) {
                File prjRoot = FileUtil.toFile(project.getProjectDirectory());
                libraryFile = PropertyUtils.resolveFile(prjRoot, libraryPath);
                if (FileOwnerQuery.getOwner(Utilities.toURI(libraryFile)) == project &&
                        libraryFile.getAbsolutePath().startsWith(prjRoot.getAbsolutePath())) {
                    //do not update the relative path if within the project..
                    libraryWithinProject = true;
                    FileObject fo = FileUtil.toFileObject(libraryFile);
                    if (new File(libraryPath).isAbsolute() && fo != null) {
                        // if absolte path within project, it will get moved/copied..
                        absolutesRelPath = FileUtil.getRelativePath(project.getProjectDirectory(), fo);
                    }
                }
            }
        }

        private void readPrivateProperties () {
            ProjectManager.mutex().readAccess(new Runnable() {
                public void run () {
                    privatePropsToRestore.clear();
                    for (String privateProp : privateProps) {
                        backUpPrivateProp(privateProp);
                    }
                }
            });
        }
        //where
        /**
         * Threading: Has to be called under project mutex
         */
        private void backUpPrivateProp(String propName) {
            assert ProjectManager.mutex().isReadAccess() || ProjectManager.mutex().isWriteAccess();
            final String tmp = helper.getProperties(AntProjectHelper.PRIVATE_PROPERTIES_PATH).getProperty(propName);
            if (tmp != null) {
                privatePropsToRestore.put(propName, tmp);
            }
        }

        private void rememberConfigurations () {
            FileObject fo = project.getProjectDirectory().getFileObject(ProjectConfigurations.CONFIG_PROPS_PATH);
            if (fo != null) {
                //Has configurations
                try {
                    FileSystem fs = FileUtil.createMemoryFileSystem();
                    FileUtil.copyFile(fo, fs.getRoot(),fo.getName());
                    fo = project.getProjectDirectory().getFileObject("nbproject/private/configs");      //NOI18N
                    if (fo != null && fo.isFolder()) {
                        FileObject cfgs = fs.getRoot().createFolder("configs");                         //NOI18N
                        for (FileObject child : fo.getChildren()) {
                            FileUtil.copyFile(child, cfgs, child.getName());
                        }
                    }
                    configs = fs;
                } catch (IOException ioe) {
                    Exceptions.printStackTrace(ioe);
                }
            }
        }

        private void fixLibraryLocation(Operations original) throws IllegalArgumentException {
            String libPath = original.libraryPath;
            if (libPath != null) {
                if (!new File(libPath).isAbsolute()) {
                    //relative path to libraries
                    if (!original.libraryWithinProject) {
                        File file = original.libraryFile;
                        if (file == null) {
                            // could happen in some rare cases, but in that case the original project was already broken, don't fix.
                            return;
                        }
                        String relativized = PropertyUtils.relativizeFile(FileUtil.toFile(project.getProjectDirectory()), file);
                        if (relativized != null) {
                            helper.getAntProjectHelper().setLibrariesLocation(relativized);
                        } else {
                            //cannot relativize, use absolute path
                            helper.getAntProjectHelper().setLibrariesLocation(file.getAbsolutePath());
                        }
                    } else {
                        //got copied over to new location.. the relative path is the same..
                    }
                } else {

                    //absolute path to libraries..
                    if (original.libraryWithinProject) {
                        if (original.absolutesRelPath != null) {
                            helper.getAntProjectHelper().setLibrariesLocation(PropertyUtils.resolveFile(FileUtil.toFile(project.getProjectDirectory()), original.absolutesRelPath).getAbsolutePath());
                        }
                    } else {
                        // absolute path to an external folder stays the same.
                    }
                }
            }
        }

        private void fixPrivateProperties (final Operations original) {
            if (original != null && !original.privatePropsToRestore.isEmpty()) {
                ProjectManager.mutex().writeAccess(new Runnable () {
                    public void run () {
                        final EditableProperties ep = helper.getProperties (AntProjectHelper.PRIVATE_PROPERTIES_PATH);
                        for (Map.Entry<String,String> entry : original.privatePropsToRestore.entrySet()) {
                            ep.put(entry.getKey(), entry.getValue());
                        }
                        helper.putProperties(AntProjectHelper.PRIVATE_PROPERTIES_PATH, ep);
                    }
                });
            }
        }

        private void restoreConfigurations (final Operations original) {
            final FileSystem fs = original.configs;
            original.configs = null;
            if (fs != null) {
                try {
                    FileObject fo = fs.getRoot().getFileObject("config.properties");        //NOI18N
                    if (fo != null) {
                        FileObject privateFolder = FileUtil.createFolder(project.getProjectDirectory(), "nbproject/private");  //NOI18N
                        if (privateFolder != null) {
                            // #131857: SyncFailedException : check for file existence before FileUtil.copyFile
                            FileObject oldFile = privateFolder.getFileObject(fo.getName(), fo.getExt());
                            if (oldFile != null) {
                                //Probably delete outside of IDE + move. First try to repair FS cache
                                privateFolder.refresh();
                                oldFile = privateFolder.getFileObject(fo.getName(), fo.getExt());
                                if (oldFile != null) {
                                    //The file still exists, delete it.
                                    oldFile.delete();
                                }
                            }

                            FileUtil.copyFile(fo, privateFolder, fo.getName());
                        }
                    }
                    fo = fs.getRoot().getFileObject("configs");                             //NOI18N
                    if (fo != null) {
                        FileObject configsFolder = FileUtil.createFolder(project.getProjectDirectory(), "nbproject/private/configs");  //NOI18N
                        if (configsFolder != null) {
                            for (FileObject child : fo.getChildren()) {
                                FileUtil.copyFile(child, configsFolder, child.getName());
                            }
                        }
                    }
                } catch (IOException ioe) {
                    Exceptions.printStackTrace(ioe);
                }
            }
        }

        private void updateProjectProperties(@NonNull final String newName) {
            if (!updatedProps.isEmpty()) {
                final ProjectInformation pi = ProjectUtils.getInformation(project);
                final String oldName = pi.getDisplayName();
                final EditableProperties ep = helper.getProperties (AntProjectHelper.PROJECT_PROPERTIES_PATH);
                for (Map.Entry<String,Pair<String,Boolean>> e : updatedProps.entrySet()) {
                    final String propName = e.getKey();
                    final String format = e.getValue().first();
                    final boolean antName = e.getValue().second();
                    final String oldProp = MessageFormat.format(
                        format,
                        antName ?
                            PropertyUtils.getUsablePropertyName(oldName) :
                            oldName);
                    final String propValue = ep.getProperty(propName);
                    if (oldProp.equals (propValue)) {
                        final String newProp = MessageFormat.format(
                            format,
                            antName ?
                                PropertyUtils.getUsablePropertyName(newName) :
                                newName);
                        ep.put (propName, newProp);
                    }
                }
                helper.putProperties (AntProjectHelper.PROJECT_PROPERTIES_PATH,ep);
            }
        }

        private void before(@NonNull final Callback.Operation operation) {
            if (callback != null) {
                callback.beforeOperation(operation);
            }
        }

        private void after(
            @NonNull final Callback.Operation operation,
            @NullAllowed final String newName,
            @NullAllowed final Pair<File,Project> oldProject) {
            if (callback != null) {
                callback.afterOperation(operation, newName, oldProject);
            }
        }

        private static void addFile(FileObject projectDirectory, String fileName, List<FileObject> result) {
            final FileObject file = projectDirectory.getFileObject(fileName);
            if (file != null) {
                result.add(file);
            }
        }

    }

}
