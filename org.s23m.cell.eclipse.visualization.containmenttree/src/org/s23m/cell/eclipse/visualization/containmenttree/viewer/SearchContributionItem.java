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

import org.eclipse.jface.action.ContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IViewSite;

public class SearchContributionItem extends ContributionItem {

	private static final int WIDTH_PADDING = 3;
	private ToolItem toolItem;
	private final IViewSite viewSite;

	public SearchContributionItem(final IViewSite viewSite) {
		this.viewSite = viewSite;
	}

	@Override
	public void fill(final ToolBar parent, final int index) {
		 final Composite page = new Composite(parent, SWT.NONE);
		 final GridLayout gridLayout = new GridLayout();
		 gridLayout.numColumns = 1;
		 final GridData gridData = new GridData();
		 gridData.grabExcessHorizontalSpace = true;
		 gridData.grabExcessHorizontalSpace = true;
		 gridData.horizontalAlignment = GridData.FILL;
		 gridData.verticalAlignment = GridData.FILL;
		 gridLayout.numColumns = 1;
		 page.setLayout(gridLayout);
		 final Text searchTxt = new Text(page, SWT.SINGLE | SWT.LEFT);
		 searchTxt.addListener(SWT.KeyUp, new Listener() {
		      public void handleEvent(final Event e) {
		    	  Viewer.setSearchText(searchTxt.getText());
		      }
		 });
		 searchTxt.setLayoutData(gridData);
		 searchTxt.pack();
		 page.pack();
		 toolItem = new ToolItem(parent, SWT.SEPARATOR, index);
		 toolItem.setWidth (page.getSize().x*WIDTH_PADDING);
	     toolItem.setToolTipText("Search");
	     toolItem.setControl(page);
	     parent.pack();
	 }

	 public void run() {

	 }

}