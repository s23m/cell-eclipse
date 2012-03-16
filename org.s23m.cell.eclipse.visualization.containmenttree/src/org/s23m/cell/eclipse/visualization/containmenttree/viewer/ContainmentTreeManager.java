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

package org.s23m.cell.eclipse.visualization.containmenttree.viewer;

import org.s23m.cell.eclipse.visualization.containmenttree.viewer.model.ContainmentModel;
import org.s23m.cell.eclipse.visualization.containmenttree.viewer.model.ContainmentTreeNode;

public class ContainmentTreeManager {

	private static ContainmentTreeManager treeManager;
	private static ContainmentModel model;

	public static ContainmentTreeManager getTreeManager() {
		if (treeManager == null) {
			treeManager = new ContainmentTreeManager();
			model = new ContainmentModel();
		}
		return treeManager;
	}

	public ContainmentTreeNode[] getRoot() {
		final ContainmentTreeNode[] root = {model.getRoot()};
		return root;
	}

	public static boolean isModelLoaded() {
		return model != null;
	}

}
