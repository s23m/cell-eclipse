#!/bin/bash

if [ -n "${M2_HOME+x}" ] && [ -d $M2_HOME ]
then
	# Remove Tycho p2 caches
	rm -rf ~/.m2/repository/.cache
	rm -rf ~/.m2/repository/.meta

	# Remove cached S23M binaries
	rm -rf ~/.m2/repository/org/s23m

	# Execute build
	set MAVEN_OPTS="-Xmx512m -XX:MaxPermSize=256m"
	bash $M2_HOME/bin/mvn clean install
else 
	echo "Maven home must be set as M2_HOME environment variable"
fi
