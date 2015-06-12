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
 * Portions Copyrighted 2007 Sun Microsystems, Inc.
 */

package org.netbeans.modules.websvc.saas.codegen;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.jar.Manifest;
import org.netbeans.junit.MockServices;
import org.netbeans.modules.websvc.saas.model.SaasServicesModel;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileSystem;
import org.openide.filesystems.FileUtil;
import org.openide.filesystems.LocalFileSystem;
import org.openide.filesystems.MultiFileSystem;
import org.openide.filesystems.Repository;
import org.openide.filesystems.XMLFileSystem;
import org.openide.modules.InstalledFileLocator;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbCollections;

/**
 *
 * @author quynguyen
 */
public class SetupUtil {
    
    private static final String ENDORSED_REF = "modules/ext/jaxws21/api/jaxws-api.jar";
    private static final String JAXWS_LIB_PROPERTY = "libs.jaxws21.classpath";
    
    public static void commonSetUp(File workingDir) throws Exception {
        File testuserdir = new File(workingDir.getParentFile(), "testuser");
        System.getProperties().setProperty("netbeans.user", testuserdir.getAbsolutePath());
        //SaasServicesModel.getInstance().reset();
        FileObject websvcHome = SaasServicesModel.getWebServiceHome();
        File userconfig = FileUtil.toFile(websvcHome.getParent());
        MainFS fs = new MainFS();
        fs.setConfigRootDir(userconfig);
        TestRepository.defaultFileSystem = fs;
        
        MockServices.setServices(InstalledFileLocatorImpl.class, TestRepository.class);
        
        InstalledFileLocatorImpl locator = (InstalledFileLocatorImpl)Lookup.getDefault().lookup(InstalledFileLocator.class);
        locator.setUserConfigRoot(userconfig);
        
        //File targetBuildProperties = new File(testuserdir, "build.properties");
        //generatePropertiesFile(targetBuildProperties);
        
    }

    public static void commonTearDown() throws Exception {
        SaasServicesModel.getWebServiceHome().delete();
        MockServices.setServices();
    }
    
    private static void generatePropertiesFile(File target) throws IOException {
        String separator = System.getProperty("path.separator");
        File apiBase = InstalledFileLocator.getDefault().locate(ENDORSED_REF, null, true).getParentFile();
        File jaxWsBase = apiBase.getParentFile();
        
        FileFilter jarFilter = new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.isFile() && pathname.getName().endsWith(".jar");
            }
        };
        
        File[] apiJars = apiBase.listFiles(jarFilter);
        File[] implJars = jaxWsBase.listFiles(jarFilter);
        
        Properties result = new Properties();
        StringBuffer classpath = new StringBuffer();
        
        for (int i = 0; i < apiJars.length; i++) {
            String pathElement = apiJars[i].getAbsolutePath() + separator;
            classpath.append(pathElement);
        }
        
        for (int i = 0; i < implJars.length; i++) {
            classpath.append(implJars[i].getAbsolutePath());
            if (i != implJars.length - 1) {
                classpath.append(separator);
            }
        }
        
        result.setProperty(JAXWS_LIB_PROPERTY, classpath.toString());
        
        FileOutputStream fos = new FileOutputStream(target);
        result.store(fos, "build.properties file");
    }
    
    public static final class TestRepository extends Repository {
        static FileSystem defaultFileSystem = null;
        
        public TestRepository() {
            super(defaultFileSystem);
        }
    }
    
    // Taken from org.openide.filesystems.ExternalUtil to allow layer files to be
    // loaded into the default filesystem (since core/startup is in the classpath
    // and registers a default Repository that we do not want)
    public static final class MainFS extends MultiFileSystem implements LookupListener {
        private final Lookup.Result<FileSystem> ALL = Lookup.getDefault().lookupResult(FileSystem.class);
        private final FileSystem MEMORY = FileUtil.createMemoryFileSystem();
        private final XMLFileSystem layers = new XMLFileSystem();
        
        private final LocalFileSystem configRoot = new LocalFileSystem();
        
        public void setConfigRootDir(File root) throws Exception {
            configRoot.setRootDirectory(root);
        }
        
        public MainFS() {
            ALL.addLookupListener(this);
            
            List<URL> layerUrls = new ArrayList<URL>();
            ClassLoader l = Thread.currentThread().getContextClassLoader();
            try {
                for (URL manifest : NbCollections.iterable(l.getResources("META-INF/MANIFEST.MF"))) { // NOI18N
                    InputStream is = manifest.openStream();
                    try {
                        Manifest mani = new Manifest(is);
                        String layerLoc = mani.getMainAttributes().getValue("OpenIDE-Module-Layer"); // NOI18N
                        if (layerLoc != null) {
                            URL layer = l.getResource(layerLoc);
                            if (layer != null) {
                                layerUrls.add(layer);
                            }
                        }
                    } finally {
                        is.close();
                    }
                }
                layers.setXmlUrls(layerUrls.toArray(new URL[layerUrls.size()]));
            } catch (Exception x) {
            }
            resultChanged(null); // run after add listener - see PN1 in #26338
        }
        
        private FileSystem[] computeDelegates() {
            List<FileSystem> arr = new ArrayList<FileSystem>();
            arr.add(MEMORY);
            arr.add(layers);
            arr.add(configRoot);
            arr.addAll(ALL.allInstances());
            return arr.toArray(new FileSystem[0]);
        }
    
        public void resultChanged(LookupEvent ev) {
            setDelegates(computeDelegates());
        }
    }
    
    
}
