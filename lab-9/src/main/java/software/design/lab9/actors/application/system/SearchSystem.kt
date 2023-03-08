package software.design.lab9.actors.application.system

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import software.design.lab9.actors.application.system.SearchSystem.Companion.MAPPER
import software.design.lab9.actors.web.dto.SearchRqDto
import software.design.lab9.actors.web.dto.SearchSystemDto
import software.design.lab9.actors.web.dto.SystemRsDto
import java.io.InputStream
import java.net.URI
import java.net.http.HttpRequest
import java.net.http.HttpRequest.BodyPublishers
import java.net.http.HttpResponse

interface SearchSystem {
    val systemDto: SearchSystemDto
    fun convertRequest(rq: SearchRqDto): HttpRequest
    fun convertResponseBody(rs: HttpResponse<InputStream>): SystemRsDto

    companion object {
        val MAPPER: ObjectMapper = ObjectMapper()
    }
}

@Component
class YandexSearchSystem @Autowired constructor(
    @Value("\${search.system.yandex.url}")
    private val url: String,
) : SearchSystem {

    override val systemDto: SearchSystemDto = SearchSystemDto.YANDEX

    override fun convertRequest(rq: SearchRqDto): HttpRequest =
        HttpRequest.newBuilder(URI(url))
            .method(
                "POST",
                BodyPublishers.ofString(
                    MAPPER.writeValueAsString(
                        rq.copy(systems = setOf(SearchSystemDto.YANDEX)),
                    ),
                ),
            )
            .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .build()

    override fun convertResponseBody(rs: HttpResponse<InputStream>): SystemRsDto =
        MAPPER.readValue(rs.body(), SystemRsDto::class.java)
}

@Component
class GoogleSearchSystem @Autowired constructor(
    @Value("\${search.system.google.url}")
    private val url: String,
) : SearchSystem {

    override val systemDto: SearchSystemDto = SearchSystemDto.GOOGLE

    override fun convertRequest(rq: SearchRqDto): HttpRequest =
        HttpRequest.newBuilder(URI(url))
            .method(
                "POST",
                BodyPublishers.ofString(
                    MAPPER.writeValueAsString(
                        rq.copy(systems = setOf(SearchSystemDto.GOOGLE)),
                    ),
                ),
            )
            .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .build()

    override fun convertResponseBody(rs: HttpResponse<InputStream>): SystemRsDto =
        MAPPER.readValue(rs.body().readAllBytes().toString(Charsets.UTF_8), SystemRsDto::class.java)
}

@Component
class YahooSearchSystem @Autowired constructor(
    @Value("\${search.system.yahoo.url}")
    private val url: String,
) : SearchSystem {

    override val systemDto: SearchSystemDto = SearchSystemDto.YAHOO

    override fun convertRequest(rq: SearchRqDto): HttpRequest =
        HttpRequest.newBuilder(URI(url))
            .method(
                "POST",
                BodyPublishers.ofString(
                    MAPPER.writeValueAsString(
                        rq.copy(systems = setOf(SearchSystemDto.YAHOO)),
                    ),
                ),
            )
            .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .build()

    override fun convertResponseBody(rs: HttpResponse<InputStream>): SystemRsDto =
        MAPPER.readValue(rs.body(), SystemRsDto::class.java)
}
