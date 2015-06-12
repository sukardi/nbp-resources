/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2010 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2009 Sun Microsystems, Inc.
 */
package org.netbeans.modules.team.commons.treelist;

import org.netbeans.modules.team.commons.ColorManager;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.ToolTipManager;
import javax.swing.plaf.ListUI;
import org.openide.util.Utilities;

/**
 * List with expandable/collapsible rows.
 *
 * @author S. Aubrecht
 */
public class TreeList extends JList {

    static final int INSETS_TOP = 2;
    static final int INSETS_BOTTOM = 2;
    static final int INSETS_LEFT = 4;
    static final int INSETS_RIGHT = 4;
    /**
     * Action key for right-arrow expansion of property sets
     */
    private static final String ACTION_EXPAND = "expandSet"; //NOI18N
    /**
     * Action key for left-arrow closing of property sets
     */
    private static final String ACTION_COLLAPSE = "collapseSet"; //NOI18N
    /**
     * Action key for invoking the custom editor
     */
    private static final String ACTION_DEFAULT = "invokeDefaultAction"; //NOI18N
    /**
     * Action ket for invoking popup menu
     */
    private static final String ACTION_SHOW_POPUP = "showPopup"; //NOI18N
    private Action expandAction;
    private Action collapseAction;
    private Action defaultAction;
    private Action showPopupAction;
    private final TreeListRenderer renderer = new TreeListRenderer();
    static final int ROW_HEIGHT = Math.max(16, Math.max(RendererPanel.getExpandedIcon().getIconHeight(), new JLabel("X").getPreferredSize().height)); // NOI18N

