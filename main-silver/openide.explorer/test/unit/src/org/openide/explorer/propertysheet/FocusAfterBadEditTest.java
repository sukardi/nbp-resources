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

package org.openide.explorer.propertysheet;

import java.awt.Component;
import java.awt.Container;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.beans.PropertyEditorSupport;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import org.netbeans.junit.NbTestCase;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.nodes.PropertySupport;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;

/** Tests property marking functionality and the ability of a Property to
 * provide a "postSetAction" action hint, which will be run if the user
 * successfully changes the property value.
 */
public class FocusAfterBadEditTest extends NbTestCase {
    private static boolean setup=false;
    
    static {
        //Register the basic core property editors
        String[] syspesp = PropertyEditorManager.getEditorSearchPath();
        String[] nbpesp = new String[] {
            "org.netbeans.beaninfo.editors", // NOI18N
            "org.openide.explorer.propertysheet.editors", // NOI18N
        };
        String[] allpesp = new String[syspesp.length + nbpesp.length];
        System.arraycopy(nbpesp, 0, allpesp, 0, nbpesp.length);
        System.arraycopy(syspesp, 0, allpesp, nbpesp.length, syspesp.length);
        PropertyEditorManager.setEditorSearchPath(allpesp);
    }
    
    public FocusAfterBadEditTest(String name) {
        super(name);
    }
    
    static SheetTable tb=null;
    static JFrame jf=null;
/*
 * This test creates a Property, Editor and Node. First test checks if initialized
 * editor contains the same value as property. The second checks if the property
 * value is changed if the same change will be done in the editor.
 */
    
    private boolean focusWorks = false;
    protected void setUp() throws Exception {
        if (setup) return;
        
        try {
            focusWorks = ExtTestCase.canSafelyRunFocusTests();
            if (!focusWorks) {
                return;
            }
            
            tp = new TProperty("Dont set me!", true);
            
            tn = new TNode();
            //            PropUtils.forceRadioButtons=true;
            final PropertySheet ps = new PropertySheet();
            
            //ensure no stored value in preferences:
            ps.setCurrentNode(tn);
            sleep();
            ps.setSortingMode(PropertySheet.UNSORTED);
            
            jf = new JFrame();
            jf.getContentPane().add(ps);
            jf.setLocation(20,20);
            jf.setSize(300, 400);
            new WaitWindow(jf);
            tb = ps.table;
            
            jf.repaint();
            
            
        } catch (Exception e) {
            e.printStackTrace();
            fail("FAILED - Exception thrown "+e.getClass().toString());
        } finally {
            setup = true;
        }
    }
    
