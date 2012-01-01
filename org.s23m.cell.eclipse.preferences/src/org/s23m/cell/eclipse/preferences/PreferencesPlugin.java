package org.s23m.cell.eclipse.preferences;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class PreferencesPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.s23m.cell.eclipse.preferences";

	// The shared instance
	private static PreferencesPlugin plugin;

	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(final BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static PreferencesPlugin getDefault() {
		return plugin;
	}

	private String getStringPreferenceValue(final String name) {
		return getPreferenceStore().getString(name);
	}

	public String getPassword() {
		return getStringPreferenceValue(RepositoryPreferences.PASSWORD);
	}

	public String getUsername() {
		return getStringPreferenceValue(RepositoryPreferences.USERNAME);
	}

	public String getDatabaseName() {
		return getStringPreferenceValue(RepositoryPreferences.DATABASE_NAME);
	}

	public String getDatabaseType() {
		return getStringPreferenceValue(RepositoryPreferences.DATABASE_TYPE);
	}

	public String getHostname() {
		return getStringPreferenceValue(RepositoryPreferences.HOSTNAME);
	}

	public int getPort() {
		return getPreferenceStore().getInt(RepositoryPreferences.PORT);
	}

	public boolean isReadOnly() {
		return getPreferenceStore().getBoolean(RepositoryPreferences.READ_ONLY);
	}

}