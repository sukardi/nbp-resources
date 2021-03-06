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
package org.netbeans.modules.web.javascript.debugger.breakpoints;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;
import org.netbeans.api.debugger.DebuggerEngine;
import org.netbeans.api.debugger.DebuggerManager;
import org.netbeans.modules.javascript2.debug.models.ViewModelSupport;
import org.netbeans.modules.web.webkit.debugging.api.Debugger;
import org.netbeans.spi.debugger.ContextProvider;
import org.netbeans.spi.debugger.DebuggerServiceRegistration;
import org.netbeans.spi.viewmodel.ExtendedNodeModel;
import org.netbeans.spi.viewmodel.ExtendedNodeModelFilter;
import org.netbeans.spi.viewmodel.ModelEvent;
import org.netbeans.spi.viewmodel.ModelListener;
import org.netbeans.spi.viewmodel.NodeModel;
import org.netbeans.spi.viewmodel.UnknownTypeException;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;
import org.openide.util.WeakListeners;
import org.openide.util.datatransfer.PasteType;

/**
 *
 * @author Martin Entlicher
 */
@DebuggerServiceRegistration(path="javascript-debuggerengine/BreakpointsView", types=ExtendedNodeModelFilter.class)
public class BreakpointModelActiveSessionFilter extends ViewModelSupport
                                                implements ExtendedNodeModelFilter,
                                                           PropertyChangeListener {
    
    private static final String DEACTIVATED_NONLINE_BREAKPOINT =
        "org/netbeans/modules/debugger/resources/editor/Breakpoint_nonline_stroke.png";         // NOI18N
    private static final String DEACTIVATED_DISABLED_NONLINE_BREAKPOINT =
        "org/netbeans/modules/debugger/resources/editor/DisabledBreakpoint_nonline_stroke.png"; // NOI18N
    
    private static final RequestProcessor RP = new RequestProcessor(BreakpointModelActiveSessionFilter.class.getName());

    private Debugger debugger;
    
    public BreakpointModelActiveSessionFilter(ContextProvider contextProvider) {
        debugger = contextProvider.lookupFirst(null, Debugger.class);
        debugger.addPropertyChangeListener(WeakListeners.propertyChange(this, debugger));
    }

    @Override
    public String getDisplayName(NodeModel original, Object node) throws UnknownTypeException {
        return original.getDisplayName(node);
    }

    @Override
    public String getShortDescription(NodeModel original, Object node) throws UnknownTypeException {
        return original.getShortDescription(node);
    }

    @Override
    public String getIconBaseWithExtension(ExtendedNodeModel original, Object node) throws UnknownTypeException {
        if (node instanceof AbstractBreakpoint && !debugger.areBreakpointsActive()) {
            AbstractBreakpoint breakpoint = (AbstractBreakpoint) node;
            if (!breakpoint.isEnabled()) {
                return DEACTIVATED_DISABLED_NONLINE_BREAKPOINT;
            } else {
                return DEACTIVATED_NONLINE_BREAKPOINT;
            }
        } else {
            return original.getIconBaseWithExtension(node);
        }
    }

    @Override
    public String getIconBase(NodeModel original, Object node) throws UnknownTypeException {
        throw new UnsupportedOperationException("Not supported, overriden by getIconBaseWithExtension().");
    }

    @Override
    public void setName(ExtendedNodeModel original, Object node, String name) throws UnknownTypeException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean canRename(ExtendedNodeModel original, Object node) throws UnknownTypeException {
        return original.canRename(node);
    }

    @Override
    public boolean canCopy(ExtendedNodeModel original, Object node) throws UnknownTypeException {
        return original.canCopy(node);
    }

    @Override
    public boolean canCut(ExtendedNodeModel original, Object node) throws UnknownTypeException {
        return original.canCut(node);
    }

    @Override
    public Transferable clipboardCopy(ExtendedNodeModel original, Object node) throws IOException, UnknownTypeException {
        return original.clipboardCopy(node);
    }

    @Override
    public Transferable clipboardCut(ExtendedNodeModel original, Object node) throws IOException, UnknownTypeException {
        return original.clipboardCut(node);
    }

    @Override
    public PasteType[] getPasteTypes(ExtendedNodeModel original, Object node, Transferable t) throws UnknownTypeException {
        return original.getPasteTypes(node, t);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        if (Debugger.PROP_BREAKPOINTS_ACTIVE.equals(propertyName)) {
            fireChangeEvent(new ModelEvent.NodeChanged(this, null, ModelEvent.NodeChanged.ICON_MASK));
        }
    }

}
