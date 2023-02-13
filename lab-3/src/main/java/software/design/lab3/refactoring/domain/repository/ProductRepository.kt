package software.design.lab3.refactoring.domain.repository

import software.design.lab3.refactoring.domain.model.Product
import software.design.lab3.refactoring.domain.model.SortDirection

interface ProductRepository {

    fun initTable()

    fun getOrderByPrice(limit: Long, direction: SortDirection): List<Product>
    fun getAll(): List<Product>

    fun create(product: Product)

    fun count(): Long?
    fun priceSum(): Long?
}
