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
package org.netbeans.modules.web.jsf.editor.facelets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import org.netbeans.modules.web.jsfapi.api.Attribute;

/**
 *
 * @author mfukala@netbeans.org
 */
public final class TagImpl extends GenericTag {

    private String name;
    private String description;
    private Map<String, Attribute> attrs;

    public TagImpl(String name, String description, Map<String, Attribute> attrs) {
        this.name = name;
        this.description = description;
        this.attrs = attrs;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean hasNonGenenericAttributes() {
        return !attrs.isEmpty();
    }

    @Override
    public Collection<Attribute> getAttributes() {
        //merge with default attributes
        Collection<Attribute> all = new ArrayList<>(super.getAttributes());
        all.addAll(attrs.values());
        return all;
    }

    @Override
    public Attribute getAttribute(String name) {
        Attribute superA = super.getAttribute(name);
        return superA != null ? superA : attrs.get(name);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Tag[name=").append(getName()).append(", attributes={"); //NOI18N
        for (Attribute attr : getAttributes()) {
            sb.append(attr.toString()).append(",");
        }
        sb.append("}]");
        return sb.toString();
    }
}
