/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2014 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2014 Sun Microsystems, Inc.
 */
package org.netbeans.modules.javascript2.requirejs.navigate;

import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import junit.framework.Test;
import org.netbeans.jellytools.EditorOperator;
import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.junit.NbModuleSuite;
import org.netbeans.modules.javascript2.requirejs.GeneralRequire;

/**
 *
 * @author Vladimir Riha
 */
public class NavigateTest extends GeneralRequire {

    public NavigateTest(String arg0) {
        super(arg0);
    }

    public static Test suite() {
        return NbModuleSuite.create(
                NbModuleSuite.createConfiguration(NavigateTest.class).addTest(
                        "openProject",
                        "testLine5",
                        "testLine6",
                        "testLine6_1",
                        "testLine8",
                        "testLine9",
                        "testLine10",
                        "testLine10_1",
                        "testLine11",
                        "testLine11_1",
                        "testLine11_2",
                        "testLine13",
                        "testLine15",
                        "testLine15_1",
                        "testLine15_2",
                        "testLine16",
                        "testLine16_1",
                        "testLine16_2",
                        "testLine17",
                        "testLine19",
                        "testLine19_1",
                        "testLine20",
                        "testLine21"
                ).enableModules(".*").clusters(".*").honorAutoloadEager(true));
    }

    public void openProject() throws Exception {
        startTest();
        NavigateTest.currentProject = "SimpleRequire";
        JemmyProperties.setCurrentTimeout("ActionProducer.MaxActionTime", 180000);
        openDataProjects("SimpleRequire");
        openFile("js|bbb|def.js", "SimpleRequire");
        endTest();
    }

    public void testLine5() {
        startTest();
        navigate("def.js", "function.js", 5, 16, 7, 21);
        endTest();
    }

    public void testLine6() {
        startTest();
        navigate("def.js", "function.js", 6, 16, 8, 21);
        endTest();
    }

    public void testLine6_1() {
        startTest();
        navigate("def.js", "function.js", 6, 18, 9, 25);
        endTest();
    }

    public void testLine8() {
        startTest();
        navigate("def.js", "newFunction.js", 8, 20, 46, 14);
        endTest();
    }

    public void testLine9() {
        startTest();
        navigate("def.js", "newFunction.js", 9, 28, 48, 13);
        endTest();
    }

    public void testLine10() {
        startTest();
        navigate("def.js", "newFunction.js", 10, 27, 50, 13);
        endTest();
    }

    public void testLine10_1() {
        startTest();
        navigate("def.js", "newFunction.js", 10, 35, 51, 17);
        endTest();
    }

    public void testLine11() {
        startTest();
        navigate("def.js", "newFunction.js", 11, 22, 36, 14);
        endTest();
    }

    public void testLine11_1() {
        startTest();
        navigate("def.js", "newFunction.js", 11, 28, 40, 17);
        endTest();
    }

    public void testLine11_2() {
        startTest();
        navigate("def.js", "newFunction.js", 11, 32, 41, 29);
        endTest();
    }

    public void testLine13() {
        startTest();
        navigate("def.js", "newFunction.js", 13, 19, 17, 14);
        endTest();
    }

    public void testLine15() {
        startTest();
        navigate("def.js", "objectLiteral.js", 15, 18, 13, 5);
        endTest();
    }

    public void testLine15_1() {
        startTest();
        navigate("def.js", "objectLiteral.js", 15, 29, 16, 14);
        endTest();
    }

    public void testLine15_2() {
        startTest();
        navigate("def.js", "objectLiteral.js", 15, 32, 17, 18);
        endTest();
    }

    public void testLine16() {
        startTest();
        navigate("def.js", "objectLiteral.js", 16, 15, 22, 6);
        endTest();
    }

    public void testLine16_1() {
        startTest();
        navigate("def.js", "objectLiteral.js", 16, 19, 23, 9);
        endTest();
    }

    public void testLine16_2() {
        startTest();
        navigate("def.js", "objectLiteral.js", 16, 23, 24, 14);
        endTest();
    }

    public void testLine17() {
        startTest();
        navigate("def.js", "objectLiteral.js", 17, 15, 29, 5);
        endTest();
    }

    public void testLine19() {
        startTest();
        navigate("def.js", "stdModule.js", 19, 22, 28, 9);
        endTest();
    }

    public void testLine19_1() {
        startTest();
        navigate("def.js", "newFunction.js", 19, 29, 50, 13);
        endTest();
    }

    public void testLine20() {
        startTest();
        navigate("def.js", "stdModule.js", 1, 20, 23, 9);
        endTest();
    }

    public void testLine21() {
        startTest();
        navigate("def.js", "stdModule.js", 21, 25, 18, 21);
        endTest();
    }

  private void navigate(String fromFile, String toFile, int fromLine, int fromColumn, int toLine, int toColumn) {
        EditorOperator eo = new EditorOperator(fromFile);
        eo.setCaretPosition(fromLine, fromColumn);
        evt.waitNoEvent(200);
        new org.netbeans.jellytools.actions.Action(null, null, KeyStroke.getKeyStroke(KeyEvent.VK_B, 2)).performShortcut(eo);
        evt.waitNoEvent(500);
        long defaultTimeout = JemmyProperties.getCurrentTimeout("ComponentOperator.WaitComponentTimeout");
        try {
            JemmyProperties.setCurrentTimeout("ComponentOperator.WaitComponentTimeout", 3000);
            EditorOperator ed = new EditorOperator(toFile);
            JemmyProperties.setCurrentTimeout("ComponentOperator.WaitComponentTimeout", defaultTimeout);
            int position = ed.txtEditorPane().getCaretPosition();
            ed.setCaretPosition(toLine, toColumn);
            int expectedPosition = ed.txtEditorPane().getCaretPosition();
            assertTrue("Incorrect caret position. Expected position " + expectedPosition + " but was " + position, position == expectedPosition);
            if (!fromFile.equals(toFile)) {
                ed.close(false);
            }
        } catch (Exception e) {
            JemmyProperties.setCurrentTimeout("ComponentOperator.WaitComponentTimeout", defaultTimeout);
            fail(e.getMessage());
        }

    }
}
