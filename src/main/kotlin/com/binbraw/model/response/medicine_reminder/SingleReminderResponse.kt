package com.binbraw.model.response.medicine_reminder

import kotlinx.serialization.Serializable

@Serializable
data class SingleReminderDataResponse(
    val reminder_id:String,
    val medicine_name:String,
    val medicine_dosage:String,
    val date_start:String,
    val date_end:String,
    val time:String,
    val instruction:String
)