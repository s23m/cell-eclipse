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

package org.s23m.cell.eclipse.openarchitectureware.tests;

import junit.framework.TestCase;

import org.eclipse.xtend.expression.TypeSystem;
import org.eclipse.xtend.expression.TypeSystemImpl;
import org.eclipse.xtend.typesystem.Type;
import org.s23m.cell.Set;
import org.s23m.cell.eclipse.openarchitectureware.types.SetType;
import org.s23m.cell.eclipse.openarchitectureware.types.singleton.IdentityType;
import org.s23m.cell.eclipse.openarchitectureware.types.singleton.KernelValuesType;
import org.s23m.cell.eclipse.openarchitectureware.types.singleton.RootSetType;

public abstract class AbstractTypeTest extends TestCase {

	protected final TypeSystem typeSystem;

	protected final IdentityType identityType;

	protected final RootSetType rootSetType;

	protected final KernelValuesType kernelValuesType;

	protected final java.util.Set<? extends Type> rootSetSuperTypes;

	public AbstractTypeTest() {
		typeSystem = new TypeSystemImpl();
		identityType = new IdentityType(typeSystem);
		rootSetType = new RootSetType(typeSystem, identityType);
		kernelValuesType = new KernelValuesType(typeSystem, rootSetType);
		rootSetSuperTypes = rootSetType.getSuperTypes();
	}

	public SetType createSetType(final Set set) {
		return new SetType(typeSystem, set, rootSetType, identityType);
	}
}
