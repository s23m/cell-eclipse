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
 * The Original Code is S23M.
 *
 * The Initial Developer of the Original Code is
 * The S23M Foundation.
 * Portions created by the Initial Developer are
 * Copyright (C) 2012 The S23M Foundation.
 * All Rights Reserved.
 *
 * Contributor(s):
 * Jorn Bettin
 * Chul Kim
 * ***** END LICENSE BLOCK ***** */

package org.s23m.cell.serialization.serializer;

import java.util.ArrayList;
import java.util.List;

import org.s23m.cell.S23MKernel;
import org.s23m.cell.Identity;
import org.s23m.cell.Set;
import org.s23m.cell.api.models.S23MSemanticDomains;
import org.s23m.cell.api.models.Root;
import org.s23m.cell.api.models.SemanticDomain;
import org.s23m.cell.serialization.EdgeType;
import org.s23m.cell.serialization.S23M;
import org.s23m.cell.serialization.InstanceType;
import org.s23m.cell.serialization.InstantiationSemantic;
import org.s23m.cell.serialization.LinkType;
import org.s23m.cell.serialization.ObjectFactory;
import org.s23m.cell.serialization.SuperSetReferenceType;
import org.s23m.cell.serialization.ValueType;
import org.s23m.cell.serialization.VariableType;
import org.s23m.cell.serialization.VisibilityType;

public final class SerializationMapper {

	private final ObjectFactory objectFactory;
	// private static final String ANON_NAME = new IdentityFactory().createAnonymousIdentity().name();
	private static final String ANON_NAME = S23MSemanticDomains.anonymous.identity().name();

	protected SerializationMapper() {
		objectFactory = InstanceBuilder.getObjectFactory();
	}

	private void addInstanceToInstanceSet(
			final S23M.Instance serializedRootInstance, final Set set) {
		final InstanceType serializedVertexInstance = objectFactory.createInstanceType();
		mapVertexToInstanceType(set, serializedVertexInstance, false);
		serializedRootInstance.getInstance().add(serializedVertexInstance);
	}

	private String getEdgeMetaElement(final Set edge) {
		return edge.category().identity().uniqueRepresentationReference().toString();
	}

	private InstantiationSemantic getInstantiationSemantic(final Set instance) {
		InstantiationSemantic semantic = null;
		if (isRootAttached(instance)) {
			semantic = InstantiationSemantic.CONCRETE_VERTEX; //either semantic domain or models
		} else {
			if (isConcrete(instance)) {
				if (isSemanticDomainType(instance)) {
					semantic = getSemanticDomainType(instance);
				} else if (isTopRepositorySet(instance)) {
					semantic = InstantiationSemantic.INSTANTIATED_CONCRETE_ARTIFACT;
				} else {
					semantic = InstantiationSemantic.CONCRETE_VERTEX;
				}
			} else {
				if (isSemanticDomainType(instance)) {
					semantic = getSemanticDomainType(instance);
				} else if (isTopRepositorySet(instance)) {
					semantic = InstantiationSemantic.ABSTRACT_VERTEX;
				} else {
					semantic = InstantiationSemantic.ABSTRACT_VERTEX;
				}
			}

		}
		return semantic;
	}

	private InstantiationSemantic getSemanticDomainType(final Set instance) {
		final boolean isAnon = instance.identity().name().equals(ANON_NAME) ? true: false;
		final Set metaSet = instance.category();
		InstantiationSemantic type = null;
		if (instance.isEqualToRepresentation(S23MSemanticDomains.sandboxSemanticDomains) ||
				instance.isEqualToRepresentation(S23MSemanticDomains.agentSemanticDomains)) {
			type = InstantiationSemantic.INSTANTIATE_SEMANTIC_DOMAIN;
		} else if (metaSet.isEqualToRepresentation(SemanticDomain.disjunctSemanticIdentitySet)) {
			if (isAnon) {
				type = InstantiationSemantic.ANON_DISJUNCT_SEMANTIC_IDENTITY_SET_ADDITION;
			} else {
				type = InstantiationSemantic.DISJUNCT_SEMANTIC_IDENTITY_SET_ADDITION;
			}
		} else if (metaSet.isEqualToRepresentation(SemanticDomain.semanticRole)) {
			if (isAnon) {
				type = InstantiationSemantic.ANON_SEMANTIC_ROLE_ADDITION;
			} else {
				type = InstantiationSemantic.SEMANTIC_ROLE_ADDITION;
			}
		} else {
			type = InstantiationSemantic.SEMANTIC_DOMAIN_ADDITION;
		}
		return type;
	}

	/*
	 * Given an identity, return a string that represents the unique representation reference
	 * @param id Identity
	 * @return String unique representation reference string
	 */
	private String getURR(final Identity id) {
		return id.uniqueRepresentationReference().toString();
	}

