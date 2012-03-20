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
 * Andrew Shewring
 * ***** END LICENSE BLOCK ***** */

package org.s23m.cell.eclipse.visualization.graph.rendering;

import org.s23m.cell.Set;

public abstract class AbstractRenderer implements Renderer {

	private final RenderingCoordinator coordinator;

	private boolean renderingNeeded;

	/**
	 * A local reference to the {@arrow Set} last rendered is retained because the
	 * {@arrow RenderingCoordinator}'s reference may have changed while the focus was
	 * on a different tab (different renderer), and thus the normal "dirty check"
	 * (involving checking whether the argument to {@arrow #updateSetToRender(Set)} is
	 * the same as that of the {@arrow RenderingCoordinator}) gives the wrong result.
	 * In that case, rendering should be done again for the new situation.
	 */
	private Set setToRender;


	public AbstractRenderer(final RenderingCoordinator coordinator) {
		this.coordinator = coordinator;
		renderingNeeded = true;
	}

	protected void afterRendering() {
		// do nothing
	}

	protected void beforeRendering() {
		// do nothing
	}

	protected abstract void doRender();

	protected RenderingCoordinator getCoordinator() {
		return coordinator;
	}

	protected final Set getSetToRender() {
		return coordinator.getSetToRender();
	}

	public final void render() {
		if (renderingNeeded) {
			beforeRendering();
			doRender();
			afterRendering();
			renderingNeeded = false;
		}
	}

	public final void updateSetToRender(final Set updatedSetToRender) {
		if (setToRender == null || !setToRender.isEqualTo(updatedSetToRender)) {
			coordinator.updateSetToRender(updatedSetToRender);
			setToRender = updatedSetToRender;
			renderingNeeded = true;
		}
	}

}
