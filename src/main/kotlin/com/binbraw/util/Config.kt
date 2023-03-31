package com.binbraw.util

data class Config(
    val host:String,
    val port:Int,
    val jdbc_url:String,
    val db_username:String,
    val db_password:String,
    val jwt_secret:String,
    val jwt_issuer:String,
    val jwt_audience:String,
    val jwt_realm:String,
    val pw_salt:String,
    val fcm_access_key:String
)
