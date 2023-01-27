package software.design.lab2.twitter.api.impl

import com.twitter.clientlib.api.TwitterApi
import software.design.lab2.twitter.api.TwitterApiProvider
import software.design.lab2.twitter.api.TwitterCredentialsWrapper

class TwitterApiProviderImpl(
    private val twitterUrl: String,
    private val credentialsWrapper: TwitterCredentialsWrapper,
) : TwitterApiProvider {
    override fun provide(): TwitterApi {
        val twitterClient = when (credentialsWrapper) {
            is TwitterCredentialsWrapper.Bearer ->
                TwitterApi(credentialsWrapper.credentials)
            is TwitterCredentialsWrapper.OAuth ->
                TwitterApi(credentialsWrapper.credentials)
        }
        twitterClient.apiClient.basePath = twitterUrl
        return twitterClient
    }
}
