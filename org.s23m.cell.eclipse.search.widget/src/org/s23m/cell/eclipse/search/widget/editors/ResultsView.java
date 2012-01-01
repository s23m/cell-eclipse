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
 * ***** END LICENSE BLOCK ***** */

package org.s23m.cell.eclipse.search.widget.editors;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.ide.IDE;
import org.s23m.cell.eclipse.visualization.containmenttree.viewer.SearchWidgetInput;

public class ResultsView extends FormEditor implements IResourceChangeListener{

	private static IWorkbenchPartSite viewerSite;
	private FormToolkit toolkit;
	private ScrolledForm form;
	private SearchWidgetInput searchInput;

	public ResultsView() {
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
	}

	private void createSearchResultsPage() {
		toolkit = getToolkit();
		this.setPartName("Searched for "+searchInput.getSearchText()+" - Gmodel Search");
		form = toolkit.createScrolledForm(getContainer());
		form.setText("Gmodel Search");
		
		TableWrapLayout layout = new TableWrapLayout();
	    layout.numColumns = 2;
		form.getBody().setLayout(layout);
		new ResultPage(toolkit, form, searchInput);
		addPage(form);
	}

	protected void createPages() {
		createSearchResultsPage();
		if (getContainer() instanceof CTabFolder) {
			((CTabFolder) getContainer()).setTabHeight(0);
		}
	}

	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		super.dispose();
	}

	public void doSave(IProgressMonitor monitor) {
		getEditor(0).doSave(monitor);
	}

	public void doSaveAs() {
		IEditorPart editor = getEditor(0);
		editor.doSaveAs();
		setPageText(0, editor.getTitle());
		setInput(editor.getEditorInput());
	}

	public void gotoMarker(IMarker marker) {
		setActivePage(0);
		IDE.gotoMarker(getEditor(0), marker);
	}

	public void init(IEditorSite site, IEditorInput editorInput)
		throws PartInitException {
		if (!(editorInput instanceof SearchWidgetInput))
			throw new PartInitException("Invalid Input: Must be SearchWidgetInput");
		super.init(site, editorInput);
		this.searchInput = (SearchWidgetInput) editorInput;
		this.viewerSite = getSite();
	}

	public boolean isSaveAsAllowed() {
		return true;
	}

	protected void pageChange(int newPageIndex) {
		super.pageChange(newPageIndex);
	}

	@Override
	protected void addPages() {
		createPages();		
	}

	public void resourceChanged(IResourceChangeEvent event) {
	}
	
   	public static IWorkbenchPartSite getViewerSite() {
   		return viewerSite;
   	}
}
