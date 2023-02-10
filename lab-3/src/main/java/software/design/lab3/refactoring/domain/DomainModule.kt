package software.design.lab3.refactoring.domain

import software.design.lab3.refactoring.domain.repository.ProductRepository
import software.design.lab3.refactoring.domain.repository.impl.ProductRepositoryImpl
import java.sql.DriverManager

object DomainModule {

    private val connectionFactory: ConnectionFactory =
        ConnectionFactory {
            DriverManager.getConnection("jdbc:sqlite:test.db")
        }

    val repository: ProductRepository =
        ProductRepositoryImpl(connectionFactory)
}
