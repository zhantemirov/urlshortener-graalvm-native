## Description

A very straightforward and simple Spring Boot REST application:
* shortens long URLs, persists them in a form of hashed IDs
* original URL is returned by requesting `http://localhost:8080/ID`
* pre-compiled as a native executable using GraalVM Native image
* written in Kotlin

## App launch time comparison (Win 11, 6core CPU, 16GB RAM)

* `./target/urlshortener.exe` -  Started UrlShortenerApplicationKt in `0.251` seconds (process running for `0.256`)
* `mvn spring-boot:run` - Started UrlShortenerApplicationKt in `2.323` seconds (process running for `2.626`)

## Room for enhancements

* As we are dealing more with Redis server integration with Spring Boot app inside a GraalVM Native image,
we primarily focus on data layer testing (Spring data repository) than API one (Spring REST Controller).
The API tests can be added later.
* Logging, custom exceptions

## URL shortener showcase

Please make sure Docker is running, this is required for:
* TestContainers (tests inside a native image) 
* or manual tests execution (starting a Redis server container).

Before proceeding further, build a GraalVM Native image:
```shell
mvn native:compile -Pnative -DskipTests
```
The compiled executable is available at `target/urlshortener`


### Tests inside a native image:

Run them by executing: 
```shell
mvn test -Pnative
```

### Manual tests:

Start Redis server:

```shell
docker-compose -f docker-compose.yml up -d
```

Run the pre-compiled executable:
```shell
./target/urlshortener
```
Send a POST request to obtain a short URL id:
```shell
curl -X POST -H "Content-Type: application/json" -d "https://google.com/" localhost:8080/shorten
```

Example output:
```
curl -X POST -H "Content-Type: application/json" -d "https://google.com/" localhost:8080/shorten
...
< HTTP/1.1 200
...
http://localhost/bf8e423d
...
```

Get original URL by sending a GET request with shortened URL id:
```shell
curl -v http://localhost:8080/bf8e423d
```

Example output:
```
curl -v http://localhost:8080/bf8e423d
...
< Location: https://google.com/
...
```

Stop and remove the Redis server container:

```shell
 docker-compose -f docker-compose.yml down
```