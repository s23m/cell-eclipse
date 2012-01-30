package org.s23m.cell.eclipse.xtend;

import org.s23m.cell.Set;
import org.s23m.cell.kernel.artifactinstantiation.InstantiationSequences;
import org.s23m.cell.kernel.artifactinstantiation.RunInstantiationSequence;

public class TestHtmlGeneration {

	public static void main(String[] args) {
		// boot
		RunInstantiationSequence.run();
		// 
		Set set = InstantiationSequences.getInstance().crm;
		HtmlTemplate template = new HtmlTemplate();
		System.out.println("Executing template:\n" + template.main(set));
	}
}
