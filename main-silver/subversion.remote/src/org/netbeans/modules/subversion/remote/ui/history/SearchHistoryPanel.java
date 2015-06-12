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
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2008 Sun
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

package org.netbeans.modules.subversion.remote.ui.history;

import java.awt.Color;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.openide.util.NbBundle;
import org.openide.explorer.ExplorerManager;
import org.openide.nodes.Node;
import org.openide.windows.TopComponent;
import org.openide.awt.Mnemonics;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import org.netbeans.modules.subversion.remote.Subversion;
import org.netbeans.modules.subversion.remote.SvnModuleConfig;
import org.netbeans.modules.subversion.remote.api.SVNClientException;
import org.netbeans.modules.subversion.remote.api.SVNRevision;
import org.netbeans.modules.subversion.remote.api.SVNUrl;
import org.netbeans.modules.subversion.remote.client.SvnClientExceptionHandler;
import org.netbeans.modules.subversion.remote.client.SvnProgressSupport;
import org.netbeans.modules.subversion.remote.ui.diff.DiffSetupSource;
import org.netbeans.modules.subversion.remote.ui.diff.Setup;
import org.netbeans.modules.subversion.remote.ui.history.SearchHistoryTopComponent.DiffResultsViewFactory;
import org.netbeans.modules.subversion.remote.ui.history.SummaryView.SvnLogEntry;
import org.netbeans.modules.subversion.remote.util.Context;
import org.netbeans.modules.subversion.remote.util.SvnUtils;
import org.netbeans.modules.remotefs.versioning.api.VCSFileProxySupport;
import org.netbeans.modules.versioning.core.api.VCSFileProxy;
import org.netbeans.modules.versioning.history.AbstractSummaryView.SummaryViewMaster.SearchHighlight;
import org.netbeans.modules.versioning.history.AbstractSummaryView.SummaryViewMaster.SearchHighlight.Kind;
import org.netbeans.modules.versioning.util.NoContentPanel;
import org.openide.filesystems.FileSystem;

/**
 * Contains all components of the Search History panel.
 *
 * @author Maros Sandor
 */
class SearchHistoryPanel extends javax.swing.JPanel implements ExplorerManager.Provider, PropertyChangeListener, DiffSetupSource, DocumentListener, ActionListener {

    private final VCSFileProxy[]                roots;
    private final SVNUrl                repositoryUrl;
    private final SearchCriteriaPanel   criteria;
    
    private Action                  searchAction;
    private SearchExecutor          currentSearch;
    private Search                  currentAdditionalSearch;

    private boolean                 criteriaVisible;
    private boolean                 searchInProgress;
    private List<RepositoryRevision> results;
    private SummaryView             summaryView;    
    private DiffResultsView         diffView;
    private SearchHistoryTopComponent.DiffResultsViewFactory diffViewFactory;
    
    private AbstractAction nextAction;
    private AbstractAction prevAction;
    
    private static final Icon ICON_COLLAPSED = UIManager.getIcon("Tree.collapsedIcon"); //NOI18N
    private static final Icon ICON_EXPANDED = UIManager.getIcon("Tree.expandedIcon"); //NOI18N
    private int showingResults;
    private List<SvnLogEntry> logEntries;
    private boolean selectFirstRevision;
    private final FileSystem fileSystem;

    enum FilterKind {
        ALL(null, NbBundle.getMessage(SearchHistoryPanel.class, "Filter.All")), //NOI18N
        MESSAGE(SearchHighlight.Kind.MESSAGE, NbBundle.getMessage(SearchHistoryPanel.class, "Filter.Message")), //NOI18N
        USER(SearchHighlight.Kind.AUTHOR, NbBundle.getMessage(SearchHistoryPanel.class, "Filter.User")), //NOI18N
        ID(SearchHighlight.Kind.REVISION, NbBundle.getMessage(SearchHistoryPanel.class, "Filter.Commit")), //NOI18N
        FILE(SearchHighlight.Kind.FILE, NbBundle.getMessage(SearchHistoryPanel.class, "Filter.File")); //NOI18N
        private final String label;
        private final Kind kind;
        
        FilterKind (SearchHighlight.Kind kind, String label) {
            this.kind = kind;
            this.label = label;
        }
        
