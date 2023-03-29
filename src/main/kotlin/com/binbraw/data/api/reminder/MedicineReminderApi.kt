package com.binbraw.data.api.reminder

import com.binbraw.data.table.reminder.MedicineReminderTable
import com.binbraw.model.request.medicine_reminder.AddNewMedicineReminderRequest
import com.binbraw.wrapper.sendGeneralResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.insert
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
                sendGeneralResponse<Any>(
                    success = true,
                    message = "Insert medicine reminder success",
                    code = HttpStatusCode.OK
                )
            }
        }
    }
}