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
 * Copyright (C) 2009-2010 Sofismo AG.
 * All Rights Reserved.
 *
 * Contributor(s):
 * Jorn Bettin
 * Chul Kim
 * ***** END LICENSE BLOCK ***** */

package org.s23m.cell.eclipse.visualization.graph.rendering;


import org.eclipse.draw2d.geometry.Point;
import org.s23m.cell.S23MKernel;
import org.s23m.cell.Identity;
import org.s23m.cell.Set;
import org.s23m.cell.api.Instantiation;
import org.s23m.cell.api.models.S23MSemanticDomains;
import org.s23m.cell.api.models.Root;
import org.s23m.cell.api.models2.RepositoryStructure;
import org.s23m.cell.api.models2.Visualization;

public class InstanceCoordinates {
	// TODO Ensure that this class (and Eclipse based visualisation and storage of layout info) works with the latest version of the graphVisualization model

	//ALL these constants are temporary as currently all outershell UUIDs are volatile and it is not possible to
	//create a registry of them
	protected static final String STRUCTURE = org.s23m.cell.api.models2.Visualization.structure.identity().name();
	protected static final String REUSE = org.s23m.cell.api.models2.Visualization.reuse.identity().name();
	protected static final String VISIBILITIES = org.s23m.cell.api.models2.Visualization.visibilities.identity().name();

	private final String INIT_COORD_VALUE = "0";
	private static final Set coordDomain = Instantiation.addSemanticDomain("coordinates domain", "coordinates domains", S23MSemanticDomains.agentSemanticDomains);
	private static final String ARTIFACT_VISUALIZATION_INSTANCE = org.s23m.cell.api.models2.Visualization.graphVisualization.identity().name();
	private static final String VISUALIZATION = org.s23m.cell.api.models2.Visualization.visualizedGraph.identity().name();
	private static final String SYMBOL = org.s23m.cell.api.models2.Visualization.symbol.identity().name();
	private static final String REPRESENTATION = org.s23m.cell.api.models2.Visualization.representation.identity().name();
	private static final String DIAGRAM = org.s23m.cell.api.models2.Visualization.diagram.identity().name();
	private static final String X = org.s23m.cell.api.models2.Visualization.x.identity().name();
	private static final String Y = org.s23m.cell.api.models2.Visualization.y.identity().name();
	private static final String Z = org.s23m.cell.api.models2.Visualization.z.identity().name();
	private static final String ANON_NAME = S23MSemanticDomains.anonymous.identity().name();

	/* TODO create semantic identities for these two strings! */
	public static final String VISUALIZED_ARTIFACT = "visualized graph";
	public static final String REPRESENTED_INSTANCE = "represented instance";

	private static final InstanceCoordinates instance = new InstanceCoordinates();

	private static class InstanceCoordinatesHoler {
	     public static final InstanceCoordinates INSTANCE = new InstanceCoordinates();
	}

	public static InstanceCoordinates getInstance() {
		return InstanceCoordinatesHoler.INSTANCE;
	}

	private InstanceCoordinates() {
    }

	public Set addArtifactInstanceToVisualize(final Set artifactInstance) {
		final Set viz = RepositoryStructure.graphVisualizations.addConcrete(getArtifactVisualizationInstance(),
				//F_SemanticStateOfInMemoryModel.addDisjunctSemanticIdentitySet(artifactInstance.identity().getName()+" graph visualizedGraph", artifactInstance.identity().getPluralName()+" container graphVisualizations", testDomain));
				Instantiation.addDisjunctSemanticIdentitySet(artifactInstance.identity().name()+" "+ org.s23m.cell.api.models2.Visualization.graphVisualization.identity().name(), artifactInstance.identity().pluralName()+" "+ org.s23m.cell.api.models2.Visualization.graphVisualization.identity().pluralName(), coordDomain));

		//payload of the visualized container instance contains the uuid of the container instance
		viz.identity().setPayload(artifactInstance.identity().uniqueRepresentationReference().toString());
		addArtifactInstanceToVisualize(viz, artifactInstance);
		return viz;
	}

//	public Set getArtifactVisualizations() {
//		return RepositoryStructure.graphVisualizations;
//	}

