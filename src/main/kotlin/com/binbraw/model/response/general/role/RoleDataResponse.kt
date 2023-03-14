package com.binbraw.model.response.general.role

import kotlinx.serialization.Serializable

@Serializable
data class RoleDataResponse(
    val role_id:Int,
    val role_name:String,
    val role_img:String
)
