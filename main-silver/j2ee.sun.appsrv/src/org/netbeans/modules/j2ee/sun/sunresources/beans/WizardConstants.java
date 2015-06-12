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
/*
 * WizardConstants.java
*
 * Created on October 15, 2002, 12:24 PM
 */

package org.netbeans.modules.j2ee.sun.sunresources.beans;

/**
 *
 * @author  shirleyc
 */
public interface WizardConstants {

    //common
    public static final String __General = "general";
    public static final String __Properties = "properties";
    public static final String __PropertiesURL = "propertiesUrl";
//////    public static final String __RegisterResource = "register-resource";

    //jdbc-connection-pool
    public static final String __Name = "name";
    public static final String __DatasourceClassname = "datasource-classname";
    public static final String __XADatasourceClassname = "datasource-classname-xa";
    public static final String __CPDatasourceClassname = "datasource-classname-cp";
    public static final String __ResType = "res-type";
    public static final String __SteadyPoolSize = "steady-pool-size";
    public static final String __MaxPoolSize = "max-pool-size";
    public static final String __MaxWaitTimeInMillis = "max-wait-time-in-millis";
    public static final String __PoolResizeQuantity = "pool-resize-quantity";
    public static final String __IdleTimeoutInSeconds = "idle-timeout-in-seconds";
    public static final String __TransactionIsolationLevel = "transaction-isolation-level";
    public static final String __IsIsolationLevelGuaranteed = "is-isolation-level-guaranteed";
    public static final String __IsConnectionValidationRequired = "is-connection-validation-required";
    public static final String __ConnectionValidationMethod = "connection-validation-method";
    public static final String __ValidationTableName = "validation-table-name";
    public static final String __FailAllConnections = "fail-all-connections";
    public static final String __Description = "description";
    public static final String __NonTransactionalConnections = "non-transactional-connections";
    public static final String __AllowNonComponentCallers = "allow-non-component-callers";
    public static final String __JdbcConnectionPool = "jdbc-connection-pool"; 
        
    public static final String __DatabaseVendor = "database-vendor";
    public static final String __DatabaseName = "databaseName";
    public static final String __Url = "URL";
    public static final String __User = "User";
    public static final String __Password = "Password";
    public static final String __NotApplicable = "NA";
    public static final String __IsXA = "isXA";  
    public static final String __IsCPExisting = "is-cp-existing";
        
    //jdbc-resource
    //Contains __Description
    public static final String __JndiName = "jndi-name";
    public static final String __PoolName = "pool-name";
    public static final String __JdbcObjectType = "object-type";
    public static final String __Enabled = "enabled";
    public static final String __JdbcResource = "jdbc-resource";
    
    //persistence-manager-factory
    //Contains __BeanjndiName and __BeanisEnabled and __Description
    public static final String __FactoryClass = "factory-class";
    public static final String __JdbcResourceJndiName = "jdbc-resource-jndi-name";
    public static final String __PersistenceManagerFactoryResource = "persistence-manager-factory-resource";
    

    
    
    //mail-resource
    //Contains __JndiName and __Enabled and __Description   
    public static final String __StoreProtocol = "store-protocol";
    public static final String __StoreProtocolClass = "store-protocol-class";
    public static final String __TransportProtocol = "transport-protocol";
    public static final String __TransportProtocolClass = "transport-protocol-class";
    public static final String __Host = "host";
    public static final String __MailUser = "user";
    public static final String __From = "from";
    public static final String __Debug = "debug";
    public static final String __MailResource = "mail-resource";
    
    
    //jms-resource
    //Contains __JndiName, __ResType, __Enabled, __Properties, __Description
    public static final String __JmsResource = "jms-resource";
    public static final String __Properties2 = "properties2";
    public static final String __AdminObjPropertyName = "Name";
    
    //jms-resource Bean
    //Contains __BeanjndiName and __BeanisEnabled and __Description   
////    public static final String __JMSResType = "resType";
    
    public static final String __JavaMessageJndiName = "jndi_name";
    public static final String __JavaMessageResType = "res-type";
    public static final String __AdminObjResAdapterName = "res-adapter";
    public static final String __ConnectorPoolResAdName = "resource-adapter-name";
    public static final String __ConnectorPoolConnDefName = "connection-definition-name";
    
    //Default Names for the resources
    public static final String __ConnectionPoolResource = "connectionPool";
    public static final String __JDBCResource = "jdbc/myDatasource";
    public static final String __JMSResource = "jms/myQueue";
    public static final String __ADMINOBJResource = "jms/myQueue";
    public static final String __CONNECTORResource = "jms/myConnector";
    public static final String __MAILResource = "mail/mySession";
    public static final String __PersistenceResource = "persistence";
    public static final String __DynamicWizPanel = "dynamicPanel"; //to identify ds & cp created dynamically
    public static final String __SunResourceExt = "sun-resource";
    
