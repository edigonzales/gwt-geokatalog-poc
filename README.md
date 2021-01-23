# gwt-geokatalog-poc

**ACHTUNG:** `spring-graalvm-native` killt livereload.

## Develop
First terminal:
```
mvn spring-boot:run -Penv-dev -pl *-server -am
```

Second terminal:
```
mvn gwt:codeserver -pl *-client -am
```

Or without downloading all the snapshots again:

```
mvn gwt:codeserver -pl *-client -am -nsu
```

## Build

### JVM

```
./mvnw clean package
docker build -f ${artifactId}-server/src/main/docker/Dockerfile.jvm -t sogis/${artifactId} .
```

### Native Image
```
docker build -f ${artifactId}-server/src/main/docker/Dockerfile.nativ-build -t sogis/${artifactId} .
```
