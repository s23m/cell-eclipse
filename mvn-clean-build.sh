# Remove Tycho p2 caches
rm -rf ~/.m2/repository/.cache
rm -rf ~/.m2/repository/.meta

# Execute build
set MAVEN_OPTS="-Xmx512m -XX:MaxPermSize=256m"
sh $M2_HOME/bin/mvn clean install
