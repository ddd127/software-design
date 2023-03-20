package software.design.lab10.events.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import software.design.lab10.events.applicaiton.StatisticService
import software.design.lab10.events.applicaiton.model.statistic.DayVisitsStatistic
import software.design.lab10.events.applicaiton.model.statistic.TicketStatistic

@RestController
@RequestMapping("/statistic")
class StatisticController @Autowired constructor(
    private val statisticService: StatisticService,
) {

    @GetMapping("/dayVisits")
    @ResponseBody
    fun getDayVisitsStatistic(): DayVisitsStatistic =
        statisticService.getDayVisitStatistic()

    @GetMapping("/ticket")
    @ResponseBody
    fun getTicketStatistic(): TicketStatistic =
        statisticService.getTicketStatistic()
}
