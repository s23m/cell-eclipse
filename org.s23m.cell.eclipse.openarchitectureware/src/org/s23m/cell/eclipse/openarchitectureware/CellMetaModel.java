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

package org.s23m.cell.eclipse.openarchitectureware;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.internal.xtend.expression.parser.SyntaxConstants;
import org.eclipse.xtend.expression.TypeSystem;
import org.eclipse.xtend.typesystem.MetaModel;
import org.eclipse.xtend.typesystem.Type;
import org.s23m.cell.G;
import org.s23m.cell.Identity;
import org.s23m.cell.Set;
import org.s23m.cell.api.CoreSets;
import org.s23m.cell.eclipse.openarchitectureware.types.SetType;
import org.s23m.cell.eclipse.openarchitectureware.types.singleton.IdentityType;
import org.s23m.cell.eclipse.openarchitectureware.types.singleton.KernelValuesType;
import org.s23m.cell.eclipse.openarchitectureware.types.singleton.RootSetType;

/**
 * This class is the entry point for Gmodel - openArchitectureWare integration
 */
public final class CellMetaModel implements MetaModel {

	private static final String NAME = CellMetaModel.class.getSimpleName();

	private TypeSystem typeSystem;

	private final Map<String, Type> typeCache;

	private IdentityType identityType;

	private RootSetType rootSetType;

	private KernelValuesType kernelValuesType;

	/**
	 * Instance (container) used as the basis for constructing a metamodel
	 */
	private final Set set;

	public CellMetaModel(final Set set) {
		this.typeCache = new HashMap<String, Type>();
		this.set = set;
	}

	/**
	 * @see org.eclipse.xtend.typesystem.MetaModel#getTypeSystem()
	 */
	public TypeSystem getTypeSystem() {
		return typeSystem;
	}

	/**
	 * @see org.eclipse.xtend.typesystem.MetaModel#setTypeSystem(org.eclipse.xtend.expression.TypeSystem)
	 */
	public void setTypeSystem(final TypeSystem typeSystem) {
		// disallow overriding TypeSystem once set
		if (this.typeSystem == null) {

			this.typeSystem = typeSystem;

			addDefaultTypesToCache(typeSystem);
			addSetsToCache(set);
		}
	}

	private void addDefaultTypesToCache(final TypeSystem typeSystem) {
		// add Identity type
		identityType = new IdentityType(typeSystem);
		addTypeToCache(identityType);

		// add Type corresponding to Set interface
		rootSetType = new RootSetType(typeSystem, identityType);
		addTypeToCache(rootSetType);

		kernelValuesType = new KernelValuesType(typeSystem, rootSetType);
		addTypeToCache(kernelValuesType);

		// TODO add other kernel types?
		final SetType vertexType = new SetType(getTypeSystem(), G.coreGraphs.vertex, rootSetType, identityType);
		addTypeToCache(vertexType);
	}

	private void addSetsToCache(final Set set) {
		addSetToCache(set);
		final Set vertexFlavored = set.filterFlavor(G.coreGraphs.vertex);
		for (final Set i : vertexFlavored) {
			addSetsToCache(i);
		}
	}

	private Type addSetToCache(final Set set) {
		final SetType type = new SetType(getTypeSystem(), set, rootSetType, identityType);
		addTypeToCache(type);
		return type;
	}

	private void addTypeToCache(final Type type) {
		typeCache.put(type.getName(), type);
	}

	/**
	 * @see org.eclipse.xtend.typesystem.MetaModel#getTypeForName(java.lang.String)
	 */
	public Type getTypeForName(final String typeName) {
		final Type type = typeCache.get(typeName);
		return type;
	}

	/**
	 * @see org.eclipse.xtend.typesystem.MetaModel#getType(java.lang.Object)
	 */
	public Type getType(final Object obj) {
		if (obj instanceof Set) {
			final Set set = (Set) obj;
			final String name = SetType.createName(set);
			final Type cachedType = typeCache.get(name);
			if (cachedType == null) {
				return addSetToCache(set);
			} else {
				return cachedType;
			}
		} else if (obj instanceof Identity) {
			return identityType;
		} else if (obj instanceof CoreSets || obj instanceof KernelValuesType) {
			return kernelValuesType;
		} else {
			// fall back to basic types in built-in meta models
			return null;
		}
	}

	/**
	 * @see org.eclipse.xtend.typesystem.MetaModel#getKnownTypes()
	 */
	public java.util.Set<Type> getKnownTypes() {
		// TODO switch to "standard" caching mechanism
		final Collection<Type> col = typeCache.values();
		return (java.util.Set<Type>) (col instanceof java.util.Set<?> ? col : new HashSet<Type>(col));
	}

	/**
	 * Returns the metamodel name.
	 *
	 * @return the metamodel name
	 */
	public String getName() {
		return NAME;
	}

	public java.util.Set<String> getNamespaces() {
		final HashSet<String> results = new HashSet<String>();
		// TODO come up with a more efficient implementation - cache results?
		for (final Type type : getKnownTypes()) {
			final String name = type.getName();
			final String namespace = name.substring(0, name.lastIndexOf(SyntaxConstants.NS_DELIM));
			results.add(namespace);
		}
		return results;
	}

	public KernelValuesType getKernelValuesType() {
		return kernelValuesType;
	}

	public RootSetType getRootSetType() {
		return rootSetType;
	}
}
