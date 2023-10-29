package org.example.kotlinurl.repository

import org.example.kotlinurl.model.ShortenedUrl
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UrlRepository : CrudRepository<ShortenedUrl, String>