package com.binbraw.model.request.reminder.doctor_reminder

import kotlinx.serialization.Serializable

@Serializable
data class AddNewDoctorReminderRequest(
    val activity:String,
    val doctor_name:String,
    val date:String,
    val time:String,
)
