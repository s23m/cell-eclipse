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
 * Chul Kim
 * Andrew Shewring
 * ***** END LICENSE BLOCK ***** */

package org.s23m.cell.eclipse.preferences.ui;

import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.s23m.cell.eclipse.connector.database.DatabaseConnector;
import org.s23m.cell.eclipse.connector.database.DatabaseConnectorSupport;
import org.s23m.cell.eclipse.preferences.PreferencesPlugin;
import org.s23m.cell.eclipse.preferences.RepositoryPreferences;
import org.s23m.cell.eclipse.preferences.ui.editors.PasswordFieldEditor;
import org.s23m.cell.eclipse.preferences.ui.editors.RadioGroupFieldEditorWithSelection;

public class RepositoryPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	private RadioGroupFieldEditorWithSelection databaseTypeEditor;
	private StringFieldEditor hostNameEditor;
	private StringFieldEditor databaseNameEditor;
	private StringFieldEditor usernameEditor;
	private StringFieldEditor passwordEditor;
	private IntegerFieldEditor portEditor;
	private BooleanFieldEditor readOnlyEditor;

	private Button testConnectionButton;

	private final List<DatabaseConnector> connectors;

	public RepositoryPreferencePage() {
		super(GRID);
		connectors = DatabaseConnectorSupport.getAvailableConnectors();
	}

	@Override
	public void createFieldEditors() {
		final String[][] array = new String[connectors.size()][2];
		for (int i = 0; i < connectors.size(); i++) {
			final String name = connectors.get(i).getName();
			array[i][0] = name;
			array[i][1] = name;
		}

		final Composite parent = getFieldEditorParent();

		databaseTypeEditor = new RadioGroupFieldEditorWithSelection(RepositoryPreferences.DATABASE_TYPE, "Database &type", 1, array, parent);
		databaseTypeEditor.getSelectedValue();
		addField(databaseTypeEditor);

		hostNameEditor = new StringFieldEditor(RepositoryPreferences.HOSTNAME, "&Hostname", parent);
		addField(hostNameEditor);

		portEditor = new IntegerFieldEditor(RepositoryPreferences.PORT, "P&ort", parent);
		portEditor.setStringValue("3306");
		portEditor.setValidRange(1025, 65536);
		// TODO use default port supplied by connector
		addField(portEditor);

		databaseNameEditor = new StringFieldEditor(RepositoryPreferences.DATABASE_NAME, "Database &name", parent);
		addField(databaseNameEditor);

		usernameEditor = new StringFieldEditor(RepositoryPreferences.USERNAME, "&Username", parent);
		addField(usernameEditor);

		passwordEditor = new PasswordFieldEditor(RepositoryPreferences.PASSWORD, "&Password", parent);
		addField(passwordEditor);

		final Composite buttonGroup = new Composite(parent, SWT.NONE);
		buttonGroup.setLayoutData(new GridData());
		final GridLayout buttonLayout = new GridLayout();
		buttonLayout.marginHeight = 0;
		buttonLayout.marginWidth = 0;
		buttonGroup.setLayout(buttonLayout);

		testConnectionButton = new Button(buttonGroup, SWT.NONE);
		testConnectionButton.setText("&Test connection");
		testConnectionButton.setEnabled(true);
		testConnectionButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {

				final Display display = Display.getCurrent();
				final Runnable connectionTest = new Runnable() {
					public void run() {
						final String hostname = hostNameEditor.getStringValue();
						final int port = portEditor.getIntValue();
						final String databaseName = databaseNameEditor.getStringValue();
						final String username = usernameEditor.getStringValue();
						final String password = passwordEditor.getStringValue();
						final String connectorName = databaseTypeEditor.getSelectedValue();

						final DatabaseConnector connector = findConnectorByName(connectorName);

						final String validConnection = DatabaseConnectorSupport.testConnection(connector,
								hostname,
								port,
								databaseName,
								username,
								password);

						final String message;
						if (validConnection == null) {
							message = "The connection details are valid";
						} else {
							message = "The connection details are invalid:\n" + validConnection;
						}
						MessageDialog.openInformation(display.getActiveShell(), "Test connection", message);
					}
				};

				BusyIndicator.showWhile(display, connectionTest);
			}
		});
		final GridData testConnectionButtonData = new GridData(GridData.FILL_HORIZONTAL);
		testConnectionButtonData.heightHint = convertVerticalDLUsToPixels(IDialogConstants.BUTTON_HEIGHT);
		//removeTagData.widthHint = convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH);
		testConnectionButton.setLayoutData(testConnectionButtonData);

		readOnlyEditor = new BooleanFieldEditor(RepositoryPreferences.READ_ONLY, "&Read Only Access",parent);
		addField(readOnlyEditor);

	}

	public void init(final IWorkbench workbench) {
		final IPreferenceStore store = PreferencesPlugin.getDefault().getPreferenceStore();
		setPreferenceStore(store);
	}

	@Override
	public boolean performOk() {
		// storeValues();
		return super.performOk();
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();
	}

	private void storeValues() {
		final IPreferenceStore store = getPreferenceStore();
		store.setValue(RepositoryPreferences.DATABASE_TYPE, databaseTypeEditor.getSelectedValue());
		store.setValue(RepositoryPreferences.HOSTNAME, hostNameEditor.getStringValue());
		store.setValue(RepositoryPreferences.DATABASE_NAME, databaseNameEditor.getStringValue());
		store.setValue(RepositoryPreferences.USERNAME, usernameEditor.getStringValue());
		store.setValue(RepositoryPreferences.PASSWORD, passwordEditor.getStringValue());
		store.setValue(RepositoryPreferences.PORT, portEditor.getIntValue());
	}

	/*
	private void initializeDefaults() {
		final IPreferenceStore store = getPreferenceStore();
		checkBox1.setSelection(store
				.getDefaultBoolean(IReadmeConstants.PRE_CHECK1));
		checkBox2.setSelection(store
				.getDefaultBoolean(IReadmeConstants.PRE_CHECK2));
		checkBox3.setSelection(store
				.getDefaultBoolean(IReadmeConstants.PRE_CHECK3));
	}
	*/

	private DatabaseConnector findConnectorByName(final String connectorName) {
		for (final DatabaseConnector connector : connectors) {
			if (connector.getName().equals(connectorName)) {
				return connector;
			}
		}
		return null;
	}
}
