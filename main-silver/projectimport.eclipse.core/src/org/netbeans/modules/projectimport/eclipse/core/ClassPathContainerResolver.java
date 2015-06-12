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

package org.netbeans.modules.projectimport.eclipse.core;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.netbeans.api.project.libraries.LibraryManager;
import org.netbeans.modules.projectimport.eclipse.core.spi.DotClassPathEntry;
import org.netbeans.modules.projectimport.eclipse.core.spi.ProjectTypeFactory;
import org.netbeans.spi.project.support.ant.PropertyUtils;

/**
 * For now all well-known containers (based on Europa/Ganymede) are hardcoded here.
 * Can be refactored into SPI if needed.
 * 
 */
public class ClassPathContainerResolver {

    public static final String JUNIT_CONTAINER = "org.eclipse.jdt.junit.JUNIT_CONTAINER/"; //NOI18N
    public static final String USER_LIBRARY_CONTAINER = "org.eclipse.jdt.USER_LIBRARY/"; //NOI18N
    public static final String WEB_CONTAINER = "org.eclipse.jst.j2ee.internal.web.container"; //NOI18N
    public static final String J2EE_MODULE_CONTAINER = "org.eclipse.jst.j2ee.internal.module.container"; //NOI18N
    public static final String JSF_CONTAINER = "org.eclipse.jst.jsf.core.internal.jsflibrarycontainer/"; //NOI18N
    public static final String J2EE_SERVER_CONTAINER = "org.eclipse.jst.server.core.container/"; //NOI18N
    public static final String MYECLIPSE_CONTAINERS = "melibrary.com.genuitec.eclipse."; //NOI18N
    
    /**
     * Converts eclipse CONTAINER claspath entry to something what can be put
     * directly to Ant based project classpath. 
     * 
     * Eg. for "org.eclipse.jdt.junit.JUNIT_CONTAINER/3.8.1" it would be "libs.junit.classpath"
     * 
     * @param importInProgress  true if this method is called during project import just before eclipse project is converted to NetBeans
     * and it is possible to create for example NetBeans IDE library here etc.
     */
    public static boolean resolve(Workspace workspace, DotClassPathEntry entry, List<String> importProblems, boolean importInProgress) throws IOException {
        assert entry.getKind() == DotClassPathEntry.Kind.CONTAINER : entry;
        
        String container = entry.getRawPath();
        
        if (container.startsWith(JUNIT_CONTAINER)) {
            String library = "libs.junit.classpath"; //NOI18N
            if (container.substring(JUNIT_CONTAINER.length()).startsWith("4")) { //NOI18N
                library = "libs.junit_4.classpath"; //NOI18N
            }
            entry.setContainerMapping(library);
            return true;
        }
        
        if (container.startsWith(USER_LIBRARY_CONTAINER)) {
            if (importInProgress) {
                createLibrary(workspace, container, importProblems);
            }
            entry.setContainerMapping("libs."+getNetBeansLibraryName(container)+".classpath"); //NOI18N
            return true;
        }
        
        if (container.startsWith(JSF_CONTAINER)) {
            if (importInProgress) {
                createLibrary(workspace, container, importProblems);
            }
            entry.setContainerMapping("libs."+getNetBeansLibraryName(container)+".classpath"); //NOI18N
            return true;
        }
        
        if (container.startsWith(J2EE_MODULE_CONTAINER) ||
            container.startsWith(J2EE_SERVER_CONTAINER)) {
            // TODO: resolve these containers as empty for now.
            //       most of these are not needed anyway as they are 
            //       handled differntly directly by web project
            entry.setContainerMapping(""); //NOI18N
            return true;
        }
        
        if (container.startsWith(MYECLIPSE_CONTAINERS)) {
            if (importInProgress) {
                if (workspace != null) {
                    workspace.loadMyEclipseLibraries(importProblems);
                }
                createLibrary(workspace, container, importProblems);
            }
            entry.setContainerMapping("libs."+getNetBeansLibraryName(container)+".classpath"); //NOI18N
            return true;
        }
        
        if (container.startsWith(WEB_CONTAINER)) {
            if (importInProgress) {
                // if project is being imported and this container was not replaced then
                // this is single (naked) project import. append a warning:
                assert workspace == null;
                importProblems.add(org.openide.util.NbBundle.getMessage(ClassPathContainerResolver.class, "MSG_UnsupportedWebContainer", container));
                return false;
            }
            // ignore this container: it was or will be dealt with via replaceContainerEntry.
            return true;
        }
        
        importProblems.add(org.openide.util.NbBundle.getMessage(ClassPathContainerResolver.class, "MSG_UnsupportedContainer", container));
        
        return false;
    }

