package org.example.urlshortener

import com.redis.testcontainers.RedisContainer
import org.junit.jupiter.api.Assertions.assertEquals
import org.example.urlshortener.model.ShortenedUrl
import org.example.urlshortener.repository.UrlRepository
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.shaded.com.google.common.hash.Hashing
import org.testcontainers.utility.DockerImageName
import java.nio.charset.StandardCharsets
import java.util.Optional

@Testcontainers
@ExtendWith(SpringExtension::class)
@SpringBootTest
class UrlShortenerApplicationTests {

	@Autowired
	private lateinit var urlRepository: UrlRepository
	private val longUrlForTest = "http://apple.com"

	companion object {
		@Container
		private val redisContainer = RedisContainer(DockerImageName.parse("redis:latest"))

		@JvmStatic
		@DynamicPropertySource
		fun redisProperties(registry: DynamicPropertyRegistry) {
			registry.add("spring.data.redis.repositories.enabled") { true }
			registry.add("spring.redis.host") { redisContainer.host }
			registry.add("spring.redis.port") { redisContainer.firstMappedPort }
		}
	}

	@BeforeEach
	fun cleanUp() = urlRepository.deleteAll()

	@Test
	fun `should save and find a ShortenedUrl by id`() {
		val shortUrlId = generateShortUrlId(longUrlForTest)
		urlRepository.save(ShortenedUrl(shortUrlId, longUrlForTest))

		val foundShortUrl: Optional<ShortenedUrl> = urlRepository.findById(shortUrlId)
		assertTrue(foundShortUrl.isPresent)

		val shortUrl: ShortenedUrl = foundShortUrl.get()
		assertEquals(shortUrlId, shortUrl.id)
		assertEquals(longUrlForTest, shortUrl.originalUrl)
	}

	// other tests such as update, delete, REST API ones, etc..

	@Test
	fun contextLoads() {
	}

	private fun generateShortUrlId(longUrl: String): String = Hashing.murmur3_32().hashString(longUrl, StandardCharsets.UTF_8).toString()
}
