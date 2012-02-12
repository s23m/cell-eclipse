package org.s23m.cell.xtend2;

import java.util.List;

import org.s23m.cell.Set;
import org.s23m.cell.eclipse.xtend2.templates.HtmlTemplate;
import org.s23m.cell.generator.ModelToTextTransformation;

public class HtmlTemplateTransformation implements ModelToTextTransformation {
	
	public CharSequence apply(List<Set> parameters) {
		if (parameters.size() != 1) {
			throw new IllegalArgumentException("Illegal number of parameters - expected 1");
		}
		final Set set = parameters.get(0);
		final HtmlTemplate html = new HtmlTemplate();
		return html.main(set);
	}
}
