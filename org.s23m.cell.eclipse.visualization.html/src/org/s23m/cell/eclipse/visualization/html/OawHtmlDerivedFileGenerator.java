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

package org.s23m.cell.eclipse.visualization.html;

import org.eclipse.emf.mwe.core.WorkflowContext;
import org.eclipse.emf.mwe.core.WorkflowContextDefaultImpl;
import org.eclipse.emf.mwe.core.issues.Issues;
import org.eclipse.emf.mwe.core.issues.IssuesImpl;
import org.eclipse.emf.mwe.core.monitor.NullProgressMonitor;
import org.eclipse.emf.mwe.core.monitor.ProgressMonitor;
import org.eclipse.internal.xtend.expression.parser.SyntaxConstants;
import org.s23m.cell.Set;
import org.s23m.cell.eclipse.xpand2.GmodelGenerator;
import org.s23m.cell.eclipse.xpand2.InMemoryOutput;

public class OawHtmlDerivedFileGenerator {

	private static final String TEMPLATE_NAME = "template";

	private static final String TEMPLATE_FUNCTION = "main";

	private static final String QUALIFIED_TEMPLATE_FUNCTION_NAME = OawHtmlDerivedFileGenerator.class.getPackage().getName().replace(".", SyntaxConstants.NS_DELIM)
		+ SyntaxConstants.NS_DELIM
		+ TEMPLATE_NAME
		+ SyntaxConstants.NS_DELIM
		+ TEMPLATE_FUNCTION;

	public String generate(final Set set, final String templateContent) {
		final GmodelGenerator generator = new GmodelGenerator(set);
		// generator.setTemplateContent(HTMLRepresentation.html_to_artifact.identity().getPayload());
		generator.setTemplateName(QUALIFIED_TEMPLATE_FUNCTION_NAME);
		final InMemoryOutput output = new InMemoryOutput();
		generator.setOutput(output);
		try {
		final WorkflowContext ctx = new WorkflowContextDefaultImpl();
		final ProgressMonitor monitor = new NullProgressMonitor();
		final Issues issues = new IssuesImpl();
		generator.invoke(ctx, monitor, issues);
		} catch (final Exception ex) {
			System.err.println(ex);
		}
		return output.getOutput();
	}

}
