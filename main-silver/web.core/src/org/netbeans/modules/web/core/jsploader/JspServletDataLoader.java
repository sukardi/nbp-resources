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

package org.netbeans.modules.web.core.jsploader;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.util.NbBundle;
import org.netbeans.api.java.loaders.JavaDataSupport;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.FileEntry;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.MultiFileLoader;

/** The DataLoader for servlets (JavaDataObjects), which have been generated by JaSPer
* from JSP pages. Recognizes by string <code>_jsp_</code> in the name of the file.
* Does not recognize associated .class files, which are recognized as a separate ClassDataObject
*
* @author Petr Jiricka
*/
public final class JspServletDataLoader extends MultiFileLoader {

    /** serialVersionUID */
    private static final long serialVersionUID = -6033464827752236719L;

    /** The standard extension for Java source files. */
    public static final String JAVA_EXTENSION = "java"; // NOI18N
    
    public static final String JSP_MARK = "_jsp";   // NOI18N

    /** Creates a new JspServletDataLoader
    * Should <em>not</em> be used by subclasses.
     */ 
    public JspServletDataLoader() {
        super("org.netbeans.modules.web.core.jsploader.JspServletDataObject"); // NOI18N
    }
    
    /** Gets default display name. Overrides superclass mthod. */
    protected @Override String defaultDisplayName() {
        return NbBundle.getBundle(JspServletDataLoader.class).getString("PROP_JspServletLoader_Name");
    }
    
    @Override
    protected String actionsContext() {
        return "Loaders/text/x-jsp-servlet/Actions/"; // NOI18N
    }

    /** For a given file finds a primary file.
    * @param fo the file to find primary file for
    * @return the primary file for the file or null if the file is not
    *   recognized by this loader
    */
    protected FileObject findPrimaryFile (FileObject fo) {
        // detects  *_jsp*.java
        FileObject javaPrim;
        // never recognize folders.
        if (fo.isFolder())
            javaPrim = null;
        else if (fo.getExt().equals(JAVA_EXTENSION))
            javaPrim = fo;
        else
            javaPrim = null;
        
        if (javaPrim == null)
            return null;
        
        // if there is a source JSP set then this is generated from a JSP
        if (javaPrim.getAttribute(JspServletDataObject.EA_ORIGIN_JSP_PAGE) != null) {
            return javaPrim;
        }

        // PENDING: old way of recognition was by name, need to remove this later
        //if (javaPrim.getName().lastIndexOf(JSP_MARK) != -1)
        //    return javaPrim;
            
        return null;
    }

    /** Create the <code>JspServletDataObject</code>.
    * @param primaryFile the primary file
    * @return the data object for this file
    * @exception DataObjectExistsException if the primary file already has a data object
    */
    protected MultiDataObject createMultiObject (FileObject primaryFile)
    throws DataObjectExistsException, java.io.IOException {
        return new JspServletDataObject(primaryFile, this);
    }

    /** Create the primary file entry.
    * @param primaryFile primary file recognized by this loader
    * @return primary entry for that file
    */
    protected MultiDataObject.Entry createPrimaryEntry (MultiDataObject obj, FileObject primaryFile) {
        if (JAVA_EXTENSION.equals(primaryFile.getExt())) {
            return JavaDataSupport.createJavaFileEntry(obj, primaryFile);
        }
        else {
            return new FileEntry(obj, primaryFile);
        }
    }

    /** Create a secondary file entry.
    * By default, {@link FileEntry.Numb} is used for the class files
    *
    * @param secondaryFile secondary file to create entry for
    * @return the entry
    */
    protected MultiDataObject.Entry createSecondaryEntry (MultiDataObject obj, FileObject secondaryFile) {
        //The JavaDataObject itself has no secondary entries, but its subclasses have.
        //So we have to keep it as MultiFileLoader
        Logger.getLogger("global").log(Level.INFO, "Subclass of JavaDataLoader (" + this.getClass().getName() + ") has secondary entries but does not override createSecondaryEntries (MultidataObject, FileObject) method."); // NOI18N
        return new FileEntry.Numb(obj, secondaryFile);
    }   

}
