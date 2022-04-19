package jp.co.torihada.models

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.*
import org.jetbrains.exposed.sql.*


//テーブルの枠
object PetsDAOs:IntIdTable("Pets"){
    val userId = reference("user_id",UsersDAOs)
    val name = varchar("name",50)
}
//テーブルにクラス
class PetsDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PetsDAO>(PetsDAOs)
    //var userId by PetsDAOs.userId
    var name by PetsDAOs.name
    var user by UsersDAO referencedOn PetsDAOs.userId
}
fun Application.petDao() {
    Database.connect("jdbc:mysql://127.0.0.1/test", "com.mysql.cj.jdbc.Driver", "root", "")
    routing {
        get("/users/{id}/pets") {
            //入力されたidを使用する
            val uid = call.parameters["id"]!!.toInt()
            val dataPets= transaction {
                PetsDAO.find { PetsDAOs.userId eq uid }.map { it.name}
            }
            val petCount = dataPets.size
            val petName = dataPets.joinToString(",")
            call.respondText("$uid"+"の買っているペットの数は"+"$petCount"+"頭で、それぞれの名前は、"+"$petName" +"です。")
        }
    }
}


