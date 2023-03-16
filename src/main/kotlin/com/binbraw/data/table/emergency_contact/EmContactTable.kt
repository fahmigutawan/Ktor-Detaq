package com.binbraw.data.table.emergency_contact

import com.binbraw.model.base.MetaResponse
import com.binbraw.model.response.emergency_contact.SingleEmergencyContactDataResponse
import com.binbraw.model.response.emergency_contact.SingleEmergencyContactResponse
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

object EmContactTable:Table("em_contact") {
    val contact_id = uuid("contact_id")
    val contact = varchar("contact", 32)
    val name = varchar("name", 64)

    fun ResultRow.toEmergencyContact() = SingleEmergencyContactResponse(
        meta = MetaResponse(
            success = true,
            message = "Get emergency contact has been succeeded"
        ),
        data = SingleEmergencyContactDataResponse(
            contact_id = this[contact_id].toString(),
            contact = this[contact],
            name = this[name]
        )
    )
}