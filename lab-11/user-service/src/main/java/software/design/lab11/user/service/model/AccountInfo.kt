package software.design.lab11.user.service.model

import kotlinx.serialization.Serializable

@Serializable
data class AccountInfo(
    val login: String,
    val balance: Long,
    val userStocks: UserStocks,
    val codeToPrice: Map<String, Long>,
) {
    val codeToAmount: Map<String, Long> =
        userStocks.stocks.mapValues { (_, info) ->
            info.count * codeToPrice.getValue(info.code)
        }
    val totalAmount = codeToAmount.values.sum()
}
