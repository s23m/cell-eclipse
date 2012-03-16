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

package org.s23m.cell.eclipse.visualization.graph.figures;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.s23m.cell.eclipse.visualization.graph.Activator;

public enum LabelIcon {

	METAELEMENT, ICON;

	private static final String DIRECTORY_PATH = "icons";
	private static final String FILE_EXTENSION = ".png";

	private Image image;

	private LabelIcon() {
		final String path = name().toLowerCase() + FILE_EXTENSION;
		final URL url = FileLocator.find(Platform.getBundle(Activator.PLUGIN_ID), new Path(DIRECTORY_PATH + "/" + path), null);
		final ImageDescriptor imgDesc = ImageDescriptor.createFromURL(url);
		image = imgDesc.createImage();
	}

	public Image getImage() {
		return image;
	}

}
