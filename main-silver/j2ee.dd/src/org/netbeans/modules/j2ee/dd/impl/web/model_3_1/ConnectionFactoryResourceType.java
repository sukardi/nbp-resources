/**
 *	This generated bean class ConnectionFactoryResourceType matches the schema element 'connection-factory-resourceType'.
 *  The root bean class is WebApp
 *
 *	===============================================================
 *	
 *	
 *	        Configuration of a Connector Connection Factory resource.
 *	        
 *	      
 *	===============================================================
 * @Generated
 */

package org.netbeans.modules.j2ee.dd.impl.web.model_3_1;

import org.w3c.dom.*;
import org.netbeans.modules.schema2beans.*;
import java.beans.*;
import java.util.*;

// BEGIN_NOI18N

public class ConnectionFactoryResourceType extends org.netbeans.modules.schema2beans.BaseBean
{

	static Vector comparators = new Vector();
	private static final org.netbeans.modules.schema2beans.Version runtimeVersion = new org.netbeans.modules.schema2beans.Version(5, 0, 0);
	;	// NOI18N

	static public final String ID = "Id";	// NOI18N
	static public final String DESCRIPTION = "Description";	// NOI18N
	static public final String DESCRIPTIONID = "DescriptionId";	// NOI18N
	static public final String DESCRIPTIONXMLLANG = "DescriptionXmlLang";	// NOI18N
	static public final String NAME = "Name";	// NOI18N
	static public final String INTERFACE_NAME = "InterfaceName";	// NOI18N
	static public final String RESOURCE_ADAPTER = "ResourceAdapter";	// NOI18N
	static public final String RESOURCEADAPTERID = "ResourceAdapterId";	// NOI18N
	static public final String MAX_POOL_SIZE = "MaxPoolSize";	// NOI18N
	static public final String MAXPOOLSIZEID = "MaxPoolSizeId";	// NOI18N
	static public final String MIN_POOL_SIZE = "MinPoolSize";	// NOI18N
	static public final String MINPOOLSIZEID = "MinPoolSizeId";	// NOI18N
	static public final String TRANSACTION_SUPPORT = "TransactionSupport";	// NOI18N
	static public final String PROPERTY2 = "Property2";	// NOI18N

	public ConnectionFactoryResourceType() {
		this(Common.USE_DEFAULT_VALUES);
	}

	public ConnectionFactoryResourceType(int options)
	{
		super(comparators, runtimeVersion);
		// Properties (see root bean comments for the bean graph)
		initPropertyTables(8);
		this.createProperty("description", 	// NOI18N
			DESCRIPTION, 
			Common.TYPE_0_1 | Common.TYPE_STRING | Common.TYPE_KEY, 
			java.lang.String.class);
		this.createAttribute(DESCRIPTION, "id", "Id", 
						AttrProp.CDATA | AttrProp.IMPLIED,
						null, null);
		this.createAttribute(DESCRIPTION, "xml:lang", "XmlLang", 
						AttrProp.CDATA | AttrProp.IMPLIED,
						null, null);
		this.createProperty("name", 	// NOI18N
			NAME, 
			Common.TYPE_1 | Common.TYPE_STRING | Common.TYPE_KEY, 
			java.lang.String.class);
		this.createProperty("interface-name", 	// NOI18N
			INTERFACE_NAME, 
			Common.TYPE_1 | Common.TYPE_STRING | Common.TYPE_KEY, 
			java.lang.String.class);
		this.createProperty("resource-adapter", 	// NOI18N
			RESOURCE_ADAPTER, 
			Common.TYPE_1 | Common.TYPE_STRING | Common.TYPE_KEY, 
			java.lang.String.class);
		this.createAttribute(RESOURCE_ADAPTER, "id", "Id", 
						AttrProp.CDATA | AttrProp.IMPLIED,
						null, null);
		this.createProperty("max-pool-size", 	// NOI18N
			MAX_POOL_SIZE, 
			Common.TYPE_0_1 | Common.TYPE_STRING | Common.TYPE_KEY, 
			java.math.BigInteger.class);
		this.createAttribute(MAX_POOL_SIZE, "id", "Id", 
						AttrProp.CDATA | AttrProp.IMPLIED,
						null, null);
		this.createProperty("min-pool-size", 	// NOI18N
			MIN_POOL_SIZE, 
			Common.TYPE_0_1 | Common.TYPE_STRING | Common.TYPE_KEY, 
			java.math.BigInteger.class);
		this.createAttribute(MIN_POOL_SIZE, "id", "Id", 
						AttrProp.CDATA | AttrProp.IMPLIED,
						null, null);
		this.createProperty("transaction-support", 	// NOI18N
			TRANSACTION_SUPPORT, 
			Common.TYPE_0_1 | Common.TYPE_STRING | Common.TYPE_KEY, 
			java.lang.String.class);
		this.createProperty("property", 	// NOI18N
			PROPERTY2, 
			Common.TYPE_0_N | Common.TYPE_BEAN | Common.TYPE_KEY, 
			PropertyType.class);
		this.createAttribute(PROPERTY2, "id", "Id", 
						AttrProp.CDATA | AttrProp.IMPLIED,
						null, null);
		this.initialize(options);
	}

