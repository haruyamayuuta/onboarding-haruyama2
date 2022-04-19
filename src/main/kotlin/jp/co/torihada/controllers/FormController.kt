package jp.co.torihada.controllers

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.thymeleaf.*
import io.ktor.server.request.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun Application.excute() {
    // Starting point for a Ktor app:
    routing {
        route("/") {
            get("form") {
                call.respond(ThymeleafContent("form", emptyMap()))
            }
            post("result") {
                val post = call.receiveParameters()["name"].toString()
                call.respond(ThymeleafContent("result", mapOf("user" to post)))
            }
        }
    }
}
data class User(val id: Int, val name: String)
