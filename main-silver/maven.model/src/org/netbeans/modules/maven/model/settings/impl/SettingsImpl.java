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
package org.netbeans.modules.maven.model.settings.impl;

import java.util.List;
import org.netbeans.modules.maven.model.settings.Mirror;
import org.netbeans.modules.maven.model.settings.ModelList;
import org.netbeans.modules.maven.model.settings.Profile;
import org.netbeans.modules.maven.model.settings.Proxy;
import org.netbeans.modules.maven.model.settings.Server;
import org.netbeans.modules.maven.model.settings.Settings;
import org.netbeans.modules.maven.model.settings.SettingsComponent;
import org.netbeans.modules.maven.model.settings.SettingsComponentVisitor;
import org.netbeans.modules.maven.model.settings.SettingsModel;
import org.netbeans.modules.maven.model.settings.StringList;
import org.netbeans.modules.maven.model.util.ModelImplUtils;
import org.w3c.dom.Element;

/**
 *
 * @author mkleint
 */
public class SettingsImpl extends SettingsComponentImpl implements Settings {

    @SuppressWarnings("unchecked")
    private static final Class<SettingsComponent>[] ORDER = new Class[] {
        ProfileImpl.List.class,
        MirrorImpl.List.class,
        ServerImpl.List.class,
        ProxyImpl.List.class,
        StringListImpl.class, //active profiles
    };

    public SettingsImpl(SettingsModel model, Element element) {
        super(model, element);
    }
    
    public SettingsImpl(SettingsModel model) {
        this(model, createElementNS(model, model.getSettingsQNames().SETTINGS));
    }

    // attributes


    @Override
    public List<Profile> getProfiles() {
        ModelList<Profile> childs = getChild(ProfileImpl.List.class);
        if (childs != null) {
            return childs.getListChildren();
        }
        return null;
    }

    @Override
    public void addProfile(Profile profile) {
        ModelList<Profile> childs = getChild(ProfileImpl.List.class);
        if (childs == null) {
            setChild(ProfileImpl.List.class,
                    getModel().getSettingsQNames().PROFILES.getName(),
                    getModel().getFactory().create(this, getModel().getSettingsQNames().PROFILES.getQName()),
                    getClassesBefore(ORDER, ProfileImpl.List.class));
            childs = getChild(ProfileImpl.List.class);
            assert childs != null;
        }
        childs.addListChild(profile);
    }

    @Override
    public void removeProfile(Profile profile) {
        ModelList<Profile> childs = getChild(ProfileImpl.List.class);
        if (childs != null) {
            childs.removeListChild(profile);
        }
    }



