/**
 *	This generated bean class Icon matches the schema element 'iconType'.
 *  The root bean class is Application
 *
 *	===============================================================
 *	
 *	
 *		The icon type contains small-icon and large-icon elements
 *		that specify the file names for small and large GIF, JPEG,
 *		or PNG icon images used to represent the parent element in a
 *		GUI tool.
 *	
 *		The xml:lang attribute defines the language that the
 *		icon file names are provided in. Its value is "en" (English)
 *		by default.
 *	
 *	      
 *	===============================================================
 * @Generated
 */

package org.netbeans.modules.j2ee.dd.impl.application.model_5;

import org.w3c.dom.*;
import org.netbeans.modules.schema2beans.*;
import java.beans.*;
import java.util.*;

// BEGIN_NOI18N

public class Icon extends org.netbeans.modules.j2ee.dd.impl.common.EnclosingBean
	 implements org.netbeans.modules.j2ee.dd.api.common.Icon
{

	static Vector comparators = new Vector();
	private static final org.netbeans.modules.schema2beans.Version runtimeVersion = new org.netbeans.modules.schema2beans.Version(5, 0, 0);
	;	// NOI18N

	static public final String XMLLANG = "XmlLang";	// NOI18N
	static public final String ID = "Id";	// NOI18N
	static public final String SMALL_ICON = "SmallIcon";	// NOI18N
	static public final String LARGE_ICON = "LargeIcon";	// NOI18N

	public Icon() {
		this(Common.USE_DEFAULT_VALUES);
	}

	public Icon(int options)
	{
		super(comparators, runtimeVersion);
		// Properties (see root bean comments for the bean graph)
		initPropertyTables(2);
		this.createProperty("small-icon", 	// NOI18N
			SMALL_ICON, 
			Common.TYPE_0_1 | Common.TYPE_STRING | Common.TYPE_KEY, 
			java.lang.String.class);
		this.createProperty("large-icon", 	// NOI18N
			LARGE_ICON, 
			Common.TYPE_0_1 | Common.TYPE_STRING | Common.TYPE_KEY, 
			java.lang.String.class);
		this.initialize(options);
	}

	// Setting the default values of the properties
	void initialize(int options) {

	}

	// This attribute is optional
	public void setXmlLang(java.lang.String value) {
		setAttributeValue(XMLLANG, value);
	}

	//
	public java.lang.String getXmlLang() {
		return getAttributeValue(XMLLANG);
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
	public void setSmallIcon(java.lang.String value) {
		this.setValue(SMALL_ICON, value);
	}

	//
	public java.lang.String getSmallIcon() {
		return (java.lang.String)this.getValue(SMALL_ICON);
	}

	// This attribute is optional
	public void setLargeIcon(java.lang.String value) {
		this.setValue(LARGE_ICON, value);
	}

	//
	public java.lang.String getLargeIcon() {
		return (java.lang.String)this.getValue(LARGE_ICON);
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
		// Validating property xmlLang
		// Validating property id
		if (getId() != null) {
			// has whitespace restriction
			if (restrictionFailure) {
				throw new org.netbeans.modules.schema2beans.ValidateException("getId() whiteSpace (collapse)", org.netbeans.modules.schema2beans.ValidateException.FailureType.DATA_RESTRICTION, "id", this);	// NOI18N
			}
		}
		// Validating property smallIcon
		// Validating property largeIcon
	}

	// Dump the content of this bean returning it as a String
	public void dump(StringBuffer str, String indent){
		String s;
		Object o;
		org.netbeans.modules.schema2beans.BaseBean n;
		str.append(indent);
		str.append("SmallIcon");	// NOI18N
		str.append(indent+"\t");	// NOI18N
		str.append("<");	// NOI18N
		o = this.getSmallIcon();
		str.append((o==null?"null":o.toString().trim()));	// NOI18N
		str.append(">\n");	// NOI18N
		this.dumpAttributes(SMALL_ICON, 0, str, indent);

		str.append(indent);
		str.append("LargeIcon");	// NOI18N
		str.append(indent+"\t");	// NOI18N
		str.append("<");	// NOI18N
		o = this.getLargeIcon();
		str.append((o==null?"null":o.toString().trim()));	// NOI18N
		str.append(">\n");	// NOI18N
		this.dumpAttributes(LARGE_ICON, 0, str, indent);

	}
	public String dumpBeanNode(){
		StringBuffer str = new StringBuffer();
		str.append("Icon\n");	// NOI18N
		this.dump(str, "\n  ");	// NOI18N
		return str.toString();
	}}

// END_NOI18N


/*
		The following schema file has been used for generation:

<?xml version="1.0" encoding="UTF-8"?>
<xsd:schema xmlns="http://www.w3.org/2001/XMLSchema"
	    targetNamespace="http://java.sun.com/xml/ns/javaee"
	    xmlns:javaee="http://java.sun.com/xml/ns/javaee"
	    xmlns:xsd="http://www.w3.org/2001/XMLSchema"
	    elementFormDefault="qualified"
	    attributeFormDefault="unqualified"
	    version="5">
  <xsd:annotation>
    <xsd:documentation>
      @(#)application_5.xsds	1.17 08/05/05
    </xsd:documentation>
  </xsd:annotation>

  <xsd:annotation>
    <xsd:documentation>

      DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

      Copyright 2003-2010 Oracle and/or its affiliates. All rights reserved.

Oracle and Java are registered trademarks of Oracle and/or its affiliates.
Other names may be trademarks of their respective owners.

      The contents of this file are subject to the terms of either the
      GNU General Public License Version 2 only ("GPL") or the Common
      Development and Distribution License("CDDL") (collectively, the
      "License").  You may not use this file except in compliance with
      the License. You can obtain a copy of the License at
      https://glassfish.dev.java.net/public/CDDL+GPL.html or
      glassfish/bootstrap/legal/LICENSE.txt.  See the License for the
      specific language governing permissions and limitations under the
      License.

      When distributing the software, include this License Header
      Notice in each file and include the License file at
      glassfish/bootstrap/legal/LICENSE.txt.  Sun designates this
      particular file as subject to the "Classpath" exception as
      provided by Sun in the GPL Version 2 section of the License file
      that accompanied this code.  If applicable, add the following
      below the License Header, with the fields enclosed by brackets []
      replaced by your own identifying information:
      "Portions Copyrighted [year] [name of copyright owner]"

      Contributor(s):

      If you wish your version of this file to be governed by only the
      CDDL or only the GPL Version 2, indicate your decision by adding
      "[Contributor] elects to include this software in this
      distribution under the [CDDL or GPL Version 2] license."  If you
      don't indicate a single choice of license, a recipient has the
      option to distribute your version of this file under either the
      CDDL, the GPL Version 2 or to extend the choice of license to its
      licensees as provided above.  However, if you add GPL Version 2
      code and therefore, elected the GPL Version 2 license, then the
      option applies only if the new code is made subject to such
      option by the copyright holder.

    </xsd:documentation>
  </xsd:annotation>

  <xsd:annotation>
    <xsd:documentation>
      <![CDATA[

	This is the XML Schema for the application 5 deployment
	descriptor.  The deployment descriptor must be named
	"META-INF/application.xml" in the application's ear file.
	All application deployment descriptors must indicate
	the application schema by using the Java EE namespace:

	http://java.sun.com/xml/ns/javaee

	and indicate the version of the schema by
	using the version element as shown below:

	    <application xmlns="http://java.sun.com/xml/ns/javaee"
	      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	      xsi:schemaLocation="http://java.sun.com/xml/ns/javaee
		http://java.sun.com/xml/ns/javaee/application_5.xsd"
	      version="5">
	      ...
	    </application>

	The instance documents may indicate the published version of
	the schema using the xsi:schemaLocation attribute for Java EE
	namespace with the following location:

	http://java.sun.com/xml/ns/javaee/application_5.xsd

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

  <xsd:include schemaLocation="javaee_5.xsd"/>


<!-- **************************************************** -->

  <xsd:element name="application" type="javaee:applicationType">
    <xsd:annotation>
      <xsd:documentation>

	The application element is the root element of a Java EE
	application deployment descriptor.

      </xsd:documentation>
    </xsd:annotation>

    <xsd:unique name="context-root-uniqueness">
      <xsd:annotation>
	<xsd:documentation>

	  The context-root element content must be unique
	  in the ear.

	</xsd:documentation>
      </xsd:annotation>
      <xsd:selector xpath="javaee:module/javaee:web"/>
      <xsd:field    xpath="javaee:context-root"/>
    </xsd:unique>

    <xsd:unique name="security-role-uniqueness">
      <xsd:annotation>
	<xsd:documentation>

	  The security-role-name element content
	  must be unique in the ear.

	</xsd:documentation>
      </xsd:annotation>
      <xsd:selector xpath="javaee:security-role"/>
      <xsd:field    xpath="javaee:role-name"/>
    </xsd:unique>

  </xsd:element>

<!-- **************************************************** -->

  <xsd:complexType name="applicationType">
    <xsd:annotation>
      <xsd:documentation>

	The applicationType defines the structure of the
	application.

      </xsd:documentation>
    </xsd:annotation>

    <xsd:sequence>
      <xsd:group ref="javaee:descriptionGroup"/>
      <xsd:element name="module"
		   type="javaee:moduleType"
		   maxOccurs="unbounded">
	<xsd:annotation>
	  <xsd:documentation>

	    The application deployment descriptor must have one
	    module element for each Java EE module in the
	    application package. A module element is defined
	    by moduleType definition.

	  </xsd:documentation>
	</xsd:annotation>
      </xsd:element>
      <xsd:element name="security-role"
		   type="javaee:security-roleType"
		   minOccurs="0"
		   maxOccurs="unbounded"/>
      <xsd:element name="library-directory"
		   type="javaee:pathType"
		   minOccurs="0"
		   maxOccurs="1">
	<xsd:annotation>
	  <xsd:documentation>

	    The library-directory element specifies the pathname
	    of a directory within the application package, relative
	    to the top level of the application package.  All files
	    named "*.jar" in this directory must be made available
	    in the class path of all components included in this
	    application package.  If this element isn't specified,
	    the directory named "lib" is searched.  An empty element
	    may be used to disable searching.

	  </xsd:documentation>
	</xsd:annotation>
      </xsd:element>
    </xsd:sequence>
    <xsd:attribute name="version"
		   type="javaee:dewey-versionType"
		   fixed="5"
		   use="required">
      <xsd:annotation>
	<xsd:documentation>

	  The required value for the version is 5.

	</xsd:documentation>
      </xsd:annotation>
    </xsd:attribute>

    <xsd:attribute name="id" type="xsd:ID"/>
  </xsd:complexType>

<!-- **************************************************** -->

  <xsd:complexType name="moduleType">
    <xsd:annotation>
      <xsd:documentation>

	The moduleType defines a single Java EE module and contains a
	connector, ejb, java, or web element, which indicates the
	module type and contains a path to the module file, and an
	optional alt-dd element, which specifies an optional URI to
	the post-assembly version of the deployment descriptor.

      </xsd:documentation>
    </xsd:annotation>

    <xsd:sequence>
      <xsd:choice>
	<xsd:element name="connector"
		     type="javaee:pathType">
	  <xsd:annotation>
	    <xsd:documentation>

	      The connector element specifies the URI of a
	      resource adapter archive file, relative to the
	      top level of the application package.

	    </xsd:documentation>
	  </xsd:annotation>
	</xsd:element>
	<xsd:element name="ejb"
		     type="javaee:pathType">
	  <xsd:annotation>
	    <xsd:documentation>

	      The ejb element specifies the URI of an ejb-jar,
	      relative to the top level of the application
	      package.

	    </xsd:documentation>
	  </xsd:annotation>
	</xsd:element>
	<xsd:element name="java"
		     type="javaee:pathType">
	  <xsd:annotation>
	    <xsd:documentation>

	      The java element specifies the URI of a java
	      application client module, relative to the top
	      level of the application package.

	    </xsd:documentation>
	  </xsd:annotation>
	</xsd:element>
	<xsd:element name="web"
		     type="javaee:webType"/>
      </xsd:choice>
      <xsd:element name="alt-dd"
		   type="javaee:pathType"
		   minOccurs="0">
	<xsd:annotation>
	  <xsd:documentation>

	    The alt-dd element specifies an optional URI to the
	    post-assembly version of the deployment descriptor
	    file for a particular Java EE module.  The URI must
	    specify the full pathname of the deployment
	    descriptor file relative to the application's root
	    directory. If alt-dd is not specified, the deployer
	    must read the deployment descriptor from the default
	    location and file name required by the respective
	    component specification.

	  </xsd:documentation>
	</xsd:annotation>
      </xsd:element>
    </xsd:sequence>
    <xsd:attribute name="id" type="xsd:ID"/>
  </xsd:complexType>

<!-- **************************************************** -->

  <xsd:complexType name="webType">
    <xsd:annotation>
      <xsd:documentation>

	The webType defines the web-uri and context-root of
	a web application module.

      </xsd:documentation>
    </xsd:annotation>
    <xsd:sequence>
      <xsd:element name="web-uri"
		   type="javaee:pathType">
	<xsd:annotation>
	  <xsd:documentation>

	    The web-uri element specifies the URI of a web
	    application file, relative to the top level of the
	    application package.

	  </xsd:documentation>
	</xsd:annotation>
      </xsd:element>
      <xsd:element name="context-root"
		   type="javaee:string">

	<xsd:annotation>
	  <xsd:documentation>

	    The context-root element specifies the context root
	    of a web application.

	  </xsd:documentation>
	</xsd:annotation>
      </xsd:element>
    </xsd:sequence>
    <xsd:attribute name="id" type="xsd:ID"/>
  </xsd:complexType>

</xsd:schema>


*/
