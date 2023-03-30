package com.binbraw.data.table.family

import org.jetbrains.exposed.sql.Table

object PatientWithFamilyTable: Table("patient_with_family") {
    val patient_id = uuid("patient_id")
    val family_id = varchar("family_id", 128)
}