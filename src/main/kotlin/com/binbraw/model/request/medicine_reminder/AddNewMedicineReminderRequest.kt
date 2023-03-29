package com.binbraw.model.request.medicine_reminder

import kotlinx.serialization.Serializable

@Serializable
data class AddNewMedicineReminderRequest(
    val medicine_name:String,
    val medicine_dosage:String,
    val date_start:String,
    val date_end:String,
    val time:String,
    val instruction:String
)
