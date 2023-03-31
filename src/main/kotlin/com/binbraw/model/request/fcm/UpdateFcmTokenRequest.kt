package com.binbraw.model.request.fcm

import kotlinx.serialization.Serializable

@Serializable
data class UpdateFcmTokenRequest(
    val fcm_token:String
)
