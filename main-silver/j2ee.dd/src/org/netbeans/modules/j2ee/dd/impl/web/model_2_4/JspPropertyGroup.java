/**
 *	This generated bean class JspPropertyGroup matches the schema element 'jsp-property-groupType'.
 *  The root bean class is WebApp
 *
 *	===============================================================
 *	
 *	
 *		This group keeps the usage of the contained description related
 *		elements consistent across J2EE deployment descriptors.
 *	
 *		All elements may occur multiple times with different languages,
 *		to support localization of the content.
 *	
 *	      
 *	===============================================================
 * @Generated
 */

package org.netbeans.modules.j2ee.dd.impl.web.model_2_4;

import org.w3c.dom.*;
import org.netbeans.modules.schema2beans.*;
import java.beans.*;
import java.util.*;

// BEGIN_NOI18N

public class JspPropertyGroup extends org.netbeans.modules.j2ee.dd.impl.common.ComponentBeanMultiple
	 implements  
                    org.netbeans.modules.j2ee.dd.api.web.JspPropertyGroup
                
{

	static Vector comparators = new Vector();
	private static final org.netbeans.modules.schema2beans.Version runtimeVersion = new org.netbeans.modules.schema2beans.Version(5, 0, 0);
	;	// NOI18N

	static public final String ID = "Id";	// NOI18N
	static public final String DESCRIPTION = "Description";	// NOI18N
	static public final String DESCRIPTIONID = "DescriptionId";	// NOI18N
	static public final String DESCRIPTIONXMLLANG = "DescriptionXmlLang";	// NOI18N
	static public final String DISPLAY_NAME = "DisplayName";	// NOI18N
	static public final String DISPLAYNAMEID = "DisplayNameId";	// NOI18N
	static public final String DISPLAYNAMEXMLLANG = "DisplayNameXmlLang";	// NOI18N
	static public final String ICON = "Icon";	// NOI18N
	static public final String URL_PATTERN = "UrlPattern";	// NOI18N
	static public final String EL_IGNORED = "ElIgnored";	// NOI18N
	static public final String PAGE_ENCODING = "PageEncoding";	// NOI18N
	static public final String PAGEENCODINGID = "PageEncodingId";	// NOI18N
	static public final String SCRIPTING_INVALID = "ScriptingInvalid";	// NOI18N
	static public final String IS_XML = "IsXml";	// NOI18N
	static public final String INCLUDE_PRELUDE = "IncludePrelude";	// NOI18N
	static public final String INCLUDE_CODA = "IncludeCoda";	// NOI18N

	public JspPropertyGroup() {
		this(Common.USE_DEFAULT_VALUES);
	}

	public JspPropertyGroup(int options)
	{
		super(comparators, runtimeVersion);
		// Properties (see root bean comments for the bean graph)
		initPropertyTables(10);
		this.createProperty("description", 	// NOI18N
			DESCRIPTION, 
			Common.TYPE_0_N | Common.TYPE_STRING | Common.TYPE_KEY, 
			java.lang.String.class);
		this.createAttribute(DESCRIPTION, "id", "Id", 
						AttrProp.CDATA | AttrProp.IMPLIED,
						null, null);
		this.createAttribute(DESCRIPTION, "xml:lang", "XmlLang", 
						AttrProp.CDATA | AttrProp.IMPLIED,
						null, null);
		this.createProperty("display-name", 	// NOI18N
			DISPLAY_NAME, 
			Common.TYPE_0_N | Common.TYPE_STRING | Common.TYPE_KEY, 
			java.lang.String.class);
		this.createAttribute(DISPLAY_NAME, "id", "Id", 
						AttrProp.CDATA | AttrProp.IMPLIED,
						null, null);
		this.createAttribute(DISPLAY_NAME, "xml:lang", "XmlLang", 
						AttrProp.CDATA | AttrProp.IMPLIED,
						null, null);
		this.createProperty("icon", 	// NOI18N
			ICON, 
			Common.TYPE_0_N | Common.TYPE_BEAN | Common.TYPE_KEY, 
			Icon.class);
		this.createAttribute(ICON, "xml:lang", "XmlLang", 
						AttrProp.CDATA | AttrProp.IMPLIED,
						null, null);
		this.createAttribute(ICON, "id", "Id", 
						AttrProp.CDATA | AttrProp.IMPLIED,
						null, null);
		this.createProperty("url-pattern", 	// NOI18N
			URL_PATTERN, 
			Common.TYPE_1_N | Common.TYPE_STRING | Common.TYPE_KEY, 
			java.lang.String.class);
		this.createProperty("el-ignored", 	// NOI18N
			EL_IGNORED, 
			Common.TYPE_0_1 | Common.TYPE_BOOLEAN | Common.TYPE_SHOULD_NOT_BE_EMPTY | Common.TYPE_KEY, 
			Boolean.class);
		this.createProperty("page-encoding", 	// NOI18N
			PAGE_ENCODING, 
			Common.TYPE_0_1 | Common.TYPE_STRING | Common.TYPE_KEY, 
			java.lang.String.class);
		this.createAttribute(PAGE_ENCODING, "id", "Id", 
						AttrProp.CDATA | AttrProp.IMPLIED,
						null, null);
		this.createProperty("scripting-invalid", 	// NOI18N
			SCRIPTING_INVALID, 
			Common.TYPE_0_1 | Common.TYPE_BOOLEAN | Common.TYPE_SHOULD_NOT_BE_EMPTY | Common.TYPE_KEY, 
			Boolean.class);
		this.createProperty("is-xml", 	// NOI18N
			IS_XML, 
			Common.TYPE_0_1 | Common.TYPE_BOOLEAN | Common.TYPE_SHOULD_NOT_BE_EMPTY | Common.TYPE_KEY, 
			Boolean.class);
		this.createProperty("include-prelude", 	// NOI18N
			INCLUDE_PRELUDE, 
			Common.TYPE_0_N | Common.TYPE_STRING | Common.TYPE_KEY, 
			java.lang.String.class);
		this.createProperty("include-coda", 	// NOI18N
			INCLUDE_CODA, 
			Common.TYPE_0_N | Common.TYPE_STRING | Common.TYPE_KEY, 
			java.lang.String.class);
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

	// This attribute is an array, possibly empty
	public void setDescription(int index, java.lang.String value) {
		this.setValue(DESCRIPTION, index, value);
	}

	//
	public java.lang.String getDescription(int index) {
		return (java.lang.String)this.getValue(DESCRIPTION, index);
	}

	// Return the number of properties
	public int sizeDescription() {
		return this.size(DESCRIPTION);
	}

	// This attribute is an array, possibly empty
	public void setDescription(java.lang.String[] value) {
		this.setValue(DESCRIPTION, value);
	}

	//
	public java.lang.String[] getDescription() {
		return (java.lang.String[])this.getValues(DESCRIPTION);
	}

	// Add a new element returning its index in the list
	public int addDescription(java.lang.String value) {
		int positionOfNewItem = this.addValue(DESCRIPTION, value);
		return positionOfNewItem;
	}

	//
	// Remove an element using its reference
	// Returns the index the element had in the list
	//
	public int removeDescription(java.lang.String value) {
		return this.removeValue(DESCRIPTION, value);
	}

	// This attribute is an array, possibly empty
	public void setDescriptionId(int index, java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(DESCRIPTION) == 0) {
			addValue(DESCRIPTION, "");
		}
		setAttributeValue(DESCRIPTION, index, "Id", value);
	}

	//
	public java.lang.String getDescriptionId(int index) {
		// If our element does not exist, then the attribute does not exist.
		if (size(DESCRIPTION) == 0) {
			return null;
		} else {
			return getAttributeValue(DESCRIPTION, index, "Id");
		}
	}

	// Return the number of properties
	public int sizeDescriptionId() {
		return this.size(DESCRIPTION);
	}

	// This attribute is an array, possibly empty
	public void setDescriptionXmlLang(int index, java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(DESCRIPTION) == 0) {
			addValue(DESCRIPTION, "");
		}
		setAttributeValue(DESCRIPTION, index, "XmlLang", value);
	}

	//
	public java.lang.String getDescriptionXmlLang(int index) {
		// If our element does not exist, then the attribute does not exist.
		if (size(DESCRIPTION) == 0) {
			return null;
		} else {
			return getAttributeValue(DESCRIPTION, index, "XmlLang");
		}
	}

	// Return the number of properties
	public int sizeDescriptionXmlLang() {
		return this.size(DESCRIPTION);
	}

	// This attribute is an array, possibly empty
	public void setDisplayName(int index, java.lang.String value) {
		this.setValue(DISPLAY_NAME, index, value);
	}

	//
	public java.lang.String getDisplayName(int index) {
		return (java.lang.String)this.getValue(DISPLAY_NAME, index);
	}

	// Return the number of properties
	public int sizeDisplayName() {
		return this.size(DISPLAY_NAME);
	}

	// This attribute is an array, possibly empty
	public void setDisplayName(java.lang.String[] value) {
		this.setValue(DISPLAY_NAME, value);
	}

	//
	public java.lang.String[] getDisplayName() {
		return (java.lang.String[])this.getValues(DISPLAY_NAME);
	}

	// Add a new element returning its index in the list
	public int addDisplayName(java.lang.String value) {
		int positionOfNewItem = this.addValue(DISPLAY_NAME, value);
		return positionOfNewItem;
	}

	//
	// Remove an element using its reference
	// Returns the index the element had in the list
	//
	public int removeDisplayName(java.lang.String value) {
		return this.removeValue(DISPLAY_NAME, value);
	}

	// This attribute is an array, possibly empty
	public void setDisplayNameId(int index, java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(DISPLAY_NAME) == 0) {
			addValue(DISPLAY_NAME, "");
		}
		setAttributeValue(DISPLAY_NAME, index, "Id", value);
	}

	//
	public java.lang.String getDisplayNameId(int index) {
		// If our element does not exist, then the attribute does not exist.
		if (size(DISPLAY_NAME) == 0) {
			return null;
		} else {
			return getAttributeValue(DISPLAY_NAME, index, "Id");
		}
	}

	// Return the number of properties
	public int sizeDisplayNameId() {
		return this.size(DISPLAY_NAME);
	}

	// This attribute is an array, possibly empty
	public void setDisplayNameXmlLang(int index, java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(DISPLAY_NAME) == 0) {
			addValue(DISPLAY_NAME, "");
		}
		setAttributeValue(DISPLAY_NAME, index, "XmlLang", value);
	}

	//
	public java.lang.String getDisplayNameXmlLang(int index) {
		// If our element does not exist, then the attribute does not exist.
		if (size(DISPLAY_NAME) == 0) {
			return null;
		} else {
			return getAttributeValue(DISPLAY_NAME, index, "XmlLang");
		}
	}

	// Return the number of properties
	public int sizeDisplayNameXmlLang() {
		return this.size(DISPLAY_NAME);
	}

	// This attribute is an array, possibly empty
	public void setIcon(int index,  
                    org.netbeans.modules.j2ee.dd.api.common.Icon
                 valueInterface) {
		Icon value = (Icon) valueInterface;
		this.setValue(ICON, index, value);
	}

	//
	public  
                    org.netbeans.modules.j2ee.dd.api.common.Icon
                 getIcon(int index) {
		return (Icon)this.getValue(ICON, index);
	}

	// Return the number of properties
	public int sizeIcon() {
		return this.size(ICON);
	}

	// This attribute is an array, possibly empty
	public void setIcon( 
                    org.netbeans.modules.j2ee.dd.api.common.Icon
                [] value) {
		this.setValue(ICON, value);
	}

	//
	public  
                    org.netbeans.modules.j2ee.dd.api.common.Icon
                [] getIcon() {
		return (Icon[])this.getValues(ICON);
	}

	// Add a new element returning its index in the list
	public int addIcon( 
                    org.netbeans.modules.j2ee.dd.api.common.Icon
                 valueInterface) {
		Icon value = (Icon) valueInterface;
		int positionOfNewItem = this.addValue(ICON, value);
		return positionOfNewItem;
	}

	//
	// Remove an element using its reference
	// Returns the index the element had in the list
	//
	public int removeIcon( 
                    org.netbeans.modules.j2ee.dd.api.common.Icon
                 valueInterface) {
		Icon value = (Icon) valueInterface;
		return this.removeValue(ICON, value);
	}

	// This attribute is an array containing at least one element
	public void setUrlPattern(int index, java.lang.String value) {
		this.setValue(URL_PATTERN, index, value);
	}

	//
	public java.lang.String getUrlPattern(int index) {
		return (java.lang.String)this.getValue(URL_PATTERN, index);
	}

	// Return the number of properties
	public int sizeUrlPattern() {
		return this.size(URL_PATTERN);
	}

	// This attribute is an array containing at least one element
	public void setUrlPattern(java.lang.String[] value) {
		this.setValue(URL_PATTERN, value);
	}

	//
	public java.lang.String[] getUrlPattern() {
		return (java.lang.String[])this.getValues(URL_PATTERN);
	}

	// Add a new element returning its index in the list
	public int addUrlPattern(java.lang.String value) {
		int positionOfNewItem = this.addValue(URL_PATTERN, value);
		return positionOfNewItem;
	}

	//
	// Remove an element using its reference
	// Returns the index the element had in the list
	//
	public int removeUrlPattern(java.lang.String value) {
		return this.removeValue(URL_PATTERN, value);
	}

	// This attribute is optional
	public void setElIgnored(boolean value) {
		this.setValue(EL_IGNORED, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
	}

	//
	public boolean isElIgnored() {
		Boolean ret = (Boolean)this.getValue(EL_IGNORED);
		if (ret == null)
			ret = (Boolean)Common.defaultScalarValue(Common.TYPE_BOOLEAN);
		return ((java.lang.Boolean)ret).booleanValue();
	}

	// This attribute is optional
	public void setPageEncoding(java.lang.String value) {
		this.setValue(PAGE_ENCODING, value);
	}

	//
	public java.lang.String getPageEncoding() {
		return (java.lang.String)this.getValue(PAGE_ENCODING);
	}

	// This attribute is optional
	public void setPageEncodingId(java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(PAGE_ENCODING) == 0) {
			setValue(PAGE_ENCODING, "");
		}
		setAttributeValue(PAGE_ENCODING, "Id", value);
	}

	//
	public java.lang.String getPageEncodingId() {
		// If our element does not exist, then the attribute does not exist.
		if (size(PAGE_ENCODING) == 0) {
			return null;
		} else {
			return getAttributeValue(PAGE_ENCODING, "Id");
		}
	}

	// This attribute is optional
	public void setScriptingInvalid(boolean value) {
		this.setValue(SCRIPTING_INVALID, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
	}

	//
	public boolean isScriptingInvalid() {
		Boolean ret = (Boolean)this.getValue(SCRIPTING_INVALID);
		if (ret == null)
			ret = (Boolean)Common.defaultScalarValue(Common.TYPE_BOOLEAN);
		return ((java.lang.Boolean)ret).booleanValue();
	}

	// This attribute is optional
	public void setIsXml(boolean value) {
		this.setValue(IS_XML, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
	}

	//
	public boolean isIsXml() {
		Boolean ret = (Boolean)this.getValue(IS_XML);
		if (ret == null)
			ret = (Boolean)Common.defaultScalarValue(Common.TYPE_BOOLEAN);
		return ((java.lang.Boolean)ret).booleanValue();
	}

	// This attribute is an array, possibly empty
	public void setIncludePrelude(int index, java.lang.String value) {
		this.setValue(INCLUDE_PRELUDE, index, value);
	}

	//
	public java.lang.String getIncludePrelude(int index) {
		return (java.lang.String)this.getValue(INCLUDE_PRELUDE, index);
	}

	// Return the number of properties
	public int sizeIncludePrelude() {
		return this.size(INCLUDE_PRELUDE);
	}

	// This attribute is an array, possibly empty
	public void setIncludePrelude(java.lang.String[] value) {
		this.setValue(INCLUDE_PRELUDE, value);
	}

	//
	public java.lang.String[] getIncludePrelude() {
		return (java.lang.String[])this.getValues(INCLUDE_PRELUDE);
	}

	// Add a new element returning its index in the list
	public int addIncludePrelude(java.lang.String value) {
		int positionOfNewItem = this.addValue(INCLUDE_PRELUDE, value);
		return positionOfNewItem;
	}

	//
	// Remove an element using its reference
	// Returns the index the element had in the list
	//
	public int removeIncludePrelude(java.lang.String value) {
		return this.removeValue(INCLUDE_PRELUDE, value);
	}

	// This attribute is an array, possibly empty
	public void setIncludeCoda(int index, java.lang.String value) {
		this.setValue(INCLUDE_CODA, index, value);
	}

	//
	public java.lang.String getIncludeCoda(int index) {
		return (java.lang.String)this.getValue(INCLUDE_CODA, index);
	}

	// Return the number of properties
	public int sizeIncludeCoda() {
		return this.size(INCLUDE_CODA);
	}

	// This attribute is an array, possibly empty
	public void setIncludeCoda(java.lang.String[] value) {
		this.setValue(INCLUDE_CODA, value);
	}

	//
	public java.lang.String[] getIncludeCoda() {
		return (java.lang.String[])this.getValues(INCLUDE_CODA);
	}

	// Add a new element returning its index in the list
	public int addIncludeCoda(java.lang.String value) {
		int positionOfNewItem = this.addValue(INCLUDE_CODA, value);
		return positionOfNewItem;
	}

	//
	// Remove an element using its reference
	// Returns the index the element had in the list
	//
	public int removeIncludeCoda(java.lang.String value) {
		return this.removeValue(INCLUDE_CODA, value);
	}

	/**
	 * Create a new bean using it's default constructor.
	 * This does not add it to any bean graph.
	 */
	public org.netbeans.modules.j2ee.dd.api.common.Icon newIcon() {
		return new Icon();
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
		// Validating property descriptionXmlLang
		// Validating property displayName
		for (int _index = 0; _index < sizeDisplayName(); ++_index) {
			java.lang.String element = getDisplayName(_index);
			if (element != null) {
				// has whitespace restriction
				if (restrictionFailure) {
					throw new org.netbeans.modules.schema2beans.ValidateException("element whiteSpace (collapse)", org.netbeans.modules.schema2beans.ValidateException.FailureType.DATA_RESTRICTION, "displayName", this);	// NOI18N
				}
			}
		}
		// Validating property displayNameId
		// Validating property displayNameXmlLang
		// Validating property icon
		for (int _index = 0; _index < sizeIcon(); ++_index) {
			org.netbeans.modules.j2ee.dd.impl.web.model_2_4.Icon element = (org.netbeans.modules.j2ee.dd.impl.web.model_2_4.Icon) getIcon(_index);
			if (element != null) {
				((Icon)element).validate();
			}
		}
		// Validating property urlPattern
		if (sizeUrlPattern() == 0) {
			throw new org.netbeans.modules.schema2beans.ValidateException("sizeUrlPattern() == 0", org.netbeans.modules.schema2beans.ValidateException.FailureType.NULL_VALUE, "urlPattern", this);	// NOI18N
		}
		// Validating property elIgnored
		{
			boolean patternPassed = false;
			if ((isElIgnored() ? "true" : "false").matches("(true|false)")) {
				patternPassed = true;
			}
			restrictionFailure = !patternPassed;
		}
		if (restrictionFailure) {
			throw new org.netbeans.modules.schema2beans.ValidateException("isElIgnored()", org.netbeans.modules.schema2beans.ValidateException.FailureType.DATA_RESTRICTION, "elIgnored", this);	// NOI18N
		}
		// Validating property pageEncoding
		if (getPageEncoding() != null) {
			// has whitespace restriction
			if (restrictionFailure) {
				throw new org.netbeans.modules.schema2beans.ValidateException("getPageEncoding() whiteSpace (collapse)", org.netbeans.modules.schema2beans.ValidateException.FailureType.DATA_RESTRICTION, "pageEncoding", this);	// NOI18N
			}
		}
		// Validating property pageEncodingId
		if (getPageEncodingId() != null) {
			// has whitespace restriction
			if (restrictionFailure) {
				throw new org.netbeans.modules.schema2beans.ValidateException("getPageEncodingId() whiteSpace (collapse)", org.netbeans.modules.schema2beans.ValidateException.FailureType.DATA_RESTRICTION, "pageEncodingId", this);	// NOI18N
			}
		}
		// Validating property scriptingInvalid
		{
			boolean patternPassed = false;
			if ((isScriptingInvalid() ? "true" : "false").matches("(true|false)")) {
				patternPassed = true;
			}
			restrictionFailure = !patternPassed;
		}
		if (restrictionFailure) {
			throw new org.netbeans.modules.schema2beans.ValidateException("isScriptingInvalid()", org.netbeans.modules.schema2beans.ValidateException.FailureType.DATA_RESTRICTION, "scriptingInvalid", this);	// NOI18N
		}
		// Validating property isXml
		{
			boolean patternPassed = false;
			if ((isIsXml() ? "true" : "false").matches("(true|false)")) {
				patternPassed = true;
			}
			restrictionFailure = !patternPassed;
		}
		if (restrictionFailure) {
			throw new org.netbeans.modules.schema2beans.ValidateException("isIsXml()", org.netbeans.modules.schema2beans.ValidateException.FailureType.DATA_RESTRICTION, "isXml", this);	// NOI18N
		}
		// Validating property includePrelude
		// Validating property includeCoda
	}

	// Dump the content of this bean returning it as a String
	public void dump(StringBuffer str, String indent){
		String s;
		Object o;
		org.netbeans.modules.schema2beans.BaseBean n;
		str.append(indent);
		str.append("Description["+this.sizeDescription()+"]");	// NOI18N
		for(int i=0; i<this.sizeDescription(); i++)
		{
			str.append(indent+"\t");
			str.append("#"+i+":");
			str.append(indent+"\t");	// NOI18N
			str.append("<");	// NOI18N
			o = this.getDescription(i);
			str.append((o==null?"null":o.toString().trim()));	// NOI18N
			str.append(">\n");	// NOI18N
			this.dumpAttributes(DESCRIPTION, i, str, indent);
		}

		str.append(indent);
		str.append("DisplayName["+this.sizeDisplayName()+"]");	// NOI18N
		for(int i=0; i<this.sizeDisplayName(); i++)
		{
			str.append(indent+"\t");
			str.append("#"+i+":");
			str.append(indent+"\t");	// NOI18N
			str.append("<");	// NOI18N
			o = this.getDisplayName(i);
			str.append((o==null?"null":o.toString().trim()));	// NOI18N
			str.append(">\n");	// NOI18N
			this.dumpAttributes(DISPLAY_NAME, i, str, indent);
		}

		str.append(indent);
		str.append("Icon["+this.sizeIcon()+"]");	// NOI18N
		for(int i=0; i<this.sizeIcon(); i++)
		{
			str.append(indent+"\t");
			str.append("#"+i+":");
			n = (org.netbeans.modules.schema2beans.BaseBean) this.getIcon(i);
			if (n != null)
				n.dump(str, indent + "\t");	// NOI18N
			else
				str.append(indent+"\tnull");	// NOI18N
			this.dumpAttributes(ICON, i, str, indent);
		}

		str.append(indent);
		str.append("UrlPattern["+this.sizeUrlPattern()+"]");	// NOI18N
		for(int i=0; i<this.sizeUrlPattern(); i++)
		{
			str.append(indent+"\t");
			str.append("#"+i+":");
			str.append(indent+"\t");	// NOI18N
			str.append("<");	// NOI18N
			o = this.getUrlPattern(i);
			str.append((o==null?"null":o.toString().trim()));	// NOI18N
			str.append(">\n");	// NOI18N
			this.dumpAttributes(URL_PATTERN, i, str, indent);
		}

		str.append(indent);
		str.append("ElIgnored");	// NOI18N
		str.append(indent+"\t");	// NOI18N
		str.append((this.isElIgnored()?"true":"false"));
		this.dumpAttributes(EL_IGNORED, 0, str, indent);

		str.append(indent);
		str.append("PageEncoding");	// NOI18N
		str.append(indent+"\t");	// NOI18N
		str.append("<");	// NOI18N
		o = this.getPageEncoding();
		str.append((o==null?"null":o.toString().trim()));	// NOI18N
		str.append(">\n");	// NOI18N
		this.dumpAttributes(PAGE_ENCODING, 0, str, indent);

		str.append(indent);
		str.append("ScriptingInvalid");	// NOI18N
		str.append(indent+"\t");	// NOI18N
		str.append((this.isScriptingInvalid()?"true":"false"));
		this.dumpAttributes(SCRIPTING_INVALID, 0, str, indent);

		str.append(indent);
		str.append("IsXml");	// NOI18N
		str.append(indent+"\t");	// NOI18N
		str.append((this.isIsXml()?"true":"false"));
		this.dumpAttributes(IS_XML, 0, str, indent);

		str.append(indent);
		str.append("IncludePrelude["+this.sizeIncludePrelude()+"]");	// NOI18N
		for(int i=0; i<this.sizeIncludePrelude(); i++)
		{
			str.append(indent+"\t");
			str.append("#"+i+":");
			str.append(indent+"\t");	// NOI18N
			str.append("<");	// NOI18N
			o = this.getIncludePrelude(i);
			str.append((o==null?"null":o.toString().trim()));	// NOI18N
			str.append(">\n");	// NOI18N
			this.dumpAttributes(INCLUDE_PRELUDE, i, str, indent);
		}

		str.append(indent);
		str.append("IncludeCoda["+this.sizeIncludeCoda()+"]");	// NOI18N
		for(int i=0; i<this.sizeIncludeCoda(); i++)
		{
			str.append(indent+"\t");
			str.append("#"+i+":");
			str.append(indent+"\t");	// NOI18N
			str.append("<");	// NOI18N
			o = this.getIncludeCoda(i);
			str.append((o==null?"null":o.toString().trim()));	// NOI18N
			str.append(">\n");	// NOI18N
			this.dumpAttributes(INCLUDE_CODA, i, str, indent);
		}

	}
	public String dumpBeanNode(){
		StringBuffer str = new StringBuffer();
		str.append("JspPropertyGroup\n");	// NOI18N
		this.dump(str, "\n  ");	// NOI18N
		return str.toString();
	}}

// END_NOI18N

