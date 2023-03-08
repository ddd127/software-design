package software.design.lab9.actors.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import software.design.lab9.actors.application.SearchService
import software.design.lab9.actors.web.dto.SearchRqDto
import software.design.lab9.actors.web.dto.SearchRsDto

@RestController
class SearchController @Autowired constructor(
    private val searchService: SearchService,
) {

    @GetMapping("/api/search")
    fun search(rq: SearchRqDto): SearchRsDto {
        return searchService.search(rq)
    }
}
