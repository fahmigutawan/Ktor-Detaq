package com.binbraw.data.table.emergency_contact

import org.jetbrains.exposed.sql.Table

object EmContactWithPatientTable: Table("em_contact_with_patient") {
    val uid = uuid("uid")
    val contact_id = uuid("contact_id")
}