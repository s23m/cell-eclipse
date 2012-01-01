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

import org.eclipse.emf.mwe.core.WorkflowContext;
import org.eclipse.emf.mwe.core.WorkflowContextDefaultImpl;
import org.eclipse.emf.mwe.core.container.CompositeComponent;
import org.eclipse.emf.mwe.core.issues.Issues;
import org.eclipse.emf.mwe.core.issues.IssuesImpl;
import org.eclipse.emf.mwe.core.monitor.NullProgressMonitor;
import org.eclipse.emf.mwe.core.monitor.ProgressMonitor;
import org.eclipse.emf.mwe.utils.DirectoryCleaner;
import org.eclipse.xpand2.output.Outlet;
import org.s23m.cell.Set;
import org.s23m.cell.eclipse.xpand2.GmodelGenerator;

/**
 * Roughly the equivalent of a basic MWE workflow
 */
public final class GmodelWorkflow {

	private final Set set;

	private final String templateName;

	public GmodelWorkflow(final Set set, final String templateName) {
		this.set = set;
		this.templateName = templateName;
	}

	public void execute() {
		final String path = "src-gen";

		final GmodelGenerator generator = new GmodelGenerator(set);
		generator.setTemplateName(templateName);
		final Outlet outlet = new Outlet();
		outlet.setPath(path);
		generator.addOutlet(outlet);

		final DirectoryCleaner directoryCleaner = new DirectoryCleaner();
		directoryCleaner.setDirectory(path);

		final WorkflowContext ctx = new WorkflowContextDefaultImpl();
		final ProgressMonitor monitor = new NullProgressMonitor();
		final Issues issues = new IssuesImpl();

		final CompositeComponent workflow = new CompositeComponent("workflow");
		workflow.addComponent(directoryCleaner);
		workflow.addComponent(generator);
		workflow.invoke(ctx, monitor, issues);
	}
}
