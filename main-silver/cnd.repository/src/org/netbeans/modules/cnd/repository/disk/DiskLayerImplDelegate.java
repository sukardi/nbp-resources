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
package org.netbeans.modules.cnd.repository.disk;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import org.netbeans.modules.cnd.repository.api.UnitDescriptor;
import org.netbeans.modules.cnd.repository.impl.spi.Layer;
import org.netbeans.modules.cnd.repository.impl.spi.LayerDescriptor;
import org.netbeans.modules.cnd.repository.impl.spi.LayerKey;
import org.netbeans.modules.cnd.repository.impl.spi.ReadLayerCapability;
import org.netbeans.modules.cnd.repository.impl.spi.UnitDescriptorsList;
import org.netbeans.modules.cnd.repository.impl.spi.WriteLayerCapability;
import org.openide.filesystems.FileSystem;

/**
 *
 * @author akrasny
 */
final class DiskLayerImplDelegate implements Layer {

    private static final Map<DiskLayerImpl, AtomicInteger> refCounter = new HashMap<DiskLayerImpl, AtomicInteger>();
    private static final Set<DiskLayerImpl> writableLayers = new HashSet<DiskLayerImpl>();
    private final LayerDescriptor layerDescriptor;
    private final DiskLayerImpl impl;

    public DiskLayerImplDelegate(DiskLayerImpl impl, LayerDescriptor layerDescriptor) {
        this.impl = impl;
        this.layerDescriptor = layerDescriptor;
        AtomicInteger counter = refCounter.get(impl);
        if (counter == null) {
            counter = new AtomicInteger();
            refCounter.put(impl, counter);
        }
        counter.incrementAndGet();
        if (layerDescriptor.isWritable()) {
            writableLayers.add(impl);
        }
    }

    @Override
    public Collection<LayerKey> removedTableKeySet() {
        return impl.removedTableKeySet();
    }
    
    

    @Override
    public boolean startup(int persistMechanismVersion, boolean recreate) {
        return impl.startup(persistMechanismVersion, recreate, layerDescriptor.isWritable());
    }

    @Override
    public ReadLayerCapability getReadCapability() {
        return impl.getReadCapability();
    }

    @Override
    public WriteLayerCapability getWriteCapability() {
        return layerDescriptor.isWritable() ? impl.getWriteCapability() : null;
    }

    @Override
    public LayerDescriptor getLayerDescriptor() {
        return layerDescriptor;
    }

    @Override
    public void shutdown() {
        AtomicInteger counter = refCounter.get(impl);
        if (counter.decrementAndGet() == 0) {
            if (writableLayers.remove(impl)) {
                impl.storeIndex();
            }
            impl.shutdown();
        }
    }

    @Override
    public void openUnit(int unitIdInLayer) {
        impl.openUnit(unitIdInLayer);
    }

    @Override
    public void closeUnit(int unitIdInLayer, boolean cleanRepository, Set<Integer> requiredUnits) {
        impl.closeUnit(unitIdInLayer, cleanRepository, requiredUnits, writableLayers.contains(impl));
    }


    @Override
    public UnitDescriptorsList getUnitsTable() {
        return impl.getUnitsTable();
    }

    @Override
    public List<FileSystem> getFileSystemsTable() {
        return impl.getFileSystemsTable();
    }

    @Override
    public int findMatchedFileSystemIndexInLayer(FileSystem clientFileSystem) {
        return impl.findMatchedFileSystemIndexInLayer(clientFileSystem);
    }
}
