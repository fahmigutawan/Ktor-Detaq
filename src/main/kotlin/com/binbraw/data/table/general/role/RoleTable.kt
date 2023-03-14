package com.binbraw.data.table.general.role

import com.binbraw.model.base.MetaResponse
import com.binbraw.model.response.general.role.RoleDataResponse
import com.binbraw.model.response.general.role.SingleRoleResponse
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

object RoleTable:Table("role") {
    val role_id = integer("role_id")
    val role_img = varchar("role_img", 255)
    val created_at = varchar("created_at", 255)
    val modified_at = varchar("modified_at", 255)
    val role_name = varchar("role_name", 40)

    fun toSingleRoleResponse(result: ResultRow) = SingleRoleResponse(
        meta = MetaResponse(
            success = true,
            message = "Get role success"
        ),
        data = RoleDataResponse(
            role_id = result[role_id],
            role_name = result[role_name],
            role_img = result[role_img]
        )
    )

    fun toRoleDataResponse(result: ResultRow) = RoleDataResponse(
        role_id = result[role_id],
        role_name = result[role_name],
        role_img = result[role_img]
    )
}