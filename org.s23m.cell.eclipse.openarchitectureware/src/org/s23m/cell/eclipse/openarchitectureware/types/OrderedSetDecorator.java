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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.s23m.cell.G;
import org.s23m.cell.Identity;
import org.s23m.cell.Set;
import org.s23m.cell.api.EventListener;
import org.s23m.cell.api.VisitorFunction;

/**
 * Allows iteration over OrderedSets in openArchitectureWare (in FOREACH loops, for example).
 *
 * The {@link org.eclipse.internal.xpand2.ast.ForEachStatement} requires that the collection
 * to be iterated over must be a {@link java.util.Collection}, even though the implementation
 * could be refactored to only require a {@link java.lang.Iterable}.
 */
// TODO change this to be a reflective proxy, i.e., we don't have to modify it when OrderedSet's methods change
public final class OrderedSetDecorator implements Set, Collection<Set> {

	private final Set delegate;

	//public OrderedSetDecorator(final Set delegate) {
	//	this.delegate = delegate;
	//}

	public OrderedSetDecorator(final Set set) {
		if (!(set.flavor().isEqualTo( G.coreSets.orderedSet))) {
			throw new IllegalArgumentException("Set to be decorated is not an OrderedSet");
		}
		this.delegate = set;
	}

	/* Methods used by ForEachStatement */

	public Iterator<Set> iterator() {
		return delegate.iterator();
	}

	public int size() {
		return delegate.size();
	}

	/* Other methods */

	public Set addAbstract(final Set metaElement, final Set semanticIdentity) {
		return delegate.addAbstract(metaElement, semanticIdentity);
	}

	public Set addConcrete(final Set metaElement, final Set semanticIdentity) {
		return delegate.addConcrete(metaElement, semanticIdentity);
	}

	public Set addToCommands(final Set anElement) {
		return delegate.addToCommands(anElement);
	}

	public Set addToQueries(final Set anElement) {
		return delegate.addToQueries(anElement);
	}

	public Set addToValues(final Set set) {
		return delegate.addToValues(set);
	}

	public Set addToVariables(final Set set) {
		return delegate.addToVariables(set);
	}

	public Set container() {
		return delegate.container();
	}

	public Set filter(final Set metaProperty) {
		return delegate.filter(metaProperty);
	}

	public Set commands() {
		return delegate.commands();
	}

	public Set edgeEnds() {
		return delegate.edgeEnds();
	}

	//public Set connectedInstances() {
	//	return delegate.connectedInstances();
	//}

	public boolean containsSemanticMatch(final Set o) {
		return delegate.containsSemanticMatch(o);
	}

	public boolean containsSemanticMatchesForAll(final Set c) {
		return delegate.containsSemanticMatchesForAll(c);
	}

	public Set containsEdgeTo(final Set orderedPair) {
		return delegate.containsEdgeTo(orderedPair);
	}

	public Set decommission() {
		return delegate.decommission();
	}

	@Override
	public boolean equals(final Object obj) {
		return delegate.equals(obj);
	}

	public Set flavor() {
		return delegate.flavor();
	}

	public Set filterFlavor(final Set flavor) {
		return delegate.filterFlavor(flavor);
	}

	public Set from() {
		return delegate.from();
	}

	public Set fromEdgeEnd() {
		return delegate.fromEdgeEnd();
	}

	@Override
	public int hashCode() {
		return delegate.hashCode();
	}

	public Set hasVisibilityOf(final Set target) {
		return delegate.hasVisibilityOf(target);
	}

	public Identity identity() {
		return delegate.identity();
	}

	//public int indexOfRepresentation(final Set o) {
	//	return delegate.indexOfRepresentation(o);
	//}

	public Set filterInstances() {
		return delegate.filterInstances();
	}

	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	public boolean isEqualTo(final Set orderedPair) {
		return delegate.isEqualTo(orderedPair);
	}

