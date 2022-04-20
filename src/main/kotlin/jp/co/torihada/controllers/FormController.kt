package jp.co.torihada.controllers

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.thymeleaf.*
import io.ktor.server.request.*

fun Application.controllerFormController() {
    // Starting point for a Ktor app:
    routing {
        route("/") {
            get("form") {
                call.respond(ThymeleafContent("signin/form", emptyMap()))
            }
            post("result") {
                val post = call.receiveParameters()["name"].toString()
                call.respond(ThymeleafContent("signup/result", mapOf("user" to post)))
            }
        }
    }
}
