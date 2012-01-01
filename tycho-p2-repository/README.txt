This temporary p2 repository contains dependencies of the Maven Tycho build.

It was created by putting the dependent OSGi bundles into the <BUNDLE-ROOT>/plugins directory and then running the following command with Eclipse (version 3.7.0):

/path/to/eclipse -debug -consolelog -nosplash -verbose -application org.eclipse.equinox.p2.publisher.FeaturesAndBundlesPublisher -metadataRepository file:<BUNDLE-ROOT>/repo -artifactRepository file:<BUNDLE-ROOT>/repo -source <BUNDLE-ROOT> -compress -publishArtifacts

The repository is then located at <BUNDLE-ROOT>/repo
