/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
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
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
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
 */

package org.netbeans.modules.j2ee.dd.api.common;
/**
 * Generated interface for EnvEntry element.
 *
 *<p><b><font color="red"><em>Important note: Do not provide an implementation of this interface unless you are a DD API provider!</em></font></b>
 *</p>
 */
public interface EnvEntry extends CommonDDBean, DescriptionInterface {

    public static final String ENV_ENTRY_NAME = "EnvEntryName";	// NOI18N
    public static final String ENV_ENTRY_TYPE = "EnvEntryType";	// NOI18N
    public static final String ENV_ENTRY_VALUE = "EnvEntryValue";	// NOI18N

        /** Setter for env-entry-name property.
         * @param value property value
         */
	public void setEnvEntryName(String value);
        /** Getter for env-entry-name property.
         * @return property value 
         */
	public String getEnvEntryName();
        /** Setter for env-entry-type property.
         * @param value property value
         */
	public void setEnvEntryType(String value);
        /** Getter for env-entry-type property.
         * @return property value 
         */
	public String getEnvEntryType();
        /** Setter for env-entry-value property.
         * @param value property value
         */
	public void setEnvEntryValue(String value);
        /** Getter for env-entry-value property.
         * @return property value 
         */
	public String getEnvEntryValue();

        // Java EE 5
        
        void setMappedName(String value) throws VersionNotSupportedException;
	String getMappedName() throws VersionNotSupportedException;
	void setInjectionTarget(int index, InjectionTarget valueInterface) throws VersionNotSupportedException;
	InjectionTarget getInjectionTarget(int index) throws VersionNotSupportedException;
	int sizeInjectionTarget() throws VersionNotSupportedException;
	void setInjectionTarget(InjectionTarget[] value) throws VersionNotSupportedException;
	InjectionTarget[] getInjectionTarget() throws VersionNotSupportedException;
	int addInjectionTarget(InjectionTarget valueInterface) throws VersionNotSupportedException;
	int removeInjectionTarget(InjectionTarget valueInterface) throws VersionNotSupportedException;
	InjectionTarget newInjectionTarget() throws VersionNotSupportedException;

}
