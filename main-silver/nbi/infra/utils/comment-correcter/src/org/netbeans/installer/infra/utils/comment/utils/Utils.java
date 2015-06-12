/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU General
 * Public License Version 2 only ("GPL") or the Common Development and Distribution
 * License("CDDL") (collectively, the "License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html or nbbuild/licenses/CDDL-GPL-2-CP. See the
 * License for the specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header Notice in
 * each file and include the License file at nbbuild/licenses/CDDL-GPL-2-CP.  Oracle
 * designates this particular file as subject to the "Classpath" exception as
 * provided by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the License Header,
 * with the fields enclosed by brackets [] replaced by your own identifying
 * information: "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * Contributor(s):
 * 
 * The Original Software is NetBeans. The Initial Developer of the Original Software
 * is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun Microsystems, Inc. All
 * Rights Reserved.
 * 
 * If you wish your version of this file to be governed by only the CDDL or only the
 * GPL Version 2, indicate your decision by adding "[Contributor] elects to include
 * this software in this distribution under the [CDDL or GPL Version 2] license." If
 * you do not indicate a single choice of license, a recipient has the option to
 * distribute your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above. However, if
 * you add GPL Version 2 code and therefore, elected the GPL Version 2 license, then
 * the option applies only if the new code is made subject to such option by the
 * copyright holder.
 */

package org.netbeans.installer.infra.utils.comment.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * This is the helper utility methods class for the initial comment correcter
 * utility.
 *
 * It provides means to read a text file into a string, write a string to a file and
 * to reformat a given text to the given line length. It also holds several
 * constants that are used elsewhere.
 *
 * @author Kirill Sorokin
 */
public final class Utils {
    /////////////////////////////////////////////////////////////////////////////////
    // Static
    /**
     * Reads the given text file into a string.
     *
     * @param file The file to read.
     * @return Contents of the file as a string.
     * @throws java.io.IOException if an I/O error occurs of there are problems 
     *      with validating the supplied file.
     * @throws java.lang.IllegalArgumentException if the parameter is null.
     */
    public static String readFile(
            final File file) throws IOException {
        // basic validation
        if (file == null) {
            throw new IllegalArgumentException(
                    "The 'file' parameter cannot be null."); // NOI18N
        }
        
        // file validation
        if (!file.exists()) {
            throw new IOException(
                    "The given file '" + file + // NOI18N
                    "' does not exist."); // NOI18N
        }
        if (!file.isFile()) {
            throw new IOException(
                    "The given file '" + file + // NOI18N
                    "' exists but is a directory."); // NOI18N
        }
        
        // read the file
        final StringBuilder string = new StringBuilder();
        
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            
            String line = null;
            while ((line = reader.readLine()) != null) {
                string.append(line + NL);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        
        return string.toString();
    }
    
    /**
     * Writes the given string to a file. The file contents are overwritten be the
     * string.
     *
     * @param file The file to which the string should be written.
     * @param string
     * @throws java.io.IOException if an I/O errors occurs of there are problems 
     *      with validating the supplied file.
     * @throws java.lang.IllegalArgumentException if either of the parameters is 
     *      null.
     */
    public static void writeFile(
            final File file,
            final String string) throws IOException {
        // basic validation
        if (file == null) {
            throw new IllegalArgumentException(
                    "The 'file' parameter cannot be null."); // NOI18N
        }
        if (string == null) {
            throw new IllegalArgumentException(
                    "The 'string' parameter cannot be null."); // NOI18N
        }
        
        // file validation
        final File parent = file.getParentFile();
        if (file.exists() && !file.isFile()) {
            throw new IOException(
                    "The given file '" + file + // NOI18N
                    "' exists and is not a file."); // NOI18N
        }
        if (parent.exists() && !parent.isDirectory()) {
            throw new IOException(
                    "The parent of the given file '" + parent + // NOI18N
                    "' exists and is not a directory."); // NOI18N
        }
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IOException(
                    "The parent of the given file '" + parent + // NOI18N
                    "' does not exist and could not be created."); // NOI18N
        }
        
        // write the file
        final BufferedReader reader =
                new BufferedReader(new StringReader(string));
        
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter(file));
            
            String line = null;
            while ((line = reader.readLine()) != null) {
                writer.println(line);
            }
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
    
    /**
     * Reformats the given text to the given line length.
     *
     * <p>
     * Optinally the caller may specify a prefix which will be prepended to every
     * string in the reformatted file.
     *
     * @param string The text to reformat.
     * @param prefix The prefix to attach to the lines of the reformatted text.
     * @param length The desired line length.
     * @return The reformatted as a string.
     * @throws java.lang.IllegalArgumentException if either of the parameters is 
     *      null or if the line length is negative or zero.
     */
    public static String reformat(
            final String string,
            final String prefix,
            final int length) {
        // basic validation
        if (string == null) {
            throw new IllegalArgumentException(
                    "The 'string' parameter cannot be null."); // NOI18N
        }
        if (prefix == null) {
            throw new IllegalArgumentException(
                    "The 'prefix' parameter cannot be null."); // NOI18N
        }
        if (length <= 0) {
            throw new IllegalArgumentException(
                    "The 'length' parameter should be positive."); // NOI18N
        }
        
        final List<String> lines = new LinkedList<String>();
        lines.addAll(Arrays.asList(string.split(NL_PATTERN)));
        
        final int realLength = length - prefix.length();
        
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            
            // if the current line's length is greater than the required one, we 
            // find the space character which will allow us to wrap the line 
            // satisfying the required length (the new line will be inserted into 
            // the list and processed at the next iteration); if the line cannot be 
            // wrapped - it is NOT modified
            // if the line's length is less than the required one, and the line is 
            // not empty (which would the end of a paragraph), and is not the last 
            // line in the text, we try to attach the next line to it and wrap the 
            // sum if it's required
            if (line.length() > realLength) {
                final int index = findPrevious(line, ' ', realLength);
                
                if (index != -1) {
                    lines.add(i, line.substring(0, index));
                    lines.set(i + 1, line.substring(index + 1));
                }
            } else if (!line.trim().equals(EL) && (i < lines.size() - 1)) {
                final String next = lines.get(i + 1);
                
                if (!next.trim().equals(EL)) {
                    if (!line.endsWith(SP)) {
                        line = line + SP + next;
                    } else {
                        line = line + next;
                    }
                    
                    if (line.length() > realLength) {
                        int index = findPrevious(line, ' ', realLength);
                        
                        if (index != -1) {
                            lines.set(i, line.substring(0, index));
                            lines.set(i + 1, line.substring(index + 1));
                        }
                    } else {
                        lines.set(i, line);
                        lines.remove(i + 1);
                    }
                }
            }
        }
        
        // concatenate the lines and return the result
        final StringBuilder builder = new StringBuilder();
        for (String line: lines) {
            builder.append(prefix).append(line).append(NL);
        }
        
        return builder.toString();
    }
    
    // private //////////////////////////////////////////////////////////////////////
    /**
     * Finds the previous entry of the given character in the given string, starting 
     * at the given offset.
     * 
     * @param string The string in which to perform the search.
     * @param ch The character to look for.
     * @param offset The offset from which to start searching.
     * @return The previous index of the given character of <code>-1</code> if the 
     *      character cannot be found.
     * @throws java.lang.IllegalArgumentException if the string parameter is null.
     */
    private static int findPrevious(
            final String string,
            final char ch,
            final int offset) {
        // basic validation
        if (string == null) {
            throw new IllegalArgumentException(
                    "The 'string' parameter cannot be null."); // NOI18N
        }
        
        for (int i = offset; i > 0; i--) {
            if (string.charAt(i) == ch) {
                return i;
            }
        }
        
        return -1;
    }
    
    /////////////////////////////////////////////////////////////////////////////////
    // Instance
    /**
     * A dummy <code>private</code> default constructor, which prevents the class 
     * from being instantiated.
     */
    private Utils() {
        // does nothing
    }
    
    /////////////////////////////////////////////////////////////////////////////////
    // Constants
    /**
     * Newline character combination for the current platform. This would be
     * equal to \r\n on windows, \n on unices and \r on mac.
     */
    public static final String NL =
            System.getProperty("line.separator"); // NOI18N
    
    /**
     * Empty line constant.
     */
    public static final String EL =
            ""; // NOI18N
    
    /**
     * Space character constant.
     */
    public static final String SP =
            " "; // NOI18N
    
    /**
     * A platform-agnostic pattern which will match the end of the line.
     */
    public static final String NL_PATTERN =
            "\r\n|\n|\r"; // NOI18N
}
