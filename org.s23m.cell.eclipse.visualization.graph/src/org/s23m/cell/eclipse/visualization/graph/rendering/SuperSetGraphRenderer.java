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
 * Andrew Shewring
 * ***** END LICENSE BLOCK ***** */

package org.s23m.cell.eclipse.visualization.graph.rendering;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Display;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphNode;
import org.s23m.cell.S23MKernel;
import org.s23m.cell.Set;
import org.s23m.cell.eclipse.visualization.graph.figures.FigureBuilder;
import org.s23m.cell.eclipse.visualization.graph.figures.S23MNodeFigure;

public final class SuperSetGraphRenderer extends AbstractGraphRenderer {

	public SuperSetGraphRenderer(final Graph graph, final RenderingCoordinator coordinator, final CTabItem item) {
		super(graph, coordinator, InstanceCoordinates.REUSE, item);
	}

	@Override
	protected void addHandlers() {
		setUpCoordinatesManager(this.getClass());
	}

	@Override
	protected void doRender() {
		final Map<UUID, GraphNode> nodeMap = new HashMap<UUID, GraphNode>();
		final S23MNodeFigure rootFigure = FigureBuilder.buildS23MNodeFigure();
		rootFigure.setColor(Display.getCurrent().getSystemColor(ROOT_COLOR));

		final Set setToRender = getSetToRender();
		FigureBuilder.createMetaNamePair(rootFigure, setToRender);
		rootFigure.setSize(-1, -1);

		for (final Set set : setToRender.filterArrows()) {
			if (set.properClass().isEqualTo(S23MKernel.coreGraphs.superSetReference)) {
				final Set superRef = set;

				final Set superSet = superRef.to();
				final Set subSet = superRef.from();
				createGraphNode(nodeMap, superSet, isPartOfContainmentSet(setToRender, superSet));
				createGraphNode(nodeMap, subSet, isPartOfContainmentSet(setToRender, subSet));

				FigureBuilder.buildSuperSetReferenceConnection(graph,
						nodeMap.get(subSet.identity().identifier()),
						nodeMap.get(superSet.identity().identifier()));

			}
		}
	}
}
