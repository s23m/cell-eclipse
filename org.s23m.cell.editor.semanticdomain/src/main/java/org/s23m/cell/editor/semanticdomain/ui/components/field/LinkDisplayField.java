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
 * Chul Kim
 * Andrew Shewring
 *
 * ***** END LICENSE BLOCK ***** */

package org.s23m.cell.editor.semanticdomain.ui.components.field;

import java.util.Collection;

import org.s23m.cell.editor.semanticdomain.data.DetailsData;
import org.s23m.cell.editor.semanticdomain.data.LinkDetailsData;
import org.vaadin.addon.customfield.CustomField;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class LinkDisplayField extends CustomField implements Container.Viewer {

	private static final String COLUMN_NAME_0 = "Name";

	private static final String COLUMN_NAME_1 = "From";

	private static final String COLUMN_NAME_2 = "To";

	private static final String TABLE_WIDTH = "85%";

	private final VerticalLayout layout = new VerticalLayout();

	private final Table table;

	public LinkDisplayField(final String title, final BeanItemContainer<LinkDetailsData> edgeDataContainer) {
		final Label lblList = new Label(title);
		table = new Table();
		table.setContainerDataSource(edgeDataContainer);
		table.setColumnHeader(LinkDetailsData.NAME_PROPERTY, COLUMN_NAME_0);
		table.setColumnHeader(LinkDetailsData.FROM_INSTANCE_PROPERTY, COLUMN_NAME_1);
		table.setColumnHeader(LinkDetailsData.TO_INSTANCE_PROPERTY, COLUMN_NAME_2);
		table.setVisibleColumns(LinkDetailsData.DISPLAY_ORDER);
		table.setPageLength(5);
		table.setWidth(TABLE_WIDTH);
		table.setSelectable(true);
		layout.addComponent(lblList);
		layout.addComponent(table);
		setImmediate(true);
		setCompositionRoot(layout);
	}

	@Override
	public Class<?> getType() {
		return DetailsData.class;
	}

	public Container getContainerDataSource() {
		return table.getContainerDataSource();
	}

	public void setContainerDataSource(final Container newDataSource) {
		table.setContainerDataSource(newDataSource);
	}

	@SuppressWarnings("rawtypes")
	public Collection getItemIds() {
		return table.getItemIds();
	}
}
