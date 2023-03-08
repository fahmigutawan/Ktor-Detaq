package com.binbraw.model.request.user

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val email:String?,
    val password:String?,
    val name:String?,
    val role_id:Int?
)
