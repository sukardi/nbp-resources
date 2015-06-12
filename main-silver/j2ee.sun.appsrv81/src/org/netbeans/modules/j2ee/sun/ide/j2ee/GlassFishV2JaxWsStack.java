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

package org.netbeans.modules.j2ee.sun.ide.j2ee;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.netbeans.modules.javaee.specs.support.api.JaxWs;
import org.netbeans.modules.websvc.wsstack.api.WSStack.Feature;
import org.netbeans.modules.websvc.wsstack.api.WSStack.Tool;
import org.netbeans.modules.websvc.wsstack.api.WSStackVersion;
import org.netbeans.modules.websvc.wsstack.api.WSTool;
import org.netbeans.modules.websvc.wsstack.spi.WSStackImplementation;
import org.netbeans.modules.websvc.wsstack.spi.WSStackFactory;
import org.netbeans.modules.websvc.wsstack.spi.WSToolImplementation;


/**
 *
 * @author mkuchtiak
 * @author ads
 */
public class GlassFishV2JaxWsStack implements WSStackImplementation<JaxWs> {
    private static final String WEBSERVICES_TOOLS_JAR = "lib/webservices-tools.jar"; //NOI18N
    private static final String WEBSERVICES_RT_JAR = "lib/webservices-rt.jar"; //NOI18N
    
    private static final String TOOLS_JAR = "lib/tools.jar"; //NOI18N
    private static final String JSTL_JAR =  "lib/appserv-jstl.jar"; //NOI18N
    private static final String JAVA_EE_JAR = "lib/javaee.jar"; //NOI18N
    private static final String APPSERV_WS_JAR = "lib/appserv-ws.jar"; //NOI18N
    private static final String MAIL_JAR =  "lib/mail.jar"; //NOI18N
    private static final String ACTIVATION_JAR = "lib/activation.jar"; //NOI18N
    
    private File root;
    private String version;
    private JaxWs jaxWs;
    
    public GlassFishV2JaxWsStack(File root) {
        this.root = root;
        try {
            version = resolveImplementationVersion();
            if (version == null) {
                // Default Version
                version = "2.1.3"; // NOI18N
            }
        } catch (IOException ex) {
            // Default Version
            version = "2.1.3"; // NOI18N
        };
        jaxWs = new JaxWs(getUriDescriptor());
    }

    @Override
    public JaxWs get() {
        return jaxWs;
    }

    @Override
    public WSStackVersion getVersion() {
        return WSStackFactory.createWSStackVersion(version);
    }

    @Override
    public WSTool getWSTool(Tool toolId) {
        if (toolId == JaxWs.Tool.WSIMPORT) {
            return WSStackFactory.createWSTool(new JaxWsTool(JaxWs.Tool.WSIMPORT));
        } else if (toolId == JaxWs.Tool.WSGEN) {
            return WSStackFactory.createWSTool(new JaxWsTool(JaxWs.Tool.WSGEN));
        } else {
            return null;
        }
    }

    private JaxWs.UriDescriptor getUriDescriptor() {
        return new JaxWs.UriDescriptor() {

            @Override
            public String getServiceUri(String applicationRoot, 
                    String serviceName, String portName, boolean isEjb) 
            {
                if (isEjb) {
                    return serviceName+"/"+portName; //NOI18N
                } else {
                    return (applicationRoot.length()>0 ? applicationRoot+"/" : 
                        "")+serviceName; //NOI18N
                }
            }

            @Override
            public String getDescriptorUri(String applicationRoot, 
                    String serviceName, String portName, boolean isEjb) 
            {
                return getServiceUri(applicationRoot, serviceName, portName, 
                        isEjb)+"?wsdl"; //NOI18N
            }

            @Override
            public String getTesterPageUri(String host, String port, 
                    String applicationRoot, String serviceName, String portName, 
                    boolean isEjb) 
            {
                return "http://"+host+":"+port+"/"+getServiceUri(applicationRoot, 
                        serviceName, portName, isEjb)+"?Tester"; //NOI18N
            }
            
        };
    }

    @Override
    public boolean isFeatureSupported(Feature feature) {
        if (feature == JaxWs.Feature.JSR109 || feature == 
            JaxWs.Feature.SERVICE_REF_INJECTION || feature == 
                JaxWs.Feature.TESTER_PAGE || feature == JaxWs.Feature.WSIT) 
        {
            return true;
        } else {
            return false;
        }    
    }
    
    private String resolveImplementationVersion() throws IOException {
        // take webservices-tools.jar file
        File wsToolsJar = new File(root, WEBSERVICES_TOOLS_JAR);
        // alternatively take appserv-ws.jar file
        if (!wsToolsJar.exists()) wsToolsJar = new File(root, APPSERV_WS_JAR);
        
        if (wsToolsJar.exists()) {            
            JarFile jarFile = new JarFile(wsToolsJar);
            JarEntry entry = jarFile.getJarEntry("com/sun/tools/ws/version.properties"); //NOI18N
            if (entry != null) {
                InputStream is = jarFile.getInputStream(entry);
                BufferedReader r = new BufferedReader(new InputStreamReader(is));
                String ln = null;
                String ver = null;
                while ((ln=r.readLine()) != null) {
                    String line = ln.trim();
                    if (line.startsWith("major-version=")) { //NOI18N
                        ver = line.substring(14);
                    }
                }
                r.close();
                return ver;
            }           
        }
        return null;
    }
    
    private class JaxWsTool implements WSToolImplementation {
        JaxWs.Tool tool;
        JaxWsTool(JaxWs.Tool tool) {
            this.tool = tool;
        }

        @Override
        public String getName() {
            return tool.getName();
        }

        @Override
        public URL[] getLibraries() {
            File wsToolsJar = new File(root, WEBSERVICES_TOOLS_JAR);  //NOI18N
            try {
                if (wsToolsJar.exists()) { // WSIT installed on top
                    return new URL[] {
                        wsToolsJar.toURI().toURL(),     // NOI18N
                        new File(root, WEBSERVICES_RT_JAR).toURI().toURL(),           // NOI18N
                        new File(root, TOOLS_JAR).toURI().toURL(),      //NOI18N
                        new File(root, JSTL_JAR).toURI().toURL(),       //NOI18N
                        new File(root, JAVA_EE_JAR).toURI().toURL(),    //NOI18N
                        new File(root, APPSERV_WS_JAR).toURI().toURL(), //NOI18N
                        new File(root, MAIL_JAR).toURI().toURL(),       //NOI18N
                        new File(root, ACTIVATION_JAR).toURI().toURL()  //NOI18N
                    };
                } else {                                                // regular appserver
                    return new URL[] {
                        new File(root, TOOLS_JAR).toURI().toURL(),        //NOI18N
                        new File(root, JSTL_JAR).toURI().toURL(),         //NOI18N
                        new File(root, JAVA_EE_JAR).toURI().toURL(),      //NOI18N
                        new File(root, APPSERV_WS_JAR).toURI().toURL(),   //NOI18N
                        new File(root, MAIL_JAR).toURI().toURL(),         //NOI18N
                        new File(root, ACTIVATION_JAR).toURI().toURL()    //NOI18N
                    };
                }
            } catch (MalformedURLException ex) {
                return new URL[0];
            } 
        }
        
    }

}
