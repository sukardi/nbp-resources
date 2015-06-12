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
package org.netbeans.modules.css.editor.module.main;

import java.util.Arrays;
import java.util.Collection;
import org.netbeans.modules.css.editor.module.spi.CssEditorModule;
import org.netbeans.modules.css.editor.module.spi.EditorFeatureContext;
import org.netbeans.modules.css.lib.api.CssModule;
import org.netbeans.modules.css.lib.api.properties.PropertyDefinition;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;

/**
 * The colors module functionality is partially implemented in the DefaultCssModule
 * from historical reasons. Newly added features are implemented here.
 *
 * @author mfukala@netbeans.org
 */
@ServiceProvider(service = CssEditorModule.class)
public class BasicUserInterfaceModule extends ExtCssEditorModule implements CssModule {

    //NOI18N>>>
    private static final Collection<String> PSEUDO_CLASSES = Arrays.asList(new String[]{
                "default",
                "valid",
                "invalid",
                "in-range",
                "out-of-range",
                "required",
                "optional",
                "read-only",
                "read-write" //NOI18N
            });
    private static final Collection<String> PSEUDO_ELEMENTS = Arrays.asList(new String[]{
                "selection",
                "value",
                "choices",
                "repeat-item",
                "repeat-index"}); //NOI18N
    private static final String PROPERTIES_DEFINITION_PATH =
            "org/netbeans/modules/css/editor/module/main/properties/basic_user_interface"; //NOI18N
    private static Collection<PropertyDefinition> propertyDescriptors;

    @Override
    public Collection<String> getPseudoClasses(EditorFeatureContext context) {
        return PSEUDO_CLASSES;
    }

    @Override
    public Collection<String> getPseudoElements(EditorFeatureContext context) {
        return PSEUDO_ELEMENTS;
    }

    @Override
    public String getName() {
        return "basic_user_interface"; //NOI18N
    }

    @Override
    public String getDisplayName() {
        return NbBundle.getMessage(this.getClass(), Constants.CSS_MODULE_DISPLAYNAME_BUNDLE_KEY_PREFIX + getName());
    }

    @Override
    public String getSpecificationURL() {
        return "http://www.w3.org/TR/css3-ui";
    }

    @Override
    protected String getPropertyDefinitionsResourcePath() {
        return PROPERTIES_DEFINITION_PATH;
    }

    @Override
    protected CssModule getCssModule() {
        return this;
    }
}
