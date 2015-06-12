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
package org.netbeans.modules.javascript2.editor.sdoc;

import java.util.*;
import org.netbeans.modules.javascript2.editor.doc.JsDocumentationTestBase;
import org.netbeans.modules.javascript2.editor.doc.spi.JsComment;
import org.netbeans.modules.javascript2.editor.doc.spi.JsDocumentationHolder;
import org.netbeans.modules.javascript2.editor.parser.JsParserResult;
import org.netbeans.modules.javascript2.editor.sdoc.elements.SDocDescriptionElement;
import org.netbeans.modules.javascript2.editor.sdoc.elements.SDocElement;
import org.netbeans.modules.javascript2.editor.sdoc.elements.SDocElementType;
import org.netbeans.modules.javascript2.editor.sdoc.elements.SDocIdentifierElement;
import org.netbeans.modules.javascript2.editor.sdoc.elements.SDocSimpleElement;
import org.netbeans.modules.javascript2.editor.sdoc.elements.SDocTypeDescribedElement;
import org.netbeans.modules.javascript2.editor.sdoc.elements.SDocTypeNamedElement;
import org.netbeans.modules.javascript2.editor.sdoc.elements.SDocTypeSimpleElement;
import org.netbeans.modules.parsing.api.ParserManager;
import org.netbeans.modules.parsing.api.ResultIterator;
import org.netbeans.modules.parsing.api.Source;
import org.netbeans.modules.parsing.api.UserTask;
import org.netbeans.modules.parsing.spi.Parser;

/**
 *
 * @author Martin Fousek <marfous@netbeans.org>
 */
public class SDocModelTest extends JsDocumentationTestBase {

    private static DocumentationElementComparator elementComparator = new DocumentationElementComparator();

    public SDocModelTest(String testName) {
        super(testName);
    }

    /**
     * The string should look like: <type>||<key1>=<value1>:<key2>=<value2>;<type>
     */
    private static List<SDocElement> parseExpected(String expected) {
        List<SDocElement> elements = new ArrayList<SDocElement>();
        String[] tags = expected.split("[;]+");
        for (String tag : tags) {
            String[] tmp = tag.split("[|][|]");
            FakeSDocElement element = new FakeSDocElement(SDocElementType.fromString(tmp[0]));
            if (tmp.length > 1) {
                String[] keyValues = tmp[1].split("[:]+");
                for (String keyValue : keyValues) {
                    String[] items = keyValue.split("[=]+");
                    if (items.length == 1) {
                        // in context sensitive cases
                        element.addProperty("desc", items[0]);
                    } else {
                        element.addProperty(items[0], items[1]);
                    }
                }
            }
            elements.add(element);
        }
        return elements;
    }

    private static void checkSDocElements(String expected, List<? extends SDocElement> elements) {
        List<SDocElement> expectedTags = parseExpected(expected);
        assertElementsEquality(expectedTags, elements);

    }

    private void checkJsDocBlock(Source source, final int offset, final String expected) throws Exception {
        ParserManager.parse(Collections.singleton(source), new UserTask() {
            public @Override
            void run(ResultIterator resultIterator) throws Exception {
                Parser.Result result = resultIterator.getParserResult();
                assertTrue(result instanceof JsParserResult);
                JsParserResult parserResult = (JsParserResult) result;

                JsDocumentationHolder documentationHolder = getDocumentationHolder(parserResult, new SDocDocumentationProvider());
                JsComment comment = documentationHolder.getCommentForOffset(offset, documentationHolder.getCommentBlocks());
                checkSDocElements(expected, ((SDocComment) comment).getTags());
            }
        });
    }

    public void testContextSensitiveDescription() throws Exception {
        Source source = getTestSource(getTestFile("testfiles/sdoc/modelTestFile.js"));
        final int caretOffset = getCaretOffset(source, "var ^line1;");
        checkJsDocBlock(source, caretOffset, "description||This should be description.");
    }

