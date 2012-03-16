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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.eclipse.draw2d.BendpointConnectionRouter;
import org.eclipse.draw2d.FanRouter;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.core.widgets.internal.PolylineArcConnection;
import org.s23m.cell.S23MKernel;
import org.s23m.cell.Set;
import org.s23m.cell.api.models.Root;
import org.s23m.cell.eclipse.visualization.graph.figures.FigureBuilder;
import org.s23m.cell.eclipse.visualization.graph.figures.S23MNodeFigure;
import org.s23m.cell.eclipse.visualization.graph.figures.S23MRouter;
import org.s23m.cell.eclipse.visualization.graph.figures.SetGraphNode;
import org.s23m.cell.eclipse.visualization.graph.figures.WorkspaceGraphNode;

public final class StructuralGraphRenderer extends AbstractGraphRenderer {

	public StructuralGraphRenderer(final Graph graph, final RenderingCoordinator coordinator, final CTabItem item) {
		super(graph, coordinator, InstanceCoordinates.STRUCTURE, item);
	}

	@Override
	protected void addHandlers() {
		addDrillDownUpHandler();
		addCursorHandler();
		setUpCoordinatesManager(this.getClass());
	}

	private void addDrillDownUpHandler() {
		// TODO change to single click and alter icon to a "hand"
		graph.addListener(SWT.MouseDoubleClick, new Listener() {
			public void handleEvent(final Event event) {
				if (!graph.getSelection().isEmpty() && graph.getSelection().get(0) instanceof SetGraphNode) {
					final SetGraphNode node = (SetGraphNode) graph.getSelection().get(0);
					final Set set = node.getSet();
					if (!set.isEqualTo(Root.root) && !set.filterInstances().isEmpty()) {
						final Set setToRender = getSetToRender();
						if (set.isEqualTo(setToRender)) {
							updateSetToRender(set.container());
						} else {
							updateSetToRender(set);
						}
						render();
					}
				} else if (!graph.getSelection().isEmpty() & graph.getSelection().get(0) instanceof WorkspaceGraphNode) {
					final WorkspaceGraphNode selectedWorkspaceNode = (WorkspaceGraphNode) graph.getSelection().get(0);
					for(final WorkspaceGraphNode node : workspaceNodes) {
						if (!node.equals(selectedWorkspaceNode)) {
							node.deSelectAsCurrentWorkspaceNode();
						}
					}
					selectedWorkspaceNode.setAsCurrentWorkspaceNode();
					for (final Object obj : graph.getNodes()) {
						if (obj instanceof SetGraphNode) {
							final SetGraphNode setNode  = (SetGraphNode) obj;
							if (GraphNodeCoordinates.getCoordinatesOf(
								getSetToRender().identity().uniqueRepresentationReference().toString(),
								setNode.getSet().identity().uniqueRepresentationReference().toString(),
								StructuralGraphRenderer.class) != null) {
								setNode.setVisible(true);
							} else {
								if (!setNode.equals(rootNode)) {
									setNode.setVisible(false);
								}
							}
						}
					}
				}
			}
		});
	}

	private void addCursorHandler() {
		graph.addListener(SWT.MouseMove, new Listener() {
			public void handleEvent(final Event event) {
				final Point pt = new Point(event.x, event.y);
				graph.getShell().setCursor(null);
				for (final Object o : graph.getNodes()) {
					if (o instanceof SetGraphNode) {
						final SetGraphNode node = (SetGraphNode) o;
						final Set set = node.getSet();
						final Set setToRender = getSetToRender();
						if (!set.isEqualTo(setToRender) && set.filterInstances().size() > 0) {
							if (node.getNodeFigure().containsPoint(pt)) {
								graph.getShell().setCursor(new Cursor(null, SWT.CURSOR_HAND));
								break;
							}
						}
					}
				}
			}
		});

		graph.addListener(SWT.MouseDown, new Listener() {
			public void handleEvent(final Event event) {
				System.err.println("D "+graph.dragDetect(event));
			}
		});
	}

