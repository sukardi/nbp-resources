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
 * Portions Copyrighted 2008 Sun Microsystems, Inc.
 */

package org.netbeans.modules.php.project.connections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javax.swing.event.ChangeListener;
import org.netbeans.api.annotations.common.CheckForNull;
import org.netbeans.modules.php.api.validation.ValidationResult;
import org.netbeans.modules.php.project.connections.ConfigManager.Configuration;
import org.netbeans.modules.php.project.connections.api.RemotePreferences;
import org.netbeans.modules.php.project.connections.spi.RemoteConnectionProvider;
import org.netbeans.modules.php.project.connections.ftp.FtpConnectionProvider;
import org.netbeans.modules.php.project.connections.sftp.SftpConnectionProvider;
import org.netbeans.modules.php.project.connections.spi.RemoteConfiguration;
import org.netbeans.modules.php.project.connections.spi.RemoteConfigurationPanel;
import org.netbeans.modules.php.project.connections.ui.RemoteConnectionsPanel;
import org.openide.util.ChangeSupport;
import org.openide.util.NbBundle;

/**
 * @author Tomas Mysik
 */
public final class RemoteConnections {

    static final Logger LOGGER = Logger.getLogger(RemoteConnections.class.getName());

    private static final RemoteConfiguration UNKNOWN_REMOTE_CONFIGURATION =
            new RemoteConfiguration.Empty("unknown-config", NbBundle.getMessage(RemoteConnections.class, "LBL_UnknownRemoteConfiguration")); // NOI18N
    private static final List<RemoteConnectionProvider> CONNECTION_PROVIDERS =
            Arrays.<RemoteConnectionProvider>asList(FtpConnectionProvider.get(), SftpConnectionProvider.get());
    private static final RemoteConnections INSTANCE = new RemoteConnections();

    private final ConfigManager configManager;
    private final DefaultConfigProvider configProvider = new DefaultConfigProvider();
    private final ChangeSupport changeSupport;

    /**
     * Get remote connections.
     * <p>
     * <b>Always return the same instance of {@link RemoteConnections}.</b>
     * @return singleton instance of {@link RemoteConnections}
     */
    public static RemoteConnections get() {
        return INSTANCE;
    }

    private RemoteConnections() {
        configManager = new ConfigManager(configProvider);
        changeSupport = new ChangeSupport(this);
    }

    private RemoteConnectionsPanel createPanel() {
        RemoteConnectionsPanel panel = new RemoteConnectionsPanel(this, configManager);
        panel.setConfigurations(getConfigurations());
        return panel;
    }

    /**
     * Open the UI manager for {@link RemoteConfiguration remote configurations} (optionally,
     * the first configuration is preselected). One can easily add, remove and edit remote configuration.
     * @return <code>true</code> if there are changes in remote configurations.
     */
    public boolean openManager() {
        return openManager(null);
    }

    /**
     * Open the UI manager for {@link RemoteConfiguration remote configurations} with the preselected
     * configuration (if possible). One can easily add, remove and edit remote configuration.
     * @param configName configuration name to be preselected, can be <code>null</code>.
     * @return <code>true</code> if there are changes in remote configurations.
     */
    public boolean openManager(RemoteConfiguration remoteConfiguration) {
        RemoteConnectionsPanel panel = createPanel();
        // original remote configurations
        final List<RemoteConfiguration> remoteConfigurations = getRemoteConfigurations();

        final boolean changed = panel.open(remoteConfiguration);
        if (changed) {
            saveRemoteConnections(remoteConfigurations);
        }
        // reset & reread config provider & manager (configs are kept in memory)
        configProvider.resetConfigs();
        configManager.reset();

        if (changed) {
            changeSupport.fireChange();
        }

        return changed;
    }

    public void addChangeListener(ChangeListener listener) {
        changeSupport.addChangeListener(listener);
    }

    public void removeChangeListener(ChangeListener listener) {
        changeSupport.removeChangeListener(listener);
    }

    List<RemoteConnectionProvider> getConnectionProviders() {
        return CONNECTION_PROVIDERS;
    }

    @CheckForNull
    public ValidationResult validateRemoteConfiguration(RemoteConfiguration remoteConfiguration) {
        for (RemoteConnectionProvider provider : getConnectionProviders()) {
            ValidationResult validationResult = provider.validate(remoteConfiguration);
            if (validationResult != null) {
                return validationResult;
            }
        }
        return null;
    }

    public List<String> getRemoteConnectionTypes() {
        List<String> names = new ArrayList<>();
        for (RemoteConnectionProvider provider : getConnectionProviders()) {
            names.add(provider.getDisplayName());
        }
        return Collections.unmodifiableList(names);
    }

    /**
     * Get remote configuration from the given configuration.
     * @param cfg {@link Configuration} to read data from
     * @return remote configuration or {@code null} if the given configuration is not accepted by any {@link RemoteConnectionProvider}
     */
    public RemoteConfiguration getRemoteConfiguration(ConfigManager.Configuration cfg) {
        for (RemoteConnectionProvider provider : getConnectionProviders()) {
            RemoteConfiguration configuration = provider.getRemoteConfiguration(cfg);
            if (configuration != null) {
                return configuration;
            }
        }
        return null;
    }

    /** Can be null. */
    public RemoteConfigurationPanel getConfigurationPanel(ConfigManager.Configuration cfg) {
        for (RemoteConnectionProvider provider : getConnectionProviders()) {
            RemoteConfigurationPanel configurationPanel = provider.getRemoteConfigurationPanel(cfg);
            if (configurationPanel != null) {
                return configurationPanel;
            }
        }
        return null;
    }

