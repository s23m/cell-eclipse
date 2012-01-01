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

package org.s23m.cell.eclipse.visualization.containmenttree.viewer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;
import org.s23m.cell.Set;
import org.s23m.cell.api.models.Root;
import org.s23m.cell.eclipse.visualization.containmenttree.viewer.model.ContainmentTreeNode;

public class VisualizationInput implements IEditorInput {

	private final ContainmentTreeNode node;

	private final TreeViewer containmentTreeViewer;

	private Set setToRender;

	private String name;

	public TreeViewer getContainmentTreeViewer() {
		return containmentTreeViewer;
	}

	public ContainmentTreeNode getNode() {
		return node;
	}

	public VisualizationInput(final ContainmentTreeNode node, final TreeViewer containmentTreeViewer) {
		this.node = node;
		this.containmentTreeViewer = containmentTreeViewer;
		updateSetToRender(node.getSet());
	}

	public void updateSetToRender(final Set setToRender) {
		this.setToRender = setToRender;
		this.name = createName();
	}

	public boolean exists() {
		return false;
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getName() {
		return name;
	}

	public IPersistableElement getPersistable() {
		return null;
	}

	public String getToolTipText() {
		return name;
	}

	@SuppressWarnings("unchecked")
	public Object getAdapter(final Class adapter) {
		return null;
	}

	private String createName() {
		final List<String> artefactNames = new ArrayList<String>();
		Set set = setToRender;
		artefactNames.add(set.identity().name());
		if (set.isEqualTo(Root.root)) {
			while (set.container().isEqualTo(Root.root)) {
				set = set.container();
				artefactNames.add(set.identity().name());
			}
		}
		final StringBuilder result = new StringBuilder();
		for (int i = artefactNames.size() - 1; i >= 0; i--) {
			result.append(artefactNames.get(i));
			if (i > 0) {
				result.append(" > ");
			}
		}
		return result.toString();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final VisualizationInput other = (VisualizationInput) obj;
		if (setToRender == null) {
			if (other.setToRender != null) {
				return false;
			}
		} else if (!setToRender.equals(other.setToRender)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((setToRender == null) ? 0 : setToRender.identity().identifier().toString().hashCode());
		return result;
	}
}
