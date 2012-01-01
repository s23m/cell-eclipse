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

package org.s23m.cell.eclipse.visualization.containmenttree.viewer;

import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;
import org.s23m.cell.serialization.container.SearchResultType;

public class SearchWidgetInput implements IEditorInput {

	private final List<SearchResultType> results;
	private final String searchText;

	public SearchWidgetInput(final String searchText, final List<SearchResultType> results) {
		this.searchText = (searchText == null) ? "": searchText;
		this.results = results;
    }

	public boolean exists() {
		return false;
	}

	@SuppressWarnings("unchecked")
	public Object getAdapter(final Class adapter) {
		return null;
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getName() {
		return "";
	}

	public IPersistableElement getPersistable() {
		return null;
	}

	public List<SearchResultType> getResults() {
		return results;
	}

	public String getSearchText() {
		return searchText;
	}

	public String getToolTipText() {
		return "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((results == null) ? 0 : results.hashCode());
		result = prime * result
				+ ((searchText == null) ? 0 : searchText.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final SearchWidgetInput other = (SearchWidgetInput) obj;
		if (results == null) {
			if (other.results != null) {
				return false;
			}
		} else if (!results.equals(other.results)) {
			return false;
		}
		if (searchText == null) {
			if (other.searchText != null) {
				return false;
			}
		} else if (!searchText.equals(other.searchText)) {
			return false;
		}
		return true;
	}

}
