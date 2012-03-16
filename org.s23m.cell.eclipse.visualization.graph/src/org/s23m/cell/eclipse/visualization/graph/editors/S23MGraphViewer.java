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

package org.s23m.cell.eclipse.visualization.graph.editors;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.SharedHeaderFormEditor;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.s23m.cell.Set;
import org.s23m.cell.api.Transaction;
import org.s23m.cell.eclipse.visualization.containmenttree.viewer.VisualizationInput;
import org.s23m.cell.eclipse.visualization.containmenttree.viewer.model.ContainmentTreeNode;
import org.s23m.cell.eclipse.visualization.graph.Activator;
import org.s23m.cell.eclipse.visualization.graph.rendering.HtmlRenderer;
import org.s23m.cell.eclipse.visualization.graph.rendering.Renderer;
import org.s23m.cell.eclipse.visualization.graph.rendering.RenderingCoordinator;
import org.s23m.cell.eclipse.visualization.graph.rendering.RenderingHistory;
import org.s23m.cell.eclipse.visualization.graph.rendering.StructuralGraphRenderer;
import org.s23m.cell.eclipse.visualization.graph.rendering.SuperSetGraphRenderer;
import org.s23m.cell.eclipse.visualization.graph.rendering.VisibilityGraphRenderer;
import org.s23m.cell.repository.client.RepositoryClient;
import org.s23m.cell.repository.client.RepositoryClientImpl;
import org.s23m.cell.serialization.container.ArtefactContainer;
import org.s23m.cell.serialization.container.ObjectFactoryHolder;
import org.s23m.cell.serialization.serializer.SerializationType;

public class S23MGraphViewer extends SharedHeaderFormEditor implements IResourceChangeListener {


	private static final String STRUCTURAL_VIEW_TITLE = "Structure";

	private static final String VISIBILITY_VIEW_TITLE = "Scope";

	private static final String SUPERSET_VIEW_TITLE = "Reuse";

	private static final String HTML_VIEW_TITLE = "Details";

	private ContainmentTreeNode renderingNode;

	private RenderingCoordinator coordinator;

	private TreeViewer controlViewer;

	private VisualizationInput input;

	/**
	 * Records the index of the last open tab. This tab will be the default for new instances.
	 */
	private static int lastOpenTabIndex;

	private ToolItem backButton;

	private ToolItem forwardButton;

	private SetMenu backButtonListener;

	private SetMenu forwardButtonListener;

	private RenderingHistory history;

	protected boolean dirty = false;

	/**
	 * Original authors:
	 * Rob Warner (rwarner@interspatial.com)
	 * Robert Harris (rbrt_harris@yahoo.com)
	 */
	private class SetMenu extends SelectionAdapter {

		private final Menu menu;

		private final Map<Set, Integer> setIndexes;

		private final int offsetMultiplier;

		public SetMenu(final ToolItem dropdown, final boolean forward) {
			this.menu = new Menu(dropdown.getParent());
			this.setIndexes = new HashMap<Set, Integer>();
			this.offsetMultiplier = forward ? 1 : -1;
		}

