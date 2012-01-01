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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.internal.xtend.type.baseimpl.OperationImpl;
import org.eclipse.xtend.expression.TypeSystem;
import org.eclipse.xtend.typesystem.Feature;
import org.eclipse.xtend.typesystem.Type;
import org.s23m.cell.G;
import org.s23m.cell.Set;
import org.s23m.cell.api.CoreSets;
import org.s23m.cell.eclipse.openarchitectureware.types.AbstractType;

public class KernelValuesType extends AbstractType {

	private static final String FULLY_QUALIFIED_NAME = createFullyQualifiedName(CoreSets.class);

	private final CoreSets delegate;

	private final RootSetType rootSetType;

	private final java.util.Set<Type> superTypes;

	public KernelValuesType(final TypeSystem typeSystem, final RootSetType rootSetType) {
		super(typeSystem, FULLY_QUALIFIED_NAME);
		this.delegate = G.coreSets;
		this.rootSetType = rootSetType;
		this.superTypes = Collections.singleton(getTypeSystem().getTypeType());
	}

	@Override
	public java.util.Set<? extends Type> getSuperTypes() {
		return superTypes;
	}

	@Override
	public Feature[] getContributedFeatures() {
		// expose all public fields as properties
		final List<Feature> features = new ArrayList<Feature>();
		final Field[] fields = delegate.getClass().getFields();
		for (final Field field : fields) {
			final Feature feature = createFeature(field);
			features.add(feature);
		}
		return features.toArray(new Feature[features.size()]);
	}

	private Feature createFeature(final Field field) {
		final String name = field.getName();
		return new OperationImpl(this, name, rootSetType) {
			@Override
			protected Object evaluateInternal(final Object target, final Object[] params) {
				return getFieldValue(field);
			}
		};
	}

	private Set getFieldValue(final Field field) {
		try {
			final Object object = field.get(delegate);
			return (Set) object;
		} catch (final Exception e) {
			throw new IllegalStateException("Could not retrieve value from field '" + field + "'", e);
		}
	}
}
