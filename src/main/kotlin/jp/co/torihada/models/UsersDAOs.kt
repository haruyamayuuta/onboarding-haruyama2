package jp.co.torihada.models

import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.*


object UsersDAOs:IntIdTable("Users"){
    val name = varchar("name",50)
}

class UsersDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UsersDAO>(UsersDAOs)
    var name by UsersDAOs.name
    val pets by PetsDAO referrersOn PetsDAOs.userId
}