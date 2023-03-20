package software.design.lab10.events.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import software.design.lab10.events.applicaiton.TicketService
import software.design.lab10.events.web.dto.CheckRq

@RestController
@RequestMapping("/check")
class CheckInOutController @Autowired constructor(
    private val ticketService: TicketService,
) {

    @PostMapping("/in")
    @ResponseBody
    fun checkIn(@RequestBody rq: CheckRq): Boolean =
        ticketService.checkIn(rq.ticketId)

    @PostMapping("/out")
    @ResponseBody
    fun checkOut(@RequestBody rq: CheckRq) =
        ticketService.checkOut(rq.ticketId)
}
