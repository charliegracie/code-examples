#!/bin/bash

for i in 1 2 3 4 8 16 32 64 128
do
   $JAVA_HOME/bin/java -Xms100m -Xmx100m $EXTRA_JVM_OPTIONS -cp . ObjectAvalanche $i 100000 1000000
done
