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

package org.netbeans.modules.debugger.ui.eval;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.lang.ref.SoftReference;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import org.openide.util.Utilities;

/**
 * This class is used as a content for PopupSwitcher classes (see below). It
 * appropriately displays its contents (<code>SwitcherTableItem</code>s)
 * according to screen size, given position, used font, number of items, etc.
 * and inteligently consider number of rows and columns to be used.
 *
 * @see SwitcherTableItem
 *
 * @author mkrauskopf
 */
public class SwitcherTable extends JTable {
    
    private static final Border rendererBorder =
            BorderFactory.createEmptyBorder(2, 5, 0, 5);
    
    private Icon nullIcon = new NullIcon();
    private Color foreground;
    private Color background;
    private Color selForeground;
    private Color selBackground;
    private int width;
    
    /** Cached preferred size value */
    private Dimension prefSize;
    
    /** Current NetBeans LookAndFreel id */
    /**
     * Flag indicating that the fixed row height has not yet been calculated -
     * this is for fontsize support
     */
    private boolean needCalcRowHeight = true;
    
    /**
     * Creates a new instance of SwitcherTable. Created table will be as high
     * as possible. Height will be used during the number of row computing.
     */
    public SwitcherTable(SwitcherTableItem[] items) {
        this(items, 0, 0);
    }
    
    /**
     * Creates a new instance of SwitcherTable. Height of created table will be
     * computed according to given y coordinate. Height will be used during the
     * number of row computing.
     */
    public SwitcherTable(SwitcherTableItem[] items, int height, int width) {
        super();
        this.width = width;
        init();
        setModel(new SwitcherTableModel(items, getRowHeight(), height));
        getSelectionModel().clearSelection();
        getSelectionModel().setAnchorSelectionIndex(-1);
        getSelectionModel().setLeadSelectionIndex(-1);
        setAutoscrolls( false );
    }
    
    private void init() {
        setBorder(BorderFactory.createLineBorder(getForeground()));
        setShowHorizontalLines(false);
        // Calc row height here so that TableModel can adjust number of columns.
        calcRowHeight(getOffscreenGraphics());
    }
    
    public void updateUI() {
        needCalcRowHeight = true;
        super.updateUI();
    }
    
    public void setFont(Font f) {
        needCalcRowHeight = true;
        super.setFont(f);
    }
    
    public Component prepareRenderer(
            TableCellRenderer renderer,
            int row,
            int column) {
        
        SwitcherTableItem item
                = (SwitcherTableItem) getSwitcherTableModel().getValueAt(row, column);
        
        boolean selected = row == getSelectedRow() &&
                column == getSelectedColumn() && item != null;
        
        DefaultTableCellRenderer ren = (DefaultTableCellRenderer)
        renderer.getTableCellRendererComponent(this, item,
                selected, selected, row, column);
        
        if (item == null) {
            // it's a filler space, we're done
            ren.setOpaque(false);
            ren.setIcon(null);
            return ren;
        }
        
        Icon icon = item.getIcon();
        if (icon == null ) {
            icon = nullIcon;
        }
        ren.setText(selected || item.isActive() ? stripHtml( item.getHtmlName() ) : item.getHtmlName());
        ren.setIcon(icon);
        ren.setBorder(rendererBorder);
        ren.setIconTextGap(26 - icon.getIconWidth());
        
        if (item.isActive()) {
            if( Utilities.isWindows() ) {
                ren.setFont(getFont().deriveFont(Font.BOLD, getFont().getSize()));
            } else {
                // don't use deriveFont() - see #49973 for details
                ren.setFont(new Font(getFont().getName(), Font.BOLD, getFont().getSize()));
            }
        }
        
        ren.setOpaque(true);
        
        return ren;
    }
    
    private String stripHtml( String htmlText ) {
        if( null == htmlText )
            return null;
        String res = htmlText.replaceAll( "<[^>]*>", "" ); // NOI18N // NOI18N
        res = res.replaceAll( "&nbsp;", " " ); // NOI18N // NOI18N
        res = res.trim();
        return res;
    }
    