	private boolean isConcrete(final Set vertex) {
		return vertex.value(S23MSemanticDomains.isAbstract) == (S23MSemanticDomains.isAbstract_FALSE);
	}

	private boolean isRootAttached(final Set instance) {
		return instance.container().isEqualTo(Root.root);
	}

	private boolean isSemanticDomainType(final Set instance) {
		final Set metaSet = instance.category();
		boolean isSemanticDomainSet = false;
		if (metaSet.isEqualToRepresentation(SemanticDomain.disjunctSemanticIdentitySet)) {
			isSemanticDomainSet = true;
		} else if (metaSet.isEqualToRepresentation(SemanticDomain.semanticRole)) {
			isSemanticDomainSet = true;
		} else if (metaSet.isEqualToRepresentation(SemanticDomain.semanticdomain)) {
			isSemanticDomainSet = true;
		}
		return isSemanticDomainSet;
	}

	private boolean isTopRepositorySet(final Set instance) {
		return
		instance.container().isEqualTo(Root.semanticdomains) ||
		instance.container().isEqualTo(Root.models);
	}

	/**
	 * Map an S23M Edge to an EdgeType
	 * @mappedInstance InstanceType
	 * @param edge S23M Edge
	 * */
	private void mapEdge(final InstanceType mappedInstance, final Set edge) {
		LinkType mappedLink;
		if (mappedInstance.getLink() != null) {
			mappedLink = mappedInstance.getLink();
		} else {
			mappedLink = objectFactory.createLinkType();
			mappedInstance.setLink(mappedLink);
		}
		final EdgeType mappedEdge = objectFactory.createEdgeType();
		mappedLink.getVisibilityAndEdgeAndEdgeTrace().add(mappedEdge);
		mapEdgeToEdgeType(edge, mappedEdge);
	}

	/**
	 * Map an S23M Edge to an EdgeType
	 * @param edge S23M Edge
	 * @param mappedEdge EdgeType
	 */
	private void mapEdgeToEdgeType (final Set edge, final EdgeType mappedEdge) {
		final Set metaEdge = edge.category();
		//final Set fromVertex = edge.connectedInstances().get(0); // first edgeend
		final Set fromVertex = edge.from(); // first edgeend
		//final Set toVertex = edge.connectedInstances().get(1); // second edgeend
		final Set toVertex = edge.to(); // second edgeend

		int index = 0;
		for (final Set e : edge.edgeEnds()) {
			final Set ee = e;
			final org.s23m.cell.serialization.EdgeType.EdgeEnd mappedEE = objectFactory.createEdgeTypeEdgeEnd();
			if (index == 0) {
				mappedEE.setInstanceId(getURR(fromVertex.identity()));
			} else {
				mappedEE.setInstanceId(getURR(toVertex.identity()));
			}
			final Set minValue = ee.value(S23MSemanticDomains.minCardinality);
			final Set maxValue = ee.value(S23MSemanticDomains.maxCardinality);
			final String iMin = minValue.identity().name();
			final String iMax = maxValue.identity().name();
			mappedEE.setIsContainer(ee.value(S23MSemanticDomains.isContainer).equals(
					S23MSemanticDomains.isContainer_TRUE));
			mappedEE.setIsNavigable(ee.value(S23MSemanticDomains.isNavigable).equals(
					S23MSemanticDomains.isNavigable_TRUE));
			mappedEE.setMinCardinality(iMin);
			mappedEE.setMaxCardinality(iMax);
			if (metaEdge != S23MKernel.coreGraphs.edge) {
				if (index == 0) {
					mappedEE.setMetaElement(getURR( metaEdge.edgeEnds().extractFirst().identity()));
				} else {
					mappedEE.setMetaElement(getURR( metaEdge.edgeEnds().extractSecond().identity()));
				}
			} else {
				mappedEE.setMetaElement(getURR(S23MKernel.coreGraphs.edgeEnd.identity()));
			}
			mappedEE.setSemanticIdentity(InstanceBuilder.mapSemanticIdentity(ee.identity()));
			mappedEdge.getEdgeEnd().add(mappedEE);
			index++;
		}
		mappedEdge.setSemanticIdentity(InstanceBuilder.mapSemanticIdentity(edge.identity()));
		mappedEdge.setMetaElement(getEdgeMetaElement(edge));
	}

	/**
	 * Walk the given Session from bottom to top and build a corresponding
	 * hierarchy with the serialization classes
	 * 
	 * @param rootVertex
	 * @return List<S23M>
	 */
	public List<S23MContent> mapRoot(final Set rootVertex) {
		final List<S23MContent> S23Ms = new ArrayList<S23MContent>();

		serializeRootInstance(S23Ms, Root.models);

		for ( final Set set: rootVertex.filterInstances()) {
			if (InstanceBuilder.isTopSemanticDomainSet(set)) {
				//process semantic domain instances
				for (final Set innerSet : set.filterInstances()) {
					if (InstanceBuilder.IsSemanticDomainTopInstances(innerSet)) {
						serializeSemanticInstance(S23Ms, innerSet);
					}
				}
			}
		}
		return S23Ms;
	}

