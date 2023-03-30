package com.binbraw.model.request.emergency_contact

import kotlinx.serialization.Serializable

@Serializable
data class SendWhatsappRequest(
    val data:List<String>
)