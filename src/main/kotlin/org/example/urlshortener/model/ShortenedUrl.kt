package org.example.urlshortener.model

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("shortenedUrl")
data class ShortenedUrl(@Id var id: String, var originalUrl: String)
