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
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.IContainer;

public class WorkspaceGraphNode extends GraphNode {

	final private int workspaceIndex;
	final protected static int DEFAULT_COLOR = SWT.COLOR_BLACK;
	boolean isSelected;

	public WorkspaceGraphNode(final IContainer graphModel, final IFigure figure, final int workspaceIndex) {
		super(graphModel, SWT.NONE, figure);
		this.workspaceIndex= workspaceIndex;
		this.setBorderColor(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
		this.setBorderWidth(2);
	}

	@Override
	protected IFigure createFigureForModel() {
		return (IFigure) getData();
	}

	public int getWorkspaceIndex() {
		return workspaceIndex;
	}

	public void setAsCurrentWorkspaceNode() {
		final GmodelNodeFigure nodeFigure = (GmodelNodeFigure) this.nodeFigure;
		nodeFigure.setBackgroundColor(new Color(Display.getCurrent(), 153, 204, 255));
		isSelected = true;
	}

	public void deSelectAsCurrentWorkspaceNode() {
		final GmodelNodeFigure nodeFigure = (GmodelNodeFigure) this.nodeFigure;
		final Display display = Display.getCurrent();
		nodeFigure.setBackgroundColor(display.getSystemColor(DEFAULT_COLOR));
		isSelected = false;
	}

	@Override
	public boolean isSelected() {
		return isSelected;
	}

}
