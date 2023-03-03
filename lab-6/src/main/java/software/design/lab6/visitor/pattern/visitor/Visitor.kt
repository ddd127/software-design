package software.design.lab6.visitor.pattern.visitor

import software.design.lab6.visitor.pattern.tokenizer.token.BinaryOp
import software.design.lab6.visitor.pattern.tokenizer.token.Brace
import software.design.lab6.visitor.pattern.tokenizer.token.Num
import software.design.lab6.visitor.pattern.tokenizer.token.Token

interface Visitor<T> {

    fun visit(tokens: List<Token>): T {
        tokens.forEach { it.accept(this) }
        return result()
    }

    fun visit(number: Num)
    fun visit(brace: Brace)
    fun visit(operation: BinaryOp)
    fun result(): T
}
