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

package org.netbeans.modules.debugger.jpda.expr.formatters;

import java.util.LinkedHashMap;
import java.util.Map;
import org.netbeans.api.debugger.Properties;
import org.netbeans.spi.debugger.DebuggerServiceRegistration;
import org.openide.util.NbBundle;

/**
 *
 * @author Martin Entlicher
 */
public final class VariablesFormatter implements Cloneable {

    private String name;
    private boolean enabled = true;
    private String[] classTypes = new String[] {};
    private boolean includeSubTypes = true;
    private String valueFormatCode = "";    // NOI18N
    private String childrenFormatCode = ""; // NOI18N
    private Map<String, String> childrenVariables = new LinkedHashMap<String, String>();
    private boolean useChildrenVariables = false;
    private String childrenExpandTestCode = ""; // NOI18N
    private boolean isDefault = false;

    public VariablesFormatter(String name) {
        this.name = name;
    }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the value of enabled
     *
     * @return the value of enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Set the value of enabled
     *
     * @param enabled new value of enabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Get the value of classTypes
     *
     * @return the value of classTypes
     */
    public String[] getClassTypes() {
        return classTypes;
    }

    /**
     * Get the value of classTypes
     *
     * @return the value of classTypes
     */
    public String getClassTypesCommaSeparated() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < classTypes.length; i++) {
            if (i > 0) sb.append(", ");
            sb.append(classTypes[i]);
        }
        return sb.toString();
    }

    /**
     * Set the value of classTypes
     *
     * @param classTypes new value of classTypes
     */
    public void setClassTypes(String[] classTypes) {
        this.classTypes = classTypes;
    }

    /**
     * Set the value of classTypes
     *
     * @param classTypes new value of classTypes
     */
    public void setClassTypes(String classTypesCommaSeparated) {
        this.classTypes = classTypesCommaSeparated.split("[, ]+");
    }

    /**
     * Get the value of includeSubtypes
     *
     * @return the value of includeSubtypes
     */
    public boolean isIncludeSubTypes() {
        return includeSubTypes;
    }

    /**
     * Set the value of includeSubtypes
     *
     * @param includeSubtypes new value of includeSubtypes
     */
    public void setIncludeSubTypes(boolean includeSubTypes) {
        this.includeSubTypes = includeSubTypes;
    }

    /**
     * Get the value of valueFormatCode
     *
     * @return the value of valueFormatCode
     */
    public String getValueFormatCode() {
        return valueFormatCode;
    }

    /**
     * Set the value of valueFormatCode
     *
     * @param valueFormatCode new value of valueFormatCode
     */
    public void setValueFormatCode(String valueFormatCode) {
        this.valueFormatCode = valueFormatCode;
    }

    /**
     * Get the value of childrenFormatCode
     *
     * @return the value of childrenFormatCode
     */
    public String getChildrenFormatCode() {
        return childrenFormatCode;
    }

    /**
     * Set the value of childrenFormatCode
     *
     * @param childrenFormatCode new value of childrenFormatCode
     */
    public void setChildrenFormatCode(String childrenFormatCode) {
        this.childrenFormatCode = childrenFormatCode;
    }

    /**
     * Get the value of childrenVariables
     *
     * @return the value of childrenVariables
     */
    public Map<String, String> getChildrenVariables() {
        return childrenVariables;
    }

    /**
     * Set the value of childrenVariables
     *
     * @param childrenVariables new value of childrenVariables
     */
    public void setChildrenVariables(Map<String, String> childrenVariables) {
        this.childrenVariables = childrenVariables;
    }

    /**
     * Add a children variable
     * @param name name of the variable
     * @param value value of the variable
     */
    public void addChildrenVariable(String name, String value) {
        this.childrenVariables.put(name, value);
    }

    /**
     * Get the value of useChildrenVariables
     *
     * @return the value of useChildrenVariables
     */
    public boolean isUseChildrenVariables() {
        return useChildrenVariables;
    }

    /**
     * Set the value of useChildrenVariables
     *
     * @param useChildrenVariables new value of useChildrenVariables
     */
    public void setUseChildrenVariables(boolean useChildrenVariables) {
        this.useChildrenVariables = useChildrenVariables;
    }

    /**
     * Get the value of childrenExpandTestCode
     *
     * @return the value of childrenExpandTestCode
     */
    public String getChildrenExpandTestCode() {
        return childrenExpandTestCode;
    }

    /**
     * Set the value of childrenExpandTestCode
     *
     * @param childrenExpandTestCode new value of childrenExpandTestCode
     */
    public void setChildrenExpandTestCode(String childrenExpandTestCode) {
        this.childrenExpandTestCode = childrenExpandTestCode;
    }

    /**
     * Test if this formatter is the default one.
     * @return true if this formatter is default
     */
    public boolean isDefault() {
        return isDefault;
    }

    @Override
    public VariablesFormatter clone() {
        VariablesFormatter f = new VariablesFormatter(name);
        f.childrenExpandTestCode = childrenExpandTestCode;
        f.childrenFormatCode = childrenFormatCode;
        f.childrenVariables = new LinkedHashMap<String, String>(childrenVariables);
        f.classTypes = classTypes.clone();
        f.enabled = enabled;
        f.includeSubTypes = includeSubTypes;
        f.useChildrenVariables = useChildrenVariables;
        f.valueFormatCode = valueFormatCode;
        return f;
    }



    public static VariablesFormatter[] loadFormatters() {
        Properties p = Properties.getDefault().getProperties("debugger.options.JPDA");
        VariablesFormatter[] formatters = (VariablesFormatter[]) p.getArray("VariableFormatters", null);
        VariablesFormatter[] defaultFormatters = createDefaultFormatters();
        if (formatters == null) {
            formatters = defaultFormatters;
        } else {
            Map<String, VariablesFormatter> fm = new LinkedHashMap<String, VariablesFormatter>(defaultFormatters.length);
            for (VariablesFormatter vf : defaultFormatters) {
                fm.put(vf.getName(), vf);
            }
            for (int i = 0; i < formatters.length; i++) {
                if (formatters[i].isDefault && fm.containsKey(formatters[i].getName())) {
                    VariablesFormatter ovf = formatters[i];
                    formatters[i] = fm.remove(formatters[i].getName());
                    formatters[i].setEnabled(ovf.isEnabled());
                }
            }
            if (!fm.isEmpty()) { // We have new default formatters
                VariablesFormatter[] newFormatters = new VariablesFormatter[formatters.length + fm.size()];
                System.arraycopy(formatters, 0, newFormatters, 0, formatters.length);
                System.arraycopy(fm.values().toArray(), 0, newFormatters, formatters.length, fm.size());
                formatters = newFormatters;
            }
        }
        return formatters;
    }


    @NbBundle.Messages({"# VariablesFormatter",
                        "MSG_CharSequenceFormatter=Default CharSequence Formatter",
                        "MSG_CollectionFormatter=Default Collection Formatter",
                        "MSG_MapFormatter=Default Map Formatter",
                        "MSG_MapEntryFormatter=Default Map.Entry Formatter",
                        "MSG_EnumFormatter=Default Enum Formatter",
                        })
    private static VariablesFormatter[] createDefaultFormatters() {
        VariablesFormatter charSequence = new VariablesFormatter(Bundle.MSG_CharSequenceFormatter());
        charSequence.setClassTypes("java.lang.CharSequence");
        charSequence.setIncludeSubTypes(true);
        charSequence.setChildrenFormatCode("");
        charSequence.setChildrenExpandTestCode("false");
        charSequence.setValueFormatCode("toString()");
        charSequence.isDefault = true;

        VariablesFormatter collection = new VariablesFormatter(Bundle.MSG_CollectionFormatter());
        collection.setClassTypes("java.util.Collection");
        collection.setIncludeSubTypes(true);
        collection.setChildrenFormatCode("toArray()");
        collection.setValueFormatCode("\"size = \"+size()");
        collection.isDefault = true;

        VariablesFormatter map = new VariablesFormatter(Bundle.MSG_MapFormatter());
        map.setClassTypes("java.util.Map");
        map.setIncludeSubTypes(true);
        map.setChildrenFormatCode("entrySet()");
        map.setValueFormatCode("\"size = \"+size()");
        map.isDefault = true;

        VariablesFormatter mapEntry = new VariablesFormatter(Bundle.MSG_MapEntryFormatter());
        mapEntry.setClassTypes("java.util.Map$Entry");
        mapEntry.setIncludeSubTypes(true);
        mapEntry.setUseChildrenVariables(true);
        Map childrenMap = new LinkedHashMap();
        childrenMap.put("key", "getKey()");
        childrenMap.put("value", "getValue()");
        mapEntry.setChildrenVariables(childrenMap);
        mapEntry.setValueFormatCode("getKey()+\" => \"+getValue()");
        mapEntry.isDefault = true;

        VariablesFormatter enumFormatter = new VariablesFormatter(Bundle.MSG_EnumFormatter());
        enumFormatter.setClassTypes("java.lang.Enum");
        enumFormatter.setIncludeSubTypes(true);
        enumFormatter.setValueFormatCode("toString()");
        enumFormatter.isDefault = true;

        return new VariablesFormatter[] { charSequence, collection, map, mapEntry, enumFormatter };
    }



    @DebuggerServiceRegistration(types=Properties.Reader.class)
    public static class ReaderWriter implements Properties.Reader {
        
        private static final String OLD_VariablesFormatter_CLASS_NAME =
                "org.netbeans.modules.debugger.jpda.ui.VariablesFormatter";     // NOI18N

        public String[] getSupportedClassNames() {
            return new String[] { OLD_VariablesFormatter_CLASS_NAME,
                                  VariablesFormatter.class.getName() };
        }

        public Object read(String className, Properties properties) {
            String name = properties.getString("name", "<EMPTY>");
            VariablesFormatter f = new VariablesFormatter(name);
            f.setEnabled(properties.getBoolean("enabled", f.isEnabled()));
            f.setClassTypes(properties.getString("classTypes", f.getClassTypesCommaSeparated()));
            f.setIncludeSubTypes(properties.getBoolean("includeSubTypes", f.isIncludeSubTypes()));
            f.setValueFormatCode(properties.getString("valueFormatCode", f.getValueFormatCode()));
            f.setChildrenFormatCode(properties.getString("childrenFormatCode", f.getChildrenFormatCode()));
            f.setChildrenVariables(properties.getMap("childrenVariables", f.getChildrenVariables()));
            f.setUseChildrenVariables(properties.getBoolean("useChildrenVariables", f.isUseChildrenVariables()));
            f.setChildrenExpandTestCode(properties.getString("childrenExpandTestCode", f.getChildrenExpandTestCode()));
            f.isDefault = properties.getBoolean("isDefault", f.isDefault);
            return f;
        }

        public void write(Object object, Properties properties) {
            VariablesFormatter f = (VariablesFormatter) object;
            properties.setString("name", f.getName());
            properties.setBoolean("enabled", f.isEnabled());
            properties.setString("classTypes", f.getClassTypesCommaSeparated());
            properties.setBoolean("includeSubTypes", f.isIncludeSubTypes());
            properties.setString("valueFormatCode", f.getValueFormatCode());
            properties.setString("childrenFormatCode", f.getChildrenFormatCode());
            properties.setMap("childrenVariables", f.getChildrenVariables());
            properties.setBoolean("useChildrenVariables", f.isUseChildrenVariables());
            properties.setString("childrenExpandTestCode", f.getChildrenExpandTestCode());
            properties.setBoolean("isDefault", f.isDefault);
        }
        
    }
}
