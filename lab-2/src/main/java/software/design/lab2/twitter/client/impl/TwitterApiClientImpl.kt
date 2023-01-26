package software.design.lab2.twitter.client.impl

import com.twitter.clientlib.ApiException
import com.twitter.clientlib.api.TwitterApi
import com.twitter.clientlib.model.SearchCount
import mu.KotlinLogging
import software.design.lab2.twitter.client.diagram.FrequencyDiagram
import software.design.lab2.twitter.client.TwitterApiClient
import software.design.lab2.twitter.client.TwitterClientException
import software.design.lab2.twitter.client.diagram.HashtagDiagramRequest
import java.time.OffsetDateTime

class TwitterApiClientImpl(
    private val api: TwitterApi,
) : TwitterApiClient {

    override fun getHashtagDiagram(
        request: HashtagDiagramRequest,
    ): FrequencyDiagram {
        validateParams(request)
        val endTime = OffsetDateTime.now()
        val startTime = endTime.minus(request.hours.toLong(), request.granularity.temporalUnit)
        try {
            val response = api.tweets().tweetCountsRecentSearch(request.hashtag)
                .startTime(startTime)
                .endTime(endTime)
                .granularity(request.granularity.parameterValue)
                .execute()
            val data: List<SearchCount>? = response.data
            if (data == null) {
                logger.warn {
                    "Failed to get data from twitter api"
                }
                throw TwitterClientException("Got null data")
            }
            val hourToTweetsCount = data.mapIndexed { index, searchCount ->
                index to searchCount.tweetCount
            }.toMap()
            return FrequencyDiagram(hourToTweetsCount)
        } catch (e: ApiException) {
            logger.warn(e) {
                "Got exception from twitter api on tweet counts query"
            }
            throw TwitterClientException("Failed to query TwitterApi", e)
        }
    }

    private fun validateParams(
        request: HashtagDiagramRequest,
    ) {
        assert(request.hours in 1..24) {
            "Unsupported duration provided: expecting integer in 1..24 but got ${request.hours}"
        }
    }

    companion object {
        private val logger = KotlinLogging.logger { }
    }
}
