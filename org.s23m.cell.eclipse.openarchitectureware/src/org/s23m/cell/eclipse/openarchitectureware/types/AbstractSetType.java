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

import java.math.BigInteger;

import org.eclipse.internal.xtend.type.baseimpl.OperationImpl;
import org.eclipse.xtend.expression.TypeSystem;
import org.eclipse.xtend.typesystem.Feature;
import org.eclipse.xtend.typesystem.Type;
import org.s23m.cell.Set;

public abstract class AbstractSetType extends AbstractType {

	private final java.util.Set<Type> superTypes;

	private final boolean isAbstract;

	public AbstractSetType(final TypeSystem typeSystem, final String name, final boolean isAbstract, final java.util.Set<Type> superTypes) {
		super(typeSystem, name);
		this.isAbstract = isAbstract;
		this.superTypes = superTypes;
	}

	public static String createName(final Set delegate) {
		if (delegate == delegate.category()) {
			return createFullyQualifiedName(delegate.getClass());
		} else {
			return createQualifiedName(delegate);
		}
	}

	private static String createQualifiedName(final Set set) {
		// TODO replace by a robust, pluggable mechanism

		/*
		final Set flavor = set.flavor();

		if (flavor.isEqualTo(F_SemanticStateOfInMemoryModel.coreSets.orderedPair)
				|| flavor.isEqualTo(F_SemanticStateOfInMemoryModel.coreGraphs.orderedSet)
				|| flavor.isEqualTo(F_SemanticStateOfInMemoryModel.coreGraphs.edgeEnd)) {

			return set.getClass().getName().replace(".", SyntaxConstants.NS_DELIM);

		} else {
			final Set container = set.artifact();
			final String setIdentityName = set.identity().getTechnicalName();
			final String artifactIdentityName = container.identity().getTechnicalName();
			return artifactIdentityName + SyntaxConstants.NS_DELIM + setIdentityName;

		}
		*/
		return set.identity().identifier().toString();
	}

	@Override
	public java.util.Set<? extends Type> getSuperTypes() {
		return superTypes;
	}

	@Override
	public boolean isAbstract() {
		return isAbstract;
	}

	@Override
	public abstract Feature[] getContributedFeatures();

	protected Integer convertToInteger(final Object o) {
		if (o instanceof Integer) {
			return (Integer) o;
		} else if (o instanceof BigInteger) {
			// add warning if too large?
			return ((BigInteger) o).intValue();
		} else {
			throw new IllegalArgumentException("Could not convert " + o + " to an Integer");
		}
	}

	/* Helper classes */

	protected abstract class ZeroParameterSetOperation extends OperationImpl {

		public ZeroParameterSetOperation(final String name) {
			super(AbstractSetType.this, name, AbstractSetType.this);
		}

		@Override
		protected Object evaluateInternal(final Object target, final Object[] params) {
			return evaluate((Set) target);
		}

		public abstract Set evaluate(Set target);
	}

	protected abstract class OneParameterSetOperation extends OperationImpl {

		public OneParameterSetOperation(final String name) {
			super(AbstractSetType.this, name, AbstractSetType.this, AbstractSetType.this);
		}

		@Override
		protected Object evaluateInternal(final Object target, final Object[] params) {
			final Set parameter = (Set) params[0];
			return evaluate((Set) target, parameter);
		}

		public abstract Set evaluate(Set target, Set parameter);
	}
}
