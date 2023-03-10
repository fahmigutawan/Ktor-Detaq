package com.binbraw.model.response.general.role

import com.binbraw.model.base.MetaResponse
import kotlinx.serialization.Serializable

@Serializable
data class SingleRoleResponse(
    val meta:MetaResponse,
    val data: RoleDataResponse
)
