package software.design.lab10.events.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import software.design.lab10.events.applicaiton.TicketService
import software.design.lab10.events.applicaiton.model.Ticket
import software.design.lab10.events.web.dto.TicketUpdateRq

@RestController
@RequestMapping(path = ["/manager"])
class ManagerController @Autowired constructor(
    private val ticketService: TicketService,
) {

    @PostMapping("/ticket")
    @ResponseBody
    fun createTicket(
        @RequestBody rq: TicketUpdateRq,
    ): Ticket =
        ticketService.createTicket(rq.visits)

    @PutMapping("/ticket/{ticketId}/")
    @ResponseBody
    fun extendTicket(
        @PathVariable ticketId: Long,
        @RequestBody rq: TicketUpdateRq,
    ): Ticket =
        ticketService.extend(ticketId, rq.visits)

    @GetMapping("/ticket/{ticketId}")
    @ResponseBody
    fun getTicket(
        @PathVariable ticketId: Long,
    ): Ticket =
        ticketService.getTickets(
            setOf(ticketId),
        )[ticketId]
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Not found ticket with id = $ticketId"
            )
}
