package software.design.lab3.refactoring.html

import software.design.lab3.refactoring.domain.model.Product
import javax.servlet.http.HttpServletResponse

interface ResponseWriter {
    fun writeOkResponse(response: HttpServletResponse)
    fun writeProductListResponse(response: HttpServletResponse, products: List<Product>)
    fun writeMinResponse(response: HttpServletResponse, product: Product?)
    fun writeMaxResponse(response: HttpServletResponse, product: Product?)
    fun writeCountResponse(response: HttpServletResponse, count: Long?)
    fun writeSumResponse(response: HttpServletResponse, sum: Long?)
}
