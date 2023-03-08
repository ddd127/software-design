package software.design.lab9.actors.application.actor

import akka.actor.ActorRef
import akka.actor.Props
import akka.actor.ReceiveTimeout
import akka.actor.UntypedAbstractActor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import software.design.lab9.actors.application.system.SearchSystem
import software.design.lab9.actors.web.dto.Message
import software.design.lab9.actors.web.dto.SearchRqDto
import software.design.lab9.actors.web.dto.SearchRsDto
import software.design.lab9.actors.web.dto.SearchSystemDto
import software.design.lab9.actors.web.dto.SystemRsDto
import java.time.Duration

class SearchMasterActor(
    private val systems: Map<SearchSystemDto, SearchSystem>,
) : UntypedAbstractActor() {

    private val responses = mutableMapOf<SearchSystemDto, SystemRsDto>()
    private lateinit var target: ActorRef

    override fun onReceive(rawMessage: Any?) {
        if (rawMessage is ReceiveTimeout) {
            LOG.debug("Received ReceiveTimeout message")
            target.tell(SearchRsDto(responses), self)
            context.stop(self)
            return
        }
        if (rawMessage !is Message) {
            throw IllegalArgumentException("Unexpected type of message ${rawMessage?.let { it::class.simpleName }}")
        }
        processMessage(rawMessage)
        if (responses.size == systems.size) {
            target.tell(SearchRsDto(responses), self)
            context.stop(self)
        }
    }

    private fun processMessage(message: Message) {
        when (message) {
            is SearchRqDto -> {
                LOG.debug("Received SearchRqDto message")
                target = sender
                systems.forEach { (dto, system) ->
                    val actorRef: ActorRef = context.actorOf(
                        Props.create(
                            SearchSystemActor::class.java,
                            system,
                        ),
                        dto.name,
                    )
                    actorRef.tell(message, self)
                }
                context.receiveTimeout = Duration.ofSeconds(5)
            }

            is SystemRsDto -> {
                LOG.debug("Received SystemRsDto message")
                responses[message.system] = message
            }

            else -> {
                val string = "Unexpected message type = ${message::class.simpleName}"
                LOG.error(string)
                throw IllegalArgumentException(string)
            }
        }
    }

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(SearchMasterActor::class.java)
    }
}