	@Override
	public void doRender() {
		final Map<UUID, GraphNode> nodeMap = new HashMap<UUID, GraphNode>();
		final S23MNodeFigure rootFigure = FigureBuilder.buildS23MNodeFigure();
		rootFigure.setColor(Display.getCurrent().getSystemColor(ROOT_COLOR));
		final Set setToRender = getSetToRender();
		FigureBuilder.createMetaNamePair(rootFigure, setToRender);
		rootFigure.setSize(-1, -1);
		rootNode = new SetGraphNode(graph, rootFigure, setToRender, false);

		//addWorkspaceNodes();

		nodeMap.put(setToRender.identity().identifier(), rootNode);
		for (final Set set : setToRender.filterInstances()) {
			buildSetGraphNode(set, nodeMap, false);
		}

		// Edges
		final FanRouter edgeRouter = new FanRouter();
		edgeRouter.setNextRouter(new BendpointConnectionRouter());
		final Map<String, GraphConnection> connectionMap = new HashMap<String, GraphConnection>();
		for (final Set set : setToRender.filterArrows()) {
			if (set.properClass().isEqualTo(S23MKernel.coreGraphs.edge)) {
				final Set edge = set;
				final Set ee1 = edge.edgeEnds().extractFirst();
				final Set ee2 = edge.edgeEnds().extractSecond();

				final Set set1 = edge.from();
				final Set set2 = edge.to();

				boolean hasMultipleConnections = false;

				if (getSetFromMap(set1, nodeMap) != null || getSetFromMap(set2, nodeMap) != null) {
					//if either of them are not present then create a dotted vertex and connect it
					createExternalVertex(set1, set2, nodeMap);
					final Set s1 = getSetFromMap(set1, nodeMap);
					final Set s2 = getSetFromMap(set2, nodeMap);
					if (s1 != null && s2 != null) {
						final GraphConnection conn = new GraphConnection(graph, ZestStyles.CONNECTIONS_SOLID,
								nodeMap.get(s1.identity().identifier()),
								nodeMap.get(s2.identity().identifier()));
						if (connectionMap.containsKey(getConnectionKey(set1, set2, false))) {
							hasMultipleConnections = true;
						}
						connectionMap.put(getConnectionKey(set1, set2, true), conn);
						connectionMap.put(getConnectionKey(set1, set2, false), conn);
						conn.setLineColor(Display.getCurrent().getSystemColor(
								SWT.COLOR_BLACK));

						final GraphNode gNode = nodeMap.get(getSetFromMap(set1, nodeMap).identity().identifier());
						final IFigure nodeFig = gNode.getNodeFigure();
						final boolean isSelfLoop = set1.isEqualToRepresentation(set2);
						conn.getConnectionFigure().setConnectionRouter(edgeRouter);
						if (isSelfLoop) {
							final PolylineArcConnection pLine = (PolylineArcConnection) conn.getConnectionFigure();
							pLine.setConnectionRouter(new S23MRouter(nodeFig));
						}
							enableAntiAliasing(conn);
							addContainsLabel(edge, ee1, conn, false);
							addContainsLabel(edge, ee2, conn, true);
							addEdgeEndLabels(ee1, conn, false, hasMultipleConnections);
							addEdgeEndLabels(ee2, conn, true, hasMultipleConnections);
						}
				}
			}
		}
	}

	private void addWorkspaceNodes() {
		workspaceNodes = new ArrayList<WorkspaceGraphNode>();
		for (int i = 0; i < NUMBER_OF_WORKSPACES; i++) {
			final S23MNodeFigure workspaceFigure = FigureBuilder.buildS23MNodeFigure();
			workspaceFigure.setSize(-1, -1);
			FigureBuilder.createWorkspaceIndex(workspaceFigure, i);
			final WorkspaceGraphNode workspaceNode = new WorkspaceGraphNode(graph, workspaceFigure, i);
			workspaceNode.setSize(40, 20);
			if (i == 0) {
				workspaceNode.setAsCurrentWorkspaceNode();
			}
			workspaceNodes.add(workspaceNode);
		}
	}

	protected void buildSetGraphNode(final Set set, final Map<UUID, GraphNode> nodeMap, final boolean isExternal) {
		if (set.properClass().isEqualTo(S23MKernel.coreGraphs.vertex)) {
			final S23MNodeFigure nodeFigure = FigureBuilder.buildS23MNodeFigure(isExternal);
			FigureBuilder.addIcon(nodeFigure, set);
			FigureBuilder.createMetaNamePair(nodeFigure, set);
			nodeFigure.setSize(-1, -1);
			final GraphNode node = new SetGraphNode(graph, nodeFigure, set, isExternal);
			nodeMap.put(set.identity().identifier(), node);
		}
	}

	protected boolean createExternalVertex(final Set set1, final Set set2, final Map<UUID, GraphNode> nodeMap) {
		if (getSetFromMap(set1, nodeMap) != null && getSetFromMap(set2, nodeMap) != null) {
			return false;
		} else {
			if (getSetFromMap(set1, nodeMap) == null) {
				buildExternalGraphNode(set1, nodeMap);
			}
			if (getSetFromMap(set2, nodeMap) == null) {
				buildExternalGraphNode(set2, nodeMap);
			}
			return true;
		}
	}

	private void buildExternalGraphNode(final Set set, final Map<UUID, GraphNode> nodeMap) {
		buildSetGraphNode(set, nodeMap, true);
	}

	protected String getConnectionKey(final Set set1, final Set set2, final boolean reverse) {
		final String key;
		if (reverse) {
			key = set2.identity().identifier().toString() + set1.identity().identifier().toString(); //$NON-NLS-1$
		} else {
			key = set1.identity().identifier().toString() + set2.identity().identifier().toString(); //$NON-NLS-1$
		}
		return key;
	}

	protected Set getSetFromMap(final Set set, final Map<UUID, GraphNode> nodeMap) {
		Set retSet = null;
		if (nodeMap.containsKey(set.identity().identifier())) {
			retSet = ((SetGraphNode)nodeMap.get(set.identity().identifier())).getSet();
		}
		return retSet;
	}

}
