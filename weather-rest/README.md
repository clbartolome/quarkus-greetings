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

# Start and configura app
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

# Start and configura app
oc new-app app-nat
oc expose svc app-nat
```

## Quarkus Performance Test in OpenShift

> use the images created in Run in Openshift section

- JVM:

```sh
# Create quotas
cat <<EOF | oc apply -f -
apiVersion: v1
kind: ResourceQuota
metadata:
  name: project-quota
spec:
  hard:
    pods: "10"
    requests.cpu: "2"
    requests.memory: 1Gi
    limits.cpu: "6"
    limits.memory: 1Gi
EOF


# Deploy apps 
for i in `seq 1 5`; do \ 
oc new-app app-jvm --name jvm-$i && \
oc expose svc jvm-$i && \
oc patch deploy jvm-$i -p '{"spec": {"template": {"spec": {"containers": [{"name": "app-jvm", "resources": { "limits":{ "cpu": "200m", "memory": "256Mi"}, "requests": { "cpu": "100m", "memory": "128Mi"}}}]}}}}' ; done

# Review limits
oc describe limits

# View quotas current state
oc describe quota project-quota 

# There is just enough memory for 4 pods
oc get pods

# Delete apps
for i in `seq 1 5`; do oc delete all -l app=jvm-$i; done

# Delete quotas
oc delete resourcequota project-quota
```


- Native:
```sh
# Create quotas
cat <<EOF | oc apply -f -
apiVersion: v1
kind: ResourceQuota
metadata:
  name: project-quota
spec:
  hard:
    pods: "10"
    requests.cpu: "500m"
    requests.memory: 256Mi
    limits.cpu: "1"
    limits.memory: 512Mi
EOF

# Deploy apps 
for i in `seq 1 5`; do \ 
oc new-app app-nat --name nat-$i && \
oc expose svc nat-$i && \
oc patch deploy nat-$i -p '{"spec": {"template": {"spec": {"containers": [{"name": "app-nat", "resources": { "limits":{ "cpu": "200m", "memory": "128Mi"}, "requests": { "cpu": "50m", "memory": "50Mi"}}}]}}}}' ; done

# Review limits
oc describe limits

# View quotas current state
oc describe quota project-quota 

# There is just enough memory for 4 pods
oc get pods

# Delete apps
for i in `seq 1 5`; do oc delete all -l app=nat-$i; done

# Delete quotas
oc delete resourcequota project-quota
```




