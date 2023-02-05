package software.design.lab2.twitter.client

import software.design.lab2.twitter.client.diagram.FrequencyDiagram
import software.design.lab2.twitter.client.diagram.HashtagDiagramRequest

interface TwitterApiClient {

    fun getHashtagDiagram(
        request: HashtagDiagramRequest,
    ): FrequencyDiagram
}
