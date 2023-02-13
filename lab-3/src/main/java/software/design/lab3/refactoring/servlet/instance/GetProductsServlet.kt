package software.design.lab3.refactoring.servlet.instance

import software.design.lab3.refactoring.domain.repository.ProductRepository
import software.design.lab3.refactoring.html.ResponseWriter
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class GetProductsServlet(
    private val responseWriter: ResponseWriter,
    private val productRepository: ProductRepository,
): HttpServlet() {

    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        responseWriter.writeProductListResponse(
            response,
            productRepository.getAll(),
        )
    }
}
