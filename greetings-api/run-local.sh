#!/bin/bash

# Check if the database container is running
if [ "$( podman inspect -f '{{.State.Status}}' greeting-db )" != "running" ]; then

  # Run database container if required
  podman run -d --name greeting-db \
    -e POSTGRES_USER=develop \
    -e POSTGRES_PASSWORD=develop \
    -e POSTGRES_DB=greeting \
    -p 5432:5432 \
    postgres:10.5

  # Wait until container is running
  until podman inspect -f '{{.State.Status}}' greeting-db | grep -m 1 "running"; do sleep 2 ; done

  podman cp ./src/main/resources/META-INF/sql/import-test.sql greeting-db:/docker-entrypoint-initdb.d/dump.sql
  podman exec -u develop greeting-db psql postgres postgres -f docker-entrypoint-initdb.d/dump.sql

fi

# Start quarkus app in dev mode
./mvnw compile quarkus:dev