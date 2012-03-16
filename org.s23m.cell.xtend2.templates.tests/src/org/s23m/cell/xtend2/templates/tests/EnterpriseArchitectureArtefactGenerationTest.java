/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1
 *
import org.s23m.cell.eclipse.api.Set;
import org.s23m.cell.eclipse.test.artifactinstantiation.TestSequence;
the "License"); you may not use this file except in compliance with
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
 * Copyright (C) 2009-2011 Sofismo AG.
 * All Rights Reserved.
 *
 * Contributor(s):
 * Andrew Shewring
 * ***** END LICENSE BLOCK ***** */

package org.s23m.cell.xtend2.templates.tests;

import org.s23m.cell.Set;
import org.s23m.cell.kernel.artifactinstantiation.InstantiationSequences;

public class EnterpriseArchitectureArtefactGenerationTest extends AbstractHtmlGenerationTest {

	@Override
	protected Set provideSet() {
		return InstantiationSequences.getInstance().acmeEA;
	}
}