	/**
	 * The given vertex is serialized
	 * @param vertex
	 * @return
	 */
	public S23MContent mapInstance(final Set vertex, final boolean isKernelSerialization) {
		final List<S23MContent> S23Ms = new ArrayList<S23MContent>();
		serializeInstance(S23Ms, vertex, isKernelSerialization);
		if (!S23Ms.isEmpty()) {
			return S23Ms.get(0);
		} else {
			throw new IllegalStateException("No serialization is done");
		}
	}

	private void mapSuperSetReference(final InstanceType mappedInstance, final Set superSetReference) {
		LinkType mappedLink;
		if (mappedInstance.getLink() != null) {
			mappedLink = mappedInstance.getLink();
		} else {
			mappedLink = objectFactory.createLinkType();
			mappedInstance.setLink(mappedLink);
		}
		final SuperSetReferenceType mappedSuperRef = objectFactory.createSuperSetReferenceType();
		mappedSuperRef.setSuperSetInstance(getURR((superSetReference).to().identity()));
		mappedSuperRef.setSubSetInstance(getURR((superSetReference).from().identity()));
		mappedSuperRef.setId(getURR(superSetReference.identity()));
		mappedSuperRef.setSemanticIdentity(InstanceBuilder.mapSemanticIdentity(superSetReference.identity()));
		mappedLink.getVisibilityAndEdgeAndEdgeTrace().add(mappedSuperRef);
	}

	private void mapValues(final Set vertex, final InstanceType mappedSubInstance) {
		for (final Set value : vertex.values()) {
			final Set valuePair = value;
			final ValueType mappedValue = objectFactory.createValueType();
			final org.s23m.cell.serialization.OrderedPair orderedPair = objectFactory.createOrderedPair();
			orderedPair.setElement(InstanceBuilder.mapSemanticIdentity(valuePair.identity()));
			orderedPair.setMetaElement(InstanceBuilder.mapSemanticIdentity(valuePair.category().identity()));
			mappedValue.setValuePair(orderedPair);
			if (mappedSubInstance.getValues() == null) {
				mappedSubInstance.setValues(objectFactory.createInstanceTypeValues());
			}
			mappedSubInstance.getValues().getValue().add(mappedValue);
		}
	}

	private void mapVariables(final Set vertex,
			final InstanceType mappedSubInstance) {
		for (final Set var : vertex.variables()) {
			final Set varVertex = var;
			final VariableType varType = objectFactory.createVariableType();
			final org.s23m.cell.serialization.OrderedPair varPair = objectFactory.createOrderedPair();
			varPair.setElement(InstanceBuilder.mapSemanticIdentity(varVertex.identity()));
			varPair.setMetaElement(InstanceBuilder.mapSemanticIdentity(varVertex.category().identity()));
			varType.setVariablePair(varPair);
			if (mappedSubInstance.getVariables() == null) {
				mappedSubInstance.setVariables(objectFactory.createInstanceTypeVariables());
			}
			mappedSubInstance.getVariables().getVariable().add(varType);
		}
	}

	private void mapVertexToS23MInstance(final InstanceType instance,
			final S23M.Instance mappedInstance,
			final boolean isSerializedInstance) {
		mappedInstance.setId(instance.getId());
		mappedInstance.setArtifact(instance.getArtifact());
		mappedInstance.setMetaElement(instance.getMetaElement());
		mappedInstance.setIsSerializationArgument(isSerializedInstance);
		mappedInstance.setIsAbstract(instance.isIsAbstract());
		mappedInstance.setType(instance.getType());
		mappedInstance.setSemanticIdentity(InstanceBuilder.mapSemanticIdentity(instance
				.getSemanticIdentity()));
	}

	/**
	 * Map a given Vertex to an IntanceType
	 * @param instance Vertex to be mapped
	 * @param mappedInstance Target IntanceType
	 * @param isSerializedInstance Is this Vertex instance the serialization argument?
	 */
	private void mapVertexToInstanceType(final Set instance,
			final InstanceType mappedInstance,
			final boolean isSerializedInstance) {
		mappedInstance.setId(getURR(instance.identity()));
		mappedInstance.setArtifact(getURR(instance.container().identity()));
		mappedInstance.setMetaElement(getURR(instance.category().identity()));
		mappedInstance.setIsSerializationArgument(isSerializedInstance);
		mappedInstance.setIsAbstract(!instance.value(S23MSemanticDomains.isAbstract)
				.equals(S23MSemanticDomains.isAbstract_FALSE));
		mappedInstance.setType(getInstantiationSemantic(instance));
		mappedInstance.setSemanticIdentity(InstanceBuilder.mapSemanticIdentity(instance
				.identity()));
	}