	public boolean isEqualToRepresentation(final Set orderedPair) {
		return delegate.isEqualToRepresentation(orderedPair);
	}

	public Set isExternal() {
		return delegate.isExternal();
	}

	public Set isLocalSuperSetOf(final Set orderedPair) {
		return delegate.isLocalSuperSetOf(orderedPair);
	}

	public Set isSuperSetOf(final Set orderedPair) {
		return delegate.isSuperSetOf(orderedPair);
	}

	public Set filterLinks() {
		return delegate.filterLinks();
	}

	public ListIterator<Set> listIterator() {
		return delegate.listIterator();
	}

	public ListIterator<Set> listIterator(final int index) {
		return delegate.listIterator(index);
	}

	public Set localRootSuperSetOf(final Set orderedPair) {
		return delegate.localRootSuperSetOf(orderedPair);
	}

	public Set directSuperSetOf(final Set orderedPair) {
		return delegate.directSuperSetOf(orderedPair);
	}

	public Set category() {
		return delegate.category();
	}

	public Set queries() {
		return delegate.queries();
	}

	public Set removeFromCommands(final Set anElement) {
		return delegate.removeFromCommands(anElement);
	}

	public Set removeFromQueries(final Set anElement) {
		return delegate.removeFromQueries(anElement);
	}

	public Set to() {
		return delegate.to();
	}

	public Set toEdgeEnd() {
		return delegate.toEdgeEnd();
	}

	@Override
	public String toString() {
		return delegate.toString();
	}

	public Set value(final Set variable) {
		return delegate.value(variable);
	}

	public Set values() {
		return delegate.values();
	}

	public Set variables() {
		return delegate.variables();
	}

	public Set visibleArtifactsForSubGraph(final Set subgraph) {
		return delegate.visibleArtifactsForSubGraph(subgraph);
	}

	public boolean containsAllRepresentations(final Set c) {
		return delegate.containsAllRepresentations(c);
	}

	public boolean containsRepresentation(final Set o) {
		return delegate.containsRepresentation(o);
	}

	public Set containsDecommissionedSets() {
		return delegate.containsDecommissionedSets();
	}

	public Set containsNewSets() {
		return delegate.containsNewSets();
	}

	public Set hasNewName() {
		return delegate.hasNewName();
	}

	public Set hasNewPluralName() {
		return delegate.hasNewPluralName();
	}

	public Set isDecommissioned() {
		return delegate.isDecommissioned();
	}

	public Set isNewInstance() {
		return delegate.isNewInstance();
	}

	public Set assignNewName(final String newName) {
		return delegate.assignNewName(newName);
	}

	public Set assignNewPluralName(final String newPluralName) {
		return delegate.assignNewPluralName(newPluralName);
	}

	public Set assignNewPayload(final String newPayload) {
		return delegate.assignNewPayload(newPayload);
	}

	public Set decommissionPayload() {
		return delegate.decommissionPayload();
	}

	public Set hasDecommissionedPayload() {
		return delegate.hasDecommissionedPayload();
	}

	public Set hasNewPayload() {
		return delegate.hasNewPayload();
	}

	/* ---------------------------- *
	 * java.util.Collection methods *
	 * ---------------------------- */

	public Set[] toArray() {
		return delegate.toArray();
	}

	public Set[] toArray(final Set[] a) {
		return delegate.toArray(a);
	}

	public List<Set> asList() {
		return delegate.asList();
	}

	public boolean add(final Set o) {
		throw new UnsupportedOperationException("Not implemented");
	}

	public boolean addAll(final Collection<? extends Set> c) {
		throw new UnsupportedOperationException("Not implemented");
	}

	public void clear() {
		throw new UnsupportedOperationException("Not implemented");
	}

	public boolean contains(final Object o) {
		throw new UnsupportedOperationException("Not implemented");
	}

	public boolean containsAll(final Collection<?> c) {
		throw new UnsupportedOperationException("Not implemented");
	}

	public boolean remove(final Object o) {
		throw new UnsupportedOperationException("Not implemented");
	}

