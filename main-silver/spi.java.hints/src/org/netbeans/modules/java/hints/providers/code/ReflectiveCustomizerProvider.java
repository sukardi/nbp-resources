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
package org.netbeans.modules.java.hints.providers.code;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;
import org.netbeans.spi.java.hints.BooleanOption;
import org.netbeans.spi.java.hints.CustomizerProvider;
import org.netbeans.spi.java.hints.IntegerOption;
import org.openide.util.Exceptions;

/**
 *
 * @author lahvac
 */
public class ReflectiveCustomizerProvider implements CustomizerProvider {
    private final String hintClassName;
    private final String hintId;
    private final List<OptionDescriptor> options;

    public ReflectiveCustomizerProvider(String hintClassName, String hintId, List<OptionDescriptor> options) {
        this.hintClassName = hintClassName;
        this.hintId = hintId;
        this.options = options;
    }

    @Override
    public JComponent getCustomizer(Preferences prefs) {
        return new CustomizerImpl(prefs, hintClassName, hintId, options);
    }

    private static final class CustomizerImpl extends JPanel {
        private int row;
        
        public CustomizerImpl(Preferences prefs, String hintClassName, String hintId, List<OptionDescriptor> options) {
            try {
                setLayout(new GridBagLayout());
                row = 0;
                
                for (OptionDescriptor option : options) {
                    if (option.parameters instanceof IntegerOption) {
                        createIntegerOption(option, prefs);
                    }
                }

                for (OptionDescriptor option : options) {
                    if (option.parameters instanceof BooleanOption) {
                        createBooleanOption(option, prefs);
                    }
                }

                GridBagConstraints constraints = new GridBagConstraints();

                constraints.anchor = GridBagConstraints.NORTHWEST;
                constraints.fill = GridBagConstraints.BOTH;
                constraints.gridheight = 1;
                constraints.gridwidth = GridBagConstraints.REMAINDER;
                constraints.gridx = 0;
                constraints.gridy = row++;
                constraints.weightx = 1;
                constraints.weighty = 1;

                add(new JPanel(), constraints);
            } catch (IllegalArgumentException ex) {
                Exceptions.printStackTrace(ex);
            } catch (SecurityException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        
        private void createIntegerOption(OptionDescriptor option, Preferences prefs) {
            IntegerOption iopt = (IntegerOption)option.parameters;
            JLabel l = new JLabel();
            org.openide.awt.Mnemonics.setLocalizedText(l, option.displayName + ":");
            
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.anchor = GridBagConstraints.WEST;
            constraints.fill = GridBagConstraints.NONE;
            constraints.gridheight = 1;
            constraints.gridwidth = 1;
            constraints.gridx = 0;
            constraints.gridy = row;
            constraints.weightx = 0;
            constraints.weighty = 0;
            constraints.insets = new Insets(0, 0, 0, 8);
            
            add(l, constraints);
            
            JComponent field;
            int val = prefs.getInt(option.preferencesKey, ((Integer)option.defaultValue).intValue());
            if (iopt.step() > 0) {
                val = Math.min(iopt.maxValue(), Math.max(iopt.minValue(), val));
                prefs.putInt(option.preferencesKey, val);
                JSpinner spinner = new JSpinner(
                        new SpinnerNumberModel(val, iopt.minValue(), iopt.maxValue(), iopt.step()));
                spinner.addChangeListener(new ActionListenerImpl(option.preferencesKey, prefs));
                field = spinner;
            } else {
                NumberFormatter nf = new NumberFormatter();
                nf.setValueClass(Integer.class);
                nf.setMaximum(iopt.maxValue());
                nf.setMinimum(iopt.minValue());
                JFormattedTextField formatted = new JFormattedTextField(nf);
                field = formatted;
            }
            if (option.tooltip != null && !option.tooltip.isEmpty()) {
                field.setToolTipText(option.tooltip);
            }
            constraints = new GridBagConstraints();
            constraints.anchor = GridBagConstraints.WEST;
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.gridheight = 1;
            constraints.gridwidth = 1;
            constraints.gridx = 1;
            constraints.gridy = row;
            constraints.weightx = 0;
            constraints.weighty = 0;
            
            add(field, constraints);

            constraints = new GridBagConstraints();
            constraints.anchor = GridBagConstraints.WEST;
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.gridheight = 1;
            constraints.gridwidth = GridBagConstraints.REMAINDER;
            constraints.gridx = 2;
            constraints.gridy = row++;
            constraints.weightx = 1;
            constraints.weighty = 0;
            
            add(new JPanel(), constraints);
        }
        
        private JComponent createBooleanOption(OptionDescriptor option, Preferences prefs)  {
            JCheckBox checkBox = new JCheckBox();

            org.openide.awt.Mnemonics.setLocalizedText(checkBox, option.displayName);
            checkBox.setToolTipText(option.tooltip);
            checkBox.addActionListener(new ActionListenerImpl(option.preferencesKey, prefs));

            checkBox.setSelected(prefs.getBoolean(option.preferencesKey, 
                    Boolean.TRUE == option.defaultValue));
            prefs.putBoolean(option.preferencesKey, checkBox.isSelected());
            GridBagConstraints constraints = new GridBagConstraints();

            constraints.anchor = GridBagConstraints.WEST;
            constraints.fill = GridBagConstraints.NONE;
            constraints.gridheight = 1;
            constraints.gridwidth = 2;
            constraints.gridx = 0;
            constraints.gridy = row++;
            constraints.weightx = 0;
            constraints.weighty = 0;

            add(checkBox, constraints);
            return checkBox;
        }
                
        
        private static final class ActionListenerImpl implements ActionListener, ChangeListener {
            private final String key;
            private final Preferences prefs;

            public ActionListenerImpl(String key, Preferences prefs) {
                this.key = key;
                this.prefs = prefs;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox checkBox = ((JCheckBox)e.getSource());
                prefs.putBoolean(key, checkBox.isSelected());
            }

            @Override
            public void stateChanged(ChangeEvent e) {
                Integer i = (Integer)((JSpinner)e.getSource()).getValue();
                prefs.putInt(key, i);
            }

        }
        
    }

    public static final class OptionDescriptor {
        public final String preferencesKey;
        public final Object defaultValue;
        public final String displayName;
        public final String tooltip;
        /**
         * The original Annotation object, type-specific parameters.
         */
        public final Object parameters;

        public OptionDescriptor(
                String preferencesKey, 
                Object defaultValue, 
                String displayName, String tooltip, 
                Object parameters) {
            this.preferencesKey = preferencesKey;
            this.defaultValue = defaultValue;
            this.displayName = displayName;
            this.tooltip = tooltip;
            this.parameters = parameters;
        }

    }

}
