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
 * Portions Copyrighted 2010 Sun Microsystems, Inc.
 */
package org.netbeans.modules.php.editor.elements;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.netbeans.modules.parsing.spi.indexing.support.IndexResult;
import org.netbeans.modules.php.editor.api.ElementQuery;
import org.netbeans.modules.php.editor.api.NameKind;
import org.netbeans.modules.php.editor.api.PhpElementKind;
import org.netbeans.modules.php.editor.api.elements.TypeConstantElement;
import org.netbeans.modules.php.editor.api.elements.TypeElement;
import org.netbeans.modules.php.editor.index.PHPIndexer;
import org.netbeans.modules.php.editor.index.Signature;
import org.netbeans.modules.php.editor.model.impl.VariousUtils;
import org.netbeans.modules.php.editor.model.nodes.ClassConstantDeclarationInfo;
import org.netbeans.modules.php.editor.parser.astnodes.ConstantDeclaration;
import org.openide.util.Parameters;

/**
 * @author Radek Matous
 */
public final class TypeConstantElementImpl extends PhpElementImpl implements TypeConstantElement {

    public static final String IDX_FIELD = PHPIndexer.FIELD_CLASS_CONST;
    private final TypeElement enclosingType;
    private final String value;
    private final boolean isMagic;

    private TypeConstantElementImpl(
            final TypeElement enclosingType,
            final String constantName,
            final String value,
            final int offset,
            final String fileUrl,
            final ElementQuery elementQuery,
            final boolean isDeprecated) {
        this(enclosingType, constantName, value, offset, fileUrl, elementQuery, isDeprecated, false);
    }

    private TypeConstantElementImpl(
            final TypeElement enclosingType,
            final String constantName,
            final String value,
            final int offset,
            final String fileUrl,
            final ElementQuery elementQuery,
            final boolean isDeprecated,
            final boolean isMagic) {
        super(constantName, enclosingType.getName(), fileUrl, offset, elementQuery, isDeprecated);
        this.enclosingType = enclosingType;
        this.value = value;
        this.isMagic = isMagic;
    }

    public static Set<TypeConstantElement> getMagicConstants(TypeElement type) {
        Set<TypeConstantElement> retval = new HashSet<>();
        retval.add(createMagicConstant(type, "class")); //NOI18N
        return retval;
    }

    private static TypeConstantElement createMagicConstant(TypeElement type, String constantName) {
        TypeConstantElement retval = new TypeConstantElementImpl(
                type,
                constantName,
                type.getFullyQualifiedName().toString(),
                0,
                type.getFilenameUrl(),
                null,
                type.isDeprecated(),
                true);
        return retval;
    }

    public static Set<TypeConstantElement> fromSignature(final TypeElement type,
            final IndexQueryImpl indexScopeQuery, final IndexResult indexResult) {
        return fromSignature(type, NameKind.empty(), indexScopeQuery, indexResult);
    }

    public static Set<TypeConstantElement> fromSignature(final TypeElement type, final NameKind query,
            final IndexQueryImpl indexScopeQuery, final IndexResult indexResult) {
        final String[] values = indexResult.getValues(IDX_FIELD);
        final Set<TypeConstantElement> retval = values.length > 0
                ? new HashSet<TypeConstantElement>() : Collections.<TypeConstantElement>emptySet();
        for (final String val : values) {
            final TypeConstantElement constant = fromSignature(type, query, indexScopeQuery, Signature.get(val));
            if (constant != null) {
                retval.add(constant);
            }
        }
        return retval;
    }

    private static TypeConstantElement fromSignature(final TypeElement type, final NameKind query,
            final IndexQueryImpl indexScopeQuery, final Signature signature) {
        final ConstantSignatureParser signParser = new ConstantSignatureParser(signature);
        TypeConstantElement retval = null;
        if (matchesQuery(query, signParser)) {
            retval = new TypeConstantElementImpl(
                    type,
                    signParser.getConstantName(),
                    signParser.getValue(),
                    signParser.getOffset(),
                    signParser.getFileUrl(),
                    indexScopeQuery,
                    signParser.isDeprecated());
        }
        return retval;
    }

    public static Set<TypeConstantElement> fromNode(final TypeElement type, ConstantDeclaration node, final ElementQuery.File fileQuery) {
        Parameters.notNull("type", type);
        Parameters.notNull("node", node);
        Parameters.notNull("fileQuery", fileQuery);
        final List<? extends ClassConstantDeclarationInfo> consts = ClassConstantDeclarationInfo.create(node);
        final Set<TypeConstantElement> retval = new HashSet<>();
        for (ClassConstantDeclarationInfo info : consts) {
            retval.add(new TypeConstantElementImpl(
                    type, info.getName(), info.getValue(), info.getRange().getStart(),
                    fileQuery.getURL().toExternalForm(), fileQuery,
                    VariousUtils.isDeprecatedFromPHPDoc(fileQuery.getResult().getProgram(), node)));
        }
        return retval;
    }

    private static boolean matchesQuery(final NameKind query, ConstantSignatureParser signParser) {
        Parameters.notNull("query", query); //NOI18N
        return (query instanceof NameKind.Empty)
                || query.matchesName(TypeConstantElement.KIND, signParser.getConstantName());
    }

    @Override
    public String getSignature() {
        StringBuilder sb = new StringBuilder();
        sb.append(getName().toLowerCase()).append(Separator.SEMICOLON);
        sb.append(getName()).append(Separator.SEMICOLON);
        sb.append(getOffset()).append(Separator.SEMICOLON);
        sb.append(getValue()).append(Separator.SEMICOLON);
        sb.append(isDeprecated() ? 1 : 0).append(Separator.SEMICOLON);
        sb.append(getFilenameUrl()).append(Separator.SEMICOLON);
        checkSignature(sb);
        return sb.toString();
    }

    @Override
    public PhpElementKind getPhpElementKind() {
        return TypeConstantElement.KIND;
    }

    @Override
    public TypeElement getType() {
        return enclosingType;
    }

    private void checkSignature(StringBuilder sb) {
        boolean checkEnabled = false;
        assert checkEnabled = true;
        if (checkEnabled) {
            String retval = sb.toString();
            ConstantSignatureParser parser = new ConstantSignatureParser(Signature.get(retval));
            assert getName().equals(parser.getConstantName());
            assert getOffset() == parser.getOffset();
        }
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean isStatic() {
        return getPhpModifiers().isStatic();
    }

    @Override
    public boolean isPublic() {
        return getPhpModifiers().isPublic();
    }

    @Override
    public boolean isProtected() {
        return getPhpModifiers().isProtected();
    }

    @Override
    public boolean isPrivate() {
        return getPhpModifiers().isPrivate();
    }

    @Override
    public boolean isFinal() {
        return getPhpModifiers().isFinal();
    }

    @Override
    public boolean isAbstract() {
        return getPhpModifiers().isAbstract();
    }

    @Override
    public boolean isMagic() {
        return isMagic;
    }

    private static class ConstantSignatureParser {

        private final Signature signature;

        ConstantSignatureParser(Signature signature) {
            this.signature = signature;
        }

        String getConstantName() {
            return signature.string(1);
        }

        int getOffset() {
            return signature.integer(2);
        }

        String getValue() {
            return signature.string(3);
        }

        boolean isDeprecated() {
            return signature.integer(4) == 1;
        }

        String getFileUrl() {
            return signature.string(5);
        }
    }
}
