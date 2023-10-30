package org.example.urlshortener.repository

import org.example.urlshortener.model.ShortenedUrl
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UrlRepository : CrudRepository<ShortenedUrl, String>
