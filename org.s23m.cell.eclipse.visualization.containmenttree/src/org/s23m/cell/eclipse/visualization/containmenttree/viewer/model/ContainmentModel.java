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

package org.s23m.cell.eclipse.visualization.containmenttree.viewer.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.s23m.cell.S23MKernel;
import org.s23m.cell.Set;
import org.s23m.cell.api.models.Root;

public class ContainmentModel {

	private final Map<String, List<Set>> setRegistry;
	private final ContainmentTreeNode rootNode;

	public ContainmentModel() {
		setRegistry = new HashMap<String, List<Set>>();
		rootNode = setUpTree();
	}

	private ContainmentTreeNode setUpTree() {
		final ContainmentTreeNode root = new ContainmentTreeNode(null, Root.root);
		setUpContentTree(root);
		return root;
	}

	private void setUpContentTree(final ContainmentTreeNode rootNode) {
		final Set rootSet = rootNode.getSet();
		for (final Set set : rootSet.filterInstances()) {
			if (isSerializableInstance(set) && set.properClass().isEqualTo(S23MKernel.coreGraphs.vertex)) {
				final ContainmentTreeNode node = new ContainmentTreeNode(
						rootNode, set);
				setUpVertices(node);
				rootNode.addToChildNodes(node);
			}
		}
	}

	private void setUpVertices(final ContainmentTreeNode node) {
		final Set set = node.getSet();
		for (final Set s : set.filterInstances()) {
			if (s.properClass().isEqualTo(S23MKernel.coreGraphs.vertex)) {
				final ContainmentTreeNode vertexNode = new ContainmentTreeNode(node, s);
				node.addToChildNodes(vertexNode);
				setUpVertices(vertexNode);
			}
		}
	}

	private void buildTree(final Set set) {
		for (final Set s : set.filterInstances()) {
			if (!s.identity().isPartOfKernel()
					&& !s.category().identity().isPartOfKernel()
					&& s.properClass().isEqualTo(S23MKernel.coreGraphs.vertex)) {
				final String key = s.category().identity()
						.uniqueRepresentationReference().toString();
				if (!setRegistry.containsKey(key)) {
					setRegistry.put(key, new ArrayList<Set>());
				}
				setRegistry.get(key).add(s);
			}
		}
	}

	private void setUpTopSets(final ContainmentTreeNode root) {
		for (final Set s : Root.root.filterInstances()) {
			if (!s.identity().isPartOfKernel()
					&& s.category().identity().isPartOfKernel()
					&& s.properClass().isEqualTo(S23MKernel.coreGraphs.vertex)) {
				final ContainmentTreeNode node = new ContainmentTreeNode(root,
						s);
				root.addToChildNodes(node);
				final String key = s.identity()
						.uniqueRepresentationReference().toString();
				for (final Set set : node.getSet().filterInstances()) {
					if (set.properClass().isEqualTo(S23MKernel.coreGraphs.vertex)) {
						final ContainmentTreeNode vertexNode = new ContainmentTreeNode(
								node, set);
						node.addToChildNodes(vertexNode);
					}
				}
				if (setRegistry.containsKey(key)) {
					final List<Set> instances = setRegistry.get(key);
					for (final Set instance : instances) {
						final ContainmentTreeNode instanceNode = new ContainmentTreeNode(
								node, instance);
						node.addToChildNodes(instanceNode);
						setUpLowerSets(instanceNode);
					}
				}
			}
		}
	}

	// taken from org.s23m.cell.serialization project
	private static boolean isSerializableInstance(final Set set) {
		return !set.identity().isPartOfKernel() ||
		set.identity().isEqualToRepresentation(Root.cellengineering.identity()) ||
		set.identity().isEqualToRepresentation(Root.semanticdomains.identity()) ||
		set.identity().isEqualToRepresentation(Root.models.identity()) ||
		set.container().identity().isEqualToRepresentation(Root.models.identity());
	}

	private void setUpLowerSets(final ContainmentTreeNode parentNode) {
		final String key = parentNode.getSet().identity()
				.uniqueRepresentationReference().toString();
		for (final Set set : parentNode.getSet().filterInstances()) {
			if (set.properClass().isEqualTo(S23MKernel.coreGraphs.vertex)) {
				final ContainmentTreeNode node = new ContainmentTreeNode(
						parentNode, set);
				parentNode.addToChildNodes(node);
			}
		}
		if (setRegistry.containsKey(key)) {
			final List<Set> instances = setRegistry.get(key);
			for (final Set instance : instances) {
				final ContainmentTreeNode node = new ContainmentTreeNode(
						parentNode, instance);
				parentNode.addToChildNodes(node);
				setUpLowerSets(node);
			}
		}
	}

	public ContainmentTreeNode getRoot() {
		return rootNode;
	}

	public ContainmentTreeNode getContainmentTree() {
		return null;
	}

}
