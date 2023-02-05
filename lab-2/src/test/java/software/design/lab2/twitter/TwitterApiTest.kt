package software.design.lab2.twitter

import com.google.common.base.Strings
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.twitter.clientlib.TwitterCredentialsBearer
import org.junit.jupiter.api.Test
import software.design.lab2.twitter.api.TwitterCredentialsWrapper
import software.design.lab2.twitter.api.impl.TwitterApiProviderImpl
import software.design.lab2.twitter.client.diagram.FrequencyDiagram
import software.design.lab2.twitter.client.diagram.Granularity
import software.design.lab2.twitter.client.diagram.HashtagDiagramRequest
import software.design.lab2.twitter.client.impl.TwitterApiClientImpl
import java.net.URI
import java.net.URLDecoder
import java.time.Duration
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlin.test.assertEquals


class TwitterApiTest {

    @Test
    fun testParamsPassing() {

        val endTime = Instant.EPOCH.plus(Duration.ofDays(10_000)).atOffset(ZoneOffset.UTC)
        val request = HashtagDiagramRequest(
            "#hashtag",
            3,
            Granularity.HOUR,
            endTime,
        )

        val handler = HttpHandler { exchange ->
            validateExchange(request, exchange)

            val response = buildResponse(request)
            exchange.sendResponseHeaders(200, response.length.toLong())

            val output = exchange.responseBody
            output.write(response.toByteArray(Charsets.UTF_8))
            exchange.close()
        }
        val credentialsWrapper = TwitterCredentialsWrapper.Bearer(TwitterCredentialsBearer("fake"))
        val api = TwitterApiProviderImpl("http://localhost:8080", credentialsWrapper).provide()
        val client = TwitterApiClientImpl(api)
        StubServer.withSubServer("/", 8080, handler) {
            val expected = (0 until request.hours)
                .associateWith(::hourToTweets)
                .let(::FrequencyDiagram)
            val actual = client.getHashtagDiagram(request)
            assertEquals(expected, actual)
        }
    }

    companion object {

        private fun validateExchange(
            request: HashtagDiagramRequest,
            exchange: HttpExchange,
        ) {
            val path = exchange.requestURI.path
            val params = splitQuery(exchange.requestURI)

            assertEquals("/2/tweets/counts/recent", path)

            assertEquals(4, params.size)
            assertEquals(
                request.hashtag,
                params.getValue("query").single(),
            )
            assertEquals(
                request.granularity.parameterValue,
                params.getValue("granularity").single(),
            )
            assertEquals(
                request.endTime.isoFormat(),
                params.getValue("end_time").single(),
            )
            assertEquals(
                request.endTime.minusHours(request.hours.toLong()).isoFormat(),
                params.getValue("start_time").single(),
            )

        }

        private fun splitQuery(url: URI): Map<String, List<String?>> {
            return if (Strings.isNullOrEmpty(url.query)) {
                emptyMap()
            } else url.query.split("&")
                .map(::splitQueryParameter)
                .groupBy { it.first }
                .mapValues { (_, pairs) ->
                    pairs.map { it.second }
                }
        }

        private fun splitQueryParameter(it: String): Pair<String, String?> {
            val idx = it.indexOf("=")
            if (idx == -1) {
                return URLDecoder.decode(it, Charsets.UTF_8) to null
            }
            val key = it.substring(0, idx)
            val value = it.substring(idx + 1).takeIf { it.isNotEmpty() }
            return URLDecoder.decode(key, Charsets.UTF_8) to
                    URLDecoder.decode(value, Charsets.UTF_8)
        }

        private fun OffsetDateTime.isoFormat() = format(DateTimeFormatter.ISO_DATE_TIME)

        private fun hourToTweets(hour: Int): Int {
            return hour * 127 % 17
        }

        private val responsePrefix = """
            {
                "data": [
            """.trimIndent()

        private fun buildResponse(request: HashtagDiagramRequest): String =
            (0 until request.hours).reversed().map { it.toLong() }
                .joinToString(
                    ", ",
                    prefix = responsePrefix,
                    postfix = responsePostfix
                ) { hour ->
                    """
                    {
                        "start": "${request.endTime.minusHours(hour + 1).isoFormat()}",
                        "end": "${request.endTime.minusHours(hour).isoFormat()}",
                        "tweet_count": ${hourToTweets((request.hours - hour - 1).toInt())}
                    }
                    """.trimIndent()
                }

        private val responsePostfix = """
                ]
            }
            """.trimIndent()
    }
}
