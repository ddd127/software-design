package software.design.lab3.refactoring.domain

import java.sql.Connection
import java.sql.DriverManager

object DatasourceConfiguration : ConnectionFactory {
    override fun getConnection(): Connection =
        DriverManager.getConnection("jdbc:sqlite:test.db")
}
