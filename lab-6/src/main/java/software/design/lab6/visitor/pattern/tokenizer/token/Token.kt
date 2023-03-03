package software.design.lab6.visitor.pattern.tokenizer.token

import software.design.lab6.visitor.pattern.visitor.Visitor

sealed interface Token {
    fun accept(visitor: Visitor<*>)
}
