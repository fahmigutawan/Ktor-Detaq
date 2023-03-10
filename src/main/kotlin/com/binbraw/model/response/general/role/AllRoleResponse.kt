package com.binbraw.model.response.general.role

import com.binbraw.model.base.MetaResponse
import kotlinx.serialization.Serializable

@Serializable
data class AllRoleResponse(
    val meta:MetaResponse,
    val data:List<RoleDataResponse>
)