        @Override
        public final String toString () {
            return label;
        }
    }
    private final Timer filterTimer;

    /** Creates new form SearchHistoryPanel */
    public SearchHistoryPanel(VCSFileProxy [] roots, SearchCriteriaPanel criteria) {
        this.roots = roots;
        fileSystem = VCSFileProxySupport.getFileSystem(roots[0]);
        this.repositoryUrl = null;
        this.criteria = criteria;
        this.diffViewFactory = new SearchHistoryTopComponent.DiffResultsViewFactory();
        criteriaVisible = true;
        explorerManager = new ExplorerManager ();
        initComponents();
        initializeFilter();
        filterTimer = new Timer(500, this);
        filterTimer.setRepeats(false);
        filterTimer.stop();
        aquaBackgroundWorkaround();
        setupComponents();
        refreshComponents(true);
    }
    
    public SearchHistoryPanel(SVNUrl repositoryUrl, VCSFileProxy localRoot, SearchCriteriaPanel criteria) {
        this.repositoryUrl = repositoryUrl;
        this.roots = new VCSFileProxy[] { localRoot };
        fileSystem = VCSFileProxySupport.getFileSystem(roots[0]);
        this.criteria = criteria;
        this.diffViewFactory = new SearchHistoryTopComponent.DiffResultsViewFactory();
        criteriaVisible = true;
        explorerManager = new ExplorerManager ();
        initComponents();
        initializeFilter();
        filterTimer = new Timer(500, this);
        filterTimer.stop();
        aquaBackgroundWorkaround();
        setupComponents();
        refreshComponents(true);
    }

    public FileSystem getFileSystem() {
        return fileSystem;
    }
    
    private void aquaBackgroundWorkaround() {
        if( "Aqua".equals( UIManager.getLookAndFeel().getID() ) ) {             // NOI18N
            Color color = UIManager.getColor("NbExplorerView.background");      // NOI18N
            setBackground(color); 
            jToolBar1.setBackground(color); 
            resultsPanel.setBackground(color); 
            searchCriteriaPanel.setBackground(color); 
            criteria.setBackground(color); 
        }
    }

    /**
     * Sets the factory creating the appropriate DiffResultsView to display.
     * @param fac factory creating the appropriate DiffResultsView to display. If null then a default factory will be created.
     */
    public void setDiffResultsViewFactory(DiffResultsViewFactory fac) {
        if (fac != null) {
            this.diffViewFactory = fac;
        }
    }

    void setSearchCriteria(boolean showCriteria) {
        criteriaVisible = showCriteria;
        refreshComponents(false);
    }

    private void enableFilters (boolean enabled) {
        lblFilter.setEnabled(enabled);
        cmbFilterKind.setEnabled(enabled);
        lblFilterContains.setEnabled(enabled);
        txtFilter.setEnabled(enabled);
    }

