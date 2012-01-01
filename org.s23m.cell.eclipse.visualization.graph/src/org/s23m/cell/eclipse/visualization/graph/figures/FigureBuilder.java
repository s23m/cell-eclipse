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

package org.s23m.cell.eclipse.visualization.graph.figures;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.s23m.cell.Set;
import org.s23m.cell.api.models.GmodelSemanticDomains;

public class FigureBuilder {

	private static final int ANNOTATION_FONT_SIZE = 9;
	private static final String FONT_NAME = "Arial";
	private static final int FONT_SIZE = 10;
	private static final String META_SYMBOL = ":";

	public static GmodelNodeFigure buildGmodelNodeFigure() {
		return new GmodelNodeFigure(false);
	}

	public static GmodelNodeFigure buildGmodelNodeFigure(final boolean isExternal) {
		return new GmodelNodeFigure(isExternal);
	}

	private static Label createMetaLabel(final String metaElementName, final boolean isAbstract) {
		final Label metaLabel = new Label(metaElementName+META_SYMBOL);
		if (isAbstract) {
			metaLabel.setFont(new Font(null, FONT_NAME, FONT_SIZE, SWT.ITALIC));
		} else {
			metaLabel.setFont(new Font(null, FONT_NAME, FONT_SIZE, SWT.NONE));
		}
		metaLabel.setForegroundColor(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GRAY));
		return metaLabel;
	}

	private static Label createLabel(final String name) {
		final Label label = new Label(name);
		label.setFont(new Font(null, FONT_NAME, 10, SWT.NONE));
		label.setForegroundColor(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
		return label;
	}

	public static void addIcon(final GmodelNodeFigure nodeFigure, final Set set) {
		final ImageFigure imgFig = new ImageFigure(LabelIcon.ICON.getImage());
		nodeFigure.addToAttributesFigure(imgFig);
	}

	public static void createMetaNamePair(final GmodelNodeFigure nodeFigure, final Set set) {
		final boolean isAbstract = set.category().value(GmodelSemanticDomains.isAbstract).isEqualTo(GmodelSemanticDomains.isAbstract_TRUE);
		nodeFigure.addToAttributesFigure(createMetaLabel(set.category().identity().name(), isAbstract));
		nodeFigure.addToAttributesFigure(createLabel(set.identity().name()));
	}

	public static Font containsLabelFont() {
		return new Font(null, FONT_NAME, ANNOTATION_FONT_SIZE, SWT.ITALIC);
	}

	public static GraphConnection buildVisibilityConnection (final Graph graph, final GraphNode sourceNode, final GraphNode targetNode) {
		final GraphConnection conn = new GraphConnection(graph, ZestStyles.CONNECTIONS_SOLID,
				sourceNode,targetNode);
		conn.setLineColor(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
		final PolylineConnection pLine = (PolylineConnection) conn.getConnectionFigure();
		pLine.setAntialias(SWT.ON);
		pLine.setLineStyle(SWT.LINE_CUSTOM);
		pLine.setLineDash(new float[] { 5.0f, 5.0f });
		final PolygonDecoration decoration = new PolygonDecoration();
		final PointList decoPtList = new PointList();
		decoPtList.addPoint(0,0);
		decoPtList.addPoint(-1,1);
		decoPtList.addPoint(0,0);
		decoPtList.addPoint(-1,-1);
		decoration.setTemplate(decoPtList);
		decoration.setAntialias(SWT.ON);
		pLine.setTargetDecoration(decoration);
		return conn;
	}

	public static void dashConnection(final Connection conn) {
		final PolylineConnection pLine = (PolylineConnection) conn;
		pLine.setAntialias(SWT.ON);
		pLine.setLineStyle(SWT.LINE_CUSTOM);
		pLine.setLineDash(new float[] { 5.0f, 5.0f });
	}

	public static GraphConnection buildSuperSetReferenceConnection (final Graph graph, final GraphNode sourceNode, final GraphNode targetNode) {
		final GraphConnection conn = new GraphConnection(graph, ZestStyles.CONNECTIONS_SOLID,
				sourceNode,targetNode);
		conn.setLineColor(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));
		final PolylineConnection pLine = (PolylineConnection) conn.getConnectionFigure();
		pLine.setAntialias(SWT.ON);
		pLine.setLineStyle(SWT.LINE_CUSTOM);
		final PolygonDecoration decoration = new PolygonDecoration();
		final PointList decoPtLinst = new PointList();
		decoPtLinst.addPoint(-1,1);
		decoPtLinst.addPoint(0,0);
		decoPtLinst.addPoint(-1,-1);
		decoration.setTemplate(decoPtLinst);
		decoration.setAntialias(SWT.ON);
		pLine.setTargetDecoration(decoration);
		return conn;
	}

	public static void addContainmentArrow(final GraphConnection conn, final boolean hasMetaContainment) {
		final PolylineConnection pLine = (PolylineConnection) conn.getConnectionFigure();
		final int colorValue = hasMetaContainment ? SWT.COLOR_BLACK : SWT.COLOR_RED;
		pLine.setAntialias(SWT.ON);
		pLine.setLineStyle(SWT.LINE_CUSTOM);
		final PolygonDecoration decoration = new PolygonDecoration();
		final PointList decoPtList = new PointList();
		decoPtList.addPoint(0,0);
		decoPtList.addPoint(-1,1);
		decoPtList.addPoint(-2,0);
		decoPtList.addPoint(-1,-1);
		decoration.setTemplate(decoPtList);
		decoration.setAntialias(SWT.ON);
		decoration.setForegroundColor(Display.getCurrent().getSystemColor(colorValue));
		pLine.setSourceDecoration(decoration);
	}

	public static void addDirectionalArrow(final GraphConnection conn, final boolean isEnd) {
		final PolylineConnection pLine = (PolylineConnection) conn.getConnectionFigure();
		pLine.setAntialias(SWT.ON);
		pLine.setLineStyle(SWT.LINE_CUSTOM);
		final PolygonDecoration decoration = new PolygonDecoration();
		final PointList decoPtLinst = new PointList();
		decoPtLinst.addPoint(0,0);
		decoPtLinst.addPoint(-1,1);
		decoPtLinst.addPoint(0,0);
		decoPtLinst.addPoint(-1,-1);
		decoration.setTemplate(decoPtLinst);
		decoration.setAntialias(SWT.ON);
		decoration.setForegroundColor(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
		if (isEnd) {
			pLine.setTargetDecoration(decoration);
		} else {
			pLine.setSourceDecoration(decoration);
		}
	}

	public static void createWorkspaceIndex(final GmodelNodeFigure workspaceFigure, final int n) {
		final Label indexLabel = new Label(""+(n+1));
		indexLabel.setFont(new Font(null, FONT_NAME, FONT_SIZE, SWT.ITALIC));
		indexLabel.setBackgroundColor(Display.getCurrent().getSystemColor(WorkspaceGraphNode.DEFAULT_COLOR));
		workspaceFigure.setBackgroundColor(Display.getCurrent().getSystemColor(WorkspaceGraphNode.DEFAULT_COLOR));
		workspaceFigure.addToAttributesFigure(indexLabel);
	}

}
