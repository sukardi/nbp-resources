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

package org.netbeans.modules.parsing.impl.indexing;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.netbeans.api.annotations.common.NonNull;
import org.netbeans.api.annotations.common.NullAllowed;
import org.netbeans.api.editor.document.EditorMimeTypes;
import org.openide.util.TopologicalSortException;
import org.openide.util.BaseUtilities;

/**
 *
 * @author Tomas Zezula
 */
public final class Util {


    /*tests*/ static Set<String> allMimeTypes;
    private static volatile boolean hadCycles =
        Boolean.getBoolean("disable.reversedeps.fastpath"); //NOI18N

    public static Set<String> getAllMimeTypes () {
        return allMimeTypes != null ?
            allMimeTypes :
            EditorMimeTypes.getDefault().getSupportedMimeTypes();
    }

    public static StackTraceElement findCaller(StackTraceElement[] elements, Object... classesToFilterOut) {
        loop: for (StackTraceElement e : elements) {
            if (e.getClassName().equals(Util.class.getName()) || e.getClassName().startsWith("java.lang.")) { //NOI18N
                continue;
            }

            if (classesToFilterOut != null && classesToFilterOut.length > 0) {
                for(Object c : classesToFilterOut) {
                    if (c instanceof Class && e.getClassName().startsWith(((Class) c).getName())) {
                        continue loop;
                    } else if (c instanceof String && e.getClassName().startsWith((String) c)) {
                        continue loop;
                    }
                }
            } else {
                if (e.getClassName().startsWith("org.netbeans.modules.parsing.")) { //NOI18N
                    continue;
                }
            }

            return e;
        }
        return null;
    }

    /**
     * Resolves URL within a root.
     * @param root the root to resolve the URL in.
     * @param relativePath the relative path under the root.
     * @param isDirectory true if the relativePath is known to point to directory,
     * false if the relativePath is known to point to file, null if nothing is known
     * about the target.
     * @return
     * @throws MalformedURLException 
     * @throws IllegalStateException when file ends with '/'
     */
    public static URL resolveUrl(
            @NonNull final URL root,
            @NonNull final String relativePath,
            @NullAllowed final Boolean isDirectory) throws MalformedURLException, IllegalStateException {
        try {
            if ("file".equals(root.getProtocol())) { //NOI18N
                if (isDirectory == Boolean.FALSE &&
                    (relativePath.isEmpty() || relativePath.charAt(relativePath.length()-1) == '/')) {  //NOI18N
                    throw new IllegalStateException(
                        MessageFormat.format("relativePath: {0}", relativePath));   //NOI18N
                }
                // Performance optimization for File.toURI() which calls this method
                // and the original implementation calls into native method
                return BaseUtilities.toURI(new FastFile(
                    BaseUtilities.toFile(root.toURI()),
                    relativePath,
                    isDirectory)).toURL();
            } else {
                return new URL(root, relativePath);
            }
        } catch (URISyntaxException use) {
            MalformedURLException mue = new MalformedURLException("Can't resolve URL: root=" + root + ", relativePath=" + relativePath); //NOI18N
            mue.initCause(use);
            throw mue;
        }
    }

    /**
     * Resolves URL within a file root.
     * @param root the root to resolve the path in.
     * @param relativePath the relative path under the root.
     * @param isDirectory true if the relativePath is known to point to directory,
     * false if the relativePath is known to point to file, null if nothing is known
     * about the target.
     * @return
     * @throws MalformedURLException
     * @throws IllegalStateException when file ends with '/' or {@link File#separatorChar}
     */
    public static URL resolveFile(
        @NonNull final File file,
        @NonNull final String relativePath,
        @NullAllowed final Boolean isDirectory) throws MalformedURLException {
        if (isDirectory == Boolean.FALSE &&
            (relativePath.isEmpty() ||
             relativePath.charAt(relativePath.length()-1) == '/' ||    //NOI18N
             relativePath.charAt(relativePath.length()-1) == File.separatorChar)) {
            throw new IllegalStateException(
                MessageFormat.format("relativePath: {0}", relativePath));   //NOI18N
        }
        // Performance optimization for File.toURI() which calls this method
        // and the original implementation calls into native method
        return BaseUtilities.toURI(new FastFile(
            file,
            relativePath,
            isDirectory)).toURL();
    }