		private void add(final Set set) {
			final int index = setIndexes.size() + 1;
			setIndexes.put(set, index);
			final MenuItem menuItem = new MenuItem(menu, SWT.NONE);
			menuItem.setData(set);
			menuItem.setText(getName(set));
			menuItem.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(final SelectionEvent event) {
					final MenuItem selected = (MenuItem) event.widget;
					final Set selectedSet = (Set) selected.getData();
					final int index = setIndexes.get(selectedSet);
					final int offset = offsetMultiplier * index;
					navigate(offset);
				}
			});
		}

		public void setItems(final List<Set> sets) {
			// dispose of existing items to remove them from the menu
			for (final MenuItem menuItem : menu.getItems()) {
				menuItem.dispose();
			}

			setIndexes.clear();
			// add new items
			for (final Set set : sets) {
				add(set);
			}
		}

		/**
		 * Called when either the button itself or the dropdown arrow is clicked
		 *
		 * @param event
		 *            the event that trigged this call
		 */
		@Override
		public void widgetSelected(final SelectionEvent event) {
			// If they clicked the arrow, we show the list
			if (event.detail == SWT.ARROW) {
				// Determine where to put the dropdown list
				final ToolItem item = (ToolItem) event.widget;
				final Rectangle rect = item.getBounds();
				final Point rectangleLocation = new Point(rect.x, rect.y);
				final Point pt = item.getParent().toDisplay(rectangleLocation);
				menu.setLocation(pt.x, pt.y + rect.height);
				menu.setVisible(true);
			} else {
				// button pushed - handled by other listener
			}
		}
	}

	public S23MGraphViewer() {
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
	}

	private void addHtmlPage() {
		final Composite parentContainer = new Composite(getContainer(), SWT.NONE);
		parentContainer.setLayout(new FillLayout());
		final int index = addPage(parentContainer);
		setPageText(index, HTML_VIEW_TITLE);

		final HtmlRenderer htmlRenderer = new HtmlRenderer(coordinator, parentContainer);
		addRenderer(htmlRenderer);
	}

    @Override
	protected void addPages() {
		coordinator.updateSetToRender(renderingNode.getSet());

		final Graph structuralGraph = addZestPage(STRUCTURAL_VIEW_TITLE);
		addRenderer(new StructuralGraphRenderer(structuralGraph, coordinator, getLastTabItem()));

		final Graph visibilityGraph = addZestPage(VISIBILITY_VIEW_TITLE);
		addRenderer(new VisibilityGraphRenderer(visibilityGraph, coordinator, getLastTabItem()));

		final Graph superSetGraph = addZestPage(SUPERSET_VIEW_TITLE);
		addRenderer(new SuperSetGraphRenderer(superSetGraph, coordinator, getLastTabItem()));

		addHtmlPage();

		// initial rendering
		setActivePage(lastOpenTabIndex);
	}

    private void addRenderer(final Renderer renderer) {
		coordinator.addRenderer(renderer);
	}

	private Graph addZestPage(final String tabTitle) {
		final Composite parentContainer = new Composite(getContainer(), SWT.NONE);
		final Graph graph = new Graph(parentContainer, SWT.NONE);
		graph.setNodeStyle(ZestStyles.NODES_NO_LAYOUT_ANIMATION);
		parentContainer.setLayout(new FillLayout());
		final int index = addPage(parentContainer);
		setPageText(index, tabTitle);
		return graph;
	}

	private ToolItem createDropDownButton(final ToolBar parent, final Image image) {
		final ToolItem item = new ToolItem(parent, SWT.DROP_DOWN);
		item.setImage(image);
		// initially disabled
		item.setEnabled(false);
		return item;
	}

	/**
	 * Subclasses should extend this method to configure the form that owns the
	 * shared header. If the header form will contain controls that can change
	 * the state of the editor, they should be wrapped in an IFormPart so that
	 * they can participate in the life cycle event management.
	 *
	 * @param headerForm the form that owns the shared header
	 * @see IFormPart
	 */
	@Override
	protected void createHeaderContents(final IManagedForm headerForm) {
		final FormToolkit toolkit = getToolkit();
		final Form form = headerForm.getForm().getForm();
		toolkit.decorateFormHeading(form);
		createHeaderControls(form);
	}

	private void createHeaderControls(final Form form) {
		final Layout layout = new FillLayout();
		final Composite headClient = new Composite(form.getHead(), SWT.NULL);
		headClient.setLayout(layout);

		final ToolBar toolBar = new ToolBar(headClient, SWT.FLAT);

		createPreviousControl(toolBar);
		createNextControl(toolBar);

		form.setHeadClient(headClient);
	}

	private void createNextControl(final ToolBar toolBar) {
		forwardButton = createDropDownButton(toolBar, getImage("e_forward.gif"));
	    forwardButtonListener = new SetMenu(forwardButton, true);
	    forwardButton.addSelectionListener(forwardButtonListener);

	    forwardButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				if (e.detail != SWT.ARROW) {
					final Set next = history.next();
					updateSetToRenderAfterNavigation(next);
				}
			}
		});
	}

	private void createPreviousControl(final ToolBar toolBar) {
		backButton = createDropDownButton(toolBar, getImage("e_back.gif"));
	    backButtonListener = new SetMenu(backButton, false);
	    backButton.addSelectionListener(backButtonListener);

	    backButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				if (e.detail != SWT.ARROW) {
					final Set previous = history.previous();
					updateSetToRenderAfterNavigation(previous);
				}
			}
		});
	}

	@Override
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		super.dispose();
	}

	@Override
	public void doSave(final IProgressMonitor monitor) {

	  	BusyIndicator.showWhile(Display.getCurrent(), new Runnable() {
			public void run() {
				final RepositoryClient client = RepositoryClientImpl.getInstance();
				final ArtefactContainer persistenceArtefact = ObjectFactoryHolder.getInstance().createArtefactContainer();
				persistenceArtefact.setContentType(SerializationType.CHANGESET_PERSISTENCE.toString());
				client.put(persistenceArtefact);
				Transaction.commitChangedSets();
				setDirty(false);
	        }
	  	});
	}

	@Override
	public void doSaveAs() {
		// do nothing
	}

	private Image getImage(final String fileName) {
	    final URL url = FileLocator.find(Platform.getBundle(Activator.PLUGIN_ID), new Path("icons/" + fileName), null);
		final ImageDescriptor imgDesc = ImageDescriptor.createFromURL(url);
		return imgDesc.createImage();
	}

	private CTabItem getLastTabItem() {
		final int index = getPageCount() - 1;
		final Control control = (Control) pages.get(index);
		final CTabFolder folder = (CTabFolder) control.getParent();
		final CTabItem item = folder.getItem(index);
		return item;
	}

	private String getName(final Set set) {
		return set.identity().name();
	}

	@Override
	public String getTitleToolTip() {
		return input.getName();
	}

	@Override
	public void init(final IEditorSite site, final IEditorInput editorInput) throws PartInitException {
		super.init(site, editorInput);
		input = (VisualizationInput) editorInput;
		renderingNode = input.getNode();
		controlViewer = input.getContainmentTreeViewer();

		coordinator = new RenderingCoordinator(this, controlViewer);
		history = coordinator.getHistory();
	}

	@Override
	public boolean isDirty(){
        return dirty;
    }

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	private void navigate(final int offset) {
		coordinator.navigate(offset);
		coordinator.render();
	}

	@Override
	protected void pageChange(final int newPageIndex) {
		// inform coordinator of page change
		coordinator.pageChange(newPageIndex);
		lastOpenTabIndex = newPageIndex;
	}

	public void resourceChanged(final IResourceChangeEvent event) {
	}

	public void setDirty(final boolean value){
        dirty = value;
        firePropertyChange(PROP_DIRTY);
    }

	void setFont() {
	}

	public void updateSetToRender(final Set setToRender) {
		final String text = setToRender.identity().name();
		setPartName(text);
		input.updateSetToRender(setToRender);
		setTitleToolTip(input.getName());

		// update button state
		backButton.setEnabled(history.hasPrevious());
		forwardButton.setEnabled(history.hasNext());

		// update dropdowns
		backButtonListener.setItems(history.getPreviousSets());
		forwardButtonListener.setItems(history.getNextSets());
	}

	private void updateSetToRenderAfterNavigation(final Set setToRender) {
		// inform coordinator of change
		coordinator.updateSetToRender(setToRender);
		coordinator.render();
	}

}
