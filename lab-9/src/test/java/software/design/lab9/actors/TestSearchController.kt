package software.design.lab9.actors

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.TestPropertySource
import software.design.lab9.actors.web.SearchController
import software.design.lab9.actors.web.dto.SearchRqDto
import software.design.lab9.actors.web.dto.SearchRsDto
import software.design.lab9.actors.web.dto.SearchSystemDto
import software.design.lab9.actors.web.dto.SystemRsDto
import kotlin.test.assertEquals

@ComponentScan
@TestPropertySource("classpath:all-systems.properties")
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
)
class TestSearchController {

    @Autowired
    private lateinit var searchController: SearchController

    @Autowired
    private lateinit var stubSearchSystem: StubSearchSystem

    @BeforeEach
    fun resetDisabled() {
        stubSearchSystem.reset()
    }

    @Test
    fun `all system`() {
        val rq = SearchRqDto("random text")
        val expected = SearchRsDto(
            SearchSystemDto.values()
                .associateWith { systemDto ->
                    SystemRsDto(
                        systemDto,
                        (1..5).map { index ->
                            "Response $index for ${rq.text}"
                        }
                    )
                }
        )
        val actual = searchController.search(rq)
        assertEquals(expected, actual)
    }

    @Test
    fun `no systems`() {
        SearchSystemDto.values().forEach(stubSearchSystem::disable)
        val rq = SearchRqDto("random text")
        val expected = SearchRsDto(emptyMap())
        val actual = searchController.search(rq)
        assertEquals(expected, actual)
    }

    @Test
    fun `no google system`() {
        stubSearchSystem.disable(SearchSystemDto.GOOGLE)
        val rq = SearchRqDto("random text")
        val expected = SearchRsDto(
            SearchSystemDto.values()
                .filter { it != SearchSystemDto.GOOGLE }
                .associateWith { systemDto ->
                    SystemRsDto(
                        systemDto,
                        (1..5).map { index ->
                            "Response $index for ${rq.text}"
                        }
                    )
                }
        )
        val actual = searchController.search(rq)
        assertEquals(expected, actual)
    }
}
