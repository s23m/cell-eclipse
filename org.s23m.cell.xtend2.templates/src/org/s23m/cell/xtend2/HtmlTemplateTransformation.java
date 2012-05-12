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
package org.s23m.cell.xtend2;

import java.util.List;

import org.s23m.cell.Set;
import org.s23m.cell.generator.ModelToTextTransformation;
import org.s23m.cell.xtend2.templates.HtmlTemplate;

public class HtmlTemplateTransformation implements ModelToTextTransformation {
	
	public CharSequence apply(List<Set> parameters) {
		if (parameters.size() != 1) {
			throw new IllegalArgumentException("Illegal number of parameters - expected 1");
		}
		final Set set = parameters.get(0);
		final HtmlTemplate html = new HtmlTemplate();
		return html.main(set);
	}
}
