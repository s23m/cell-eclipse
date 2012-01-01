package org.s23m.cell.eclipse.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

// FIXME move to independent plugin
public class GmodelDemoPreferencesInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		final IPreferenceStore store = PreferencesPlugin.getDefault().getPreferenceStore();
		store.setDefault(RepositoryPreferences.DATABASE_TYPE, "MySQL");
		store.setDefault(RepositoryPreferences.HOSTNAME, "gmodel.cw7d0ugpsjzw.us-east-1.rds.amazonaws.com/demo");
		store.setDefault(RepositoryPreferences.DATABASE_NAME, "demo");
		store.setDefault(RepositoryPreferences.PORT, "3306");
		store.setDefault(RepositoryPreferences.USERNAME, "gmodeldemo");
		store.setDefault(RepositoryPreferences.PASSWORD, "pyXvFCxxEuLGwAvX");
		store.setDefault(RepositoryPreferences.READ_ONLY, true);
	}

}
