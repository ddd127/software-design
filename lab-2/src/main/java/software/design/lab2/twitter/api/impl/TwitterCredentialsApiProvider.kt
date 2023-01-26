package software.design.lab2.twitter.api.impl

import com.twitter.clientlib.api.TwitterApi
import software.design.lab2.twitter.api.TwitterApiProvider
import software.design.lab2.twitter.api.TwitterCredentialsWrapper

class TwitterCredentialsApiProvider(
    private val credentialsWrapper: TwitterCredentialsWrapper,
) : TwitterApiProvider {
    override fun provide(): TwitterApi =
        when (credentialsWrapper) {
            is TwitterCredentialsWrapper.Bearer ->
                TwitterApi(credentialsWrapper.credentials)
            is TwitterCredentialsWrapper.OAuth ->
                TwitterApi(credentialsWrapper.credentials)
        }
}
