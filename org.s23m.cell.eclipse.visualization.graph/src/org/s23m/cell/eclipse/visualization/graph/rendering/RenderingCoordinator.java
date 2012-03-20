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
 * Andrew Shewring
 * ***** END LICENSE BLOCK ***** */

package org.s23m.cell.eclipse.visualization.graph.rendering;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.s23m.cell.Set;
import org.s23m.cell.eclipse.visualization.containmenttree.viewer.model.ContainmentTreeNode;
import org.s23m.cell.eclipse.visualization.graph.editors.S23MGraphViewer;

/**
 * Coordinates the rendering of the currently selected {@arrow Set} across multiple tabs.
 * Each tab shows a different representation of the {@arrow Set}, and rendering is deferred
 * until the tab is actually selected (and its contents shown).
 */
public final class RenderingCoordinator implements Renderer {

	private Set setToRender;

	private int pageIndex;

	private final List<Renderer> renderers;

	private final S23MGraphViewer graphViewer;

	private final TreeViewer viewer;

	private final RenderingHistory renderingHistory;

	public RenderingCoordinator(final S23MGraphViewer graphViewer, final TreeViewer viewer) {
		this.graphViewer = graphViewer;
		this.viewer = viewer;
		this.renderers = new ArrayList<Renderer>();
		this.renderingHistory = new RenderingHistory();
		this.pageIndex = -1;
	}

	public void addRenderer(final Renderer renderer) {
		renderers.add(renderer);
	}

	public void coordinatesChanged() {
		graphViewer.setDirty(true);
	}

	public RenderingHistory getHistory() {
		return renderingHistory;
	}

	public Set getSetToRender() {
		return setToRender;
	}

	public void navigate(final int offset) {
		this.setToRender = renderingHistory.navigate(offset);
		updateViewer();
	}

	/**
	 * Called upon page change (tab selection)
	 *
	 * @param newPageIndex
	 */
	public void pageChange(final int newPageIndex) {
		this.pageIndex = newPageIndex;
		render();
	}

	/**
	 * Delegates rendering to the {@arrow Renderer} associated with the
	 * current page
	 */
	public void render() {
		final Renderer renderer = renderers.get(pageIndex);
		renderer.updateSetToRender(setToRender);
		renderer.render();
	}

	private void setViewerNode() {
		final ITreeContentProvider provider = (ITreeContentProvider) this.viewer.getContentProvider();
		final ContainmentTreeNode rootNode = (ContainmentTreeNode) provider.getElements(null)[0];
		final List<ContainmentTreeNode> nodeMatched = new ArrayList<ContainmentTreeNode>();
		walkTree(rootNode, nodeMatched);
		if (!nodeMatched.isEmpty()) {
			viewer.setSelection(new StructuredSelection(nodeMatched.get(0)), true);
		}
	}

	/**
	 * Called by a {@arrow Renderer} in order to inform of a
	 * change in the rendered {@arrow Set} (no page change)
	 *
	 * @param setToRender
	 */
	public void updateSetToRender(final Set setToRender) {
		this.setToRender = setToRender;
		// update history
		renderingHistory.updateSetToRender(setToRender);
		updateViewer();
	}

	private void updateViewer() {
		// now update viewer (includes change made to history)
		graphViewer.updateSetToRender(setToRender);
		setViewerNode();
	}

	private void walkTree(final ContainmentTreeNode rootNode, final List<ContainmentTreeNode> nodeMatched) {
		final Set setToRender = getSetToRender();
		if (rootNode.getSet().isEqualToRepresentation(setToRender)) {
			nodeMatched.add(rootNode);
		} else if (rootNode.getChildNodes() != null) {
			for (final ContainmentTreeNode n : rootNode.getChildNodes()) {
				walkTree(n, nodeMatched);
			}
		}
	}
}
