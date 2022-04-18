package com.example.models

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.*
import org.jetbrains.exposed.sql.*

//オブジェクト作成
object UsersDAOs:IntIdTable("Users"){
    val name = varchar("name",50)
    //val users = reference("name",PetsDAOs)
}
//class作成
class UsersDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UsersDAO>(UsersDAOs)
    var name by UsersDAOs.name
    val pets by PetsDAO referrersOn PetsDAOs.user
    //val users by PetsDAO referrersOn PetsDAOs.name
}
fun Application.usersDao() {
    Database.connect("jdbc:mysql://127.0.0.1/test", "com.mysql.cj.jdbc.Driver", "root", "")
    routing {
        get("/users/{id}") {
            var data: String? = null
            val sid = call.parameters["id"]
            val i: Int = Integer.parseInt(sid)
            transaction {
                val userdata = UsersDAO.findById(i)
                if (userdata != null) {
                    data = userdata.name
                }
            }
            call.respondText("$data")
        }
        get("/users/{id}/pets_v2"){
            val uid = call.parameters["id"]!!.toInt()
            val pets= transaction {
                UsersDAO.findById(uid)?.pets?.toList()
            }
            val petNames = pets?.joinToString(",") { it.user.name }
            call.respondText(petNames.toString())
        }
    }
}
