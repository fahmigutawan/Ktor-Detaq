package com.binbraw.model.request.emergency_contact

import kotlinx.serialization.Serializable

@Serializable
data class NewEmergencyContactRequest(
    val contact:String,
    val name:String
)
