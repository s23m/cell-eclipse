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

package org.s23m.cell.eclipse.visualization.containmenttree.viewer.model;

import java.util.ArrayList;
import java.util.List;

import org.s23m.cell.Set;

public class ContainmentTreeNode {

	private ContainmentTreeNode parent;

	private final Set set;

	private final List<ContainmentTreeNode> children;

	public  ContainmentTreeNode(final ContainmentTreeNode parent, final Set set) {
		this.parent = parent;
		this.set = set;
		this.children = new ArrayList<ContainmentTreeNode>();
	}

	public Set getSet() {
		return set;
	}

	public ContainmentTreeNode getParent() {
		return parent;
	}

	public void setParentSet(final ContainmentTreeNode parent) {
		this.parent = parent;
	}

	public ContainmentTreeNode[] getChildNodes() {
		if (!children.isEmpty()) {
			final ContainmentTreeNode[] nodes = new ContainmentTreeNode[children.size()];
			return children.toArray(nodes);
		} else {
			return null;
		}
	}

	public void addToChildNodes(final ContainmentTreeNode node) {
		children.add(node);
	}

	public boolean hasChildren() {
		return !children.isEmpty();
	}

}
