#!/usr/bin/env bash
java -javaagent:./agent/target/agent-1.0-SNAPSHOT.jar -jar ./stand/target/stand-1.0-SNAPSHOT.jar
