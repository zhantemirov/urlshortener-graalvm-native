package org.example.urlshortener.service

import org.example.urlshortener.model.ShortenedUrl
import java.util.*

interface UrlService {
    fun getOriginalUrlById(id: String): Optional<ShortenedUrl>
    fun save(entity: ShortenedUrl): ShortenedUrl
}
