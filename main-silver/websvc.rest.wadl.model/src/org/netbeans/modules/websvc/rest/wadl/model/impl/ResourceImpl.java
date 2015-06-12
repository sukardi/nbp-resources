//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-558 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.11.07 at 12:36:44 PM PST 
//


package org.netbeans.modules.websvc.rest.wadl.model.impl;

import java.util.Collection;
import org.netbeans.modules.websvc.rest.wadl.model.*;
import org.w3c.dom.Element;

public class ResourceImpl extends ResourceTypeImpl implements Resource {

    /** Creates a new instance of OperationImpl */
    public ResourceImpl(WadlModel model, Element e) {
        super(model, e);
    }

    public ResourceImpl(WadlModel model){
        this(model, createNewElement(WadlQNames.RESOURCE.getQName(), model));
    }

    public Collection<Resource> getResource() {
        return getChildren(Resource.class);
    }

    public void addResource(Resource resource) {
        addAfter(RESOURCE_PROPERTY, resource, TypeCollection.FOR_RESOURCE.types());
    }

    public void removeResource(Resource resource) {
        removeChild(RESOURCE_PROPERTY, resource);
    }

    public String getType() {
        return getAttribute(WadlAttribute.TYPE);
    }

    public void setType(String base) {
        setAttribute(TYPE_PROPERTY, WadlAttribute.TYPE, base);
    }

    public String getQueryType() {
        return getAttribute(WadlAttribute.QUERY_TYPE);
    }

    public void setQueryType(String base) {
        setAttribute(QUERY_TYPE_PROPERTY, WadlAttribute.QUERY_TYPE, base);
    }

    public String getPath() {
        return getAttribute(WadlAttribute.PATH);
    }

    public void setPath(String base) {
        setAttribute(PATH_PROPERTY, WadlAttribute.PATH, base);
    }

}