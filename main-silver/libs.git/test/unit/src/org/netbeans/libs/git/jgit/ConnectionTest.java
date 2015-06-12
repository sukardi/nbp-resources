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

package org.netbeans.libs.git.jgit;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.SystemReader;
import org.netbeans.libs.git.GitClient;
import org.netbeans.libs.git.GitClientCallback;
import org.netbeans.libs.git.GitException;
import org.netbeans.libs.git.GitRemoteConfig;

/**
 *
 * @author ondra
 */
public class ConnectionTest extends AbstractGitTestCase {
    private Repository repository;
    private File workDir;

    public ConnectionTest (String testName) throws IOException {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        workDir = getWorkingDirectory();
        repository = getRepository(getLocalGitRepository());
    }
    
    public void testGitConnection () throws Exception {
        GitClient client = getClient(workDir);
        client.listRemoteBranches("git://vcs-test.cz.oracle.com/AnagramGameGit.git/", NULL_PROGRESS_MONITOR);
    }
    
    // start damon as git daemon --base-path=/srv/git --verbose --export-all /srv/git &
    public void testHttpConnectionCredentialsInUri () throws Exception {
        // UN and PWD in uri
        GitClient client = getClient(workDir);
        client.listRemoteBranches("http://user:heslo@vcs-test.cz.oracle.com/git/repo/", NULL_PROGRESS_MONITOR);
    }
    
    public void testHttpConnection () throws Exception {
        // UN and PWD provided by a callback
        GitClient client = getClient(workDir);
        try {
            client.listRemoteBranches("http://vcs-test.cz.oracle.com/git/repo/", NULL_PROGRESS_MONITOR);
            fail();
        } catch (GitException.AuthorizationException ex) {
            assertEquals("http://vcs-test.cz.oracle.com/git/repo/", ex.getRepositoryUrl());
        }
        GitClientCallback callback = new GitClientCallback() {
            @Override
            public String askQuestion (String uri, String prompt) {
                throw new UnsupportedOperationException();
            }

            @Override
            public String getUsername (String uri, String prompt) {
                return "user";
            }

            @Override
            public char[] getPassword (String uri, String prompt) {
                return "heslo".toCharArray();
            }

            @Override
            public char[] getPassphrase (String uri, String prompt) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getIdentityFile (String uri, String prompt) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
            
            @Override
            public Boolean askYesNoQuestion (String uri, String prompt) {
                throw new UnsupportedOperationException();
            }
        };
        client.setCallback(callback);
        client.listRemoteBranches("http://vcs-test.cz.oracle.com/git/repo/", NULL_PROGRESS_MONITOR);
    }
    
    public void testHttpConnectionPublic () throws Exception {
        GitClient client = getClient(workDir);
        // no username or password
        client.setCallback(null);
        try {
            client.listRemoteBranches("http://vcs-test.cz.oracle.com/git/repo/", NULL_PROGRESS_MONITOR);
            fail();
        } catch (GitException.AuthorizationException ex) {
            assertEquals("http://vcs-test.cz.oracle.com/git/repo/", ex.getRepositoryUrl());
        }
        client.listRemoteBranches("http://vcs-test.cz.oracle.com/git-public/repo/", NULL_PROGRESS_MONITOR);
        // callback should not be called at all
        client.setCallback(new DefaultCallback());
        client.listRemoteBranches("http://vcs-test.cz.oracle.com/git-public/repo/", NULL_PROGRESS_MONITOR);
    }
    
    public void testHttpConnectionEmptyPassword () throws Exception {
        GitClient client = getClient(workDir);
        // UN and EMPTY password provided by a callback
        GitClientCallback callback = new GitClientCallback() {
            @Override
            public String askQuestion (String uri, String prompt) {
                throw new UnsupportedOperationException();
            }

            @Override
            public String getUsername (String uri, String prompt) {
                return "user2";
            }

            @Override
            public char[] getPassword (String uri, String prompt) {
                return "".toCharArray();
            }

            @Override
            public char[] getPassphrase (String uri, String prompt) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getIdentityFile (String uri, String prompt) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Boolean askYesNoQuestion (String uri, String prompt) {
                throw new UnsupportedOperationException();
            }
        };
        client.setCallback(callback);
        client.listRemoteBranches("http://vcs-test.cz.oracle.com/git/repo/", NULL_PROGRESS_MONITOR);
    }

