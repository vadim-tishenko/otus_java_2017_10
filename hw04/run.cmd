set MEMORY=-Xms2g -Xmx2g

set GC=-XX:+UseSerialGC
java %MEMORY% %GC% -jar target/hw04-1.0-SNAPSHOT.jar

set GC=-XX:+UseParallelGC
java %MEMORY% %GC% -jar target/hw04-1.0-SNAPSHOT.jar

set GC=-XX:+UseConcMarkSweepGC
java %MEMORY% %GC% -jar target/hw04-1.0-SNAPSHOT.jar

set GC=-XX:+UseG1GC
java %MEMORY% %GC% -jar target/hw04-1.0-SNAPSHOT.jar
