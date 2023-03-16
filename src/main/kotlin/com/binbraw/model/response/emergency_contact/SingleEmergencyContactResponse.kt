package com.binbraw.model.response.emergency_contact

import com.binbraw.model.base.MetaResponse
import kotlinx.serialization.Serializable

@Serializable
data class SingleEmergencyContactResponse(
    val meta:MetaResponse,
    val data:SingleEmergencyContactDataResponse
)

@Serializable
data class SingleEmergencyContactDataResponse(
    val contact_id:String,
    val contact:String,
    val name:String
)
