package com.binbraw.util

data class Config(
    val host:String,
    val port:Int,
    val jdbc_url:String,
    val db_username:String,
    val db_password:String
)
