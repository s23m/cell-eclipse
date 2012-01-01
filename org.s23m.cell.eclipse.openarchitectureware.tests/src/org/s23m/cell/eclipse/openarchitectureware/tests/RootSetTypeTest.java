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

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.xtend.typesystem.Feature;
import org.eclipse.xtend.typesystem.Operation;
import org.eclipse.xtend.typesystem.Type;
import org.junit.Test;
import org.s23m.cell.G;

public final class RootSetTypeTest extends AbstractTypeTest {

	private static final Class<?> CLASS = org.s23m.cell.Set.class;

	private static final Set<String> EXCLUDED_OPERATIONS = new HashSet<String>(Arrays.asList(
			"addAbstract",
			"addConcrete",
			"addToCommands",
			"addToQueries",
			"addToValues",
			"addToVariables",
			"commands",
			"containsRepresentation",
			"containsAllRepresentations",
			"edgeEnds",
			"decommission",
			"fromEdgeEnd",
			"isEqualTo",
			"isEqualToRepresentation",
			"isSuperSetOf",
			"iterator",
			"listIterator",
			"category",
			"queries",
			"removeFromCommands",
			"removeFromQueries",
			"removeFromValues",
			"removeFromVariables",
			"setValue",
			"toArray",
			"toEdgeEnd",
			"value"
	));

	/**
	 * Checks that all contributed {@link Feature}s correspond
	 * to operations on the {@link org.s23m.cell.eclipse.api.Set} interface,
	 * and that all relevant operations are present
	 *
	 * @throws Exception
	 */
	// TODO adopt an implementation which does not require duplication of what is in the org.s23m.cell.eclipse.api.Set interface
	/*
	public void testComplenessOfContributedFeatures() throws Exception {
		final List<Method> setMethods = Arrays.asList(CLASS.getMethods());
		final List<Method> graphFlavorMethods = Arrays.asList(GraphFlavor.class.getMethods());

		final Set<String> methodNames = new HashSet<String>();
		for (final Method method : setMethods) {
			methodNames.add(method.getName());
		}
		for (final Method method : graphFlavorMethods) {
			methodNames.add(method.getName());
		}
		final List<Feature> features = Arrays.asList(rootSetType.getContributedFeatures());
		final Set<String> featureNames = new HashSet<String>();
		for (final Feature feature : features) {
			featureNames.add(feature.getName());
		}
		final Set<String> missingFeatures = new HashSet<String>(methodNames);
		missingFeatures.removeAll(featureNames);
		missingFeatures.removeAll(EXCLUDED_OPERATIONS);
		assertTrue("Missing features: " + missingFeatures, missingFeatures.isEmpty());

		final Set<String> additionalFeatures = new HashSet<String>(featureNames);
		additionalFeatures.removeAll(methodNames);
		assertTrue("Additional features: " + additionalFeatures, additionalFeatures.isEmpty());
	}
	*/

	@Test
	public void testCorrectnessOfContributedFeatures() throws Exception {
		final List<Feature> features = Arrays.asList(rootSetType.getContributedFeatures());

		final org.s23m.cell.Set set = G.coreGraphs.graph;

		for (final Feature feature : features) {
			assertTrue(feature instanceof Operation);
			final Operation operation = (Operation) feature;
			final String name = operation.getName();
			final List<Type> parameterTypes = operation.getParameterTypes();
			// TODO add tests for operations with parameters
			if (parameterTypes.size() == 0) {
				final Method method = getMethod(name);
				assertNotNull("Method named '" + name + "' was not found", method);

				final Object evaluationResult = operation.evaluate(set, new Object[0]);
				assertNotNull(evaluationResult);
				final Object invocationResult = method.invoke(set);

				if ("identity".equals(name) || "isEmpty".equals(name) || "size".equals(name)) {
					assertEquals(evaluationResult, invocationResult);
				} else {
					assertTrue("Evaluation of '" + name + "' operation was not a Set", evaluationResult instanceof org.s23m.cell.Set);
					assertTrue(invocationResult instanceof org.s23m.cell.Set);

					final org.s23m.cell.Set evaluationResultSet = (org.s23m.cell.Set) evaluationResult;
					final org.s23m.cell.Set invocationResultSet = (org.s23m.cell.Set) invocationResult;
					assertTrue(evaluationResultSet.isEqualTo(invocationResultSet));
				}
			}
		}
	}

	private Method getMethod(final String name) throws Exception {
		try {
			final Method method = CLASS.getMethod(name);
			return method;
		} catch (final Exception e) {
			for (final Method method : org.s23m.cell.Set.class.getMethods()) {
			//	for (final Method method : Graph.class.getMethods()) {
				if (method.getName().equals(name)) {
					return method;
				}
			}
			return null;
		}
	}
}
