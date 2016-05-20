package org.s23m.cell.xtend2;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

import org.s23m.cell.Set;
import org.s23m.cell.kernel.tests.S23MTestCase;
import org.s23m.cell.platform.api.models.CellEngineering;
import org.s23m.cell.platform.api.models.CellPlatform;

/**
 * Sanity test for HTML rendering used by editor
 */
public class HtmlTemplateTransformationTest extends S23MTestCase {

	@Override
	protected void executeInstantiationSequence() {
		// instantiate feature so that formula is properly defined
		CellPlatform.instantiateFeature();
		
		Set formula = CellEngineering.formula;
		HtmlTemplateTransformation htt = new HtmlTemplateTransformation();
		CharSequence result = htt.apply(Arrays.asList(formula));
		String htmlRepresentation = result.toString();
		assertNotNull(htmlRepresentation);
		
		// read in expected HTML content
		InputStream stream = getClass().getClassLoader().getResourceAsStream("org/s23m/cell/xtend2/formula-expected-html.txt");
		assertNotNull(stream);
		String streamToString = convertStreamToString(stream);
		assertNotNull(streamToString);
		
		assertFalse(streamToString.isEmpty());
		assertFalse(htmlRepresentation.isEmpty());
		assertEquals(streamToString.trim(), htmlRepresentation.trim());
	}
	
	static String convertStreamToString(java.io.InputStream is) {
	    Scanner scanner = new Scanner(is);
		Scanner s = scanner.useDelimiter("\\A");
	    try {
		    return s.hasNext() ? s.next() : "";
	    } finally {
	    	scanner.close();
	    }
	}
	
	private String readFile(String pathname) throws IOException {
	    File file = new File(pathname);
	    StringBuilder fileContents = new StringBuilder((int)file.length());
	    Scanner scanner = new Scanner(file);
	    String lineSeparator = System.getProperty("line.separator");

	    try {
	        while(scanner.hasNextLine()) {
	            fileContents.append(scanner.nextLine() + lineSeparator);
	        }
	        return fileContents.toString();
	    } finally {
	        scanner.close();
	    }
	}
}
