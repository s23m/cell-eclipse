package org.s23m.cell.eclipse.xtend;

import java.util.Arrays;
import java.util.List;

import org.s23m.cell.Set;
import org.s23m.cell.generator.ModelToTextTransformation;
import org.s23m.cell.kernel.artifactinstantiation.InstantiationSequences;
import org.s23m.cell.kernel.artifactinstantiation.RunInstantiationSequence;

public class TestHtmlGeneration {

	public static void main(String[] args) {
		// boot
		RunInstantiationSequence.run();
		// 
		Set set = InstantiationSequences.getInstance().crm;
		
		ModelToTextTransformation template = new ModelToTextTransformation() {
			public CharSequence invoke(List<Set> parameters) {
				if (parameters.size() != 1) {
					throw new IllegalArgumentException("Illegal number of parameters - expected 1");
				}
				final Set set = parameters.get(0);
				HtmlTemplate html = new HtmlTemplate();
				return html.main(set);
			}
		};
		
		System.out.println("Executing template:\n" + template.invoke(Arrays.asList(set)));
	}
}
