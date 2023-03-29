package com.binbraw.model.response.emergency_contact

import com.binbraw.model.base.MetaResponse
import kotlinx.serialization.Serializable

@Serializable
data class AllEmergencyContactResponse(
    val meta:MetaResponse,
    val data:List<SingleEmergencyContactDataResponse>
)
