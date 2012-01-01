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

package org.s23m.cell.eclipse.openarchitectureware.types.singleton;

import java.util.Collections;
import java.util.Set;

import org.eclipse.internal.xtend.type.baseimpl.OperationImpl;
import org.eclipse.xtend.expression.TypeSystem;
import org.eclipse.xtend.typesystem.Feature;
import org.eclipse.xtend.typesystem.Type;
import org.s23m.cell.Identity;
import org.s23m.cell.api.models.GmodelSemanticDomains;
import org.s23m.cell.eclipse.openarchitectureware.types.AbstractType;

public final class IdentityType extends AbstractType {

	private static final String FULLY_QUALIFIED_NAME = createFullyQualifiedName(Identity.class);

	private final Set<Type> superTypes;

	public IdentityType(final TypeSystem typeSystem) {
		super(typeSystem, FULLY_QUALIFIED_NAME);
		this.superTypes = Collections.singleton(getTypeSystem().getObjectType());
	}

	@Override
	public Set<? extends Type> getSuperTypes() {
		return superTypes;
	}

	@Override
	public Feature[] getContributedFeatures() {
		final Type stringType = getTypeSystem().getStringType();
		return new Feature[] {
			new OperationImpl(this, GmodelSemanticDomains.name.identity().name(), stringType) {
				protected @Override String evaluateInternal(final Object target, final Object[] params) {
					return ((Identity) target).name();
				}
			},
			new OperationImpl(this, GmodelSemanticDomains.pluralName.identity().name(), stringType) {
				protected @Override String evaluateInternal(final Object target, final Object[] params) {
					return ((Identity) target).pluralName();
				}
			},
			new OperationImpl(this, GmodelSemanticDomains.technicalName.identity().name(), stringType) {
				protected @Override String evaluateInternal(final Object target, final Object[] params) {
					return ((Identity) target).technicalName();
				}
			},
			new OperationImpl(this, GmodelSemanticDomains.identifier.identity().name(), stringType) {
				protected @Override String evaluateInternal(final Object target, final Object[] params) {
					return ((Identity) target).identifier().toString();
				}
			}
		};
	}

}
