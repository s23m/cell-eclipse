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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.s23m.cell.G;
import org.s23m.cell.Set;
import org.s23m.cell.eclipse.openarchitectureware.types.OrderedSetDecorator;
import org.junit.Test;

public class OrderedSetDecoratorTest extends TestCase {

	@Test
	public void testConstruction() {
		final Set o = G.coreSets.orderedSet;
		new OrderedSetDecorator(o);

		try {
			new OrderedSetDecorator(G.coreGraphs.graph);
			fail("Exception expected");
		} catch (final IllegalArgumentException e) {
			// expected
		}
	}

	@Test
	public void testMethodsRequiredForIteration() {
		final Set orderedSet = G.coreSets.orderedSet;
		final OrderedSetDecorator decorator = new OrderedSetDecorator(orderedSet);
		assertEquals(orderedSet.size(), decorator.size());

		final List<Set> listFromIteratingOverOrderedSet = new ArrayList<Set>();
		final Iterator<Set> osI = orderedSet.iterator();
		while (osI.hasNext()) {
			listFromIteratingOverOrderedSet.add(osI.next());
		}
		final List<Set> listFromIteratingOverDecorator = new ArrayList<Set>();
		final Iterator<Set> decoratorI = orderedSet.iterator();
		while (decoratorI.hasNext()) {
			listFromIteratingOverDecorator.add(decoratorI.next());
		}
		assertEquals(listFromIteratingOverOrderedSet, listFromIteratingOverDecorator);
	}

}
