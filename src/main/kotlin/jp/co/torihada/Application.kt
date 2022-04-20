package jp.co.torihada


import io.ktor.server.application.*
import jp.co.torihada.controllers.*
import jp.co.torihada.models.*
import jp.co.torihada.config.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureTemplateEngine()
    configureRouting()
    configureDatabase()
}
