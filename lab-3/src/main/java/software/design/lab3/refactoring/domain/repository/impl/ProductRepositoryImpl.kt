package software.design.lab3.refactoring.domain.repository.impl

import software.design.lab3.refactoring.domain.ConnectionFactory
import software.design.lab3.refactoring.domain.model.Product
import software.design.lab3.refactoring.domain.model.SortDirection
import software.design.lab3.refactoring.domain.repository.ProductRepository
import java.sql.ResultSet
import java.sql.Statement

class ProductRepositoryImpl(
    private val connectionFactory: ConnectionFactory,
) : ProductRepository {

    override fun initTable(): Unit =
        withStatement { statement ->
            val sql = """
                CREATE TABLE IF NOT EXISTS PRODUCT
                (
                    ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    NAME                   TEXT          NOT NULL,
                    PRICE                  INT           NOT NULL
                );
            """.trimIndent()
            statement.executeUpdate(sql)
        }

    override fun getOrderByPrice(limit: Int, direction: SortDirection): List<Product> =
        withStatement { statement ->
            statement.executeQuery(
                "SELECT * FROM PRODUCT ORDER BY PRICE ${direction.sql} LIMIT $limit;",
            ).use { resultSet ->
                resultSet.productList()
            }
        }

    override fun getAll(): List<Product> =
        withStatement { statement ->
            statement.executeQuery(
                "SELECT * FROM PRODUCT;",
            ).use { resultSet ->
                resultSet.productList()
            }
        }

    override fun create(product: Product): Unit =
        withStatement { statement ->
            val sql = """
                INSERT INTO PRODUCT (NAME, PRICE)
                VALUES ("${product.name}", ${product.price});
            """.trimIndent()
            statement.executeUpdate(sql)
        }

    override fun count(): Long? =
        withStatement { statement ->
            statement.executeQuery(
                "SELECT COUNT(*) FROM PRODUCT;",
            ).use { resultSet ->
                resultSet.longOrNull(1)
            }
        }

    override fun priceSum(): Long? =
        withStatement { statement ->
            statement.executeQuery(
                "SELECT SUM(price) FROM PRODUCT;",
            ).use { resultSet ->
                resultSet.longOrNull(1)
            }
        }

    private fun <T> withStatement(block: (Statement) -> T): T =
        connectionFactory.getConnection().use { connection ->
            connection.createStatement().use { statement ->
                block(statement)
            }
        }

    companion object {

        fun ResultSet.longOrNull(index: Int): Long? =
            if (next()) getLong(index) else null

        fun ResultSet.productList(): List<Product> =
            buildList {
                while (next()) {
                    this += Product(
                        getString("name"),
                        getLong("price"),
                    )
                }
            }
    }
}
