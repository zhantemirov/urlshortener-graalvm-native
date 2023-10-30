package org.example.urlshortener.service.impl

import org.example.urlshortener.model.ShortenedUrl
import org.example.urlshortener.repository.UrlRepository
import org.example.urlshortener.service.UrlService
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class UrlServiceImpl(val repository: UrlRepository) : UrlService {

    override fun getOriginalUrlById(id: String): Optional<ShortenedUrl> = repository.findById(id)

    override fun save(entity: ShortenedUrl): ShortenedUrl = repository.save(entity)
}
