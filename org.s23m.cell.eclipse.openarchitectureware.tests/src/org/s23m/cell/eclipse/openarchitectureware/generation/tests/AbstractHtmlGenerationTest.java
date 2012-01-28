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

package org.s23m.cell.eclipse.openarchitectureware.generation.tests;

import junit.framework.TestCase;

import org.junit.Test;
import org.s23m.cell.Set;
import org.s23m.cell.eclipse.visualization.html.OawHtmlDerivedFileGenerator;
import org.s23m.cell.kernel.artifactinstantiation.RunInstantiationSequence;

public abstract class AbstractHtmlGenerationTest extends TestCase {

	private static boolean hasRunScript;

	@Override
	protected void setUp() throws Exception {
		if (!hasRunScript) {
			RunInstantiationSequence.run();
			hasRunScript = true;
		}
	}

	protected abstract Set provideSet();

	// TODO use try-catch?
	@Test
	public void testHtmlGeneration() {
		//final String templateName = "org::s23m::cell::eclipse::visualization::html::template::main"
		final String templateName = OawHtmlDerivedFileGenerator.QUALIFIED_TEMPLATE_FUNCTION_NAME;
		final Set set = provideSet();
		final GmodelWorkflow workflow = new GmodelWorkflow(set, templateName);
		workflow.execute();
	}

}
