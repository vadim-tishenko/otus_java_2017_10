#!/usr/bin/env bash

MEMORY="-Xms2g -Xmx2g"

GC=-XX:+UseSerialGC
java $MEMORY $GC -jar target/hw04-1.0-SNAPSHOT.jar

GC=-XX:+UseParallelGC
java $MEMORY $GC -jar target/hw04-1.0-SNAPSHOT.jar

GC=-XX:+UseConcMarkSweepGC
java $MEMORY $GC -jar target/hw04-1.0-SNAPSHOT.jar

GC=-XX:+UseG1GC
java $MEMORY $GC -jar target/hw04-1.0-SNAPSHOT.jar