	private Set addArtifactInstanceToVisualize(final Set artifactVisualization, final Set artifactInstanceToVisualize) {
		//Instance w reference to the container instance to be visualized
		final Set viz = artifactVisualization.addConcrete(getVisualizationVertexByName(VISUALIZATION),
				Instantiation.addDisjunctSemanticIdentitySet(artifactInstanceToVisualize.identity().name()+" "+ org.s23m.cell.api.models2.Visualization.visualizedGraph.identity().name(),
						artifactInstanceToVisualize.identity().pluralName()+" "+ org.s23m.cell.api.models2.Visualization.graphVisualization.identity().pluralName(), coordDomain));
		viz.identity().setPayload(artifactInstanceToVisualize.identity().uniqueRepresentationReference().toString());
		createNonContainmentReference(getVisualizationEdgeById(Visualization.visualizedGraph_to_graph.identity()), viz, artifactInstanceToVisualize);
		//create containment links for three visualizedGraph aspects
		final Set struct = artifactVisualization.addConcrete(getVisualizationVertexByName(STRUCTURE),
				Instantiation.addDisjunctSemanticIdentitySet("", "", coordDomain));
		struct.identity().setPayload(getVisualizationVertexByName(STRUCTURE).identity().uniqueRepresentationReference().toString());
		final Set visibilities = artifactVisualization.addConcrete(getVisualizationVertexByName(VISIBILITIES),
				Instantiation.addDisjunctSemanticIdentitySet("", "", coordDomain));
		final Set reuse = artifactVisualization.addConcrete(getVisualizationVertexByName(REUSE),
				Instantiation.addDisjunctSemanticIdentitySet("", "", coordDomain));
		final Set structDiag = createDiagram(artifactVisualization, getVisualizationVertexByName(STRUCTURE));
		final Set visibilitiesDiag = createDiagram(artifactVisualization, getVisualizationVertexByName(VISIBILITIES));
		final Set reuseDiag = createDiagram(artifactVisualization, getVisualizationVertexByName(REUSE));
		createContainmentVisualizationLink(getVisualizationEdgeById(Visualization.visualizedAspect_to_diagram.identity()), struct, structDiag);
		createContainmentVisualizationLink(getVisualizationEdgeById(Visualization.visualizedAspect_to_diagram.identity()), visibilities, visibilitiesDiag);
		createContainmentVisualizationLink(getVisualizationEdgeById(Visualization.visualizedAspect_to_diagram.identity()), reuse, reuseDiag);

		return viz;
	}

	public Set addInstanceToVisualize(final Set instanceToVisualize, final Set artifactVisualization, final Set visualizationAspect) {
		final Set vizInstance = artifactVisualization.addConcrete(getVisualizationVertexByName(REPRESENTATION),
				Instantiation.addDisjunctSemanticIdentitySet("","", coordDomain));
		vizInstance.identity().setPayload(getRepresentationInstanceKey(instanceToVisualize, visualizationAspect));

		createNonContainmentReference(getVisualizationEdgeById(Visualization.representation_to_representedInstance.identity()),
									vizInstance, instanceToVisualize);
		//Set up coords
		final Set instanceXCoord = artifactVisualization.addConcrete(getVisualizationVertexByName(X),
				Instantiation.addDisjunctSemanticIdentitySet("", "", coordDomain));
		instanceXCoord.identity().setPayload(INIT_COORD_VALUE);
		createContainmentVisualizationLink(getVisualizationEdgeById(Visualization.representation_to_x.identity()),
				vizInstance, instanceXCoord);
		final Set instanceYCoord = artifactVisualization.addConcrete(getVisualizationVertexByName(Y),
				Instantiation.addDisjunctSemanticIdentitySet("", "", coordDomain));
		instanceYCoord.identity().setPayload(INIT_COORD_VALUE);
		createContainmentVisualizationLink(getVisualizationEdgeById(Visualization.representation_to_y.identity()),
				vizInstance, instanceYCoord);
		final Set instanceIcon = artifactVisualization.addConcrete(getVisualizationVertexByName(SYMBOL),
				Instantiation.addDisjunctSemanticIdentitySet("", "", coordDomain));
		// TODO Chul to confirm the deletion of the statement below. Given the latest version of graphVisualization model it should be obsolete
		//createContainmentVisualizationLink(getVisualizationEdgeById(Visualization.visualization_of_semantic_domain_to_symbols.identity()),
		//		vizInstance, instanceIcon);
		//add the instance to the diagram instance that belongs to the struct aspect
		final Set diagramInstance = findDiagramInstance(artifactVisualization, getVisualizationVertexByName(STRUCTURE));
		createContainmentVisualizationLink(getVisualizationEdgeById(Visualization.diagram_to_representation.identity()),
				diagramInstance, vizInstance);
		return vizInstance;
	}

	public Set getVisualizationVertexByName(final String name) {
		for (final Set s : getArtifactVisualizationInstance().filterInstances()) {
			if (s.properClass().isEqualTo(S23MKernel.coreGraphs.vertex)) {
				if (s.identity().name().equals(name)) {
					return s;
				}
			}
		}
		throw new IllegalStateException("No visualizedGraph instance is found.");
	}

