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

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

public class InnerFigureBorder extends AbstractBorder {

	private static final int LINE_WIDTH = 1;
	private static final int ARC_DIMENSION = 8;
	private static final int ZERO_PADDING = 3;
	private static final int PADDING = 3;

	private final boolean isDashed;

	public InnerFigureBorder(final boolean isDashed) {
		this.isDashed = isDashed;
	}

	public void paint(final IFigure figure, final Graphics graphics, final Insets insets) {
		if (isDashed) {
			 graphics.setAntialias(SWT.ON);
			 graphics.setLineStyle(SWT.LINE_CUSTOM);
			 graphics.setForegroundColor(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GRAY));
			 graphics.setLineWidth(LINE_WIDTH);
			 graphics.setLineDash(new float[] { 5.0f, 5.0f });
			 final Rectangle rec = tempRect.setBounds(getPaintRectangle(figure, insets));
			 rec.crop(new Insets(LINE_WIDTH, LINE_WIDTH, LINE_WIDTH*2, LINE_WIDTH*2));
		     final float lineInset = Math.max(1.0f, graphics.getLineWidthFloat()) / 2.0f;
			 graphics.drawRoundRectangle(rec, Math.max(0, ARC_DIMENSION - (int) lineInset),
					 						  Math.max(0, ARC_DIMENSION - (int) lineInset));
		}
	}

	public Insets getInsets(final IFigure figure) {
		if (isDashed) {
			return new Insets(ZERO_PADDING, ZERO_PADDING, ZERO_PADDING, ZERO_PADDING);
		} else {
			return new Insets(PADDING, PADDING, PADDING, PADDING);
		}
	}

}

