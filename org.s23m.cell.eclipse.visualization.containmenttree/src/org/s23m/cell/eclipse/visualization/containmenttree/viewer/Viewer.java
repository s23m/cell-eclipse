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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.ViewPart;
import org.s23m.cell.eclipse.visualization.containmenttree.viewer.commands.Retrieve;
import org.s23m.cell.eclipse.visualization.containmenttree.viewer.commands.StoreRoot;
import org.s23m.cell.eclipse.visualization.containmenttree.viewer.handlers.EditorLoadingHandler;
import org.s23m.cell.eclipse.visualization.containmenttree.viewer.image.TreeIcon;
import org.s23m.cell.eclipse.visualization.containmenttree.viewer.model.ContainmentTreeNode;

public class Viewer extends ViewPart {

	private static final int INDEX_OF_STORE_ITEM = 2;
	private static final int NUM_COLUMNS = 5;
	private static TreeViewer containmentTreeViewer;
	private static ToolBar btnToolbar;
	private static final String CONTEXT_MENU_GROUP_KEY = "action";
	private static String searchText;
	private static IWorkbenchPartSite viewerSite;

	public static TreeViewer getContainmentTreeViewer() {
		return containmentTreeViewer;
	}

	public static String getSearchText() {
		return searchText;
	}

	public static void setAccessType(final boolean isReadOnly) {
		if (btnToolbar != null && btnToolbar.getItemCount() > INDEX_OF_STORE_ITEM) {
			btnToolbar.getItem(INDEX_OF_STORE_ITEM).setEnabled(!isReadOnly);
		}
	}

	public static void setSearchText(final String text) {
		searchText = text;
	}

