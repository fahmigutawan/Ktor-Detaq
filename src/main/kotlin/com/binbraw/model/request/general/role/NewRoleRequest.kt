package com.binbraw.model.request.general.role

import kotlinx.serialization.Serializable

@Serializable
data class NewRoleRequest(
    val role_id:Int,
    val role_name:String
)
