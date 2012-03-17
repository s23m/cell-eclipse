#!/bin/bash

export WHICH_MVN=`which mvn`
if [ -n "${WHICH_MVN}" ]
then
	export MVN_EXECUTABLE="$WHICH_MVN"
	echo "mvn command is on the path - using '$MVN_EXECUTABLE' to run script"
elif [ -n "${M2_HOME+x}" ] && [ -d $M2_HOME ]
then
	export MVN_EXECUTABLE="$M2_HOME/bin/mvn"
	echo "M2_HOME is a valid directory - using '$MVN_EXECUTABLE' to run script"
else
	echo "Maven command (mvn) must be on the PATH or Maven home must be set as the M2_HOME environment variable"
fi

echo "Resolved mvn location: $MVN_EXECUTABLE"
if [ -n "${MVN_EXECUTABLE+x}" ]
then
	# Remove Tycho p2 caches
	rm -rf ~/.m2/repository/.cache
	rm -rf ~/.m2/repository/.meta

	# Remove cached S23M binaries
	rm -rf ~/.m2/repository/org/s23m

	# Execute build
	set MAVEN_OPTS="-Xmx512m -XX:MaxPermSize=256m"
	bash $MVN_EXECUTABLE clean install
fi
