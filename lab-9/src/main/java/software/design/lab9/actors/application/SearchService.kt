package software.design.lab9.actors.application

import akka.actor.ActorSystem
import akka.actor.Props
import akka.pattern.Patterns.ask
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import software.design.lab9.actors.application.actor.SearchMasterActor
import software.design.lab9.actors.application.system.SearchSystem
import software.design.lab9.actors.web.dto.SearchRqDto
import software.design.lab9.actors.web.dto.SearchRsDto
import java.time.Duration

@Service
class SearchService @Autowired constructor(
    private val searchActorSystem: ActorSystem,
    searchSystems: List<SearchSystem>,
) {

    private val searchSystems = searchSystems.associateBy { it.systemDto }

    fun search(rq: SearchRqDto): SearchRsDto {
        LOG.debug("Executing search request")
        val master = searchActorSystem.actorOf(
            Props.create(
                SearchMasterActor::class.java,
                searchSystems,
            ),
        )
        LOG.debug("Created master actor")
        val future = ask(master, rq, Duration.ofSeconds(7)).toCompletableFuture()
        LOG.debug("Sent future")
        val result = future.get() as SearchRsDto
        LOG.debug("Got result")
        return result
    }

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(SearchService::class.java)
    }
}
