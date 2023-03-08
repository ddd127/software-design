package software.design.lab9.actors.application.actor

import akka.actor.UntypedAbstractActor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import software.design.lab9.actors.application.system.SearchSystem
import software.design.lab9.actors.web.dto.SearchRqDto
import java.net.http.HttpClient
import java.net.http.HttpResponse

class SearchSystemActor(
    private val system: SearchSystem,
) : UntypedAbstractActor() {

    private val client: HttpClient = HttpClient.newHttpClient()

    private val bodyHandler = HttpResponse.BodyHandlers.ofInputStream()

    override fun onReceive(rawMessage: Any?) {
        LOG.debug("Got message")
        val message = rawMessage as SearchRqDto
        val request = system.convertRequest(message)
        LOG.debug("Successfully sent request")
        val response = client.send(request, bodyHandler).let(system::convertResponseBody)
        LOG.debug("Successfully got response")
        sender.tell(response, self)
        LOG.debug("Successfully tell response")
    }

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(SearchSystemActor::class.java)
    }
}
