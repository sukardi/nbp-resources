/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2008-2010 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2008-2009 Sun Microsystems, Inc.
 */

package org.netbeans.modules.bugtracking.ui.search;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.netbeans.modules.bugtracking.APIAccessor;
import org.netbeans.modules.bugtracking.BugtrackingOwnerSupport;
import static org.netbeans.modules.bugtracking.BugtrackingOwnerSupport.ContextType.MAIN_PROJECT_ONLY;
import org.netbeans.modules.bugtracking.IssueImpl;
import org.netbeans.modules.bugtracking.RepositoryImpl;
import org.netbeans.modules.bugtracking.api.Issue;
import org.netbeans.modules.bugtracking.api.IssueQuickSearch.RepositoryFilter;
import org.netbeans.modules.bugtracking.api.Repository;
import org.netbeans.modules.bugtracking.util.BugtrackingUtil;
import org.netbeans.modules.bugtracking.ui.repository.RepositoryComboSupport;
import org.openide.filesystems.FileObject;

/**
 *
 * @author Tomas Stupka, Jan Stola
 */
public class QuickSearchPanel extends javax.swing.JPanel implements ItemListener, ChangeListener {
    private final QuickSearchComboBar qs;
    private ChangeListener listener;
    private final FileObject referenceFile;
    private final JButton newButton;
    
    public QuickSearchPanel(FileObject referenceFile, RepositoryFilter filter) {
        initComponents();
        
        newButton = createNewRepoButton();
        ((GroupLayout) getLayout()).replace(jLabel1, newButton);
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onNewRepo();
            }
        });
        this.referenceFile = referenceFile;
        
        qs = new QuickSearchComboBar(this);
        issuePanel.add(qs, BorderLayout.NORTH);
        issueLabel.setLabelFor(qs);
        
        if (referenceFile != null) {
            RepositoryComboSupport.setup(this, repositoryComboBox, filter, referenceFile);
        } else {
            RepositoryComboSupport.setup(this, repositoryComboBox, filter, false);
        }
        repositoryComboBox.addItemListener(this);
    }

    public Issue getIssue() {
        Issue issue = qs.getIssue();
        Repository selectedRepository = getSelectedRepository();
        if(issue != null && selectedRepository != null) {
            if(referenceFile != null) {
                // Some issue was picked by the user and we have a file in relation 
                // to which it was done (e.g. from a commit hook). Lets save the 
                // assotiation between the file and the repository as it
                // can be used next time to preselect a repository ...
                BugtrackingOwnerSupport.getInstance().setFirmAssociations(new FileObject[] { referenceFile }, APIAccessor.IMPL.getImpl(selectedRepository));
            } else {
                // Some issue was picked but we have no file context.
                // Still, some nodes (eventually leading to a file) might be currently 
                // selected in the IDE and indicationg on what the user was currently 
                // working on. Lets save at least that weaker assiociation ...
                 BugtrackingOwnerSupport.getInstance().setLooseAssociation(MAIN_PROJECT_ONLY, APIAccessor.IMPL.getImpl(selectedRepository));
            }
        }
        return issue;
    }
    
    public void setIssue(IssueImpl impl) {
        qs.setIssue(impl);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        repositoryLabel = new javax.swing.JLabel();
        issueLabel = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        issuePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        repositoryLabel.setLabelFor(repositoryComboBox);
        org.openide.awt.Mnemonics.setLocalizedText(repositoryLabel, org.openide.util.NbBundle.getMessage(QuickSearchPanel.class, "QuickSearchPanel.repositoryLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(issueLabel, org.openide.util.NbBundle.getMessage(QuickSearchPanel.class, "QuickSearchPanel.issueLabel.text")); // NOI18N

        jLabel2.setForeground(javax.swing.UIManager.getDefaults().getColor("Label.disabledForeground"));
        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(QuickSearchPanel.class, "QuickSearchPanel.jLabel2.text")); // NOI18N

        issuePanel.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2)
            .addComponent(issuePanel, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(issuePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2))
        );

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(QuickSearchPanel.class, "QuickSearchPanel.jLabel1.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(repositoryLabel)
                    .addComponent(issueLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(repositoryComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addContainerGap())
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(repositoryLabel)
                    .addComponent(repositoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(issueLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        repositoryComboBox.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(QuickSearchPanel.class, "QuickSearchPanel.repositoryComboBox.AccessibleContext.accessibleDescription")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel issueLabel;
    private javax.swing.JPanel issuePanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    final javax.swing.JComboBox repositoryComboBox = new javax.swing.JComboBox();
    private javax.swing.JLabel repositoryLabel;
    // End of variables declaration//GEN-END:variables

    private void onNewRepo() {
        RepositoryImpl repoImpl = BugtrackingUtil.createRepository();
        if(repoImpl == null) {
            return;
        }
        Repository repo = repoImpl.getRepository();
        repositoryComboBox.addItem(repo);
        repositoryComboBox.setSelectedItem(repo);
    }
    
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (repositoryComboBox.isEnabled()) {
            enableFields();
        }
        if(e.getStateChange() == ItemEvent.SELECTED) {
            Object item = e.getItem();
            if(item instanceof Repository) {
                Repository repo = (Repository) item;
                qs.setRepository(repo);
            }
        }
    }

    @Override
    public void addNotify() {
        qs.addChangeListener(this);
        qs.addChangeListener(listener);
        super.addNotify();
    }

    @Override
    public void removeNotify() {
        qs.removeChangeListener(this);
        qs.removeChangeListener(listener);
        super.removeNotify();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        enableFields();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        enableFields(enabled);

        repositoryLabel.setEnabled(enabled);
        repositoryComboBox.setEnabled(enabled);
        newButton.setEnabled(enabled);
    }

    /**
     * Register for notifications about changes in the issue combo bar
     * @param listener 
     */
    public void setChangeListener(ChangeListener listener) {
        this.listener  = listener;
        qs.addChangeListener(listener);
    }

    private void enableFields() {
        enableFields(true);
    }

    public void enableFields(boolean enable) {
        boolean repoSelected = repositoryComboBox.getSelectedItem() instanceof Repository;

        issueLabel.setEnabled(repoSelected && enable);
        qs.enableFields(repoSelected && enable);
    }

    public Repository getSelectedRepository() {
        Object obj = repositoryComboBox.getSelectedItem();
        return obj instanceof Repository ? (Repository) obj : null;
    }

    public void setRepository(Repository repository) {
        repositoryComboBox.setSelectedItem(repository);
    }

    private JButton createNewRepoButton() {
        class DoubleWidthButton extends JButton {
            @Override
            public Dimension getPreferredSize() {
                Dimension defPrefSize = super.getPreferredSize();
                return new Dimension((int) (1.8f * defPrefSize.width),
                                     defPrefSize.height);
            }
            @Override
            public Dimension getMinimumSize() {
                return getPreferredSize();
            }
        }
        JButton bt = new DoubleWidthButton();
        org.openide.awt.Mnemonics.setLocalizedText(bt, org.openide.util.NbBundle.getMessage(QuickSearchPanel.class, "QuickSearchPanel.newButton.text")); // NOI18N
        return bt;
    }
}
