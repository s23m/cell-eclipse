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

import java.util.UUID;
import java.util.logging.Logger;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.eclipse.ui.handlers.IHandlerService;
import org.s23m.cell.serialization.container.InstanceIdentityType;
import org.s23m.cell.serialization.container.SearchResultType;
import org.s23m.cell.eclipse.visualization.containmenttree.viewer.ContainmentTreeManager;
import org.s23m.cell.eclipse.visualization.containmenttree.viewer.SearchWidgetInput;

public class ResultPage {
	
	private static final String TXT_GAP = " ";
	private static final String URR_TAG = "URR: ";
	private static final String PLURAL_NAME_TAG = TXT_GAP+"plural Name: ";
	private static final String NAME_TAG = TXT_GAP+"name: ";
	private static final String PART_TAG = "part of: ";
	private static final String INSTANCE_SELECTION_COMMAND = "org.s23m.cell.eclipse.visualization.containmenttree.viewer.commands.instanceSelection";
	private static final String RETRIEVAL_COMMAND = "org.s23m.cell.eclipse.visualization.containmenttree.viewer.commands.retrieve";
	private static final int FULL_COL_SPAN = 2;
	private static final int PAGE_SIZE = 8;
	private static final int PAGE_LINK_FONT_SIZE = 12;
	private static final String META_ELEMENT_NOTATION = " : ";

	private Composite parent = null;
	private FormToolkit toolkit;
	private ScrolledForm form;
	private SearchWidgetInput searchInput;
	private int pageIndex;
	
	protected ResultPage(FormToolkit toolkit, ScrolledForm form, SearchWidgetInput searchInput) {
		this.toolkit = toolkit;
		this.form = form;
		this.searchInput = searchInput;
		buildPage();
	}

	private void buildPage() {
		if (parent != null) {
			parent.dispose();
		}
		parent = toolkit.createComposite(form.getBody());
		TableWrapLayout parentLayout = new TableWrapLayout();
		parentLayout.numColumns = 2;
	    parent.setLayout(parentLayout);
		
		final Label lblSearchText = toolkit.createLabel(parent, 
				searchInput.getSearchText(), 
				SWT.WRAP);
		lblSearchText.setFont(getFontWithSize(lblSearchText, 16));
		setLayoutData(lblSearchText, FULL_COL_SPAN);
	    
		final Label lblResults = toolkit.createLabel(parent, 
				"Returned "+searchInput.getResults().size()+" result(s)", 
				SWT.WRAP);
	    setLayoutData(lblResults, FULL_COL_SPAN);
	    
	    final int startIndex = pageIndex * PAGE_SIZE;
	    final int endIndex = startIndex + PAGE_SIZE;
	    
	    if (!searchInput.getResults().isEmpty()) {
	    	for (int n = startIndex; (n < endIndex && n < searchInput.getResults().size()); n++) {
	    		final SearchResultType result = searchInput.getResults().get(n);
	    		final InstanceIdentityType instanceId = result.getInstanceIdentity();
		    	final InstanceIdentityType containerId = result.getContainerIdentity();
		    	
     		    final StyledText txtInstanceLink = new StyledText(parent, SWT.READ_ONLY);
     		    final String metaLinkTxt = result.getMetaInstanceIdentity().getName()+META_ELEMENT_NOTATION;
     		    final String instanceLinkTxt = metaLinkTxt+instanceId.getName();
     		    txtInstanceLink.setText(instanceLinkTxt);
     		    txtInstanceLink.addMouseListener(new MouseListener() {
					public void mouseDoubleClick(MouseEvent e) {
					}
	
					public void mouseDown(MouseEvent e) {
					}
	
					public void mouseUp(MouseEvent e) {
						final UUID urrToSelect = (result.getMetaInstanceIdentity().getName().equals("edge end")) ? UUID.fromString(containerId.getUuid()) : UUID.fromString(instanceId.getUuid());
						//TODO: F_SemanticStateOfInMemoryModel.coreGraphs.edgeEnd.identity().getName() should be used instead but this will violate the initialization sequence
						loadContainmentTree(urrToSelect, ContainmentTreeManager.isModelLoaded());	
					}
     		    });
     		    
     		    txtInstanceLink.addListener(SWT.MouseExit, new Listener() {
     		       public void handleEvent(Event e) {
        		     txtInstanceLink.setCursor(null);
     		       }
     		     });
     		    txtInstanceLink.addListener(SWT.MouseEnter, new Listener() {
     		       public void handleEvent(Event e) {
     		  		final Cursor wCursor = new Cursor(null, SWT.CURSOR_HAND);
     		  		txtInstanceLink.setCursor(wCursor);
     		       }
     		    });
     		    txtInstanceLink.setStyleRange(this.getStyleRanges(0, metaLinkTxt.length(), Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GRAY), true, Display.getCurrent().getSystemColor(SWT.COLOR_BLUE)));
     		    txtInstanceLink.setStyleRange(this.getStyleRanges(metaLinkTxt.length(), instanceLinkTxt.length()-metaLinkTxt.length(), Display.getCurrent().getSystemColor(SWT.COLOR_BLUE), true, Display.getCurrent().getSystemColor(SWT.COLOR_BLUE)));
     		    setLayoutData(txtInstanceLink, FULL_COL_SPAN);

     		    final StyledText txtResultDescription = new StyledText(parent, SWT.READ_ONLY);
			    txtResultDescription.setText(
						  PART_TAG+containerId.getName()+""+
						  NAME_TAG+instanceId.getName()+""+
						  PLURAL_NAME_TAG+instanceId.getPluralName()
				);
			    setTextStyles(txtResultDescription, instanceId, containerId);
				setLayoutData(txtResultDescription, FULL_COL_SPAN);
	    	}
	    }
	    
