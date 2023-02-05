package software.design.lab2.twitter.api

import com.twitter.clientlib.api.TwitterApi

interface TwitterApiProvider {
    fun provide(): TwitterApi
}
