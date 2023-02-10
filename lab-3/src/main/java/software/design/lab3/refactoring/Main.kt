package software.design.lab3.refactoring

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import software.design.lab3.refactoring.domain.DatasourceConfiguration
import software.design.lab3.refactoring.domain.repository.ProductRepository
import software.design.lab3.refactoring.domain.repository.impl.ProductRepositoryImpl
import software.design.lab3.refactoring.html.ResponseWriter
import software.design.lab3.refactoring.html.impl.ResponseWriterImpl
import software.design.lab3.refactoring.servlet.AddProductServlet
import software.design.lab3.refactoring.servlet.GetProductsServlet
import software.design.lab3.refactoring.servlet.QueryServlet

fun main() {
    val writer: ResponseWriter = ResponseWriterImpl
    val repository: ProductRepository = ProductRepositoryImpl(DatasourceConfiguration)

    repository.initTable()

    val server = Server(8081)
    val context = ServletContextHandler(ServletContextHandler.SESSIONS)
    context.contextPath = "/"
    server.handler = context

    context.createServlets(writer, repository)

    server.start()
    server.join()
}

private fun ServletContextHandler.createServlets(
    writer: ResponseWriter,
    repository: ProductRepository,
) {
    addServlet(
        ServletHolder(AddProductServlet(writer, repository)),
        "/add-product",
    )
    addServlet(
        ServletHolder(GetProductsServlet(writer, repository)),
        "/get-products",
    )
    addServlet(
        ServletHolder(QueryServlet(writer, repository)),
        "/query",
    )
}
