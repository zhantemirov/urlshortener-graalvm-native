A very straightforward and simple Spring Boot REST application that is pre-compiled as a native executable using GraalVM Native image 

Run the executable:
```shell
./target/urlshortener
```

Send a POST request to obtain a short URL:
```shell
curl -vX POST -H "Content-Type: application/json" -d "https://google.com/" localhost:8080/shorten
```

Example output:
```
curl -vX POST -H "Content-Type: application/json" -d "https://google.com/" localhost:8080/shorten
...
< HTTP/1.1 200
...
http://my-url-shortener.com/bf8e423d
...
```

Get original URL by sending a GET request with shortened URL id:
```shell
curl -v localhost:8080/bf8e423d
```

Example output:
```
curl -v localhost:8080/bf8e423d
...
< Location: https://google.com/
...
```