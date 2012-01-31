package org.s23m.cell.eclipse.xtend

import org.s23m.cell.Set
import org.s23m.cell.api.Query
import org.s23m.cell.api.models.GmodelSemanticDomains

class HtmlTemplate {
	
	def main(Set set) '''
		<html>
			�head()�
			�body(set)�
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
				<h2>�displaySet(set)�</h2>
				
				�/* Display <category name> : <name> in a simple one column table */�
				
				�set.flavorContainer(Query::vertex, [s | vertexFlavorTable(s)])�
				
				�set.flavorContainer(Query::superSetReference, [s | supersetReferenceFlavorTable(s)])�
				
				�set.flavorContainer(Query::edge, [s | edgeEndFlavorTable(s)])�
				
				�set.flavorContainer(Query::visibility, [s | visibilityFlavorTable(s)])�
			</div>
			<!-- Artifact container - END -->
		</body>
	'''
	
	def private flavorContainer(Set set, Set flavor, (Set)=>CharSequence tableBody) '''
		<div class="flavorContainer">
			�val flavoredSet = set.filterFlavor(flavor)�
			<h2>�flavor.identity.name��flavored�</h2>
			�IF flavoredSet.isEmpty�
				�emptySet()�
			�ELSE�
				<table>
					�tableBody.apply(flavoredSet)�
				</table>
			�ENDIF�
		</div>
	'''
	
	def private vertexFlavorTable(Set flavoredSet) '''
		�FOR s : flavoredSet�
			<tr>
				�/* Condition is consistent with other views */�
				<td>�IF s.filterInstances.isEmpty��displaySet(s)��ELSE��displayVertexFlavoredSet(s)��ENDIF�</td>
			</tr>
		�ENDFOR�
	'''
	
	def private supersetReferenceFlavorTable(Set flavoredSet) '''
		<tr>
			<th>sub set</th>
			<th>super set</th>
		</tr>
		�FOR s : flavoredSet�
		<tr>
			<td>�displaySet( s.from() ) /* s.fromSubSet() */�</td>
			<td>�displaySet( s.to() ) /* s.toSuperSet() */�</td>
		</tr>
		�ENDFOR�		
	'''
	
	def private edgeEndFlavorTable(Set flavoredSet) '''
		<tr>
			<th><span class="arrow">&larr;</span></th>
			<th>1<sup>st</sup>&nbsp;[min,&nbsp;max]</th>
			<th>�kernelEdge.identity.name��flavored�</th>
			<th>2<sup>nd</sup>&nbsp;[min,&nbsp;max]</th>
			<th><span class="arrow">&rarr;</span></th>
		</tr>
		�FOR s : flavoredSet�
		<tr>
			<td>�displaySet( s.filterFrom )�</td>
			�val source = s.fromEdgeEnd�
			<td>�displaySet( source )�&nbsp;<span class="cardinality">�source.value(minCardinality).identity.name�,�source.value(maxCardinality).identity.name�</span></td>
			<td>�displaySet( s )�</td>
			�val target = s.toEdgeEnd�
			<td>�displaySet( target )�&nbsp;<span class="cardinality">�target.value(minCardinality).identity.name�,�target.value(maxCardinality).identity.name�</span></td>
			<td>�displaySet( s.filterTo )�</td>
		</tr>
		�ENDFOR�
	'''
	
	def private visibilityFlavorTable(Set flavoredSet) '''
		<tr>
			<th>from</th>
			<th>to</th>
		</tr>
		�FOR s : flavoredSet�
		<tr>
			<td>�displaySet( s.from() )�</td>
			<td>�displaySet( s.to() )�</td>
		</tr>
		�ENDFOR�
	'''
		
	def private displaySet(Set set) '''
		<span class="metaArtifactName">�set.category.identity.name�</span> : <span class="setName">�set.identity.name�</span>
	'''
	
	def private displayVertexFlavoredSet(Set set) '''
		<span class="metaArtifactName">�set.category.identity.name�</span> : <span class="setName"><a href="�set.identity.identifier�">�set.identity.name�</a></span>
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
		
	def private kernelEdge() {
		Query::edge
	}
}