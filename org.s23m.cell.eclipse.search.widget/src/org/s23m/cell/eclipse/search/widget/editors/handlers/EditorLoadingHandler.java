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
 * ***** END LICENSE BLOCK ***** */

package org.s23m.cell.eclipse.search.widget.editors.handlers;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.s23m.cell.eclipse.search.widget.editors.ResultsView;
import org.s23m.cell.eclipse.visualization.containmenttree.viewer.VisualizationInput;
import org.s23m.cell.eclipse.visualization.containmenttree.viewer.model.ContainmentTreeNode;

public class EditorLoadingHandler {

	private static final String VISUALIZATION_WIDGET_ID = "org.s23m.cell.eclipse.visualization.graph.editors.GmodelGraphViewer";

	public static void openVisualizationWidget(final ContainmentTreeNode node, final TreeViewer controlViewer) {
		try {
			 final IWorkbenchPage page = getActivePage();
			 page.openEditor(new VisualizationInput(node, controlViewer), VISUALIZATION_WIDGET_ID);
		} catch (final PartInitException ex) {
			java.util.logging.Logger.getLogger("global").log(
					java.util.logging.Level.SEVERE, null, ex);
		}
	}

	private static IWorkbenchPage getActivePage() {
		return ResultsView.getViewerSite().getPage();
	}

}