	// Setting the default values of the properties
	void initialize(int options) {

	}

	// This attribute is optional
	public void setId(java.lang.String value) {
		setAttributeValue(ID, value);
	}

	//
	public java.lang.String getId() {
		return getAttributeValue(ID);
	}

	// This attribute is optional
	public void setDescription(java.lang.String value) {
		this.setValue(DESCRIPTION, value);
	}

	//
	public java.lang.String getDescription() {
		return (java.lang.String)this.getValue(DESCRIPTION);
	}

	// This attribute is optional
	public void setDescriptionId(java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(DESCRIPTION) == 0) {
			setValue(DESCRIPTION, "");
		}
		setAttributeValue(DESCRIPTION, "Id", value);
	}

	//
	public java.lang.String getDescriptionId() {
		// If our element does not exist, then the attribute does not exist.
		if (size(DESCRIPTION) == 0) {
			return null;
		} else {
			return getAttributeValue(DESCRIPTION, "Id");
		}
	}

	// This attribute is optional
	public void setDescriptionXmlLang(java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(DESCRIPTION) == 0) {
			setValue(DESCRIPTION, "");
		}
		setAttributeValue(DESCRIPTION, "XmlLang", value);
	}

	//
	public java.lang.String getDescriptionXmlLang() {
		// If our element does not exist, then the attribute does not exist.
		if (size(DESCRIPTION) == 0) {
			return null;
		} else {
			return getAttributeValue(DESCRIPTION, "XmlLang");
		}
	}

	// This attribute is mandatory
	public void setName(java.lang.String value) {
		this.setValue(NAME, value);
	}

	//
	public java.lang.String getName() {
		return (java.lang.String)this.getValue(NAME);
	}

	// This attribute is mandatory
	public void setInterfaceName(java.lang.String value) {
		this.setValue(INTERFACE_NAME, value);
	}

	//
	public java.lang.String getInterfaceName() {
		return (java.lang.String)this.getValue(INTERFACE_NAME);
	}

	// This attribute is mandatory
	public void setResourceAdapter(java.lang.String value) {
		this.setValue(RESOURCE_ADAPTER, value);
	}

	//
	public java.lang.String getResourceAdapter() {
		return (java.lang.String)this.getValue(RESOURCE_ADAPTER);
	}

	// This attribute is optional
	public void setResourceAdapterId(java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(RESOURCE_ADAPTER) == 0) {
			setValue(RESOURCE_ADAPTER, "");
		}
		setAttributeValue(RESOURCE_ADAPTER, "Id", value);
	}

	//
	public java.lang.String getResourceAdapterId() {
		// If our element does not exist, then the attribute does not exist.
		if (size(RESOURCE_ADAPTER) == 0) {
			return null;
		} else {
			return getAttributeValue(RESOURCE_ADAPTER, "Id");
		}
	}

	// This attribute is optional
	public void setMaxPoolSize(java.math.BigInteger value) {
		this.setValue(MAX_POOL_SIZE, value);
	}

	//
	public java.math.BigInteger getMaxPoolSize() {
		return (java.math.BigInteger)this.getValue(MAX_POOL_SIZE);
	}

	// This attribute is optional
	public void setMaxPoolSizeId(java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(MAX_POOL_SIZE) == 0) {
			setValue(MAX_POOL_SIZE, "");
		}
		setAttributeValue(MAX_POOL_SIZE, "Id", value);
	}

	//
	public java.lang.String getMaxPoolSizeId() {
		// If our element does not exist, then the attribute does not exist.
		if (size(MAX_POOL_SIZE) == 0) {
			return null;
		} else {
			return getAttributeValue(MAX_POOL_SIZE, "Id");
		}
	}

	// This attribute is optional
	public void setMinPoolSize(java.math.BigInteger value) {
		this.setValue(MIN_POOL_SIZE, value);
	}

	//
	public java.math.BigInteger getMinPoolSize() {
		return (java.math.BigInteger)this.getValue(MIN_POOL_SIZE);
	}

	// This attribute is optional
	public void setMinPoolSizeId(java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(MAX_POOL_SIZE) == 0) {
			setValue(MAX_POOL_SIZE, "");
		}
		setAttributeValue(MAX_POOL_SIZE, "Id", value);
	}

	//
	public java.lang.String getMinPoolSizeId() {
		// If our element does not exist, then the attribute does not exist.
		if (size(MAX_POOL_SIZE) == 0) {
			return null;
		} else {
			return getAttributeValue(MAX_POOL_SIZE, "Id");
		}
	}

	// This attribute is optional
	public void setTransactionSupport(java.lang.String value) {
		this.setValue(TRANSACTION_SUPPORT, value);
	}

	//
	public java.lang.String getTransactionSupport() {
		return (java.lang.String)this.getValue(TRANSACTION_SUPPORT);
	}

	// This attribute is an array, possibly empty
	public void setProperty2(int index, PropertyType value) {
		this.setValue(PROPERTY2, index, value);
	}

	//
	public PropertyType getProperty2(int index) {
		return (PropertyType)this.getValue(PROPERTY2, index);
	}

	// Return the number of properties
	public int sizeProperty2() {
		return this.size(PROPERTY2);
	}

	// This attribute is an array, possibly empty
	public void setProperty2(PropertyType[] value) {
		this.setValue(PROPERTY2, value);
	}

	//
	public PropertyType[] getProperty2() {
		return (PropertyType[])this.getValues(PROPERTY2);
	}

	// Add a new element returning its index in the list
	public int addProperty2(org.netbeans.modules.j2ee.dd.impl.web.model_3_1.PropertyType value) {
		int positionOfNewItem = this.addValue(PROPERTY2, value);
		return positionOfNewItem;
	}

	//
	// Remove an element using its reference
	// Returns the index the element had in the list
	//
	public int removeProperty2(org.netbeans.modules.j2ee.dd.impl.web.model_3_1.PropertyType value) {
		return this.removeValue(PROPERTY2, value);
	}

	/**
	 * Create a new bean using it's default constructor.
	 * This does not add it to any bean graph.
	 */
	public PropertyType newPropertyType() {
		return new PropertyType();
	}

	//
	public static void addComparator(org.netbeans.modules.schema2beans.BeanComparator c) {
		comparators.add(c);
	}

	//
	public static void removeComparator(org.netbeans.modules.schema2beans.BeanComparator c) {
		comparators.remove(c);
	}
	public void validate() throws org.netbeans.modules.schema2beans.ValidateException {
		boolean restrictionFailure = false;
		boolean restrictionPassed = false;
		// Validating property id
		if (getId() != null) {
			// has whitespace restriction
			if (restrictionFailure) {
				throw new org.netbeans.modules.schema2beans.ValidateException("getId() whiteSpace (collapse)", org.netbeans.modules.schema2beans.ValidateException.FailureType.DATA_RESTRICTION, "id", this);	// NOI18N
			}
		}
		// Validating property description
		// Validating property descriptionId
		if (getDescriptionId() != null) {
			// has whitespace restriction
			if (restrictionFailure) {
				throw new org.netbeans.modules.schema2beans.ValidateException("getDescriptionId() whiteSpace (collapse)", org.netbeans.modules.schema2beans.ValidateException.FailureType.DATA_RESTRICTION, "descriptionId", this);	// NOI18N
			}
		}
		// Validating property descriptionXmlLang
		// Validating property name
		if (getName() == null) {
			throw new org.netbeans.modules.schema2beans.ValidateException("getName() == null", org.netbeans.modules.schema2beans.ValidateException.FailureType.NULL_VALUE, "name", this);	// NOI18N
		}
		// Validating property interfaceName
		if (getInterfaceName() == null) {
			throw new org.netbeans.modules.schema2beans.ValidateException("getInterfaceName() == null", org.netbeans.modules.schema2beans.ValidateException.FailureType.NULL_VALUE, "interfaceName", this);	// NOI18N
		}
		// Validating property resourceAdapter
		if (getResourceAdapter() == null) {
			throw new org.netbeans.modules.schema2beans.ValidateException("getResourceAdapter() == null", org.netbeans.modules.schema2beans.ValidateException.FailureType.NULL_VALUE, "resourceAdapter", this);	// NOI18N
		}
		// has whitespace restriction
		if (restrictionFailure) {
			throw new org.netbeans.modules.schema2beans.ValidateException("getResourceAdapter() whiteSpace (collapse)", org.netbeans.modules.schema2beans.ValidateException.FailureType.DATA_RESTRICTION, "resourceAdapter", this);	// NOI18N
		}
		// Validating property resourceAdapterId
		if (getResourceAdapterId() != null) {
			// has whitespace restriction
			if (restrictionFailure) {
				throw new org.netbeans.modules.schema2beans.ValidateException("getResourceAdapterId() whiteSpace (collapse)", org.netbeans.modules.schema2beans.ValidateException.FailureType.DATA_RESTRICTION, "resourceAdapterId", this);	// NOI18N
			}
		}
		// Validating property maxPoolSize
		// Validating property maxPoolSizeId
		if (getMaxPoolSizeId() != null) {
			// has whitespace restriction
			if (restrictionFailure) {
				throw new org.netbeans.modules.schema2beans.ValidateException("getMaxPoolSizeId() whiteSpace (collapse)", org.netbeans.modules.schema2beans.ValidateException.FailureType.DATA_RESTRICTION, "maxPoolSizeId", this);	// NOI18N
			}
		}
		// Validating property minPoolSize
		// Validating property minPoolSizeId
		if (getMinPoolSizeId() != null) {
			// has whitespace restriction
			if (restrictionFailure) {
				throw new org.netbeans.modules.schema2beans.ValidateException("getMinPoolSizeId() whiteSpace (collapse)", org.netbeans.modules.schema2beans.ValidateException.FailureType.DATA_RESTRICTION, "minPoolSizeId", this);	// NOI18N
			}
		}
		// Validating property transactionSupport
		if (getTransactionSupport() != null) {
			final java.lang.String[] enumRestrictionTransactionSupport = {"NoTransaction", "LocalTransaction", "XATransaction"};
			restrictionFailure = true;
			for (int _index2 = 0; 
				_index2 < enumRestrictionTransactionSupport.length; ++_index2) {
				if (enumRestrictionTransactionSupport[_index2].equals(getTransactionSupport())) {
					restrictionFailure = false;
					break;
				}
			}
			if (restrictionFailure) {
				throw new org.netbeans.modules.schema2beans.ValidateException("getTransactionSupport() enumeration test", org.netbeans.modules.schema2beans.ValidateException.FailureType.ENUM_RESTRICTION, "transactionSupport", this);	// NOI18N
			}
		}
		// Validating property property2
		for (int _index = 0; _index < sizeProperty2(); ++_index) {
			org.netbeans.modules.j2ee.dd.impl.web.model_3_1.PropertyType element = getProperty2(_index);
			if (element != null) {
				element.validate();
			}
		}
	}

	// Dump the content of this bean returning it as a String
	public void dump(StringBuffer str, String indent){
		String s;
		Object o;
		org.netbeans.modules.schema2beans.BaseBean n;
		str.append(indent);
		str.append("Description");	// NOI18N
		str.append(indent+"\t");	// NOI18N
		str.append("<");	// NOI18N
		o = this.getDescription();
		str.append((o==null?"null":o.toString().trim()));	// NOI18N
		str.append(">\n");	// NOI18N
		this.dumpAttributes(DESCRIPTION, 0, str, indent);

		str.append(indent);
		str.append("Name");	// NOI18N
		str.append(indent+"\t");	// NOI18N
		str.append("<");	// NOI18N
		o = this.getName();
		str.append((o==null?"null":o.toString().trim()));	// NOI18N
		str.append(">\n");	// NOI18N
		this.dumpAttributes(NAME, 0, str, indent);

		str.append(indent);
		str.append("InterfaceName");	// NOI18N
		str.append(indent+"\t");	// NOI18N
		str.append("<");	// NOI18N
		o = this.getInterfaceName();
		str.append((o==null?"null":o.toString().trim()));	// NOI18N
		str.append(">\n");	// NOI18N
		this.dumpAttributes(INTERFACE_NAME, 0, str, indent);

		str.append(indent);
		str.append("ResourceAdapter");	// NOI18N
		str.append(indent+"\t");	// NOI18N
		str.append("<");	// NOI18N
		o = this.getResourceAdapter();
		str.append((o==null?"null":o.toString().trim()));	// NOI18N
		str.append(">\n");	// NOI18N
		this.dumpAttributes(RESOURCE_ADAPTER, 0, str, indent);

		str.append(indent);
		str.append("MaxPoolSize");	// NOI18N
		str.append(indent+"\t");	// NOI18N
		str.append("<");	// NOI18N
		o = this.getMaxPoolSize();
		str.append((o==null?"null":o.toString().trim()));	// NOI18N
		str.append(">\n");	// NOI18N
		this.dumpAttributes(MAX_POOL_SIZE, 0, str, indent);

		str.append(indent);
		str.append("MinPoolSize");	// NOI18N
		str.append(indent+"\t");	// NOI18N
		str.append("<");	// NOI18N
		o = this.getMinPoolSize();
		str.append((o==null?"null":o.toString().trim()));	// NOI18N
		str.append(">\n");	// NOI18N
		this.dumpAttributes(MIN_POOL_SIZE, 0, str, indent);

		str.append(indent);
		str.append("TransactionSupport");	// NOI18N
		str.append(indent+"\t");	// NOI18N
		str.append("<");	// NOI18N
		o = this.getTransactionSupport();
		str.append((o==null?"null":o.toString().trim()));	// NOI18N
		str.append(">\n");	// NOI18N
		this.dumpAttributes(TRANSACTION_SUPPORT, 0, str, indent);

		str.append(indent);
		str.append("Property2["+this.sizeProperty2()+"]");	// NOI18N
		for(int i=0; i<this.sizeProperty2(); i++)
		{
			str.append(indent+"\t");
			str.append("#"+i+":");
			n = (org.netbeans.modules.schema2beans.BaseBean) this.getProperty2(i);
			if (n != null)
				n.dump(str, indent + "\t");	// NOI18N
			else
				str.append(indent+"\tnull");	// NOI18N
			this.dumpAttributes(PROPERTY2, i, str, indent);
		}

	}
	public String dumpBeanNode(){
		StringBuffer str = new StringBuffer();
		str.append("ConnectionFactoryResourceType\n");	// NOI18N
		this.dump(str, "\n  ");	// NOI18N
		return str.toString();
	}}

