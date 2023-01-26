package software.design.lab2.twitter.client

class TwitterClientException(
    message: String,
    original: Throwable? = null,
) : RuntimeException(message, original)
