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
import org.eclipse.draw2d.FanRouter;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.zest.core.widgets.internal.PolylineArcConnection;

public class GmodelRouter extends FanRouter {

	private final static int GAP_SIZE = 30;

	private final IFigure nodeFig;

	public GmodelRouter(final IFigure nodeFig) {
		this.nodeFig = nodeFig;
	}

	@Override
	protected void setEndPoints(final Connection connection) {
		if (connection instanceof PolylineArcConnection) {
			final PolylineArcConnection pLine = (PolylineArcConnection) connection;
			final PointList points = new PointList();
			final int x = nodeFig.getBounds().x;
			final int y = nodeFig.getBounds().y;
			final int w = nodeFig.getBounds().width;
			final int ht = nodeFig.getBounds().height;
			final Point p1 = new Point(x + w / 2, y + ht);
			final Point p2 = new Point(x + w / 2, y + ht + GAP_SIZE);
			final Point p3 = new Point(x - GAP_SIZE, y + ht + GAP_SIZE);
			final Point p4 = new Point(x - GAP_SIZE, y + ht / 2);
			final Point p5 = new Point(x, y + ht / 2);
			points.addPoint(p1);
			points.addPoint(p2);
			points.addPoint(p3);
			points.addPoint(p4);
			points.addPoint(p5);

			pLine.setPoints(points);
		} else {
			super.setEndPoints(connection);
		}
	}
}
