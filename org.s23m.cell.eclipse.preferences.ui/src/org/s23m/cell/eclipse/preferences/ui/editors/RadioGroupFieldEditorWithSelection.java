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

package org.s23m.cell.eclipse.preferences.ui.editors;

import java.lang.reflect.Field;

import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.swt.widgets.Composite;

/*
 * Workaround for https://bugs.eclipse.org/bugs/show_bug.cgi?id=129722
 */
public class RadioGroupFieldEditorWithSelection extends RadioGroupFieldEditor {

	private Field valueField;

	public RadioGroupFieldEditorWithSelection(final String name, final String labelText, final int numColumns,
            final String[][] labelAndValues, final Composite parent) {
        super(name, labelText, numColumns, labelAndValues, parent, false);
        try {
			valueField = RadioGroupFieldEditor.class.getDeclaredField("value");
			valueField.setAccessible(true);
		} catch (final Exception e) {
			throw new IllegalStateException("Could not find field", e);
		}
    }

	public String getSelectedValue() {
		try {
			return (String) valueField.get(this);
		} catch (final Exception e) {
			throw new IllegalStateException("Could not retrieve field value", e);
		}
	}

}
