/**
 *	This generated bean class ConnectionFactoryResourceType matches the schema element 'connection-factory-resourceType'.
 *  The root bean class is EjbJar
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

package org.netbeans.modules.j2ee.dd.impl.ejb.model_3_2;

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
			Property.class);
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
	public void setProperty2(int index, org.netbeans.modules.j2ee.dd.api.ejb.Property valueInterface) {
		Property value = (Property) valueInterface;
		this.setValue(PROPERTY2, index, value);
	}

	//
	public org.netbeans.modules.j2ee.dd.api.ejb.Property getProperty2(int index) {
		return (Property)this.getValue(PROPERTY2, index);
	}

	// Return the number of properties
	public int sizeProperty2() {
		return this.size(PROPERTY2);
	}

	// This attribute is an array, possibly empty
	public void setProperty2(org.netbeans.modules.j2ee.dd.api.ejb.Property[] value) {
		this.setValue(PROPERTY2, value);
	}

	//
	public org.netbeans.modules.j2ee.dd.api.ejb.Property[] getProperty2() {
		return (Property[])this.getValues(PROPERTY2);
	}

	// Add a new element returning its index in the list
	public int addProperty2(org.netbeans.modules.j2ee.dd.api.ejb.Property valueInterface) {
		Property value = (Property) valueInterface;
		int positionOfNewItem = this.addValue(PROPERTY2, value);
		return positionOfNewItem;
	}

	//
	// Remove an element using its reference
	// Returns the index the element had in the list
	//
	public int removeProperty2(org.netbeans.modules.j2ee.dd.api.ejb.Property valueInterface) {
		Property value = (Property) valueInterface;
		return this.removeValue(PROPERTY2, value);
	}

	/**
	 * Create a new bean using it's default constructor.
	 * This does not add it to any bean graph.
	 */
	public org.netbeans.modules.j2ee.dd.api.ejb.Property newProperty() {
		return new Property();
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
			org.netbeans.modules.j2ee.dd.impl.ejb.model_3_2.Property element = (org.netbeans.modules.j2ee.dd.impl.ejb.model_3_2.Property) getProperty2(_index);
			if (element != null) {
				((Property)element).validate();
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

