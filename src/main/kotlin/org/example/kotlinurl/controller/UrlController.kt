package org.example.kotlinurl.controller

import com.google.common.hash.Hashing
import jakarta.servlet.http.HttpServletResponse
import org.apache.commons.validator.routines.UrlValidator
import org.example.kotlinurl.model.ShortenedUrl
import org.example.kotlinurl.service.UrlService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.nio.charset.StandardCharsets
import java.util.Optional

@RestController
class UrlController(val service: UrlService) {

    @PostMapping("/shorten")
    private fun shortenUrl(@RequestBody longUrl: String): ResponseEntity<String> {
        return if (UrlValidator(arrayOf("https", "http")).isValid(longUrl)) {
            val shortUrlId: String = Hashing.murmur3_32().hashString(longUrl, StandardCharsets.UTF_8).toString()
            val savedShortUrl: ShortenedUrl = service.save(ShortenedUrl(shortUrlId, longUrl))

            ResponseEntity("http://my-url-shortener.com/" + savedShortUrl.id, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/{id}")
    private fun getLongUrl(@PathVariable id: String, response: HttpServletResponse) {
        val shortenedUrl: Optional<ShortenedUrl> = service.getOriginalUrlById(id)

        if (shortenedUrl.isPresent) response.sendRedirect(shortenedUrl.get().originalUrl) else response.sendError(HttpServletResponse.SC_NOT_FOUND)
    }
}