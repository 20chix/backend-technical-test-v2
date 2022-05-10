#!/bin/bash

echo "Starting backend technical test v2"

mvn clean verify

docker-compose up