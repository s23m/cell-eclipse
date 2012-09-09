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
 * Jorn Bettin
 * Chul Kim
 * ***** END LICENSE BLOCK ***** */

package org.s23m.cell.repository.client.server;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ConfigValues {
	private static final String BUNDLE_NAME = "org.s23m.cell.repository.client.server.configvalues"; //$NON-NLS-1$

	// TODO attempt to make configuration visible when run using embedded Jetty
	// private static final String BUNDLE_NAME = "org.s23m.cell.repository.client.configvalues"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private ConfigValues() {
	}

	public static String getString(final String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (final MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
