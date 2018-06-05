#!/bin/bash

$JAVA_HOME/bin/java -Xmx128m -Xms128m $EXTRA_JVM_OPTIONS -cp . PrimitiveMathTest 10000 10 100000
