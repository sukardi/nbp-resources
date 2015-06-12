/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2013 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2013 Sun Microsystems, Inc.
 */
package org.netbeans.swing.laf.dark;

import java.awt.Color;
import java.awt.Font;
import java.util.concurrent.Callable;
import javax.swing.JLabel;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

/**
 *
 * @author S. Aubrecht
 */
public class DarkNimbusTheme {

    public static void install( LookAndFeel laf ) {

        Color caretForeground = new Color( 230, 230, 230);
        Color selectionBackground = new Color( 104, 93, 156);
        Color selectedText = new Color( 255, 255, 255);        
        
        UIManager.put( "nb.dark.theme", Boolean.TRUE );
        UIManager.put( "control", new Color( 128, 128, 128) );
        UIManager.put( "info", new Color(128,128,128) );
        UIManager.put( "nimbusBase", new Color( 18, 30, 49) );
        UIManager.put( "nimbusAlertYellow", new Color( 248, 187, 0) );
        UIManager.put( "nimbusDisabledText", new Color( 196, 196, 196) );
        UIManager.put( "nimbusFocus", new Color(115,164,209) );
        UIManager.put( "nimbusGreen", new Color(176,179,50) );
        UIManager.put( "nimbusInfoBlue", new Color( 66, 139, 221) );
        UIManager.put( "nimbusLightBackground", new Color( 18, 30, 49) );
        UIManager.put( "nimbusOrange", new Color(191,98,4) );
        UIManager.put( "nimbusRed", new Color(169,46,34) );
        UIManager.put( "nimbusSelectedText", selectedText );
        UIManager.put( "nimbusSelectionBackground", selectionBackground );
        UIManager.put( "text", new Color( 230, 230, 230) );
//        UIManager.put( "nb.imageicon.filter", new DarkIconFilter() );
        UIManager.put( "nb.errorForeground", new Color(127,0,0) ); //NOI18N
        UIManager.put( "nb.warningForeground", new Color(255,216,0) ); //NOI18N

        UIManager.put( "nb.heapview.border1", new Color( 128, 128, 128) ); //NOI18N
        UIManager.put( "nb.heapview.border2", new Color( 128, 128, 128).darker() ); //NOI18N
        UIManager.put( "nb.heapview.border3", new Color(115,164,209) ); //NOI18N

        UIManager.put( "nb.heapview.foreground", new Color( 230, 230, 230) ); //NOI18N

        UIManager.put( "nb.heapview.background1", new Color( 18, 30, 49) ); //NOI18N

        UIManager.put( "nb.heapview.background2", new Color( 18, 30, 49).brighter() ); //NOI18N

        UIManager.put( "nb.heapview.grid1.start", new Color( 97, 95, 87 ) ); //NOI18N
        UIManager.put( "nb.heapview.grid1.end", new Color( 98, 96, 88 ) ); //NOI18N
        UIManager.put( "nb.heapview.grid2.start", new Color( 99, 97, 90 ) ); //NOI18N
        UIManager.put( "nb.heapview.grid2.end", new Color( 101, 99, 92 ) ); //NOI18N
        UIManager.put( "nb.heapview.grid3.start", new Color( 102, 101, 93 ) ); //NOI18N
        UIManager.put( "nb.heapview.grid3.end", new Color( 105, 103, 95 ) ); //NOI18N
        UIManager.put( "nb.heapview.grid4.start", new Color( 107, 105, 97 ) ); //NOI18N
        UIManager.put( "nb.heapview.grid4.end", new Color( 109, 107, 99 ) ); //NOI18N

        UIManager.put( "PropSheet.setBackground", new Color(112, 112, 112) ); //NOI18N
        UIManager.put( "PropSheet.selectedSetBackground", new Color(100, 100, 100) ); //NOI18N

        UIManager.put( "nb.bugtracking.comment.background", new Color(112, 112, 112) ); //NOI18N
        UIManager.put( "nb.bugtracking.comment.foreground", new Color(230, 230, 230) ); //NOI18N
        UIManager.put( "nb.bugtracking.label.highlight", new Color(160, 160, 160) ); //NOI18N
        UIManager.put( "nb.bugtracking.table.background", new Color(18, 30, 49) ); //NOI18N
        UIManager.put( "nb.bugtracking.table.background.alternate", new Color(13, 22, 36) ); //NOI18N
        UIManager.put( "nb.bugtracking.new.color", new Color(0, 224, 0)); //NOI18N
        UIManager.put( "nb.bugtracking.modified.color", new Color(81, 182, 255)); //NOI18N
        UIManager.put( "nb.bugtracking.obsolete.color", new Color(153, 153, 153)); //NOI18N
        UIManager.put( "nb.bugtracking.conflict.color", new Color(255, 51, 51)); //NOI18N

        UIManager.put( "nb.html.link.foreground", new Color(164,164,255) ); //NOI18N
        UIManager.put( "nb.html.link.foreground.hover", new Color(255,216,0) ); //NOI18N
        UIManager.put( "nb.html.link.foreground.visited", new Color(0,200,0) ); //NOI18N
        UIManager.put( "nb.html.link.foreground.focus", new Color(255,216,0) ); //NOI18N

        UIManager.put( "nb.startpage.defaultbackground", Boolean.TRUE );
        UIManager.put( "nb.startpage.defaultbuttonborder", Boolean.TRUE );
        UIManager.put( "nb.startpage.bottombar.background", new Color(64,64,64) );
        UIManager.put( "nb.startpage.topbar.background", new Color(64,64,64) );
        UIManager.put( "nb.startpage.border.color", new Color(18, 30, 49) );
        UIManager.put( "nb.startpage.tab.border1.color", new Color(64,64,64) );
        UIManager.put( "nb.startpage.tab.border2.color", new Color(64,64,64) );
        UIManager.put( "nb.startpage.rss.details.color", new Color(230, 230, 230) );
        UIManager.put( "nb.startpage.rss.header.color", new Color(128,128,255) );
        UIManager.put( "nb.startpage.tab.imagename.selected", "org/netbeans/modules/welcome/resources/tab_selected_dark.png" ); //NOI18N
        UIManager.put( "nb.startpage.tab.imagename.rollover", "org/netbeans/modules/welcome/resources/tab_rollover_dark.png" ); //NOI18N
        UIManager.put( "nb.startpage.imagename.contentheader", "org/netbeans/modules/welcome/resources/content_banner_dark.png" ); //NOI18N
        UIManager.put( "nb.startpage.contentheader.color1", new Color(12,33,61) ); //NOI18N
        UIManager.put( "nb.startpage.contentheader.color2", new Color(16,24,42) ); //NOI18N

        UIManager.put( "nb.popupswitcher.background", new Color(18, 30, 49) ); //NOI18N

        UIManager.put( "TextField.selectionForeground", selectedText); //NOI18N
        UIManager.put( "TextField.selectionBackground", selectionBackground); //NOI18N
        UIManager.put( "TextField.caretForeground", caretForeground); //NOI18N
        UIManager.put( "nb.editor.errorstripe.caret.color", caretForeground ); //NOI18N        

        UIManager.put( "nb.wizard.hideimage", Boolean.TRUE ); //NOI18N

        //diff & diff sidebar
        UIManager.put( "nb.diff.added.color", new Color(36, 52, 36) ); //NOI18N
        UIManager.put( "nb.diff.changed.color", new Color(32, 40, 51) ); //NOI18N
        UIManager.put( "nb.diff.deleted.color", new Color(51, 32, 36) ); //NOI18N
        UIManager.put( "nb.diff.applied.color", new Color(36, 52, 36) ); //NOI18N
        UIManager.put( "nb.diff.notapplied.color", new Color(32, 40, 51) ); //NOI18N
        UIManager.put( "nb.diff.unresolved.color", new Color(51, 32, 36) ); //NOI18N

        UIManager.put( "nb.diff.sidebar.changed.color", new Color(18, 30, 74) ); //NOI18N
        UIManager.put( "nb.diff.sidebar.deleted.color", new Color(66, 30, 49) ); //NOI18N

        UIManager.put( "nb.versioning.tooltip.background.color", new Color(18, 30, 74) ); //NOI18N

        //form designer
        UIManager.put( "nb.formdesigner.gap.fixed.color", new Color(112,112,112) ); //NOI18N
        UIManager.put( "nb.formdesigner.gap.resizing.color", new Color(116,116,116) ); //NOI18N
        UIManager.put( "nb.formdesigner.gap.min.color", new Color(104,104,104) ); //NOI18N

        UIManager.put( "nbProgressBar.Foreground", new Color( 230, 230, 230) );
        UIManager.put( "nbProgressBar.popupDynaText.foreground", new Color(191, 186, 172) );

        // debugger
        UIManager.put( "nb.debugger.debugging.currentThread", new Color(30, 80, 28) ); //NOI18N
        UIManager.put( "nb.debugger.debugging.highlightColor", new Color(40, 60, 38) ); //NOI18N
        UIManager.put( "nb.debugger.debugging.BPHits", new Color(65, 65, 0)); //NOI18N
        UIManager.put( "nb.debugger.debugging.bars.BPHits", new Color(120, 120, 25)); //NOI18N
        UIManager.put( "nb.debugger.debugging.bars.currentThread", new Color(40, 100, 35)); //NOI18N

        //versioning
        UIManager.put( "nb.versioning.added.color", new Color(0, 224, 0)); //NOI18N
        UIManager.put( "nb.versioning.modified.color", new Color(81, 182, 255)); //NOI18N
        UIManager.put( "nb.versioning.deleted.color", new Color(153, 153, 153)); //NOI18N
        UIManager.put( "nb.versioning.conflicted.color", new Color(255, 51, 51)); //NOI18N
        UIManager.put( "nb.versioning.ignored.color", new Color(153, 153, 153)); //NOI18N
        UIManager.put( "nb.versioning.remotemodification.color", new Color( 230, 230, 230)); //NOI18N

        // db.dataview
        UIManager.put("nb.dataview.table.background", new RelativeColor(new Color(0,0,0), new Color(0,0,0), "Table.background")); //NOI18N
        UIManager.put("nb.dataview.table.altbackground", new RelativeColor(new Color(0,0,0), new Color(30,30,30), "Table.background")); //NOI18N
        UIManager.put("nb.dataview.table.sqlconstant.foreground", new Color(220,220,220)); //NOI18N
        UIManager.put("Table.selectionBackground", new RelativeColor(new Color(0,0,0), new Color(0,0,0), "Table[Enabled+Selected].textBackground")); //NOI18N
        UIManager.put("nb.dataview.tablecell.focused", new RelativeColor(new Color(0,0,0), new Color(30,10,10), "Table.selectionBackground")); //NOI18N
        UIManager.put("nb.dataview.table.rollOverRowBackground", new RelativeColor(new Color(0,0,0), new Color(30,30,30), "Table[Enabled+Selected].textBackground")); //NOI18N
        UIManager.put("nb.dataview.tablecell.edited.selected.foreground", new Color(255, 248, 60));  //NOI18N
        UIManager.put("nb.dataview.tablecell.edited.unselected.foreground", new Color(0, 255, 16));  //NOI18N
        UIManager.put("nb.dataview.jxdatetimepicker.background",  new RelativeColor(new Color(0,0,0), new Color(0,0,0), "Table.background")); //NOI18N
        UIManager.put("nb.dataview.jxdatetimepicker.foreground", new RelativeColor(new Color(0,0,0), new Color(0,0,0), "Table.foreground")); //NOI18N
        UIManager.put("nb.dataview.jxdatetimepicker.selectedBackground", new RelativeColor(new Color(0,0,0), new Color(0,0,0), "Table[Enabled+Selected].textBackground")); //NOI18N
        UIManager.put("nb.dataview.jxdatetimepicker.selectedForeground", new RelativeColor(new Color(0,0,0), new Color(0,0,0), "Table[Enabled+Selected].textForeground")); //NOI18N
        UIManager.put("nb.dataview.jxdatetimepicker.daysOfTheWeekForeground", new RelativeColor(new Color(0,0,0), new Color(0,0,0), "Table.background")); //NOI18N
        UIManager.put("nb.dataview.jxdatetimepicker.todayBackground", new RelativeColor(new Color(0,0,0), new Color(20,20,20), "Table.background")); //NOI18N
        UIManager.put("nb.dataview.jxdatetimepicker.todayPanel.background.gradient.start", new RelativeColor(new Color(0,0,0), new Color(0,0,0), "TableHeader.background")); //NOI18N
        UIManager.put("nb.dataview.jxdatetimepicker.todayPanel.background.gradient.end", new RelativeColor(new Color(0,0,0), new Color(10,10,10), "TableHeader.background")); //NOI18N
        UIManager.put("nb.dataview.jxdatetimepicker.todayPanel.linkForeground", new RelativeColor(new Color(0,0,0), new Color(0,0,0), "TableHeader.foreground")); //NOI18N
        
        // autoupdate
        UIManager.put("nb.autoupdate.search.highlight", new Color(255, 75, 0));
        
        UIManager.put("selection.highlight", new Color(202, 152, 0));
        UIManager.put( "textArea.background", new Color( 128, 128, 128) );

        UIManager.put( "nb.laf.postinstall.callable", new Callable<Object>() { //NOI18N

            @Override
            public Object call() throws Exception {
                //change the default link foreground color
                HTMLEditorKit kit = new HTMLEditorKit();
                StyleSheet newStyleSheet = new StyleSheet();
                Font f = new JLabel().getFont();
                newStyleSheet.addRule(new StringBuffer("body { font-size: ").append(f.getSize()) // NOI18N
                            .append("; font-family: ").append(f.getName()).append("; }").toString()); // NOI18N
                newStyleSheet.addRule( "a { color: #A4A4FF; text-decoration: underline}"); //NOI18N
                newStyleSheet.addStyleSheet(kit.getStyleSheet());
                kit.setStyleSheet(newStyleSheet);
                return null;
            }
        });

        UIManager.put( "nb.close.tab.icon.enabled.name", "org/openide/awt/resources/vista_close_enabled.png");
        UIManager.put( "nb.close.tab.icon.pressed.name", "org/openide/awt/resources/vista_close_pressed.png");
        UIManager.put( "nb.close.tab.icon.rollover.name", "org/openide/awt/resources/vista_close_rollover.png");
        UIManager.put( "nb.bigclose.tab.icon.enabled.name", "org/openide/awt/resources/vista_bigclose_rollover.png");
        UIManager.put( "nb.bigclose.tab.icon.pressed.name", "org/openide/awt/resources/vista_bigclose_rollover.png");
        UIManager.put( "nb.bigclose.tab.icon.rollover.name", "org/openide/awt/resources/vista_bigclose_rollover.png");

        //browser picker
        UIManager.put( "Nb.browser.picker.background.light", new Color(116,116,116));
        UIManager.put( "Nb.browser.picker.foreground.light", new Color(192,192,192));
        //#233622
        UIManager.put( "List[Selected].textForeground", UIManager.getColor( "nimbusSelectedText" ) );

        UIManager.put( "nb.explorer.noFocusSelectionBackground", UIManager.get( "nimbusSelectionBackground") );

        //search in projects
        UIManager.put("nb.search.sandbox.highlight", selectionBackground);
        UIManager.put("nb.search.sandbox.regexp.wrong", new Color(255, 71, 71));
   }
}
