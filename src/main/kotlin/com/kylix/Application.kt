package com.kylix

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.kylix.plugins.*
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

fun main() {
    embeddedServer(Netty, port = 8080, host = "localhost", module = Application::module)
        .start(wait = true)
}

val userApplications = mutableMapOf<Long, UserApplication>()
val userApplicationRepository = UserApplicationRepository("mongodb+srv://testuser:Wrs0DTzoA2V4LGgp@cluster0.m7q3j0q.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0")
//fun main() {
//    val port = System.getenv("PORT")?.toInt() ?: 8080
//    embeddedServer(Netty, port = port, host = "0.0.0.0", module = Application::module)
//        .start(wait = true)
//}
fun Application.module() {
    FirebaseAdmin.init()
    configureRouting()
}
