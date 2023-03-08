package com.binbraw.data.api.user

import com.binbraw.data.table.user.UserTable
import com.binbraw.model.request.user.RegisterRequest
import com.binbraw.util.PasswordManager
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.exposedLogger
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

object UserApi : KoinComponent {
    val userTable by inject<UserTable>()
    val passwordManager by inject<PasswordManager>()

    fun Route.register() {
        post("/register") {
            val body = call.receive<RegisterRequest>()
            exposedLogger.error(body.toString())

            //Check if email has been used
            transaction { userTable.select { userTable.email eq (body.email ?: "") }.count() }
                .let {
                    if (it > 0) {
                        call.respond(HttpStatusCode.Conflict, "Email has been used, try another email")
                        return@post
                    }else{
                        transaction {
                            userTable.insert {
                                userTable.run {
                                    it[uid]= UUID.randomUUID()
                                    it[email]=body.email ?: ""
                                    it[password]= passwordManager.hashPassword(body.password ?: "")
                                    it[name]=body.name ?: ""
                                    it[role_id]=body.role_id ?: 1
                                }
                            }
                        }.let {
                            call.respond(HttpStatusCode.OK, "Berhasil")
                        }
                    }
                }
        }
    }
}