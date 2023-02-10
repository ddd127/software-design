package software.design.lab3.refactoring.servlet

import software.design.lab3.refactoring.domain.model.SortDirection
import software.design.lab3.refactoring.domain.repository.ProductRepository
import software.design.lab3.refactoring.html.ResponseWriter
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class QueryServlet(
    private val responseWriter: ResponseWriter,
    private val productRepository: ProductRepository,
) : HttpServlet() {

    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        when (request.getParameter("command")) {
            "max" -> {
                responseWriter.writeMaxResponse(
                    response,
                    productRepository.getOrderByPrice(1, SortDirection.DESC).singleOrNull(),
                )
            }
            "min" -> {
                responseWriter.writeMinResponse(
                    response,
                    productRepository.getOrderByPrice(1, SortDirection.ASC).singleOrNull(),
                )
            }
            "sum" -> {
                responseWriter.writeSumResponse(
                    response,
                    productRepository.priceSum(),
                )
            }
            "count" -> {
                responseWriter.writeCountResponse(
                    response,
                    productRepository.count(),
                )
            }
        }
    }
}
