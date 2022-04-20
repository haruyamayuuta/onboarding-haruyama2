package jp.co.torihada.config

import io.ktor.server.application.*
import io.ktor.server.thymeleaf.*
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver

fun Application.configureTemplateEngine() {
    install(Thymeleaf) {
        setTemplateResolver(
            ClassLoaderTemplateResolver().apply {
                prefix = "views/"
                suffix = ".html"
                characterEncoding = "utf-8"
            }
        )
    }
}