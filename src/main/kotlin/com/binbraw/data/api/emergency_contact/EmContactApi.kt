package com.binbraw.data.api.emergency_contact

import com.binbraw.data.table.emergency_contact.EmContactTable
import com.binbraw.data.table.emergency_contact.EmContactTable.toEmergencyContact
import com.binbraw.data.table.emergency_contact.EmContactWithPatientTable
import com.binbraw.model.request.emergency_contact.NewEmergencyContactRequest
import com.binbraw.wrapper.jwtAuthenticator
import com.binbraw.wrapper.sendGeneralResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

object EmContactApi : KoinComponent {
    val emContactTable by inject<EmContactTable>()
    val emContactWithPatientTable by inject<EmContactWithPatientTable>()

    fun Route.addNewEmergencyContact(path: String) {
        post(path) {
            val request = call.receive<NewEmergencyContactRequest>()
            var isError = false
            jwtAuthenticator { s ->
                transaction {
                    try {
                        val randomized_contact_id = UUID.randomUUID()
                        emContactTable.insert {
                            emContactTable.run {
                                it[contact_id] = randomized_contact_id
                                it[contact] = request.contact
                                it[name] = request.name
                            }
                        }

                        emContactWithPatientTable.insert {
                            emContactWithPatientTable.run {
                                it[uid] = s
                                it[contact_id] = randomized_contact_id.toString()
                            }
                        }
                    } catch (e: Exception) {
                        rollback()
                        isError = true
                    }
                }

                if(isError){
                    sendGeneralResponse<Any>(
                        success = false,
                        message = "Something went wrong, try again later",
                        code = HttpStatusCode.BadRequest
                    )
                }else{
                    sendGeneralResponse<Any>(
                        success = true,
                        message = "New emergency contact has been created",
                        code = HttpStatusCode.OK
                    )
                }
            }
        }
    }

    fun Route.getAllEmergencyContact(path: String) {
        get(path) {
            jwtAuthenticator { s ->
                val page = call.request.queryParameters["page"]?.toIntOrNull()

                when {
                    (page == null) -> {
                        sendGeneralResponse<Any>(
                            success = false,
                            message = "Input the correct page",
                            code = HttpStatusCode.BadRequest
                        )
                        return@jwtAuthenticator
                    }

                    (page < 1) -> {
                        sendGeneralResponse<Any>(
                            success = false,
                            message = "Page must be started with 1",
                            code = HttpStatusCode.BadRequest
                        )
                        return@jwtAuthenticator
                    }
                }
            }
        }
    }

    fun Route.getEmergencyContactByContactId(path: String) {
        get(path) {
            jwtAuthenticator { s ->
                val contact_id = call.request.queryParameters["contact_id"]

                if (contact_id == null) {
                    sendGeneralResponse<Any>(
                        success = false,
                        message = "Input the correct contact id",
                        code = HttpStatusCode.BadRequest
                    )
                    return@jwtAuthenticator
                }

                transaction {
                    emContactTable.select {
                        emContactTable.contact_id eq UUID.fromString(contact_id)
                    }.firstOrNull()
                }?.let {
                    val response = it.toEmergencyContact()
                    call.respond(response)
                }
            }
        }
    }
}