    public void testUnknown() throws Exception {
        Source source = getTestSource(getTestFile("testfiles/sdoc/modelTestFile.js"));
        final int caretOffset = getCaretOffset(source, "var ^line2;");
        checkJsDocBlock(source, caretOffset, "@anyCustomTag");
    }

    public void testAlias() throws Exception {
        Source source = getTestSource(getTestFile("testfiles/sdoc/modelTestFile.js"));
        final int caretOffset = getCaretOffset(source, "var ^line3;");
        checkJsDocBlock(source, caretOffset, "@alias||ident=aliasIdent");
    }

    public void testAuthor() throws Exception {
        Source source = getTestSource(getTestFile("testfiles/sdoc/modelTestFile.js"));
        final int caretOffset = getCaretOffset(source, "var ^line4;");
        checkJsDocBlock(source, caretOffset, "@author||desc=Jackie Chan");
    }

    public void testClassDescription() throws Exception {
        Source source = getTestSource(getTestFile("testfiles/sdoc/modelTestFile.js"));
        final int caretOffset = getCaretOffset(source, "var ^line5;");
        checkJsDocBlock(source, caretOffset, "@classDescription||desc=classDescription text");
    }

    public void testConstructor() throws Exception {
        Source source = getTestSource(getTestFile("testfiles/sdoc/modelTestFile.js"));
        final int caretOffset = getCaretOffset(source, "var ^line6;");
        checkJsDocBlock(source, caretOffset, "@constructor");
    }

    public void testDeprecated() throws Exception {
        Source source = getTestSource(getTestFile("testfiles/sdoc/modelTestFile.js"));
        final int caretOffset = getCaretOffset(source, "var ^line7;");
        checkJsDocBlock(source, caretOffset, "@deprecated");
    }

    public void testExample() throws Exception {
        Source source = getTestSource(getTestFile("testfiles/sdoc/modelTestFile.js"));
        final int caretOffset = getCaretOffset(source, "var ^line8;");
        checkJsDocBlock(source, caretOffset, "@example||desc=var bleeper");
    }

    public void testException() throws Exception {
        Source source = getTestSource(getTestFile("testfiles/sdoc/modelTestFile.js"));
        final int caretOffset = getCaretOffset(source, "var ^line9;");
        checkJsDocBlock(source, caretOffset, "@exception||type=IOE:desc=ioexception");
    }

    public void testId() throws Exception {
        Source source = getTestSource(getTestFile("testfiles/sdoc/modelTestFile.js"));
        final int caretOffset = getCaretOffset(source, "var ^line10;");
        checkJsDocBlock(source, caretOffset, "@id||desc=idText");
    }

    public void testInherits() throws Exception {
        Source source = getTestSource(getTestFile("testfiles/sdoc/modelTestFile.js"));
        final int caretOffset = getCaretOffset(source, "var ^line11;");
        checkJsDocBlock(source, caretOffset, "@inherits||ident=inheritsIdent");
    }

    public void testInternal() throws Exception {
        Source source = getTestSource(getTestFile("testfiles/sdoc/modelTestFile.js"));
        final int caretOffset = getCaretOffset(source, "var ^line12;");
        checkJsDocBlock(source, caretOffset, "@internal");
    }

    public void testMemberOf() throws Exception {
        Source source = getTestSource(getTestFile("testfiles/sdoc/modelTestFile.js"));
        final int caretOffset = getCaretOffset(source, "var ^line13;");
        checkJsDocBlock(source, caretOffset, "@memberOf||ident=memberOfIdent");
    }

    public void testMethod() throws Exception {
        Source source = getTestSource(getTestFile("testfiles/sdoc/modelTestFile.js"));
        final int caretOffset = getCaretOffset(source, "var ^line14;");
        checkJsDocBlock(source, caretOffset, "@method");
    }

    public void testNamespace() throws Exception {
        Source source = getTestSource(getTestFile("testfiles/sdoc/modelTestFile.js"));
        final int caretOffset = getCaretOffset(source, "var ^line15;");
        checkJsDocBlock(source, caretOffset, "@namespace||ident=namespaceIdent");
    }

