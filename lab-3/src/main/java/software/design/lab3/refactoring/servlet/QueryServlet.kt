package software.design.lab3.refactoring.servlet

import software.design.lab3.refactoring.domain.model.SortDirection
import software.design.lab3.refactoring.domain.repository.ProductRepository
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class QueryServlet(
    private val productRepository: ProductRepository,
) : HttpServlet() {

    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        response.writer.println("<html><body>")
        when (request.getParameter("command")) {
            "max" -> {
                response.writer.println("<h1>Product with max price: </h1>")
                productRepository.getOrderByPrice(1, SortDirection.DESC)
                    .singleOrNull()
                    ?.also {
                        response.writer.println("${it.name}\t${it.price}</br>")
                    }
            }
            "min" -> {
                response.writer.println("<h1>Product with min price: </h1>")
                productRepository.getOrderByPrice(1, SortDirection.ASC)
                    .singleOrNull()
                    ?.also {
                        response.writer.println("${it.name}\t${it.price}</br>")
                    }
            }
            "sum" -> {
                response.writer.println("Summary price: ")
                productRepository.priceSum()?.also { sum ->
                    response.writer.println(sum)
                }
            }
            "count" -> {
                response.writer.println("Number of products: ")
                productRepository.count()?.also { count ->
                    response.writer.println(count)
                }
            }
        }
        response.writer.println("</body></html>")

        response.contentType = "text/html"
        response.status = HttpServletResponse.SC_OK
    }
}
