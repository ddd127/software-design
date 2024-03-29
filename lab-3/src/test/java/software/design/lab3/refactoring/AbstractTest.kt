package software.design.lab3.refactoring

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import software.design.lab3.refactoring.AbstractTest.Companion.Response.Companion.CONTENT_AFFIX
import software.design.lab3.refactoring.domain.ConnectionFactory
import software.design.lab3.refactoring.domain.repository.ProductRepository
import software.design.lab3.refactoring.domain.repository.impl.ProductRepositoryImpl
import software.design.lab3.refactoring.html.ResponseWriter
import software.design.lab3.refactoring.html.impl.ResponseWriterImpl
import software.design.lab3.refactoring.servlet.instance.AddProductServlet
import software.design.lab3.refactoring.servlet.instance.GetProductsServlet
import software.design.lab3.refactoring.servlet.instance.QueryServlet
import java.net.HttpURLConnection
import java.net.URLEncoder
import java.sql.DriverManager

abstract class AbstractTest {

    @BeforeEach
    fun initDb() {
        DriverManager.getConnection(DATASOURCE_URL).use { connection ->
            val drop = """
                DROP TABLE IF EXISTS PRODUCT;
            """.trimIndent()
            connection.createStatement().apply {
                use {
                    execute(drop)
                }
            }
            val create = """
                CREATE TABLE IF NOT EXISTS PRODUCT (
                    ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    NAME                   TEXT          NOT NULL,
                    PRICE                  INT           NOT NULL
                );
            """.trimIndent()
            connection.createStatement().apply {
                use {
                    try {
                        execute(create)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    companion object {

        const val DATASOURCE_URL = "jdbc:sqlite:test.db"

        private val factory = ConnectionFactory {
            DriverManager.getConnection(DATASOURCE_URL)
        }

        private val repository: ProductRepository =
            ProductRepositoryImpl(factory)
        private val writer: ResponseWriter =
            ResponseWriterImpl

        private lateinit var server: Server

        fun request(
            path: String,
            queryParams: Map<String, String> = emptyMap(),
        ): Response {
            val encodedParams = if (queryParams.isEmpty()) {
                ""
            } else {
                queryParams.entries.joinToString(
                    separator = "&",
                    prefix = "?",
                ) { (key, value) ->
                    val encodedKey = URLEncoder.encode(key, Charsets.UTF_8)
                    val encodedValue = URLEncoder.encode(value, Charsets.UTF_8)
                    "$encodedKey=$encodedValue"
                }
            }
            val url = server.uri
                .resolve("$path$encodedParams")
                .toURL()
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            connection.connect()
            return Response(
                connection.responseCode,
                connection.inputStream.bufferedReader(Charsets.UTF_8)
                    .readLines() - CONTENT_AFFIX,
            ).also {
                connection.disconnect()
            }
        }

        @BeforeAll
        @JvmStatic
        fun startServer() {
            server = Server(8080)
            val context = ServletContextHandler(ServletContextHandler.SESSIONS)
            context.contextPath = "/"
            server.handler = context
            context.addServlet(ServletHolder(AddProductServlet(writer, repository)), "/add-product")
            context.addServlet(ServletHolder(GetProductsServlet(writer, repository)), "/get-products")
            context.addServlet(ServletHolder(QueryServlet(writer, repository)), "/query")
            server.start()
        }

        @AfterAll
        @JvmStatic
        fun stopServer() {
            server.stop()
            server.join()
        }

        data class Response(
            val code: Int,
            val pageContent: List<String>,
        ) {
            companion object {
                val CONTENT_AFFIX = setOf(
                    "<html><body>",
                    "</body></html>",
                )
            }
        }
    }
}
