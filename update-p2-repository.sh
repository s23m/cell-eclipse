#!/bin/bash
set -e
set -o posix

PWD=`pwd`
# Quick hack for msysgit
DIR=${PWD/\/C\//C:/}
echo $DIR

# Assume that cell is in the same directory
CELL_DIR=$DIR/../cell
BUNDLE_ROOT="$DIR/tycho-build/temp"
ECLIPSE_EXEC="$ECLIPSE_HOME/eclipse"
PLUGINS_DIR=$BUNDLE_ROOT/plugins
GENERATED_REPO_DIR=$BUNDLE_ROOT/repo
REPO_DIR="$DIR/tycho-p2-repository"

if [ -n "${ECLIPSE_HOME+x}" ] && [ -d $ECLIPSE_HOME ]
then
  if [ -f $ECLIPSE_EXEC ]
  then
		# Generate the jars
		echo "Building dependent jars..."
		cd "$CELL_DIR"
		./sbt.sh clean package
		cd $DIR

		# Copy the jars to a temporary directory
		rm -rf $BUNDLE_ROOT
		mkdir $BUNDLE_ROOT
		mkdir "$PLUGINS_DIR"
		echo "Copying jars"
		cp $CELL_DIR/org.s23m.cell.*/target/*.jar $PLUGINS_DIR

		# Create a p2 repository
		echo "Generating p2 repository"
		$ECLIPSE_EXEC -debug -consolelog -nosplash -verbose -application org.eclipse.equinox.p2.publisher.FeaturesAndBundlesPublisher -metadataRepository file:$GENERATED_REPO_DIR -artifactRepository file:$GENERATED_REPO_DIR -source $BUNDLE_ROOT -compress -publishArtifacts

		echo "Removing existing p2 repository"
		rm -rf "$REPO_DIR/plugins"
		rm -f $REPO_DIR/*.jar
		
		echo "Replacing p2 repository"
		cp -r $GENERATED_REPO_DIR/* $REPO_DIR

		echo "Removing the temporary directory"
		rm -rf $BUNDLE_ROOT

		echo "Done"
	else
		echo "Eclipse executable not found ("$ECLIPSE_EXEC")"
  fi
else 
	echo "Eclipse home must be set as ECLIPSE_HOME environment variable"
fi
