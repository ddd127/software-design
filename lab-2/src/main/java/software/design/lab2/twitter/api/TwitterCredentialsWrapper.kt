package software.design.lab2.twitter.api

import com.twitter.clientlib.TwitterCredentialsBearer
import com.twitter.clientlib.TwitterCredentialsOAuth2

sealed interface TwitterCredentialsWrapper {

    class Bearer(
        val credentials: TwitterCredentialsBearer,
    ) : TwitterCredentialsWrapper

    class OAuth(
        val credentials: TwitterCredentialsOAuth2,
    ) : TwitterCredentialsWrapper
}
