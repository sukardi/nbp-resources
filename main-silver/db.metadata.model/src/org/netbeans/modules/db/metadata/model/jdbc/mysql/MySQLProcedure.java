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

package org.netbeans.modules.db.metadata.model.jdbc.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.netbeans.modules.db.metadata.model.api.MetadataElement;
import org.netbeans.modules.db.metadata.model.api.Nullable;
import org.netbeans.modules.db.metadata.model.api.Parameter.Direction;
import org.netbeans.modules.db.metadata.model.api.SQLType;
import org.netbeans.modules.db.metadata.model.jdbc.JDBCParameter;
import org.netbeans.modules.db.metadata.model.jdbc.JDBCProcedure;
import org.netbeans.modules.db.metadata.model.jdbc.JDBCSchema;
import org.netbeans.modules.db.metadata.model.jdbc.JDBCUtils;
import org.netbeans.modules.db.metadata.model.jdbc.JDBCValue;

/**
 *
 * @author David Van Couvering
 */
public class MySQLProcedure extends JDBCProcedure {
    public MySQLProcedure(JDBCSchema jdbcSchema, String name) {
        super(jdbcSchema, name);
    }

    @Override
    protected JDBCParameter createJDBCParameter(int position, ResultSet rs) throws SQLException {
        Direction direction = JDBCUtils.getProcedureDirection(rs.getShort("COLUMN_TYPE"));
        return new JDBCParameter(this.getProcedure(), createValue(rs, this.getProcedure()), direction, position);
    }

    @Override
    protected JDBCValue createJDBCValue(ResultSet rs) throws SQLException {
        return createValue(rs, this.getProcedure());
    }

    @Override
    public String toString() {
        return "MySQLProcedure[name=" + getName() + "]";
    }

    /**
     * A "special" version because MySQL returns character lengths in
     * the precision column instead of the length column - sheesh.
     *
     * Logged as a MySQL bug - http://bugs.mysql.com/bug.php?id=41269
     * When this is fixed this workaround will need to be backed out.
     */
    private static JDBCValue createValue(ResultSet rs, MetadataElement parent) throws SQLException {
        String name = rs.getString("COLUMN_NAME");

        int length = 0;
        int precision = 0;

        SQLType type = JDBCUtils.getSQLType(rs.getInt("DATA_TYPE"));
        String typeName = rs.getString("TYPE_NAME");
        if (JDBCUtils.isNumericType(type)) {
            precision = rs.getInt("PRECISION");
        } else {
            length = rs.getInt("PRECISION");
        }
        short scale = rs.getShort("SCALE");
        short radix = rs.getShort("RADIX");
        Nullable nullable = JDBCUtils.getProcedureNullable(rs.getShort("NULLABLE"));

        return new JDBCValue(parent, name, type, typeName, length, precision, radix, scale, nullable);
    }


}
