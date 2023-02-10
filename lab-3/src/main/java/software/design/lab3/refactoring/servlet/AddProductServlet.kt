package software.design.lab3.refactoring.servlet

import software.design.lab3.refactoring.domain.model.Product
import software.design.lab3.refactoring.domain.repository.ProductRepository
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AddProductServlet(
    private val productRepository: ProductRepository,
) : HttpServlet() {

    override fun doGet(
        request: HttpServletRequest,
        response: HttpServletResponse,
    ) {
        val product = Product(
            request.getParameter("name"),
            request.getParameter("price").toLong(),
        )
        productRepository.create(product)
        response.contentType = "text/html"
        response.status = HttpServletResponse.SC_OK
        response.writer.println("OK")
    }
}
