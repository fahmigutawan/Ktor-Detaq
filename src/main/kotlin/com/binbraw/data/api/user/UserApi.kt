package com.binbraw.data.api.user

import com.binbraw.data.table.general.role.RoleTable
import com.binbraw.data.table.user.UserTable
import com.binbraw.model.base.MetaResponse
import com.binbraw.model.request.user.LoginRequest
import com.binbraw.model.request.user.RegisterRequest
import com.binbraw.model.response.user.*
import com.binbraw.util.PasswordManager
import com.binbraw.util.TokenManager
import com.binbraw.wrapper.sendGeneralResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

object UserApi : KoinComponent {
    val userTable by inject<UserTable>()
    val roleTable by inject<RoleTable>()
    val passwordManager by inject<PasswordManager>()

    fun Route.register(path:String) {
        post(path) {
            val body = call.receive<RegisterRequest>()

            //Check request body
            when {
                !body.email.contains('@') -> {
                    sendGeneralResponse<Any>(
                        success = false,
                        message = "Input the correct email format",
                        code = HttpStatusCode.BadRequest
                    )
                    return@post
                }

                body.name.length > 40 -> {
                    sendGeneralResponse<Any>(
                        success = false,
                        message = "Name must be less than 40 characters",
                        code = HttpStatusCode.BadRequest
                    )
                    return@post
                }

                body.password.length > 20 -> {
                    sendGeneralResponse<Any>(
                        success = false,
                        message = "Password must be less than 20 characters",
                        code = HttpStatusCode.BadRequest
                    )
                    return@post
                }
            }

            //Check if email has been used
            transaction { userTable.select { userTable.email eq (body.email ?: "") }.count() }
                .let {
                    if (it > 0) {
                        sendGeneralResponse<Any>(
                            success = false,
                            message = "Email has been used. Try another email",
                            code = HttpStatusCode.Conflict
                        )
                        return@post
                    }
                }

            //Register flow
            val hashedPw = passwordManager.hashPassword(body.password ?: "")
            val randomizedUid = UUID.randomUUID()
            transaction {
                userTable.insert {
                    userTable.run {
                        it[uid] = randomizedUid
                        it[email] = body.email ?: ""
                        it[password] = hashedPw
                        it[name] = body.name ?: ""
                        it[role_id] = body.role_id ?: 1
                    }
                }
            }
                .let {
                val token = TokenManager.generateJwtToken(randomizedUid.toString())
                call.respond(
                    HttpStatusCode.OK,
                    RegisterResponse(
                        meta = MetaResponse(
                            success = true,
                            message = "User successfully registered"
                        ),
                        data = RegisterResponseData(
                            email = body.email ?: "",
                            token = token
                        )
                    )
                )
            }
        }
    }

    fun Route.login(path:String) {
        post(path) {
            val body = call.receive<LoginRequest>()

            //Check if email has been used
            transaction { userTable.select { userTable.email eq (body.email) }.count() }
                .let {
                    if (it < 1) {
                        sendGeneralResponse<Any>(
                            success = false,
                            message = "Email has not been registered. Try to register first",
                            code = HttpStatusCode.Conflict
                        )
                        return@post
                    }
                }

            //Get user data by email and check the password
            transaction {
                userTable.select {
                    userTable.email eq (body.email)
                }.firstOrNull()
            }?.let {
                if (PasswordManager.checkPassword(body.password, it[userTable.password])) {
                    val token = TokenManager.generateJwtToken(it[userTable.uid].toString())
                    call.respond(
                        HttpStatusCode.OK,
                        LoginResponse(
                            meta = MetaResponse(
                                success = true,
                                message = "Login success"
                            ),
                            data = LoginResponseData(
                                name = it[userTable.name],
                                email = it[userTable.email],
                                token = token
                            )
                        )
                    )
                } else {
                    sendGeneralResponse<Any>(
                        success = false,
                        message = "Password is incorrect, input the correct password",
                        code = HttpStatusCode.BadRequest
                    )
                }
            }
        }
    }

    fun Route.getMyOwnUserInfo(path:String){
        get(path){
            val uid = call.principal<JWTPrincipal>()!!.payload.getClaim("uid").asString()

            val role_id = transaction {
                userTable.select {
                    userTable.uid eq UUID.fromString(uid)
                }.firstOrNull()
            }?.let {
                it[userTable.role_id]
            }

            val role_name = transaction {
                roleTable.select {
                    roleTable.role_id eq (role_id ?: 2)
                }.firstOrNull()
            }?.let {
                it[roleTable.role_name]
            }

            try{
                call.respond(
                    HttpStatusCode.OK,
                    message = transaction {
                        userTable.select {
                            userTable.uid eq UUID.fromString(uid)
                        }.mapNotNull {
                            UserResponse(
                                meta = MetaResponse(
                                    success = true,
                                    message = "Get user success"
                                ),
                                data = UserDataResponse(
                                    email = it[userTable.email],
                                    name = it[userTable.name],
                                    role_name = role_name ?: ""
                                )
                            )
                        }[0]
                    }
                )
            }catch (e:Exception){
                sendGeneralResponse<Any>(
                    success = false,
                    message = "Get user failed by some reason",
                    code = HttpStatusCode.OK
                )
            }
        }
    }
}