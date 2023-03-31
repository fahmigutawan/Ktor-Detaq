package com.binbraw.data.api.family

import com.binbraw.data.table.family.PatientWithFamilyTable
import com.binbraw.data.table.user.UserTable
import com.binbraw.wrapper.sendGeneralResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

object PatientWithFamilyApi : KoinComponent {
    val patientWithFamilyTable by inject<PatientWithFamilyTable>()
    val userTable by inject<UserTable>()

    fun Route.addNewFamily(path: String) {
        get(path) {
            val uid = call.principal<JWTPrincipal>()!!.payload.getClaim("uid").asString()
            val email = call.parameters["email"]
            val family_id = try {
                val tmp = transaction {
                    userTable.select {
                        userTable.email eq (email ?: "")
                    }.mapNotNull {
                        it[userTable.uid].toString()
                    }
                }

                if (tmp.isEmpty()) {
                    sendGeneralResponse<Any>(
                        success = false,
                        message = "Email not found, please re-check your input",
                        code = HttpStatusCode.BadRequest
                    )
                    ""
                } else {
                    tmp[0]
                }
            } catch (e: Exception) {
                sendGeneralResponse<Any>(
                    success = false,
                    message = e.message.toString(),
                    code = HttpStatusCode.BadRequest
                )
                ""
            }

            if (family_id == "") {
                return@get
            }

            if (family_id == uid) {
                sendGeneralResponse<Any>(
                    success = false,
                    message = "Can't add your own email",
                    code = HttpStatusCode.BadRequest
                )
                return@get
            }

            val availableFamily = transaction {
                patientWithFamilyTable.select {
                    patientWithFamilyTable.patient_id eq UUID.fromString(uid)
                }.mapNotNull {
                    it[patientWithFamilyTable.family_id]
                }
            }

            if(availableFamily.contains(family_id)){
                sendGeneralResponse<Any>(
                    success = false,
                    message = "Account has been on your family list before.",
                    code = HttpStatusCode.BadRequest
                )
                return@get
            }

            transaction {
                patientWithFamilyTable.insert {
                    it[patientWithFamilyTable.patient_id] = UUID.fromString(uid)
                    it[patientWithFamilyTable.family_id] = family_id
                }
            }.let {
                sendGeneralResponse<Any>(
                    success = true,
                    message = "$email has been added to your family list",
                    code = HttpStatusCode.OK
                )
            }
        }
    }
}