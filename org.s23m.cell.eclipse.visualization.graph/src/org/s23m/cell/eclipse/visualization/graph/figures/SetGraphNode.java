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
 * Copyright (C) 2009-2010 Sofismo AG.
 * All Rights Reserved.
 *
 * Contributor(s):
 * Jorn Bettin
 * Chul Kim
 * ***** END LICENSE BLOCK ***** */

package org.s23m.cell.eclipse.visualization.graph.figures;

import org.eclipse.draw2d.IFigure;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.IContainer;
import org.s23m.cell.Set;

public class SetGraphNode extends GraphNode {

	private Set set;

	final private boolean isExternalSet;

	public SetGraphNode(final IContainer graphModel, final IFigure figure, final Set set, final boolean isExternalSet) {
		super(graphModel, SWT.NONE, figure);
		this.set = set;
		this.isExternalSet = isExternalSet;
		this.setBorderColor(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
		this.setBorderWidth(2);
	}

	@Override
	protected IFigure createFigureForModel() {
		return (IFigure) getData();
	}

	public Set getSet() {
		return set;
	}

	public boolean isExternalSet() {
		return isExternalSet;
	}

	public void setSet(final Set set) {
		this.set = set;
	}


}