    public void testSshConnectionCredentialsInUri () throws Exception {
        GitClient client = getClient(workDir);
        client.setCallback(new DefaultCallback());
        client.listRemoteBranches("ssh://tester:tester@vcs-test.cz.oracle.com/srv/git/repo/", NULL_PROGRESS_MONITOR);
    }

    public void testSshConnectionCanceled () throws Exception {
        GitClient client = getClient(workDir);
        GitClientCallback callback = new DefaultCallback() {
            @Override
            public String getUsername (String uri, String prompt) {
                return null;
            }

            @Override
            public char[] getPassword (String uri, String prompt) {
                return null;
            }
        };
        client.setCallback(callback);
        try {
            client.listRemoteBranches("ssh://vcs-test.cz.oracle.com/srv/git/repo/", NULL_PROGRESS_MONITOR);
        } catch (GitException.AuthorizationException ex) {
            assertEquals("ssh://vcs-test.cz.oracle.com/srv/git/repo/", ex.getRepositoryUrl());
        }
    }

    public void testSshConnectionCredentialsFromCallback () throws Exception {
        GitClient client = getClient(workDir);
        GitClientCallback callback = new DefaultCallback() {
            @Override
            public String getUsername (String uri, String prompt) {
                return "tester";
            }

            @Override
            public char[] getPassword (String uri, String prompt) {
                return "tester".toCharArray();
            }
        };
        client.setCallback(callback);
        client.listRemoteBranches("ssh://vcs-test.cz.oracle.com/srv/git/repo/", NULL_PROGRESS_MONITOR);
    }

    public void testSshConnectionUserInUriPasswordFromCallback () throws Exception {
        GitClient client = getClient(workDir);
        GitClientCallback callback = new DefaultCallback() {
            @Override
            public char[] getPassword (String uri, String prompt) {
                return "tester".toCharArray();
            }
        };
        client.setCallback(callback);
        client.listRemoteBranches("ssh://tester@vcs-test.cz.oracle.com/srv/git/repo/", NULL_PROGRESS_MONITOR);
    }

    public void testSshFetchCredentialsFromCallback () throws Exception {
        GitClient client = getClient(workDir);
        client.setRemote(new GitRemoteConfig("origin",
                Arrays.asList("ssh://vcs-test.cz.oracle.com/srv/git/repo/"),
                Collections.<String>emptyList(),
                Arrays.asList("+refs/heads/*:refs/remotes/origin/*"),
                Collections.<String>emptyList()), NULL_PROGRESS_MONITOR);
        GitClientCallback callback = new DefaultCallback() {
            @Override
            public String getUsername (String uri, String prompt) {
                return "tester";
            }

            @Override
            public char[] getPassword (String uri, String prompt) {
                return "tester".toCharArray();
            }
        };
        client.setCallback(callback);
        client.fetch("origin", NULL_PROGRESS_MONITOR);
    }
    
    public void testSshConnectionPassphrase () throws Exception {
        GitClient client = getClient(workDir);
        client.setCallback(new DefaultCallback() {
            @Override
            public String getUsername (String uri, String prompt) {
                return "gittester";
            }

            @Override
            public String getIdentityFile (String uri, String prompt) {
                return new File(getDataDir(), "private_key").getAbsolutePath();
            }
            
            @Override
            public char[] getPassphrase (String uri, String prompt) {
                assertTrue("Expected passphrase prompt for private_key, was " + prompt, prompt.contains(new File(getDataDir(), "private_key").getAbsolutePath()));
                return "qwerty".toCharArray();
            }
        });
        client.listRemoteBranches("ssh://gittester@vcs-test.cz.oracle.com/srv/git/repo/", NULL_PROGRESS_MONITOR);
    }
    
