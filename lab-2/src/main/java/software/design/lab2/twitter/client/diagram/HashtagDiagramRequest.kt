package software.design.lab2.twitter.client.diagram

data class HashtagDiagramRequest(
    val hashtag: String,
    val hours: Int,
    val granularity: Granularity,
)