    //First Step - temporary workaround
    public static final String __FirstStepChoose = "Choose";
    //Resource Folder
    public static final String __SunResourceFolder = "setup";
    
    //Operations for getting resourceproperties
    public static final String __GetJdbcResource = "getJdbcResource";
    public static final String __GetJdbcConnectionPool = "getJdbcConnectionPool";
    public static final String __GetMailResource = "getMailResource";
    public static final String __GetJmsResource = "getJmsResource";
    public static final String __GetAdmObjResource = "getAdminObjectResource";
    public static final String __GetConnectorResource = "getConnectorResource";
    public static final String __GetConnPoolResource = "getConnectorConnectionPool";
    public static final String __GetPMFResource = "getPersistenceManagerFactoryResource";
    public static final String __GetJdbcResourceByName = "getJdbcResourceByJndiName";
    public static final String __GetJdbcConnectionPoolByName = "getJdbcConnectionPoolByName";
    public static final String __GetProperties = "getProperties";
    public static final String __SetProperty = "setProperty";
    public static final String MAP_RESOURCES = "com.sun.appserv:type=resources,category=config";//NOI18N
       
    public static final String __DerbyDatabaseName = "DatabaseName";
    public static final String __DerbyPortNumber = "PortNumber";
    public static final String __ServerName = "serverName";
    public static final String __InformixHostName = "IfxIFXHOST";
    public static final String __InformixServer = "InformixServer";
    public static final String __DerbyConnAttr = "connectionAttributes";
    
    public static final String __PortNumber = "portNumber";
    public static final String __SID = "SID";
    public static final String __DriverClass = "driverClass";
    
    public static final String[] VendorsExtraProps = {"sun_db2", "sun_oracle", "sun_msftsql",
        "sun_sybase", "db2", "microsoft_sql", "post-gre-sql", "mysql", "datadirect_sql",
        "datadirect_db2", "datadirect_informix", "datadirect_sybase", "datadirect_oracle", 
        "jtds_sql", "jtds_sybase", "informix", "sybase2", "as400"};
    
    
    public static final String[] VendorsDBNameProp = {"sun_db2", "sun_oracle", "sun_msftsql",
        "db2", "microsoft_sql", "post-gre-sql", "mysql", "datadirect_sql", 
        "datadirect_db2", "datadirect_informix", "datadirect_sybase", "datadirect_oracle",
        "jtds_sql", "jtds_sybase", "informix"};
    
    
    public static final String[] Reqd_DBName = {"sun_db2", "sun_msftsql", "datadirect_sql", 
        "microsoft_sql", "datadirect_db2", "datadirect_informix", "datadirect_sybase"};
    
    //Suffixes to create Resource File Names (for Zero Config)
    public static final String __ConnPoolSuffix = "Pool"; 
    /** Suffix to create connector connection pool for JMS Resource. */
    public static final String __ConnPoolSuffixJMS = "-Connection-Pool";
    
    public static final String __QUEUE = "javax.jms.Queue"; // NOI18N
    public static final String __TOPIC = "javax.jms.Topic"; // NOI18N
    public static final String QUEUE_PROP = "PhysicalQueue"; // NOI18N
    public static final String TOPIC_PROP = "PhysicalTopic"; // NOI18N
    
    public static final String __QUEUE_CNTN_FACTORY = "javax.jms.QueueConnectionFactory"; // NOI18N
    public static final String __TOPIC_CNTN_FACTORY = "javax.jms.TopicConnectionFactory"; // NOI18N
    public static final String __CNTN_FACTORY = "javax.jms.ConnectionFactory"; // NOI18N
    
    public static final String __JmsResAdapter = "jmsra";  
    //Create Resource Operations
    public static final String __CreateCP = "createJdbcConnectionPool";
    public static final String __CreateDS = "createJdbcResource";
    public static final String __CreatePMF = "createPersistenceManagerFactoryResource";
    public static final String __CreateMail = "createMailResource";
    public static final String __CreateAdmObj = "createAdminObjectResource"; 
    public static final String __CreateConnector = "createConnectorResource";
    public static final String __CreateConnPool = "createConnectorConnectionPool";
    
    public static final String __Type_Datasource = "javax.sql.DataSource"; //NOI18N
    public static final String __Type_XADatasource = "javax.sql.XADataSource"; //NOI18N
    public static final String __Type_ConnectionPoolDataSource = "javax.sql.ConnectionPoolDataSource"; //NOI18N
}
