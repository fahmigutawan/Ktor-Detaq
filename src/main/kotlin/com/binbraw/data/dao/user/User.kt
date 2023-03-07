package com.binbraw.data.dao.user

import org.jetbrains.exposed.sql.Table

object User:Table() {
    val id = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)
    val name = varchar("name", 255)
}