    /** Can be null. */
    public String getConfigurationType(ConfigManager.Configuration cfg) {
        for (RemoteConnectionProvider provider : getConnectionProviders()) {
            RemoteConfigurationPanel remoteConfigurationPanel = provider.getRemoteConfigurationPanel(cfg);
            if (remoteConfigurationPanel != null) {
                return provider.getDisplayName();
            }
        }
        return null;
    }

    /**
     * Get the ordered list of existing (already defined) {@link RemoteConfiguration remote configurations}.
     * The list is ordered according to configuration's display name (locale-sensitive string comparison).
     * @return the ordered list of all the existing remote configurations.
     * @see RemoteConfiguration
     */
    public List<RemoteConfiguration> getRemoteConfigurations() {
        // get all the configs
        List<Configuration> configs = getConfigurations();

        // convert them to remote connections
        List<RemoteConfiguration> remoteConfigs = new ArrayList<>(configs.size());
        for (Configuration cfg : configs) {
            RemoteConfiguration configuration = getRemoteConfiguration(cfg);
            if (configuration == null) {
                // unknown configuration type => get config of unknown type
                configuration = UNKNOWN_REMOTE_CONFIGURATION;
            }
            remoteConfigs.add(configuration);
        }
        return Collections.unmodifiableList(remoteConfigs);
    }

    /**
     * Get the {@link RemoteConfiguration remote configuration} for the given name (<b>NOT</b> the display name).
     * @param name the name of the configuration.
     * @return the {@link RemoteConfiguration remote configuration} for the given name or <code>null</code> if not found.
     */
    public RemoteConfiguration remoteConfigurationForName(String name) {
        assert name != null;
        for (RemoteConfiguration remoteConfig : getRemoteConfigurations()) {
            if (remoteConfig.getName().equals(name)) {
                return remoteConfig;
            }
        }
        return null;
    }

    /** Can be null. */
    public RemoteConfiguration createRemoteConfiguration(String type, ConfigManager.Configuration configuration) {
        assert type != null;
        for (RemoteConnectionProvider provider : getConnectionProviders()) {
            if (type.equals(provider.getDisplayName())) {
                RemoteConfiguration remoteConfiguration = provider.createRemoteConfiguration(configuration);
                assert remoteConfiguration != null : "Remote configuration must be provided for " + type;
                return remoteConfiguration;
            }
        }
        return null;
    }

    private List<Configuration> getConfigurations() {
        Collection<String> cfgNames = configManager.configurationNames();
        List<Configuration> configs = new ArrayList<>(cfgNames.size() - 1); // without default config

        for (String name : cfgNames) {
            if (name == null) {
                // default config
                continue;
            }
            Configuration cfg = configManager.configurationFor(name);
            if (cfg == null) {
                // deleted configuration
                continue;
            }
            configs.add(cfg);
        }
        Collections.sort(configs, ConfigManager.getConfigurationComparator());
        return configs;
    }

    private void saveRemoteConnections(List<RemoteConfiguration> remoteConfigurations) {
        final Preferences remoteConnectionsPreferences = RemotePreferences.getServerConfigsPreferences();
        for (String name : configManager.configurationNames()) {
            if (name == null) {
                // default config
                continue;
            }
            if (!configManager.exists(name)) {
                // deleted
                try {
                    remoteConnectionsPreferences.node(name).removeNode();
                    // remove password from keyring
                    for (RemoteConfiguration remoteConfiguration : remoteConfigurations) {
                        if (remoteConfiguration.getName().equals(name)) {
                            remoteConfiguration.notifyDeleted();
                            break;
                        }
                    }
                } catch (BackingStoreException bse) {
                    LOGGER.log(Level.INFO, "Error while removing unused remote connection: " + name, bse);
                }
            } else {
                // add/update
                Configuration configuration = configManager.configurationFor(name);
                RemoteConfiguration remoteConfiguration = getRemoteConfiguration(configuration);
                assert remoteConfiguration != null : "No remote configuration for configuration " + configuration.getName();

                Preferences node = remoteConnectionsPreferences.node(name);
                for (String propertyName : configuration.getPropertyNames()) {
                    String value = configuration.getValue(propertyName);
                    if (propertyName.equals(ConfigManager.PROP_DISPLAY_NAME)) {
                        // display name
                        if (value != null) {
                            // save it
                            node.put(propertyName, value);
                        } else {
                            // remove it
                            node.remove(propertyName);
                        }
                        continue;
                    }
                    if (value == null) {
                        // unknown property (e.g. knownHostsFile for FTP config => null value)
                        continue;
                    }
                    if (remoteConfiguration.saveProperty(propertyName, value)) {
                        node.remove(propertyName);
                    } else {
                        node.put(propertyName, value);
                    }
                }
            }
        }
    }

    private class DefaultConfigProvider implements ConfigManager.ConfigProvider {
        final Map<String, Map<String, String>> configs;

        public DefaultConfigProvider() {
            configs = ConfigManager.createEmptyConfigs();
            readConfigs();
        }

        @Override
        public String[] getConfigProperties() {
            Set<String> properties = new HashSet<>();
            for (RemoteConnectionProvider provider : getConnectionProviders()) {
                properties.addAll(provider.getPropertyNames());
            }
            return properties.toArray(new String[properties.size()]);
        }

        // changes a map in config manager as well! it holds just a reference, not a copy
        public void resetConfigs() {
            configs.clear();
            configs.putAll(ConfigManager.createEmptyConfigs());
            readConfigs();
        }

        @Override
        public Map<String, Map<String, String>> getConfigs() {
            return configs;
        }

        private void readConfigs() {
            configs.putAll(RemotePreferences.getServerConfigs());
        }
    }
}
