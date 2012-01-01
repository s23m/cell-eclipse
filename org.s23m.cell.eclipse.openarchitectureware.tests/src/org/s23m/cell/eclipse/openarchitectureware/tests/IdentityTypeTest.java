/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPimport java.lang.reflect.Method;

import org.eclipse.xtend.typesystem.Feature;
import org.eclipse.xtend.typesystem.Operation;
import org.s23m.cell.G;
import org.s23m.cell.eclipse.api.Identity;
import org.s23m.cell.eclipse.core.IdentityImpl;

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

import java.lang.reflect.Method;

import org.eclipse.xtend.typesystem.Feature;
import org.eclipse.xtend.typesystem.Operation;
import org.junit.Test;
import org.s23m.cell.Identity;
import org.s23m.cell.api.Instantiation;
import org.s23m.cell.api.models.GmodelSemanticDomains;
import org.s23m.cell.core.IdentityImpl;

public final class IdentityTypeTest extends AbstractTypeTest {

	/**
	 * Checks that all contributed {@link Feature}s correspond
	 * to operations on the {@link Identity} interface
	 *
	 * @throws Exception
	 */
	@Test
	public void testContributedFeatures() throws Exception {
		final Feature[] features = identityType.getContributedFeatures();
		assertTrue(features.length > 0);

		// this test accesses indexIsNotAvailable, which should be private, as well as the IdentityImpl(...) constructor, which is not part of the API
		// TODO the test needs to be reworked

		final Identity identity = new IdentityImpl(GmodelSemanticDomains.name.identity().name(), GmodelSemanticDomains.pluralName.identity().name(), Instantiation.indexIsNotAvailable);

		for (final Feature feature : features) {
			assertTrue(feature instanceof Operation);
			final Operation operation = (Operation) feature;
			final String name = operation.getName();
			final Method method = Identity.class.getMethod(name);
			assertNotNull(method);

			final Object evaluationResult = operation.evaluate(identity, new Object[0]);
			assertNotNull(evaluationResult);
			final Object invocationResult = method.invoke(identity);
			if (name.equals(GmodelSemanticDomains.identifier.identity().name())) {
				// feature provides a useful shortcut
				assertEquals(evaluationResult, String.valueOf(invocationResult));
			} else {
				assertEquals(evaluationResult, invocationResult);
			}
		}

	}

}
