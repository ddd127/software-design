package software.design.lab11.user.service.domain

import software.design.lab11.user.service.model.StockInfo
import software.design.lab11.user.service.model.UserStocks

interface UserStockRepository {
    fun getStocks(userId: Long): UserStocks
    fun addStock(userId: Long, stock: StockInfo): UserStocks
    fun removeStock(userId: Long, stock: StockInfo): UserStocks
}
