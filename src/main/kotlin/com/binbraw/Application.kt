package com.binbraw

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.binbraw.configuration.*
import com.binbraw.di.ApiInjection
import com.binbraw.di.TableInjection
import com.binbraw.data.database.DatabaseProvider
import com.binbraw.util.PasswordManager
import com.binbraw.util.getConfig
import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*
import org.koin.dsl.module
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin

fun main() {
    val config = getConfig(
        defaultEnvPath = "dev",
        hoconApplicationConfig = HoconApplicationConfig(ConfigFactory.load())
    )

    embeddedServer(
        Netty,
        port = config.port,
        host = config.host
    ) {
        module {
            install(Koin) {
                modules(
                    module {
                        single { config }
                        single { DatabaseProvider() }
                        single { PasswordManager }
                    },
                    TableInjection.provide,
                    ApiInjection.provide,
                )
            }
            configurations()
        }
    }.start(wait = true)
}

fun Application.configurations() {
    val databaseProvider by inject<DatabaseProvider>()
    databaseProvider.init()

    configureSerialization()
    configureMonitoring()
    configureHTTP()
    configureAuthorization()
    configureRegularRouting()
    configureAuthorizedRouting()
}
