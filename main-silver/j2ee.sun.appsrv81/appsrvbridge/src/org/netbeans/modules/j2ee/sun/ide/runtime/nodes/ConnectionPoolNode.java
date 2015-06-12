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
package org.netbeans.modules.j2ee.sun.ide.runtime.nodes;


import org.netbeans.modules.j2ee.sun.bridge.apis.AppserverMgmtController;
import org.netbeans.modules.j2ee.sun.util.NodeTypes;


/**
 *
 *
 */
public class ConnectionPoolNode extends ResourceLeafNode {

    private static final String NODE_TYPE = NodeTypes.CONNECTION_POOL;
    
    /**
     * Constructor for creating an instance of a ConnectionPool leaf node.
     *
     * @param connectionPoolName
     */
    public ConnectionPoolNode(final AppserverMgmtController controller, 
            final String connectionPoolName) {
        super(controller, NODE_TYPE, connectionPoolName);
    }
    
    
    /**
     * Return the SheetProperties to be displayed for this JDBC Connection Pool 
     * entry.
     *
     * @return A java.util.Map containing all JDBC Connection Pool 
     *         properties.
     */
    protected java.util.Map getSheetProperties() {      
        return getAppserverMgmtController().
            getJDBCConnectionPoolProperties(getResourceName(), 
                getPropertiesToIgnore());
    }
    
    
    /**
     * Sets the property as an attribute to the underlying AMX mbeans. It 
     * usually will delegate to the controller object which is responsible for
     * finding the correct AMX mbean objectname in order to execute a 
     * JMX setAttribute.
     *
     * @param attrName The name of the property to be set.
     * @param value The value retrieved from the property sheet to be set in the
     *        backend.
     * @returns the updated Attribute accessed from the Sheet.
     */
    public javax.management.Attribute setSheetProperty(String attrName, Object value) {
        return getAppserverMgmtController().
            setJDBCConnectionPoolProperty(getResourceName(), attrName, value);
    }
     
    
    /**
     * Removes resource.
     */
    public void remove() {
        getAppserverMgmtController().
            deleteJDBCConnectionPool(getResourceName());
    }
    
    
    /**
     * Removes all the jdbc resources that reference this jdbc connection pool.
     */
    public void removeDependentJDBCResources() {
       getAppserverMgmtController().
            deleteDependentJDBCResources(getResourceName());
    }
    
}
