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
 * Copyright (C) 2009-2011 Sofismo AG.
 * All Rights Reserved.
 *
 * Contributor(s):
 * Andrew Shewring
 * ***** END LICENSE BLOCK ***** */

package org.s23m.cell.xtend2.templates.tests;

import java.util.Arrays;

import junit.framework.TestCase;

import org.junit.Test;
import org.s23m.cell.Set;
import org.s23m.cell.kernel.artifactinstantiation.RunInstantiationSequence;
import org.s23m.cell.xtend2.HtmlTemplateTransformation;

public abstract class AbstractHtmlGenerationTest extends TestCase {

	private static boolean instantiationSequenceExecuted;

	@Override
	protected void setUp() throws Exception {
		if (!instantiationSequenceExecuted) {
			RunInstantiationSequence.run();
			instantiationSequenceExecuted = true;
		}
	}

	protected abstract Set provideSet();

	@Test
	public void testHtmlGeneration() {
		final Set set = provideSet();
		final HtmlTemplateTransformation htmlTransformation = new HtmlTemplateTransformation();

		try {
			htmlTransformation.apply(Arrays.asList(set));
		} catch (final Exception e) {
			fail("HTML generation failed: " + e);
		}
	}

}