    @Override
    public void accept(SettingsComponentVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public List<String> getActiveProfiles() {
        List<StringList> lists = getChildren(StringList.class);
        for (StringList list : lists) {
            if (getModel().getSettingsQNames().ACTIVEPROFILES.getName().equals(list.getPeer().getNodeName())) {
                return list.getListChildren();
            }
        }
        return null;
    }

    @Override
    public void addActiveProfile(String profileid) {
        List<StringList> lists = getChildren(StringList.class);
        for (StringList list : lists) {
            if (getModel().getSettingsQNames().ACTIVEPROFILES.getName().equals(list.getPeer().getNodeName())) {
                list.addListChild(profileid);
                return;
            }
        }
        setChild(StringListImpl.class,
                 getModel().getSettingsQNames().ACTIVEPROFILES.getName(),
                 getModel().getFactory().create(this, getModel().getSettingsQNames().ACTIVEPROFILES.getQName()),
                 getClassesBefore(ORDER, StringListImpl.class));
        lists = getChildren(StringList.class);
        for (StringList list : lists) {
            if (getModel().getSettingsQNames().ACTIVEPROFILES.getName().equals(list.getPeer().getNodeName())) {
                list.addListChild(profileid);
                return;
            }
        }
    }

    @Override
    public void removeActiveProfile(String profileid) {
        List<StringList> lists = getChildren(StringList.class);
        for (StringList list : lists) {
            if (getModel().getSettingsQNames().ACTIVEPROFILES.getName().equals(list.getPeer().getNodeName())) {
                list.removeListChild(profileid);
                return;
            }
        }
    }

    @Override
    public Profile findProfileById(String id) {
        assert id != null;
        java.util.List<Profile> profiles = getProfiles();
        if (profiles != null) {
            for (Profile p : profiles) {
                if (id.equals(p.getId())) {
                    return p;
                }
            }
        }
        return null;
    }

    @Override
    public List<String> getPluginGroups() {
        List<StringList> lists = getChildren(StringList.class);
        for (StringList list : lists) {
            if (ModelImplUtils.hasName(list, getModel().getSettingsQNames().PLUGINGROUPS.getName())) {
                return list.getListChildren();
            }
        }
        return null;
    }

    @Override
    public void addPluginGroup(String group) {
        List<StringList> lists = getChildren(StringList.class);
        for (StringList list : lists) {
            if (ModelImplUtils.hasName(list, getModel().getSettingsQNames().PLUGINGROUPS.getName())) {
                list.addListChild(group);
                return;
            }
        }
        setChild(StringListImpl.class,
                 getModel().getSettingsQNames().PLUGINGROUPS.getName(),
                 getModel().getFactory().create(this, getModel().getSettingsQNames().PLUGINGROUPS.getQName()),
                 getClassesBefore(ORDER, StringListImpl.class));
        lists = getChildren(StringList.class);
        for (StringList list : lists) {
            if (ModelImplUtils.hasName(list, getModel().getSettingsQNames().PLUGINGROUPS.getName())) {
                list.addListChild(group);
                return;
            }
        }
    }

    @Override
    public void removePluginGroup(String group) {
        List<StringList> lists = getChildren(StringList.class);
        for (StringList list : lists) {
            if (ModelImplUtils.hasName(list, getModel().getSettingsQNames().PLUGINGROUPS.getName())) {
                list.removeListChild(group);
                return;
            }
        }
    }

    @Override
    public List<Proxy> getProxies() {
        ModelList<Proxy> childs = getChild(ProxyImpl.List.class);
        if (childs != null) {
            return childs.getListChildren();
        }
        return null;
    }

    @Override
    public void addProxy(Proxy proxy) {
        ModelList<Proxy> childs = getChild(ProxyImpl.List.class);
        if (childs == null) {
            setChild(ProxyImpl.List.class,
                    getModel().getSettingsQNames().PROXIES.getName(),
                    getModel().getFactory().create(this, getModel().getSettingsQNames().PROXIES.getQName()),
                    getClassesBefore(ORDER, ProxyImpl.List.class));
            childs = getChild(ProxyImpl.List.class);
            assert childs != null;
        }
        childs.addListChild(proxy);
    }

    @Override
    public void removeProxy(Proxy proxy) {
        ModelList<Proxy> childs = getChild(ProxyImpl.List.class);
        if (childs != null) {
            childs.removeListChild(proxy);
        }
    }

    @Override
    public List<Server> getServers() {
        ModelList<Server> childs = getChild(ServerImpl.List.class);
        if (childs != null) {
            return childs.getListChildren();
        }
        return null;
    }

    @Override
    public void addServer(Server server) {
        ModelList<Server> childs = getChild(ServerImpl.List.class);
        if (childs == null) {
            setChild(ServerImpl.List.class,
                    getModel().getSettingsQNames().SERVERS.getName(),
                    getModel().getFactory().create(this, getModel().getSettingsQNames().SERVERS.getQName()),
                    getClassesBefore(ORDER, ServerImpl.List.class));
            childs = getChild(ServerImpl.List.class);
            assert childs != null;
        }
        childs.addListChild(server);
    }

    @Override
    public void removeServer(Server server) {
        ModelList<Server> childs = getChild(ServerImpl.List.class);
        if (childs != null) {
            childs.removeListChild(server);
        }
    }

    @Override
    public List<Mirror> getMirrors() {
        ModelList<Mirror> childs = getChild(MirrorImpl.List.class);
        if (childs != null) {
            return childs.getListChildren();
        }
        return null;
    }

    @Override
    public void addMirror(Mirror mirror) {
        ModelList<Mirror> childs = getChild(MirrorImpl.List.class);
        if (childs == null) {
            setChild(MirrorImpl.List.class,
                    getModel().getSettingsQNames().MIRRORS.getName(),
                    getModel().getFactory().create(this, getModel().getSettingsQNames().MIRRORS.getQName()),
                    getClassesBefore(ORDER, MirrorImpl.List.class));
            childs = getChild(MirrorImpl.List.class);
            assert childs != null;
        }
        childs.addListChild(mirror);
    }

    @Override
    public void removeMirror(Mirror mirror) {
        ModelList<Mirror> childs = getChild(MirrorImpl.List.class);
        if (childs != null) {
            childs.removeListChild(mirror);
        }
    }

    @Override
    public Mirror findMirrorById(String id) {
        assert id != null;
        java.util.List<Mirror> mirrors = getMirrors();
        if (mirrors != null) {
            for (Mirror m : mirrors) {
                if (id.equals(m.getId())) {
                    return m;
                }
            }
        }
        return null;
    }


    @Override
    public String getLocalRepository() {
        return getChildElementText(getModel().getSettingsQNames().LOCALREPOSITORY.getQName());
    }

    @Override
    public void setLocalRepository(String repo) {
        setChildElementText(getModel().getSettingsQNames().LOCALREPOSITORY.getName(), repo,
                getModel().getSettingsQNames().LOCALREPOSITORY.getQName());
    }

    @Override
    public Boolean isInteractiveMode() {
        String str = getChildElementText(getModel().getSettingsQNames().INTERACTIVEMODE.getQName());
        if (str != null) {
            return Boolean.valueOf(str);
        }
        return null;
    }

    @Override
    public void setInteractiveMode(Boolean interactive) {
        setChildElementText(getModel().getSettingsQNames().INTERACTIVEMODE.getName(),
                interactive == null ? null : interactive.toString(),
                getModel().getSettingsQNames().INTERACTIVEMODE.getQName());
    }

    @Override
    public Boolean isUsePluginRegistry() {
        String str = getChildElementText(getModel().getSettingsQNames().USEPLUGINREGISTRY.getQName());
        if (str != null) {
            return Boolean.valueOf(str);
        }
        return null;
    }

    @Override
    public void setUsePluginRegistry(Boolean use) {
        setChildElementText(getModel().getSettingsQNames().USEPLUGINREGISTRY.getName(),
                use == null ? null : use.toString(),
                getModel().getSettingsQNames().USEPLUGINREGISTRY.getQName());
    }

    @Override
    public Boolean isOffline() {
        String str = getChildElementText(getModel().getSettingsQNames().OFFLINE.getQName());
        if (str != null) {
            return Boolean.valueOf(str);
        }
        return null;
    }

    @Override
    public void setOffline(Boolean offline) {
        setChildElementText(getModel().getSettingsQNames().OFFLINE.getName(),
                offline == null ? null : offline.toString(),
                getModel().getSettingsQNames().OFFLINE.getQName());
    }

}