    public void testParam() throws Exception {
        Source source = getTestSource(getTestFile("testfiles/sdoc/modelTestFile.js"));
        final int caretOffset = getCaretOffset(source, "var ^line16;");
        checkJsDocBlock(source, caretOffset, "@param||type=paramType:name=paramName:desc=paramDescription");
    }

    public void testPrivate() throws Exception {
        Source source = getTestSource(getTestFile("testfiles/sdoc/modelTestFile.js"));
        final int caretOffset = getCaretOffset(source, "var ^line17;");
        checkJsDocBlock(source, caretOffset, "@private");
    }

    public void testProjectDescription() throws Exception {
        Source source = getTestSource(getTestFile("testfiles/sdoc/modelTestFile.js"));
        final int caretOffset = getCaretOffset(source, "var ^line18;");
        checkJsDocBlock(source, caretOffset, "@projectDescription||desc=projectDescription text");
    }

    public void testProperty() throws Exception {
        Source source = getTestSource(getTestFile("testfiles/sdoc/modelTestFile.js"));
        final int caretOffset = getCaretOffset(source, "var ^line19;");
        checkJsDocBlock(source, caretOffset, "@property||type=propertyType");
    }

    public void testReturn() throws Exception {
        Source source = getTestSource(getTestFile("testfiles/sdoc/modelTestFile.js"));
        final int caretOffset = getCaretOffset(source, "var ^line20;");
        checkJsDocBlock(source, caretOffset, "@return||type=returnType:desc=returnDescription");
    }

    public void testSee() throws Exception {
        Source source = getTestSource(getTestFile("testfiles/sdoc/modelTestFile.js"));
        final int caretOffset = getCaretOffset(source, "var ^line21;");
        checkJsDocBlock(source, caretOffset, "@see||desc=seeDescription");
    }

    public void testSince() throws Exception {
        Source source = getTestSource(getTestFile("testfiles/sdoc/modelTestFile.js"));
        final int caretOffset = getCaretOffset(source, "var ^line22;");
        checkJsDocBlock(source, caretOffset, "@since||desc=sinceDescription");
    }

    public void testType() throws Exception {
        Source source = getTestSource(getTestFile("testfiles/sdoc/modelTestFile.js"));
        final int caretOffset = getCaretOffset(source, "var ^line23;");
        checkJsDocBlock(source, caretOffset, "@type||type=typeType");
    }

    public void testVersion() throws Exception {
        Source source = getTestSource(getTestFile("testfiles/sdoc/modelTestFile.js"));
        final int caretOffset = getCaretOffset(source, "var ^line24;");
        checkJsDocBlock(source, caretOffset, "@version||desc=versionDescription");
    }

    public void testParameterWithoutDesc() throws Exception {
        Source source = getTestSource(getTestFile("testfiles/sdoc/parameterTypes.js"));
        final int caretOffset = getCaretOffset(source, "function ^line1(userName){}");
        checkJsDocBlock(source, caretOffset, "@param||type=String:name=userName");
    }

    public void testParameterWithMoreTypes() throws Exception {
        Source source = getTestSource(getTestFile("testfiles/sdoc/parameterTypes.js"));
        final int caretOffset = getCaretOffset(source, "function ^line2(product){}");
        checkJsDocBlock(source, caretOffset, "@param||type=String|Number:name=product");
    }

    public void testParameterOptional() throws Exception {
        Source source = getTestSource(getTestFile("testfiles/sdoc/parameterTypes.js"));
        final int caretOffset = getCaretOffset(source, "function ^line3(accessLevel){}");
        checkJsDocBlock(source, caretOffset, "@param||type=String:name=accessLevel:desc=accessLevel is optional");
    }

    public void testParameterOptionalWithDefault() throws Exception {
        Source source = getTestSource(getTestFile("testfiles/sdoc/parameterTypes.js"));
        final int caretOffset = getCaretOffset(source, "function ^line4(userName){}");
        checkJsDocBlock(source, caretOffset, "@param||type=String:name=userName:desc=name of the user");
    }

