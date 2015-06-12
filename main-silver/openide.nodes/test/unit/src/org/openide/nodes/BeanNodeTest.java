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

package org.openide.nodes;

import java.beans.*;
import java.util.*;

import junit.textui.TestRunner;
import org.netbeans.junit.NbTestCase;
import org.netbeans.junit.NbTestSuite;

import org.openide.nodes.*;
import org.openide.util.lookup.Lookups;

/** Test updating of bean children in proper circumstances, e.g.
 * deleting nodes or beans.
 * @author Jesse Glick
 */
public class BeanNodeTest extends NbTestCase {

    public BeanNodeTest(String name) {
        super(name);
    }

    public static void main(String[] args) {
        TestRunner.run(new NbTestSuite(BeanNodeTest.class));
    }
    
    /** Tests that basic introspected properties of the bean are reflected
     * as node properties.
     */
    public void testBasicBeanProperties() throws Exception {
        Bean1 b = new Bean1();
        assertEquals("hello", b.getName());
        BeanNode n = new BeanNode(b);
        assertEquals("hello", n.getName());
        n.setName("hi");
        assertEquals("hi", b.getName());
        assertEquals("hi", n.getName());
        Node.PropertySet[] propsets = n.getPropertySets();
        assertEquals(1, propsets.length);
        assertEquals(Sheet.PROPERTIES, propsets[0].getName());
        Node.Property[] props = propsets[0].getProperties();
        Node.Property prop = null;
        // Will have just one prop, 'foo'. OK to also have 'name' but we ignore it if so.
        for (int i = 0; i < props.length; i++) {
            if (props[i].getName().equals("foo") && prop == null) {
                prop = props[i];
            } else {
                assertEquals("name", props[i].getName());
            }
        }
        assertNotNull(prop);
        assertEquals("Foo", prop.getDisplayName());
        assertEquals("The foo.", prop.getShortDescription());
        assertEquals(new Integer(0), prop.getValue());
        b.setFoo(1);
        assertEquals(new Integer(1), prop.getValue());
        WaitPCL l2 = new WaitPCL("foo");
        n.addPropertyChangeListener(l2);
        b.setFoo(2);
        assertTrue("Calling a bean setter fires a Node.Property value change", l2.changed());
        assertEquals(new Integer(2), prop.getValue());
        prop.setValue(new Integer(3));
        assertEquals(new Integer(3), prop.getValue());
        assertEquals(new Integer(3), new Integer(b.getFoo()));
        NL l1 = new NL();
        n.addNodeListener(l1);
        b.setName("newname");
        assertTrue("Calling bean's name setter fires Node.PROPERTY_NAME", l1.changed());
    }
    
    /** Test that beans extending a private superclass are still usable.
     * No methods defined in the private superclass will be usable, so
     * they may be skipped if found by the introspector.
     * @see "#24767"
     */
    public void testPrivateInheritance() throws Exception {
        Bean2 b = new Bean2();
        BeanNode n = new BeanNode(b);
        Node.PropertySet[] propsets = n.getPropertySets();
        assertEquals(1, propsets.length);
        assertEquals(Sheet.PROPERTIES, propsets[0].getName());
        Node.Property[] props = propsets[0].getProperties();
        Node.Property prop = null;
        for (int i = 0; i < props.length; i++) {
            if (props[i].getName().equals("yaya") && prop == null) {
                prop = props[i];
            } else if (props[i].getName().equals("class")) {
                // OK, useless prop, ignore it
            } else if (props[i].getName().equals("foo")) {
                // OK, not required to be here, but can be
                // Cf. comment in BeanNode, and code in PropertySupport.Reflection
                // But we must be able to get the value!
                if (props[i].canRead()) {
                    assertEquals("hello", props[i].getValue());
                }
            } else {
                assertEquals("name", props[i].getName());
            }
        }
        assertNotNull(prop);
        assertEquals(new Integer(5), prop.getValue());
        b.setYaya(3);
        assertEquals(new Integer(3), prop.getValue());
        // no foo, no listener
    }
    
    public void testNoFirindOfHiddenProperties () throws Exception {
        HidenPropertyBean bean = new HidenPropertyBean ();
        
        
        BeanNode node = new BeanNode (bean);
        
        Node.PropertySet[] arr = node.getPropertySets ();
        int cnt = 0;
        for (int i = 0; i < arr.length; i++) {
            cnt += arr[i].getProperties ().length;
        }
        assertEquals ("Bean node does not show hidden properties", 0, cnt);
        
        WaitPCL pcl = new WaitPCL (null);
        node.addPropertyChangeListener (pcl);
        
        bean.setHiddenProperty (11);
        
        assertFalse ("No change should be notified", pcl.changed ());
    }
    
    public void testLookupAndBean() throws Exception {
        class MyClass {
        }

        MyClass inst = new MyClass();

        Bean1 b = new Bean1();
        BeanNode n = new BeanNode(b, null, Lookups.singleton(inst));

        // my lookup is propagated
        MyClass tst = n.getLookup().lookup(MyClass.class);
        assertSame(inst, tst);
    }

