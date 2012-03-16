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

package org.s23m.cell.eclipse.connector.database;

public abstract class AbstractDatabaseConnector implements DatabaseConnector {

	private final String name;
	private final String driverClass;
	private final String dialect;
	private final int defaultPort;

	public AbstractDatabaseConnector(final String name,
			final String driverClass,
			final String dialect,
			final int defaultPort) {

		this.defaultPort = defaultPort;
		this.dialect = dialect;
		this.driverClass = driverClass;
		this.name = name;
	}

	public final String getName() {
		return name;
	}

	public final String getDriverClass() {
		return driverClass;
	}

	public final String getDialect() {
		return dialect;
	}

	public final int getDefaultPort() {
		return defaultPort;
	}

	public String createConnectionUrl(final String hostname, final int port, final String databaseName) {
		return "jdbc:" + name.toLowerCase() + "://" + hostname + ":" + port + "/" + databaseName;
	}

	public String getTestSqlStatement() {
		return "SELECT 1";
	}

	@Override
	public final String toString() {
		return DatabaseConnector.class.getSimpleName() + ": " + getName();
	}
}
