package software.design.lab3.refactoring.servlet

import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class QueryServlet : HttpServlet() {

    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        val command = request.getParameter("command")
        if ("max" == command) {
            try {
                DriverManager.getConnection("jdbc:sqlite:test.db").use { c ->
                    val stmt: Statement = c.createStatement()
                    val rs: ResultSet = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1;")
                    response.writer.println("<html><body>")
                    response.writer.println("<h1>Product with max price: </h1>")
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
        } else if ("min" == command) {
            try {
                DriverManager.getConnection("jdbc:sqlite:test.db").use { c ->
                    val stmt: Statement = c.createStatement()
                    val rs: ResultSet = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1;")
                    response.writer.println("<html><body>")
                    response.writer.println("<h1>Product with min price: </h1>")
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
        } else if ("sum" == command) {
            try {
                DriverManager.getConnection("jdbc:sqlite:test.db").use { c ->
                    val stmt: Statement = c.createStatement()
                    val rs: ResultSet = stmt.executeQuery("SELECT SUM(price) FROM PRODUCT;")
                    response.writer.println("<html><body>")
                    response.writer.println("Summary price: ")
                    if (rs.next()) {
                        response.writer.println(rs.getInt(1))
                    }
                    response.writer.println("</body></html>")
                    rs.close()
                    stmt.close()
                }
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        } else if ("count" == command) {
            try {
                DriverManager.getConnection("jdbc:sqlite:test.db").use { c ->
                    val stmt: Statement = c.createStatement()
                    val rs: ResultSet = stmt.executeQuery("SELECT COUNT(*) FROM PRODUCT;")
                    response.writer.println("<html><body>")
                    response.writer.println("Number of products: ")
                    if (rs.next()) {
                        response.writer.println(rs.getInt(1))
                    }
                    response.writer.println("</body></html>")
                    rs.close()
                    stmt.close()
                }
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        } else {
            response.writer.println("Unknown command: $command")
        }
        response.contentType = "text/html"
        response.status = HttpServletResponse.SC_OK
    }
}
