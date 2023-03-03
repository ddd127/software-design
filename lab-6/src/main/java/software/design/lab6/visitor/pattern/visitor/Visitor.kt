package software.design.lab6.visitor.pattern.visitor

import software.design.lab6.visitor.pattern.tokenizer.token.BinaryOp
import software.design.lab6.visitor.pattern.tokenizer.token.Brace
import software.design.lab6.visitor.pattern.tokenizer.token.Num

interface Visitor<T> {
    fun visit(number: Num)
    fun visit(brace: Brace)
    fun visit(operation: BinaryOp)
    fun result(): T
}
