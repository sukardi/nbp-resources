/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2012 Oracle and/or its affiliates. All rights reserved.
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
 *
 * Contributor(s):
 *
 * Portions Copyrighted 2012 Sun Microsystems, Inc.
 */
package org.netbeans.modules.openide.loaders.data;

import java.io.IOException;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiFileLoader;
import org.openide.util.HelpCtx;

/**
 * @see DataObjectFactoryProcessorTest
 * @author Eric Barboni <skygo@netbeans.org>
 */
@DataObject.Registration(mimeType =  "text/test1", displayName = "labeltest", position = 3565, iconBase = "org/openide/loaders/unknown.gif")
public class DoFPDataObject extends DataObject {

    public DoFPDataObject(FileObject pf, MultiFileLoader loader) throws DataObjectExistsException {
        super(pf, loader);
    }

    @Override
    public boolean isDeleteAllowed() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isCopyAllowed() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isMoveAllowed() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isRenameAllowed() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public HelpCtx getHelpCtx() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected DataObject handleCopy(DataFolder f) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void handleDelete() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected FileObject handleRename(String name) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected FileObject handleMove(DataFolder df) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected DataObject handleCreateFromTemplate(DataFolder df, String name) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
