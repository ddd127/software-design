package software.design.lab3.refactoring.servlet

import software.design.lab3.refactoring.domain.repository.ProductRepository
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class GetProductsServlet(
    private val productRepository: ProductRepository,
): HttpServlet() {

    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        response.writer.println("<html><body>")
        productRepository.getAll().forEach {
            response.writer.println("${it.name}\t${it.price}</br>")
        }
        response.writer.println("</body></html>")
        response.contentType = "text/html"
        response.status = HttpServletResponse.SC_OK
    }
}
