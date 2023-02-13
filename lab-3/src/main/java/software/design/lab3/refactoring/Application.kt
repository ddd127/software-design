package software.design.lab3.refactoring

import org.eclipse.jetty.server.Handler
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import software.design.lab3.refactoring.domain.DomainModule
import software.design.lab3.refactoring.html.HtmlModule
import software.design.lab3.refactoring.servlet.ServletModule

class Application {

    val domain = DomainModule
    val html = HtmlModule
    val servlet = ServletModule

    private val context: Handler =
        ServletContextHandler(ServletContextHandler.SESSIONS)
            .apply {
                contextPath = "/"
                servlet.servlets(this@Application).forEach { (path, servlet) ->
                    addServlet(ServletHolder(servlet), path)
                }
            }
    private val server: Server =
        Server(8081).apply {
            handler = context
        }

    fun run() {
        domain.repository.initTable()

        server.start()
        server.join()
    }
}

fun main() = Application().run()
