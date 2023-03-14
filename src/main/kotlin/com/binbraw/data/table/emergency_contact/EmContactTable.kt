package com.binbraw.data.table.emergency_contact

import org.jetbrains.exposed.sql.Table

object EmContactTable:Table("em_contact") {
    val contact_id = uuid("contact_id")
    val contact = varchar("contact", 32)
    val name = varchar("name", 64)
}