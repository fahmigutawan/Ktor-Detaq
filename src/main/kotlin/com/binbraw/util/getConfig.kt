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
            jdbc_url = System.getenv("jdbc_url")
        )
    }catch (e:Exception){
        print(e.stackTrace)
        Config(
            host = defaultHoconEnv.property("host").getString(),
            port = Integer.parseInt(defaultHoconEnv.property("port").getString()),
            jdbc_url = defaultHoconEnv.property("jdbc_url").getString()
        )
    }
}