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

package org.s23m.cell.eclipse.visualization.graph.rendering;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.draw2d.ConnectionEndpointLocator;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.eclipse.zest.layouts.progress.ProgressEvent;
import org.eclipse.zest.layouts.progress.ProgressListener;
import org.s23m.cell.S23MKernel;
import org.s23m.cell.Set;
import org.s23m.cell.api.models.S23MSemanticDomains;
import org.s23m.cell.eclipse.visualization.graph.figures.FigureBuilder;
import org.s23m.cell.eclipse.visualization.graph.figures.S23MNodeFigure;
import org.s23m.cell.eclipse.visualization.graph.figures.SetGraphNode;
import org.s23m.cell.eclipse.visualization.graph.figures.WorkspaceGraphNode;

public abstract class AbstractGraphRenderer extends AbstractRenderer {

	private static final String CARDINALITY_CONCATENATION = ".."; //$NON-NLS-1$

	private static final int CARDINALITY_LABEL_FROM_OWNER_DISTANCE = 20;

	private static final int LABEL_DISTANCE = 3;

	private static final int LABEL_GAP_MULTIPLIER = 10;

	protected static final int ROOT_COLOR = SWT.COLOR_GRAY;

	protected final Graph graph;

	private final CTabItem item;

	protected SetGraphNode rootNode;

	protected List<WorkspaceGraphNode> workspaceNodes;

	private final Set visualizationAspect;

	protected static final int NUMBER_OF_WORKSPACES = 2;

