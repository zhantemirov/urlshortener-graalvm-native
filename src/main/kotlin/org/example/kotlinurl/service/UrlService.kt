package org.example.kotlinurl.service

import org.example.kotlinurl.model.ShortenedUrl
import org.example.kotlinurl.repository.UrlRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class UrlService(val repository: UrlRepository) {

    fun getOriginalUrlById(id: String): Optional<ShortenedUrl> = repository.findById(id)

    fun save(entity: ShortenedUrl): ShortenedUrl = repository.save(entity)
}