    /**
     * This method is called after all workspace projects were loaded as it may need
     * to reference to other projects. The purpose of this method is to replace
     * an container with something else, eg. WEB_CONTAINER is replaced with 
     * list of JARs/folders from the projects.
     */
    public static List<DotClassPathEntry> replaceContainerEntry(EclipseProject project, Workspace workspace, DotClassPathEntry entry, List<String> importProblems) {
        assert entry.getKind() == DotClassPathEntry.Kind.CONTAINER : entry;
        String container = entry.getRawPath();
        if (container.startsWith(WEB_CONTAINER)) {
            String projectName = null;
            if (container.length() > WEB_CONTAINER.length()) {
                projectName = container.substring(WEB_CONTAINER.length()+1);
            }
            return createClassPathForWebContainer(project, workspace, projectName);
        }
        
        return null;
    }
    
    private static List<DotClassPathEntry> createClassPathForWebContainer(EclipseProject project, Workspace w, String name) {
        List<DotClassPathEntry> newEntries = new ArrayList<DotClassPathEntry>();
        EclipseProject p = name != null ? w.getProjectByName(name) : project;
        if (p == null) {
            return newEntries;
        }
        if (!p.isImportSupported()) {
            return newEntries;
        }
        File location = p.getProjectFileLocation(ProjectTypeFactory.FILE_LOCATION_TOKEN_WEBINF);
        if (location == null) {
            return newEntries;
        }
        File lib = new File(location, "lib"); // NOI18N
        if (lib.exists()) {
            for (File f : lib.listFiles()) {
                if (f.isFile()) {
                    newEntries.add(createFileDotClassPathEntry(f));
                }
            }
        }
        File classes = new File(location, "classes"); // NOI18N
        if (classes.exists() && classes.isDirectory()) {
            newEntries.add(createFileDotClassPathEntry(classes));
        }
        return newEntries;
    }
    
    private static DotClassPathEntry createFileDotClassPathEntry(File f) {
        Map<String, String> props = new HashMap<String, String>();
        props.put(DotClassPathEntry.ATTRIBUTE_KIND, "lib"); // NOI18N
        props.put(DotClassPathEntry.ATTRIBUTE_PATH, f.getPath());
        DotClassPathEntry d = new DotClassPathEntry(props, null);
        d.setAbsolutePath(f.getPath());
        return d;
    }
        
    private static String getNetBeansLibraryName(String container) {
        return PropertyUtils.getUsablePropertyName(getEclipseLibraryName(container));
    }

    private static String getEclipseLibraryName(String container) {
        if (container.startsWith(MYECLIPSE_CONTAINERS)) {
            int index = container.indexOf(".MYECLIPSE_"); // NOI18N
            if (index == -1) {
                index = container.lastIndexOf("."); // NOI18N
                if (index !=-1) {
                    index +=1;
                }
            } else {
                index += 11;
            }
            assert index != -1 : container;
            return container.substring(index);
        }
        String prefix = container.startsWith(USER_LIBRARY_CONTAINER) ? USER_LIBRARY_CONTAINER : JSF_CONTAINER;
        return container.substring(prefix.length());
    }

    private static void createLibrary(Workspace workspace, String container, List<String> importProblems) throws IOException {
        // create eclipse user libraries in NetBeans:
        assert container.startsWith(USER_LIBRARY_CONTAINER) ||
                container.startsWith(JSF_CONTAINER) ||
                container.startsWith(MYECLIPSE_CONTAINERS): container;
        String library = getNetBeansLibraryName(container);
        LibraryManager lm = LibraryManager.getDefault();
        if (lm.getLibrary(library) != null) {
            return;
        }
        Map<String,List<URL>> content = new HashMap<String,List<URL>>();
        if (workspace == null) {
            importProblems.add(org.openide.util.NbBundle.getMessage(ClassPathContainerResolver.class, "MSG_CannotCreateUserLibrary", library));
            return;
        }
        content.put("classpath", workspace.getJarsForUserLibrary(getEclipseLibraryName(container))); //NOI18N
        List<URL> urls = workspace.getJavadocForUserLibrary(getEclipseLibraryName(container), importProblems);
        if (urls != null) {
            content.put("javadoc", urls);
        }
        urls = workspace.getSourcesForUserLibrary(getEclipseLibraryName(container), importProblems);
        if (urls != null) {
            content.put("src", urls);
        }
        lm.createLibrary("j2se", library, content); //NOI18N
    }
    
    public static boolean isJUnit(DotClassPathEntry entry) {
        if (entry.getKind() == DotClassPathEntry.Kind.CONTAINER && 
                entry.getRawPath().startsWith(JUNIT_CONTAINER)) {
            return true;
        }
        if (entry.getKind() == DotClassPathEntry.Kind.LIBRARY ||  
            entry.getKind() == DotClassPathEntry.Kind.VARIABLE) {
            int i = entry.getRawPath().replace('\\', '/').lastIndexOf('/'); // NOI18N
            if (i != -1) {
                String s = entry.getRawPath().substring(i+1);
                return s.startsWith("junit") && s.endsWith(".jar"); // NOI18N
            }
        }
        return false;
    }
        
}
