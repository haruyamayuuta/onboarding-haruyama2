package jp.co.torihada.models

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
}
//class作成
class UsersDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UsersDAO>(UsersDAOs)
    var name by UsersDAOs.name
    val pets by PetsDAO referrersOn PetsDAOs.userId
    //val users by PetsDAO referrersOn PetsDAOs.name
}
fun Application.modelsUsersDAOs() {
    Database.connect("jdbc:mysql://127.0.0.1/test", "com.mysql.cj.jdbc.Driver", "root", "")
    routing {
        get("/users/{id}") {
            val sid = call.parameters["id"]
            val id: Int = Integer.parseInt(sid)
            val userData = transaction {
                UsersDAO.findById(id)!!.name
            }
            call.respondText("$userData")
        }
        get("/users/{id}/pets_v2"){
            val uid = call.parameters["id"]!!.toInt()
            val pets= transaction {
                UsersDAO.findById(uid)?.pets?.map { it.name }
            }
            val petNames = pets!!.joinToString(",")
            call.respondText("$uid"+"の買っているペットの数は"+"${pets!!.size}"+"頭で、それぞれの名前は、"+"$petNames"+"です。")
        }
    }
}
