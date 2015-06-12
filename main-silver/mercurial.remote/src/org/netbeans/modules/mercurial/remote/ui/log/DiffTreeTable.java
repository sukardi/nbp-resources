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
package org.netbeans.modules.mercurial.remote.ui.log;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import org.openide.explorer.ExplorerManager;
import org.openide.nodes.Node;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.NbBundle;
import org.openide.ErrorManager;

import javax.swing.*;
import java.util.*;
import java.beans.PropertyVetoException;
import java.io.CharConversionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import org.netbeans.swing.etable.ETableColumnModel;
import org.netbeans.swing.outline.RenderDataProvider;
import org.openide.explorer.view.OutlineView;
import org.openide.util.NbBundle.Messages;
import org.openide.xml.XMLUtil;

/**
 * Treetable to show results of Search History action.
 * 
 * @author Maros Sandor
 */
class DiffTreeTable extends OutlineView {
    
    private RevisionsRootNode rootNode;
    private List<RepositoryRevision> results;
    private final SearchHistoryPanel master;

    @Messages("LBL_DiffView.TreeColumnLabel=Revision")
    public DiffTreeTable(SearchHistoryPanel master) {
        super(Bundle.LBL_DiffView_TreeColumnLabel());
        this.master = master;
        getOutline().setShowHorizontalLines(true);
        getOutline().setShowVerticalLines(false);
        getOutline().setRootVisible(false);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setupColumns();
        getOutline().setRenderDataProvider( new NoLeafIconRenderDataProvider( getOutline().getRenderDataProvider() ) );
    }
    
    @SuppressWarnings("unchecked")
    private void setupColumns() {
        ResourceBundle loc = NbBundle.getBundle(DiffTreeTable.class);
        setPropertyColumns(RevisionNode.COLUMN_NAME_PATH, loc.getString("LBL_DiffTree_Column_Path"),
                RevisionNode.COLUMN_NAME_DATE, loc.getString("LBL_DiffTree_Column_Time"),
                RevisionNode.COLUMN_NAME_USERNAME, loc.getString("LBL_DiffTree_Column_Username"),
                RevisionNode.COLUMN_NAME_MESSAGE, loc.getString("LBL_DiffTree_Column_Message"));
        setPropertyColumnDescription(RevisionNode.COLUMN_NAME_PATH, loc.getString("LBL_DiffTree_Column_Path_Desc"));
        setPropertyColumnDescription(RevisionNode.COLUMN_NAME_DATE, loc.getString("LBL_DiffTree_Column_Time_Desc"));
        setPropertyColumnDescription(RevisionNode.COLUMN_NAME_USERNAME, loc.getString("LBL_DiffTree_Column_Username_Desc"));
        setPropertyColumnDescription(RevisionNode.COLUMN_NAME_MESSAGE, loc.getString("LBL_DiffTree_Column_Message_Desc"));
        TableColumnModel model = getOutline().getColumnModel();
        if (model instanceof ETableColumnModel) {
            ((ETableColumnModel) model).setColumnHidden(model.getColumn(1), true);
        }
        TableColumn column = getOutline().getColumn(loc.getString("LBL_DiffTree_Column_Message"));
        column.setCellRenderer(new MessageRenderer(getOutline().getDefaultRenderer(String.class)));
        setDefaultColumnSizes();
    }
    
