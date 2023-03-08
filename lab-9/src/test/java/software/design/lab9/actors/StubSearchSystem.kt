package software.design.lab9.actors

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import software.design.lab9.actors.web.dto.SearchRqDto
import software.design.lab9.actors.web.dto.SearchSystemDto
import software.design.lab9.actors.web.dto.SystemRsDto

@RestController
class StubSearchSystem {

    private val disabled: MutableSet<SearchSystemDto> = mutableSetOf()

    fun disable(system: SearchSystemDto) {
        disabled += system
    }

    fun reset() {
        disabled.clear()
    }

    @PostMapping("/api/stub-search-system")
    @ResponseBody
    fun fakeSearch(@RequestBody rq: SearchRqDto): SystemRsDto {
        val system = rq.systems.first()
        if (system in disabled) {
            Thread.sleep(10_000)
            throw IllegalArgumentException("Trying to get disabled system")
        }
        return SystemRsDto(
            rq.systems.first(),
            (1..5).map { index ->
                "Response $index for ${rq.text}"
            }
        )
    }
}