		//add a search pages bar
	    if (searchInput.getResults().size() > PAGE_SIZE) {
			createPageBar();
	    }
		form.getBody().getParent().layout(true);
	    form.getBody().getParent().redraw();
	    form.getBody().getParent().update();
	}

	private void createPageBar() {
		final Composite pageBar = toolkit.createComposite(parent);
		final TableWrapData td = new TableWrapData(TableWrapData.FILL);
		td.colspan = FULL_COL_SPAN;
		pageBar.setLayoutData(td);
		RowLayout rowLayout = new RowLayout();
		pageBar.setLayout(rowLayout);
		
		int numPages = searchInput.getResults().size() / PAGE_SIZE;
		if (searchInput.getResults().size() % PAGE_SIZE > 0) {
			numPages++;
		}
		
		for (int n = 0; n < numPages; n++) {
			final int linkIndex = n;
			Hyperlink pageLink = toolkit.createHyperlink(pageBar, ""+(n+1),
					SWT.WRAP);
			FontData[] fontData = pageLink.getFont().getFontData();
			fontData[0].setHeight(PAGE_LINK_FONT_SIZE);
			pageLink.setFont( new Font(Display.getCurrent(),fontData[0]));

			pageLink.addHyperlinkListener(new HyperlinkAdapter() {
				public void linkActivated(HyperlinkEvent e) {
					pageIndex = linkIndex;
					buildPage();
				}
			});
			if (linkIndex == pageIndex) {
				pageLink.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_DARK_RED));
			}
		}
	}

	private void setTextStyles(final StyledText txtResultDescription, final InstanceIdentityType instanceId, final InstanceIdentityType containerId) {
		int txtIndex = 0;
		txtIndex = PART_TAG.length()+containerId.getName().length();
		txtResultDescription.setStyleRange(getStyleRanges(txtIndex, NAME_TAG.length(), Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GRAY), false, Display.getCurrent().getSystemColor(SWT.COLOR_BLACK)));
		txtIndex += NAME_TAG.length()+instanceId.getName().length();
		txtResultDescription.setStyleRange(getStyleRanges(txtIndex, PLURAL_NAME_TAG.length(), Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GRAY), false, Display.getCurrent().getSystemColor(SWT.COLOR_BLACK)));
	}
	
	private StyleRange getStyleRanges(final int begin, final int length, final Color color, final boolean isUnderLined,
			final Color underLinedColor) {
		StyleRange styleRange = new StyleRange();
		styleRange.start = begin;
		styleRange.length = length;
		styleRange.foreground = color;
		styleRange.underline = isUnderLined;
		styleRange.underlineColor = underLinedColor;
		return styleRange;
	}

	private void setLayoutData(final Control control, final int colSpan) {
		TableWrapData td = new TableWrapData();
	    td.colspan = colSpan;
	    control.setLayoutData(td);
	}

	private Font getFontWithSize(final Label lblSearchText, final int size) {
		FontData[] fontData = lblSearchText.getFont().getFontData();
		fontData[0].setHeight(size);
		return new Font(Display.getCurrent(), fontData[0]);
	}
	
	private void loadContainmentTree(final UUID selectedNodeURR, boolean isModelLoaded) {
		 final IHandlerService handlerService = (IHandlerService) PlatformUI.getWorkbench()
		 						.getService(IHandlerService.class);
		 final Event event = new Event();
		 final String commandId = !isModelLoaded ? RETRIEVAL_COMMAND : INSTANCE_SELECTION_COMMAND;
		 event.data = selectedNodeURR;
         try {
			handlerService
			       .executeCommand(commandId, event);
		} catch (final ExecutionException ex) {
			Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex);
		} catch (final NotDefinedException ex) {
			Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex);
		} catch (final NotEnabledException ex) {
			Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex);
		} catch (final NotHandledException ex) {
			Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex);
		}
	}
	
}