    public void tearDown() {
        //        jf.hide();
        //        jf.dispose();
    }
    private void requestFocus(final JComponent jc) throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                jc.requestFocus();
            }
        });
        sleep();
    }
    
    public void testFocusReturn() throws Exception {
        if (!focusWorks) {
            return;
        }
        clickOn(tb, 1, 1);
        requestFocus(tb);
        typeKey(tb, KeyEvent.VK_B);
        typeKey(tb, KeyEvent.VK_E);
        typeKey(tb, KeyEvent.VK_SPACE);
        typeKey(tb, KeyEvent.VK_N);
        typeKey(tb, KeyEvent.VK_I);
        typeKey(tb, KeyEvent.VK_C);
        typeKey(tb, KeyEvent.VK_E);
        sleep();
        SLEEP_LENGTH=1000;
        Component c = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
        sleep();
        pressKey(c, KeyEvent.VK_ENTER);
        typeKey(c, KeyEvent.VK_ENTER);
        sleep();
        sleep();
        sleep();
        sleep();
        c = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
        assertNotNull(c);
        Container top = ((JComponent) c).getTopLevelAncestor();
        assertTrue("Focus should no longer be on the property sheet after an erroneous value was entered", top != jf);
        assertTrue("An error dialog should be showing after an exception was thrown in setAsText() but focus owner is " + c, jf != top);
        
        JRootPane jrp = ((JComponent) c).getRootPane();
        jrp.getDefaultButton().doClick();
        sleep();
        
        c = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
        assertTrue("After the error dialog is closed following a bad property edit, the table should return to edit mode on the previously edited property",
                c instanceof InplaceEditor);
        
    }
    
    
    private static class WaitWindow extends WindowAdapter {
        boolean shown=false;
        public WaitWindow(JFrame f) {
            f.addWindowListener(this);
            f.show();
            if (!shown) {
                synchronized(this) {
                    try {
                        //System.err.println("Waiting for window");
                        wait(5000);
                    } catch (Exception e) {}
                }
            }
        }
        
        public void windowOpened(WindowEvent e) {
            shown = true;
            synchronized(this) {
                //System.err.println("window opened");
                notifyAll();
                ((JFrame) e.getSource()).removeWindowListener(this);
            }
        }
    }
    
    private static int SLEEP_LENGTH=500;
    private void sleep() {
        //useful when running interactively
        
        
        try {
            Thread.currentThread().sleep(SLEEP_LENGTH);
        } catch (InterruptedException ie) {
            //go away
        }
        
        
        try {
            //jf.getTreeLock().wait();
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    System.currentTimeMillis();
                }
            });
            //jf.getTreeLock().wait();
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    System.currentTimeMillis();
                }
            });
        } catch (Exception e) {
        }
        
        
    }
    
    
    private void clickOn(final SheetTable tb, final int row, final int col) throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                Rectangle r = tb.getCellRect(row, col, false);
                Point toClick = r.getLocation();
                toClick.x += 15;
                toClick.y +=3;
                MouseEvent me = new MouseEvent(tb, MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(), MouseEvent.BUTTON1_MASK, toClick.x, toClick.y, 2, false);
                tb.dispatchEvent(me);
            }
        });
        sleep();
    }
    
    private void releaseKey(final Component target, final int key) throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                KeyEvent ke = new KeyEvent(target, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, key, (char) key);
                target.dispatchEvent(ke);
            }
        });
        sleep();
    }
    
    private void pressKey(final Component target, final int key) throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                KeyEvent ke = new KeyEvent(target, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, key, (char) key);
                target.dispatchEvent(ke);
            }
        });
        sleep();
    }
    
    private void typeKey(final Component target, final int key) throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                KeyEvent ke = new KeyEvent(target, KeyEvent.KEY_TYPED, System.currentTimeMillis(), 0, KeyEvent.VK_UNDEFINED, (char) key);
                target.dispatchEvent(ke);
            }
        });
        sleep();
    }
    
    //Node definition
    public class TNode extends AbstractNode {
        //create Node
        public TNode() {
            super(Children.LEAF);
            setName("TNode"); // or, super.setName if needed
            setDisplayName("TNode");
        }
        //clone existing Node
        public Node cloneNode() {
            return new TNode();
        }
        
        public void addProp(Node.Property p) {
            props.put(p);
            this.firePropertyChange(PROP_PROPERTY_SETS, null, null);
            this.firePropertySetsChange(null, null);
        }
        
        Sheet sheet=null;
        Sheet.Set props=null;
        // Create a property sheet:
        protected Sheet createSheet() {
            sheet = super.createSheet();
            // Make sure there is a "Properties" set:
            props = sheet.get(Sheet.PROPERTIES);
            if (props == null) {
                props = Sheet.createPropertiesSet();
                sheet.put(props);
            }
            props.put(tp);
            return sheet;
        }
        // Method firing changes
        public void fireMethod(String s, Object o1, Object o2) {
            firePropertyChange(s,o1,o2);
        }
    }
    
    // Property definition
    public class TProperty extends PropertySupport {
        private Object myValue = "Value";
        // Create new Property
        public TProperty(String name, boolean isWriteable) {
            super(name, String.class, name, "", true, isWriteable);
        }
        // get property value
        public Object getValue() {
            return myValue;
        }
        // set property value
        public void setValue(Object value) throws IllegalArgumentException,IllegalAccessException, InvocationTargetException {
            Object oldVal = myValue;
            myValue = value;
            tn.fireMethod(getName(), oldVal, myValue);
        }
        // get the property editor
        public PropertyEditor getPropertyEditor() {
            return new BadEditor();
        }
        
    }
    
    
    public class BadEditor extends PropertyEditorSupport implements ExPropertyEditor {
        PropertyEnv env;
        
        public BadEditor() {
        }
        
        public String[] getTags() {
            //return new String[] {"a","b","c","d","Value"};
            return null;
        }
        
        public void attachEnv(PropertyEnv env) {
            this.env = env;
            env.setState(env.STATE_INVALID);
        }
        
        public boolean supportsCustomEditor() {
            return false;
        }
        
        public void setValue(Object newValue) {
            super.setValue(newValue);
        }
        
        public void setAsText(String txt) {
            IllegalArgumentException iae = new IllegalArgumentException("Bad, bad, bad");
            Exceptions.attachLocalizedMessage(iae, "I can\'t be nice, I\'m the evil property editor.");
            throw iae;
        }
    }
    
    
    private static TNode tn;
    private static TProperty tp;
    
}
