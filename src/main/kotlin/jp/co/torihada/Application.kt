package jp.co.torihada


import io.ktor.server.application.*
import io.ktor.server.thymeleaf.*
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
import jp.co.torihada.controllers.*
import jp.co.torihada.models.*
import jp.co.torihada.config.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureRouting()
    excute()
    usersDao()
    petDao()
    install(Thymeleaf){
        setTemplateResolver(ClassLoaderTemplateResolver().apply {
            prefix = "/"
            suffix = ".html"
            characterEncoding = "utf-8"
        })
    }
}
