package software.design.lab3.refactoring.servlet

import software.design.lab3.refactoring.Application
import software.design.lab3.refactoring.servlet.instance.AddProductServlet
import software.design.lab3.refactoring.servlet.instance.GetProductsServlet
import software.design.lab3.refactoring.servlet.instance.QueryServlet
import javax.servlet.Servlet

object ServletModule {
    fun servlets(application: Application): Map<String, Servlet> = mapOf(
        "/add-product" to
                AddProductServlet(
                    application.html.writer,
                    application.domain.repository,
                ),
        "/get-products" to
                GetProductsServlet(
                    application.html.writer,
                    application.domain.repository,
                ),
        "/query" to
                QueryServlet(
                    application.html.writer,
                    application.domain.repository,
                ),
    )
}
