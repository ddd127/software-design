package software.design.lab6.visitor.pattern.visitor

import software.design.lab6.visitor.pattern.tokenizer.token.Add
import software.design.lab6.visitor.pattern.tokenizer.token.BinaryOp
import software.design.lab6.visitor.pattern.tokenizer.token.Brace
import software.design.lab6.visitor.pattern.tokenizer.token.CloseBrace
import software.design.lab6.visitor.pattern.tokenizer.token.Div
import software.design.lab6.visitor.pattern.tokenizer.token.Mul
import software.design.lab6.visitor.pattern.tokenizer.token.Num
import software.design.lab6.visitor.pattern.tokenizer.token.OpenBrace
import software.design.lab6.visitor.pattern.tokenizer.token.Sub

class PrintVisitor : Visitor<String> {

    private val result = StringBuilder()

    private fun add(value: String) {
        if (result.isNotEmpty() && result.last() != '(' && value != ")") {
            result.append(" ")
        }
        result.append(value)
    }

    override fun visit(number: Num) {
        add(number.evaluate().toString())
    }

    override fun visit(brace: Brace) {
        add(
            when (brace) {
                OpenBrace -> "("
                CloseBrace -> ")"
            },
        )
    }

    override fun visit(operation: BinaryOp) {
        add(
            when (operation) {
                Add -> "+"
                Sub -> "-"
                Mul -> "*"
                Div -> "/"
            },
        )
    }

    override fun result(): String {
        return result.toString().trim()
    }
}