// END_NOI18N


/*
		The following schema file has been used for generation:

<?xml version="1.0" encoding="UTF-8"?>
<xsd:schema xmlns="http://www.w3.org/2001/XMLSchema"
            targetNamespace="http://xmlns.jcp.org/xml/ns/javaee"
            xmlns:javaee="http://xmlns.jcp.org/xml/ns/javaee"
            xmlns:xsd="http://www.w3.org/2001/XMLSchema"
            elementFormDefault="qualified"
            attributeFormDefault="unqualified"
            version="3.1">
  <xsd:annotation>
    <xsd:documentation>

      DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
      
      Copyright (c) 2009-2013 Oracle and/or its affiliates. All rights reserved.
      
      The contents of this file are subject to the terms of either the GNU
      General Public License Version 2 only ("GPL") or the Common Development
      and Distribution License("CDDL") (collectively, the "License").  You
      may not use this file except in compliance with the License.  You can
      obtain a copy of the License at
      https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
      or packager/legal/LICENSE.txt.  See the License for the specific
      language governing permissions and limitations under the License.
      
      When distributing the software, include this License Header Notice in each
      file and include the License file at packager/legal/LICENSE.txt.
      
      GPL Classpath Exception:
      Oracle designates this particular file as subject to the "Classpath"
      exception as provided by Oracle in the GPL Version 2 section of the License
      file that accompanied this code.
      
      Modifications:
      If applicable, add the following below the License Header, with the fields
      enclosed by brackets [] replaced by your own identifying information:
      "Portions Copyright [year] [name of copyright owner]"
      
      Contributor(s):
      If you wish your version of this file to be governed by only the CDDL or
      only the GPL Version 2, indicate your decision by adding "[Contributor]
      elects to include this software in this distribution under the [CDDL or GPL
      Version 2] license."  If you don't indicate a single choice of license, a
      recipient has the option to distribute your version of this file under
      either the CDDL, the GPL Version 2 or to extend the choice of license to
      its licensees as provided above.  However, if you add GPL Version 2 code
      and therefore, elected the GPL Version 2 license, then the option applies
      only if the new code is made subject to such option by the copyright
      holder.
      
    </xsd:documentation>
  </xsd:annotation>

  <xsd:annotation>
    <xsd:documentation>
      <![CDATA[[
      This is the XML Schema for the Servlet 3.1 deployment descriptor.
      The deployment descriptor must be named "WEB-INF/web.xml" in the
      web application's war file.  All Servlet deployment descriptors
      must indicate the web application schema by using the Java EE
      namespace:
      
      http://xmlns.jcp.org/xml/ns/javaee 
      
      and by indicating the version of the schema by 
      using the version element as shown below: 
      
      <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="..."
      version="3.1"> 
      ...
      </web-app>
      
      The instance documents may indicate the published version of
      the schema using the xsi:schemaLocation attribute for Java EE
      namespace with the following location:
      
      http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd
      
      ]]>
    </xsd:documentation>
  </xsd:annotation>

  <xsd:annotation>
    <xsd:documentation>

      The following conventions apply to all Java EE
      deployment descriptor elements unless indicated otherwise.
      
      - In elements that specify a pathname to a file within the
      same JAR file, relative filenames (i.e., those not
      starting with "/") are considered relative to the root of
      the JAR file's namespace.  Absolute filenames (i.e., those
      starting with "/") also specify names in the root of the
      JAR file's namespace.  In general, relative names are
      preferred.  The exception is .war files where absolute
      names are preferred for consistency with the Servlet API.
      
    </xsd:documentation>
  </xsd:annotation>

  <xsd:include schemaLocation="web-common_3_1.xsd"/>


<!-- **************************************************** -->

  <xsd:element name="web-app"
               type="javaee:web-appType">
    <xsd:annotation>
      <xsd:documentation>

        The web-app element is the root of the deployment
        descriptor for a web application.  Note that the sub-elements
        of this element can be in the arbitrary order. Because of
        that, the multiplicity of the elements of distributable,
        session-config, welcome-file-list, jsp-config, login-config,
        and locale-encoding-mapping-list was changed from "?" to "*"
        in this schema.  However, the deployment descriptor instance
        file must not contain multiple elements of session-config,
        jsp-config, and login-config. When there are multiple elements of
        welcome-file-list or locale-encoding-mapping-list, the container
        must concatenate the element contents.  The multiple occurence
        of the element distributable is redundant and the container
        treats that case exactly in the same way when there is only
        one distributable. 
        
      </xsd:documentation>
    </xsd:annotation>
    <xsd:unique name="web-common-servlet-name-uniqueness">
      <xsd:annotation>
        <xsd:documentation>

          The servlet element contains the name of a servlet.
          The name must be unique within the web application.
          
        </xsd:documentation>
      </xsd:annotation>
      <xsd:selector xpath="javaee:servlet"/>
      <xsd:field xpath="javaee:servlet-name"/>
    </xsd:unique>
    <xsd:unique name="web-common-filter-name-uniqueness">
      <xsd:annotation>
        <xsd:documentation>

          The filter element contains the name of a filter.
          The name must be unique within the web application.
          
        </xsd:documentation>
      </xsd:annotation>
      <xsd:selector xpath="javaee:filter"/>
      <xsd:field xpath="javaee:filter-name"/>
    </xsd:unique>
    <xsd:unique name="web-common-ejb-local-ref-name-uniqueness">
      <xsd:annotation>
        <xsd:documentation>

          The ejb-local-ref-name element contains the name of an EJB
          reference. The EJB reference is an entry in the web
          application's environment and is relative to the
          java:comp/env context.  The name must be unique within
          the web application.
          
          It is recommended that name is prefixed with "ejb/".
          
        </xsd:documentation>
      </xsd:annotation>
      <xsd:selector xpath="javaee:ejb-local-ref"/>
      <xsd:field xpath="javaee:ejb-ref-name"/>
    </xsd:unique>
    <xsd:unique name="web-common-ejb-ref-name-uniqueness">
      <xsd:annotation>
        <xsd:documentation>

          The ejb-ref-name element contains the name of an EJB
          reference. The EJB reference is an entry in the web
          application's environment and is relative to the
          java:comp/env context.  The name must be unique within
          the web application.
          
          It is recommended that name is prefixed with "ejb/".
          
        </xsd:documentation>
      </xsd:annotation>
      <xsd:selector xpath="javaee:ejb-ref"/>
      <xsd:field xpath="javaee:ejb-ref-name"/>
    </xsd:unique>
    <xsd:unique name="web-common-resource-env-ref-uniqueness">
      <xsd:annotation>
        <xsd:documentation>

          The resource-env-ref-name element specifies the name of
          a resource environment reference; its value is the
          environment entry name used in the web application code.
          The name is a JNDI name relative to the java:comp/env
          context and must be unique within a web application.
          
        </xsd:documentation>
      </xsd:annotation>
      <xsd:selector xpath="javaee:resource-env-ref"/>
      <xsd:field xpath="javaee:resource-env-ref-name"/>
    </xsd:unique>
    <xsd:unique name="web-common-message-destination-ref-uniqueness">
      <xsd:annotation>
        <xsd:documentation>

          The message-destination-ref-name element specifies the name of
          a message destination reference; its value is the
          environment entry name used in the web application code.
          The name is a JNDI name relative to the java:comp/env
          context and must be unique within a web application.
          
        </xsd:documentation>
      </xsd:annotation>
      <xsd:selector xpath="javaee:message-destination-ref"/>
      <xsd:field xpath="javaee:message-destination-ref-name"/>
    </xsd:unique>
    <xsd:unique name="web-common-res-ref-name-uniqueness">
      <xsd:annotation>
        <xsd:documentation>

          The res-ref-name element specifies the name of a
          resource manager connection factory reference.  The name
          is a JNDI name relative to the java:comp/env context.
          The name must be unique within a web application.
          
        </xsd:documentation>
      </xsd:annotation>
      <xsd:selector xpath="javaee:resource-ref"/>
      <xsd:field xpath="javaee:res-ref-name"/>
    </xsd:unique>
    <xsd:unique name="web-common-env-entry-name-uniqueness">
      <xsd:annotation>
        <xsd:documentation>

          The env-entry-name element contains the name of a web
          application's environment entry.  The name is a JNDI
          name relative to the java:comp/env context.  The name
          must be unique within a web application.
          
        </xsd:documentation>
      </xsd:annotation>
      <xsd:selector xpath="javaee:env-entry"/>
      <xsd:field xpath="javaee:env-entry-name"/>
    </xsd:unique>
    <xsd:key name="web-common-role-name-key">
      <xsd:annotation>
        <xsd:documentation>

          A role-name-key is specified to allow the references
          from the security-role-refs.
          
        </xsd:documentation>
      </xsd:annotation>
      <xsd:selector xpath="javaee:security-role"/>
      <xsd:field xpath="javaee:role-name"/>
    </xsd:key>
    <xsd:keyref name="web-common-role-name-references"
                refer="javaee:web-common-role-name-key">
      <xsd:annotation>
        <xsd:documentation>

          The keyref indicates the references from
          security-role-ref to a specified role-name.
          
        </xsd:documentation>
      </xsd:annotation>
      <xsd:selector xpath="javaee:servlet/javaee:security-role-ref"/>
      <xsd:field xpath="javaee:role-link"/>
    </xsd:keyref>
  </xsd:element>


<!-- **************************************************** -->

  <xsd:complexType name="web-appType">
    <xsd:choice minOccurs="0"
                maxOccurs="unbounded">
      <xsd:element name="module-name"
                   type="javaee:string"
                   minOccurs="0"/>
      <xsd:group ref="javaee:web-commonType"/>
      <xsd:element name="deny-uncovered-http-methods"
                   type="javaee:emptyType">
        <xsd:annotation>
          <xsd:documentation>

            When specified, this element causes uncovered http methods
            to be denied. For every url-pattern that is the target of a 
            security-constrant, this element causes all HTTP methods that
            are NOT covered (by a security constraint) at the url-pattern
            to be denied.
            
          </xsd:documentation>
        </xsd:annotation>
      </xsd:element>
      <xsd:element name="absolute-ordering"
                   type="javaee:absoluteOrderingType"/>
    </xsd:choice>
    <xsd:attributeGroup ref="javaee:web-common-attributes"/>
  </xsd:complexType>


<!-- **************************************************** -->

  <xsd:complexType name="absoluteOrderingType">
    <xsd:annotation>
      <xsd:documentation>

        Please see section 8.2.2 of the specification for details.
        
      </xsd:documentation>
    </xsd:annotation>
    <xsd:choice minOccurs="0"
                maxOccurs="unbounded">
      <xsd:element name="name"
                   type="javaee:java-identifierType"
                   minOccurs="0"
                   maxOccurs="unbounded"/>
      <xsd:element name="others"
                   type="javaee:ordering-othersType"
                   minOccurs="0"
                   maxOccurs="1"/>
    </xsd:choice>
  </xsd:complexType>

</xsd:schema>

*/