    private static class NullIcon implements Icon {
        public int getIconWidth() { return 0; }
        public int getIconHeight() { return 0; }
        public void paintIcon(Component c, Graphics g, int x, int y) {}
    }
    
    public Color getForeground() {
        if (foreground == null) {
            foreground = UIManager.getColor("ComboBox.foreground");
        }
        return foreground != null ? foreground : super.getForeground();
    }
    
    public Color getBackground() {
        if (background == null) {
            background = UIManager.getColor("ComboBox.background");
        }
        return background != null ? background : super.getBackground();
    }
    
    public Color getSelectionForeground() {
        if (selForeground == null) {
            selForeground = UIManager.getColor("ComboBox.selectionForeground");
        }
        return selForeground != null ? selForeground : super.getSelectionForeground();
    }
    
    public Color getSelectionBackground() {
        if (selBackground == null) {
            selBackground = UIManager.getColor("ComboBox.selectionBackground");
        }
        return selBackground != null ? selBackground : super.getSelectionBackground();
    }
    
    /**
     * Calculate the height of rows based on the current font.  This is done
     * when the first paint occurs, to ensure that a valid Graphics object is
     * available.
     *
     * @since 1.25
     */
    private void calcRowHeight(Graphics g) {
        Font f = getFont();
        FontMetrics fm = g.getFontMetrics(f);
        // As icons are displayed use maximum from font and icon height
        int rowHeight = Math.max(fm.getHeight(), 16) + 4;
        needCalcRowHeight = false;
        setRowHeight(rowHeight);
    }
    
    private static SoftReference<BufferedImage> ctx = null;
    
    /**
     * Provides an offscreen graphics context so that widths based on character
     * size can be calculated correctly before the component is shown
     */
    private static Graphics2D getOffscreenGraphics() {
        BufferedImage result = null;
        // XXX multi-monitors w/ different resolution may have problems; Better
        // to call Toolkit to create a screen graphics
        if (ctx != null) {
            result = ctx.get();
        }
        if (result == null) {
            result = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
            ctx = new SoftReference<BufferedImage>(result);
        }
        return (Graphics2D) result.getGraphics();
    }
    
    /**
     * Overridden to calculate a preferred size based on the current optimal
     * number of columns, and set up the preferred width for each column based
     * on the maximum width item & icon displayed in it
     */
    public Dimension getPreferredSize() {
        if (prefSize == null) {
            int cols = getColumnCount();
            int rows = getRowCount();
            
            // Iterate all rows and find the widest cell of a whole table
            int columnWidth = 0;
            for (int i = 0; i < cols; i++) {
                for (int j = 0; j < rows; j++) {
                    TableCellRenderer ren = getCellRenderer(j,i);
                    Component c = prepareRenderer(ren, j, i);
                    // sometime adding of one pixel is needed to prevent "..." truncating
                    columnWidth = Math.max(
                            c.getPreferredSize().width + 1, columnWidth);
                }
            }
            int maxColumnWidth = (width > 0) ? width/cols : 250;
            columnWidth = Math.min(columnWidth, maxColumnWidth);
            // Set the same (maximum) widht to all columns
            for (int i = 0; i < cols; i++) {
                getColumnModel().getColumn(i).setPreferredWidth(columnWidth);
            }
            // Rows will be fixed height, so just multiply it out
            prefSize = new Dimension(columnWidth * cols, rows * getRowHeight());
        }
        return prefSize;
    }
    
    private SwitcherTableModel getSwitcherTableModel() {
        return (SwitcherTableModel) getModel();
    }
    
    public SwitcherTableItem getSelectedItem() {
        return (SwitcherTableItem) getValueAt(getSelectedRow(), getSelectedColumn());
    }
    
    public void paint(Graphics g) {
        if (needCalcRowHeight) {
            calcRowHeight(g);
        }
        super.paint(g);
    }
    
    /**
     * Returns the last valid row in the last collumn.
     *
     * @return index of last non-null value in the last collumn or -1 when all
     * values are null.
     */
    public int getLastValidRow() {
        int lastColIdx = getColumnCount() - 1;
        for (int i = getRowCount() - 1; i >= 0; i--) {
            if (getValueAt(i, lastColIdx) != null) {
                return i;
            }
        }
        return -1;
    }
}
