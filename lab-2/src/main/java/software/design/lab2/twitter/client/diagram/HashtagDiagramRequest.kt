package software.design.lab2.twitter.client.diagram

import java.time.OffsetDateTime

data class HashtagDiagramRequest(
    val hashtag: String,
    val hours: Int,
    val granularity: Granularity,
    val endTime: OffsetDateTime = OffsetDateTime.now(),
)
