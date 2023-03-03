package software.design.lab6.visitor.pattern.visitor

import software.design.lab6.visitor.pattern.tokenizer.token.BinaryOp
import software.design.lab6.visitor.pattern.tokenizer.token.Brace
import software.design.lab6.visitor.pattern.tokenizer.token.CloseBrace
import software.design.lab6.visitor.pattern.tokenizer.token.Num
import software.design.lab6.visitor.pattern.tokenizer.token.OpenBrace
import software.design.lab6.visitor.pattern.tokenizer.token.Token

class ParseVisitor : Visitor<List<Token>> {

    private val result = mutableListOf<Token>()
    private val operations = mutableListOf<Token>()

    override fun visit(number: Num) {
        result.add(number)
    }

    override fun visit(brace: Brace) {
        when (brace) {
            OpenBrace -> operations.add(brace)
            CloseBrace -> {
                while (operations.isNotEmpty() && operations.last() != OpenBrace) {
                    result.add(operations.removeLast())
                }
                if (operations.isNotEmpty()) {
                    operations.removeLast()
                }
            }
        }
    }

    override fun visit(operation: BinaryOp) {
        while (
            operations.isNotEmpty() &&
            operations.last().let { it is BinaryOp && it.precedence >= operation.precedence }
        ) {
            result.add(operations.removeLast())
        }
        operations.add(operation)
    }

    override fun result(): List<Token> {
        return result + operations.reversed()
    }
}
