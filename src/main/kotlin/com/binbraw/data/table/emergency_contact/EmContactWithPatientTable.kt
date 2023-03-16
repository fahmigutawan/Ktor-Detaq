package com.binbraw.data.table.emergency_contact

import org.jetbrains.exposed.sql.Table

object EmContactWithPatientTable: Table("em_contact_with_patient") {
    val uid = varchar("uid", 128)
    val contact_id = varchar("contact_id", 128)
}