	public boolean removeAll(final Collection<?> c) {
		throw new UnsupportedOperationException("Not implemented");
	}

	public boolean retainAll(final Collection<?> c) {
		throw new UnsupportedOperationException("Not implemented");
	}

	public <T> T[] toArray(final T[] a) {
		throw new UnsupportedOperationException("Not implemented");
	}

	public Set allowableEdgeCategories(final Set orderedPair) {
		return delegate.allowableEdgeCategories(orderedPair);
	}

	public Set filterPolymorphic(final Set category) {
		return delegate.filterPolymorphic(category);
	}

	public Set semanticIdentity() {
		return delegate.semanticIdentity();
	}

	//public int indexOfIdentifier(final Set o) {
	//	return delegate.indexOfIdentifier(o);
	//}

	public Set executableQueries() {
		return delegate.executableQueries();
	}

	public Set executableCommands() {
		return delegate.executableCommands();
	}

	public Set union(final Set s) {
		return delegate.union(s);
	}

	public Set intersection(final Set s) {
		return delegate.intersection(s);
	}

	public Set complement(final Set s) {
		return delegate.complement(s);
	}

	public Set addElement(final Set semanticIdentity) {
		return delegate.addElement(semanticIdentity);
	}

	public Set removeElement(final Set semanticIdentity) {
		return delegate.removeElement(semanticIdentity);
	}

	public Set isElementOf(final Set semanticIdentity) {
		return delegate.isElementOf(semanticIdentity);
	}

	public Set transformToOrderedSetOfSemanticIdentities() {
		return delegate.transformToOrderedSetOfSemanticIdentities();
	}

	public Set processEvent(final Set event) {
		return delegate.processEvent(event);
	}

	public Set setMaintenanceCommand() {
		return delegate.setMaintenanceCommand();
	}

	public Set generatingSet() {
		return delegate.generatingSet();
	}

	public Set generatingElement() {
		return delegate.generatingElement();
	}

	public Set unionOfconnectingLinks(final Set instance) {
		return delegate.unionOfconnectingLinks(instance);
	}

	public Set not() {
		return delegate.not();
	}

	public Set and(final Set b) {
		return delegate.and(b);
	}

	public Set or(final Set b) {
		return delegate.or(b);
	}

	public Set isQuality() {
		return delegate.isQuality();
	}

	public Set isInformation() {
		return delegate.isInformation();
	}

	public boolean is_NOTAPPLICABLE() {
		return delegate.is_NOTAPPLICABLE();
	}

	public boolean is_FALSE() {
		return delegate.is_FALSE();
	}

	public boolean is_UNKNOWN() {
		return delegate.is_UNKNOWN();
	}

	public boolean is_TRUE() {
		return delegate.is_TRUE();
	}

	public Set includesValue(final Set value, final Set equivalenceClass) {
		return delegate.includesValue(value, equivalenceClass);
	}

	public Set and() {
		return delegate.and();
	}

	public Set or() {
		return delegate.or();
	}

	public String localVisualRecognitionText() {
		return delegate.localVisualRecognitionText();
	}

	public String visualRecognitionText() {
		return delegate.visualRecognitionText();
	}

	public String fullVisualRecognitionText() {
		return delegate.fullVisualRecognitionText();
	}

	public String localVisualRecognitionTextWithEdgeEnds() {
		return delegate.localVisualRecognitionTextWithEdgeEnds();
	}

	public Set elementsOfSemanticIdentitySet() {
		return delegate.elementsOfSemanticIdentitySet();

	}

	public Set filterLinks(final Set flavorOrCategory, final Set fromSet, final Set toSet) {
		return delegate.filterLinks( flavorOrCategory,  fromSet,  toSet);
	}

	public Set filterByLinkedTo(final Set toSet) {
		return delegate.filterByLinkedTo(  toSet);
	}

	public Set filterByLinkedFrom(final Set fromSet) {
		return delegate.filterByLinkedFrom( fromSet);
	}