    public TreeList(TreeListModel model) {
        super(model);
        setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        setFixedCellHeight(ROW_HEIGHT + INSETS_TOP + INSETS_BOTTOM + 2);
        setCellRenderer(renderer);
        setBackground(ColorManager.getDefault().getDefaultBackground());
        ToolTipManager.sharedInstance().registerComponent(this);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() != 2 || e.isPopupTrigger() || e.isConsumed()) {
                    return;
                }
                int index = locationToIndex(e.getPoint());
                if (index < 0 || index >= getModel().getSize()) {
                    return;
                }
                Object value = getModel().getElementAt(index);
                if (value instanceof TreeListNode) {
                    TreeListNode node = (TreeListNode) value;

                    if (null != node && !node.isExpandable()) {
                        ActionListener al = node.getDefaultAction();
                        if (null != al) {
                            al.actionPerformed(new ActionEvent(e.getSource(), e.getID(), e.paramString()));
                        }
                    } else if (null != node && node.isExpandable()) {
                        if (!node.isLoaded()) {
                            return;
                        }
                        node.setExpanded(!node.isExpanded());
                    }
                }
            }
        });
    }

    @Override
    public void setUI(ListUI ui) {
        super.setUI(new TreeListUI());
    }

    @Override
    public void updateUI() {
        super.updateUI();

        initKeysAndActions();
    }

    /**
     * Right-arrow key expands a row, left-arrow collapses a row, enter invokes
     * row's default action (if any).
     */
    private void initKeysAndActions() {
        unregisterKeyboardAction(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0));
        unregisterKeyboardAction(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0));
        unregisterKeyboardAction(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
        unregisterKeyboardAction(KeyStroke.getKeyStroke(KeyEvent.VK_F10, KeyEvent.SHIFT_DOWN_MASK));

        expandAction = new ExpandAction();
        collapseAction = new CollapseAction();
        defaultAction = new DefaultAction();
        showPopupAction = new ShowPopupAction();

        InputMap imp = getInputMap();
        InputMap impAncestor = getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        ActionMap am = getActionMap();

        imp.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), ACTION_EXPAND);
        imp.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), ACTION_COLLAPSE);
        imp.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), ACTION_DEFAULT);
        imp.put(KeyStroke.getKeyStroke(KeyEvent.VK_F10, KeyEvent.SHIFT_DOWN_MASK), ACTION_SHOW_POPUP);

        impAncestor.remove(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0));
        impAncestor.remove(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0));
        impAncestor.remove(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
        impAncestor.remove(KeyStroke.getKeyStroke(KeyEvent.VK_F10, KeyEvent.SHIFT_DOWN_MASK));

        am.put(ACTION_EXPAND, expandAction);
        am.put(ACTION_COLLAPSE, collapseAction);
        am.put(ACTION_DEFAULT, defaultAction);
        am.put(ACTION_SHOW_POPUP, showPopupAction);
    }

    private TreeListNode getSelectedTreeListNode() {
        Object sel = super.getSelectedValue();
        if (sel instanceof TreeListNode) {
            return (TreeListNode) sel;
        }
        return null;
    }

    /**
     * Show popup menu from actions provided by node at given index (if any).
     *
     * @param rowIndex
     * @param location
     */
    void showPopupMenuAt(int rowIndex, Point location) {
        TreeListNode node = (TreeListNode) getModel().getElementAt(rowIndex);
        boolean popupForSelected = false;
        if (getSelectionMode() != ListSelectionModel.SINGLE_SELECTION) {
            popupForSelected = isPopupForSelected(node);
        }
        if (!popupForSelected) {
            setSelectedIndex(rowIndex);
        }
        Action[] actions = node.getPopupActions();

        if (null == actions || actions.length == 0) {
            return;
        }
        JPopupMenu popup = Utilities.actionsToPopup(actions, this);
        popup.show(this, location.x, location.y);
    }

    /**
     * Determines if popup was called for one of the selected nodes
     */
    private boolean isPopupForSelected(TreeListNode node) {
        Object[] selectedValues = getSelectedValues();
        for (int i = 0; i < selectedValues.length; i++) {
            Object selectedNode = selectedValues[i];
            if (selectedNode.equals(node)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getToolTipText(MouseEvent event) {
        if (event != null) {
            Point p = event.getPoint();
            int index = locationToIndex(p);
            ListCellRenderer r = getCellRenderer();
            Rectangle cellBounds;

            if (index != -1 && r != null && (cellBounds =
                    getCellBounds(index, index)) != null
                    && cellBounds.contains(p.x, p.y)) {
                ListSelectionModel lsm = getSelectionModel();
                Component rComponent = r.getListCellRendererComponent(
                        this, getModel().getElementAt(index), index,
                        lsm.isSelectedIndex(index),
                        (hasFocus() && (lsm.getLeadSelectionIndex()
                        == index)));

                if (rComponent instanceof JComponent) {
                    rComponent.setBounds(cellBounds);
                    rComponent.doLayout();
                    MouseEvent newEvent;

                    p.translate(-cellBounds.x, -cellBounds.y);
                    newEvent = new MouseEvent(rComponent, event.getID(),
                            event.getWhen(),
                            event.getModifiers(),
                            p.x, p.y, event.getClickCount(),
                            event.isPopupTrigger());

                    String tip = ((JComponent) rComponent).getToolTipText(
                            newEvent);

                    if (tip != null) {
                        return tip;
                    }
                }
            }
        }
        return super.getToolTipText();
    }

    private static class TreeListRenderer implements ListCellRenderer {

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (!(value instanceof TreeListNode)) {
                //shoudln't happen
                return new JLabel();
            }
            TreeListNode node = (TreeListNode) value;
            int rowHeight = list.getFixedCellHeight();
            int rowWidth = list.getVisibleRect().width;
            int dropIndex = -1;
            DropLocation dropLocation = list.getDropLocation();
            if (dropLocation != null && !dropLocation.isInsert()) {
                dropIndex = dropLocation.getIndex();
            }
            boolean isDropTarget = dropIndex == index;
            isSelected = isSelected || isDropTarget;
            Color background = isSelected ? list.getSelectionBackground() : list.getBackground();
            Color foreground = isSelected ? list.getSelectionForeground() : list.getForeground();

            return node.getRenderer(foreground, background, isSelected, cellHasFocus, rowHeight, rowWidth);
        }
    }

    //*************Actions bound to the keyboard ******************
    private class ExpandAction extends AbstractAction {

        public ExpandAction() {
            super(ACTION_EXPAND);
        }

        public void actionPerformed(ActionEvent ae) {
            TreeListNode node = getSelectedTreeListNode();

            if (null != node && node.isExpandable()) {
                node.setExpanded(true);
            }
        }

        @Override
        public boolean isEnabled() {
            TreeListNode node = getSelectedTreeListNode();

            return null != node && node.isExpandable();
        }
    }

    private class CollapseAction extends AbstractAction {

        public CollapseAction() {
            super(ACTION_COLLAPSE);
        }

        public void actionPerformed(ActionEvent ae) {
            TreeListNode node = getSelectedTreeListNode();

            if (null != node && node.isExpandable() && node.isExpanded()) {
                node.setExpanded(false);
            } else if (node != null && node.getParent() != null){
                node.getParent().setExpanded(false);
                setSelectedValue(node.getParent(), true);
            }
        }

        @Override
        public boolean isEnabled() {
            TreeListNode node = getSelectedTreeListNode();

            return null != node;
        }
    }

    private class ShowPopupAction extends AbstractAction {

        public ShowPopupAction() {
            super(ACTION_SHOW_POPUP);
        }

        public void actionPerformed(ActionEvent ae) {
            TreeListNode node = getSelectedTreeListNode();
            TreeList tl = (TreeList) ae.getSource();
            JPopupMenu menu = Utilities.actionsToPopup(node.getPopupActions(), tl);
            Point p = tl.getUI().indexToLocation(tl, tl.getSelectedIndex());
            menu.show(tl, p.x + 22, p.y);
        }

        @Override
        public boolean isEnabled() {
            TreeListNode node = getSelectedTreeListNode();
            return null != node && null != node.getPopupActions();
        }
    }

    private class DefaultAction extends AbstractAction {

        public DefaultAction() {
            super(ACTION_DEFAULT);
        }

        public void actionPerformed(ActionEvent ae) {
            TreeListNode node = getSelectedTreeListNode();

            if (null != node) {
                ActionListener al = node.getDefaultAction();
                if (null != al) {
                    al.actionPerformed(ae);
                }
            }
        }

        @Override
        public boolean isEnabled() {
            TreeListNode node = getSelectedTreeListNode();

            return null != node && null != node.getDefaultAction();
        }
    }
}
