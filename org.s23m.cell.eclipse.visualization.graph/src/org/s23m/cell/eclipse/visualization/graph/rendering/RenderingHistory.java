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
 * Andrew Shewring
 * ***** END LICENSE BLOCK ***** */

package org.s23m.cell.eclipse.visualization.graph.rendering;

import java.util.ArrayList;
import java.util.List;

import org.s23m.cell.Set;

public class RenderingHistory {

	private final List<Set> sets;

	private int index;

	public RenderingHistory() {
		sets = new ArrayList<Set>();
		index = -1;
	}

	/**
	 * Invoked after navigating a "hyperlink" to a different {@link Set}
	 * @param setToRender
	 */
	public void updateSetToRender(final Set setToRender) {
		final Set currentSet = getCurrentSet();
		if (currentSet == null || !currentSet.isEqualTo(setToRender)) {
			// remove sets after this one
			final int removals = sets.size() - index - 1;
			for (int i = 0; i < removals; i++) {
				sets.remove(index);
			}

			// add set at the end
			sets.add(setToRender);

			// update index
			index++;
		}
	}

	/**
	 * Navigate to a different {@link Set} by way of an offset
	 *
	 * @param offset
	 * @return the resulting {@link Set}
	 */
	public Set navigate(final int offset) {
		if (offset == 0) {
			throw new IllegalArgumentException("Offset must be non-zero");
		}
		final int newIndex = index + offset;
		if (newIndex < 0 || newIndex >= sets.size()) {
			throw new IllegalArgumentException("Offset of " + offset + " is invalid because it implies an index of " + newIndex + " (size is " + sets.size() + ")");
		}
		index = newIndex;
		return getCurrentSet();
	}

	public Set getCurrentSet() {
		if (index < 0) {
			return null;
		}
		return sets.get(index);
	}

	public Set next() {
		// forward
		index++;
		return getCurrentSet();
	}

	public Set previous() {
		// back
		index--;
		return getCurrentSet();
	}

	public boolean hasNext() {
		return index < sets.size() - 1;
	}

	public boolean hasPrevious() {
		return index > 0;
	}

	public List<Set> getPreviousSets() {
		return sets.subList(0, index);
	}

	public List<Set> getNextSets() {
		return sets.subList(index + 1, sets.size());
	}
}
