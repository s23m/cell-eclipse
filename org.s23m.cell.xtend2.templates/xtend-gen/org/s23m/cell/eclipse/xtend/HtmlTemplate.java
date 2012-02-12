package org.s23m.cell.eclipse.xtend;

import java.util.UUID;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.StringExtensions;
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
    _builder.append("\t\t");
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
    _builder.newLine();
    _builder.append("\t\t");
    final Function1<Set,CharSequence> _function = new Function1<Set,CharSequence>() {
        public CharSequence apply(final Set it) {
          CharSequence _vertexFlavorTable = HtmlTemplate.this.vertexFlavorTable(it);
          return _vertexFlavorTable;
        }
      };
    CharSequence _flavorContainer = this.flavorContainer(set, Query.vertex, _function);
    _builder.append(_flavorContainer, "		");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    final Function1<Set,CharSequence> _function_1 = new Function1<Set,CharSequence>() {
        public CharSequence apply(final Set it) {
          CharSequence _supersetReferenceFlavorTable = HtmlTemplate.this.supersetReferenceFlavorTable(it);
          return _supersetReferenceFlavorTable;
        }
      };
    CharSequence _flavorContainer_1 = this.flavorContainer(set, Query.superSetReference, _function_1);
    _builder.append(_flavorContainer_1, "		");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    final Function1<Set,CharSequence> _function_2 = new Function1<Set,CharSequence>() {
        public CharSequence apply(final Set it) {
          CharSequence _edgeEndFlavorTable = HtmlTemplate.this.edgeEndFlavorTable(it);
          return _edgeEndFlavorTable;
        }
      };
    CharSequence _flavorContainer_2 = this.flavorContainer(set, Query.edge, _function_2);
    _builder.append(_flavorContainer_2, "		");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    final Function1<Set,CharSequence> _function_3 = new Function1<Set,CharSequence>() {
        public CharSequence apply(final Set it) {
          CharSequence _visibilityFlavorTable = HtmlTemplate.this.visibilityFlavorTable(it);
          return _visibilityFlavorTable;
        }
      };
    CharSequence _flavorContainer_3 = this.flavorContainer(set, Query.visibility, _function_3);
    _builder.append(_flavorContainer_3, "		");
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
  
  private CharSequence flavorContainer(final Set set, final Set flavor, final Function1<? super Set,? extends CharSequence> tableBody) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<div class=\"flavorContainer\">");
    _builder.newLine();
    _builder.append("\t");
    Set _filterFlavor = set.filterFlavor(flavor);
    final Set flavoredSet = _filterFlavor;
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<h2>");
    String _identityName = this.identityName(flavor);
    _builder.append(_identityName, "	");
    CharSequence _flavored = this.flavored();
    _builder.append(_flavored, "	");
    _builder.append("</h2>");
    _builder.newLineIfNotEmpty();
    {
      boolean _isEmpty = flavoredSet.isEmpty();
      if (_isEmpty) {
        _builder.append("\t");
        CharSequence _emptySet = this.emptySet();
        _builder.append(_emptySet, "	");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("\t");
        _builder.append("<table>");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        CharSequence _apply = tableBody.apply(flavoredSet);
        _builder.append(_apply, "		");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("</table>");
        _builder.newLine();
      }
    }
    _builder.append("</div>");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence vertexFlavorTable(final Set flavoredSet) {
    StringConcatenation _builder = new StringConcatenation();
    {
      for(final Set s : flavoredSet) {
        _builder.append("<tr>");
        _builder.newLine();
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("<td>");
        {
          Set _filterInstances = s.filterInstances();
          boolean _isEmpty = _filterInstances.isEmpty();
          if (_isEmpty) {
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
    return _builder;
  }
  
  private CharSequence supersetReferenceFlavorTable(final Set flavoredSet) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<tr>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<th>sub set</th>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<th>super set</th>");
    _builder.newLine();
    _builder.append("</tr>");
    _builder.newLine();
    {
      for(final Set s : flavoredSet) {
        _builder.append("<tr>");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("<td>");
        Set _from = s.from();
        CharSequence _displaySet = this.displaySet(_from);
        _builder.append(_displaySet, "	");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        Set _to = s.to();
        CharSequence _displaySet_1 = this.displaySet(_to);
        _builder.append(_displaySet_1, "	");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("</tr>");
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  private CharSequence edgeEndFlavorTable(final Set flavoredSet) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<tr>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<th><span class=\"arrow\">&larr;</span></th>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<th>1<sup>st</sup>&nbsp;[min,&nbsp;max]</th>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<th>");
    Set _kernelEdge = this.kernelEdge();
    String _identityName = this.identityName(_kernelEdge);
    _builder.append(_identityName, "	");
    CharSequence _flavored = this.flavored();
    _builder.append(_flavored, "	");
    _builder.append("</th>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<th>2<sup>nd</sup>&nbsp;[min,&nbsp;max]</th>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<th><span class=\"arrow\">&rarr;</span></th>");
    _builder.newLine();
    _builder.append("</tr>");
    _builder.newLine();
    {
      for(final Set s : flavoredSet) {
        _builder.append("<tr>");
        _builder.newLine();
        _builder.append("\t");
        Set _fromEdgeEnd = s.fromEdgeEnd();
        final Set source = _fromEdgeEnd;
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        Set _edgeEnd = s.toEdgeEnd();
        final Set target = _edgeEnd;
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        Set _filterFrom = s.filterFrom();
        CharSequence _displaySet = this.displaySet(_filterFrom);
        _builder.append(_displaySet, "	");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        CharSequence _cardinalityOf = this.cardinalityOf(source);
        _builder.append(_cardinalityOf, "	");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        CharSequence _displaySet_1 = this.displaySet(s);
        _builder.append(_displaySet_1, "	");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        CharSequence _cardinalityOf_1 = this.cardinalityOf(target);
        _builder.append(_cardinalityOf_1, "	");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        Set _filterTo = s.filterTo();
        CharSequence _displaySet_2 = this.displaySet(_filterTo);
        _builder.append(_displaySet_2, "	");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("</tr>");
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  private CharSequence cardinalityOf(final Set set) {
    StringConcatenation _builder = new StringConcatenation();
    Set _minCardinality = this.minCardinality();
    Set _value = set.value(_minCardinality);
    String _identityName = this.identityName(_value);
    final String min = _identityName;
    _builder.newLineIfNotEmpty();
    Set _maxCardinality = this.maxCardinality();
    Set _value_1 = set.value(_maxCardinality);
    String _identityName_1 = this.identityName(_value_1);
    final String max = _identityName_1;
    _builder.newLineIfNotEmpty();
    _builder.append("<td>");
    CharSequence _displaySet = this.displaySet(set);
    _builder.append(_displaySet, "");
    _builder.append("&nbsp;<span class=\"cardinality\">");
    _builder.append(min, "");
    _builder.append(",");
    _builder.append(max, "");
    _builder.append("</span></td>");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence visibilityFlavorTable(final Set flavoredSet) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<tr>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<th>from</th>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<th>to</th>");
    _builder.newLine();
    _builder.append("</tr>");
    _builder.newLine();
    {
      for(final Set s : flavoredSet) {
        _builder.append("<tr>");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("<td>");
        Set _from = s.from();
        CharSequence _displaySet = this.displaySet(_from);
        _builder.append(_displaySet, "	");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        Set _to = s.to();
        CharSequence _displaySet_1 = this.displaySet(_to);
        _builder.append(_displaySet_1, "	");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("</tr>");
        _builder.newLine();
      }
    }
    return _builder;
  }
  
  private CharSequence displaySet(final Set set) {
    StringConcatenation _builder = new StringConcatenation();
    final Function1<Set,String> _function = new Function1<Set,String>() {
        public String apply(final Set s) {
          Identity _identity = s.identity();
          String _name = _identity.name();
          return _name;
        }
      };
    CharSequence _decorateSet = this.decorateSet(set, _function);
    _builder.append(_decorateSet, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence displayVertexFlavoredSet(final Set set) {
    StringConcatenation _builder = new StringConcatenation();
    final Function1<Set,String> _function = new Function1<Set,String>() {
        public String apply(final Set s) {
          Identity _identity = set.identity();
          UUID _identifier = _identity.identifier();
          String _operator_plus = StringExtensions.operator_plus("<a href=\"", _identifier);
          String _operator_plus_1 = StringExtensions.operator_plus(_operator_plus, "\">");
          String _identityName = HtmlTemplate.this.identityName(set);
          String _operator_plus_2 = StringExtensions.operator_plus(_operator_plus_1, _identityName);
          String _operator_plus_3 = StringExtensions.operator_plus(_operator_plus_2, "</a>");
          return _operator_plus_3;
        }
      };
    CharSequence _decorateSet = this.decorateSet(set, _function);
    _builder.append(_decorateSet, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence decorateSet(final Set set, final Function1<? super Set,? extends CharSequence> setNameContents) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<span class=\"metaArtifactName\">");
    Set _category = set.category();
    String _identityName = this.identityName(_category);
    _builder.append(_identityName, "");
    _builder.append("</span> : <span class=\"setName\">");
    CharSequence _apply = setNameContents.apply(set);
    _builder.append(_apply, "");
    _builder.append("</span>");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private String identityName(final Set set) {
    Identity _identity = set.identity();
    String _name = _identity.name();
    return _name;
  }
  
  private CharSequence flavored() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<sub class=\"flavored\">flavored</sub>");
    _builder.newLine();
    return _builder;
  }
  
  private CharSequence emptySet() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<span><i>None</i></span>");
    _builder.newLine();
    return _builder;
  }
  
  private Set minCardinality() {
    return GmodelSemanticDomains.minCardinality;
  }
  
  private Set maxCardinality() {
    return GmodelSemanticDomains.maxCardinality;
  }
  
  private Set kernelEdge() {
    return Query.edge;
  }
}
