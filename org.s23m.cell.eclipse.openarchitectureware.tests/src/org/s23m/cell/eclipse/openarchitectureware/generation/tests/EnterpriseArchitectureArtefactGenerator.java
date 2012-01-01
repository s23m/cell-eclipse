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
 * The Original Code is Gmodel.
 *
 * The Initial Developer of the Original Code is
 * Sofismo AG (Sofismo).
 * Portions created by the Initial Developer are
 * Copyright (C) 2009-2011 Sofismo AG.
 * All Rights Reserved.
 *
 * Contributor(s):
 * Andrew Shewring
 * ***** END LICENSE BLOCK ***** */

package org.s23m.cell.eclipse.openarchitectureware.generation.tests;

import org.s23m.cell.Set;
import org.s23m.cell.kernel.artifactinstantiation.InstantiationSequences;

public class EnterpriseArchitectureArtefactGenerator extends AbstractHtmlGenerator {

	protected EnterpriseArchitectureArtefactGenerator(final Set set) {
		super(set);
	}

	public static void main(final String[] args) {
		// final EnterpriseArchitectureExample example = new EnterpriseArchitectureExample();
		// final Set set = example.getCentrelinkEA();
		// new EnterpriseArchitectureArtefactGenerator(set);
		org.s23m.cell.G.boot();
		InstantiationSequences.run();
		final Set set = InstantiationSequences.acmeEA;
		new EnterpriseArchitectureArtefactGenerator(set);

	}
}
