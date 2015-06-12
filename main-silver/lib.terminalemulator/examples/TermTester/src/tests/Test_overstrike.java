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
 * Contributor(s): Ivan Soleimanipour
 *
 * Portions Copyrighted 2014 Sun Microsystems, Inc.
 */

package tests;

import termtester.Context;
import termtester.Test;
import termtester.Util;

/**
 *
 * @author ivan
 */
public class Test_overstrike extends Test {

    public Test_overstrike(Context context) {
        super("overstrike", context, 0, 0, false, Util.FillPattern.NONE);
        info("\n\t\\ESC[4l\tReplace mode\n" +
           "\t\\ESC[4h\tInsert mode");
    }

    public void runBasic(String[] args) {
        context.send("abcdefghijklmnopqrstuvwxyz\n\r");
        context.send("abcdefghijklmnopqrstuvwxyz\n\r");
        context.pause();

        context.send("\\ESC[1;1H");
        context.send("\\ESC[4l");       // replace
        context.send("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        context.pause();

        context.send("\\ESC[2;1H");
        context.send("\\ESC[4h");       // insert
        context.send("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        context.pause();

        context.interp.printf("CR/LF under replace mode\n");
        context.send("\\ESC[5;1H");
        context.send("\\ESC[4l");       // replace
        context.send("hello\\CR\\LFthere");
        context.pause();

        context.interp.printf("TAB under replace mode\n");
        context.send("\\ESC[7;1H");
        context.send("..........................\r");
        context.send("\\ESC[4l");       // replace
        context.send("hello\\HTthere");
        context.pause();

        context.interp.printf("TAB under insert mode\n");
        context.send("\\ESC[9;1H");
        context.send("..........................\r");
        context.send("\\ESC[4h");       // insert
        context.send("hello\\HTthere");
    }
}