	public Set getVisualizationEdgeById(final Identity id) {
		for (final Set s : getArtifactVisualizationInstance().filterArrows()) {
				if (s.properClass().isEqualTo(S23MKernel.coreGraphs.edge)) {
					if (s.identity().name().equals(id.name())) {
						return s;
					}
				}
		}
		throw new IllegalStateException("No visualizedGraph instance is found.");
	}

	private Set createContainmentVisualizationLink(final Set metaLink, final Set firstSet, final Set secondSet) {
		final Set link =
			 Instantiation.arrow(metaLink,
					 Instantiation.addAnonymousDisjunctSemanticIdentitySet(coordDomain), Instantiation.addAnonymousDisjunctSemanticIdentitySet(coordDomain),
					firstSet,
					S23MSemanticDomains.minCardinality_NOTAPPLICABLE, S23MSemanticDomains.maxCardinality_NOTAPPLICABLE, S23MSemanticDomains.isNavigable_NOTAPPLICABLE, S23MSemanticDomains.isContainer_TRUE,
					Instantiation.addAnonymousDisjunctSemanticIdentitySet(coordDomain),
					secondSet,
					S23MSemanticDomains.minCardinality_NOTAPPLICABLE, S23MSemanticDomains.maxCardinality_NOTAPPLICABLE, S23MSemanticDomains.isNavigable_NOTAPPLICABLE, S23MSemanticDomains.isContainer_FALSE
			);
		if (link.identity().name().equals(ANON_NAME)) {
			return link;
		} else {
			throw new IllegalStateException("Link instance has not been set up. "+metaLink+""+firstSet.identity()+"to"+secondSet);
		}
	}

	private Set createDiagram(final Set artifactVisualization, final Set diagramType) {
		final Set diag = artifactVisualization.addConcrete(getVisualizationVertexByName(DIAGRAM),
				Instantiation.addDisjunctSemanticIdentitySet("", "", coordDomain));
		diag.identity().setPayload(diagramType.identity().uniqueRepresentationReference().toString());
		return diag;
	}

	private Set createNonContainmentReference(final Set metaInstance, final Set viz, final Set instanceToVisualize) {
		final Set link = Instantiation.arrow(metaInstance,
				Instantiation.addAnonymousDisjunctSemanticIdentitySet(coordDomain), Instantiation.addAnonymousDisjunctSemanticIdentitySet(coordDomain),
				viz,
				S23MSemanticDomains.minCardinality_NOTAPPLICABLE, S23MSemanticDomains.maxCardinality_NOTAPPLICABLE, S23MSemanticDomains.isNavigable_NOTAPPLICABLE, S23MSemanticDomains.isContainer_FALSE,
				Instantiation.addAnonymousDisjunctSemanticIdentitySet(coordDomain),
				instanceToVisualize,
				S23MSemanticDomains.minCardinality_NOTAPPLICABLE, S23MSemanticDomains.maxCardinality_NOTAPPLICABLE, S23MSemanticDomains.isNavigable_NOTAPPLICABLE, S23MSemanticDomains.isContainer_FALSE
				);
		if (link.identity().name().equals(ANON_NAME)) {
			return link;
		} else {
			throw new IllegalStateException("Link instance has not been set up. "+metaInstance+""+viz.identity()+"to"+instanceToVisualize);
		}
	}

	private Set findDiagramInstance(final Set artifactInstance, final Set visualizationAspect) {
		Set diagram = null;
		for (final Set s : artifactInstance.filterInstances()) {
			if (s.category().identity().isEqualToRepresentation(getVisualizationVertexByName(DIAGRAM).identity()) &&
					s.identity().payload().equals(visualizationAspect.identity().uniqueRepresentationReference().toString())) {
				diagram = s;
				break;
			}
		}
		if (diagram != null) {
			return diagram;
		} else {
			throw new IllegalStateException("Diagram instance has not been set up.");
		}
	}

	private Set findRepresentationInstance(final Set instance, final Set visualizedArtifact, final Set visualizationAspect) {
		Set representationSet = null;
		for (final Set l : visualizedArtifact.filterInstances()) {
			final String setPayload = (l.identity().payload() == null) ? "" : l.identity().payload();
			if (l.category().identity().isEqualToRepresentation(getVisualizationVertexByName(REPRESENTATION).identity()) &&
					setPayload.equals(
							getRepresentationInstanceKey(instance,
									visualizationAspect))) {
				representationSet = l;
				break;
			}
		}
		return representationSet;
	}

