/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.modules.markhtmloccurrences;

import java.awt.Color;
import java.lang.ref.WeakReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JEditorPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyleConstants;
import org.netbeans.api.editor.settings.AttributesUtilities;
import org.netbeans.modules.editor.NbEditorUtilities;
import org.netbeans.spi.editor.highlighting.support.OffsetsBag;
import org.openide.cookies.EditorCookie;
import org.openide.loaders.DataObject;
import org.openide.util.RequestProcessor;

public class MarkHTMLOccurrencesHighlighter implements CaretListener {

    private static final AttributeSet defaultColors =
            AttributesUtilities.createImmutable(StyleConstants.Background,
            new Color(236, 235, 163));
    
    private final OffsetsBag bag;
    
    private JTextComponent comp;
    private final WeakReference<Document> weakDoc;
    
    private final RequestProcessor rp;
    private final static int REFRESH_DELAY = 100;
    private RequestProcessor.Task lastRefreshTask;

    public MarkHTMLOccurrencesHighlighter(Document doc) {
        rp = new RequestProcessor(MarkHTMLOccurrencesHighlighter.class);
        bag = new OffsetsBag(doc);
        weakDoc = new WeakReference<Document>((Document) doc);
        DataObject dobj = NbEditorUtilities.getDataObject(weakDoc.get());
        if (dobj != null) {
            EditorCookie pane = dobj.getLookup().lookup(EditorCookie.class);
            JEditorPane[] panes = pane.getOpenedPanes();
            if (panes != null && panes.length > 0) {
                comp = panes[0];
                comp.addCaretListener(this);
            }
        }
    }

    @Override
    public void caretUpdate(CaretEvent e) {
        bag.clear();
        setupAutoRefresh();
    }

    public void setupAutoRefresh() {
        if (lastRefreshTask == null) {
            lastRefreshTask = rp.create(new Runnable() {
                @Override
                public void run() {
                    String selection = comp.getSelectedText();
                    if (selection != null) {
                        Pattern p = Pattern.compile(selection);
                        Matcher m = p.matcher(comp.getText());
                        while (m.find() == true) {
                            int startOffset = m.start();
                            int endOffset = m.end();
                            bag.addHighlight(startOffset, endOffset, defaultColors);
                        }
                    }
                }
            });
        }
        lastRefreshTask.schedule(REFRESH_DELAY);
    }

    public OffsetsBag getHighlightsBag() {
        return bag;
    }

}