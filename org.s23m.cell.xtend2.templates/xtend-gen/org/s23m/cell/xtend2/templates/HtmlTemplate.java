/**
 * BEGIN LICENSE BLOCK
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
 * Andrew Shewring
 * ***** END LICENSE BLOCK
 */
package org.s23m.cell.xtend2.templates;

import java.util.UUID;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.s23m.cell.Identity;
import org.s23m.cell.Set;
import org.s23m.cell.api.Query;
import org.s23m.cell.api.models.S23MSemanticDomains;

@SuppressWarnings("all")
public class HtmlTemplate {
  public CharSequence main(final Set set) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<html>");
    _builder.newLine();
    _builder.append("\t");
    CharSequence _head = this.head();
    _builder.append(_head, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    CharSequence _body = this.body(set);
    _builder.append(_body, "\t");
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
    _builder.append(_displaySet, "\t\t");
    _builder.append("</h2>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    final Function1<Set, CharSequence> _function = new Function1<Set, CharSequence>() {
      public CharSequence apply(final Set it) {
        return HtmlTemplate.this.vertexFlavorTable(it);
      }
    };
    CharSequence _flavorContainer = this.flavorContainer(set, Query.vertex, _function);
    _builder.append(_flavorContainer, "\t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    final Function1<Set, CharSequence> _function_1 = new Function1<Set, CharSequence>() {
      public CharSequence apply(final Set it) {
        return HtmlTemplate.this.supersetReferenceFlavorTable(it);
      }
    };
    CharSequence _flavorContainer_1 = this.flavorContainer(set, Query.superSetReference, _function_1);
    _builder.append(_flavorContainer_1, "\t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    final Function1<Set, CharSequence> _function_2 = new Function1<Set, CharSequence>() {
      public CharSequence apply(final Set it) {
        return HtmlTemplate.this.edgeEndFlavorTable(it);
      }
    };
    CharSequence _flavorContainer_2 = this.flavorContainer(set, Query.edge, _function_2);
    _builder.append(_flavorContainer_2, "\t\t");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    final Function1<Set, CharSequence> _function_3 = new Function1<Set, CharSequence>() {
      public CharSequence apply(final Set it) {
        return HtmlTemplate.this.visibilityFlavorTable(it);
      }
    };
    CharSequence _flavorContainer_3 = this.flavorContainer(set, Query.visibility, _function_3);
    _builder.append(_flavorContainer_3, "\t\t");
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
  
  private CharSequence flavorContainer(final Set set, final Set flavor, final Function1<? super Set, ? extends CharSequence> tableBody) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<div class=\"flavorContainer\">");
    _builder.newLine();
    _builder.append("\t");
    final Set flavoredSet = set.filterProperClass(flavor);
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<h2>");
    String _identityName = this.identityName(flavor);
    _builder.append(_identityName, "\t");
    CharSequence _flavored = this.flavored();
    _builder.append(_flavored, "\t");
    _builder.append("</h2>");
    _builder.newLineIfNotEmpty();
    {
      boolean _isEmpty = flavoredSet.isEmpty();
      if (_isEmpty) {
        _builder.append("\t");
        CharSequence _emptySet = this.emptySet();
        _builder.append(_emptySet, "\t");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("\t");
        _builder.append("<table>");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("\t");
        CharSequence _apply = tableBody.apply(flavoredSet);
        _builder.append(_apply, "\t\t");
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
            _builder.append(_displaySet, "\t");
          } else {
            CharSequence _displayVertexFlavoredSet = this.displayVertexFlavoredSet(s);
            _builder.append(_displayVertexFlavoredSet, "\t");
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
        _builder.append(_displaySet, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        Set _to = s.to();
        CharSequence _displaySet_1 = this.displaySet(_to);
        _builder.append(_displaySet_1, "\t");
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
    _builder.append(_identityName, "\t");
    CharSequence _flavored = this.flavored();
    _builder.append(_flavored, "\t");
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
        final Set source = s.fromEdgeEnd();
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        final Set target = s.toEdgeEnd();
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        Set _filterFrom = s.filterFrom();
        CharSequence _displaySet = this.displaySet(_filterFrom);
        _builder.append(_displaySet, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        CharSequence _cardinalityOf = this.cardinalityOf(source);
        _builder.append(_cardinalityOf, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        CharSequence _displaySet_1 = this.displaySet(s);
        _builder.append(_displaySet_1, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        CharSequence _cardinalityOf_1 = this.cardinalityOf(target);
        _builder.append(_cardinalityOf_1, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        Set _filterTo = s.filterTo();
        CharSequence _displaySet_2 = this.displaySet(_filterTo);
        _builder.append(_displaySet_2, "\t");
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
    final String min = this.identityName(_value);
    _builder.newLineIfNotEmpty();
    Set _maxCardinality = this.maxCardinality();
    Set _value_1 = set.value(_maxCardinality);
    final String max = this.identityName(_value_1);
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
        _builder.append(_displaySet, "\t");
        _builder.append("</td>");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<td>");
        Set _to = s.to();
        CharSequence _displaySet_1 = this.displaySet(_to);
        _builder.append(_displaySet_1, "\t");
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
    final Function1<Set, CharSequence> _function = new Function1<Set, CharSequence>() {
      public CharSequence apply(final Set s) {
        Identity _identity = s.identity();
        return _identity.name();
      }
    };
    CharSequence _decorateSet = this.decorateSet(set, _function);
    _builder.append(_decorateSet, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence displayVertexFlavoredSet(final Set set) {
    StringConcatenation _builder = new StringConcatenation();
    final Function1<Set, CharSequence> _function = new Function1<Set, CharSequence>() {
      public CharSequence apply(final Set s) {
        Identity _identity = set.identity();
        UUID _identifier = _identity.identifier();
        String _plus = ("<a href=\"" + _identifier);
        String _plus_1 = (_plus + "\">");
        String _identityName = HtmlTemplate.this.identityName(set);
        String _plus_2 = (_plus_1 + _identityName);
        return (_plus_2 + "</a>");
      }
    };
    CharSequence _decorateSet = this.decorateSet(set, _function);
    _builder.append(_decorateSet, "");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  private CharSequence decorateSet(final Set set, final Function1<? super Set, ? extends CharSequence> setNameContents) {
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
    return _identity.name();
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
    return S23MSemanticDomains.minCardinality;
  }
  
  private Set maxCardinality() {
    return S23MSemanticDomains.maxCardinality;
  }
  
  private Set kernelEdge() {
    return Query.edge;
  }
}
