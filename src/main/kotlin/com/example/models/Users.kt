package com.example.models


import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.*
import org.jetbrains.exposed.sql.*

object Users:IntIdTable(){
    val pets = reference("pet", PetsDAOs)
    val users = reference("user", UsersDAOs)
}
class User(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<User>(Users)
    val pets by User referrersOn PetsDAOs.userId
}

fun Application.userPet(){
    Database.connect("jdbc:mysql://127.0.0.1/test", "com.mysql.cj.jdbc.Driver", "root", "")
    routing {
        get("/users/{id}/pets_v2") {
            //入力されたidを使用する
            val uid = call.parameters["id"]!!.toInt()
            val dataPets= transaction {
                User.findById(uid)
            }
            call.respondText("$dataPets")
        }
    }
}