	private Set getCoordinateInstanceOf(final Set instance, final Set visualizedArtifact, final Set coordinateSet, final Set visualizationAspect) {
		final Set representationSet = findRepresentationInstance(instance, visualizedArtifact, visualizationAspect);
		Set coordInstance = null;
		if (representationSet != null) {
			for (final Set l : visualizedArtifact.filterArrows()) {
				if (l.properClass().isEqualTo(S23MKernel.coreGraphs.edge)) {
					final Set e = l;
					if (e.from().identity().isEqualToRepresentation(representationSet.identity())) {
						final Set toMetaInstance = e.to().category();
						if (toMetaInstance.identity().isEqualToRepresentation(coordinateSet.identity())) {
							coordInstance = e.to();
							break;
						}
					}
				}
			}
		} else {
			throw new IllegalStateException("Representation instance has not been set up.");
		}

		if (coordInstance != null) {
			return coordInstance;
		} else {
			throw new IllegalStateException("Coordinate instance has not been set up.");
		}
	}

	public Point getCoordinatesOf(final Set instance, final Set visualizedArtifact, final Set visualizationAspect) {
		final Set xSet = getCoordinateInstanceOf(instance, visualizedArtifact, getVisualizationVertexByName(X), visualizationAspect);
		final Set ySet = getCoordinateInstanceOf(instance, visualizedArtifact, getVisualizationVertexByName(Y), visualizationAspect);
		return new Point(Integer.parseInt(xSet.identity().payload()),
				Integer.parseInt(ySet.identity().payload()));
	}

	private String getRepresentationInstanceKey(final Set instance,	final Set visualizationAspect) {
		return visualizationAspect.identity().uniqueRepresentationReference().toString()+
		instance.identity().uniqueRepresentationReference().toString();
	}

	public Set getVisualizedArtifactInstance(final Set artifactInstance) {
		Set vizualizedArtifactInstance = null;
		for (final Set s : RepositoryStructure.graphVisualizations.filterInstances()) {
			final String setPayload = (s.identity().payload() == null) ? "" : s.identity().payload();
			if (s.category().identity().isEqualToRepresentation(getArtifactVisualizationInstance().identity()) &&
					setPayload.equals(artifactInstance.identity().uniqueRepresentationReference().toString())) {
				vizualizedArtifactInstance = s;
				break;
			}
		}

		if (vizualizedArtifactInstance == null) {
			//try to fetch this from the repository
//			final InstanceHandler instanceHandler = InstanceHandler.getInstance();
//			try {
//				instanceHandler.deserializeInstance(artifactInstance.identity().getUniqueRepresentationReference().toString());
//			} catch (final IllegalAccessException e) {
//			}
			throw new IllegalStateException("Visualized Artifact instance has not been set up.");

		}
		return vizualizedArtifactInstance;
	}

	public boolean hasVisualizedArtifactInstance(final Set artifactInstance) {
		Set vizualizedArtifactInstance = null;
		for (final Set s : RepositoryStructure.graphVisualizations.filterInstances()) {
			final String setPayload = (s.identity().payload() == null) ? "" : s.identity().payload();
			if (s.category().identity().isEqualToRepresentation(getArtifactVisualizationInstance().identity()) &&
					setPayload.equals(artifactInstance.identity().uniqueRepresentationReference().toString())) {
				vizualizedArtifactInstance = s;
				break;
			}
		}

		if (vizualizedArtifactInstance == null) {
			return false;
		} else {
			return true;
		}
	}

	public Set getArtifactVisualizationInstance() {
		for (final Set s : Root.cellengineering.filterInstances()) {
			if (s.identity().name().equals(ARTIFACT_VISUALIZATION_INSTANCE)) {
				return s;
			}
		}
		throw new IllegalStateException("No artifacti visualizedGraph instance is found.");
	}

	public void updateCoordinatesOf(final Set instance, final Point location, final Set visualizedArtifact, final Set visualizationAspect) {
		final Set xSet = getCoordinateInstanceOf(instance, visualizedArtifact, getVisualizationVertexByName(X), visualizationAspect);
		final Set ySet = getCoordinateInstanceOf(instance, visualizedArtifact, getVisualizationVertexByName(Y), visualizationAspect);
		xSet.assignNewPayload(Integer.toString(location.x));
		ySet.assignNewPayload(Integer.toString(location.y));
	}

	public boolean hasCoordinatesOf(final Set instance, final Set containerSet, final Set visualizationAspect) {
		if (hasVisualizedArtifactInstance(containerSet)) {
			try {
				final Set visualizedArtifact = getVisualizedArtifactInstance(containerSet);
				final Set xSet = getCoordinateInstanceOf(instance, visualizedArtifact, getVisualizationVertexByName(X), visualizationAspect);
				final Set ySet = getCoordinateInstanceOf(instance, visualizedArtifact,getVisualizationVertexByName(Y), visualizationAspect);
					if (xSet != null && ySet != null) {
						return true;
					} else {
						return false;
					}
			} catch(final IllegalStateException ex) {
				return false;
			}
		} else {
			return false;
		}
	}

}