    private void setupComponents() {
        searchCriteriaPanel.add(criteria);
        searchAction = new AbstractAction(NbBundle.getMessage(SearchHistoryPanel.class,  "CTL_Search")) { // NOI18N
            {
                putValue(Action.SHORT_DESCRIPTION, NbBundle.getMessage(SearchHistoryPanel.class, "TT_Search")); // NOI18N
            }
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }
        };
        searchCriteriaPanel.getInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "search"); // NOI18N
        searchCriteriaPanel.getActionMap().put("search", searchAction); // NOI18N
        bSearch.setAction(searchAction);
        Mnemonics.setLocalizedText(bSearch, NbBundle.getMessage(SearchHistoryPanel.class,  "CTL_Search")); // NOI18N
        
        Dimension d1 = tbSummary.getPreferredSize();
        Dimension d2 = tbDiff.getPreferredSize();
        if (d1.width > d2.width) {
            tbDiff.setPreferredSize(d1);
        }
        
        nextAction = new AbstractAction(null, new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/modules/subversion/remote/resources/icons/diff-next.png"))) { // NOI18N
            {
                putValue(Action.SHORT_DESCRIPTION, java.util.ResourceBundle.getBundle("org/netbeans/modules/subversion/remote/ui/diff/Bundle"). // NOI18N
                                                   getString("CTL_DiffPanel_Next_Tooltip")); // NOI18N
            }
            @Override
            public void actionPerformed(ActionEvent e) {
                diffView.onNextButton();
            }
        };
        prevAction = new AbstractAction(null, new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/modules/subversion/remote/resources/icons/diff-prev.png"))) { // NOI18N
            {
                putValue(Action.SHORT_DESCRIPTION, java.util.ResourceBundle.getBundle("org/netbeans/modules/subversion/remote/ui/diff/Bundle"). // NOI18N
                                                   getString("CTL_DiffPanel_Prev_Tooltip")); // NOI18N
            }
            @Override
            public void actionPerformed(ActionEvent e) {
                diffView.onPrevButton();
            }
        };
        bNext.setAction(nextAction);
        bPrev.setAction(prevAction);

        criteria.tfFrom.getDocument().addDocumentListener(this);
        criteria.tfTo.getDocument().addDocumentListener(this);
        
        getActionMap().put("jumpNext", nextAction); // NOI18N
        getActionMap().put("jumpPrev", prevAction); // NOI18N

        fileInfoCheckBox.setSelected(SvnModuleConfig.getDefault(fileSystem).getShowFileAllInfo());
    }

    private ExplorerManager             explorerManager;

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (ExplorerManager.PROP_SELECTED_NODES.equals(evt.getPropertyName())) {
            TopComponent tc = (TopComponent) SwingUtilities.getAncestorOfClass(TopComponent.class, this);
            if (tc == null) {
                return;
            }
            tc.setActivatedNodes((Node[]) evt.getNewValue());
        }
    }

    @Override
    public void addNotify() {
        super.addNotify();
        explorerManager.addPropertyChangeListener(this);
    }

    @Override
    public void removeNotify() {
        explorerManager.removePropertyChangeListener(this);
        super.removeNotify();
    }
    
    @Override
    public ExplorerManager getExplorerManager () {
        return explorerManager;
    }
    
    final void refreshComponents(boolean refreshResults) {
        if (refreshResults) {
            resultsPanel.removeAll();
            if (results == null) {
                if (searchInProgress) {
                    resultsPanel.add(new NoContentPanel(NbBundle.getMessage(SearchHistoryPanel.class, "LBL_SearchHistory_Searching"))); // NOI18N
                } else {
                    resultsPanel.add(new NoContentPanel(NbBundle.getMessage(SearchHistoryPanel.class, "LBL_SearchHistory_NoResults"))); // NOI18N
                }
            } else {
                if (tbSummary.isSelected()) {
                    if (summaryView == null) {
                        summaryView = new SummaryView(this, logEntries = createLogEntries(results));
                    }
                    resultsPanel.add(summaryView.getComponent());
                    summaryView.requestFocusInWindow();
                } else {
                    if (diffView == null) {
                        diffView = diffViewFactory.createDiffResultsView(this, filter(results));
                    }
                    resultsPanel.add(diffView.getComponent());
                    if (selectFirstRevision) {
                        selectFirstRevision();
                    }
                }
            }
            resultsPanel.revalidate();
            resultsPanel.repaint();
        }
        nextAction.setEnabled(!tbSummary.isSelected() && diffView != null && diffView.isNextEnabled());
        prevAction.setEnabled(!tbSummary.isSelected() && diffView != null && diffView.isPrevEnabled());

        searchCriteriaPanel.setVisible(criteriaVisible);
        expandCriteriaButton.setIcon(criteriaVisible ? ICON_EXPANDED : ICON_COLLAPSED);
        fileInfoCheckBox.setEnabled(tbSummary.isSelected());
        enableFilters(results != null);
        revalidate();
        repaint();
    }

    private void selectFirstRevision () {
        if (diffView != null && results != null && !results.isEmpty()) {
            diffView.select(results.get(0));
        }
    }
    
    void setResults (List<RepositoryRevision> newResults, int limit) {
        setResults(newResults, false, limit);
    }

    private void setResults (List<RepositoryRevision> newResults, boolean searching, int limit) {
        this.results = newResults;
        this.searchInProgress = searching;
        showingResults = limit;
        if (newResults != null && newResults.size() < limit) {
            showingResults = -1;
        }
        summaryView = null;
        diffView = null;
        refreshComponents(true);
    }

    public SVNUrl getRepositoryUrl() {
        return repositoryUrl;
    }

    public SVNUrl getSearchRepositoryRootUrl() throws SVNClientException {
        if (repositoryUrl != null) {
            return repositoryUrl;
        }
        return SvnUtils.getRepositoryRootUrl(roots[0]);
    }

    public VCSFileProxy[] getRoots() {
        return roots;
    }

    public SearchCriteriaPanel getCriteria() {
        return criteria;
    }
    
    boolean isShowInfo() {
        return fileInfoCheckBox.isSelected();
    }

    private synchronized void search() {
        cancelBackgroundTasks();
        setResults(null, true, -1);
        currentSearch = new SearchExecutor(fileSystem, this);
        currentSearch.start();
    }
    
    void windowClosed () {
        cancelBackgroundTasks();
        if (diffView != null) {
            diffView.cancelBackgroundTasks();
        }
    }
    
    void cancelBackgroundTasks () {
        if (currentSearch != null) {
            currentSearch.cancel();
        }
        if (currentAdditionalSearch != null) {
            currentAdditionalSearch.cancel();
        }
    }
    
    void executeSearch() {
        search();
    }

    void showDiff(final RepositoryRevision.Event revision) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                tbDiff.setSelected(true);
                refreshComponents(true);
                diffView.select(revision);
            }
        };
        if(EventQueue.isDispatchThread()) {
            r.run();
        } else {
            EventQueue.invokeLater(r);
        }
    }

    public void showDiff(final RepositoryRevision container) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                tbDiff.setSelected(true);
                refreshComponents(true);
                diffView.select(container);
            }
        };
        if(EventQueue.isDispatchThread()) {
            r.run();
        } else {
            EventQueue.invokeLater(r);
        }
    }

    /**
     * Return diff setup describing shown history.
     * It return empty collection on non-atomic
     * revision ranges. XXX move this logic to clients?
     */
    @Override
    public Collection<Setup> getSetups() {
        if (results == null) {
            return Collections.<Setup>emptySet();
        }
        if (tbDiff.isSelected()) {
            return diffView.getSetups();
        } else {
            return summaryView.getSetups();
        }
    }
    
    Collection<Setup> getSetups(RepositoryRevision [] revisions, RepositoryRevision.Event [] events) {
        long fromRevision = Long.MAX_VALUE;
        long toRevision = Long.MIN_VALUE;
        Set<VCSFileProxy> filesToDiff = new HashSet<>();
        
        for (RepositoryRevision revision : revisions) {
            long rev = revision.getLog().getRevision().getNumber();
            if (rev > toRevision) {
                toRevision = rev;
            }
            if (rev < fromRevision) {
                fromRevision = rev;
            }
            List<RepositoryRevision.Event> evs = revision.getEvents();
            for (RepositoryRevision.Event event : evs) {
                VCSFileProxy file = event.getFile();
                if (file != null) {
                    filesToDiff.add(file);
                }
            }
        }

        for (RepositoryRevision.Event event : events) {
            long rev = event.getLogInfoHeader().getLog().getRevision().getNumber();
            if (rev > toRevision) {
                toRevision = rev;
            }
            if (rev < fromRevision) {
                fromRevision = rev;
            }
            if (event.getFile() != null) {
                filesToDiff.add(event.getFile());
            }
        }

        List<Setup> setups = new ArrayList<>();
        for (VCSFileProxy file : filesToDiff) {
            Setup setup = new Setup(file, Long.toString(fromRevision - 1), Long.toString(toRevision), false);
            setups.add(setup);
        }
        return setups;
    }
    
    @Override
    public String getSetupDisplayName() {
        return null;
    }

    public static int compareRevisions(String r1, String r2) {
        StringTokenizer st1 = new StringTokenizer(r1, "."); // NOI18N
        StringTokenizer st2 = new StringTokenizer(r2, "."); // NOI18N
        for (;;) {
            if (!st1.hasMoreTokens()) {
                return st2.hasMoreTokens() ? -1 : 0;
            }
            if (!st2.hasMoreTokens()) {
                return st1.hasMoreTokens() ? 1 : 0;
            }
            int n1 = Integer.parseInt(st1.nextToken());
            int n2 = Integer.parseInt(st2.nextToken());
            if (n1 != n2) {
                return n2 - n1;
            }
        }
    }

    void activateDiffView (boolean selectFirstRevision) {
        tbDiff.setSelected(true);
        this.selectFirstRevision = selectFirstRevision;
        selectFirstRevision();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        searchCriteriaPanel = new javax.swing.JPanel();
        bSearch = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        tbSummary = new javax.swing.JToggleButton();
        tbDiff = new javax.swing.JToggleButton();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        lblFilter = new javax.swing.JLabel();
        cmbFilterKind = new javax.swing.JComboBox();
        lblFilterContains = new javax.swing.JLabel();
        txtFilter = new javax.swing.JTextField();
        resultsPanel = new javax.swing.JPanel();
        expandCriteriaButton = new org.netbeans.modules.versioning.history.LinkButton();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 0, 8));

        searchCriteriaPanel.setLayout(new java.awt.BorderLayout());

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("org/netbeans/modules/subversion/remote/ui/history/Bundle"); // NOI18N
        bSearch.setToolTipText(bundle.getString("TT_Search")); // NOI18N

        jToolBar1.setLayout(new BoxLayout(jToolBar1, BoxLayout.X_AXIS));
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        buttonGroup1.add(tbSummary);
        tbSummary.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(tbSummary, bundle.getString("CTL_ShowSummary")); // NOI18N
        tbSummary.setToolTipText(bundle.getString("TT_Summary")); // NOI18N
        tbSummary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onViewToggle(evt);
            }
        });
        jToolBar1.add(tbSummary);

        buttonGroup1.add(tbDiff);
        org.openide.awt.Mnemonics.setLocalizedText(tbDiff, bundle.getString("CTL_ShowDiff")); // NOI18N
        tbDiff.setToolTipText(bundle.getString("TT_ShowDiff")); // NOI18N
        tbDiff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onViewToggle(evt);
            }
        });
        jToolBar1.add(tbDiff);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator2.setMaximumSize(new java.awt.Dimension(2, 32767));
        jToolBar1.add(jSeparator2);

        bNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/modules/subversion/remote/resources/icons/diff-next.png"))); // NOI18N
        jToolBar1.add(bNext);
        bNext.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(SearchHistoryPanel.class, "ACSN_NextDifference")); // NOI18N
        bNext.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(SearchHistoryPanel.class, "ACSD_NextDifference")); // NOI18N

        bPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/netbeans/modules/subversion/remote/resources/icons/diff-prev.png"))); // NOI18N
        jToolBar1.add(bPrev);
        bPrev.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(SearchHistoryPanel.class, "ACSN_PrevDifference")); // NOI18N
        bPrev.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(SearchHistoryPanel.class, "ACSD_PrevDifference")); // NOI18N

        jToolBar1.add(jSeparator3);

        org.openide.awt.Mnemonics.setLocalizedText(fileInfoCheckBox, org.openide.util.NbBundle.getMessage(SearchHistoryPanel.class, "LBL_SearchHistoryPanel_AllInfo")); // NOI18N
        fileInfoCheckBox.setOpaque(false);
        fileInfoCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileInfoCheckBoxActionPerformed(evt);
            }
        });
        jToolBar1.add(fileInfoCheckBox);

        org.openide.awt.Mnemonics.setLocalizedText(lblFilter, org.openide.util.NbBundle.getMessage(SearchHistoryPanel.class, "filterLabel.text")); // NOI18N
        lblFilter.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));
        jToolBar1.add(lblFilter);

        cmbFilterKind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFilterKindActionPerformed(evt);
            }
        });
        jToolBar1.add(cmbFilterKind);

        org.openide.awt.Mnemonics.setLocalizedText(lblFilterContains, org.openide.util.NbBundle.getMessage(SearchHistoryPanel.class, "containsLabel")); // NOI18N
        lblFilterContains.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));
        jToolBar1.add(lblFilterContains);
        jToolBar1.add(txtFilter);

        resultsPanel.setLayout(new java.awt.BorderLayout());

        org.openide.awt.Mnemonics.setLocalizedText(expandCriteriaButton, org.openide.util.NbBundle.getMessage(SearchHistoryPanel.class, "CTL_expandCriteriaButton.text")); // NOI18N
        expandCriteriaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expandCriteriaButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(resultsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 504, Short.MAX_VALUE)
            .addComponent(searchCriteriaPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(bSearch))
            .addGroup(layout.createSequentialGroup()
                .addComponent(expandCriteriaButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resultsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(expandCriteriaButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchCriteriaPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bSearch)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void onViewToggle(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onViewToggle
        refreshComponents(true);
    }//GEN-LAST:event_onViewToggle

    private void expandCriteriaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expandCriteriaButtonActionPerformed
        searchCriteriaPanel.setVisible(!searchCriteriaPanel.isVisible());
        expandCriteriaButton.setIcon(searchCriteriaPanel.isVisible() ? ICON_EXPANDED : ICON_COLLAPSED);
        criteriaVisible = searchCriteriaPanel.isVisible();
    }//GEN-LAST:event_expandCriteriaButtonActionPerformed

    private void fileInfoCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileInfoCheckBoxActionPerformed
        SvnModuleConfig.getDefault(fileSystem).setShowFileAllInfo(fileInfoCheckBox.isSelected());
        if (summaryView != null) {
            summaryView.refreshView();
        }
    }//GEN-LAST:event_fileInfoCheckBoxActionPerformed

    private void cmbFilterKindActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFilterKindActionPerformed
        boolean filterCritVisible = cmbFilterKind.getSelectedItem() != FilterKind.ALL;
        lblFilterContains.setVisible(filterCritVisible);
        txtFilter.setVisible(filterCritVisible);
        if (filterCritVisible) {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run () {
                    if (!cmbFilterKind.isPopupVisible()) {
                        txtFilter.requestFocusInWindow();
                    }
                }
            });
        }
        if (filterTimer != null && !txtFilter.getText().trim().isEmpty()) {
            filterTimer.restart();
        }
    }//GEN-LAST:event_cmbFilterKindActionPerformed

    @Override
    public void insertUpdate(DocumentEvent e) {
        documentChanged(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        documentChanged(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        documentChanged(e);
    }
    
    private void documentChanged (DocumentEvent e) {
        if (e.getDocument() == txtFilter.getDocument()) {
            filterTimer.restart();
        } else {
            validateUserInput();
        }
    }
    
    private void validateUserInput() {
        SVNRevision from = criteria.getFrom();
        if(from == null && criteria.tfFrom.getText().trim().length() > 0) {
            bSearch.setEnabled(false);
            return;
        }
        SVNRevision to = criteria.getTo();
        if(to == null && criteria.tfTo.getText().trim().length() > 0) {
            bSearch.setEnabled(false);
            return;
        }        
        bSearch.setEnabled(true);
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        if (e.getSource() == filterTimer) {
            if (summaryView != null) {
                summaryView.refreshView();
            }
            if (diffView != null) {
                diffView.refreshResults(filter(results));
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    final javax.swing.JButton bNext = new javax.swing.JButton();
    final javax.swing.JButton bPrev = new javax.swing.JButton();
    private javax.swing.JButton bSearch;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cmbFilterKind;
    private org.netbeans.modules.versioning.history.LinkButton expandCriteriaButton;
    final javax.swing.JCheckBox fileInfoCheckBox = new javax.swing.JCheckBox();
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblFilter;
    private javax.swing.JLabel lblFilterContains;
    private javax.swing.JPanel resultsPanel;
    private javax.swing.JPanel searchCriteriaPanel;
    private javax.swing.JToggleButton tbDiff;
    private javax.swing.JToggleButton tbSummary;
    private javax.swing.JTextField txtFilter;
    // End of variables declaration//GEN-END:variables
    
    List<RepositoryRevision> getResults () {
        return results;
    }

    boolean hasMoreResults () {
        return showingResults > -1;
    }
    
    void getMoreRevisions (PropertyChangeListener callback, int count) {
        if (currentSearch == null) {
            throw new IllegalStateException("No search task active"); //NOI18N
        }
        if (currentAdditionalSearch != null) {
            currentAdditionalSearch.cancel();
        }
        if (count < 0 || showingResults < 0) {
            count = 0;
        } else {
            count += showingResults;
        }
        try {
            currentAdditionalSearch = new Search(getSearchRepositoryRootUrl(), count);
            currentAdditionalSearch.start(Subversion.getInstance().getParallelRequestProcessor(), currentAdditionalSearch.repoUrl,
                    NbBundle.getMessage(SearchHistoryPanel.class, "MSG_SearchHistoryPanel.GettingMoreRevisions")); //NOI18N
        } catch (SVNClientException ex) {
            SvnClientExceptionHandler.notifyException(new Context(roots), ex, true, true);
        }
    }

    Collection<SearchHighlight> getSearchHighlights () {
        String filterText = txtFilter.getText().trim();
        Object selectedFilterKind = cmbFilterKind.getSelectedItem();
        if (selectedFilterKind == FilterKind.ALL || filterText.isEmpty() || !(selectedFilterKind instanceof FilterKind)) {
            return Collections.<SearchHighlight>emptyList();
        } else {
            return Collections.singleton(new SearchHighlight(((FilterKind) selectedFilterKind).kind, filterText));
        }
    }

    private void initializeFilter () {
        DefaultComboBoxModel<FilterKind> filterModel = new DefaultComboBoxModel<>();
        filterModel.addElement(FilterKind.ALL);
        filterModel.addElement(FilterKind.ID);
        filterModel.addElement(FilterKind.MESSAGE);
        filterModel.addElement(FilterKind.USER);
//        filterModel.addElement(FilterKind.FILE);
        cmbFilterKind.setModel(filterModel);
        cmbFilterKind.setSelectedItem(FilterKind.ALL);
        txtFilter.getDocument().addDocumentListener(this);
    }

    private List<RepositoryRevision> filter (List<RepositoryRevision> results) {
        List<RepositoryRevision> newResults = new ArrayList<>(results.size());
        for (RepositoryRevision rev : results) {
            if (applyFilter(rev)) {
                newResults.add(rev);
            }
        }
        return newResults;
    }

    boolean applyFilter (RepositoryRevision rev) {
        boolean visible = true;
        String filterText = txtFilter.getText().trim().toLowerCase(Locale.getDefault());
        Object selectedFilterKind = cmbFilterKind.getSelectedItem();
        if (selectedFilterKind != FilterKind.ALL && !filterText.isEmpty()) {
            if (selectedFilterKind == FilterKind.MESSAGE) {
                visible = rev.getLog().getMessage().toLowerCase(Locale.getDefault()).contains(filterText);
            } else if (selectedFilterKind == FilterKind.USER) {
                visible = rev.getLog().getAuthor().toLowerCase(Locale.getDefault()).contains(filterText);
            } else if (selectedFilterKind == FilterKind.ID) {
                visible = rev.getLog().getRevision().toString().contains(filterText);
            }
        }
        return visible;
    }

    private class Search extends SvnProgressSupport {
        private final int count;
        private final SearchExecutor executor;
        private final SVNUrl repoUrl;

        private Search (SVNUrl repoUrl, int count) {
            super(fileSystem);
            this.repoUrl = repoUrl;
            this.count = count;
            this.executor = currentSearch;
        }

        @Override
        protected void perform () {
            final List<RepositoryRevision> newResults = executor.search(repoUrl, count, this);
            if (!isCanceled()) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run () {
                        if (!isCanceled()) {
                            Set<SVNRevision.Number> visibleRevisions = new HashSet<>(results.size());
                            for (RepositoryRevision rev : results) {
                                visibleRevisions.add(rev.getLog().getRevision());
                            }
                            
                            List<RepositoryRevision> toAdd = new ArrayList<>(newResults.size());
                            for (RepositoryRevision rev : filter(newResults)) {
                                if (!visibleRevisions.contains(rev.getLog().getRevision())) {
                                    toAdd.add(rev);
                                }
                            }
                            results.addAll(toAdd);
                            if (count == 0) {
                                showingResults = -1;
                            } else {
                                showingResults = count;
                            }
                            if (showingResults > newResults.size()) {
                                showingResults = -1;
                            }
                            logEntries = createLogEntries(results);
                            if (diffView != null) {
                                diffView.refreshResults(results);
                            }
                            if (summaryView != null) {
                                summaryView.entriesChanged(logEntries);
                            }
                        }
                    }
                });
            }
        }
    }

    List<SvnLogEntry> createLogEntries(List<RepositoryRevision> results) {
        List<SvnLogEntry> ret = new LinkedList<>();
        for (RepositoryRevision repositoryRevision : results) {
            ret.add(new SummaryView.SvnLogEntry(repositoryRevision, this));
        }
        return ret;
    }
}