	private void buildContextMenu(final IMenuManager menuManager) {
		menuManager.add(new Separator(CONTEXT_MENU_GROUP_KEY));
		menuManager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void createContextMenu() {
		final MenuManager menuManager = new MenuManager("#PopupMenu");
		menuManager.setRemoveAllWhenShown(true);
		menuManager.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(final IMenuManager m) {
				Viewer.this.buildContextMenu(m);
			}
		});
		final Menu menu = menuManager.createContextMenu(containmentTreeViewer
				.getControl());
		containmentTreeViewer.getControl().setMenu(menu);
		containmentTreeViewer.getControl().getMenu().setVisible(false);
		getSite().registerContextMenu(menuManager, containmentTreeViewer);
	}

	@Override
	public void createPartControl(final Composite parent) {
		parent.setLayout(new FillLayout());
		createMainComposite(parent);
		viewerSite = getSite();
	}

	private void createMainComposite(final Composite parent) {
		final Composite mainComposite = new Composite(parent, SWT.NONE);
		final GridLayout mainCompositeLayout = new GridLayout();
		mainCompositeLayout.numColumns = NUM_COLUMNS;
		mainCompositeLayout.marginWidth = 0;
		mainCompositeLayout.horizontalSpacing = 0;
		mainComposite.setLayout(mainCompositeLayout);
		final SearchTextComposite searchComposite = new SearchTextComposite(mainComposite, SWT.NONE);
		final GridData btnCompositeLData = new GridData();
		btnCompositeLData.horizontalAlignment = GridData.END;
		final Composite btnComposite = new Composite(mainComposite, SWT.NONE);
		final FillLayout btnCompositeLayout = new FillLayout(
				org.eclipse.swt.SWT.HORIZONTAL);
		btnComposite.setLayout(btnCompositeLayout);
		btnComposite.setLayoutData(btnCompositeLData);
		btnToolbar = new ToolBar(btnComposite, SWT.NONE);
		//final ToolItem itemSearch = new ToolItem(btnToolbar, SWT.NONE);
		//itemSearch.setImage(TreeIcon.SEARCH.getImage());
		//itemSearch.setToolTipText(TreeIcon.SEARCH.name().toLowerCase());
		final ToolItem itemRetrieve = new ToolItem(btnToolbar, SWT.NONE);
		itemRetrieve.setImage(TreeIcon.RETRIEVE.getImage());
		itemRetrieve.setToolTipText(TreeIcon.RETRIEVE.name().toLowerCase());
		final ToolItem itemStore = new ToolItem(btnToolbar, SWT.NONE);
		itemStore.setImage(TreeIcon.STORE.getImage());
		itemStore.setToolTipText(TreeIcon.STORE.name().toLowerCase());
		itemStore.setEnabled(false);
	    final Listener listener = new Listener() {
	        public void handleEvent(final Event event) {
	        	 /*if (event.widget.equals(itemSearch)) {
	     	    	final String searchTerm = searchComposite.getText();
	 				if (searchTerm.length() > 0) {
						Viewer.setSearchText(searchTerm);
						execCommand(Search.COMMAND_ID);
					}
	             } else
	             */
	        	if(event.widget.equals(itemRetrieve)) {
	        		execCommand(Retrieve.COMMAND_ID);
	        	} else if(event.widget.equals(itemStore)) {
	        		execCommand(StoreRoot.COMMAND_ID);
	        	}
	        }

			private void execCommand(final String commandId) {
				final IHandlerService handlerService = (IHandlerService) getSite().getService(IHandlerService.class);
				try {
					handlerService.executeCommand(commandId, new Event());
				} catch (final Throwable ex) {
					Logger.getLogger("global").log(Level.SEVERE, null, ex);
				}
			}
	      };

	    //itemSearch.addListener(SWT.Selection, listener);
	    itemRetrieve.addListener(SWT.Selection, listener);
		itemStore.addListener(SWT.Selection, listener);
		createTree(mainComposite);

	}

	private void createTree(final Composite parent) {
		final GridData treeCompositeLData = new GridData();
		treeCompositeLData.grabExcessHorizontalSpace = true;
		treeCompositeLData.horizontalAlignment = GridData.FILL;
		treeCompositeLData.horizontalSpan = NUM_COLUMNS;
		treeCompositeLData.verticalAlignment = GridData.FILL;
		treeCompositeLData.grabExcessHorizontalSpace = true;
		treeCompositeLData.grabExcessVerticalSpace = true;
		final Composite treeComposite = new Composite(parent, SWT.NONE);
		final GridData layoutData = new GridData();
		layoutData.horizontalSpan = NUM_COLUMNS;
		layoutData.horizontalAlignment = GridData.FILL;
		layoutData.verticalAlignment = GridData.FILL;
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.grabExcessVerticalSpace = true;
		final FillLayout treeCompositeLayout = new FillLayout(
				org.eclipse.swt.SWT.HORIZONTAL);
		treeCompositeLayout.marginWidth = 0;
		//treeCompositeLayout.marginWidth = TREE_COMPOSITE_MARGIN_WIDTH;
		treeComposite.setLayout(treeCompositeLayout);
		treeComposite.setLayoutData(treeCompositeLData);
		containmentTreeViewer = new TreeViewer(treeComposite, SWT.H_SCROLL
				| SWT.V_SCROLL);
		containmentTreeViewer.setContentProvider(new TreeContentProvider());
		containmentTreeViewer.setLabelProvider(new TreeLabelProvider());
		containmentTreeViewer.setSorter(new ViewerSorter());
		final Listener doubleClickListener = new Listener() {
			public void handleEvent(final Event event) {
				final Point point = new Point(event.x, event.y);
				final TreeItem item = containmentTreeViewer.getTree().getItem(
						point);
				if (item != null) {
				final ContainmentTreeNode selectedNode = (ContainmentTreeNode) item
						.getData();
				// if (item != null &&
				// !selectedNode.getSet().isEqualTo(Root.root)) {
					EditorLoadingHandler.openVisualizationWidget(
							((ContainmentTreeNode) item.getData()),
							containmentTreeViewer);
				}
				// }
			}
		};
		containmentTreeViewer.getTree().addListener(SWT.MouseDoubleClick,
				doubleClickListener);
		containmentTreeViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(
							final SelectionChangedEvent event) {
						if (containmentTreeViewer.getControl().getMenu() == null) {
							createContextMenu();
						}
					}
				});

		getSite().setSelectionProvider(containmentTreeViewer);
	}

	@Override
	public void setFocus() {
	}

	public static IWorkbenchPartSite getViewerSite() {
		return viewerSite;
	}

	public static void selectNode(final UUID instanceURR) {
		final ITreeContentProvider provider = (ITreeContentProvider) containmentTreeViewer
				.getContentProvider();
		final ContainmentTreeNode rootNode = (ContainmentTreeNode) provider
				.getElements(null)[0];
		final List<ContainmentTreeNode> nodeMatched = new ArrayList<ContainmentTreeNode>();
		walkTree(instanceURR, rootNode, nodeMatched);
		if (!nodeMatched.isEmpty()) {
			containmentTreeViewer.setSelection(new StructuredSelection(
					nodeMatched.get(0)), true);
		}
	}

	private static void walkTree(final UUID instanceURR,
			final ContainmentTreeNode rootNode,
			final List<ContainmentTreeNode> nodeMatched) {
		if (rootNode.getSet().identity().uniqueRepresentationReference()
				.equals(instanceURR)) {
			nodeMatched.add(rootNode);
		} else {
			if (rootNode.getChildNodes() != null) {
				for (final ContainmentTreeNode n : rootNode.getChildNodes()) {
					walkTree(instanceURR, n, nodeMatched);
				}
			}
		}
	}

}
