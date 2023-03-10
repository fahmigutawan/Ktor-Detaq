package com.binbraw.data.api.general.role

import com.binbraw.data.table.general.role.RoleTable
import com.binbraw.data.table.general.role.RoleTable.toRoleDataResponse
import com.binbraw.model.base.MetaResponse
import com.binbraw.model.request.general.role.NewRoleRequest
import com.binbraw.model.response.general.role.AllRoleResponse
import com.binbraw.wrapper.sendGeneralResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.selects.select
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

object RoleApi : KoinComponent {
    val roleTable by inject<RoleTable>()

    fun Route.newRole(path: String) {
        post(path) {
            val body = call.receive<NewRoleRequest>()

            //Check length
            when {
                body.role_name.length > 40 -> {
                    sendGeneralResponse<Any>(
                        success = false,
                        message = "Role name must be less than 40 characters",
                        code = HttpStatusCode.BadRequest
                    )
                    return@post
                }
            }

            //Check if role_id has been existed
            transaction {
                roleTable.select { roleTable.role_id eq body.role_id }.count()
            }.let {
                if (it > 0) {
                    sendGeneralResponse<Any>(
                        success = false,
                        message = "role_id has been used, try another role_id",
                        code = HttpStatusCode.BadRequest
                    )
                    return@post
                }
            }

            //Insert
            transaction {
                roleTable.insert {
                    it[role_id] = body.role_id
                    it[role_name] = body.role_name
                    it[role_img] = body.role_img
                }
            }.let {
                sendGeneralResponse<Any>(
                    success = true,
                    message = "New role has been added",
                    code = HttpStatusCode.OK
                )
            }
        }
    }

    fun Route.getAllRole(path: String) {
        get(path) {
            val data = transaction {
                roleTable.selectAll().mapNotNull {
                    roleTable.toRoleDataResponse(it)
                }
            }

            call.respond(
                HttpStatusCode.OK,
                AllRoleResponse(
                    meta = MetaResponse(
                        success = true,
                        message = "Get all roles success"
                    ),
                    data = data
                )
            )
        }
    }

    fun Route.getRoleById(path:String){
        get(path){
            val query = call.parameters["id"]

            if(query == null){
                sendGeneralResponse<Any>(
                    success = false,
                    message = "role_id must be specified when you call this endpoint",
                    code = HttpStatusCode.BadRequest
                )
                return@get
            }

            val data = transaction {
                roleTable.select { roleTable.role_id eq Integer.parseInt(query) }.firstOrNull()
            }

            if(data == null){
                sendGeneralResponse<Any>(
                    success = false,
                    message = "role_id not found, try re-check your input",
                    code = HttpStatusCode.BadRequest
                )
                return@get
            }

            data?.let {
                call.respond(
                    HttpStatusCode.OK,
                    roleTable.toSingleRoleResponse(it)
                )
            }
        }
    }
}