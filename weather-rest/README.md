# weather-rest Project

This project uses Quarkus, the Supersonic Subatomic Java Framework.


## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Run in OpenShift

- JVM:

```sh
# Package Application
mvn clean package

# Create JVM build
cat src/main/docker/Dockerfile.jvm | oc new-build --name app-jvm --strategy=docker --dockerfile -

# Start build and follow up process
oc start-build app-jvm --from-dir .
oc logs bc/app-jvm -f

# Validate image
oc get is

# Optional: Start and configura app
oc new-app app-jvm
oc expose svc app-jvm
oc get route
```

- Native:

```sh
# Package Application using Navice+Docker
mvn clean package -Pnative -Dquarkus.native.container-build=true

# Create Native build
cat src/main/docker/Dockerfile.native | oc new-build --name app-nat --strategy=docker --dockerfile -

# Start build and follow up process
oc start-build app-nat --from-dir .
oc logs bc/app-nat -f

# Validate image
oc get is

# Optional: Start and configura app
oc new-app app-nat
oc expose svc app-nat
```


