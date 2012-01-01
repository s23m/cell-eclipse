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

package org.s23m.cell.eclipse.openarchitectureware.types;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.eclipse.internal.xtend.type.baseimpl.OperationImpl;
import org.eclipse.xtend.expression.TypeSystem;
import org.eclipse.xtend.typesystem.Feature;
import org.eclipse.xtend.typesystem.Type;
import org.s23m.cell.Identity;
import org.s23m.cell.Set;
import org.s23m.cell.eclipse.openarchitectureware.types.singleton.IdentityType;
import org.s23m.cell.eclipse.openarchitectureware.types.singleton.RootSetType;

public final class SetType extends AbstractSetType {

	private final Set set;

	private final RootSetType rootSetType;

	private final IdentityType identityType;

	/**
	 * Constructor used to create {@link SetType}s for all {@link Set} implementations
	 *
	 * @param typeSystem
	 * @param instance
	 */
	public SetType(final TypeSystem typeSystem, final Set set, final RootSetType rootSetType, final IdentityType identityType) {
		super(typeSystem, createName(set), false, getSuperTypes(set, typeSystem, rootSetType, identityType));
		this.set = set;
		this.rootSetType = rootSetType;
		this.identityType = identityType;
	}

	private static java.util.Set<Type> getSuperTypes(final Set delegate, final TypeSystem typeSystem, final RootSetType rootSetType, final IdentityType identityType) {
		final java.util.Set<Type> result = new HashSet<Type>();
		Set metaElement = delegate;
		while (metaElement != metaElement.category()) {
			metaElement = metaElement.category();
			final Type superType = new SetType(typeSystem, metaElement, rootSetType, identityType);
			result.add(superType);
		}
		// add Type corresponding to Set interface, and its super types
		result.add(rootSetType);
		result.addAll(rootSetType.getSuperTypes());

		return result;
	}

	@Override
	public Feature[] getContributedFeatures() {
		// iterate through queries
		final Type listType = getTypeSystem().getListType(this);

		final Set queries = set.queries();
		final int size = queries.size();
		final List<Feature> features = new ArrayList<Feature>(size);
		for (final Set query : queries) {
			final Set q = query;
			final String name = q.identity().name();
			final int parameters = q.size();
			// here we assume that all queries return Sets
			if (parameters == 0) {
				// TODO generalise distinction between sets and singleton sets
				if ("filterInstances".equals(name)) {
					features.add(new OperationImpl(this, name, listType) {
						protected @Override Object evaluateInternal(final Object target, final Object[] params) {
							final Set set = (Set) target;
							return set.filterInstances();
						}
					});
				} else if ("identity".equals(name)) {
					features.add(new OperationImpl(this, name, identityType) {
						protected @Override Identity evaluateInternal(final Object target, final Object[] params) {
							final Set set = (Set) target;
							return (Identity) invokeMethod(set, name);
						}
					});
				} else {
					features.add(new ZeroParameterSetOperation(name) {
						public @Override Set evaluate(final Set target) {
							return (Set) invokeMethod(target, name);
						}
					});
				}

			} else if (parameters == 1) {
				// TODO consider queries with parameters
				if ("filterFlavor".equals(name)) {
					features.add(new OperationImpl(this, name, listType, rootSetType) {
						protected @Override Object evaluateInternal(final Object target, final Object[] params) {
							final Set set = (Set) target;
							final Set flavor = (Set) params[0];
							return new OrderedSetDecorator(set.filterFlavor(flavor));
						}
					});
				} else if ("value".equals(name)) {
					features.add(new OperationImpl(this, name, rootSetType, rootSetType) {
						protected @Override Object evaluateInternal(final Object target, final Object[] params) {
							final Set set = (Set) target;
							final Set orderedPair = (Set) params[0];
							return set.value(orderedPair);
						}
					});
				}
			}
		}
		return features.toArray(new Feature[size]);
	}

	private Object invokeMethod(final Set target, final String methodName) {
		try {
			final Class<? extends Set> targetClass = target.getClass();
			final Method method = targetClass.getMethod(methodName);
			return method.invoke(target);
		} catch (final Exception e) {
			throw new IllegalStateException("Could not invoke method '" + methodName + "' on target '" + target + "'", e);
		}
	}
}
