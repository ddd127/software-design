package software.design.lab3.refactoring.html.impl

import software.design.lab3.refactoring.domain.model.Product
import software.design.lab3.refactoring.html.ResponseWriter
import javax.servlet.http.HttpServletResponse

object ResponseWriterImpl : ResponseWriter {

    override fun writeOkResponse(
        response: HttpServletResponse,
    ) = response.ok {
        writer.write("OK")
    }

    override fun writeProductListResponse(
        response: HttpServletResponse,
        products: List<Product>,
    ) = response.ok {
        body {
            products.forEach { product(it) }
        }
    }

    override fun writeMinResponse(
        response: HttpServletResponse,
        product: Product?,
    ) = response.ok {
        body {
            response.writer.println("<h1>Product with min price: </h1>")
            product?.also { product(it) }
        }
    }

    override fun writeMaxResponse(
        response: HttpServletResponse,
        product: Product?,
    ) = response.ok {
        body {
            response.writer.println("<h1>Product with max price: </h1>")
            product?.also { product(it) }
        }
    }

    override fun writeCountResponse(
        response: HttpServletResponse,
        count: Long?,
    ) = response.ok {
        body {
            response.writer.println("Number of products: ")
            count?.also { writer.println(it) }
        }
    }

    override fun writeSumResponse(
        response: HttpServletResponse,
        sum: Long?,
    ) = response.ok {
        body {
            response.writer.println("Summary price: ")
            sum?.also { writer.println(it) }
        }
    }

    private fun HttpServletResponse.ok(content: HttpServletResponse.() -> Unit) {
        content()
        contentType = "text/html"
        status = HttpServletResponse.SC_OK
    }

    private fun HttpServletResponse.body(content: HttpServletResponse.() -> Unit) {
        writer.println("<html><body>")
        content()
        writer.println("</body></html>")
    }

    private fun HttpServletResponse.product(product: Product) {
        writer.println("${product.name}\t${product.price}</br>")
    }
}
