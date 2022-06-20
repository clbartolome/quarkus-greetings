# greetings-api

Quarkus MicroService to show greetings.

Extensions installed:
- RestEasy: `mvn quarkus:add-extension -Dextensions="resteasy-jackson"`
- Health: `mvn quarkus:add-extension -Dextensions="quarkus-smallrye-health"`
- OpenApi: `mvn quarkus:add-extension -Dextensions="smallrye-openapi"`
- Metrics: `mvn quarkus:add-extension -Dextensions="quarkus-smallrye-metrics"`
- Postgresql (panache): `mvn quarkus:add-extension -Dextensions="quarkus-hibernate-orm-panache, quarkus-jdbc-postgresql, quarkus-jdbc-h2"`

! TIP: use `mvn quarkus:list-extensions` to get a list with all extensions.

## API Documentation & Other Tools

API live documentation and other helpfull resources can be found at:

- Index: `/index.html`
- Health: `/q/health/live` | `/q/health/ready` | `/q/health`
- OpenApi: `/openapi`
- Swagger: `/q/swagger-ui`
- Metrics: `/q/metrics/application` or `curl -H "Accept: application/json" http://localhost:8080/q/metrics/application`


## Run Locally

- Create PostgressDB:
```sh
docker run -d --name catalog \
  -e POSTGRES_USER=develop \
  -e POSTGRES_PASSWORD=develop \
  -e POSTGRES_DB=greeting-db \
  -p 5432:5432 \
  postgres:10.5
```

- Run Application in development mode:
```shell script
./mvnw compile quarkus:dev
```