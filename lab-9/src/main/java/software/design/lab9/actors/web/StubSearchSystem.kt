package software.design.lab9.actors.web

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import software.design.lab9.actors.web.dto.SearchRqDto
import software.design.lab9.actors.web.dto.SystemRsDto

@RestController
interface StubSearchSystem {

    @PostMapping("/api/stub-search-system")
    fun fakeSearch(@RequestBody rq: SearchRqDto): SystemRsDto {
        return SystemRsDto(
            rq.systems.first(),
            (1..5).map { index ->
                "Response $index for ${rq.text}"
            }
        )
    }
}
