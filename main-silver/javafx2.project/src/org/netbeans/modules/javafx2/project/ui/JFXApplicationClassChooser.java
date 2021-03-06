/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2011 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2011 Sun Microsystems, Inc.
 */

/*
 * JFXApplicationClassChooser.java
 *
 * Created on 18.8.2011, 14:26:27
 */
package org.netbeans.modules.javafx2.project.ui;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.netbeans.api.annotations.common.NonNull;
import org.netbeans.api.java.classpath.ClassPath;
import org.netbeans.api.java.source.ClasspathInfo;
import org.netbeans.api.java.source.CompilationController;
import org.netbeans.api.java.source.JavaSource;
import org.netbeans.api.java.source.ScanUtils;
import org.netbeans.api.java.source.SourceUtils;
import org.netbeans.api.java.source.Task;
import org.netbeans.api.project.Project;
import org.netbeans.modules.java.api.common.project.ProjectProperties;
import org.netbeans.modules.javafx2.project.JFXProjectProperties;
import org.netbeans.modules.javafx2.project.JFXProjectUtils;
import org.netbeans.spi.project.support.ant.PropertyEvaluator;
import org.openide.awt.MouseUtils;
import org.openide.filesystems.FileObject;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;

/**
 *
 * @author Petr Somol
 * @author Milan Kubec
 * @author Jiri Rechtacek
 */
public class JFXApplicationClassChooser extends javax.swing.JPanel {

    private static final RequestProcessor RP = new RequestProcessor(JFXApplicationClassChooser.class);
    private static final Logger LOG = Logger.getLogger(JFXApplicationClassChooser.class.getName());

    /*test*/ static final String LOG_INIT = "INIT";  //NOI18N
    /*test*/ static final String LOG_MAIN_CLASSES = "MAIN-CLASSES: {0} SCAN: {1}";  //NOI18N

    private final PropertyEvaluator evaluator;
    private final Project project;
    private ChangeListener changeListener;
    private final boolean isFXinSwing;

    /** Creates new form JFXApplicationClassChooser */
    public JFXApplicationClassChooser(final @NonNull Project p, final @NonNull PropertyEvaluator pe) {
        this.evaluator = pe;
        this.project = p;
        this.isFXinSwing = JFXProjectUtils.isFXinSwingProject(p);
        initComponents();
        if(!SourceUtils.isScanInProgress()) labelScanning.setVisible(false);
        listAppClasses.setCellRenderer(new AppClassRenderer());
        initClassesView();
        initClassesModel();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        labelAppClasses = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listAppClasses = new javax.swing.JList();
        labelScanning = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(380, 300));
        setLayout(new java.awt.GridBagLayout());

        labelAppClasses.setLabelFor(listAppClasses);
        org.openide.awt.Mnemonics.setLocalizedText(labelAppClasses, org.openide.util.NbBundle.getMessage(JFXApplicationClassChooser.class, "LBL_JFXApplicationClassChooser.labelAppClasses.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 2, 10);
        add(labelAppClasses, gridBagConstraints);
        labelAppClasses.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(JFXApplicationClassChooser.class, "AN_JFXApplicationClassChooser.labelAppClasses.text")); // NOI18N
        labelAppClasses.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(JFXApplicationClassChooser.class, "AD_JFXApplicationClassChooser.labelAppClasses.text")); // NOI18N

        jScrollPane1.setViewportView(listAppClasses);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        add(jScrollPane1, gridBagConstraints);

        labelScanning.setFont(labelScanning.getFont().deriveFont((labelScanning.getFont().getStyle() | java.awt.Font.ITALIC), labelScanning.getFont().getSize()+1));
        labelScanning.setText(org.openide.util.NbBundle.getMessage(JFXApplicationClassChooser.class, "LBL_ChooseMainClass_SCANNING_MESSAGE")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 10, 0, 0);
        add(labelScanning, gridBagConstraints);
        labelScanning.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(JFXApplicationClassChooser.class, "LBL_ChooseMainClass_SCANNING_MESSAGE")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelAppClasses;
    private javax.swing.JLabel labelScanning;
    private javax.swing.JList listAppClasses;
    // End of variables declaration//GEN-END:variables

