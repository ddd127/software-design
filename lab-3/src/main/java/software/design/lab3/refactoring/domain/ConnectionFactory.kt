package software.design.lab3.refactoring.domain

import java.sql.Connection

fun interface ConnectionFactory {
    fun getConnection(): Connection
}
