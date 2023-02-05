package software.design.lab3.refactoring

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import software.design.lab3.refactoring.servlet.AddProductServlet
import software.design.lab3.refactoring.servlet.GetProductsServlet
import software.design.lab3.refactoring.servlet.QueryServlet
import java.sql.DriverManager

fun main() {
    DriverManager.getConnection("jdbc:sqlite:test.db").use { c ->
        val sql = """
            CREATE TABLE IF NOT EXISTS PRODUCT
            (
                ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                NAME                   TEXT          NOT NULL,
                PRICE                  INT           NOT NULL
            );
        """.trimIndent()
        val stmt = c.createStatement()
        stmt.executeUpdate(sql)
        stmt.close()
    }
    val server = Server(8081)
    val context = ServletContextHandler(ServletContextHandler.SESSIONS)
    context.contextPath = "/"
    server.handler = context
    context.addServlet(ServletHolder(AddProductServlet()), "/add-product")
    context.addServlet(ServletHolder(GetProductsServlet()), "/get-products")
    context.addServlet(ServletHolder(QueryServlet()), "/query")
    server.start()
    server.join()
}
