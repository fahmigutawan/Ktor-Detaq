package com.binbraw.model.request.fcm_token

import kotlinx.serialization.Serializable

@Serializable
data class UpdateFcmTokenRequest(
    val fcm_token:String
)