    public void testSshConnectionGITSSH_Issue213394 () throws Exception {
        SystemReader sr = SystemReader.getInstance();
        SystemReader.setInstance(new DelegatingSystemReader(sr) {

            @Override
            public String getenv (String string) {
                if ("GIT_SSH".equals(string)) {
                     return "/usr/bin/externalsshtool";
                }
                return super.getenv(string);
            }
            
        });
        try {
            GitClient client = getClient(workDir);
            client.setCallback(new DefaultCallback() {
                @Override
                public String getUsername (String uri, String prompt) {
                    return "gittester";
                }

                @Override
                public String getIdentityFile (String uri, String prompt) {
                    return new File(getDataDir(), "private_key").getAbsolutePath();
                }

                @Override
                public char[] getPassphrase (String uri, String prompt) {
                    assertTrue("Expected passphrase prompt for private_key, was " + prompt, prompt.contains(new File(getDataDir(), "private_key").getAbsolutePath()));
                    return "qwerty".toCharArray();
                }
            });
            client.listRemoteBranches("ssh://gittester@vcs-test.cz.oracle.com/srv/git/repo/", NULL_PROGRESS_MONITOR);
            // The minority of users really wanting to use external SSH still have the chance by using a commandline switch.
            // see issue #227161
            System.setProperty("versioning.git.library.useSystemSSHClient", "true");
            try {
                client.listRemoteBranches("ssh://gittester@vcs-test.cz.oracle.com/srv/git/repo/", NULL_PROGRESS_MONITOR);
            } catch (GitException ex) {
                assertTrue(ex.getMessage().contains("Cannot run program \"/usr/bin/externalsshtool\""));
            }
        } finally {
            SystemReader.setInstance(sr);
            System.setProperty("versioning.git.library.useSystemSSHClient", "false");
        }
    }
    
    /**
     * When starts failing then consider rewriting callbacks to return passwords only in getPassword
     * For this test to pass, keyboard-interactive ssh authentication must be enabled on localhost
     */
    public void testSshLocalhostConnection () throws Exception {
        GitClient client = getClient(workDir);
        final AtomicBoolean asked = new AtomicBoolean(false);
        client.setCallback(new DefaultCallback() {
            @Override
            public String askQuestion (String uri, String prompt) {
                assertTrue("Expected question prompt for password", prompt.startsWith("Password"));
                asked.set(true);
                return null;
            }

            @Override
            public String getUsername (String uri, String prompt) {
                return "gittester2";
            }

            @Override
            public String getIdentityFile (String uri, String prompt) {
                return new File(getDataDir(), "private_key").getAbsolutePath();
            }
            
            @Override
            public char[] getPassphrase (String uri, String prompt) {
                return null;
            }
        });
        try {
            client.listRemoteBranches("ssh://gittester2@127.0.0.1/" + workDir.getAbsolutePath(), NULL_PROGRESS_MONITOR);
        } catch (GitException ex) {
            
        }
        // this depends on the server setup
//        assertTrue(asked.get());
    }
    
    private static class DefaultCallback extends GitClientCallback {
        @Override
        public String askQuestion (String uri, String prompt) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String getUsername (String uri, String prompt) {
            return null;
        }

        @Override
        public char[] getPassword (String uri, String prompt) {
            return null;
        }

        @Override
        public String getIdentityFile (String uri, String prompt) {
            return null;
        }

        @Override
        public Boolean askYesNoQuestion (String uri, String prompt) {
            if (prompt.contains("RSA key fingerprint")) {
                return true;
            }
            return null;
        }

        @Override
        public char[] getPassphrase (String uri, String prompt) {
            return "".toCharArray();
        }
        
    }

    private static class DelegatingSystemReader extends SystemReader {
        private final SystemReader instance;

        public DelegatingSystemReader (SystemReader sr) {
            this.instance = sr;
        }

        @Override
        public String getHostname () {
            return instance.getHostname();
        }

        @Override
        public String getenv (String string) {
            return instance.getenv(string);
        }

        @Override
        public String getProperty (String string) {
            return instance.getProperty(string);
        }

        @Override
        public FileBasedConfig openUserConfig (Config config, FS fs) {
            return instance.openUserConfig(config, fs);
        }

        @Override
        public FileBasedConfig openSystemConfig (Config config, FS fs) {
            return instance.openSystemConfig(config, fs);
        }

        @Override
        public long getCurrentTime () {
            return instance.getCurrentTime();
        }

        @Override
        public int getTimezone (long l) {
            return instance.getTimezone(l);
        }
    }
}