	public Set filterByLinkedFromAndTo(final Set fromSet, final Set toSet) {
		return delegate.filterByLinkedFromAndTo( fromSet,  toSet);
	}

	public Set filterByLinkedToVia(final Set toEdgeEnd) {
		return delegate.filterByLinkedToVia( toEdgeEnd);
	}

	public Set filterByLinkedFromVia(final Set fromEdgeEnd) {
		return delegate.filterByLinkedFromVia( fromEdgeEnd);
	}

	public Set filterByLinkedFromAndToVia(final Set fromEdgeEnd, final Set toEdgeEnd) {
		return delegate.filterByLinkedFromAndToVia(  fromEdgeEnd,  toEdgeEnd);
	}

	public Set filterFrom() {
		return delegate.filterFrom();
	}

	public Set filterTo() {
		return delegate.filterTo();
	}

	public Set filterFromAndTo() {
		return delegate.filterFromAndTo();
	}

	public Set filterByLinkedToSemanticRole(final Set toSetReferencedSemanticRole) {
		return delegate.filterByLinkedToSemanticRole(toSetReferencedSemanticRole);
	}

	public Set filterByLinkedFromSemanticRole(final Set fromSetReferencedSemanticRole) {
		return delegate.filterByLinkedFromSemanticRole(fromSetReferencedSemanticRole);
	}

	public Set filterByLinkedFromAndToSemanticRole(final Set fromSetReferencedSemanticRole, final Set toSetReferencedSemanticRole) {
		return delegate.filterByLinkedFromAndToSemanticRole(  fromSetReferencedSemanticRole,  toSetReferencedSemanticRole);
	}

	public Set isEqualTo(final Set set, final Set equivalenceClass) {
		return delegate.isEqualTo(  set,  equivalenceClass);

	}

	public Set filterBySemanticIdentity(final Set set) {
		return delegate.filterBySemanticIdentity(  set);
	}

	public Set filterByEquivalenceClass(final Set set) {
		return delegate.filterByEquivalenceClass(  set);
	}

	public Set isALink() {
		return delegate.isALink(  );

	}

	public Set extractUniqueMatch(final Identity identity) {
		return delegate.extractUniqueMatch(  identity);

	}

	public Set extractUniqueMatch(final String uuidAsString) {
		return delegate.extractUniqueMatch(  uuidAsString);

	}

	public Set extractUniqueMatch(final Set set) {
		return delegate.extractUniqueMatch(  set);

	}

	public Set extractFirst() {
		return delegate.extractFirst(  );

	}

	public Set extractSecond() {
		return delegate.extractSecond(  );

	}

	public Set extractLast() {
		return delegate.extractLast(  );

	}

	public boolean isASemanticIdentity() {
		return delegate.isASemanticIdentity(  );
	}

	public Set extractNext(final Set element) {
		return delegate.extractNext( element );
	}

	public Set extractPrevious(final Set element) {
		return delegate.extractPrevious( element );
	}

	public Set initializeWalk(final VisitorFunction visitorFunction) {
		return delegate.initializeWalk( visitorFunction );
	}

	public Set walkDownThenRight(final VisitorFunction visitorFunction) {
		return delegate.walkDownThenRight( visitorFunction );
	}

	public Set walkDownThenLeft(final VisitorFunction visitorFunction) {
		return delegate.walkDownThenLeft( visitorFunction );
	}

	public Set walkRightThenDown(final VisitorFunction visitorFunction) {
		return delegate.walkRightThenDown( visitorFunction );
	}

	public Set walkLeftThenDown(final VisitorFunction visitorFunction) {
		return delegate.walkLeftThenDown( visitorFunction );
	}

	public Set addSubscriber(final EventListener instance) {
		return delegate.addSubscriber( instance );
	}

	public Set removeSubscriber(final EventListener instance) {
		return delegate.removeSubscriber( instance );
	}

}
