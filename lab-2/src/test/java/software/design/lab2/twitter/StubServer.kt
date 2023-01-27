package software.design.lab2.twitter

import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress

object StubServer {

    fun withSubServer(path: String, port: Int, handler: HttpHandler, block: () -> Unit) {
        val server = HttpServer.create(InetSocketAddress(port), 0)
        server.createContext(path, handler)
        server.executor = null
        server.start()
        block()
        server.stop(0)
    }
}
