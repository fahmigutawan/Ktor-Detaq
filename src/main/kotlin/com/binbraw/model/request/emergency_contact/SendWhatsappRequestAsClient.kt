package com.binbraw.model.request.emergency_contact

import kotlinx.serialization.Serializable

@Serializable
data class SendWhatsappRequestAsClient(
    val destination:String,
    val message:String
)