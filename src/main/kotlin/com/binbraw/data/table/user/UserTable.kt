package com.binbraw.data.table.user

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
}