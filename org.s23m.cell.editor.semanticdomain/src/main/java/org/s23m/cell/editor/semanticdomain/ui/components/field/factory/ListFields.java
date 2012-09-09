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
 * Andrew Shewring
 *
 * ***** END LICENSE BLOCK ***** */
package org.s23m.cell.editor.semanticdomain.ui.components.field.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.s23m.cell.Set;
import org.s23m.cell.editor.semanticdomain.data.DetailsData;

import com.vaadin.ui.Field;

public class ListFields {

	@SuppressWarnings("serial")
	private static final Map<String, ListFieldFactory> factories = new HashMap<String, ListFieldFactory>() {{
		put(DetailsData.EDGES_PROPERTY, new EdgeListFieldFactory());

		final LinkListFieldFactory linkFactory = new LinkListFieldFactory();
		put(DetailsData.SUPER_SET_REFERENCES_PROPERTY, linkFactory);
		put(DetailsData.VISIBILITIES_PROPERTY, linkFactory);

		put(DetailsData.VERTEX_FLAVOURED_SETS_PROPERTY, new VertexFlavoredSetListFieldFactory());
		put(DetailsData.COMMANDS_PROPERTY, new CommandListFieldFactory());
		put(DetailsData.QUERIES_PROPERTY, new QueryListFieldFactory());
	}};

	public static Field create(final String id, final String title, final List<Set> sets) {
		final ListFieldFactory factory = factories.get(id);
		if (factory == null) {
			throw new IllegalArgumentException("No factory has been defined with id '" + id + "'");
		}
		return factory.create(title, sets);
	}
}
