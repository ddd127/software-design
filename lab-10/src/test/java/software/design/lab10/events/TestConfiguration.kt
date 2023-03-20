package software.design.lab10.events

import io.zonky.test.db.postgres.embedded.EmbeddedPostgres
import kotlinx.datetime.Clock
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.test.context.TestPropertySource
import javax.sql.DataSource

@Configuration
@ComponentScan
//@AutoConfigureEmbeddedDatabase(
//    type = AutoConfigureEmbeddedDatabase.DatabaseType.POSTGRES,
//    provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.EMBEDDED,
//)
@TestPropertySource("classpath:context-testing.properties")
class TestConfiguration {

    @Bean
    fun dataSource(): DataSource =
        EmbeddedPostgres.start().postgresDatabase

    @Bean
    @Primary
    fun testClock(): Clock = TestClock
}
