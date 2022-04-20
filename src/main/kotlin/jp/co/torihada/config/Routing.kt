package jp.co.torihada.config

import io.ktor.server.routing.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.thymeleaf.*
import jp.co.torihada.controllers.FormController
import jp.co.torihada.models.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureRouting() {
    Database.connect("jdbc:mysql://127.0.0.1/test", "com.mysql.cj.jdbc.Driver", "root", "")
    val date = LocalDateTime.now()
    val dtformat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
    val fdate = dtformat.format(date)
    routing {
        get("/hello") {
            call.respondText("Hello $fdate")
        }
        get("/hello2/{id}") {
            val id = call.parameters["id"]
            call.respondText("Hello $id")
        }
        //kadai2-7-a
        route(""){
            get("form"){FormController(call).form()}
            post("result"){FormController(call).result()}
        }

        route("users") {
            get("{id}/pets") {
                val uid = call.parameters["id"]!!.toInt()
                val dataPets = transaction {
                    PetsDAO.find { PetsDAOs.userId eq uid }.map { it.name }
                }
                val petCount = dataPets.size
                val petName = dataPets.joinToString(",")
                call.respondText("$uid" + "の買っているペットの数は" + "$petCount" + "頭で、それぞれの名前は、" + "$petName" + "です。")
            }
            get("{id}") {
                val sid = call.parameters["id"]
                val id: Int = Integer.parseInt(sid)
                val userData = transaction {
                    UsersDAO.findById(id)!!.name
                }
                call.respondText("$userData")
            }
            get("{id}/pets_v2") {
                val uid = call.parameters["id"]!!.toInt()
                val pets = transaction {
                    UsersDAO.findById(uid)?.pets?.map { it.name }
                }
                val petNames = pets!!.joinToString(",")
                call.respondText("$uid" + "の買っているペットの数は" + "${pets!!.size}" + "頭で、それぞれの名前は、" + "$petNames" + "です。")
            }
        }
    }
}
