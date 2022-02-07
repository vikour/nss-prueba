#!/bin/sh

SERVICE_NAME=nss-prueba-postgres

docker stop $SERVICE_NAME
docker rm $SERVICE_NAME
docker run \
	--name $SERVICE_NAME \
	-p 5432:5432 \
	-e POSTGRES_PASSWORD=1234 \
	-v $(pwd)/src/main/sql/scheme-postgres.sql:/docker-entrypoint-initdb.d/scheme-postgres.sql \
	-d postgres
