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
 * Copyright (C) 2009-2011 Sofismo AG.
 * All Rights Reserved.
 *
 * Contributor(s):
 * Andrew Shewring
 * ***** END LICENSE BLOCK ***** */

package org.s23m.cell.eclipse.xpand2;

import org.eclipse.xpand2.output.OutputImpl;

public class InMemoryOutput extends OutputImpl {

	private StringBuilder builder;

	public InMemoryOutput() {
		reset();
	}

	public void reset() {
		builder = new StringBuilder();
	}

	@Override
	public void openFile(final String path, final String outletName) {
		// do nothing
	}

	@Override
	public void closeFile() {
		// do nothing
	}

	@Override
	public void write(final String bytes) {
		builder.append(bytes);
	}

	public String getOutput() {
		return builder.toString();
	}

}
