package com.binbraw.data.api.reminder

import com.binbraw.data.table.reminder.DoctorReminderTable
import com.binbraw.model.base.MetaResponse
import com.binbraw.model.request.reminder.doctor_reminder.AddNewDoctorReminderRequest
import com.binbraw.model.response.reminder.doctor_reminder.AllDoctorReminderResponse
import com.binbraw.model.response.reminder.doctor_reminder.SingleDoctorReminderDataResponse
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

object DoctorReminderApi:KoinComponent {
    val docReminderTable by inject<DoctorReminderTable>()

    fun Route.addNewDoctorReminder(path:String){
        post(path){
            val body = call.receive<AddNewDoctorReminderRequest>()
            val uid = call.principal<JWTPrincipal>()!!.payload.getClaim("uid").asString()

            val randomizedUid = UUID.randomUUID()
            transaction{
                docReminderTable.insert {
                    it[docReminderTable.reminder_id] = randomizedUid
                    it[docReminderTable.activity] = body.activity
                    it[docReminderTable.doctor_name] = body.doctor_name
                    it[docReminderTable.date] = body.date
                    it[docReminderTable.time] = body.time
                    it[docReminderTable.uid] = uid
                }
            }.let {
                sendGeneralResponse<Any>(
                    success = true,
                    message = "Add new doctor reminder success",
                    code = HttpStatusCode.OK
                )
            }
        }
    }

    fun Route.getAllDoctorReminder(path:String){
        get(path){
            val uid = call.principal<JWTPrincipal>()!!.payload.getClaim("uid").asString()

            call.respond(
                HttpStatusCode.OK,
                AllDoctorReminderResponse(
                    meta = MetaResponse(
                        success = true,
                        message = "Get all doctor reminder success"
                    ),
                    data = transaction {
                        docReminderTable.select{
                            docReminderTable.uid eq uid
                        }.mapNotNull {
                            SingleDoctorReminderDataResponse(
                                reminder_id = it[docReminderTable.reminder_id].toString(),
                                activity = it[docReminderTable.activity],
                                doctor_name = it[docReminderTable.doctor_name],
                                date = it[docReminderTable.date],
                                time = it[docReminderTable.time]
                            )
                        }
                    }
                )
            )
        }
    }
}