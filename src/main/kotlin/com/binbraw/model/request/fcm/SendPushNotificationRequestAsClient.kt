package com.binbraw.model.request.fcm

import kotlinx.serialization.Serializable

@Serializable
data class SendPushNotificationRequestAsClient(
    val to:String,
    val notification:SendPushNotificationRequestAsClientData
)

@Serializable
data class SendPushNotificationRequestAsClientData(
    val body:String,
    val title:String,
    val maps_link:String
)