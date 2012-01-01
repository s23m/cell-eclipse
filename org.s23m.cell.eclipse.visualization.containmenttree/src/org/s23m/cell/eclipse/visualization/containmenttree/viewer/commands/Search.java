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

package org.s23m.cell.eclipse.visualization.containmenttree.viewer.commands;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.s23m.cell.eclipse.visualization.containmenttree.viewer.Viewer;
import org.s23m.cell.eclipse.visualization.containmenttree.viewer.handlers.EditorLoadingHandler;
import org.s23m.cell.repository.client.RepositoryClient;
import org.s23m.cell.repository.client.mediator.RepositoryClientMediator;
import org.s23m.cell.serialization.container.ArtefactContainer;
import org.s23m.cell.serialization.container.ContainerTypeMapper;
import org.s23m.cell.serialization.container.SearchResultType;
import org.s23m.cell.serialization.serializer.ProtocolType;
import org.s23m.cell.serialization.serializer.SerializationType;

public class Search extends AbstractHandler {

	public static final String COMMAND_ID = "org.s23m.cell.eclipse.visualization.containmenttree.viewer.commands.search";

	public Object execute(final ExecutionEvent event) throws ExecutionException {
	    final Display display = Display.getCurrent();
		final String searchTxt = Viewer.getSearchText();
		if (searchTxt != null && !searchTxt.trim().equals("")) {

			BusyIndicator.showWhile(display, new Runnable() {
				public void run() {
		            try {
		            	doSearch();
		            } catch (final Throwable th) {
		            	Logger.getLogger("global").log(Level.SEVERE, null, th);
		            }
		        }

				private void doSearch() {
					final RepositoryClient repoClient = RepositoryClientMediator.getInstance().getComponent(ProtocolType.REPOSITORY_CLIENT);
					final ArtefactContainer searchRequestContainer = ContainerTypeMapper.mapArugmentToArtefactContainerContent(searchTxt, SerializationType.SEARCH_ARGUMENTS);
					final ArtefactContainer searchResultsContainer = repoClient.get(searchRequestContainer);
					final List<SearchResultType> results = ContainerTypeMapper.mapSearchResultListToSearchResultTypeList(searchResultsContainer.getSearchResult());

					if (results != null) {
						display.asyncExec(new Runnable() {
							public void run() {
								final Tree tree = Viewer.getContainmentTreeViewer().getTree();
								if (tree.isDisposed()) {
									return;
								}
								tree.setCursor(null);
								tree.setEnabled(true);
								EditorLoadingHandler.openSearchResultsWidget(searchTxt, results);
							}
						});
					}
				}
			});
		}
		return null;
	}

}
