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
            host = System.getenv("host"),
            port = Integer.parseInt(System.getenv("port")),
            jdbc_url = System.getenv("jdbc_url"),
            db_username = System.getenv("db_username"),
            db_password = System.getenv("db_password")
        )
    }catch (e:Exception){
        Config(
            host = defaultHoconEnv.property("host").getString(),
            port = Integer.parseInt(defaultHoconEnv.property("port").getString()),
            jdbc_url = defaultHoconEnv.property("jdbc_url").getString(),
            db_username = defaultHoconEnv.property("db_username").getString(),
            db_password = defaultHoconEnv.property("db_password").getString()
        )
    }
}