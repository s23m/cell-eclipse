This temporary p2 repository contains dependencies of the Maven Tycho build.

It was created using the update-p2-repository.sh script.

Tycho retains caches of p2 repositories. To clear these, remove the following:

~/.m2/repository/.cache
~/.m2/repository/.meta

and, just in case,

~/.m2/repository/org/s23m

