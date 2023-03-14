package com.binbraw.data.table.user

import com.binbraw.model.base.MetaResponse
import com.binbraw.model.response.user.UserDataResponse
import com.binbraw.model.response.user.UserResponse
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object UserTable : Table("user") {
    val uid = uuid("uid").autoGenerate()
    val email = varchar("email", 255)
    val password = varchar("password", 512)
    val name = varchar("name", 255)
    val role_id = integer("role_id")
    val created_at = varchar("created_at", 255)
    val modified_at = varchar("modified_at", 255)

    fun ResultRow.toUserResponse(role_name:String) = UserResponse(
        meta = MetaResponse(
            success = true,
            message = "Get user success"
        ),
        data = UserDataResponse(
            email = this[email],
            name = this[name],
            role_name = role_name,
            created_at = this[created_at],
            modified_at = this[modified_at]
        )
    )
}