package com.binbraw.data.dao.user

import org.jetbrains.exposed.sql.Table

object UserDao:Table() {
    override val tableName: String = "user"
    override val primaryKey = PrimaryKey(varchar("uid", 255))

    val uid = varchar("uid", 255)
    val email = varchar("email", 255)
    val password = varchar("password", 255)
    val name = varchar("name", 255)
    val role_id = integer("role_id")
    val created_at = varchar("created_at", 255)
    val modified_at = varchar("modified_at", 255)
}