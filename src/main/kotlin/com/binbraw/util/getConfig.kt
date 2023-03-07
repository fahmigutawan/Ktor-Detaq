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
            supabaseUrl = System.getenv("supabase_url"),
            supabaseKey = System.getenv("supabase_key")
        )
    }catch (e:Exception){
        print(e.stackTrace)
        Config(
            host = defaultHoconEnv.property("host").getString(),
            port = Integer.parseInt(defaultHoconEnv.property("port").getString()),
            supabaseUrl = defaultHoconEnv.property("supabase_url").getString(),
            supabaseKey = defaultHoconEnv.property("supabase_key").getString()
        )
    }
}