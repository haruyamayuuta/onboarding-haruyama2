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
    //val countber = integer("id").uniqueIndex()
    val userid=integer("user_id").uniqueIndex()
    val name = varchar("name",50)
}
//テーブルにクラス
class PetsDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PetsDAO>(PetsDAOs)
    //var countber by UsersDAO.countber
    var userid by PetsDAOs.userid
    var name by PetsDAOs.name
    //val pets by User referrersOn Users.pets
}


