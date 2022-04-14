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
    val userid = integer("user_id").uniqueIndex()
    val name = varchar("name",50)
}
//テーブルにクラス
class PetsDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PetsDAO>(PetsDAOs)
    var userid by PetsDAOs.userid
    var name by PetsDAOs.name
}
fun Application.petdao() {
    Database.connect("jdbc:mysql://127.0.0.1/test", "com.mysql.cj.jdbc.Driver", "root", "")
    //データベース内データ数のカウント
    var count:Int = 0
    transaction {
        val pet_all = PetsDAO.all()
        count = pet_all.count().toInt()
    }
    routing {
        get("/users/{id}/pets") {
            var did: Int = 0
            var dname: String? = null
            var dd: String? = null
            var pet_count: Int = 0 //ペットの数
            var allname: String = ""
            transaction {
                //入力されたidを使用する
                val sid = call.parameters["id"]
                //sidをint型に変更
                val i: Int = Integer.parseInt(sid)
                val userdata = PetsDAO.findById(i)
                //didにuserid、dnameにnameを代入
                if (userdata != null) {
                    did = userdata.userid
                    dname = userdata.name
                }
            }
            var t: Int = 0
            var ssid: Int = 0
            var sna: String? = null
            //探索
            while (true) {
                t += 1
                transaction {
                    val se = PetsDAO.findById(t)
                    ssid = se!!.userid
                    sna = se!!.name
                }
                if (ssid == did) {
                    if (t != 1) {
                        allname += ","
                    }
                    allname += "${sna}"
                    pet_count += 1 //ペット数加える
                }
                if (t == count) {
                    break
                }
            }
            call.respondText("$did" + "の買っているペットは" + "$pet_count" + "頭で、それぞれの名前は、" + "$allname"+"です。")
        }
    }
}


