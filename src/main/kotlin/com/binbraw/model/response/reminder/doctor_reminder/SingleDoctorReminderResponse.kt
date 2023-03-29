package com.binbraw.model.response.reminder.doctor_reminder

import kotlinx.serialization.Serializable

@Serializable
data class SingleDoctorReminderDataResponse(
    val reminder_id:String,
    val activity:String,
    val doctor_name:String,
    val date:String,
    val time:String
)