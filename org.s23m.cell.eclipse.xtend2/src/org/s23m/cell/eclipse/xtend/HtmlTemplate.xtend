package org.s23m.cell.eclipse.xtend

import org.s23m.cell.Set
import org.s23m.cell.api.Query
import org.s23m.cell.api.models.GmodelSemanticDomains

class HtmlTemplate {
	
	def main(Set set) '''
		<html>
			«head()»
			«body(set)»
		</html>
	'''
	
	def private head() '''
		<head>
			<style type="text/css">
				span, td, th, h2 {
					font-family: sans-serif;
				}
	
				th, h2 {
					color: gray;
				}
				
				table, td, th {
					cell-spacing: 0px;
					cell-padding: 0px; 
				}
				
				table {
					border-collapse: collapse;
				}
			
				td, th {
					padding: 3px;
					padding-left: 10px;
					padding-right: 10px;
					vertical-align: top;
				}
					
				span.metaArtifactName {
					color: #808080;
				}
				
				span.setName {
				}
				
				sub.flavored {
					font-size: 9pt;
					font-variant: small-caps;
				}
				
				span.arrow {
					font-size: 150%;
				}
				
				div.flavorContainer {
					margin: 0px 10px 10px;
					padding: 0px 10px 10px;
				}
				
				div.artifactContainer {
					padding: 0px 5px;
				}
				
				div.artifactContainer h2 {
					margin: 10px;
				}
				
				div.flavorContainer h2 {
					margin: 10px 10px 10px 0;
				}
				
				table, td, th, div.flavorContainer, div.artifactContainer {
					border: 1px solid #c3c3c3;
				}
				
				div.artifactContainer, div.flavorContainer {
					// ignore for IE
					border-radius: 5px;
					-moz-border-radius: 5px;
					-webkit-border-radius: 5px;
				}
				
				span.cardinality {
					color: red;
				}
			</style>
		</head>
	'''
	
	def private body(Set set) '''
		<body>
			<!-- Artifact container - START -->
			<div class="artifactContainer">
				<h2>«displaySet(set)»</h2>
				
				«/* Display <category name> : <name> in a simple one column table */»
				«set.container(s | vertexFlavoredContainer(s))»
				
				«set.container(s | supersetReferenceFlavoredContainer(s))»
				
				«set.container(s | edgeEndFlavored(s))»
				
				«set.container(s | visibilityFlavoredContainer(s))»
			</div>
			<!-- Artifact container - END -->
		</body>
	'''
	
	def private container(Set set, (Set)=>CharSequence containerBody) '''
		<div class="flavorContainer">
			«containerBody.apply(set)»
		</div>
	'''
	
	def private vertexFlavoredContainer(Set set) '''
		«val vertexFlavored = set.filterFlavor(getKernelVertex())»
		<h2>«getKernelVertex().identity().name()»«flavored()»</h2>
		«IF vertexFlavored.isEmpty»
			«emptySet()»
		«ELSE»
			<table>
			«FOR s : vertexFlavored»
				<tr>
					<!-- Condition is consistent with other views  -->
					<td>«IF s.filterInstances().isEmpty()»«displaySet(s)»«ELSE»«displayVertexFlavoredSet(s)»«ENDIF»</td>
				</tr>
			«ENDFOR»
			</table>
		«ENDIF»		
	'''
	
	def private supersetReferenceFlavoredContainer(Set set) '''
		«val superSetReferenceFlavored = set.filterFlavor(getKernelSuperSetReference())»
		<h2>«getKernelSuperSetReference().identity().pluralName()»</h2>
		«IF superSetReferenceFlavored.isEmpty»
			«emptySet()»
		«ELSE»
			<table>
				<tr>
					<th>sub set</th>
					<th>super set</th>
				</tr>
				«FOR s : superSetReferenceFlavored»
				<tr>
					<td>«displaySet( s.from() ) /* s.fromSubSet() */»</td>
					<td>«displaySet( s.to() ) /* s.toSuperSet() */»</td>
				</tr>
				«ENDFOR»
			</table>
		«ENDIF»
	'''
	
	def private edgeEndFlavored(Set set) '''
		«val edgeFlavored = set.filterFlavor(getKernelEdge())»
		<h2>«getKernelEdge().identity().name()»«flavored()»</h2>
		«IF edgeFlavored.isEmpty»
			«emptySet()»
		«ELSE»
			<table>
				<tr>
					<th><span class="arrow">&larr;</span></th>
					<th>1<sup>st</sup>&nbsp;[min,&nbsp;max]</th>
					<th>«getKernelEdge().identity().name()»«flavored()»</th>
					<th>2<sup>nd</sup>&nbsp;[min,&nbsp;max]</th>
					<th><span class="arrow">&rarr;</span></th>
				</tr>
				«FOR s : edgeFlavored»
					<tr>
						<td>«displaySet( s.filterFrom() )»</td>
						«val source = s.fromEdgeEnd()»
						<td>«displaySet( source )»&nbsp;<span class="cardinality">«source.value(minCardinality).identity().name()»,«source.value(maxCardinality).identity().name()»</span></td>
						<td>«displaySet( s )»</td>
						«val target = s.toEdgeEnd()»
						<td>«displaySet( target )»&nbsp;<span class="cardinality">«target.value(minCardinality).identity().name()»,«target.value(maxCardinality).identity().name()»</span></td>
						<td>«displaySet( s.filterTo() )»</td>
					</tr>
				«ENDFOR»
			</table>
		«ENDIF»
	'''
	
	def private visibilityFlavoredContainer(Set set) '''
		«val visibilityFlavored = set.filterFlavor(getKernelVisibility())»
		<h2>«getKernelVisibility().identity().name()»«flavored()»</h2>	
		«IF visibilityFlavored.isEmpty»
			«emptySet()»
		«ELSE»
			<table>
				<tr>
					<th>from</th>
					<th>to</th>
				</tr>
				«FOR s : visibilityFlavored»
				<tr>
					<td>«displaySet( s.from() )»</td>
					<td>«displaySet( s.to() )»</td>
				</tr>
				«ENDFOR»
			</table>
		«ENDIF»
	'''
		
	def private displaySet(Set set) '''
		<span class="metaArtifactName">«set.category().identity().name()»</span> : <span class="setName">«set.identity().name()»</span>
	'''
	
	def private displayVertexFlavoredSet(Set set) '''
		<span class="metaArtifactName">«set.category().identity().name()»</span> : <span class="setName"><a href="«set.identity().identifier()»">«set.identity().name()»</a></span>
	'''
	
	def private flavored() {
		"<sub class=\"flavored\">flavored</sub>"
	}
	
	def private emptySet() {
		"<span><i>None</i></span>"
	}
	
	def private minCardinality() {
		GmodelSemanticDomains::minCardinality
	}
	
	def private maxCardinality() {
		GmodelSemanticDomains::maxCardinality
	}
	
	def private getKernelVertex() {
		Query::vertex
	}
	
	def private getKernelSuperSetReference() {
		Query::superSetReference
	}

	def private getKernelVisibility() {
		Query::visibility
	}
	
	def private getKernelEdge() {
		Query::edge
	}
}