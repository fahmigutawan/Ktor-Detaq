package com.binbraw.model.response.reminder.medicine_reminder

import com.binbraw.model.base.MetaResponse
import kotlinx.serialization.Serializable

@Serializable
data class AllMedicineReminderResponse(
    val meta:MetaResponse,
    val data:List<SingleMedicineReminderDataResponse>
)
