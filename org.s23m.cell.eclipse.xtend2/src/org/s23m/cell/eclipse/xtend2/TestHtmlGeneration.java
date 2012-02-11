package org.s23m.cell.eclipse.xtend2;

import java.util.Arrays;

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
		
		ModelToTextTransformation htmlTransformation = new HtmlTemplateTransformation();
		
		System.out.println("Executing template:\n" + htmlTransformation.apply(Arrays.asList(set)));
	}
}
