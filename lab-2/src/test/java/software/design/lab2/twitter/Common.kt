package software.design.lab2.twitter

import software.design.lab2.twitter.client.diagram.Granularity
import software.design.lab2.twitter.client.diagram.HashtagDiagramRequest

object Common {
    const val hashtag = "#hashtag"
    const val hours = 12
    val granularity = Granularity.HOUR

    val defaultRequest = HashtagDiagramRequest(
        hashtag,
        hours,
        granularity
    )
}
