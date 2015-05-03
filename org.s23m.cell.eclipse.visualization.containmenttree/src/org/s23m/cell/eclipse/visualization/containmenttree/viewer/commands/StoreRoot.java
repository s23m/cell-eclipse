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

package org.s23m.cell.eclipse.visualization.containmenttree.viewer.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;
import org.s23m.cell.S23MKernel;
import org.s23m.cell.platform.api.models.CellPlatform;
import org.s23m.cell.serialization.serializer.SerializationContent;
import org.s23m.cell.serialization.serializer.SerializationType;
import org.s23m.cell.serialization.serializer.Serializer;
import org.s23m.cell.serialization.serializer.SerializerHolder;

public class StoreRoot extends AbstractHandler {

	public static final String COMMAND_ID = "org.s23m.cell.eclipse.visualization.containmenttree.viewer.commands.storeroot";

	public Object execute(final ExecutionEvent event) throws ExecutionException {
	    final Display display = Display.getCurrent();

	    BusyIndicator.showWhile(display, new Runnable() {
			public void run() {
	            try {
					storeRoot();
	            } catch (final Throwable th) {
	            	Logger.getLogger("global").log(Level.SEVERE, null, th); //$NON-NLS-1$
	            }
	        }

			private void storeRoot() throws Exception {
				/*
				if(!ContainmentTreeViewerStatus.getInstance().isKernelInitialized()) {
					bootstrappingByScripts();
					final RepositoryClient client = RepositoryClientImpl.getInstance();
					final ArtefactContainer persistenceArtefact = ObjectFactoryHolder.getInstance().createArtefactContainer();
					persistenceArtefact.setContentType(SerializationType.IN_MEMORY_PERSISTENCE.toString());
					client.put(persistenceArtefact);
					persistenceArtefact.setContentType(SerializationType.OBJECT_POOL_PERSISTENCE.toString());
					client.put(persistenceArtefact);
				}
				ContainmentTreeViewerStatus.getInstance().setKernelInitialized(true);
				display.asyncExec(new Runnable() {
					public void run() {
						final Tree tree = Viewer.getContainmentTreeViewer().getTree();
						if (tree.isDisposed()) {
							return;
						}
						tree.setCursor(null);
						tree.setEnabled(true);
						Viewer.getContainmentTreeViewer().setInput(ContainmentTreeManager.getTreeManager());
			            Viewer.selectNode(Root.root.identity().uniqueRepresentationReference());
					}
				});
				*/
			}

			private void bootstrappingByScripts() {
				org.s23m.cell.S23MKernel.completeCellKernelInitialization();
				CellPlatform.instantiateFeature();
				org.s23m.cell.kernel.artifactinstantiation.RunInstantiationSequence.run();
				S23MKernel.goLiveWithCellEditor();
			}

			private List<String> getAllArtifacts() throws Exception {
				final Serializer sz = SerializerHolder.getS23MInstanceSerializer(SerializationType.XML);
				final List<SerializationContent> sessionContent = sz.serializeRoot();
				final List<String> artifacts = new ArrayList<String>();

				for(final SerializationContent content : sessionContent) {
					artifacts.add(content.getContent());
				}

				return artifacts;
			}

	    });

		return null;
	}

}
