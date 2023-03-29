package com.binbraw.model.response.reminder.doctor_reminder

import com.binbraw.model.base.MetaResponse
import kotlinx.serialization.Serializable

@Serializable
data class AllDoctorReminderResponse(
    val meta:MetaResponse,
    val data:List<SingleDoctorReminderDataResponse>
)
