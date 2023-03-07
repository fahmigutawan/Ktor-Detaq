package com.binbraw.util

import io.ktor.server.config.*
import java.io.File
import java.io.FileInputStream
import java.util.Properties

fun getConfig(
    defaultEnvPath:String,
    hoconApplicationConfig: HoconApplicationConfig
):Config{
    val defaultHoconEnv = hoconApplicationConfig.config("ktor.deployment.$defaultEnvPath")

    return try{
        Config(
            host = System.getenv("HOST"),
            port = Integer.parseInt(System.getenv("PORT")),
            jdbc_url = System.getenv("JDBC_URL"),
            db_username = System.getenv("DB_USERNAME"),
            db_password = System.getenv("DB_PASSWORD"),
            jwt_audience = System.getenv("JWT_AUDIENCE"),
            jwt_issuer = System.getenv("JWT_ISSUER"),
            jwt_realm = System.getenv("JWT_REALM"),
            jwt_secret = System.getenv("JWT_SECRET")
        )
    }catch (e:Exception){
        Config(
            host = defaultHoconEnv.property("host").getString(),
            port = Integer.parseInt(defaultHoconEnv.property("port").getString()),
            jdbc_url = defaultHoconEnv.property("jdbc_url").getString(),
            db_username = defaultHoconEnv.property("db_username").getString(),
            db_password = defaultHoconEnv.property("db_password").getString(),
            jwt_audience = defaultHoconEnv.property("JWT_AUDIENCE").getString(),
            jwt_issuer = defaultHoconEnv.property("JWT_ISSUER").getString(),
            jwt_realm = defaultHoconEnv.property("JWT_REALM").getString(),
            jwt_secret = defaultHoconEnv.property("JWT_SECRET").getString()
        )
    }
}