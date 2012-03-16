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
 * Copyright (C) 2012 The S23M Foundation.
 * All Rights Reserved.
 *
 * Contributor(s):
 * Jorn Bettin
 * Chul Kim
 * ***** END LICENSE BLOCK ***** */

package org.s23m.cell.eclipse.visualization.containmenttree.viewer.commands;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Tree;
import org.s23m.cell.eclipse.visualization.containmenttree.viewer.ContainmentTreeManager;
import org.s23m.cell.eclipse.visualization.containmenttree.viewer.Viewer;

public final class InstanceSelection extends AbstractHandler {

	public Object execute(final ExecutionEvent event) throws ExecutionException {
	    final Display display = Display.getCurrent();
	    final UUID selectedNodeURR = (UUID) ((Event)event.getTrigger()).data;
	    if (selectedNodeURR != null) {
			final Cursor wCursor = new Cursor(null, SWT.CURSOR_WAIT);
		    Viewer.getContainmentTreeViewer().getTree().setCursor(wCursor);
		    new Thread() {
		        @Override
				public void run() {
		            try {
		            	selectNode();
		            } catch (final Throwable th) {
		            	Logger.getLogger("global").log(Level.SEVERE, null, th);
		            }
		            if (display.isDisposed()) {
						return;
					}
		        }

				private void selectNode() {
					display.asyncExec(new Runnable() {
			        	public void run() {
			                final Tree tree = Viewer.getContainmentTreeViewer().getTree();
							if (tree.isDisposed()) {
								return;
							}
			                tree.setCursor(null);
			                tree.setEnabled(true);
			                Viewer.getContainmentTreeViewer().setInput(ContainmentTreeManager.getTreeManager());
			                Viewer.selectNode(selectedNodeURR);
			        	}
			    	});
				}
		      }.start();
	    }
		return null;
	}

}