	private static final LayoutAlgorithm LAYOUT_ALGORITHM = new SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);

	private static final String EMPTY_GRAPH_MESSAGE = "There is nothing to display in the current view";

	private static final InstanceCoordinates instanceCoords = InstanceCoordinates.getInstance();

	public AbstractGraphRenderer(final Graph graph, final RenderingCoordinator coordinator, final String visualizationAspect, final CTabItem item) {
		super(coordinator);
		this.graph = graph;
		this.item = item;
		this.visualizationAspect = InstanceCoordinates.getInstance().getVisualizationVertexByName(visualizationAspect);
		setUpGraph(graph);
	}

	private void addCardinalityLabel(final String min, final String max, final GraphConnection conn, final boolean isEnd,
									final boolean hasMultipleConnections, final boolean edgeTextExits) {
		final String NA = S23MSemanticDomains.is_NOTAPPLICABLE.identity().name();
		if (!min.equals(NA) && !max.equals(NA)) {
			final Label cardianlityLabel = new Label(min + CARDINALITY_CONCATENATION + max);
			cardianlityLabel.setForegroundColor(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
			final ConnectionEndpointLocator endPointLocator = new ConnectionEndpointLocator(conn.getConnectionFigure(), isEnd);
			conn.getConnectionFigure().add(cardianlityLabel, endPointLocator);
			// bring the labels closer to the connector if there are no edge labels
			final int gapMulti = (edgeTextExits) ? LABEL_GAP_MULTIPLIER: 1;
			endPointLocator.setUDistance(CARDINALITY_LABEL_FROM_OWNER_DISTANCE);
			if (hasMultipleConnections) {
				endPointLocator.setVDistance(LABEL_DISTANCE * gapMulti);
			} else {
				endPointLocator.setVDistance(-LABEL_DISTANCE * gapMulti);
			}
		}
	}

	protected void addContainsLabel(final Set edge, final Set ee, final GraphConnection conn, final boolean isEnd) {
		final Set metaSet = edge.category();
		boolean hasMetaContainment = false;
		if (metaSet.properClass().isEqualTo(S23MKernel.coreGraphs.edge) && !metaSet.identity().isPartOfKernel()) {
			final Set metaEdge = metaSet;
			final int eeIndex = (isEnd) ? 1 : 0;
			final Set mEE;
			if (eeIndex == 0) {
				mEE = metaEdge.edgeEnds().extractFirst();
			} else {
				mEE = metaEdge.edgeEnds().extractSecond();
			}

			if (mEE.value(S23MSemanticDomains.isContainer).isEqualTo(
					S23MSemanticDomains.isContainer_TRUE)) {
				hasMetaContainment = true;
			}
		}
		if (ee.value(S23MSemanticDomains.isContainer).isEqualTo(
				S23MSemanticDomains.isContainer_TRUE)) {
			FigureBuilder.addContainmentArrow(conn, hasMetaContainment);
		}
	}

	protected void addEdgeEndLabels(final Set ee, final GraphConnection conn, final boolean isEnd, final boolean hasMultipleConnections) {
		String eeText = ""; //$NON-NLS-1$
		if (!ee.identity().name().equals((S23MSemanticDomains.anonymous.identity().name()))) {
			eeText = ee.identity().name();
			final Label containLabel = new Label(eeText);
			containLabel.setFont(FigureBuilder.containsLabelFont());
			final ConnectionEndpointLocator endPt = new ConnectionEndpointLocator(
					conn.getConnectionFigure(), isEnd);
			conn.getConnectionFigure().add(containLabel, endPt);
			endPt.setUDistance(LABEL_DISTANCE);
			endPt.setVDistance(-LABEL_DISTANCE);
			if (hasMultipleConnections) {
				endPt.setUDistance(LABEL_DISTANCE);
				endPt.setVDistance(LABEL_DISTANCE);
			}
		}
		final String iMin = getValueOf(ee, S23MSemanticDomains.minCardinality);
		final String iMax = getValueOf(ee, S23MSemanticDomains.maxCardinality);
		addCardinalityLabel(iMin, iMax, conn, isEnd, hasMultipleConnections, !eeText.trim().equals("")); //$NON-NLS-1$
	}

	protected abstract void addHandlers();

	@Override
	protected void afterRendering() {
		if (graph.getNodes().isEmpty()) {
			final CTabFolder folder = item.getParent();
			final Text text = new Text(folder, SWT.MULTI);
		    text.setText(EMPTY_GRAPH_MESSAGE);
		    text.setEditable(false);
		    item.setControl(text);
		} else {
			item.setControl(graph.getParent());
		    graph.applyLayout();
		}
	}

	@Override
	protected void beforeRendering() {
		clearGraph();
	}

	private void clearGraph() {
		for (final Object object : graph.getConnections().toArray()) {
			((GraphConnection) object).dispose();
		}
		for (final Object object : graph.getNodes().toArray()) {
			((GraphNode) object).dispose();
		}
	}

	protected void createGraphNode(final Map<UUID, GraphNode> nodeMap, final Set set, final boolean isPartOfContainmentSet) {
		if (!nodeMap.containsKey(set.identity().identifier())) {
			final S23MNodeFigure nodeFigure = FigureBuilder.buildS23MNodeFigure(!isPartOfContainmentSet);
			FigureBuilder.createMetaNamePair(nodeFigure, set);
			nodeFigure.setSize(-1, -1);
			final GraphNode node = new SetGraphNode(graph, nodeFigure, set, isPartOfContainmentSet);
			nodeMap.put(set.identity().identifier(), node);
		}
	}

	@Override
	protected abstract void doRender();

	protected void enableAntiAliasing(final GraphConnection connection) {
		final PolylineConnection pLine = (PolylineConnection) connection.getConnectionFigure();
		pLine.setAntialias(SWT.ON);
	}

	private String getValueOf(final Set set, final Set valueSet) {
		return set.value(valueSet).identity().name();
	}

	protected Set getVisualizationAspect() {
		return visualizationAspect;
	}

	protected boolean isPartOfContainmentSet(final Set containmentSet, final Set set) {
		return containmentSet.filterInstances().containsSemanticMatch(set);
	}

	protected <T extends Renderer> void setUpCoordinatesManager(final Class<T> renderer) {
		graph.addPaintListener(new PaintListener(){
			public void paintControl(final PaintEvent e) {
				if (rootNode != null) {
					rootNode.setLocation(0, 0);
				}
				if (graph.getSelection().size() > 0) {
					if (graph.getSelection().get(0) instanceof SetGraphNode) {
						final SetGraphNode setNode= (SetGraphNode) graph.getSelection().get(0);
						if (setNode.isExternalSet()) {
							System.err.println("");
						}
						//Update coordinates whenever a node is moved
						if (setNode.isVisible()) {
							if (instanceCoords.hasCoordinatesOf(setNode.getSet(), getSetToRender(), visualizationAspect)) {
								final Set vizArtifact = instanceCoords.getVisualizedArtifactInstance(getSetToRender());
								instanceCoords.updateCoordinatesOf(setNode.getSet(), setNode.getLocation(), vizArtifact, visualizationAspect);
								getCoordinator().coordinatesChanged();
							}
						}
					}
				}
			}
	    });

		graph.getLayoutAlgorithm().addProgressListener(new ProgressListener() {
			private void initNodeLocation() {
				for (final Object node : graph.getNodes()) {
					if (node instanceof SetGraphNode) {
						final SetGraphNode gNode = (SetGraphNode) node;
						final Set vizArtifact = (instanceCoords.hasVisualizedArtifactInstance(getSetToRender())) ?
								 instanceCoords.getVisualizedArtifactInstance(getSetToRender()) :
									 instanceCoords.addArtifactInstanceToVisualize(getSetToRender());
						try {
							if (instanceCoords.hasCoordinatesOf(gNode.getSet(), getSetToRender(), visualizationAspect)) {
								final Point nodeLocation = instanceCoords.getCoordinatesOf(gNode.getSet(), vizArtifact, visualizationAspect);
								gNode.setLocation(nodeLocation.x, nodeLocation.y);
								gNode.setVisible(true);
							} else {
								instanceCoords.addInstanceToVisualize(gNode.getSet(), vizArtifact, visualizationAspect);
								instanceCoords.updateCoordinatesOf(gNode.getSet(), gNode.getLocation(),vizArtifact, visualizationAspect);
								gNode.setVisible(true);
							}
						} catch (final IllegalStateException ex) {
							Logger.getLogger("global").log(Level.WARNING, null, ex);
						}
					}
				}
			}

			public void progressEnded(final ProgressEvent e) {
					initNodeLocation();
			}

			public void progressStarted(final ProgressEvent e) {
			}

			public void progressUpdated(final ProgressEvent e) {
			}
		}
		);

	}

	private void setUpGraph(final Graph graph) {
		graph.setLayoutAlgorithm(LAYOUT_ALGORITHM, true);
		addHandlers();
	}

	private <T extends Renderer> void turnAllConnectedNodesClear(final int workspaceIndex, final SetGraphNode setNode, final Class<T> renderer) {
		setNode.setVisible(false);
		final String artifactUUID = setNode.getSet().container().identity().uniqueRepresentationReference().toString();
		final String setUUID = setNode.getSet().identity().uniqueRepresentationReference().toString();
		if (GraphNodeCoordinates.hasCoordinatesOf(artifactUUID, setUUID, renderer)) {
			//remove coords and give a new location
			GraphNodeCoordinates.removeCoordinatesOf(artifactUUID, setUUID, renderer);
			final Random random = new Random();
			final int rndX = random.nextInt(workspaceNodes.get(0).getLocation().x - setNode.getNodeFigure().getBounds().width);
			setNode.setLocation(rndX, 300);
			GraphNodeCoordinates.addSet(artifactUUID, setUUID, setNode.getLocation(), renderer);
		}
		final List conns = new ArrayList(setNode.getSourceConnections());
		conns.addAll(setNode.getTargetConnections());
		for (final Object obj : conns) {
			final GraphConnection conn = (GraphConnection) obj;
			conn.setVisible(false);
			final SetGraphNode srcNode = (SetGraphNode) conn.getSource();
			final SetGraphNode destNode = (SetGraphNode) conn.getDestination();
			if (srcNode.isVisible() != false) {
				turnAllConnectedNodesClear(workspaceIndex, srcNode, renderer);
			}
			if (destNode.isVisible() != false) {
				turnAllConnectedNodesClear(workspaceIndex, destNode, renderer);
			}
		}
	}

}
