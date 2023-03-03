package software.design.lab6.visitor.pattern.tokenizer.token

sealed interface Terminal : Token {
    fun evaluate(): Int
}

data class Num(
    private val value: Int,
) : Terminal {
    override fun evaluate(): Int = value
}
