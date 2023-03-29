package com.binbraw.data.table.reminder

import org.jetbrains.exposed.sql.Table

object DoctorReminderTable:Table("doctor_reminder") {
    val reminder_id = uuid("reminder_id")
    val activity = varchar("activity", 128)
    val doctor_name = varchar("doctor_name", 128)
    val date = varchar("date", 128)
    val time = varchar("time", 128)
    val uid = varchar("uid", 128)
}