    private static final class AppClassRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent (JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            String displayName;
            if (value instanceof String) {
                displayName = (String) value;
            } else {
                displayName = value.toString ();
            }
            return super.getListCellRendererComponent (list, displayName, index, isSelected, cellHasFocus);
        }
    }

    public void addChangeListener (ChangeListener l) {
        changeListener = l;
    }
    
    public void removeChangeListener (ChangeListener l) {
        changeListener = null;
    }

    private Object[] getWarmupList () {        
          return new Object[] {NbBundle.getMessage (JFXApplicationClassChooser.class, "Item_ChooseMainClass_WARMUP_MESSAGE")}; // NOI18N
    }

    private void initClassesView () {
        listAppClasses.setSelectionMode (ListSelectionModel.SINGLE_SELECTION);
        listAppClasses.setListData (getWarmupList ());
        listAppClasses.addListSelectionListener (new ListSelectionListener () {
            @Override
            public void valueChanged (ListSelectionEvent evt) {
                if (changeListener != null) {
                    changeListener.stateChanged (new ChangeEvent (evt));
                }
            }
        });
        // support for double click to finish dialog with selected class
        listAppClasses.addMouseListener (new MouseListener () {
            @Override
            public void mouseClicked (MouseEvent e) {
                if (MouseUtils.isDoubleClick (e)) {
                    if (getSelectedClass () != null) {
                        if (changeListener != null) {
                            changeListener.stateChanged (new ChangeEvent (e));
                        }
                    }
                }
            }
            @Override
            public void mousePressed (MouseEvent e) {}
            @Override
            public void mouseReleased (MouseEvent e) {}
            @Override
            public void mouseEntered (MouseEvent e) {}
            @Override
            public void mouseExited (MouseEvent e) {}
        });
    }
    
    private void initClassesModel() {
        
        final Map<FileObject,List<ClassPath>> classpathMap = JFXProjectUtils.getClassPathMap(project);
        if (classpathMap.isEmpty()) {
            //No sources at all.
            return;
        }
        RP.post(new Runnable() {
            @Override
            public void run() {
                LOG.log(Level.FINE, LOG_INIT);
                final List<ClassPath> cps = classpathMap.values().iterator().next();
                final ClasspathInfo cpInfo = ClasspathInfo.create(cps.get(0), cps.get(1), cps.get(2));
                final JavaSource js = JavaSource.create(cpInfo);
                ScanUtils.postUserActionTask(js, new Task<CompilationController>() {
                    @Override
                    public void run(CompilationController parameter) throws Exception {
                        final boolean barrier = SourceUtils.isScanInProgress();
                        final Set<String> appClassNames = isFXinSwing ?
                            JFXProjectUtils.getMainClassNames(project) :
                            JFXProjectUtils.getAppClassNames(classpathMap, "javafx.application.Application"); //NOI18N
                        LOG.log(
                            Level.FINE,
                            LOG_MAIN_CLASSES,
                            new Object[]{
                                appClassNames,
                                barrier
                            });
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                if (!barrier) {
                                    labelScanning.setVisible(false);
                                }
                                listAppClasses.setListData(appClassNames.toArray());
                                String appClassName = evaluator.getProperty(isFXinSwing ? ProjectProperties.MAIN_CLASS : JFXProjectProperties.MAIN_CLASS);
                                if (appClassName != null && appClassNames.contains(appClassName)) {
                                    listAppClasses.setSelectedValue(appClassName, true);
                                }
                            }
                        });
                        if (barrier) {
                            ScanUtils.signalIncompleteData(parameter, null);
                        }

                    }
                });
            }
        });
    }

    /** Returns the selected class.
     *
     * @return name of class or null if no class is selected
     */    
    public String getSelectedClass () {
        Object sel = listAppClasses.getSelectedValue();
        if(sel == null) {
            return null;
        }
        if(sel instanceof String) {
            return (String)sel;
        }
        return null;
    }

}
