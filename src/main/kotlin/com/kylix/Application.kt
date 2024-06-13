package com.kylix

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.kylix.plugins.*

//fun main() {
//    embeddedServer(Netty, port = 8080, host = "localhost", module = Application::module)
//        .start(wait = true)
//}
fun main() {
    val port = System.getenv("PORT")?.toInt() ?: 8080
    embeddedServer(Netty, port = port, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}
fun Application.module() {
    FirebaseAdmin.init()
    configureRouting()
}
