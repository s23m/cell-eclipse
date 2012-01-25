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

package org.s23m.cell.eclipse.xpand2;

import java.util.List;

import org.eclipse.emf.mwe.core.WorkflowContext;
import org.eclipse.emf.mwe.core.issues.Issues;
import org.eclipse.emf.mwe.core.lib.AbstractWorkflowComponent;
import org.eclipse.emf.mwe.core.monitor.ProgressMonitor;
import org.eclipse.xpand2.Generator;
import org.eclipse.xpand2.output.Outlet;
import org.eclipse.xpand2.output.Output;
import org.eclipse.xtend.expression.AbstractExpressionsUsingWorkflowComponent.GlobalVarDef;
import org.eclipse.xtend.expression.TypeSystemImpl;
import org.s23m.cell.Set;
import org.s23m.cell.api.Query;
import org.s23m.cell.eclipse.openarchitectureware.CellMetaModel;

/**
 * An Xpand generator component which loads a {@link Set}
 *
 * NOTE: not designed to be called from MWE workflows
 */
public class CellGenerator extends AbstractWorkflowComponent {

	private static final String MODEL_SLOT_NAME = "instanceModel";

	private static final String DEFAULT_FILE_ENCODING = "UTF-8";

	private final Generator generator;

	private final CellMetaModel metaModel;

	private String templateName;

	private final Set set;

	public CellGenerator(final Set set) {
		this.set = set;
		this.metaModel = new CellMetaModel(set);
		this.generator = new Generator();
		generator.setFileEncoding(DEFAULT_FILE_ENCODING);

		generator.addMetaModel(metaModel);

		// add TypeSystem manually, so that KernelValuesType is initialised
		metaModel.setTypeSystem(new TypeSystemImpl());
	}

	public void setTemplateName(final String templateName) {
		this.templateName = templateName;
		final String expand = templateName + " FOR " + MODEL_SLOT_NAME;
		generator.setExpand(expand);
	}

	@Override
	protected void invokeInternal(final WorkflowContext ctx, final ProgressMonitor monitor, final Issues issues) {
		// load model
		ctx.set(MODEL_SLOT_NAME, set);

		// add kernel concepts as global variables
		addKernelConcepts(ctx);

		addGlobalVariable(ctx, metaModel.getKernelValuesType(), "kernelValuesType");

		generator.invoke(ctx, monitor, issues);
	}

	private void addKernelConcepts(final WorkflowContext ctx) {
		addConcept(ctx, Query.vertex);
		addConcept(ctx, Query.edgeEnd);
		addConcept(ctx, Query.orderedSet);
		addConcept(ctx, Query.link);
		addConcept(ctx, Query.edge);
		addConcept(ctx, Query.superSetReference);
		addConcept(ctx, Query.visibility);
		addConcept(ctx, Query.graph);
	}

	private void addConcept(final WorkflowContext ctx, final Set set) {
		// final String name = set.identity().getName();
		final String name = set.identity().technicalName();
		final String variableName = "kernel" + name.substring(0, 1).toUpperCase() + name.substring(1);

		addGlobalVariable(ctx, set, variableName);
	}

	private void addGlobalVariable(final WorkflowContext ctx, final Object variable, final String variableName) {
		// populate context
		ctx.set(variableName, variable);

		// add global variable
		final GlobalVarDef globalVarDef = new GlobalVarDef();
		globalVarDef.setName(variableName);
		globalVarDef.setValue(variableName);
		generator.addGlobalVarDef(globalVarDef);
	}

	public void checkConfiguration(final Issues issues) {
		if (templateName == null) {
			issues.addError(this, "Template name must be specified");
		}

		// Check underlying Generator's configuration
		generator.checkConfiguration(issues);
	}

	/*----------------------
	    Generator methods
	 ----------------------*/

	public void addOutlet(final Outlet outlet) {
		generator.addOutlet(outlet);
	}

	public void addAdvice(final String advice) {
		generator.addAdvice(advice);
	}

	public void addExtensionAdvice(final String extensionAdvice) {
		generator.addExtensionAdvice(extensionAdvice);
	}

	public void setFileEncoding(final String fileEncoding) {
		generator.setFileEncoding(fileEncoding);
	}

	public void setBeautifier(final List<?> beautifiers) {
		generator.setBeautifier(beautifiers);
	}

	public void setOutput(final Output output) {
		generator.setOutput(output);
	}
}
