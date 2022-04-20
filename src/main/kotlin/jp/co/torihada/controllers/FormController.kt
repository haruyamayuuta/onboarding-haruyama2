package jp.co.torihada.controllers

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.thymeleaf.*
import io.ktor.server.request.*

class FormController(val call: ApplicationCall){
    suspend fun form(){
        call.respond{ThymeleafContent("form/form", emptyMap())}
    }
    suspend fun result(){
        val post = call.receiveParameters()["name"].toString()
        call.respond(ThymeleafContent("form/result", mapOf("user" to post)))
    }
}
