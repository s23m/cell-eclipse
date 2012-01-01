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

package org.s23m.cell.eclipse.openarchitectureware.tests;

import org.eclipse.xtend.typesystem.Feature;
import org.junit.Test;

public class KernelValuesTypeTest extends AbstractTypeTest {

	@Test
	public void testComplenessOfContributedFeatures() throws Exception {
		final Feature[] contributedFeatures = kernelValuesType.getContributedFeatures();
		assertNotNull(contributedFeatures);
		assertTrue(contributedFeatures.length > 0);
		// TODO add more tests
	}

}
