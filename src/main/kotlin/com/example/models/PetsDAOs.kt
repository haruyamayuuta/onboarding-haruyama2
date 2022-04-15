package com.example.models

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.*
import org.jetbrains.exposed.sql.*


//テーブルの枠
object PetsDAOs:IntIdTable("Pets"){
    val userId = integer("user_id").uniqueIndex()
    val name = varchar("name",50)
}
//テーブルにクラス
class PetsDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PetsDAO>(PetsDAOs)
    var userId by PetsDAOs.userId
    var name by PetsDAOs.name
}
fun Application.petDao() {
    Database.connect("jdbc:mysql://127.0.0.1/test", "com.mysql.cj.jdbc.Driver", "root", "")

    routing {
        get("/users/{id}/pets") {
            var petCount = 0
            var userData: List<String>
            var petName = ""
            var useId = 0
            //入力されたidを使用する
            val uid = call.parameters["id"]!!.toInt()
            transaction {
                val userPets = PetsDAO.find{PetsDAOs.userId eq uid}
                useId = userPets.map { it.userId }[0]
                petCount = userPets.count().toInt()
                userData = userPets.map { it.name }
                petName = userData.joinToString(",")
            }
            call.respondText("$useId"+"の買っているペットの数は"+"$petCount"+"頭で、それぞれの名前は、"+"$petName" +"です。")
        }
    }
}


