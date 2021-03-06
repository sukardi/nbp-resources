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
package org.netbeans.modules.nativeexecution.support.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.List;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;
import org.openide.util.NbBundle;

/**
 *
 * @author ak119685
 */
public final class CompletionPopup extends JPopupMenu implements KeyListener {

    private final String[] NO_COMPLETION_OPTIONS = new String[]{loc("CompletionPopup.NoAutoCompletion")}; // NOI18N
    private final String[] WAITING_FOR_DATA = new String[]{loc("CompletionPopup.Waiting")}; // NOI18N
    private boolean enabled = false;
    private final Completable completable;
    private final JList list;
    private final Color enabledColor;
    private final Color disabledColor = Color.lightGray;

    public CompletionPopup(Completable completable) {
        this.list = new JList();
        enabledColor = list.getForeground();
        list.setVisibleRowCount(4);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.completable = completable;

        JScrollPane jsp = new JScrollPane(list);
        add(jsp);
        list.setFocusable(false);
        jsp.setFocusable(false);
        setFocusable(false);

        list.addFocusListener(new FocusHandler());
        list.addMouseListener(new MouseHandler());
        list.addMouseMotionListener(new MouseHandler());

        completable.addKeyListener(CompletionPopup.this);
    }

    public void setOptionsList(List<String> options) {
        if (options.isEmpty()) {
            list.setListData(NO_COMPLETION_OPTIONS);
            list.setForeground(disabledColor);
            enabled = false;
        } else {
            list.setListData(options.<String>toArray(new String[0]));
            list.setForeground(enabledColor);
            enabled = true;
        }
    }

    private void setSelectNext() {
        if (list.getModel().getSize() > 0 && enabled) {
            int cur = (list.getSelectedIndex() + 1) % list.getModel().getSize();
            list.setSelectedIndex(cur);
            list.ensureIndexIsVisible(cur);
        }
    }

    private void setSelectPrevious() {
        if (list.getModel().getSize() > 0 && enabled) {
            int cur = (list.getSelectedIndex() == -1) ? 0
                    : list.getSelectedIndex();
            cur = (cur == 0) ? list.getModel().getSize() - 1 : cur - 1;
            list.setSelectedIndex(cur);
            list.ensureIndexIsVisible(cur);
        }
    }

    public void showPopup(JTextComponent source, int x, int y) {
        setPreferredSize(new Dimension(source.getWidth(), source.getHeight() * 4));
        show(source, x, y);
        list.clearSelection();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // no operation
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!isVisible() || e.isConsumed()) {
            return;
        }

        int code = e.getKeyCode();
        String commonPrefix = null;
        String option = null;

        switch (code) {
            case KeyEvent.VK_DOWN:
                setSelectNext();
                e.consume();
                break;
            case KeyEvent.VK_UP:
                setSelectPrevious();
                e.consume();
                break;
            case KeyEvent.VK_ESCAPE:
                setVisible(false);
                completable.requestFocus();
                e.consume();
                break;
            case KeyEvent.VK_ENTER:
                if (enabled) {
                    // Substitute with the selected item
                    option = (String) list.getSelectedValue();
                    if (option != null) {
                        boolean leaveVisible = completable.completeWith(option);
                        if (!leaveVisible) {
                            setVisible(false);
                        }
                        completable.requestFocus();
                    }
                }
                e.consume();
                break;
            case KeyEvent.VK_TAB:
            case KeyEvent.VK_RIGHT:
                if (enabled) {
                    option = (String) list.getSelectedValue();

                    if (option == null && list.getModel().getSize() == 1) {
                        option = (String) list.getModel().getElementAt(0);
                    }

                    if (option != null) {
                        boolean leaveVisible = completable.completeWith(option);
                        if (!leaveVisible) {
                            setVisible(false);
                        }
                        completable.requestFocus();
                    } else {
                        // Nothing selected - complete with a common prefix (if any)
                        commonPrefix = getCommonPrefix();
                        if (commonPrefix != null) {
                            completable.completeWith(commonPrefix);
                        }
                    }
                }
                if (code == KeyEvent.VK_TAB) {
                    e.consume();
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // no operation
    }

    private String getCommonPrefix() {
        int listSize = list.getModel().getSize();

        if (listSize == 0) {
            return null;
        }

        String first = (String) list.getModel().getElementAt(0);
        StringBuilder prefix = new StringBuilder(first);
        int prefixLen = first.length();
        String item;
        int itemLen;

        for (int i = 1; i < listSize; i++) {
            if (prefixLen == 0) {
                return null;
            }

            item = (String) list.getModel().getElementAt(i);
            itemLen = item.length();

            if (itemLen < prefixLen) {
                prefixLen = itemLen;
                prefix.setLength(prefixLen);
            }

            for (int j = 0; j < prefixLen; j++) {
                if (item.charAt(j) != prefix.charAt(j)) {
                    prefixLen = j;
                    prefix.setLength(prefixLen);
                }
            }
        }

        return prefixLen == 0 ? null : prefix.toString();
    }

    public void setWaiting() {
        list.setListData(WAITING_FOR_DATA);
        list.setForeground(disabledColor);
        enabled = false;
    }

    private class FocusHandler extends FocusAdapter {

        @Override
        public void focusLost(FocusEvent e) {
            if (!e.isTemporary()) {
                setVisible(false);
                completable.requestFocus();
            }
        }
    }

    private class MouseHandler extends MouseAdapter implements MouseMotionListener {

        @Override
        public void mouseMoved(MouseEvent e) {
            if (!enabled) {
                return;
            }

            if (e.getSource() == list) {
                Point location = e.getPoint();
                int index = list.locationToIndex(location);
                Rectangle r = new Rectangle();
                list.computeVisibleRect(r);
                if (r.contains(location)) {
                    list.setSelectedIndex(index);
                }
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (e.getSource() == list) {
                return;
            }

            if (isVisible()) {
                MouseEvent newEvent = convertMouseEvent(e);
                Rectangle r = new Rectangle();
                list.computeVisibleRect(r);
                Point location = newEvent.getPoint();
                int index = list.locationToIndex(location);
                if (r.contains(location)) {
                    list.setSelectedIndex(index);
                }
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (!enabled) {
                return;
            }

            Point p = e.getPoint();
            int index = list.locationToIndex(p);
            list.setSelectedIndex(index);
            setVisible(false);
            String option = (String) list.getSelectedValue();

            if (option == null) {
                return;
            }

            completable.completeWith(option);
            completable.requestFocus();
        }

        private MouseEvent convertMouseEvent(MouseEvent e) {
            Point convertedPoint = SwingUtilities.convertPoint((Component) e.getSource(),
                    e.getPoint(), list);
            MouseEvent newEvent = new MouseEvent((Component) e.getSource(),
                    e.getID(),
                    e.getWhen(),
                    e.getModifiers(),
                    convertedPoint.x,
                    convertedPoint.y,
                    e.getClickCount(),
                    e.isPopupTrigger());
            return newEvent;
        }
    }

    private String loc(String message, String... params) {
        return NbBundle.getMessage(CompletionPopup.class, message, params);
    }
}
