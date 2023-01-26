package software.design.lab2.twitter

import com.twitter.clientlib.ApiException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import software.design.lab2.twitter.client.TwitterApiClient
import software.design.lab2.twitter.client.TwitterClientException
import software.design.lab2.twitter.client.diagram.FrequencyDiagram
import software.design.lab2.twitter.client.diagram.Granularity
import software.design.lab2.twitter.client.diagram.HashtagDiagramRequest
import software.design.lab2.twitter.client.impl.TwitterApiClientImpl
import kotlin.test.assertEquals
import kotlin.test.assertNull

class TwitterClientTest {

    @Test
    fun testEmptyResponse() {
        val response = MockHelper.mockCountsRecentResponse(emptyList())
        val api = MockHelper.mockTwitterSearchApi { response }
        val client: TwitterApiClient = TwitterApiClientImpl(api)

        val expected = FrequencyDiagram(emptyMap())
        val actual = client.getHashtagDiagram(defaultRequest)

        assertEquals(expected, actual)
    }

    @Test
    fun testUsualResponse() {
        val counts = listOf(2, 1, 3)
        val response = MockHelper.mockCountsRecentResponse(counts)
        val api = MockHelper.mockTwitterSearchApi { response }
        val client: TwitterApiClient = TwitterApiClientImpl(api)

        val expected = FrequencyDiagram(
            counts.mapIndexed { index, count ->
                index to count
            }.toMap()
        )
        val actual = client.getHashtagDiagram(defaultRequest)

        assertEquals(expected, actual)
    }

    @Test
    fun testApiException() {
        val apiException = ApiException()
        val api = MockHelper.mockTwitterSearchApi {
            throw apiException
        }
        val client: TwitterApiClient = TwitterApiClientImpl(api)

        val actual = assertThrows<TwitterClientException> {
            client.getHashtagDiagram(defaultRequest)
        }
        assertEquals(apiException, actual.cause)
        assertEquals(
            "Failed to query TwitterApi",
            actual.message,
        )
    }

    @Test
    fun testNullData() {
        val response = MockHelper.mockCountsRecentResponse(null)
        val api = MockHelper.mockTwitterSearchApi { response }
        val client: TwitterApiClient = TwitterApiClientImpl(api)

        val expected = assertThrows<TwitterClientException> {
            client.getHashtagDiagram(defaultRequest)
        }
        assertNull(expected.cause)
        assertEquals(
            "Got null data",
            expected.message,
        )
    }

    companion object {
        private const val hashtag = "#hashtag"
        private const val hours = 12
        private val granularity = Granularity.HOUR

        private val defaultRequest = HashtagDiagramRequest(
            hashtag,
            hours,
            granularity
        )
    }
}
