package software.design.lab11.user.service.domain.impl

import org.springframework.stereotype.Repository
import software.design.lab11.user.service.domain.UserStockRepository
import software.design.lab11.user.service.model.StockInfo
import software.design.lab11.user.service.model.UserStocks
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

@Repository
class UserStockRepositoryImpl : UserStockRepository {

    private val userToStocks: ConcurrentMap<Long, ConcurrentMap<String, Long>> = ConcurrentHashMap()

    override fun getStocks(userId: Long): UserStocks {
        return userToStocks.getOrDefault(userId, emptyMap()).toUserStocks()
    }

    override fun addStock(userId: Long, stock: StockInfo): UserStocks {
        return userToStocks.compute(userId) outer@{ _, currentUserStocks ->
            currentUserStocks?.compute(stock.code) inner@{ _, currentCount ->
                (currentCount ?: 0L) + stock.count
            }
            currentUserStocks ?: ConcurrentHashMap(mapOf(stock.code to stock.count))
        }!!.toUserStocks()
    }

    override fun removeStock(userId: Long, stock: StockInfo): UserStocks {
        return userToStocks.computeIfPresent(userId) outer@{ _, currentUserStocks ->
            currentUserStocks.compute(stock.code) inner@{ _, currentCount ->
                currentCount ?: throw NoSuchElementException("Not found such stocks on user account")
                val newCount = currentCount - stock.count
                when {
                    newCount < 0L -> throw IllegalArgumentException("Can't remove more stocks than User has")
                    newCount == 0L -> null
                    else -> newCount
                }
            }
            currentUserStocks
        }?.toUserStocks() ?: throw NoSuchElementException("Not found user with given id")
    }

    companion object {
        private fun Map<String, Long>.toUserStocks(): UserStocks {
            return toMap().mapValues { (code, count) ->
                StockInfo(code, count)
            }.let(::UserStocks)
        }
    }
}
