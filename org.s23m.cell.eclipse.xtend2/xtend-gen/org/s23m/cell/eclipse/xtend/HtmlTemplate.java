package org.s23m.cell.eclipse.xtend;

import java.util.UUID;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.s23m.cell.Identity;
import org.s23m.cell.Set;
import org.s23m.cell.api.Query;
import org.s23m.cell.api.models.GmodelSemanticDomains;

@SuppressWarnings("all")
public class HtmlTemplate {
  public CharSequence main(final Set set) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<html>");
    _builder.newLine();
    _builder.append("\t");
    CharSequence _head = this.head();
    _builder.append(_head, "	");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _body = this.body(set);
    _builder.append(_body, "	");
    _builder.newLineIfNotEmpty();
    _builder.append("</html>");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence head() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<head>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<style type=\"text/css\">");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("span, td, th, h2 {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("font-family: sans-serif;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("th, h2 {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("color: gray;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("table, td, th {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("cell-spacing: 0px;");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("cell-padding: 0px; ");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("table {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("border-collapse: collapse;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("td, th {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("padding: 3px;");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("padding-left: 10px;");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("padding-right: 10px;");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("vertical-align: top;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("span.metaArtifactName {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("color: #808080;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("span.setName {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("sub.flavored {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("font-size: 9pt;");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("font-variant: small-caps;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("span.arrow {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("font-size: 150%;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("div.flavorContainer {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("margin: 0px 10px 10px;");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("padding: 0px 10px 10px;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("div.artifactContainer {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("padding: 0px 5px;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("div.artifactContainer h2 {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("margin: 10px;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("div.flavorContainer h2 {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("margin: 10px 10px 10px 0;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("table, td, th, div.flavorContainer, div.artifactContainer {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("border: 1px solid #c3c3c3;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("div.artifactContainer, div.flavorContainer {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("// ignore for IE");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("border-radius: 5px;");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("-moz-border-radius: 5px;");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("-webkit-border-radius: 5px;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("span.cardinality {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("color: red;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("</style>");
    _builder.newLine();
    _builder.append("</head>");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence body(final Set set) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<body>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<!-- Artifact container - START -->");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<div class=\"artifactContainer\">");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<h2>");
    CharSequence _displaySet = this.displaySet(set);
    _builder.append(_displaySet, "		");
    _builder.append("</h2>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    final Function1<Set,CharSequence> _function = new Function1<Set,CharSequence>() {
        public CharSequence apply(final Set s) {
          CharSequence _vertexFlavoredContainer = HtmlTemplate.this.vertexFlavoredContainer(s);
          return _vertexFlavoredContainer;
        }
      };
    CharSequence _container = this.container(set, _function);
    _builder.append(_container, "		");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    final Function1<Set,CharSequence> _function_1 = new Function1<Set,CharSequence>() {
        public CharSequence apply(final Set s) {
          CharSequence _supersetReferenceFlavoredContainer = HtmlTemplate.this.supersetReferenceFlavoredContainer(s);
          return _supersetReferenceFlavoredContainer;
        }
      };
    CharSequence _container_1 = this.container(set, _function_1);
    _builder.append(_container_1, "		");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    final Function1<Set,CharSequence> _function_2 = new Function1<Set,CharSequence>() {
        public CharSequence apply(final Set s) {
          CharSequence _edgeEndFlavored = HtmlTemplate.this.edgeEndFlavored(s);
          return _edgeEndFlavored;
        }
      };
    CharSequence _container_2 = this.container(set, _function_2);
    _builder.append(_container_2, "		");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    final Function1<Set,CharSequence> _function_3 = new Function1<Set,CharSequence>() {
        public CharSequence apply(final Set s) {
          CharSequence _visibilityFlavoredContainer = HtmlTemplate.this.visibilityFlavoredContainer(s);
          return _visibilityFlavoredContainer;
        }
      };
    CharSequence _container_3 = this.container(set, _function_3);
    _builder.append(_container_3, "		");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("</div>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<!-- Artifact container - END -->");
    _builder.newLine();
    _builder.append("</body>");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence container(final Set set, final Function1<? super Set,? extends CharSequence> containerBody) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<div class=\"flavorContainer\">");
    _builder.newLine();
    _builder.append("\t");
    CharSequence _apply = containerBody.apply(set);
    _builder.append(_apply, "	");
    _builder.newLineIfNotEmpty();
    _builder.append("</div>");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence vertexFlavoredContainer(final Set set) {
    StringConcatenation _builder = new StringConcatenation();
    Set _kernelVertex = this.getKernelVertex();
    Set _filterFlavor = set.filterFlavor(_kernelVertex);
    final Set vertexFlavored = _filterFlavor;
    _builder.newLineIfNotEmpty();
    _builder.append("<h2>");
    Set _kernelVertex_1 = this.getKernelVertex();
    Identity _identity = _kernelVertex_1.identity();
    String _name = _identity.name();
    _builder.append(_name, "");
    String _flavored = this.flavored();
    _builder.append(_flavored, "");
    _builder.append("</h2>");
    _builder.newLineIfNotEmpty();
    {
      boolean _isEmpty = vertexFlavored.isEmpty();
      if (_isEmpty) {
        String _emptySet = this.emptySet();
        _builder.append(_emptySet, "");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("<table>");
        _builder.newLine();
        {
          for(final Set s : vertexFlavored) {
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("<!-- Condition is consistent with other views  -->");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("<td>");
            {
              Set _filterInstances = s.filterInstances();
              boolean _isEmpty_1 = _filterInstances.isEmpty();
              if (_isEmpty_1) {
                CharSequence _displaySet = this.displaySet(s);
                _builder.append(_displaySet, "	");
              } else {
                CharSequence _displayVertexFlavoredSet = this.displayVertexFlavoredSet(s);
                _builder.append(_displayVertexFlavoredSet, "	");
              }
            }
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("</tr>");
            _builder.newLine();
          }
        }
        _builder.append("</table>");
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  private CharSequence supersetReferenceFlavoredContainer(final Set set) {
    StringConcatenation _builder = new StringConcatenation();
    Set _kernelSuperSetReference = this.getKernelSuperSetReference();
    Set _filterFlavor = set.filterFlavor(_kernelSuperSetReference);
    final Set superSetReferenceFlavored = _filterFlavor;
    _builder.newLineIfNotEmpty();
    _builder.append("<h2>");
    Set _kernelSuperSetReference_1 = this.getKernelSuperSetReference();
    Identity _identity = _kernelSuperSetReference_1.identity();
    String _pluralName = _identity.pluralName();
    _builder.append(_pluralName, "");
    _builder.append("</h2>");
    _builder.newLineIfNotEmpty();
    {
      boolean _isEmpty = superSetReferenceFlavored.isEmpty();
      if (_isEmpty) {
        String _emptySet = this.emptySet();
        _builder.append(_emptySet, "");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("<table>");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("<tr>");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("<th>sub set</th>");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("<th>super set</th>");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("</tr>");
        _builder.newLine();
        {
          for(final Set s : superSetReferenceFlavored) {
            _builder.append("\t");
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<td>");
            Set _from = s.from();
            CharSequence _displaySet = this.displaySet(_from);
            _builder.append(_displaySet, "		");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<td>");
            Set _to = s.to();
            CharSequence _displaySet_1 = this.displaySet(_to);
            _builder.append(_displaySet_1, "		");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("</tr>");
            _builder.newLine();
          }
        }
        _builder.append("</table>");
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  private CharSequence edgeEndFlavored(final Set set) {
    StringConcatenation _builder = new StringConcatenation();
    Set _kernelEdge = this.getKernelEdge();
    Set _filterFlavor = set.filterFlavor(_kernelEdge);
    final Set edgeFlavored = _filterFlavor;
    _builder.newLineIfNotEmpty();
    _builder.append("<h2>");
    Set _kernelEdge_1 = this.getKernelEdge();
    Identity _identity = _kernelEdge_1.identity();
    String _name = _identity.name();
    _builder.append(_name, "");
    String _flavored = this.flavored();
    _builder.append(_flavored, "");
    _builder.append("</h2>");
    _builder.newLineIfNotEmpty();
    {
      boolean _isEmpty = edgeFlavored.isEmpty();
      if (_isEmpty) {
        String _emptySet = this.emptySet();
        _builder.append(_emptySet, "");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("<table>");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("<tr>");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("<th><span class=\"arrow\">&larr;</span></th>");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("<th>1<sup>st</sup>&nbsp;[min,&nbsp;max]</th>");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("<th>");
        Set _kernelEdge_2 = this.getKernelEdge();
        Identity _identity_1 = _kernelEdge_2.identity();
        String _name_1 = _identity_1.name();
        _builder.append(_name_1, "		");
        String _flavored_1 = this.flavored();
        _builder.append(_flavored_1, "		");
        _builder.append("</th>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append("<th>2<sup>nd</sup>&nbsp;[min,&nbsp;max]</th>");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("<th><span class=\"arrow\">&rarr;</span></th>");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("</tr>");
        _builder.newLine();
        {
          for(final Set s : edgeFlavored) {
            _builder.append("\t");
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<td>");
            Set _filterFrom = s.filterFrom();
            CharSequence _displaySet = this.displaySet(_filterFrom);
            _builder.append(_displaySet, "		");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            Set _fromEdgeEnd = s.fromEdgeEnd();
            final Set source = _fromEdgeEnd;
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<td>");
            CharSequence _displaySet_1 = this.displaySet(source);
            _builder.append(_displaySet_1, "		");
            _builder.append("&nbsp;<span class=\"cardinality\">");
            Set _minCardinality = this.minCardinality();
            Set _value = source.value(_minCardinality);
            Identity _identity_2 = _value.identity();
            String _name_2 = _identity_2.name();
            _builder.append(_name_2, "		");
            _builder.append(",");
            Set _maxCardinality = this.maxCardinality();
            Set _value_1 = source.value(_maxCardinality);
            Identity _identity_3 = _value_1.identity();
            String _name_3 = _identity_3.name();
            _builder.append(_name_3, "		");
            _builder.append("</span></td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<td>");
            CharSequence _displaySet_2 = this.displaySet(s);
            _builder.append(_displaySet_2, "		");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            Set _edgeEnd = s.toEdgeEnd();
            final Set target = _edgeEnd;
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<td>");
            CharSequence _displaySet_3 = this.displaySet(target);
            _builder.append(_displaySet_3, "		");
            _builder.append("&nbsp;<span class=\"cardinality\">");
            Set _minCardinality_1 = this.minCardinality();
            Set _value_2 = target.value(_minCardinality_1);
            Identity _identity_4 = _value_2.identity();
            String _name_4 = _identity_4.name();
            _builder.append(_name_4, "		");
            _builder.append(",");
            Set _maxCardinality_1 = this.maxCardinality();
            Set _value_3 = target.value(_maxCardinality_1);
            Identity _identity_5 = _value_3.identity();
            String _name_5 = _identity_5.name();
            _builder.append(_name_5, "		");
            _builder.append("</span></td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<td>");
            Set _filterTo = s.filterTo();
            CharSequence _displaySet_4 = this.displaySet(_filterTo);
            _builder.append(_displaySet_4, "		");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("</tr>");
            _builder.newLine();
          }
        }
        _builder.append("</table>");
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  private CharSequence visibilityFlavoredContainer(final Set set) {
    StringConcatenation _builder = new StringConcatenation();
    Set _kernelVisibility = this.getKernelVisibility();
    Set _filterFlavor = set.filterFlavor(_kernelVisibility);
    final Set visibilityFlavored = _filterFlavor;
    _builder.newLineIfNotEmpty();
    _builder.append("<h2>");
    Set _kernelVisibility_1 = this.getKernelVisibility();
    Identity _identity = _kernelVisibility_1.identity();
    String _name = _identity.name();
    _builder.append(_name, "");
    String _flavored = this.flavored();
    _builder.append(_flavored, "");
    _builder.append("</h2>\t");
    _builder.newLineIfNotEmpty();
    {
      boolean _isEmpty = visibilityFlavored.isEmpty();
      if (_isEmpty) {
        String _emptySet = this.emptySet();
        _builder.append(_emptySet, "");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("<table>");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("<tr>");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("<th>from</th>");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("<th>to</th>");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("</tr>");
        _builder.newLine();
        {
          for(final Set s : visibilityFlavored) {
            _builder.append("\t");
            _builder.append("<tr>");
            _builder.newLine();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<td>");
            Set _from = s.from();
            CharSequence _displaySet = this.displaySet(_from);
            _builder.append(_displaySet, "		");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("<td>");
            Set _to = s.to();
            CharSequence _displaySet_1 = this.displaySet(_to);
            _builder.append(_displaySet_1, "		");
            _builder.append("</td>");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("</tr>");
            _builder.newLine();
          }
        }
        _builder.append("</table>");
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  private CharSequence displaySet(final Set set) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<span class=\"metaArtifactName\">");
    Set _category = set.category();
    Identity _identity = _category.identity();
    String _name = _identity.name();
    _builder.append(_name, "");
    _builder.append("</span> : <span class=\"setName\">");
    Identity _identity_1 = set.identity();
    String _name_1 = _identity_1.name();
    _builder.append(_name_1, "");
    _builder.append("</span>");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence displayVertexFlavoredSet(final Set set) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<span class=\"metaArtifactName\">");
    Set _category = set.category();
    Identity _identity = _category.identity();
    String _name = _identity.name();
    _builder.append(_name, "");
    _builder.append("</span> : <span class=\"setName\"><a href=\"");
    Identity _identity_1 = set.identity();
    UUID _identifier = _identity_1.identifier();
    _builder.append(_identifier, "");
    _builder.append("\">");
    Identity _identity_2 = set.identity();
    String _name_1 = _identity_2.name();
    _builder.append(_name_1, "");
    _builder.append("</a></span>");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private String flavored() {
    return "<sub class=\"flavored\">flavored</sub>";
  }
  
  private String emptySet() {
    return "<span><i>None</i></span>";
  }
  
  private Set minCardinality() {
    return GmodelSemanticDomains.minCardinality;
  }
  
  private Set maxCardinality() {
    return GmodelSemanticDomains.maxCardinality;
  }
  
  private Set getKernelVertex() {
    return Query.vertex;
  }
  
  private Set getKernelSuperSetReference() {
    return Query.superSetReference;
  }
  
  private Set getKernelVisibility() {
    return Query.visibility;
  }
  
  private Set getKernelEdge() {
    return Query.edge;
  }
}
