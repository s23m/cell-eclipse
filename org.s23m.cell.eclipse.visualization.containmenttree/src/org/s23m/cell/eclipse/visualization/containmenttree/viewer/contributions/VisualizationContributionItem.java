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

package org.s23m.cell.eclipse.visualization.containmenttree.viewer.contributions;

import java.util.Collections;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.handlers.IHandlerService;

public class VisualizationContributionItem extends ContributionItem {

	private static final String MENU_TEXT = "Visualize";
	private final IViewSite viewSite;
	private final IHandler handler;
	private MenuItem menuItem;
	private ToolItem toolItem;

	public VisualizationContributionItem(final IViewSite viewSite, final IHandler handler) {
		this.handler = handler;
		this.viewSite = viewSite;
	}

	@Override
	public void fill(final Menu menu, final int index) {
		menuItem = new MenuItem(menu, SWT.NONE, index);
		menuItem.setText(MENU_TEXT);
		menuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				run();
			}
		});
	}

	@Override
	public void fill(final ToolBar parent, final int index) {
		toolItem = new ToolItem(parent, SWT.NONE, index);
		toolItem.setToolTipText(MENU_TEXT);
		toolItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				run();
			}
		});
	}

	public void run() {
		final IHandlerService handlerService = (IHandlerService) viewSite
				.getService(IHandlerService.class);
		final IEvaluationContext evaluationContext = handlerService
				.createContextSnapshot(true);
		final ExecutionEvent event = new ExecutionEvent(null, Collections.EMPTY_MAP,
				null, evaluationContext);

		try {
			handler.execute(event);
		} catch (final ExecutionException ex) {
			java.util.logging.Logger.getLogger("global").log(
					java.util.logging.Level.SEVERE, null, ex);
		}
	}

}
