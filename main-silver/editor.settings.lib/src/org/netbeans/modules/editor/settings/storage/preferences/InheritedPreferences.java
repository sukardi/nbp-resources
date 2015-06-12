/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2013 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2013 Sun Microsystems, Inc.
 */
package org.netbeans.modules.editor.settings.storage.preferences;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.prefs.AbstractPreferences;
import java.util.prefs.BackingStoreException;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;
import org.netbeans.modules.editor.settings.storage.api.MemoryPreferences;
import org.netbeans.modules.editor.settings.storage.api.OverridePreferences;
import org.openide.util.WeakListeners;

/**
 * Support for inheriting Preferences, while still working with stored ones.
 * 
 * This class solves the 'diamond inheritance', which is present during editing:
 * a MIME-type preferences derive from BOTH its persistent values (preferred) and
 * from the parent, whose actual values are potentially transient, and also persistent.
 * <p/>
 * Let us assume the following assignment:
 * <ul>
 * <li>TC (this current) = currently added/changed/removed values
 * <li>TP (this persistent) = persistent values, the getLocal() part of the Mime PreferencesImpl object
 * <li>PC (parent current) = currently added/changed/removed values of the parent
 * <li>PP (parent persistent) = persistent values, the getLocal() part of the parent MimePreferences
 * </ul>
 * The desired priority to find a value is: TC, TP, PC, PP. Because of {@link MemoryPreferences}, the
 * PC, PP (and potentially fallback to a grandparent) we already have, if we use the parent's {@link MemoryPreferences}
 * preferences as 'inherited'. The "TC" is handled by ProxyPreferences for this Mime node. In InheritedPreferences,
 * we must only inject the TP in between TC and the parent's preferences (PC, PP, ...)
 * <p/>
 * The object is intended to act as a ProxyPreferences delegate, all writes go directly to the stored
 * Mime preferences.
 * 
 * @author sdedic
 */
public final class InheritedPreferences extends AbstractPreferences implements PreferenceChangeListener, OverridePreferences  {
    /**
     * Preferences inherited, ie from a parent Mime type
     */
    private Preferences inherited;
    
    /**
     * Stored preferences, 
     */
    private Preferences stored;
    
    public InheritedPreferences(Preferences inherited, Preferences stored) {
        super(null, ""); // NOI18N
        this.inherited = inherited;
        if (!(stored instanceof OverridePreferences)) {
            throw new IllegalArgumentException();
        }
        this.stored = stored;
        stored.addPreferenceChangeListener(WeakListeners.create(PreferenceChangeListener.class, this, stored));
        inherited.addPreferenceChangeListener(WeakListeners.create(PreferenceChangeListener.class, this, inherited));
    }

    /* package */ Preferences getParent() {
        return inherited;
    }

    @Override
    protected void putSpi(String key, String value) {
        // do nothing, the AbstractPref then just fires an event
    }

    @Override
    public void put(String key, String value) {
        if (Boolean.TRUE != ignorePut.get()) {
            stored.put(key, value);
        }
        super.put(key, value);
    }

    @Override
    public void putInt(String key, int value) {
        if (Boolean.TRUE != ignorePut.get()) {
            stored.putInt(key, value);
        }
        super.putInt(key, value); 
    }

    @Override
    public void putLong(String key, long value) {
        if (Boolean.TRUE != ignorePut.get()) {
            stored.putLong(key, value);
        }
        super.putLong(key, value);
    }

    @Override
    public void putBoolean(String key, boolean value) {
        if (Boolean.TRUE != ignorePut.get()) {
            stored.putBoolean(key, value);
        }
        super.putBoolean(key, value);
    }

    @Override
    public void putFloat(String key, float value) {
        if (Boolean.TRUE != ignorePut.get()) {
            stored.putFloat(key, value);
        }
        super.putFloat(key, value); 
    }

    @Override
    public void putDouble(String key, double value) {
        if (Boolean.TRUE != ignorePut.get()) {
            stored.putDouble(key, value);
        }
        super.putDouble(key, value); 
    }

    @Override
    public void putByteArray(String key, byte[] value) {
        if (Boolean.TRUE != ignorePut.get()) {
            stored.putByteArray(key, value);
        }
        super.putByteArray(key, value);
    }
    
    private ThreadLocal<Boolean> ignorePut = new ThreadLocal<Boolean>();

    @Override
    public void preferenceChange(PreferenceChangeEvent evt) {
        // potential NPE fix; key ought not be null, but guard against it.
        if (evt.getKey() == null) {
            return;
        }
        ignorePut.set(true);
        String k = evt.getKey();
        String v = evt.getNewValue();
        try {
            if (evt.getSource() == stored) {
                // not important, obnly local storage should refire, except clearing of the local value
                if (!isOverriden(k) && v != null) {
                    return;
                }
                // try to recover the inherited value
                if (v == null) {
                    v = inherited.get(k, null);
                }
            } else if (isOverriden(k)) {
                return;
            }
            // potential NPE, null values should be reported as removals.
            if (v == null) {
                remove(k);
            } else {
                put(k, v);
            }
        } finally {
            ignorePut.set(false);
        }
    }
    
    /**
     * The value is defined locally, if the stored prefs define the value
     * locally. The parent definitions do not count. It is expected, that the
     * ProxyPreferences will report its local overrides as local in front of this
     * InheritedPreferences.
     * 
     * @param k
     * @return 
     */
    public @Override boolean isOverriden(String k) {
        if (stored instanceof OverridePreferences) {
            return ((OverridePreferences)stored).isOverriden(k);
        } else {
            return true;
        }
    }
    
    @Override
    protected String getSpi(String key) {
        // check the stored values
        OverridePreferences localStored = (OverridePreferences)stored;
        if (localStored.isOverriden(key)) {
            return stored.get(key, null);
        }
        // fall back to the inherited prefs, potentially its stored values etc.
        return inherited.get(key, null);
    }

    @Override
    protected void removeSpi(String key) {
        if (Boolean.TRUE != ignorePut.get()) {
            stored.remove(key);
        }
    }

    @Override
    protected void removeNodeSpi() throws BackingStoreException {
        stored.removeNode();
    }

    @Override
    protected String[] keysSpi() throws BackingStoreException {
        Collection<String> names = new HashSet<String>();
        names.addAll(Arrays.asList(stored.keys()));
        names.addAll(Arrays.asList(inherited.keys()));
        return names.toArray(new String[names.size()]);
    }

    @Override
    protected String[] childrenNamesSpi() throws BackingStoreException {
        if (stored != null) {
            return stored.childrenNames();
        } else {
            return new String[0];
        }
    }

    @Override
    protected AbstractPreferences childSpi(String name) {
        Preferences storedNode = stored != null ? stored.node(name) : null;
        if (storedNode != null) {
            return new InheritedPreferences(null, storedNode);
        } else {
            return null;
        }
    }

    @Override
    protected void syncSpi() throws BackingStoreException {
        stored.sync();
    }

    @Override
    protected void flushSpi() throws BackingStoreException {
        stored.flush();
    }
    
    
}
