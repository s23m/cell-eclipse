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

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

// Change methods to instance methods, and inject this class into the RDBRepository
public class DatabaseConnectorSupport {

	private static final String TYPE_EXTENSION_ID = "org.s23m.cell.eclipse.connector.database.type";

	private static final Comparator<DatabaseConnector> BY_NAME_COMPARATOR = new Comparator<DatabaseConnector>() {
		public int compare(final DatabaseConnector lhs, final DatabaseConnector rhs) {
			final String lhsName = lhs.getName();
			final String rhsName = rhs.getName();
			return lhsName.compareTo(rhsName);
		}
	};

	public static List<DatabaseConnector> getAvailableConnectors() {
		final List<DatabaseConnector> result = new ArrayList<DatabaseConnector>();

		final IConfigurationElement[] config = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(TYPE_EXTENSION_ID);
		try {
			for (final IConfigurationElement e : config) {
				final Object o = e.createExecutableExtension("class");
				if (o instanceof DatabaseConnector) {
					final ISafeRunnable runnable = new ISafeRunnable() {
						public void handleException(final Throwable exception) {
							System.out.println("Exception in client");
						}

						public void run() throws Exception {
							final DatabaseConnector connector = (DatabaseConnector) o;
							result.add(connector);
						}
					};
					SafeRunner.run(runnable);
				}
			}
		} catch (final CoreException ex) {
			System.out.println(ex.getMessage());
		}

		Collections.sort(result, BY_NAME_COMPARATOR);
		return result;
	}

	public static String testConnection(final DatabaseConnector connector,
			final String hostname,
			final int port,
			final String databaseName,
			final String username,
			final String password) {
		final Configuration configuration = buildConfiguration(connector, hostname, port, databaseName, username, password);
		if (configuration == null) {
			return "Could not make connection";
		}
		try {
			final Connection connection = getConnection(configuration);
			final Statement statement = connection.createStatement();
			final String testSql = connector.getTestSqlStatement();
			statement.execute(testSql);
			return null;
		} catch (final Exception e) {
			return e.getCause().getMessage();
		}
	}

	public static DatabaseConnector getConnectorByName(final String name) {
		if (name == null) {
			return null;
		}
		final List<DatabaseConnector> list = getAvailableConnectors();
		for (final DatabaseConnector connector : list) {
			if (name.equals(connector.getName())) {
				return connector;
			}
		}
		return null;
	}

	public static Configuration buildConfiguration(final DatabaseConnector connector,
			final String hostname,
			final int port,
			final String databaseName,
			final String username,
			final String password) {

		try {
			final Configuration config = new Configuration();
			config.setProperty("hibernate.dialect", connector.getDialect());
			config.setProperty("hibernate.connection.driver_class", connector.getDriverClass());
			config.setProperty("hibernate.connection.url", connector.createConnectionUrl(hostname, port, databaseName));
			config.setProperty("hibernate.connection.username", username);
			config.setProperty("hibernate.connection.password", password);
			config.setProperty("hibernate.default_schema", databaseName);
			config.setProperty("hibernate.jdbc.batch_size", "50");
			config.setProperty("hibernate.show_sql", "false");
			return config;
		} catch (final Exception e) {
			return null;
		}
	}

	public static DatabaseConnector getDefaultConnector() {
		return new DatabaseConnectorSupport().new GenericConnector()	{
					private static final String CONNECTION_URL_PREFIX = "jdbc:db2j:net://";
					@Override
					public String createConnectionUrl(final String hostname, final int port, final String databaseName) {
						return CONNECTION_URL_PREFIX + hostname + ":" + port + "/" + databaseName;
					}
		};
	}

	@SuppressWarnings("deprecation")
	private static Connection getConnection(final Configuration configuration) {
		final SessionFactory factory = configuration.buildSessionFactory();

		final Session session = factory.openSession();
		return session.connection();
	}

	class GenericConnector extends AbstractDatabaseConnector {

		private static final String DRIVER_CLASS = "com.ibm.db2.jcc.DB2Driver";

		private static final String DIALECT = "org.hibernate.dialect.DB2Dialect";

		private static final int DEFAULT_PORT = 50000;

		public GenericConnector() {
			super("DB2", DRIVER_CLASS, DIALECT, DEFAULT_PORT);
		}

	}

}
