package software.design.lab3.refactoring.servlet

import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class GetProductsServlet : HttpServlet() {

    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        try {
            DriverManager.getConnection("jdbc:sqlite:test.db").use { c ->
                val stmt: Statement = c.createStatement()
                val rs: ResultSet = stmt.executeQuery("SELECT * FROM PRODUCT")
                response.writer.println("<html><body>")
                while (rs.next()) {
                    val name = rs.getString("name")
                    val price = rs.getInt("price")
                    response.writer.println("$name\t$price</br>")
                }
                response.writer.println("</body></html>")
                rs.close()
                stmt.close()
            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
        response.contentType = "text/html"
        response.status = HttpServletResponse.SC_OK
    }
}
