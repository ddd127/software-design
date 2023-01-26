package software.design.lab2.twitter

import com.twitter.clientlib.api.TweetsApi
import com.twitter.clientlib.api.TwitterApi
import com.twitter.clientlib.model.Get2TweetsCountsRecentResponse
import com.twitter.clientlib.model.SearchCount
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

object MockHelper {

    fun mockCountsRecentResponse(
        counts: List<Int>?,
    ): Get2TweetsCountsRecentResponse {
        val searchCountsMocks = counts?.map { count ->
            mock<SearchCount> {
                on { tweetCount } doReturn count
            }
        }
        return mock {
            on { data } doReturn searchCountsMocks
        }
    }

    fun mockTwitterSearchApi(
        supplier: () -> Get2TweetsCountsRecentResponse,
    ): TwitterApi {
        val requestMock: TweetsApi.APItweetCountsRecentSearchRequest = mock {
            on { startTime(any()) } doReturn this.mock
            on { endTime(any()) } doReturn this.mock
            on { granularity(any()) } doReturn this.mock
            on { execute() } doAnswer {
                supplier()
            }
        }
        val tweetsApiMock: TweetsApi = mock {
            on { tweetCountsRecentSearch(any()) } doReturn requestMock
        }
        return mock {
            on { tweets() } doReturn tweetsApiMock
        }
    }
}