	/**
	 * Map a given Visibility instance to a VisibilityType and set it to the mapped Vertex instance
	 * @param mappedInstance Mapped Vertex instance
	 * @param visibility Visibility instance to be mapped
	 */
	private void mapVisibility(final InstanceType mappedInstance,
			final Set visibility) {
		LinkType mappedLink;
		if (mappedInstance.getLink() != null) {
			mappedLink = mappedInstance.getLink();
		} else {
			mappedLink = objectFactory.createLinkType();
			mappedInstance.setLink(mappedLink);
		}
		final VisibilityType mappedVisible = objectFactory.createVisibilityType();
		mappedVisible.setSourceInstance(getURR((visibility).from().identity()));
		mappedVisible.setTargetInstance(getURR((visibility).to().identity()));
		mappedVisible.setId(getURR(visibility.identity()));
		mappedVisible.setSemanticIdentity(InstanceBuilder.mapSemanticIdentity(visibility.identity()));
		mappedLink.getVisibilityAndEdgeAndEdgeTrace().add(mappedVisible);
	}

	private void serializeInstance(final List<S23MContent> S23Ms, final Set instance, final boolean isKernelSerialization, final boolean isSemanticInstance, final boolean isRecursive) {
		final S23M serializedModel = objectFactory.createS23M();
		final InstanceType serializedInstance = objectFactory.createInstanceType();
		final S23M.Instance serializedRootInstance = objectFactory.createS23MInstance();
		mapVertexToInstanceType(instance, serializedInstance, true);
		mapVertexToS23MInstance(serializedInstance, serializedRootInstance, true);
		serializedModel.getInstance().add(serializedRootInstance);

		mapValues(instance, serializedInstance);
		mapVariables(instance, serializedInstance);

		for (final Set set : instance.filterInstances()) {
			if (set.properClass().isEqualTo(S23MKernel.coreGraphs.edge)) {
				mapEdge(serializedRootInstance, set);
			} else if (set.properClass().isEqualTo(S23MKernel.coreGraphs.visibility)) {
				mapVisibility(serializedRootInstance, set);
			} else if (set.properClass().isEqualTo(S23MKernel.coreGraphs.superSetReference)) {
				mapSuperSetReference(serializedRootInstance, set);
			}
		}

		for (final Set set : instance.filterInstances()) {
			//add instance reference
			if (isKernelSerialization && set.properClass().isEqualTo(S23MKernel.coreGraphs.vertex)) {
				addInstanceToInstanceSet(serializedRootInstance, set);
			} else {
				if (isSemanticInstance && set.properClass().isEqualTo(S23MKernel.coreGraphs.vertex)) {
					addInstanceToInstanceSet(serializedRootInstance, set);
				} else if(InstanceBuilder.IsSemanticDomainTopInstances(set)) {
					addInstanceToInstanceSet(serializedRootInstance, set);
				} else if (set.properClass().isEqualTo(S23MKernel.coreGraphs.vertex)) {
					addInstanceToInstanceSet(serializedRootInstance, set);
				}
			}
		}

		S23Ms.add(new S23MContent(instance.identity().uniqueRepresentationReference().toString(),
				serializedModel));
		if (isRecursive) {
			for (final Set set : instance.filterInstances()) {
				if (isSemanticInstance && set.properClass().isEqualTo(S23MKernel.coreGraphs.vertex)) {
					serializeInstance(S23Ms, set, isKernelSerialization, isSemanticInstance, true);
				} else if (set.properClass().isEqualTo(S23MKernel.coreGraphs.vertex)){ //&& InstanceBuilder.isSerializableInstance(set)) {
					serializeInstance(S23Ms, set, isKernelSerialization, isSemanticInstance, true);
				}
			}
		}
	}

	private void serializeRootInstance(final List<S23MContent> S23Ms, final Set models) {
		if (models.properClass().isEqualTo(S23MKernel.coreGraphs.vertex)) {
			serializeInstance(S23Ms, models, false, false, true);
		}
	}

	private void serializeInstance(final List<S23MContent> S23Ms, final Set models, final boolean isKernelSerialization) {
		if (models.properClass().isEqualTo(S23MKernel.coreGraphs.vertex)) {
			serializeInstance(S23Ms, models, isKernelSerialization, false, false);
		}
	}

	private void serializeSemanticInstance(final List<S23MContent> S23Ms, final Set instance) {
		if (instance.properClass().isEqualTo(S23MKernel.coreGraphs.vertex)) {
			serializeInstance(S23Ms, instance, false, true, true);
		}
	}

}
