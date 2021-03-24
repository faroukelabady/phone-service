# Go Giker
An application for viewing customer phones

## Requirements:
[Docker](https://docs.docker.com/install/)
[Docker-compose](https://docs.docker.com/compose/install/)

## running the application:
from the project application directory run
`docker-compose up`
or
`docker-compose up -f {path/to/docker-compose.yml}`

you can find the application swagger page at

- http://localhost:8080/swagger-ui.html
- http://localhost:8080/actuator

## APIlinks
- http://localhost:8080/customer?page=0&size=10
- http://localhost:8080/customer/country/{countryCode}?page=0&size=10 eg: countrycode -> 237, page -> pageindex, size -> pageSize
- http://localhost:8080/customer/country/{countryCode}/state/{state}?page=0&size=10 eg: countrycode -> 237, state -> VALID, page -> pageindex, size -> pageSize
- http://127.0.0.1:8080/customer/state/{state}?page=0&size=10  eg: VALID, INVALID, ALL, page -> pageindex, size -> pageSize
- http://127.0.0.1:8080/country?page=0&size=10 eg: page -> pageindex, size -> pageSize

## Agnular Page
- http://localhost:9000
