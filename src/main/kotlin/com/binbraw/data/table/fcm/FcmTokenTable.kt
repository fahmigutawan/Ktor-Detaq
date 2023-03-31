package com.binbraw.data.table.fcm

import org.jetbrains.exposed.sql.Table

object FcmTokenTable:Table("fcm_token") {
    val uid = uuid("uid")
    val token = varchar("token", 512)
}