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

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.s23m.cell.eclipse.visualization.containmenttree.viewer.model.ContainmentTreeNode;

public class TreeContentProvider implements ITreeContentProvider {

	public Object[] getChildren(final Object parentElement) {
		final ContainmentTreeNode node = (ContainmentTreeNode) parentElement;
		return node.getChildNodes();

	}

	public Object getParent(final Object element) {
		final ContainmentTreeNode node = (ContainmentTreeNode) element;
		return node.getParent();
	}

	public boolean hasChildren(final Object element) {
		final ContainmentTreeNode node = (ContainmentTreeNode) element;
		return node.hasChildren();
	}

	public Object[] getElements(final Object inputElement) {
		return ContainmentTreeManager.getTreeManager().getRoot();
	}

	public void dispose() {

	}

	public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
	}

}