    public static boolean containsAny(Collection<? extends String> searchIn, Collection<? extends String> searchFor) {
        if (searchIn != null && searchFor != null) {
            for(String s : searchFor) {
                if (searchIn.contains(s)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isParentOf(
            @NonNull final File folder,
            @NonNull final File file) {
        return file.getAbsolutePath().startsWith(folder.getAbsolutePath());
    }

    @NonNull
    @org.netbeans.api.annotations.common.SuppressWarnings(value={"DMI_COLLECTION_OF_URLS"}, justification="URLs have never host part")
    public static Set<URL> findReverseSourceRoots(
            @NonNull final URL thisSourceRoot,
            @NonNull final Map<URL, List<URL>> deps,
            @NonNull final Map<URL, List<URL>> peers) {
        //Create inverse dependencies
        final Map<URL, Collection<URL>> inverseDeps = findReverseDependencies(deps);
        //Collect dependencies
        final Set<URL> result = new HashSet<>();
        final LinkedList<URL> todo = new LinkedList<> ();
        todo.add (thisSourceRoot);
        while (!todo.isEmpty()) {
            final URL u = todo.removeFirst();
            if (!result.contains(u)) {
                result.add (u);
                Collection<URL> ideps = inverseDeps.get(u);
                if (ideps != null) {
                    todo.addAll (ideps);
                }
                ideps = peers.get(u);
                if (ideps != null) {
                    todo.addAll (ideps);
                }
            }
        }
        return result;
    }

    @NonNull
    @org.netbeans.api.annotations.common.SuppressWarnings(value={"DMI_COLLECTION_OF_URLS"}, justification="URLs have never host part")
    public static Map<URL,Collection<URL>> findTransitiveReverseDependencies(
            @NonNull final Map<URL, List<URL>> deps,
            @NonNull final Map<URL, List<URL>> peers) {
        //Create inverse dependencies
        final Map<URL, Collection<URL>> inverseDeps = findReverseDependencies(deps);
        //Collect dependencies
        if (!hadCycles) {
            try {
                return fastTransitiveDeps(inverseDeps, peers);
            } catch (TopologicalSortException tse) {
                hadCycles = true;
            }
        }
        return slowTransitiveDeps(inverseDeps, peers);
    }

    @NonNull
    private static Map<URL,Collection<URL>> fastTransitiveDeps(
        @NonNull final Map<URL,? extends Collection<URL>> inverseDeps,
        @NonNull final Map<URL,? extends Collection<URL>> peers) throws TopologicalSortException {
        final List<URL> sortedNodes = BaseUtilities.topologicalSort(inverseDeps.keySet(), inverseDeps);
        Collections.reverse(sortedNodes);        
        final Map<URL,Collection<URL>> result = new HashMap<URL,Collection<URL>>();
        for (Map.Entry<URL,? extends Collection<URL>> peerEntry : peers.entrySet()) {
            final Collection<URL> deps = inverseDeps.get(peerEntry.getKey());
            if (deps != null) {
                final Collection<URL> peerValue = peerEntry.getValue();
                if (peerValue != null) {
                    for (URL peer : peerValue) {
                        deps.add(peer);
                        final Collection<URL> peerDeps = inverseDeps.get(peer);
                        if (peerDeps != null) {
                            deps.addAll(peerDeps);
                        }
                    }
                }
            }
        }
        for (URL root : sortedNodes) {
            final Set<URL> deps = new HashSet<URL>();
            result.put(root, deps);
            deps.add(root);
            final Collection<URL> directDeps = inverseDeps.get(root);
            for (URL dd : directDeps) {
                final Collection<URL> transitiveDeps = result.get(dd);
                if (transitiveDeps != null) {
                    deps.addAll(transitiveDeps);
                } else {
                    deps.add(dd);
                }
            }
        }        
        return result;
    }

    @NonNull
    private static Map<URL,Collection<URL>> slowTransitiveDeps(
        @NonNull final Map<URL,? extends Collection<URL>> inverseDeps,
        @NonNull final Map<URL,? extends Collection<URL>> peers) {
        final Map<URL,Collection<URL>> result = new HashMap<URL,Collection<URL>>();
        for (URL thisSourceRoot : inverseDeps.keySet()) {
            final LinkedList<URL> todo = new LinkedList<URL> ();
            final Set<URL> partialResult = new HashSet<URL>();
            todo.add (thisSourceRoot);
            while (!todo.isEmpty()) {
                final URL u = todo.removeFirst();
                if (!partialResult.contains(u)) {
                    partialResult.add (u);
                    Collection<URL> ideps = inverseDeps.get(u);
                    if (ideps != null) {
                        todo.addAll (ideps);
                    }
                    ideps = peers.get(u);
                    if (ideps != null) {
                        todo.addAll (ideps);
                    }
                }
            }
            result.put(thisSourceRoot, partialResult);
        }
        return result;
    }


    @NonNull
    @org.netbeans.api.annotations.common.SuppressWarnings(value={"DMI_COLLECTION_OF_URLS"}, justification="URLs have never host part")
    private static Map<URL,Collection<URL>> findReverseDependencies(@NonNull final Map<URL, List<URL>> deps) {
        final Map<URL, Collection<URL>> inverseDeps = new HashMap<URL, Collection<URL>> ();
        for (Map.Entry<URL,List<URL>> entry : deps.entrySet()) {
            final URL u1 = entry.getKey();
            if (inverseDeps.get(u1) == null) {
                inverseDeps.put(u1, new HashSet<URL>());
            }
            final List<URL> l1 = entry.getValue();
            for (URL u2 : l1) {
                Collection<URL> l2 = inverseDeps.get(u2);
                if (l2 == null) {
                    l2 = new HashSet<URL>();
                    inverseDeps.put (u2,l2);
                }
                l2.add (u1);
            }
        }
        return inverseDeps;
    }

    private Util() {
    }

    private static final class FastFile extends File {

        private static final java.nio.file.InvalidPathException IP =
                new java.nio.file.InvalidPathException("", "") {    //NOI18N
                    @Override
                    public Throwable fillInStackTrace() {
                        return this;
                    }
                };

        private final Boolean isDirectory;

        FastFile(
            @NonNull final File file,
            @NonNull final String path,
            @NullAllowed final Boolean isDirectory) {
            super(file, path);
            this.isDirectory = isDirectory;
        }

        @Override
        public File getAbsoluteFile() {
            if (isAbsolute()) {
                return this;
            } else {
                return super.getAbsoluteFile();
            }
        }

        @Override
        public boolean isDirectory() {
            return isDirectory == null ?
                super.isDirectory() :
                isDirectory;
        }

        @Override
        public Path toPath() {
            if (isDirectory != null) {
                throw IP;
            } else {
                return super.toPath();
            }
        }
    }
}
