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

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class GmodelNodeFigure extends RoundedRectangle {

    private static final int ALPHA_LEVEL = 125;

    private static final Color FIGURE_COLOUR = new Color(null, 215, 213, 246);

	private final AttributeFigure attributeFigure = new AttributeFigure();

    public GmodelNodeFigure(final boolean isExternal) {
        final ToolbarLayout layout = new ToolbarLayout();
        setLayoutManager(layout);
        if (isExternal) {
    		final Display display = Display.getCurrent();
			final Color white = display.getSystemColor(SWT.COLOR_WHITE);
			setColor(white);
    		setBackgroundColor(white);
    		setForegroundColor(white);
    		setBorder(new InnerFigureBorder(true));
    	} else {
    		setBackgroundColor(FIGURE_COLOUR);
    	}
        setOpaque(true);
        add(attributeFigure);
        setAntialias(SWT.ON);
        setAlpha(ALPHA_LEVEL);
    }

	public void addToAttributesFigure(final IFigure figure) {
		attributeFigure.add(figure);
	}

	public void setColor(final Color color) {
		this.setBackgroundColor(color);
	}

}
