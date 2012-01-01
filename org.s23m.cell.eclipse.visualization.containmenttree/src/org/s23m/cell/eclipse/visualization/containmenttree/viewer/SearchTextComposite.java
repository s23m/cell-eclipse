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

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.handlers.IHandlerService;
import org.s23m.cell.eclipse.visualization.containmenttree.viewer.commands.Search;

public class SearchTextComposite extends Composite {

	private static final int HORIZONTAL_SPAN = 3;
	private boolean isHelpTextOn;
	private final FormToolkit toolkit;
	private final Text text;

	protected String getText() {
		final String searchTxt = isHelpTextOn ? "" : text.getText().trim();
		return searchTxt;
	}

	public SearchTextComposite(final Composite parent, final int style) {
		super(parent, style);
		toolkit = new FormToolkit(parent.getDisplay());
		text = toolkit.createText(this, ""); //$NON-NLS-1$
		buildPart();
	}

	private void buildPart() {
		final GridLayout searchCompositeLayout = new GridLayout();
		searchCompositeLayout.makeColumnsEqualWidth = true;
		searchCompositeLayout.horizontalSpacing = 0;
		this.setLayout(searchCompositeLayout);
		final GridData lData = new GridData();
		lData.horizontalSpan = HORIZONTAL_SPAN;
		lData.grabExcessHorizontalSpace = true;
		lData.horizontalAlignment = GridData.FILL;
		this.setLayoutData(lData);
		final GridData textLData = new GridData();
		textLData.horizontalAlignment = GridData.FILL;
		textLData.grabExcessHorizontalSpace = true;
		text.setLayoutData(textLData);
		setTextStyle(Messages.SearchTextComposite_SEARCH_HELP_TEXT, true);
		text.addFocusListener(getFocusListener());
		text.addKeyListener(new KeyListener(){

			public void keyPressed(final KeyEvent e) {
			}

			public void keyReleased(final KeyEvent e) {
				final Text txt = (Text) e.widget;
				if (!isHelpTextOn && txt.getText().trim().length() > 0) {
					if (e.keyCode  == SWT.CR) {
						Viewer.setSearchText(txt.getText().trim());
						 try {
							((IHandlerService)PlatformUI.getWorkbench().getService(IHandlerService.class)).
							 	executeCommand(Search.COMMAND_ID, new Event());
						} catch (final Throwable ex) {
							Logger.getLogger("global").log(Level.SEVERE, null, ex);
						} ;
					}
				}
			}

		});
	}

	private FocusListener getFocusListener() {
		final FocusListener focusListener = new FocusListener() {
			public void focusGained(final FocusEvent e) {
				if (isHelpTextOn) {
					setTextStyle("", false);
				}
			}

			public void focusLost(final FocusEvent e) {
				final Text txt = (Text) e.widget;
				if (txt.getText().trim().length() < 1 || isHelpTextOn) {
					setTextStyle(Messages.SearchTextComposite_SEARCH_HELP_TEXT,
							true);
				}
			}
		};
		return focusListener;
	}

	private void setTextStyle(final String value, final boolean isHelpText) {
		text.setText(value);
		if (isHelpText) {
			text.setForeground(Display.getCurrent().getSystemColor(
					SWT.COLOR_GRAY));
			isHelpTextOn = true;
		} else {
			text.setForeground(Display.getCurrent().getSystemColor(
					SWT.COLOR_BLACK));
			isHelpTextOn = false;
		}
	}

}
