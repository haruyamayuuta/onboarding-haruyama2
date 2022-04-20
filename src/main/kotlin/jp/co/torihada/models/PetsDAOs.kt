package jp.co.torihada.models

import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.*

object PetsDAOs:IntIdTable("Pets"){
    val userId = reference("user_id",UsersDAOs)
    val name = varchar("name",50)
}

class PetsDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PetsDAO>(PetsDAOs)
    //var userId by PetsDAOs.userId
    var name by PetsDAOs.name
    var user by UsersDAO referencedOn PetsDAOs.userId
}



