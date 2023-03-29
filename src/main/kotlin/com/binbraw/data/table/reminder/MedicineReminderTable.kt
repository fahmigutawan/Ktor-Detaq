package com.binbraw.data.table.reminder

import org.jetbrains.exposed.sql.Table

object MedicineReminderTable: Table("medicine_reminder") {
    val reminder_id = uuid("reminder_id")
    val med_name = varchar("med_name", 128)
    val med_dosage = varchar("med_dosage", 128)
    val date_start = varchar("date_start", 128)
    val date_end = varchar("date_end", 128)
    val time = varchar("time", 128)
    val instruction = varchar("instruction", 128)
}