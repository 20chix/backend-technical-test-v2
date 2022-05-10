# Backend technical Test v2 [![Build](https://github.com/20chix/backend-technical-test-v2/actions/workflows/maven.yml/badge.svg)](https://github.com/20chix/backend-technical-test-v2/actions/workflows/maven.yml)

This is a Java Spring application where users can order Pilotes (Majorcan recipe that consist of a meatball stew) from the great Miquel Montoro 

## Setup
To run this project using helper bash script:

```
.\start-service.sh
```
This script will build, test, package and run the application with docker compose using at port `8081`. 

Run helper bash scrip to clean up docker container.

```
.\stop-service.sh
```
## Usage

### Swagger 

In order to consume the REST endpoints navigate to:

```
http://localhost:8081/swagger-ui.html
```
### Postman

As an alternative to Swagger, `backend-technical-test-v2.postman_collection.json` postman collection is available to import and test all exposed endpoints.