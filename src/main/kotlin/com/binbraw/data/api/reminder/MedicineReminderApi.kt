package com.binbraw.data.api.reminder

import com.binbraw.data.table.reminder.MedicineReminderTable
import com.binbraw.model.base.MetaResponse
import com.binbraw.model.request.reminder.medicine_reminder.AddNewMedicineReminderRequest
import com.binbraw.model.response.reminder.medicine_reminder.AllMedicineReminderResponse
import com.binbraw.model.response.reminder.medicine_reminder.SingleMedicineReminderDataResponse
import com.binbraw.wrapper.sendGeneralResponse
import com.binbraw.wrapper.sendGeneralResponseWithId
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
import java.util.UUID

object MedicineReminderApi : KoinComponent {
    val medReminderTable by inject<MedicineReminderTable>()
    fun Route.addNewMedicineReminder(path: String) {
        post(path) {
            val uid = call.principal<JWTPrincipal>()!!.payload.getClaim("uid").asString()
            val body = call.receive<AddNewMedicineReminderRequest>()
            val randomizedUid = UUID.randomUUID()
            transaction {
                medReminderTable.insert {
                    it[medReminderTable.reminder_id] = randomizedUid
                    it[medReminderTable.uid] = uid
                    it[medReminderTable.med_name] = body.medicine_name
                    it[medReminderTable.med_dosage] = body.medicine_dosage
                    it[medReminderTable.date_start] = body.date_start
                    it[medReminderTable.date_end] = body.date_end
                    it[medReminderTable.instruction] = body.instruction
                    it[medReminderTable.time] = body.time
                }
            }.let {
                sendGeneralResponseWithId(
                    success = true,
                    message = "Add new doctor reminder success",
                    code = HttpStatusCode.OK,
                    idRelated = randomizedUid.toString()
                )
            }
        }
    }

    fun Route.getAllMedicineReminder(path:String){
        get(path) {
            val uid = call.principal<JWTPrincipal>()!!.payload.getClaim("uid").asString()

            call.respond(
                AllMedicineReminderResponse(
                    meta = MetaResponse(
                        success = true,
                        message = "Get all reminder success"
                    ),
                    data = transaction {
                        medReminderTable.select {
                            medReminderTable.uid eq uid
                        }.mapNotNull {
                            SingleMedicineReminderDataResponse(
                                reminder_id  = it[medReminderTable.reminder_id].toString(),
                                medicine_name = it[medReminderTable.med_name],
                                medicine_dosage = it[medReminderTable.med_dosage],
                                date_start = it[medReminderTable.date_start],
                                date_end = it[medReminderTable.date_end],
                                time = it[medReminderTable.time],
                                instruction = it[medReminderTable.instruction]
                            )
                        }
                    }
                )
            )
        }
    }
}