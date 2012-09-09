/* ***** BEGIN LICENSE BLOCK *****
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
 * Chul Kim
 * ***** END LICENSE BLOCK ***** */
package org.s23m.cell.editor.semanticdomain.ui.components.upload;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.s23m.cell.Set;
import org.s23m.cell.api.Instantiation;
import org.s23m.cell.api.models.S23MSemanticDomains;
import org.s23m.cell.api.models2.RepositoryStructure;
import org.s23m.cell.api.models2.Visualization;
import org.s23m.cell.platform.api.models.CellPlatformDomain;

import com.vaadin.terminal.StreamResource;
import com.vaadin.terminal.StreamResource.StreamSource;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Upload;

public class IconUploadComponent extends CustomComponent
                        implements Upload.SucceededListener,
                                   Upload.FailedListener,
                                   Upload.Receiver {

	private static final long serialVersionUID = 1L;

	private final Set set;

	/* Root element for contained components */
    private final Panel root;

    /* Panel that contains the uploaded image */
    private final Panel imagePanel;

    /* Holds the icon image contents */
    private ByteArrayOutputStream outputStream;

    /**
     * Constructor
     *
     * @param set the {@link Set} in focus
     */
    public IconUploadComponent(final Set set) {
    	this.set = set;

        root = new Panel("My Upload Component");
        setCompositionRoot(root);

        // Create the Upload component.
        final Upload upload = new Upload();
        upload.setReceiver(this);

        // Use a custom button caption instead of plain "Upload".
        //upload.setButtonCaption("Upload Now");

        // Listen for events regarding the success of upload.
        upload.addListener((Upload.SucceededListener) this);
        upload.addListener((Upload.FailedListener) this);

        root.addComponent(upload);
        root.addComponent(new Label("Click 'Browse' to "+
                "select a file and then click 'Upload'."));

        // Create a panel for displaying the uploaded image.
        imagePanel = new Panel("Uploaded image");
        imagePanel.addComponent(
                         new Label("No image uploaded yet"));
        root.addComponent(imagePanel);
    }

    // Callback method to begin receiving the upload.
    public OutputStream receiveUpload(final String filename, final String MIMEType) {
    	// TODO validate allowable mime types

        outputStream = new ByteArrayOutputStream();

    	// the returned outputstream will be written to
    	return outputStream;
    }

    // This is called if the upload is finished.
    public void uploadSucceeded(final Upload.SucceededEvent event) {
        // Log the upload on screen.
        root.addComponent(new Label("File " + event.getFilename()
                + " of type '" + event.getMIMEType()
                + "' uploaded."));


        // Display the uploaded file in the image panel
        final StreamSource streamSource = new IconStreamSource(outputStream);
        final StreamResource imageResource = new StreamResource(streamSource, event.getFilename(), getApplication());

        imagePanel.removeAllComponents();
        imagePanel.addComponent(new Embedded("", imageResource));

        final Set graphVisualization = findGraphVisualization();
        //final Set representationOfSemanticIdentity = findRepresentationOfSemanticIdentity(visualizedGraph);
        final String iconContent = new String(outputStream.toByteArray());
		findIcon(graphVisualization).identity().setPayload(iconContent);

        /*
        final FileResource imageResource =
                new FileResource(file, getApplication());
        imagePanel.removeAllComponents();
        imagePanel.addComponent(new Embedded("", imageResource));
        */
    }

    private Set findGraphVisualization() {
    	final Set container = set.container();
		for (final Set gv: RepositoryStructure.graphVisualizations.filterInstances()) {
			for (final Set vis_to_visGraph: gv.filter(Visualization.visualizedGraph_to_graph)) {
				if (vis_to_visGraph.to().isEqualTo(container)) {
					return vis_to_visGraph.from().container();
				}
			}
		}
		// create the visualisation artefact, as it does not exist yet
		return createGraphVisualization(container);
	}

    // Copied from TestSequence
    private Set createGraphVisualization(final Set graph) {
		final Set gv = RepositoryStructure.graphVisualizations.addConcrete(Visualization.graphVisualization, graph);
		final Set v = gv.addConcrete(Visualization.visualizedGraph, graph);

		final Set vg_to_graph = Instantiation.arrow(Visualization.visualizedGraph_to_graph,
				S23MSemanticDomains.anonymous,
				Visualization.visualizedGraph,
				v,
			    S23MSemanticDomains.minCardinality_NOTAPPLICABLE,
			    S23MSemanticDomains.maxCardinality_NOTAPPLICABLE,
			    S23MSemanticDomains.isNavigable_NOTAPPLICABLE,
			    S23MSemanticDomains.isContainer_FALSE,
				graph,
				graph,
				S23MSemanticDomains.minCardinality_NOTAPPLICABLE,
				S23MSemanticDomains.maxCardinality_NOTAPPLICABLE,
				S23MSemanticDomains.isNavigable_NOTAPPLICABLE,
				S23MSemanticDomains.isContainer_FALSE);

		final Set details = gv.addConcrete(Visualization.details,Visualization.details);
		final Set structure = gv.addConcrete(Visualization.structure,Visualization.structure);
		final Set reuse = gv.addConcrete(Visualization.reuse,Visualization.reuse);
		final Set visibilities = gv.addConcrete(Visualization.visibilities,Visualization.visibilities);
		return gv;
	}

	//private Set findRepresentationOfSemanticIdentity(final Set visualizedGraph) {
	//	for (final Set r_to_ri: visualizedGraph.categorizedSet(Visualization.representation_to_representedInstance)) {
	//		if (r_to_ri.toConnectedInstance().isEqualTo(set)) {
	//			return r_to_ri.fromConnectedInstance();
	//		}
	//	}
	//	return F_InstantiationImpl.raiseError(visualizedGraph.identity(), S23MSemanticDomains.semanticErr);
	//}

    private Set findIcon(final Set graphVisualization) {
    	final Set container = set.container();

			for (final Set symbol_to_si: graphVisualization.filter(Visualization.symbol_to_semantic_identity)) {
				if (symbol_to_si.to().isEqualTo(set)
						&& symbol_to_si.fromEdgeEnd().isEqualTo(CellPlatformDomain.theDefault)) {
					return symbol_to_si.from();
			}
		}
		// create the icon artefact, as it does not exist yet
		final Set icon = graphVisualization.addConcrete(Visualization.symbol, CellPlatformDomain.icon);
		Instantiation.arrow(Visualization.symbol_to_semantic_identity,
				S23MSemanticDomains.anonymous,
				CellPlatformDomain.theDefault,
			    icon,
			    S23MSemanticDomains.minCardinality_NOTAPPLICABLE,
			    S23MSemanticDomains.maxCardinality_NOTAPPLICABLE,
			    S23MSemanticDomains.isNavigable_NOTAPPLICABLE,
			    S23MSemanticDomains.isContainer_FALSE,
			    set,
			    set,
			    S23MSemanticDomains.minCardinality_NOTAPPLICABLE,
			    S23MSemanticDomains.maxCardinality_NOTAPPLICABLE,
			    S23MSemanticDomains.isNavigable_NOTAPPLICABLE,
			    S23MSemanticDomains.isContainer_FALSE
			);
		return createGraphVisualization(container);
	}

	// This is called if the upload fails.
    public void uploadFailed(final Upload.FailedEvent event) {
        // Log the failure on screen.
        root.addComponent(new Label("Uploading "
                + event.getFilename() + " of type '"
                + event.getMIMEType() + "' failed."));
    }

    private class IconStreamSource implements StreamSource {

		private static final long serialVersionUID = 1L;

		private final InputStream is;

    	public IconStreamSource(final ByteArrayOutputStream os) {
    		// See http://stackoverflow.com/questions/1225909/most-efficient-way-to-create-inputstream-from-outputstream
    	    is = new ByteArrayInputStream(os.toByteArray());
    	}

    	public InputStream getStream() {
			return is;
		}
    }
}