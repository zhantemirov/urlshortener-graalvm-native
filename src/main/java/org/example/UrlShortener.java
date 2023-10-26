package org.example;

import com.google.common.hash.Hashing;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@EnableAutoConfiguration
@Controller
public class UrlShortener {

    private Map<String, String> urlStorage = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        SpringApplication.run(UrlShortener.class, args);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public void getShortUrl(@PathVariable String id, HttpServletResponse response) throws IOException {
        final String shortUrl = urlStorage.get(id);

        if (shortUrl != null) {
            response.sendRedirect(shortUrl);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @RequestMapping(value = "/shorten", method = RequestMethod.POST)
    public ResponseEntity<String> shortenUrl(@RequestBody String longUrl) {
        if (new UrlValidator(new String[]{"http", "https"}).isValid(longUrl)) {
            final String shortUrlId = String.valueOf(Hashing.murmur3_32().hashString(longUrl, StandardCharsets.UTF_8));
            urlStorage.put(shortUrlId, longUrl);
            return new ResponseEntity<>("http://my-url-shortener.com/" + shortUrlId, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}