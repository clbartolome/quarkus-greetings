#!/bin/bash

# Check if the database container is running
if [ "$( podman inspect -f '{{.State.Status}}' greeting-db )" != "running" ]; then

  # Run database container if required
  podman run -d --name greeting-db \
    -e POSTGRES_USER=develop \
    -e POSTGRES_PASSWORD=develop \
    -e POSTGRES_DB=greeting-db \
    -p 5432:5432 \
    postgres:10.5

  # Wait until container is running
  sleep 5
  until podman inspect -f '{{.State.Status}}' greeting-db | grep -m 1 "running"; do sleep 2 ; done

  podman cp ./src/main/resources/META-INF/sql/import-test.sql greeting-db:/docker-entrypoint-initdb.d/dump.sql
  podman exec greeting-db /bin/bash -c 'psql greeting-db -U develop -W < /docker-entrypoint-initdb.d/dump.sql'

fi

# Start quarkus app in dev mode
./mvnw compile quarkus:dev