# Backend technical Test v2 [![Build](https://github.com/20chix/backend-technical-test-v2/actions/workflows/maven.yml/badge.svg)](https://github.com/20chix/backend-technical-test-v2/actions/workflows/maven.yml)

This is a Java Spring application where users can order Pilotes (Majorcan recipe that consist of a meatball stew) from the great Miquel Montoro 

## Setup
To run this project using helper bash script:

```
.\start-service.sh
```
This script will build, test, package and run the application with docker compose at port `8081`. 

Run helper bash scrip to clean up docker container once finished.

```
.\stop-service.sh
```
## Usage

Basic auth is required for the search endpoint `/api/v1/pilotes/search` with username `admin` and password as `admin`

### Example Pilotes order
```
{
  "numberOfPilotes": "5",
  "street": "Buckingham Palace",
  "postcode": "SW1A 1AA",
  "city": "London",
  "country": "United Kingdom",
  "firstName": "Hadi",
  "lastName": "Elmekawi",
  "telephone": "+44123456789",
  "email": "fried.wings@gmail.com"
}
```
### Swagger 

In order to consume REST endpoints use any browser and navigate to:

```
http://localhost:8081/swagger-ui.html
```
### Postman

As an alternative to Swagger, `backend-technical-test-v2.postman_collection.json` postman collection is available to import and test all exposed endpoints.