    public void testCanUseCookieSetWithoutLookup() throws Exception {
        class MyBeanNode extends BeanNode {
            MyBeanNode(Object bean) throws IntrospectionException {
                super(bean, null, null);
                getCookieSet();
            }
        };

        new MyBeanNode(new Bean1());
    }
    
    // XXX test synchronizeName
    
    public static final class Bean1 {
        
        private String name = "hello";        
        private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);        
        private int foo = 0;
        
        public void addPropertyChangeListener(PropertyChangeListener l) {
            propertyChangeSupport.addPropertyChangeListener(l);
        }
        public void removePropertyChangeListener(PropertyChangeListener l) {
            propertyChangeSupport.removePropertyChangeListener(l);
        }
        
        public String getName() {
            return this.name;
        }
        public void setName(String name) {
            String oldName = this.name;
            this.name = name;
            propertyChangeSupport.firePropertyChange("name", oldName, name);
        }
        
        public int getFoo() {
            return this.foo;
        }
        public void setFoo(int foo) {
            int oldFoo = this.foo;
            this.foo = foo;
            propertyChangeSupport.firePropertyChange("foo", new Integer(oldFoo), new Integer(foo));
        }
        
    }
    
    public static final class Bean1BeanInfo extends SimpleBeanInfo {
        public PropertyDescriptor[] getPropertyDescriptors() {
            try {
                PropertyDescriptor foo = new PropertyDescriptor("foo", Bean1.class);
                foo.setDisplayName("Foo");
                foo.setShortDescription("The foo.");
                return new PropertyDescriptor[] {foo};
            } catch (IntrospectionException ie) {
                ie.printStackTrace();
                return null;
            }
        }
    }
    
    private static class Bean2S {
        
        private String foo = "hello";
        protected PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
        
        public void addPropertyChangeListener(PropertyChangeListener l) {
            propertyChangeSupport.addPropertyChangeListener(l);
        }
        public void removePropertyChangeListener(PropertyChangeListener l) {
            propertyChangeSupport.removePropertyChangeListener(l);
        }
        
        public String getFoo() {
            return this.foo;
        }
        public void setFoo(String foo) {
            String oldFoo = this.foo;
            this.foo = foo;
            propertyChangeSupport.firePropertyChange("foo", oldFoo, foo);
        }
        
    }
    
    public static final class Bean2 extends Bean2S {
        
        private int yaya = 5;
        
        public int getYaya() {
            return this.yaya;
        }
        public void setYaya(int yaya) {
            int oldYaya = this.yaya;
            this.yaya = yaya;
            propertyChangeSupport.firePropertyChange("yaya", new Integer(oldYaya), new Integer(yaya));
        }
        
    }
    
    /** Prop listener that will tell you if it gets a change.
     */
    private static class WaitPCL implements PropertyChangeListener {
        
        /** whether a change has been received, and if so count  */
        public int gotit = 0;
        
        /** optional property name to filter by (if null, accept any)  */
        private final String prop;
        
        public WaitPCL(String p) {
            prop = p;
        }
        
        public synchronized void propertyChange(PropertyChangeEvent evt) {
            if (prop == null || prop.equals(evt.getPropertyName())) {
                gotit++;
                notifyAll();
            }
        }
        
        public boolean changed() {
            return changed(1500);
        }
        
        public synchronized boolean changed(int timeout) {
            if (gotit > 0) {
                return true;
            }
            try {
                wait(timeout);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            return gotit > 0;
        }
        
    }
    
    private static class NL extends WaitPCL implements NodeListener {
        public NL() {
            super(Node.PROP_NAME);
        }
        public void childrenAdded(NodeMemberEvent ev) {}
        public void childrenRemoved(NodeMemberEvent ev) {}
        public void childrenReordered(NodeReorderEvent ev) {}
        public void nodeDestroyed(NodeEvent ev) {}
    }

    public static class HidenPropertyBean {
        private int    hiddenProperty;
        private PropertyChangeSupport pcs;

        /** Creates a new instance of Model */
        public HidenPropertyBean () {
            pcs = new PropertyChangeSupport(this);
            hiddenProperty = -1;
        }

        public void addPropertyChangeListener(PropertyChangeListener l) {
            pcs.addPropertyChangeListener(l);
        }

        public void removePropertyChangeListener(PropertyChangeListener l) {
            pcs.removePropertyChangeListener(l);
        }

        public int getHiddenProperty() {
            return hiddenProperty;
        }

        public void setHiddenProperty(int value) {
            this.hiddenProperty = value;
            pcs.firePropertyChange("hiddenProperty", null, null);
        }
    }
    
    public static class HidenPropertyBeanBeanInfo extends SimpleBeanInfo {
        public PropertyDescriptor[] getPropertyDescriptors () {
            PropertyDescriptor[] properties = new PropertyDescriptor[1];

            try {
                properties[0] = new PropertyDescriptor ( "hiddenProperty", HidenPropertyBean.class, "getHiddenProperty", "setHiddenProperty" );
                properties[0].setHidden ( true );
            }
            catch( IntrospectionException e) {}                          

            // Here you can add code for customizing the properties array.

            return properties;         
        }                             
    }
    
}