    /**
     * Examples of expected values:
     *
     * @alias <ident>
     * @example <desc>
     * @private
     * @type <type>
     * @exception <type> <desc>
     * @param <type> <name> <desc>
     */
    private static void assertElementEquality(FakeSDocElement expected, SDocElement parsed) {
        assertEquals(expected.getType(), parsed.getType());
        switch (parsed.getType().getCategory()) {
            case IDENT:
                assertTrue(parsed instanceof SDocIdentifierElement);
                SDocIdentifierElement identElement = (SDocIdentifierElement) parsed;
                assertEquals(expected.getProperty("ident"), identElement.getIdentifier());
                break;
            case DESCRIPTION:
                assertTrue(parsed instanceof SDocDescriptionElement);
                SDocDescriptionElement descElement = (SDocDescriptionElement) parsed;
                assertEquals(expected.getProperty("desc"), descElement.getDescription());
                break;
            case SIMPLE:
                assertTrue(parsed instanceof SDocSimpleElement);
                break;
            case UNKNOWN:
                assertTrue(parsed instanceof SDocDescriptionElement);
                break;
            case TYPE_SIMPLE:
                assertTrue(parsed instanceof SDocTypeSimpleElement);
                SDocTypeSimpleElement typeSimpleElement = (SDocTypeSimpleElement) parsed;
                assertTypesEquality(expected, typeSimpleElement);
                break;
            case TYPE_DESCRIBED:
                assertTrue(parsed instanceof SDocTypeDescribedElement);
                SDocTypeDescribedElement typeDescElement = (SDocTypeDescribedElement) parsed;
                assertEquals(expected.getProperty("desc"), typeDescElement.getTypeDescription());
                assertTypesEquality(expected, typeDescElement);
                break;
            case TYPE_NAMED:
                assertTrue(parsed instanceof SDocTypeNamedElement);
                SDocTypeNamedElement typeNamedElement = (SDocTypeNamedElement) parsed;
                assertEquals(expected.getProperty("name"), typeNamedElement.getTypeName().getName());
                assertEquals(expected.getProperty("desc"), typeNamedElement.getTypeDescription());
                assertTypesEquality(expected, typeNamedElement);
                break;
            default:
                throw new AssertionError();
        }
    }

    private static void assertTypesEquality(FakeSDocElement expected, SDocTypeSimpleElement element) {
        if (expected.getProperty("type").indexOf("|") != -1) {
            String[] splitedType = expected.getProperty("type").split("[|]");
            for (int i = 0; i < splitedType.length; i++) {
                assertEquals(splitedType[i], element.getDeclaredTypes().get(i).getType());
            }
        } else {
            assertEquals(expected.getProperty("type"), element.getDeclaredTypes().get(0).getType());
        }
    }

    private static void assertElementsEquality(List<SDocElement> expectedTags, List<? extends SDocElement> elements) {
        Collections.sort(expectedTags, elementComparator);
        Collections.sort(elements, elementComparator);

        assertEquals(expectedTags.size(), elements.size());

        for (int i = 0; i < expectedTags.size(); i++) {
            SDocElement expected = expectedTags.get(i);
            SDocElement parsed = elements.get(i);
            assertElementEquality((FakeSDocElement) expected, parsed);
        }
    }

    private static class DocumentationElementComparator implements Comparator<SDocElement> {

        @Override
        public int compare(SDocElement o1, SDocElement o2) {
            return o1.getType().toString().compareTo(o2.getType().toString());
        }
    }

    private static class FakeSDocElement implements SDocElement {

        private final SDocElementType type;
        private Map<String, String> properties = new HashMap<String, String>();

        public FakeSDocElement(SDocElementType type) {
            assertNotNull(type);
            this.type = type;
        }

        @Override
        public SDocElementType getType() {
            return type;
        }

        public void addProperty(String key, String value) {
            assertNotNull(key);
            assertNotNull(value);
            properties.put(key, value);
        }

        public String getProperty(String key) {
            String property = properties.get(key);
            return property == null ? "" : property;
        }
    }
}