    private void setDefaultColumnSizes() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (getOutline().getColumnCount() == 4) {
                    int width = getWidth();
                    getOutline().getColumnModel().getColumn(0).setPreferredWidth(width * 25 / 100);
                    getOutline().getColumnModel().getColumn(1).setPreferredWidth(width * 15 / 100);
                    getOutline().getColumnModel().getColumn(2).setPreferredWidth(width * 10 / 100);
                    getOutline().getColumnModel().getColumn(3).setPreferredWidth(width * 50 / 100);
                }
            }
        });
    }

    void setSelection(int idx) {
        getOutline().getSelectionModel().setValueIsAdjusting(false);
        getOutline().scrollRectToVisible(getOutline().getCellRect(idx, 1, true));
        getOutline().getSelectionModel().setSelectionInterval(idx, idx);
    }

    void setSelection(RepositoryRevision container) {
        RevisionNode node = (RevisionNode) getNode(rootNode, container);
        if (node == null) {
            return;
        }
        ExplorerManager em = ExplorerManager.find(this);
        try {
            em.setSelectedNodes(new Node [] { node });
        } catch (PropertyVetoException e) {
            ErrorManager.getDefault().notify(e);
        }
    }

    void setSelection (RepositoryRevision.Event... events) {
        List<Node> nodes = new ArrayList<>(events.length);
        for (RepositoryRevision.Event event : events) {
            RevisionNode node = (RevisionNode) getNode(rootNode, event);
            if (node != null) {
                nodes.add(node);
            }
        }
        ExplorerManager em = ExplorerManager.find(this);
        try {
            em.setSelectedNodes(nodes.toArray(new Node[nodes.size()]));
        } catch (PropertyVetoException e) {
            ErrorManager.getDefault().notify(e);
        }
    }

    private Node getNode(Node node, Object obj) {
        Object object = node.getLookup().lookup(obj.getClass());
        if (obj.equals(object)) {
            return node;
        }
        Enumeration children = node.getChildren().nodes();
        while (children.hasMoreElements()) {
            Node child = (Node) children.nextElement();
            Node result = getNode(child, obj);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    public int [] getSelection() {
        return getOutline().getSelectedRows();
    }

    public int getRowCount() {
        return getOutline().getRowCount();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        ExplorerManager em = ExplorerManager.find(this);
        em.setRootContext(rootNode);
    }

    public void setResults(List<RepositoryRevision> results) {
        this.results = results;
        rootNode = new RevisionsRootNode();
        ExplorerManager em = ExplorerManager.find(this);
        if (em != null) {
            em.setRootContext(rootNode);
        }
    }
    
    public void refreshResults (List<RepositoryRevision> results) {
        this.results = results;
        ((RevisionsRootNodeChildren) rootNode.getChildren()).refreshKeys();
    }

    private static class MessageRenderer implements TableCellRenderer {
        private final TableCellRenderer delegate;
        private final Map<String, String> tooltips = new HashMap<>();

        public MessageRenderer (TableCellRenderer delegate) {
            this.delegate = delegate;
        }

        @Override
        public Component getTableCellRendererComponent (JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component comp = delegate.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (comp instanceof JComponent) {
                JComponent c = (JComponent) comp;
                if (value == null) {
                    c.setToolTipText(null);
                } else {
                    String val = value.toString();
                    String tooltip = tooltips.get(val);
                    if (tooltip == null) {
                        tooltip = val.replace("\r\n", "\n").replace("\r", "\n"); //NOI18N
                        try {
                            tooltip = XMLUtil.toElementContent(tooltip);
                        } catch (CharConversionException e1) {
                            Logger.getLogger(DiffTreeTable.class.getName()).log(Level.INFO, "Can not HTML escape: ", tooltip);  //NOI18N
                        }
                        if (tooltip.contains("\n")) { //NOI18N
                            tooltip = "<html><body><p>" + tooltip.replace("\n", "<br>") + "</p></body></html>"; //NOI18N
                            c.setToolTipText(tooltip);
                        }
                        tooltips.put(val, tooltip);
                    }
                    c.setToolTipText(tooltip);
                }
            }
            return comp;
        }
    }
    
    private class RevisionsRootNode extends AbstractNode {
    
        public RevisionsRootNode() {
            super(new RevisionsRootNodeChildren());
        }

        @Override
        public String getName() {
            return "revision"; // NOI18N
        }

        @Override
        public String getDisplayName() {
            return NbBundle.getMessage(DiffTreeTable.class, "LBL_DiffTree_Column_Name"); // NOI18N
        }

        @Override
        public String getShortDescription() {
            return NbBundle.getMessage(DiffTreeTable.class, "LBL_DiffTree_Column_Name_Desc"); // NOI18N
        }
    }

    private class NoLeafIconRenderDataProvider implements RenderDataProvider {
        private final RenderDataProvider delegate;
        public NoLeafIconRenderDataProvider( RenderDataProvider delegate ) {
            this.delegate = delegate;
        }

        @Override
        public String getDisplayName(Object o) {
            return delegate.getDisplayName(o);
        }

        @Override
        public boolean isHtmlDisplayName(Object o) {
            return delegate.isHtmlDisplayName(o);
        }

        @Override
        public Color getBackground(Object o) {
            return delegate.getBackground(o);
        }

        @Override
        public Color getForeground(Object o) {
            return delegate.getForeground(o);
        }

        @Override
        public String getTooltipText(Object o) {
            return delegate.getTooltipText(o);
        }

        @Override
        public Icon getIcon(Object o) {
            if( getOutline().getOutlineModel().isLeaf(o) ) {
                return NO_ICON;
            }
            return null;
        }

    }
    private static final Icon NO_ICON = new NoIcon();
    private static class NoIcon implements Icon {

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {

        }

        @Override
        public int getIconWidth() {
            return 0;
        }

        @Override
        public int getIconHeight() {
            return 0;
        }
    }
    private class RevisionsRootNodeChildren extends Children.Keys {
    
        public RevisionsRootNodeChildren() {
        }

        @Override
        protected void addNotify() {
            refreshKeys();
        }
        
        @SuppressWarnings("unchecked")
        @Override
        protected void removeNotify() {
            setKeys(Collections.EMPTY_SET);
        }
    
        @SuppressWarnings("unchecked")
        void refreshKeys() {
            setKeys(results);
            repaint();
        }
    
        @Override
        protected Node[] createNodes(Object key) {
            RevisionNode node;
            if (key instanceof RepositoryRevision) {
                node = new RevisionNode((RepositoryRevision) key, master);
            } else { // key instanceof RepositoryRevision.Event
                node = new RevisionNode(((RepositoryRevision.Event) key), master);
            }
            return new Node[] { node };
        }
    }
}
