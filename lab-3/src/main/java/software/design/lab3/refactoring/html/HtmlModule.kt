package software.design.lab3.refactoring.html

import software.design.lab3.refactoring.html.impl.ResponseWriterImpl

object HtmlModule {
    val writer: ResponseWriter = ResponseWriterImpl
}
