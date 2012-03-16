/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is S23M.
 *
 * The Initial Developer of the Original Code is
 * The S23M Foundation.
 * Portions created by the Initial Developer are
 * Copyright (C) 2012 The S23M Foundation.
 * All Rights Reserved.
 *
 * Contributor(s):
 * Andrew Shewring
 * ***** END LICENSE BLOCK ***** */

package org.s23m.cell.eclipse.connector.database.db2;

import org.s23m.cell.eclipse.connector.database.AbstractDatabaseConnector;

public final class DB2Connector extends AbstractDatabaseConnector {

	private static final String DRIVER_CLASS = "com.ibm.db2.jcc.DB2Driver";

	private static final String DIALECT = "org.hibernate.dialect.DB2Dialect";

	private static final int DEFAULT_PORT = 50000;

	private static final String CONNECTION_URL_PREFIX = "jdbc:db2j:net://";

	public DB2Connector() {
		super("DB2", DRIVER_CLASS, DIALECT, DEFAULT_PORT);
	}

	/**
	 * Example URL: jdbc:db2j:net://localhost:50000/S23M
	 */
	@Override
	public String createConnectionUrl(final String hostname, final int port, final String databaseName) {
		return CONNECTION_URL_PREFIX + hostname + ":" + port + "/" + databaseName;
	}

}
