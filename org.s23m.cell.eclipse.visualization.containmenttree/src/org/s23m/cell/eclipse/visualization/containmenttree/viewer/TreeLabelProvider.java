/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1
 *
 import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.s23m.cell.eclipse.api.Set;
import org.s23m.cell.eclipse.impl.F_SemanticStateOfInMemoryModel;
import org.s23m.cell.eclipse.impl.Root;
import org.s23m.cell.eclipse.visualization.containmenttree.viewer.image.TreeIcon;
import org.s23m.cell.eclipse.visualization.containmenttree.viewer.model.ContainmentTreeNode;
nse
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

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.s23m.cell.Set;
import org.s23m.cell.api.models.GmodelSemanticDomains;
import org.s23m.cell.api.models.Root;
import org.s23m.cell.eclipse.visualization.containmenttree.viewer.image.TreeIcon;
import org.s23m.cell.eclipse.visualization.containmenttree.viewer.model.ContainmentTreeNode;

public class TreeLabelProvider implements ILabelProvider  {

	public Image getImage(final Object element) {
		final Set set = ((ContainmentTreeNode) element).getSet();
		if (set.isEqualTo(Root.root)) {
			return TreeIcon.ROOT.getImage();
		} else if (set.container().isEqualTo(Root.root)) {
			if (set.value(GmodelSemanticDomains.isAbstract).isEqualTo(GmodelSemanticDomains.isAbstract_FALSE)) {
				return TreeIcon.CONCRETE_INSTANCE.getImage();
			} else {
				return TreeIcon.ABSTRACT_INSTANCE.getImage();
			}
		} else {
			return TreeIcon.VERTEX.getImage();
		}
	}

	// FIXME: this does not appear to be used to display anything in the UI
	public String getText(final Object element) {
		if (element != null) {
			final Set set = ((ContainmentTreeNode) element).getSet();
			return set.localVisualRecognitionText();
		} else {
			return null;
		}
	}

	public void addListener(final ILabelProviderListener listener) {
	}

	public void dispose() {
	}

	public boolean isLabelProperty(final Object element, final String property) {
		return false;
	}

	public void removeListener(final ILabelProviderListener listener) {
	}

}
